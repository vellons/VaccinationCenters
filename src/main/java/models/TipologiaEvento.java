package models;

import java.io.Serializable;

/**
 * La classe TipologiaEvento permette di settare/prelevare le informazioni inerenti alle singole tipologie di evento avverso
 *
 * @author Mahdi Said
 */

public class TipologiaEvento implements Serializable {

    /**
     * <code>serialVersionUID</code> &egrave; utilizzatto per identificare l'oggetto nella classe Serializable.
     * <p>
     * &egrave; dichiarato <strong>final</strong> perch&egrave; di fatto rappresenta una costante
     * &egrave; dichiarato <strong>static</strong> così da poterlo utilizzato senza istanziare l'oggetto
     * &egrave; dichiarato <strong>long</strong> permette di scrivere dati di lunghezza fino a 64 bit
     */

    private static final long serialVersionUID = 1L;

    /**
     * <code>id</code> &egrave; l'id della tipologia di evento avverso
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int id;

    /**
     * <code>nome</code> &egrave; il nome della tipologia di evento avverso
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String nome;

    /**
     * Costrutore della classe
     *
     * @param id                   &egrave; l'identificativo della tipologia di evento avverso
     * @param nome                 &egrave; il nome della tipologia di evento avverso
     */

    public TipologiaEvento(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    /**
     * Costrutore della classe
     *
     * @param id                   &egrave; l'identificativo della tipologia di evento avverso
     */

    public TipologiaEvento(int id) {
        this.id = id;
    }

    /**
     * @return l'id della tipologia dell'evento avverso
     */

    public int getId() {
        return id;
    }

    /**
     * @param id &egrave; l'id della tipologia dell'evento avverso
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return il nome della tipologia dell'evento avverso
     */

    public String getNome() {
        return nome;
    }

    /**
     * @param nome &egrave; il nome della tipologia dell'evento avverso
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return le informazioni sulla tipologia dell'evento avverso riunite in un'unica stringa
     */

    public String toString() {
        return "TipologiaEvento<[" + id + "] " + nome + ">";
    }
}
