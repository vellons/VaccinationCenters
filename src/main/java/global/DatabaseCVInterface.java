package global;

import models.CentroVaccinale;
import models.TipologiaCentroVaccinale;
import models.TipologiaVaccino;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DatabaseCVInterface extends Remote {
    void logMessage(String message) throws RemoteException;

    List<CentroVaccinale> getCentriVaccinali(String where) throws RemoteException;

    List<TipologiaVaccino> getTipologiaVaccino() throws RemoteException;

    List<TipologiaCentroVaccinale> getTipologiaCentroVaccinale() throws RemoteException;
    boolean inserisciCentroVaccinale(CentroVaccinale cv)throws RemoteException;
}
