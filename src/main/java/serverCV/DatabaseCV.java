package serverCV;

import global.DatabaseCVInterface;
import models.*;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class DatabaseCV extends UnicastRemoteObject implements DatabaseCVInterface {
    private static final long serialVersionUID = 1L;
    private final JTextArea textAreaServerStatus;
    private Connection conn;

    protected DatabaseCV(JTextArea textAreaServerStatus) throws RemoteException {
        super();
        this.textAreaServerStatus = textAreaServerStatus;
    }

    protected void setConnection(Connection conn) {
        this.conn = conn;
    }

    public synchronized void logMessage(String message) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
        String out = "[" + currentTime + "] DatabaseCV: " + message + "\n";
        System.out.print(out);
        textAreaServerStatus.append(out);
        textAreaServerStatus.setCaretPosition(textAreaServerStatus.getDocument().getLength());
    }

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
        } catch (SQLException throwables) {
            logMessage("ERROR: getVaccinatoByIDUnique()");
            throwables.printStackTrace();
        }

        return findCitizen;
    }

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
        } catch (SQLException throwables) {
            logMessage("ERROR: getVaccinatoByEmailAndPasswordSha()");
            throwables.printStackTrace();
        }
        return findCitizen;
    }

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

    public List<EventoAvverso> getEventiAvversiCV(int idCV) throws RemoteException {
        // Restituisce gli eventi avversi di un centro vaccinale
        List<EventoAvverso> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT ea.* FROM eventi_avversi ea INNER JOIN vaccinati v ON ea.vaccinato_id = v.id INNER JOIN centri_vaccinali cv ON v.centro_vaccinale_id = cv.id WHERE cv.id = " + idCV + ";";
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
            logMessage("ERROR: getEventiAvversiCV()");
            e.printStackTrace();
        }
        return returnList;
    }

    public List<DashboardCentroVaccinale> getDashboardCVInfo(String where) throws RemoteException {
        // Restituisce gli eventi avversi di un centro vaccinale
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

    public synchronized boolean inserisciCentroVaccinale(CentroVaccinale cv) throws RemoteException { // inserimento centro vaccinale nel DB remoto

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

    public int rowCounterInTable(String table) throws RemoteException {
        // Restituisce il conteggio delle righe della tabella passata
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

    public int vaccinatiOggi() throws RemoteException {
        //restituisce il conteggio degli utenti vaccinati in un centro nella data corrente
        int totVaccinatiOggi = 0;
        try{
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT count(v.id) FROM vaccinati v" +
                    " WHERE Date(data_somministrazione) = current_date ";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                totVaccinatiOggi=rs.getInt("count");
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: resocontoVaccinatiOggi()");
            e.printStackTrace();

        }
        return totVaccinatiOggi;
    }

    public Map<String, Integer> getCountEventiCV(int idCV) throws RemoteException {
        // restituisce il conteggio gi ogni evento avverso, per tipologia
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

    public Map<Integer, Integer> vaccinatiPerOgniCentroVaccinale() throws RemoteException {
        // Restituisce il conteggio dei vaccinati per ogni centro vaccinale
        Map<Integer, Integer> vaccinatiPerCentro = new HashMap<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT id, vaccinati FROM dashboard_centri_vaccinali;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                vaccinatiPerCentro.put(rs.getInt("id"), rs.getInt("vaccinati"));
            }
            rs.close();
            stmt.close();
            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
        } catch (Exception e) {
            logMessage("ERROR: vaccinatiPerOgniCentroVaccinale()");
            e.printStackTrace();
        }
        return vaccinatiPerCentro;
    }

    public synchronized boolean updateRegistraVaccinato(String email, String password, String idUnivoco) throws RemoteException {

        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            int count = rowCounterInTable("vaccinati WHERE email = '" + email + "'");
            System.out.println(count);
            if (count > 0) {
                System.out.println("L'email esiste già");
                return false;  // TODO SILVIO: codice errore 1
            } else {
                String query = "UPDATE vaccinati SET email = '" + email + "', pass = '" + Sha1.sha1(password) + "' WHERE id_univoco = '" + idUnivoco + "';";
                stmt.executeUpdate(query);
                stmt.close();
                long duration = (System.nanoTime() - startTime) / 1000000;
                logMessage("Aggiornamento utente completato in: " + duration + "mS");
                return true; // TODO SILVIO: codice errore 0
            }

        } catch (Exception e) {
            logMessage("ERROR: updateRegistraVaccinato()");
            e.printStackTrace();
            return false; // TODO SILVIO: codice errore -1
        }
    }

    public synchronized List<EventoAvverso> getEventiAvversiCittadino(int vaccinatoID) throws RemoteException {
        // restituisce gli eventi avversi di un deteteminato cittadino
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

    public synchronized void inserisciNuovoEventoAvversoCittadino(EventoAvverso ea) throws RemoteException {
        // permette l'inserimento di un nuovo evento avverso all'interno del DB remoto

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
}
