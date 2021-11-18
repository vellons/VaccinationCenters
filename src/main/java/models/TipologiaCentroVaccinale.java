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
 * La classe TipologiaCentroVaccinale permette di settare/prelevare le informazioni inerenti alle singole tipologie di centro vaccinale
 *
 * @author Mahdi Said
 */

public class TipologiaCentroVaccinale implements Serializable {

    /**
     * <code>serialVersionUID</code> &egrave; utilizzatto per identificare l'oggetto nella classe Serializable.
     * <p>
     * &egrave; dichiarato <strong>final</strong> perch&egrave; di fatto rappresenta una costante
     * &egrave; dichiarato <strong>static</strong> così da poterlo utilizzato senza istanziare l'oggetto
     * &egrave; dichiarato <strong>long</strong> permette di scrivere dati di lunghezza fino a 64 bit
     */

    private static final long serialVersionUID = 1L;

    /**
     * <code>id</code> &egrave; l'id della tipologia di centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int id;

    /**
     * <code>nome</code> &egrave; il nome della tipologia di centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String nome;

    /**
     * Costrutore della classe
     *
     * @param id                   &egrave; l'identificativo della tipologia di centro vaccinale
     */

    public TipologiaCentroVaccinale(int id) {
        this.id = id;
    }

    /**
     * Costrutore della classe
     *
     * @param id                   &egrave; l'identificativo della tipologia di centro vaccinale
     * @param nome                 &egrave; il nome della tipologia di centro vaccinale
     */

    public TipologiaCentroVaccinale(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * @return l'id della tipologia del centro vaccinale
     */

    public int getId() {
        return id;
    }

    /**
     * @param id &egrave; l'id della tipologia del centro vaccinale
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return il nome della tipologia del centro vaccinale
     */

    public String getNome() {
        return nome;
    }

    /**
     * @param nome &egrave; il nome della tipologia del centro vaccinale
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return le informazioni sulla tipologia del centro vaccinale riunite in un'unica stringa
     */

    public String toString() {
        return "TipologiaCentroVaccinale<[" + id + "] " + nome + ">";
    }
}
