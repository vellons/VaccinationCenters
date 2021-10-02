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
