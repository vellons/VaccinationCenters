package models;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

public class Vaccinato implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String id_univoco;
    private int centro_vaccinale_id;
    private int tipologia_vaccino_id;
    private String nome;
    private String cognome;
    private String codice_fiscale;
    private Timestamp data_somministrazione;
    private String email;
    private String pass;

    public Vaccinato(int id, String id_univoco, int centro_vaccinale_id, int tipologia_vaccino_id, String nome, String cognome,
                     String codice_fiscale, Timestamp data_somministrazione, String email, String pass) {
        this.id = id;
        this.id_univoco = id_univoco;
        this.centro_vaccinale_id = centro_vaccinale_id;
        this.tipologia_vaccino_id = tipologia_vaccino_id;
        this.nome = nome;
        this.cognome = cognome;
        this.codice_fiscale = codice_fiscale;
        this.data_somministrazione = data_somministrazione;
        this.email = email;
        this.pass = pass;
    }

    public Vaccinato(String id_univoco, int centro_vaccinale_id, int tipologia_vaccino_id, String nome, String cognome,
                     String codice_fiscale, Timestamp data_somministrazione, String email, String pass) {
        this.id_univoco = id_univoco;
        this.centro_vaccinale_id = centro_vaccinale_id;
        this.tipologia_vaccino_id = tipologia_vaccino_id;
        this.nome = nome;
        this.cognome = cognome;
        this.codice_fiscale = codice_fiscale;
        this.data_somministrazione = data_somministrazione;
        this.email = email;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_univoco() {
        return id_univoco;
    }

    public void setId_univoco(String id_univoco) {
        this.id_univoco = id_univoco;
    }

    public int getCentro_vaccinale_id() {
        return centro_vaccinale_id;
    }

    public void setCentro_vaccinale_id(int centro_vaccinale_id) {
        this.centro_vaccinale_id = centro_vaccinale_id;
    }

    public int getTipologia_vaccino_id() {
        return tipologia_vaccino_id;
    }

    public void setTipologia_vaccino_id(int tipologia_vaccino_id) {
        this.tipologia_vaccino_id = tipologia_vaccino_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public Timestamp getData_somministrazione() {
        return data_somministrazione;
    }

    public void setData_somministrazione(Timestamp data_somministrazione) {
        this.data_somministrazione = data_somministrazione;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String toString() {
        return id + ", " + id_univoco + ", " + centro_vaccinale_id + ", " + tipologia_vaccino_id + ", "
                + nome + ", " + cognome + ", " + codice_fiscale + ", " + data_somministrazione + ", " +
                email + ", " + pass;
    }
}
