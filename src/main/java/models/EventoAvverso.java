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
 * La classe EventoAvverso permette di settare/prelevare le informazioni inerenti agli eventi avversi
 *
 * @author Mahdi Said
 */

public class EventoAvverso implements Serializable {

    /**
     * <code>serialVersionUID</code> &egrave; utilizzatto per identificare l'oggetto nella classe Serializable.
     * <p>
     * &egrave; dichiarato <strong>final</strong> perch&egrave; di fatto rappresenta una costante
     * &egrave; dichiarato <strong>static</strong> così da poterlo utilizzato senza istanziare l'oggetto
     * &egrave; dichiarato <strong>long</strong> permette di scrivere dati di lunghezza fino a 64 bit
     */

    private static final long serialVersionUID = 1L;

    /**
     * <code>id</code> &egrave; l'id dell'evento avverso
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int id;

    /**
     * <code>vaccinato_id</code> &egrave; l'id dell'utente vaccinato
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int vaccinato_id;

    /**
     * <code>tipologia_evento_id</code> &egrave; l'id della tipologia di evento avverso
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int tipologia_evento_id;

    /**
     * <code>severita</code> &egrave; la severit&agrave; dell'evento avverso
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int severita;

    /**
     * <code>note</code> sono le note aggiuntive dell'evento avverso
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String note;

    /**
     * Costrutore della classe
     *
     * @param id                           &egrave; l'id univoco dell'evento avverso
     * @param vaccinato_id                 &egrave; l'identificativo dell'utente vaccinato
     * @param tipologia_evento_id          &egrave; l'id della tipologia dell'evento avverso
     * @param severita                     &egrave; la severit&agrave; dell'evento avverso
     * @param note                         sono le note aggiuntive dell'evento avverso
     */

    public EventoAvverso(int id, int vaccinato_id, int tipologia_evento_id, int severita, String note) {
        this.id = id;
        this.vaccinato_id = vaccinato_id;
        this.tipologia_evento_id = tipologia_evento_id;
        this.severita = severita;
        this.note = note;
    }


    /**
     * Costrutore della classe
     *
     * @param vaccinato_id                 &egrave; l'identificativo dell'utente vaccinato
     * @param tipologia_evento_id          &egrave; l'id della tipologia dell'evento avverso
     * @param severita                     &egrave; la severit&agrave; dell'evento avverso
     * @param note                         sono le note aggiuntive dell'evento avverso
     */

    public EventoAvverso(int vaccinato_id, int tipologia_evento_id, int severita, String note) {
        this.vaccinato_id = vaccinato_id;
        this.tipologia_evento_id = tipologia_evento_id;
        this.severita = severita;
        this.note = note;
    }

    /**
     * Costrutore della classe
     *
     * @param id &egrave; l'id univoco dell'evento avverso
     */

    public EventoAvverso(int id) {
        this.id = id;
    }

    /**
     * @return l'identificativo dell'evento avverso
     */

    public int getId() {
        return id;
    }

    /**
     * @param id &egrave; l'identificativo dell'evento avverso
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return l'identificativo dell'utente vaccinato
     */

    public int getVaccinato_id() {
        return vaccinato_id;
    }

    /**
     * @param vaccinato_id &egrave; l'identificativo dell'utente vaccinato
     */

    public void setVaccinato_id(int vaccinato_id) {
        this.vaccinato_id = vaccinato_id;
    }

    /**
     * @return l'identificativo della tipologia di evento avverso
     */

    public int getTipologia_evento_id() {
        return tipologia_evento_id;
    }

    /**
     * @param tipologia_evento_id &egrave; l'identificativo della tipologia dell'evento avverso
     */

    public void setTipologia_evento_id(int tipologia_evento_id) {
        this.tipologia_evento_id = tipologia_evento_id;
    }

    /**
     * @return la severit&agrave; dell'evento avverso
     */

    public int getSeverita() {
        return severita;
    }

    /**
     * @param severita &egrave; la severit&agrave; dell'evento avverso
     */

    public void setSeverita(int severita) {
        this.severita = severita;
    }

    /**
     * @return le note aggiuntive dell'evento avverso
     */

    public String getNote() {
        return note;
    }

    /**
     * @param note sono le note aggiuntive dell'evento avverso
     */

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return le informazioni sull'evento avverso riunite in un'unica stringa
     */

    public String toString() {
        return "EventoAvverso<[" + id + "], vaccinato=" + vaccinato_id + ", tipo=" + tipologia_evento_id + ", severita=" + severita + ", " + note + ">";
    }
}
