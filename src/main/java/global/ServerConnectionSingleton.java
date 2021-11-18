/*
 * Copyright (c) 2021. University Of Insubria, Varese.
 *
 * Authors:
 * - Vellone Alex 741527 VA
 * - Macaj Manuel 741854 VA
 * - Said Ibrahim Mahdi 741512 VA
 * - Pazienza Silvio 741486 VA
 */

package global;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * La classe ServerConnectionSingleton ai client di gestire una singola connessione con il server.
 * Questa classe utilizza il Design Pattern Singleton per garantire che esista una sola istanza della classe ServerConnectionSingleton.
 * <br>
 * Questa classe &egrave; molto importante in quanto si occupa di mantenere la connessione con il database.
 * Tutti i metodi utilizzabile sono nella classe DatabaseCVInterface.
 * <br>
 * La porta di default per la connessione al server, tramite RMI &egrave; la 1099.
 * L'indirizzo IP deve essere fornito dall'utente in fase di esecuzione.
 * <br>
 * In alternativa si pu&ograve; usare la variabile <b>CV_SERVER_HOST</b> d'ambiente per specificare l'host del server.
 *
 * @author Alex Vellone
 * @see serverCV.Server
 * @see DatabaseCVInterface
 */
public class ServerConnectionSingleton {

    /**
     * <code>singleton_instance</code> contiene l'unica istanza della classe ServerConnectionSingleton.
     * Deve essere dichiarata private e static per garantire che sia univoca.
     */
    private static ServerConnectionSingleton singleton_instance = null;

    /**
     * <code>database</code> oggetto sul quale eseguire le operazioni a database.
     *
     * @see serverCV.DatabaseCV
     * @see DatabaseCVInterface
     */
    private static DatabaseCVInterface database;

    /**
     * <code>REGISTRY_SERVER_IP</code> contiene l'indirizzo IP del server al quale connettersi
     */
    private static String REGISTRY_SERVER_IP = null;

    /**
     * <code>REGISTRY_SERVER_PORT</code> contiene la porta RMI del server. Di default 1099
     */
    private final static int REGISTRY_SERVER_PORT = 1099;

    /**
     * Costruttore della classe.
     * Il costruttore &egrave; di tipo <b>private</b> perch&egrave; questa classe utilizza il
     * Design Pattern Singleton per garantire che esista una sola istanza della classe ServerConnectionSingleton.
     */
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

    /**
     * <code>getDatabaseInstance</code> metodo per utilizzare l'istanza di questa classe che utilizza il
     * Design Pattern Singleton.
     *
     * @return istanza della classe DatabaseCVInterface
     */
    public static DatabaseCVInterface getDatabaseInstance() {
        if (singleton_instance == null || database == null) {
            singleton_instance = new ServerConnectionSingleton();
        }
        return database;
    }

    /**
     * <code>resetConnection</code> metodo per resettare la connessione con il server.
     * Questo metodo viene chiamato durante la gestione delle eccezioni per assicurarsi
     * che la connessione con il server venga ristabilita in caso di problemi.
     */
    public static void resetConnection() {
        singleton_instance = null;
        database = null;
        System.err.println("Reset connection with server");
    }

    /**
     * <code>isValidIP</code> metodo per verificare che la stinga fornita corrisponda all'indirizzo IPv4
     * @param ip string
     * @return valore booleano
     */
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
