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
 * La classe DashboardCentroVaccinale permette di settare/prelevare le informazioni inerenti alla dashboard del centro vaccinale
 *
 * @author Mahdi Said
 */

public class DashboardCentroVaccinale implements Serializable {

    /**
     * <code>serialVersionUID</code> &egrave; utilizzatto per identificare l'oggetto nella classe Serializable.
     * <p>
     * &egrave; dichiarato <strong>final</strong> perch&egrave; di fatto rappresenta una costante
     * &egrave; dichiarato <strong>static</strong> così da poterlo utilizzato senza istanziare l'oggetto
     * &egrave; dichiarato <strong>long</strong> permette di scrivere dati di lunghezza fino a 64 bit
     */

    private static final long serialVersionUID = 1L;

    /**
     * <code>id</code> &egrave; l'id del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int id;

    /**
     * <code>nome</code> il nome del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String nome;

    /**
     * <code>vaccinati_con_eventi_avversi</code> &egrave; il numero di vaccinati che hanno riscontrato eventi avversi
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int vaccinati_con_eventi_avversi;

    /**
     * <code>vaccinati_con_eventi_avversi</code> &egrave; il totale di eventi avversi
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int somma_eventi_avversi;

    /**
     * <code>vaccinati</code> &egrave; il totale di utenti vaccinati
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int vaccinati;

    /**
     * Costrutore della classe
     *
     * @param id &egrave; l'id univoco della dashboard del centro vaccinale
     */

    public DashboardCentroVaccinale(int id) {
        this.id = id;
    }

    /**
     * @return l'identificativo del centro vaccinale
     */

    public int getId() {
        return id;
    }

    /**
     * @param id &egrave; l'identificativo del centro vaccinale
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return il nome del centro vaccinale
     */

    public String getNome() {
        return nome;
    }

    /**
     * @param nome &egrave; il nome del centro vaccinale
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return i vaccinati che hanno riscontrato eventi avversi
     */

    public int getVaccinati_con_eventi_avversi() {
        return vaccinati_con_eventi_avversi;
    }

    /**
     * @param vaccinati_con_eventi_avversi sono i vaccinati che hanno riscontrato eventi avversi
     */

    public void setVaccinati_con_eventi_avversi(int vaccinati_con_eventi_avversi) {
        this.vaccinati_con_eventi_avversi = vaccinati_con_eventi_avversi;
    }

    /**
     * @return il totale degli eventi avversi
     */

    public int getSomma_eventi_avversi() {
        return somma_eventi_avversi;
    }

    /**
     * @param somma_eventi_avversi &egrave; il totale degli eventi avversi
     */

    public void setSomma_eventi_avversi(int somma_eventi_avversi) {
        this.somma_eventi_avversi = somma_eventi_avversi;
    }

    /**
     * @return il numero di utenti vaccinati
     */

    public int getVaccinati() {
        return vaccinati;
    }

    /**
     * @param vaccinati &egrave; il numero di utenti vaccinati
     */

    public void setVaccinati(int vaccinati) {
        this.vaccinati = vaccinati;
    }

    /**
     * @return le informazioni della dashboard del centro vaccinale riunite in un'unica stringa
     */

    public String toString() {
        return "DashboardCentroVaccinale<[" + id + "] " + nome + ", vaccinati con ea=" + vaccinati_con_eventi_avversi +
                ", somma ea=" + somma_eventi_avversi + ", " + vaccinati + ">";
    }
}
