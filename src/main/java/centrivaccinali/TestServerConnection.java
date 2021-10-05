package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaVaccino;

import java.rmi.RemoteException;
import java.util.List;

public class TestServerConnection {

    public static void main(String[] args) throws RemoteException {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server

        List<TipologiaVaccino> returnList1 = db.getTipologiaVaccino();
        for (TipologiaVaccino tv : returnList1) {
            System.out.println(tv.toString());
        }

        List<CentroVaccinale> returnList2 = db.getCentriVaccinali("");
        for (CentroVaccinale tv : returnList2) {
            System.out.println(tv.toString());
        }
    }
}
