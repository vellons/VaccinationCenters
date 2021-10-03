package serverCV;

import global.DatabaseCVInterface;
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
    public List<TipologiaVaccino> getTipologiaVaccino() throws RemoteException {
        List<TipologiaVaccino> returnList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM tipologia_vaccino;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                TipologiaVaccino obj = new TipologiaVaccino();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setProduttore(rs.getString("produttore"));
                returnList.add(obj);
            }
            rs.close();
            stmt.close();
            logMessage(query);
        } catch (Exception e) {
            logMessage("ERROR: getTipologiaVaccino()");
            e.printStackTrace();
        }
        return returnList;
    }
}
