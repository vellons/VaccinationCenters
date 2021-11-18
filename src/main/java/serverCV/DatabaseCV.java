/*
 * Copyright (c) 2021. University Of Insubria, Varese.
 *
 * Authors:
 * - Vellone Alex 741527 VA
 * - Macaj Manuel 741854 VA
 * - Said Ibrahim Mahdi 741512 VA
 * - Pazienza Silvio 741486 VA
 */

package serverCV;

import global.DatabaseCVInterface;
import models.*;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe DatabaseCV permette di eseguire metodi che il client chiama da remoto con l'utilizzo di
 * RMI.
 *
 * @author Silvio Pazienza, Manuel Macaj, Alex Vellone, Mahdi Said
 */

public class DatabaseCV extends UnicastRemoteObject implements DatabaseCVInterface {

    /**
     * <code>serialVersionUID</code> &egrave; un identificatore univoco della classe
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private static final long serialVersionUID = 1L;

    /**
     * <code>textAreaServerStatus</code> &egrave; un area di testo in cui si mostrano i messaggi di esito
     * dei metodi, informazioni sul server (socket), nuovi connessioni client
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private final JTextArea textAreaServerStatus;

    /**
     * <code>conn</code> &egrave; un oggetto di classe Connection che rappresenta la connessione col
     * database remoto
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private Connection conn;

    /**
     * Costruttore della classe
     *
     * @param textAreaServerStatus &egrave; un area di testo passato come argomento
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    protected DatabaseCV(JTextArea textAreaServerStatus) throws RemoteException {
        super();
        this.textAreaServerStatus = textAreaServerStatus;
    }

    /**
     * <code>setConnection</code> &egrave; un metodo setter per la variabile della classe
     *
     * @param conn &egrave; un oggetto di classe Connection che rappresenta la connessione col
     *             database remoto
     */

    protected void setConnection(Connection conn) {
        this.conn = conn;
    }

    /**
     * <code>logMessage</code> &egrave; un metodo che mostra un messaggio di log all'interno della
     * text area
     *
     * @param message &egrave; una stringa che rappresenta un messaggio da mostrare
     */

    public synchronized void logMessage(String message) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
        String out = "[" + currentTime + "] DatabaseCV: " + message + "\n";
        System.out.print(out);
        textAreaServerStatus.append(out);
        textAreaServerStatus.setCaretPosition(textAreaServerStatus.getDocument().getLength());
    }

    /**
     * <code>isSafeWhere</code> &egrave; un metodo per capire se la clausole where &egrave; accettabile
     *
     * @param &egrave; where una stringa che rappresenta l'operatore condizionale where
     * @return un valore booleano
     */

    private boolean isSafeWhere(String where) {
        where = where.toLowerCase();
        String[] toBeChecked = {";", "\"", "drop", "delete"};
        for (String s : toBeChecked) {
            if (where.contains(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <code>getEventiAvversi</code> &egrave; un metodo per ottenere tutti gli eventi avversi segnalati da tutti gli utenti
     *
     * @return una lista contenente tutti gli eventi avversi
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public List<EventoAvverso> getEventiAvversi() throws RemoteException {
        List<EventoAvverso> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM eventi_avversi;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                EventoAvverso obj = new EventoAvverso(rs.getInt("id"));
                obj.setVaccinato_id(rs.getInt("vaccinato_id"));
                obj.setTipologia_evento_id(rs.getInt("tipologia_evento_id"));
                obj.setSeverita(rs.getInt("severita"));
                obj.setNote(rs.getString("note"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getEventiAvversi()");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>getTipologieEventi</code> &egrave; un metodo per ottenere le tipologie di eventi avversi
     *
     * @return una lista contenente tutte le tipologie di eventi avversi
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public List<TipologiaEvento> getTipologieEventi() throws RemoteException {
        List<TipologiaEvento> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tipologia_evento ORDER BY id ASC;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TipologiaEvento obj = new TipologiaEvento(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getTipologieEventi()");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>getVaccinati</code> &egrave; un metodo per ottenere tutti gli utenti vaccinati
     *
     * @return una lista contenente tutte le persone vaccinate
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public List<Vaccinato> getVaccinati() throws RemoteException {
        List<Vaccinato> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM vaccinati;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Vaccinato obj = new Vaccinato(rs.getInt("id"));
                obj.setId_univoco(rs.getString("id_univoco"));
                obj.setCentro_vaccinale_id(rs.getInt("centro_vaccinale_id"));
                obj.setTipologia_vaccino_id(rs.getInt("tipologia_vaccino_id"));
                obj.setNome(rs.getString("nome"));
                obj.setCognome(rs.getString("cognome"));
                obj.setCodice_fiscale(rs.getString("codice_fiscale"));
                obj.setData_somministrazione(rs.getTimestamp("data_somministrazione"));
                obj.setEmail(rs.getString("email"));
                obj.setPass(rs.getString("pass"));

                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getVaccinati()");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>getVaccinatoByIDUnique</code> &egrave; un metodo per cercare l'utente vaccinato all'interno
     * del db in base all'id univoco
     *
     * @param idUnivoco &egrave; una stringa che rappresenta l'id univoco dell'utente
     * @return la persona vaccinata con un certo id unico, se esiste
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public Vaccinato getVaccinatoByIDUnique(String idUnivoco) throws RemoteException {
        Vaccinato findCitizen = null;
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM vaccinati WHERE id_univoco = '" + idUnivoco + "';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                findCitizen = new Vaccinato(rs.getInt("id"));
                findCitizen.setId_univoco(rs.getString("id_univoco"));
                findCitizen.setCentro_vaccinale_id(rs.getInt("centro_vaccinale_id"));
                findCitizen.setTipologia_vaccino_id(rs.getInt("tipologia_vaccino_id"));
                findCitizen.setNome(rs.getString("nome"));
                findCitizen.setCognome(rs.getString("cognome"));
                findCitizen.setCodice_fiscale(rs.getString("codice_fiscale"));
                findCitizen.setData_somministrazione(rs.getTimestamp("data_somministrazione"));
                findCitizen.setEmail(rs.getString("email"));
                findCitizen.setPass(rs.getString("pass"));
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getVaccinatoByIDUnique()");
            e.printStackTrace();
        }

        return findCitizen;
    }

    /**
     * <code>getVaccinatoByEmailAndPasswordSha</code> &egrave; un metodo per cercare il vaccinato con
     * email e password inseriti dall'utente
     *
     * @param email    &egrave; una stringa che rappresenta l'email usata dall'utente
     * @param password &egrave; una stringa che rappresenta la password usata dall'utente
     * @return il cittadino vaccinato con email e password specificati, se esiste
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public Vaccinato getVaccinatoByEmailAndPasswordSha(String email, String password) throws RemoteException {
        Vaccinato findCitizen = null;
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            if (email == null || password == null) return null;
            String query = "SELECT * FROM vaccinati WHERE email = '" + email + "' AND pass = '" + Sha1.sha1(password) + "';";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                findCitizen = new Vaccinato(rs.getInt("id"));
                findCitizen.setId_univoco(rs.getString("id_univoco"));
                findCitizen.setCentro_vaccinale_id(rs.getInt("centro_vaccinale_id"));
                findCitizen.setTipologia_vaccino_id(rs.getInt("tipologia_vaccino_id"));
                findCitizen.setNome(rs.getString("nome"));
                findCitizen.setCognome(rs.getString("cognome"));
                findCitizen.setCodice_fiscale(rs.getString("codice_fiscale"));
                findCitizen.setData_somministrazione(rs.getTimestamp("data_somministrazione"));
                findCitizen.setEmail(rs.getString("email"));
                findCitizen.setPass(rs.getString("pass"));
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage("SELECT * FROM vaccinati WHERE email = 'XXX' AND password = 'XXX'; in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getVaccinatoByEmailAndPasswordSha()");
            e.printStackTrace();
        }
        return findCitizen;
    }

    /**
     * <code>getCentriVaccinali</code> &egrave; un metodo per prelevare i centri vaccinali
     *
     * @param where &egrave; una stringa che rappresenta l'operatore condizionale where
     * @return lista dei centri vaccinali
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public List<CentroVaccinale> getCentriVaccinali(String where) throws RemoteException {
        List<CentroVaccinale> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            if (!isSafeWhere(where)) throw new SQLException("Not safe query");
            String query = "SELECT * FROM centri_vaccinali " + where + "ORDER BY id ASC;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                CentroVaccinale obj = new CentroVaccinale(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setTipologia_id(rs.getInt("tipologia_id"));
                obj.setStato(rs.getInt("stato"));
                obj.setIndirizzo_qualificatore(rs.getString("indirizzo_qualificatore"));
                obj.setIndirizzo(rs.getString("indirizzo"));
                obj.setIndirizzo_civico(rs.getString("indirizzo_civico"));
                obj.setIndirizzo_comune(rs.getString("indirizzo_comune"));
                obj.setIndirizzo_sigla_provincia(rs.getString("indirizzo_sigla_provincia"));
                obj.setIndirizzo_cap(rs.getString("indirizzo_cap"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getCentriVaccinali() ");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>getTipologiaVaccino</code> &egrave; un metodo per prelevare le tipologie di vaccino
     *
     * @return lista contenente le tipologie di vaccini esistenti
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public List<TipologiaVaccino> getTipologiaVaccino() throws RemoteException {
        List<TipologiaVaccino> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tipologia_vaccino ORDER BY id ASC;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TipologiaVaccino obj = new TipologiaVaccino(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setProduttore(rs.getString("produttore"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getTipologiaVaccino()");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>getTipologiaCentroVaccinale</code> &egrave; un metodo per prelevare le tipologie di centri
     * vaccinali
     *
     * @return lista delle tipologie di centri vaccinali
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public List<TipologiaCentroVaccinale> getTipologiaCentroVaccinale() throws RemoteException {
        List<TipologiaCentroVaccinale> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tipologia_centro_vaccinale ORDER BY id ASC;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TipologiaCentroVaccinale obj = new TipologiaCentroVaccinale(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getTipologiaCentroVaccinale()");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>getMediaEventiAvversiCV</code> &egrave; un metodo per prelevare la media degli eventi
     * avversi in base all'id del centro vaccinale
     *
     * @param idCV &egrave; una stringa che rappresenta l'id del centro vaccinale
     * @return la media degli eventi avversi con id del centro vaccinale specifico
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public float getMediaEventiAvversiCV(int idCV) throws RemoteException {
        float media = 0;
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT AVG(ea.severita) FROM vaccinati v JOIN eventi_avversi ea ON v.id = ea.vaccinato_id WHERE v.centro_vaccinale_id = " + idCV + ";";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                media = rs.getFloat("avg");
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getMediaEventiAvversiCV()");
            e.printStackTrace();
        }
        return media;
    }

    /**
     * <code>getDashboardCVInfo</code> &egrave; un metodo per prelevare le informazioni aggiuntive di
     * un centro vaccinale
     *
     * @param where &egrave; una stringa che rappresenta l'operatore condizionale where
     * @return una lista contente le informazioni aggiuntive di un centro vaccinale
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public List<DashboardCentroVaccinale> getDashboardCVInfo(String where) throws RemoteException {
        List<DashboardCentroVaccinale> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            if (!isSafeWhere(where)) throw new SQLException("Not safe query");
            String query = "SELECT * FROM dashboard_centri_vaccinali " + where + ";"; // Definizione vista in create_table.sql
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                DashboardCentroVaccinale obj = new DashboardCentroVaccinale(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setVaccinati_con_eventi_avversi(rs.getInt("vaccinati_con_ea"));
                obj.setSomma_eventi_avversi(rs.getInt("somma_ea"));
                obj.setVaccinati(rs.getInt("vaccinati"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getDashboardCVInfo()");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>inserisciCentroVaccinale</code> &egrave; un metodo per l'inserimento di un centro
     * vaccinale sul DB remoto
     *
     * @param cv &egrave; rappresenta il centro vaccinale che l'operatore vuole registrare
     * @return true se l'inserimento del centro vaccinale nel DB remoto è andato a buon fine, false altrimenti
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public synchronized boolean inserisciCentroVaccinale(CentroVaccinale cv) throws RemoteException {
        String query = "INSERT INTO centri_vaccinali(nome, tipologia_id, stato," +
                " indirizzo_qualificatore, indirizzo, indirizzo_civico, indirizzo_comune, " +
                "indirizzo_sigla_provincia, indirizzo_cap) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            long startTime = System.nanoTime();

            pStmt.setString(1, cv.getNome());
            pStmt.setInt(2, cv.getTipologia_id());
            pStmt.setInt(3, cv.getStato());
            pStmt.setString(4, cv.getIndirizzo_qualificatore());
            pStmt.setString(5, cv.getIndirizzo());
            pStmt.setString(6, cv.getIndirizzo_civico());
            pStmt.setString(7, cv.getIndirizzo_comune());
            pStmt.setString(8, cv.getIndirizzo_sigla_provincia());
            pStmt.setString(9, cv.getIndirizzo_cap());

            int affectedRows = pStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            pStmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
            return true;
        } catch (Exception e) {
            logMessage("ERROR: inserisciCentroVaccinale()");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * <code>inserisciCittadinoVaccinato</code> &egrave; un metodo per l'inserimento di un cittadino
     * vaccinato sul DB remoto
     *
     * @param vax &egrave; rappresenta l'utente vaccinato che l'operatore registra dopo la somministrazione
     * @return true se l'inserimento del cittadino vaccinato nel DB remoto è andato a buon fine, false altrimenti
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public synchronized boolean inserisciCittadinoVaccinato(Vaccinato vax) throws RemoteException {
        String query = "INSERT INTO vaccinati(id_univoco, centro_vaccinale_id, tipologia_vaccino_id," +
                " nome, cognome, codice_fiscale, data_somministrazione) " +
                "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            long startTime = System.nanoTime();

            pStmt.setString(1, vax.getId_univoco());
            pStmt.setInt(2, vax.getCentro_vaccinale_id());
            pStmt.setInt(3, vax.getTipologia_vaccino_id());
            pStmt.setString(4, vax.getNome());
            pStmt.setString(5, vax.getCognome());
            pStmt.setString(6, vax.getCodice_fiscale());
            pStmt.setTimestamp(7, vax.getData_somministrazione());

            int affectedRows = pStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            pStmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
            return true;
        } catch (Exception e) {
            logMessage("ERROR: inserisciCittadinoVaccinato()");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * <code>rowCounterInTable</code> &egrave; un metodo per contare quante righe sono presenti nella
     * tabella che è stata passata
     *
     * @param table &egrave; rappresenta la tabella su cui vogliamo effettuare il conteggio delle tuple
     * @return il conteggio delle tuple della tabella passata
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public int rowCounterInTable(String table) throws RemoteException {
        int rowCounter = 0;
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + ";";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                rowCounter = rs.getInt("count");
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: rowCounterInTable(" + table + ")");
            e.printStackTrace();
        }
        return rowCounter;
    }

    /**
     * <code>vaccinatiOggi</code> &egrave; un metodo per contare il numero di utenti vaccinati
     *
     * @return il conteggio degli utenti vaccinati in un centro nella data corrente
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public int vaccinatiOggi() throws RemoteException {
        int totVaccinatiOggi = 0;
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT count(v.id) FROM vaccinati v" +
                    " WHERE Date(data_somministrazione) = current_date ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                totVaccinatiOggi = rs.getInt("count");
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: vaccinatiOggi()");
            e.printStackTrace();

        }
        return totVaccinatiOggi;
    }

    /**
     * <code>getEventiAvversiCV</code> &egrave; un metodo per contare il numero di eventi avversi per
     * tipologia
     *
     * @param idCV &egrave; un intero che rappresenta l'id del centro vaccinale
     * @return il conteggio di ogni evento avverso, per tipologia
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public Map<String, Integer> getEventiAvversiCV(int idCV) throws RemoteException {
        Map<String, Integer> totEventiAvvPerTipologia = new HashMap<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT te_scroll.nome, (SELECT count(*) FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id " +
                    "INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id" +
                    " WHERE ea.tipologia_evento_id = te_scroll.id AND cv.id = " + idCV + ") total " +
                    "from tipologia_evento te_scroll ORDER BY te_scroll.id ASC;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                totEventiAvvPerTipologia.put(rs.getString("nome"), rs.getInt("total"));
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: resocontoEventiCV()");
            e.printStackTrace();
        }
        return totEventiAvvPerTipologia;
    }

    /**
     * <code>updateRegistraVaccinato</code> &egrave; un metodo per completare la registrazione di un
     * utente vaccinato tramite l'inserimento di email e password
     *
     * @param email     &egrave; una stringa che rappresenta l'email usata dall'utente
     * @param password  &egrave; una stringa che rappresenta la password usata dall'utente
     * @param idUnivoco &egrave; una stringa che rappresenta l'id univoco del vaccinato
     * @return un valore intero che rappresenta l'esito dell'esecuzione del metodo
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public synchronized int updateRegistraVaccinato(String email, String password, String idUnivoco) throws RemoteException {
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            int count = rowCounterInTable("vaccinati WHERE email = '" + email + "'");
            if (count > 0) {
                return 1;
            } else {
                String query = "UPDATE vaccinati SET email = '" + email + "', pass = '" + Sha1.sha1(password) + "' WHERE id_univoco = '" + idUnivoco + "';";
                stmt.executeUpdate(query);
                stmt.close();
                long duration = (System.nanoTime() - startTime) / 1000000;
                logMessage("Aggiornamento email e password utente " + idUnivoco + " completato in: " + duration + "mS");
                return 0;
            }
        } catch (Exception e) {
            logMessage("ERROR: updateRegistraVaccinato()");
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * <code>getEventiAvversiCittadino</code> &egrave; un metodo per prelevare gli eventi avversi di
     * un cittadino specifico
     *
     * @param vaccinatoID &egrave; un intero che rappresenta l'id del vaccinato
     * @return gli eventi avversi di un determinato cittadino
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public synchronized List<EventoAvverso> getEventiAvversiCittadino(int vaccinatoID) throws RemoteException {
        List<EventoAvverso> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM eventi_avversi WHERE vaccinato_id = " + vaccinatoID + ";";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                EventoAvverso obj = new EventoAvverso(rs.getInt("id"));
                obj.setVaccinato_id(rs.getInt("vaccinato_id"));
                obj.setTipologia_evento_id(rs.getInt("tipologia_evento_id"));
                obj.setSeverita(rs.getInt("severita"));
                obj.setNote(rs.getString("note"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: getEventiAvversiCittadino()");
            e.printStackTrace();
        }
        return returnList;
    }

    /**
     * <code>inserisciNuovoEventoAvversoCittadino</code> &egrave; un metodo per l'inserimento di un
     * nuovo evento avverso sul DB remoto
     *
     * @param ea rappresenta l'evento avverso che il cittadino ha segnalato
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public synchronized void inserisciNuovoEventoAvversoCittadino(EventoAvverso ea) throws RemoteException {

        String query = "INSERT INTO eventi_avversi(id, vaccinato_id, tipologia_evento_id, severita, note)" +
                "values (nextval('eventi_avversi_id_seq'),?,?,?,?)";
        try (PreparedStatement pStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            long startTime = System.nanoTime();

            pStmt.setInt(1, ea.getVaccinato_id());
            pStmt.setInt(2, ea.getTipologia_evento_id());
            pStmt.setInt(3, ea.getSeverita());
            pStmt.setString(4, ea.getNote());

            pStmt.executeUpdate();

            pStmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: inserisciNuovoEventoAvversoCittadino()");
            e.printStackTrace();
        }
    }

    /**
     * <code>updateEventoAvverso</code> &egrave; un metodo per aggiornare un evento avverso segnalato
     * dall'utente
     *
     * @param ea rappresenta l'evento avverso che il cittadino ha modificato
     * @return true se l'aggiornamento di uno specifico evento avverso è andato a buon fine, false altrimenti
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public synchronized boolean updateEventoAvverso(EventoAvverso ea) throws RemoteException {
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "UPDATE eventi_avversi SET severita = " + ea.getSeverita() + ", note = '" + ea.getNote() + "' WHERE id = " + ea.getId() + ";";
            stmt.executeUpdate(query);
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
            return true;
        } catch (Exception e) {
            logMessage("ERROR: updateEventoAvverso()");
            e.printStackTrace();
            return false;
        }
    }
}
