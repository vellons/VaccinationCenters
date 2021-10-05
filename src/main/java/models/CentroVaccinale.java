package models;

import java.io.Serializable;

public class CentroVaccinale implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private int tipologia_id;
    private int stato;
    private String indirizzo_qualificatore;
    private String indirizzo;
    private String indirizzo_civico;
    private String indirizzo_comune;
    private String indirizzo_sigla_provincia;
    private String indirizzo_cap;

    public CentroVaccinale(int id) {
        this.id = id;
    }

    public CentroVaccinale(String nome, int tipologia_id, int stato, String indirizzo_qualificatore,
                           String indirizzo, String indirizzo_civico, String indirizzo_comune,
                           String indirizzo_sigla_provincia, String indirizzo_cap) {
        this.nome = nome;
        this.tipologia_id = tipologia_id;
        this.stato = stato;
        this.indirizzo_qualificatore = indirizzo_qualificatore;
        this.indirizzo = indirizzo;
        this.indirizzo_civico = indirizzo_civico;
        this.indirizzo_comune = indirizzo_comune;
        this.indirizzo_sigla_provincia = indirizzo_sigla_provincia;
        this.indirizzo_cap = indirizzo_cap;
    }

    public CentroVaccinale(int id, String nome, int tipologia_id, int stato, String indirizzo_qualificatore,
                           String indirizzo, String indirizzo_civico, String indirizzo_comune,
                           String indirizzo_sigla_provincia, String indirizzo_cap) {
        this.id = id;
        this.nome = nome;
        this.tipologia_id = tipologia_id;
        this.stato = stato;
        this.indirizzo_qualificatore = indirizzo_qualificatore;
        this.indirizzo = indirizzo;
        this.indirizzo_civico = indirizzo_civico;
        this.indirizzo_comune = indirizzo_comune;
        this.indirizzo_sigla_provincia = indirizzo_sigla_provincia;
        this.indirizzo_cap = indirizzo_cap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipologia_id() {
        return tipologia_id;
    }

    public void setTipologia_id(int tipologia_id) {
        this.tipologia_id = tipologia_id;
    }

    public int getStato() {
        return stato;
    }

    public void setStato(int stato) {
        this.stato = stato;
    }

    public String getIndirizzo_qualificatore() {
        return indirizzo_qualificatore;
    }

    public void setIndirizzo_qualificatore(String indirizzo_qualificatore) {
        this.indirizzo_qualificatore = indirizzo_qualificatore;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo_civico() {
        return indirizzo_civico;
    }

    public void setIndirizzo_civico(String indirizzo_civico) {
        this.indirizzo_civico = indirizzo_civico;
    }

    public String getIndirizzo_comune() {
        return indirizzo_comune;
    }

    public void setIndirizzo_comune(String indirizzo_comune) {
        this.indirizzo_comune = indirizzo_comune;
    }

    public String getIndirizzo_sigla_provincia() {
        return indirizzo_sigla_provincia;
    }

    public void setIndirizzo_sigla_provincia(String indirizzo_sigla_provincia) {
        this.indirizzo_sigla_provincia = indirizzo_sigla_provincia;
    }

    public String getIndirizzo_cap() {
        return indirizzo_cap;
    }

    public void setIndirizzo_cap(String indirizzo_cap) {
        this.indirizzo_cap = indirizzo_cap;
    }

    public String toString() {
        return "CentroVaccinale<[" + id + "] " + nome + ", tipo=" + tipologia_id + ", stato=" + stato + ", " +
                indirizzo_qualificatore + " " + indirizzo + " " + indirizzo_civico + ", " + indirizzo_comune + ", " +
                indirizzo_sigla_provincia + ", " + indirizzo_cap + ">";
    }
}
