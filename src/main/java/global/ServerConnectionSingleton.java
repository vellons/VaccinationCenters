package global;

import javax.swing.*;
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
            // Recupero dell'IP del registry server se nelle variabili d'ambiente
            if (System.getenv("CV_SERVER_HOST") != null) {
                REGISTRY_SERVER_IP = System.getenv("CV_SERVER_HOST");
            }

            // Richiesta dell'indirizzo ip o hostname del server all'utente
            while (REGISTRY_SERVER_IP == null) {
                String ip = JOptionPane.showInputDialog(null, "Inserisci l'IP del server:", "localhost");
                if (ip != null && !ip.isEmpty()) {
                    if (isValidIP(ip) || ip.equals("localhost")) {
                        REGISTRY_SERVER_IP = ip;  // Se l'indirizzo è valido posso proseguire
                    } else {
                        System.err.println("IP " + ip + " non riconosciuto. Inserisci un IPv4 valido");
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

    public static void resetConnection() {
        singleton_instance = null;
        database = null;
        System.err.println("Reset connection with server");
    }

    public static boolean isValidIP(String ip) {
        // https://stackoverflow.com/questions/4581877/validating-ipv4-string-in-java
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            return !ip.endsWith(".");
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
