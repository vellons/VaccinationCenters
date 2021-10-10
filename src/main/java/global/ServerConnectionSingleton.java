package global;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerConnectionSingleton {
    private static ServerConnectionSingleton singleton_instance = null;
    private static DatabaseCVInterface database;

    private ServerConnectionSingleton() {
        try {
            Registry reg = LocateRegistry.getRegistry(1099);
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
