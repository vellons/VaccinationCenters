package global;

import models.CentroVaccinale;
import models.TipologiaVaccino;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DatabaseCVInterface extends Remote {
    void logMessage(String message) throws RemoteException;

    List<CentroVaccinale> getCentriVaccinali(String where) throws RemoteException;

    List<TipologiaVaccino> getTipologiaVaccino() throws RemoteException;
}
