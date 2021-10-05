package serverCV;

import global.DatabaseCVInterface;
import models.CentroVaccinale;
import models.TipologiaVaccino;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
                obj.setStato(rs.getString("stato"));
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
}
