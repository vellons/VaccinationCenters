package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaCentroVaccinale;
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

        List<TipologiaCentroVaccinale> returnList3 = db.getTipologiaCentroVaccinale();
        for (TipologiaCentroVaccinale tv : returnList3) {
            System.out.println(tv.toString());
        }

        // togli i commenti se è necessario

/*        CentroVaccinale cv = new CentroVaccinale("Premosello", 3, 1, "Via", "Maestri Bocca e Manera", "12", "Premosello", "VB", "28803");
        System.out.println("Centro vaccinale registrato: " + cv.toString() + "\nEsito inserimento: " + db.inserisciCentroVaccinale(cv));

        List<CentroVaccinale> returnList4 = db.getCentriVaccinali("");
        for (CentroVaccinale tv : returnList4) {
            System.out.println(tv.toString());
        }*/
    }
}
