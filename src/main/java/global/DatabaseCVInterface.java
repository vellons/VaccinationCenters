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

    List<Vaccinato> getVaccinatiCV(int idCV) throws RemoteException;

    List<EventoAvverso> getEventiAvversiCV(int idCV) throws RemoteException;

    List<DashboardCentroVaccinale> getDashboardCVInfo() throws RemoteException;

    boolean inserisciCentroVaccinale(CentroVaccinale cv) throws RemoteException;

    boolean inserisciCittadinoVaccinato(Vaccinato vax) throws RemoteException;

    int rowCounterInTable(String table) throws RemoteException;

    Map<Integer, Integer> vaccinatiPerOgniCentroVaccinale() throws RemoteException;
}
