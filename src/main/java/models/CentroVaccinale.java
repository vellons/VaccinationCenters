package models;

import java.io.Serializable;

/**
 * La classe CentroVaccinale permette di settare/prelevare le informazioni inerenti ai centri vaccinali
 *
 * @author Mahdi Said
 */

public class CentroVaccinale implements Serializable {

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
     * <code>tipologia_id</code> &egrave; l'id della tipologia del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int tipologia_id;

    /**
     * <code>stato</code> &egrave; lo stato del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int stato;

    /**
     * <code>indirizzo_qualificatore</code> il qualificatore dell'indirizzo del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String indirizzo_qualificatore;

    /**
     * <code>indirizzo</code> l'indirizzo del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String indirizzo;

    /**
     * <code>indirizzo_civico</code> il nome del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String indirizzo_civico;

    /**
     * <code>indirizzo_comune</code> il comune del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String indirizzo_comune;

    /**
     * <code>indirizzo_sigla_provincia</code> la sigla della provincia del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String indirizzo_sigla_provincia;

    /**
     * <code>indirizzo_cap</code> il cap del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String indirizzo_cap;

    /**
     * Costrutore della classe
     *
     * @param id &egrave; l'id univoco del centro vaccinale
     */

    public CentroVaccinale(int id) {
        this.id = id;
    }

    /**
     * Costrutore della classe
     *
     * @param nome                           &egrave; il nome del centro vaccinale
     * @param tipologia_id                   &egrave; l'identificativo della tipologia del centro vaccinae
     * @param stato                          &egrave; lo stato del centro vaccinale
     * @param indirizzo_qualificatore        &egrave; il qualificatore dell'indirizzo del centro vaccinale
     * @param indirizzo                      &egrave; l'indirizzo del centro vaccinale
     * @param indirizzo_civico               &egrave; il numero civico del centro vaccinale
     * @param indirizzo_comune               &egrave; il comune del centro vaccinale
     * @param indirizzo_sigla_provincia      &egrave; la sigla della provincia del centro vaccinale
     * @param indirizzo_cap                  &egrave; il cap del centro vaccinale
     */

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

    /**
     * Costrutore della classe
     *
     * @param id                             &egrave; l'identificativo del centro vaccinale
     * @param nome                           &egrave; il nome del centro vaccinale
     * @param tipologia_id                   &egrave; l'identificativo della tipologia del centro vaccinae
     * @param stato                          &egrave; lo stato del centro vaccinale
     * @param indirizzo_qualificatore        &egrave; il qualificatore dell'indirizzo del centro vaccinale
     * @param indirizzo                      &egrave; l'indirizzo del centro vaccinale
     * @param indirizzo_civico               &egrave; il numero civico del centro vaccinale
     * @param indirizzo_comune               &egrave; il comune del centro vaccinale
     * @param indirizzo_sigla_provincia      &egrave; la sigla della provincia del centro vaccinale
     * @param indirizzo_cap                  &egrave; il cap del centro vaccinale
     */

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
     * @return l'identificativo della tipologia del centro vaccinale
     */

    public int getTipologia_id() {
        return tipologia_id;
    }

    /**
     * @param tipologia_id &egrave; l'identificativo della tipologia del centro vaccinale
     */

    public void setTipologia_id(int tipologia_id) {
        this.tipologia_id = tipologia_id;
    }

    /**
     * @return lo stato del centro vaccinale
     */

    public int getStato() {
        return stato;
    }

    /**
     * @param stato &egrave; lo stato del centro vaccinale
     */

    public void setStato(int stato) {
        this.stato = stato;
    }

    /**
     * @return il qualificatore dell'indirizzo del centro vaccinale
     */

    public String getIndirizzo_qualificatore() {
        return indirizzo_qualificatore;
    }

    /**
     * @param indirizzo_qualificatore &egrave; il qualificatore dell'indirizzo del centro vaccinale
     */

    public void setIndirizzo_qualificatore(String indirizzo_qualificatore) {
        this.indirizzo_qualificatore = indirizzo_qualificatore;
    }

    /**
     * @return l'indirizzo del centro vaccinale
     */

    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * @param indirizzo &egrave; l'indirizzo del centro vaccinale
     */

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * @return il numero civico del centro vaccinale
     */

    public String getIndirizzo_civico() {
        return indirizzo_civico;
    }

    /**
     * @param indirizzo_civico &egrave; il numero civico del centro vaccinale
     */

    public void setIndirizzo_civico(String indirizzo_civico) {
        this.indirizzo_civico = indirizzo_civico;
    }

    /**
     * @return il comune del centro vaccinale
     */

    public String getIndirizzo_comune() {
        return indirizzo_comune;
    }

    /**
     * @param indirizzo_comune &egrave; il comune del centro vaccinale
     */

    public void setIndirizzo_comune(String indirizzo_comune) {
        this.indirizzo_comune = indirizzo_comune;
    }

    /**
     * @return la sigla della provincia del centro vaccinale
     */

    public String getIndirizzo_sigla_provincia() {
        return indirizzo_sigla_provincia;
    }

    /**
     * @param indirizzo_sigla_provincia &egrave; la sigla della provincia del centro vaccinale
     */

    public void setIndirizzo_sigla_provincia(String indirizzo_sigla_provincia) {
        this.indirizzo_sigla_provincia = indirizzo_sigla_provincia;
    }

    /**
     * @return il cap del centro vaccinale
     */

    public String getIndirizzo_cap() {
        return indirizzo_cap;
    }

    /**
     * @param indirizzo_cap &egrave; il cap del centro vaccinale
     */

    public void setIndirizzo_cap(String indirizzo_cap) {
        this.indirizzo_cap = indirizzo_cap;
    }

    /**
     * @return i campi dell'indirizzo del centro vaccinale riuniti in un'unica stringa
     */

    public String getIndirizzoComposto() {
        return indirizzo_qualificatore + " " + indirizzo + " " + indirizzo_civico + ", " + indirizzo_cap + ", " +
                indirizzo_comune + ", (" + indirizzo_sigla_provincia + ")";
    }

    /**
     * @return le informazioni del centro vaccinale riunite in un'unica stringa
     */

    public String toString() {
        return "CentroVaccinale<[" + id + "] " + nome + ", tipo=" + tipologia_id + ", stato=" + stato + ", " +
                indirizzo_qualificatore + " " + indirizzo + " " + indirizzo_civico + ", " + indirizzo_comune + ", " +
                indirizzo_sigla_provincia + ", " + indirizzo_cap + ">";
    }
}
