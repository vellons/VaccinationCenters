package models;

import java.io.Serializable;

public class TipologiaVaccino implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private String produttore;

    public TipologiaVaccino() {
    }

    public TipologiaVaccino(int id, String nome, String produttore) {
        this.id = id;
        this.nome = nome;
        this.produttore = produttore;
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

    public String getProduttore() {
        return produttore;
    }

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }

    public String toString() {
        return "TipologiaVaccino<[" + id + "] " + nome + " - " + produttore + ">";
    }
}
