--- QUERY DI CREAZIONE DELLE TABELLE PER IL DB
CREATE TABLE tipologia_centro_vaccinale
(
    id   SERIAL PRIMARY KEY,
    nome VARCHAR(32) NOT NULL
);


CREATE TABLE centri_vaccinali
(
    id                        SERIAL PRIMARY KEY,
    nome                      VARCHAR(128)                                      NOT NULL,
    tipologia_id              SERIAL REFERENCES tipologia_centro_vaccinale (id) NOT NULL,
    stato                     NUMERIC(2)                                        NOT NULL,
    indirizzo_qualificatore   VARCHAR(16),
    indirizzo                 VARCHAR(128),
    indirizzo_civico          VARCHAR(8),
    indirizzo_comune          VARCHAR(64),
    indirizzo_sigla_provincia VARCHAR(4),
    indirizzo_cap             VARCHAR(8)
);


CREATE TABLE tipologia_vaccino
(
    id         SERIAL PRIMARY KEY,
    nome       VARCHAR(32) NOT NULL,
    produttore VARCHAR(64) NOT NULL
);


CREATE TABLE vaccinati
(
    id                    SERIAL PRIMARY KEY,
    id_univoco            VARCHAR(128)                             NOT NULL,
    centro_vaccinale_id   SERIAL REFERENCES centri_vaccinali (id)  NOT NULL,
    tipologia_vaccino_id  SERIAL REFERENCES tipologia_vaccino (id) NOT NULL,
    nome                  VARCHAR(64)                              NOT NULL,
    cognome               VARCHAR(64)                              NOT NULL,
    codice_fiscale        VARCHAR(24)                              NOT NULL,
    data_somministrazione TIMESTAMP                                NOT null,
    email                 VARCHAR(128),
    pass                  VARCHAR(128)
);


CREATE TABLE tipologia_evento
(
    id   SERIAL PRIMARY KEY,
    nome VARCHAR(64) NOT NULL
);


CREATE TABLE eventi_avversi
(
    id                  SERIAL PRIMARY KEY,
    vaccinato_id        SERIAL REFERENCES vaccinati (id)        NOT NULL,
    tipologia_evento_id SERIAL REFERENCES tipologia_evento (id) NOT NULL,
    severita            NUMERIC(2)                              NOT NULL,
    note                VARCHAR(256)
);



--- QUERY DI RICERCA UTILI
-- count vaccinati nel db
SELECT COUNT(*) FROM vaccinati;


-- ricerca di un centro vaccinale
SELECT * FROM centri_vaccinali WHERE LOWER(nome) LIKE '%vac%' ORDER BY id DESC;


-- eventi avversi list in un centro vaccinale getEventiAvversiCV(2)
SELECT ea.* FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id WHERE cv.id = '2';


-- count tipologia eventi avversi in un centro vaccinale
SELECT te_scroll.nome, (
    SELECT count(*) FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id
    WHERE ea.tipologia_evento_id = te_scroll.id AND cv.id = '2'
) total from tipologia_evento te_scroll ORDER BY te_scroll.id ASC;


-- numero totale di eventi avversi per ogni centro vaccinale
SELECT cv_scroll.id, cv_scroll.nome, (
    SELECT SUM(total) somma_ea
    FROM (SELECT (
                     SELECT count(*) FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id
                     WHERE ea.tipologia_evento_id = te_scroll.id AND cv.id = cv_scroll.id
                 ) total FROM tipologia_evento te_scroll
         ) eventi_avversi_total
) FROM centri_vaccinali cv_scroll ORDER BY cv_scroll.id DESC;


-- numero di persone con almeno un evento avverso per centro vaccinale
SELECT cv_scroll.id, cv_scroll.nome, (
    SELECT COUNT(persone_con_ea) vaccinati_con_ea
    FROM (SELECT DISTINCT ea.vaccinato_id persone_con_ea FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id WHERE v.centro_vaccinale_id = cv_scroll.id) somma
) FROM centri_vaccinali cv_scroll ORDER BY cv_scroll.id DESC;


-- vaccinati per ogni centro vaccinale vaccinatiPerOgniCentroVaccinale()
SELECT cv.id, cv.nome, count(*) vaccinati FROM vaccinati v INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id GROUP BY cv.id ORDER BY cv.id ASC;


-- VISTA elenco centri vaccinali con numero persone che hanno avuto eventi avversi + numero totale di eventi avversi + numero totale di vaccinati
CREATE OR REPLACE VIEW dashboard_centri_vaccinali as
SELECT t1.*, t2.somma_ea, COALESCE(vaccinati, 0)vaccinati FROM
    (SELECT cv_scroll.id, cv_scroll.nome, (
        SELECT COUNT(persone_con_ea) vaccinati_con_ea
        FROM (SELECT DISTINCT ea.vaccinato_id persone_con_ea FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id WHERE v.centro_vaccinale_id = cv_scroll.id) somma
    ) FROM centri_vaccinali cv_scroll) t1
        JOIN
    (SELECT cv_scroll.id, cv_scroll.nome, (
        SELECT SUM(total) somma_ea
        FROM (SELECT (
                         SELECT count(*) FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id
                         WHERE ea.tipologia_evento_id = te_scroll.id AND cv.id = cv_scroll.id
                     ) total FROM tipologia_evento te_scroll
             ) eventi_avversi_total
    ) FROM centri_vaccinali cv_scroll) t2
    ON t1.id = t2.id
        LEFT JOIN
    (SELECT cv.id, cv.nome, count(*) vaccinati FROM vaccinati v INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id GROUP BY cv.id) t3
    ON t2.id = t3.id
ORDER BY t1.id DESC;

SELECT * FROM dashboard_centri_vaccinali;
