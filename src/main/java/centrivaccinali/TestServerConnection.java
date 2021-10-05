package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.*;

import java.rmi.RemoteException;
import java.util.List;

public class TestServerConnection {

    public static void main(String[] args) throws RemoteException {

        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server

        List<EventoAvverso> returnList1 = db.getEventiAvversi();
        for (EventoAvverso ea : returnList1) {
            System.out.println(ea.toString());
        }

        List<TipologiaEvento> returnList2 = db.getTipologieEventi();
        for (TipologiaEvento te : returnList2) {
            System.out.println(te.toString());
        }

        List<Vaccinato> returnList3 = db.getVaccinati();
        for (Vaccinato v : returnList3) {
            System.out.println(v.toString());
        }

        List<TipologiaVaccino> returnList4 = db.getTipologiaVaccino();
        for (TipologiaVaccino tv : returnList4) {
            System.out.println(tv.toString());
        }

        List<CentroVaccinale> returnList5 = db.getCentriVaccinali("");
        for (CentroVaccinale tv : returnList5) {
            System.out.println(tv.toString());
        }
    }
}
