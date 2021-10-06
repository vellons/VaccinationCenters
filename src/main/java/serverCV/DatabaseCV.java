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
import java.util.List;

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

    @Override
    public synchronized void logMessage(String message) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
        String out = "[" + currentTime + "] DatabaseCV: " + message + "\n";
        System.out.print(out);
        textAreaServerStatus.append(out);
        textAreaServerStatus.setCaretPosition(textAreaServerStatus.getDocument().getLength());
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
                obj.setSeverita(rs.getString("severita"));
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
            String query = "SELECT * FROM tipologia_evento;";
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

    @Override
    public List<CentroVaccinale> getCentriVaccinali(String where) throws RemoteException {
        List<CentroVaccinale> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM centri_vaccinali;";
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
            logMessage("ERROR: getCentriVaccinali()");
            e.printStackTrace();
        }
        return returnList;
    }

    @Override
    public List<TipologiaVaccino> getTipologiaVaccino() throws RemoteException {
        List<TipologiaVaccino> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tipologia_vaccino;";
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

    @Override
    public List<TipologiaCentroVaccinale> getTipologiaCentroVaccinale() throws RemoteException { // restituisce lista tipologia centro vaccinale
        List<TipologiaCentroVaccinale> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tipologia_centro_vaccinale;";
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

    @Override
    public List<Vaccinato> getVaccinatiCV(int idCV) throws RemoteException {
        List<Vaccinato> returnList = new ArrayList<>();
        try {
            long startTime = System.nanoTime();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM vaccinati WHERE centro_vaccinale_id = "+ idCV +";";
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
            logMessage("ERROR: getVaccinatiCV()");
            e.printStackTrace();
        }
        return returnList;
    }

    @Override
    public boolean inserisciCentroVaccinale(CentroVaccinale cv) throws RemoteException { // inserimento centro vaccinale nel DB remoto

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
                throw new SQLException("Creating user failed, no rows affected.");
            }

            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
            pStmt.close();
            return true;
        } catch (SQLException throwables) {
            logMessage("ERROR: inserisciCentroVaccinale()");
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean inserisciCittadinoVaccinato(Vaccinato vax) throws RemoteException {
        String query = "INSERT INTO vaccinati(id_univoco, centro_vaccinale_id, tipologia_vaccino_id," +
                " nome, cognome, codice_fiscale, data_somministrazione, email, pass) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            long startTime = System.nanoTime();

            pStmt.setString(1, vax.getId_univoco());
            pStmt.setInt(2, vax.getCentro_vaccinale_id());
            pStmt.setInt(3, vax.getTipologia_vaccino_id());
            pStmt.setString(4, vax.getNome());
            pStmt.setString(5, vax.getCognome());
            pStmt.setString(6, vax.getCodice_fiscale());
            pStmt.setTimestamp(7, vax.getData_somministrazione());
            pStmt.setString(8, vax.getEmail());
            pStmt.setString(9, vax.getPass());

            int affectedRows = pStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            long duration = (System.nanoTime() - startTime) / 1000000;
            logMessage(query + " in: " + duration + "mS");
            pStmt.close();
            return true;
        } catch (SQLException throwables) {
            logMessage("ERROR: inserisciCittadinoVaccinato()");
            throwables.printStackTrace();
            return false;
        }
    }
}