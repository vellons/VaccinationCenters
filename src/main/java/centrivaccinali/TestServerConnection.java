package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.TipologiaVaccino;

import java.rmi.RemoteException;
import java.util.List;

public class TestServerConnection {

    public static void main(String[] args) throws RemoteException {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
        List<TipologiaVaccino> returnList = db.getTipologiaVaccino();
        for (TipologiaVaccino tv : returnList) {
            System.out.println(tv.toString());
        }
    }
}
