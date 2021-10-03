package centrivaccinali;

import global.DatabaseCVInterface;
import models.TipologiaVaccino;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class TestServerConnection {

    public static void main(String[] args) {
        Registry reg;
        try {
            reg = LocateRegistry.getRegistry(1099);
            DatabaseCVInterface database = (DatabaseCVInterface) reg.lookup("CVDatabaseServer");
            database.logMessage("Hello, world!");

            List<TipologiaVaccino> returnList = database.getTipologiaVaccino();
            database.logMessage(String.valueOf(returnList.size()));

        } catch (RemoteException | NotBoundException e) {
            System.err.println("ERROR: connessione con il server non riuscita: ");
            e.printStackTrace();
        }
    }
}
