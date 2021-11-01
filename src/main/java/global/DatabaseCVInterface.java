package global;

import models.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface DatabaseCVInterface extends Remote {
    void logMessage(String message) throws RemoteException;

    List<EventoAvverso> getEventiAvversi() throws RemoteException;

    List<TipologiaEvento> getTipologieEventi() throws RemoteException;

    List<Vaccinato> getVaccinati() throws RemoteException;

    List<CentroVaccinale> getCentriVaccinali(String where) throws RemoteException;

    List<TipologiaVaccino> getTipologiaVaccino() throws RemoteException;

    List<TipologiaCentroVaccinale> getTipologiaCentroVaccinale() throws RemoteException;

    Vaccinato getVaccinatoByIDUnique(String idUnivoco) throws RemoteException;

    Vaccinato getVaccinatoByEmailAndPasswordSha(String email, String password) throws RemoteException;

    List<EventoAvverso> getEventiAvversiCV(int idCV) throws RemoteException;

    List<DashboardCentroVaccinale> getDashboardCVInfo(String where) throws RemoteException;

    boolean inserisciCentroVaccinale(CentroVaccinale cv) throws RemoteException;

    boolean inserisciCittadinoVaccinato(Vaccinato vax) throws RemoteException;

    int rowCounterInTable(String table) throws RemoteException;

    Map<String, Integer> getCountEventiCV(int idCV) throws RemoteException;

    int updateRegistraVaccinato(String email, String password, String idUnivoco) throws RemoteException;

    List<EventoAvverso> getEventiAvversiCittadino(int vaccinatoID) throws RemoteException;

    void inserisciNuovoEventoAvversoCittadino(EventoAvverso ea) throws RemoteException;

    boolean updateEventoAvverso(EventoAvverso ea) throws RemoteException;

}
