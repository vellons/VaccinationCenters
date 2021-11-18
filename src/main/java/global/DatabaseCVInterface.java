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

import models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * L'interfaccia DatabaseCVInterface racchiude la definizione di
 * diversi metodi implementati nella classe <code>DatabaseCv</code>
 *
 * @author Silvio Pazienza
 * @see serverCV.DatabaseCV
 */

public interface DatabaseCVInterface extends Remote {

    /**
     * @param message &egrave; una stringa che viene passata al metodo per essere stampata
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    void logMessage(String message) throws RemoteException;

    /**
     * @return lista degli eventi avversi
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<EventoAvverso> getEventiAvversi() throws RemoteException;

    /**
     * @return lista delle tipologie di eventi avversi
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<TipologiaEvento> getTipologieEventi() throws RemoteException;

    /**
     * @return lista di tutti gli utenti vaccinati
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<Vaccinato> getVaccinati() throws RemoteException;

    /**
     * @param where &egrave; una stringa che rappresenta l'operatore condizionale where
     * @return lista dei centri vaccinali
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<CentroVaccinale> getCentriVaccinali(String where) throws RemoteException;

    /**
     * @return lista delle tipologie di vaccino esistenti
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<TipologiaVaccino> getTipologiaVaccino() throws RemoteException;

    /**
     * @return lista delle tipologie di centro vaccinale
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<TipologiaCentroVaccinale> getTipologiaCentroVaccinale() throws RemoteException;

    /**
     * @param idUnivoco &egrave; una stringa che rappresenta l'id univoco dell'utente
     * @return l'utente vaccinato in base all'id univoco, se esiste
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    Vaccinato getVaccinatoByIDUnique(String idUnivoco) throws RemoteException;

    /**
     * @param email    &egrave; una stringa che rappresenta l'email usata dall'utente
     * @param password &egrave; una stringa che rappresenta la password usata dall'utente
     * @return l'utente vaccinato in base all'email e password, se esiste
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    Vaccinato getVaccinatoByEmailAndPasswordSha(String email, String password) throws RemoteException;

    /**
     * @param idCV &egrave; una stringa che rappresenta l'id del centro vaccinale
     * @return la media degli eventi avversi di un determinato centro vaccinale, specificato dall'id
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    float getMediaEventiAvversiCV(int idCV) throws RemoteException;

    /**
     * @param where &egrave; una stringa che rappresenta l'operatore condizionale where
     * @return contente delle informazioni aggiuntive dei vari centri vaccinali
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<DashboardCentroVaccinale> getDashboardCVInfo(String where) throws RemoteException;

    /**
     * @param cv rappresenta il centro vaccinale che l'operatore vuole registrare
     * @return true se riesco a inserire un centro vaccinale, altrimenti false
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    boolean inserisciCentroVaccinale(CentroVaccinale cv) throws RemoteException;

    /**
     * @param vax &egrave; rappresenta l'utente vaccinato che l'operatore registra dopo la somministrazione
     * @return true se riesco a inserire un cittadino vaccinato, altrimenti false
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    boolean inserisciCittadinoVaccinato(Vaccinato vax) throws RemoteException;

    /**
     * @param table rappresenta la tabella su cui vogliamo effettuare il conteggio delle tuple
     * @return il numero di tuple nella tabella passata
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    int rowCounterInTable(String table) throws RemoteException;

    /**
     * @return il numero di utenti vaccinati nel giorno odierno
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    int vaccinatiOggi() throws RemoteException;

    /**
     * @param idCV &egrave; un intero che rappresenta l'id del centro vaccinale
     * @return gli eventi avversi di un centro vaccinale con id specifico
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    Map<String, Integer> getEventiAvversiCV(int idCV) throws RemoteException;

    /**
     * @param email     &egrave; una stringa che rappresenta l'email usata dall'utente
     * @param password  &egrave; una stringa che rappresenta la password usata dall'utente
     * @param idUnivoco &egrave; una stringa che rappresenta l'id univoco del vaccinato
     * @return un risultato intero che indica se si &egrave; riusciti o meno a registrare l'utente vaccinato
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    int updateRegistraVaccinato(String email, String password, String idUnivoco) throws RemoteException;

    /**
     * @param vaccinatoID &egrave; un intero che rappresenta l'id del vaccinato
     * @return lista degli eventi avversi del cittadino loggato
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    List<EventoAvverso> getEventiAvversiCittadino(int vaccinatoID) throws RemoteException;

    /**
     * @param ea rappresenta l'evento avverso che il cittadino ha segnalato
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    void inserisciNuovoEventoAvversoCittadino(EventoAvverso ea) throws RemoteException;

    /**
     * @param ea rappresenta l'evento avverso che il cittadino vuole modificare
     * @return true se riesco a modificare un evento avverso, false altrimenti
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    boolean updateEventoAvverso(EventoAvverso ea) throws RemoteException;
}
