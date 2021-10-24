package models;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * La classe Vaccinato permette di settare/prelevare le informazioni inerenti a un utente vaccinato
 *
 * @author Mahdi Said
 */
public class Vaccinato implements Serializable {

    /**
     * <code>serialVersionUID</code> &egrave; utilizzatto per identificare l'oggetto nella classe Serializable.
     * <p>
     * &egrave; dichiarato <strong>final</strong> perch&egrave; di fatto rappresenta una costante
     * &egrave; dichiarato <strong>static</strong> così da poterlo utilizzato senza istanziare l'oggetto
     * &egrave; dichiarato <strong>long</strong> permette di scrivere dati di lunghezza fino a 64 bit
     */

    private static final long serialVersionUID = 1L;

    /**
     * <code>id</code> &egrave; l'id dell'utente nel database
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int id;

    /**
     * <code>id</code> &egrave; l'id univoco dell'utente generato casualmente
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String id_univoco;

    /**
     * <code>centro_vaccinale_id</code> &egrave; l'id del centro vaccinale
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int centro_vaccinale_id;

    /**
     * <code>tipologia_vaccino_id</code> &egrave; l'id univoco dell'utente
     * <p>
     * &egrave; dichiarato <strong>int</strong> permette di scrivere dati di lunghezza fino a 32 bit
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int tipologia_vaccino_id;

    /**
     * <code>nome</code> &egrave; il nome dell'utente vaccinato
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String nome;

    /**
     * <code>cognome</code> &egrave; il cognome dell'utente vaccinato
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String cognome;

    /**
     * <code>codice_fiscale</code> &egrave; il codice fiscale dell'utente vaccinato
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String codice_fiscale;

    /**
     * <code>data_somministrazione</code> &egrave; la data di somministrazione del vaccino all'utente
     * <p>
     * &egrave; dichiarato <strong>Timestamp</strong> in quanto rappresentante di un momento temporale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private Timestamp data_somministrazione;

    /**
     * <code>email</code> &egrave; l'indirizzo email dell'utente vaccinato
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String email;

    /**
     * <code>ass</code> &egrave; la password scelta dall'utente vaccinato
     * <p>
     * &egrave; dichiarato <strong>String</strong> permette di scrivere stringhe
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private String pass;

    /**
     * Costrutore della classe
     *
     * @param id                           &egrave; l'id univoco dell'utente
     * @param id_univoco                   &egrave; l'identificativo casuale dell'utente
     * @param centro_vaccinale_id          &egrave; l'id del centro vaccinale
     * @param tipologia_vaccino_id         &egrave; l'id della tipologia del vaccino
     * @param nome                         &egrave; il nome dell'utente
     * @param cognome                      &egrave; il cognome dell'utente
     * @param codice_fiscale               &egrave; il codice fiscale dell'utente
     * @param data_somministrazione        &egrave; la data di somministrazione del vaccino
     * @param email                        &egrave; l'email dell'utente
     * @param pass                         &egrave; la password scelta dall'utente
     */

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

    /**
     * Costrutore della classe
     *
     * @param id_univoco                   &egrave; l'identificativo casuale dell'utente
     * @param centro_vaccinale_id          &egrave; l'id del centro vaccinale
     * @param tipologia_vaccino_id         &egrave; l'id della tipologia del vaccino
     * @param nome                         &egrave; il nome dell'utente
     * @param cognome                      &egrave; il cognome dell'utente
     * @param codice_fiscale               &egrave; il codice fiscale dell'utente
     * @param data_somministrazione        &egrave; la data di somministrazione del vaccino
     * @param email                        &egrave; l'email dell'utente
     * @param pass                         &egrave; la password scelta dall'utente
     */

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
    /**
     * Costrutore della classe
     *
     * @param id                           &egrave; l'id univoco dell'utente
     */
    public Vaccinato(int id) {
        this.id = id;
    }

    /**
     * @return l'id dell'utente
     */

    public int getId() {
        return id;
    }

    /**
     * @param id &egrave; l'id dell'utente
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return l'id univoco casuale dell'utente
     */

    public String getId_univoco() {
        return id_univoco;
    }

    /**
     * @param id_univoco &egrave; l'id univoco casuale dell'utente
     */

    public void setId_univoco(String id_univoco) {
        this.id_univoco = id_univoco;
    }

    /**
     * @return l'id del centro vaccinale dell'utente
     */

    public int getCentro_vaccinale_id() {
        return centro_vaccinale_id;
    }

    /**
     * @param centro_vaccinale_id &egrave; l'id del centro vaccinale dell'utente
     */

    public void setCentro_vaccinale_id(int centro_vaccinale_id) {
        this.centro_vaccinale_id = centro_vaccinale_id;
    }

    /**
     * @return l'id della tipologia del vaccino
     */

    public int getTipologia_vaccino_id() {
        return tipologia_vaccino_id;
    }

    /**
     * @param tipologia_vaccino_id &egrave; l'id della tipologia del vaccino
     */

    public void setTipologia_vaccino_id(int tipologia_vaccino_id) {
        this.tipologia_vaccino_id = tipologia_vaccino_id;
    }

    /**
     * @return il nome dell'utente
     */

    public String getNome() {
        return nome;
    }

    /**
     * @param nome &egrave; il nome dell'utente
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return il cognome dell'utente
     */

    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome &egrave; il cognome dell'utente
     */

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return il codice fiscale dell'utente
     */

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    /**
     * @param codice_fiscale &egrave; il codice fiscale dell'utente
     */

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    /**
     * @return la data di somministrazione del vaccino
     */

    public Timestamp getData_somministrazione() {
        return data_somministrazione;
    }

    /**
     * @param data_somministrazione &egrave; la data di somministrazione del vaccino
     */

    public void setData_somministrazione(Timestamp data_somministrazione) {
        this.data_somministrazione = data_somministrazione;
    }

    /**
     * @return l'email dell'utente
     */

    public String getEmail() {
        return email;
    }

    /**
     * @param email &egrave; l'email dell'utente
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return la password dell'utente
     */

    public String getPass() {
        return pass;
    }

    /**
     * @param pass &egrave; la password dell'utente
     */

    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return le informazioni dell'utente vaccinato riunite in un'unica stringa
     */

    public String toString() {
        return "Vaccinato<[" + id + "] " + id_univoco + ", " + centro_vaccinale_id + ", " + tipologia_vaccino_id + ", "
                + nome + ", " + cognome + ", " + codice_fiscale + ", " + data_somministrazione + ", " +
                email + ", " + pass + ">";
    }
}
