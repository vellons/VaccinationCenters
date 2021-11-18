/*
 * Copyright (c) 2021. University Of Insubria, Varese.
 *
 * Authors:
 * - Vellone Alex 741527 VA
 * - Macaj Manuel 741854 VA
 * - Said Ibrahim Mahdi 741512 VA
 * - Pazienza Silvio 741486 VA
 */

package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Classe di test per verificare che la connessione con il database funzioni correttamente, passando dal server.
 *
 * @author Vellons
 */
public class TestServerConnection {

    public static void main(String[] args) throws RemoteException {

        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server

        System.out.println("Totale di persone vaccinate: " + db.rowCounterInTable("vaccinati"));

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
