package global;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerConnectionSingleton {
    private static ServerConnectionSingleton singleton_instance = null;
    private static DatabaseCVInterface database;
    private static String REGISTRY_SERVER_IP = null;
    private final static int REGISTRY_SERVER_PORT = 1099;

    private ServerConnectionSingleton() {
        try {
            // Recupero dell'ip del registry server se nelle variabili d'ambiente
            if (System.getenv("CV_SERVER_HOST") != null) {
                REGISTRY_SERVER_IP = System.getenv("CV_SERVER_HOST");
            }

            // Richiesta dell'indirizzo ip o hostname del server all'utente
            while (REGISTRY_SERVER_IP == null) {
                String ip = JOptionPane.showInputDialog(null, "Inserisci l'IP del server:", "localhost");
                if (ip != null && !ip.isEmpty()) {
                    try {
                        InetAddress address = InetAddress.getByName(ip);
                        if (address.isReachable(5000)) {
                            REGISTRY_SERVER_IP = ip;  // Se l'indirizzo è valido posso proseguire
                        } else {
                            System.out.println("Host " + ip + " non raggiungibile");
                        }
                    } catch (IOException ignored) {
                    }
                }
            }

            // Creazione del registry RMI
            Registry reg = LocateRegistry.getRegistry(REGISTRY_SERVER_IP, REGISTRY_SERVER_PORT);
            database = (DatabaseCVInterface) reg.lookup("CVDatabaseServer");
            database.logMessage("Nuovo client connesso");
        } catch (RemoteException | NotBoundException e) {
            System.err.println("ERROR: creazione singleton con il server");
            database = null;
            e.printStackTrace();
        }
    }

    public static DatabaseCVInterface getDatabaseInstance() {
        if (singleton_instance == null || database == null) {
            singleton_instance = new ServerConnectionSingleton();
        }
        return database;
    }
}
