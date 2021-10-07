package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

        List<CentroVaccinale> returnList5 = db.getCentriVaccinali("");  // "WHERE LOWER(nome) LIKE '%vac%' "
        for (CentroVaccinale cv : returnList5) {
            System.out.println(cv.toString());
        }

        List<TipologiaCentroVaccinale> returnList6 = db.getTipologiaCentroVaccinale();
        for (TipologiaCentroVaccinale tv : returnList6) {
            System.out.println(tv.toString());
        }

        // togli i commenti multilinea se è necessario

        /*
        //Inserimento di un centro vaccinale
        CentroVaccinale cv = new CentroVaccinale("Premosello", 3, 1, "Via", "Maestri Bocca e Manera", "12", "Premosello", "VB", "28803");
        System.out.println("Centro vaccinale registrato: " + cv.toString() + "\nEsito inserimento: " + db.inserisciCentroVaccinale(cv));
        */

        /*
        //Aggiunta di un nuovo cittadino vaccinato
        Date date = new Date();
        long time = date.getTime();
        Vaccinato newVax = new Vaccinato(UUID.randomUUID().toString(), 2, 3, "Manuel", "Macaj", "CFDEVTEST00C1A00", new Timestamp(time), null, null);
        System.out.println("Utente vaccinato: " + newVax.toString() + "\nEsito registrazione: " + db.inserisciCittadinoVaccinato(newVax));
         */
    }
}
