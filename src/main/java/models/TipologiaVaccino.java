/*
 * Copyright (c) 2021. University Of Insubria, Varese.
 *
 * Authors:
 * - Vellone Alex 741527 VA
 * - Macaj Manuel 741854 VA
 * - Said Ibrahim Mahdi 741512 VA
 * - Pazienza Silvio 741486 VA
 */

package models;

import java.io.Serializable;

/**
 * La classe TipologiaVaccino permette di settare/prelevare le informazioni inerenti alle singole tipologie di vaccino
 *
 * @author Mahdi Said
 */

public class TipologiaVaccino implements Serializable {

    /**
     * <code>serialVersionUID</code> &egrave; utilizzatto per identificare l'oggetto nella classe Serializable.
     * <p>
     * &egrave; dichiarato <strong>final</strong> perch&egrave; di fatto rappresenta una costante
     * &egrave; dichiarato <strong>static</strong> così da poterlo utilizzato senza istanziare l'oggetto
     * &egrave; dichiarato <strong>long</strong> permette di scrivere dati di lunghezza fino a 64 bit
     */

    private static final long serialVersionUID = 1L;

    /**
     * <code>id</code> &egrave; l'id della tipologia di vaccino
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int id;

    /**
     * <code>nome</code> &egrave; il nome del vaccino
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String nome;

    /**
     * <code>produttore</code> &egrave; il produttore del vaccino
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String produttore;

    /**
     * Costrutore della classe
     *
     * @param id                   &egrave; l'identificativo del vaccino
     */

    public TipologiaVaccino(int id) {
        this.id = id;
    }

    /**
     * Costrutore della classe
     *
     * @param id                   &egrave; l'identificativo del vaccino
     * @param nome                 &egrave; il nome del vaccino
     * @param produttore           &egrave; il produttore del vaccino
     */

    public TipologiaVaccino(int id, String nome, String produttore) {
        this.id = id;
        this.nome = nome;
        this.produttore = produttore;
    }

    /**
     * @return l'id del vaccino
     */

    public int getId() {
        return id;
    }

    /**
     * @return il nome del vaccino
     */

    public String getNome() {
        return nome;
    }

    /**
     * @param nome &egrave; il nome del vaccino
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return il produttore del vaccino
     */

    public String getProduttore() {
        return produttore;
    }

    /**
     * @param produttore &egrave; il produttore del vaccino
     */

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }

    /**
     * @return le informazioni sul vaccino riunite in un'unica stringa
     */

    public String toString() {
        return "TipologiaVaccino<[" + id + "] " + nome + " - " + produttore + ">";
    }
}
