package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.DashboardCentroVaccinale;
import models.TipologiaCentroVaccinale;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe CentroVaccinalePerLista permette il caricamento dei singoli centri vaccinali
 * da caricare nell'elenco dei centri vaccinali.
 *
 * @author Vellons
 * @see ListaCentriVaccinaliPanel
 */

public class CentroVaccinalePerLista extends JPanel {

    /**
     * <code>dettaglioFrame</code> &egrave; una cornice Swing attivata nel momento nel
     * quale si vuole visualizzare le informazioni dettagliate di un singolo
     * centro vaccinale
     *
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da non doverla istanziare creando un oggetto
     */
    public static JFrame dettaglioFrame = new JFrame("Centri Vaccinali Cittadini - Dettaglio centro vaccinale");

    /**
     * <code>panelCentroVaccinalePerLista</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie uno dei singoli centri vaccinali
     * caricati sulla dashboard
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     */
    public JPanel panelCentroVaccinalePerLista;

    /**
     * <code>lblNome</code> &egrave; un'etichetta Swing dedicata al campo nome
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblNome;

    /**
     * <code>lblIndirizzo</code> &egrave; un'etichetta Swing dedicata al campo indirizzo
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblIndirizzo;

    /**
     * <code>lblVaccinazioni</code> &egrave; un'etichetta Swing dedicata al campo vaccinazioni
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblVaccinazioni;

    /**
     * <code>btnDettaglio</code> &egrave; un bottone Swing che attiva visualizzazione
     * delle informazioni dettagliate di un centro vaccinale a scelta.
     * <p>
     * Cliccandolo si aprirà un box per visualizzare i dettagli del centro vaccinale.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnDettaglio;

    /**
     * <code>lblTipologia</code> &egrave; un'etichetta Swing dedicata al campo tipologia
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblTipologia;

    /**
     * <code>lblEventiAvversi</code> &egrave; un'etichetta Swing dedicata al campo degli eventi avversi
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblEventiAvversi;

    /**
     * <code>dashboardData</code> &egrave; un ArrayList che contiene il numero di vaccinati per ogni centro vaccinale,
     * il numero di eventi avversi totale e il numero di persone che hanno avuto eventi avversi.
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    private static List<DashboardCentroVaccinale> dashboardData = new ArrayList<>();

    /**
     * Costruttore della classe
     *
     * @param cv insieme di dati relativi al centro vaccinale da visualizzare
     */
    public CentroVaccinalePerLista(CentroVaccinale cv) {
        if (dashboardData.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                dashboardData = db.getDashboardCVInfo("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        // Cerco la tipologia di centro vaccinale che combacia
        String tipologia = "";
        for (TipologiaCentroVaccinale obj : DashboardCentriVaccinaliElenco.tipologie) {
            if (cv.getTipologia_id() == obj.getId()) {
                tipologia = obj.getNome();
            }
        }
        lblTipologia.setText("Tipologia: " + tipologia);

        // Cerco le informazioni in dashboardData riferite al numero di vaccini
        String vaccinazioniInfoData = "";
        for (DashboardCentroVaccinale obj : dashboardData) {
            if (cv.getId() == obj.getId()) {
                vaccinazioniInfoData = "Vaccinazioni: " + obj.getVaccinati();
            }
        }
        lblVaccinazioni.setText(vaccinazioniInfoData);

        // Cerco le informazioni in dashboardData riferite al numero di eventi avversi
        String eventiAvversiInfoData = "";
        for (DashboardCentroVaccinale obj : dashboardData) {
            if (cv.getId() == obj.getId()) {
                eventiAvversiInfoData = "Avversità segnalate: " + obj.getSomma_eventi_avversi();
                if (obj.getVaccinati_con_eventi_avversi() == 1) {
                    eventiAvversiInfoData += " (" + obj.getVaccinati_con_eventi_avversi() + " persona coinvolta)";
                } else if (obj.getVaccinati_con_eventi_avversi() > 1) {
                    eventiAvversiInfoData += " (" + obj.getVaccinati_con_eventi_avversi() + " persone coinvolte)";
                }
            }
        }
        lblEventiAvversi.setText(eventiAvversiInfoData);

        // Completo le informazioni del centro vaccinale
        lblNome.setText(cv.getNome().substring(0, Math.min(cv.getNome().length(), 30)));
        lblIndirizzo.setText(cv.getIndirizzoComposto().substring(0, Math.min(cv.getIndirizzoComposto().length(), 60)));

        btnDettaglio.addActionListener(e -> {
            try {
                dettaglioFrame.setContentPane(new DettaglioCentroVaccinale(cv).panelDettaglioCV);
                Cittadini.initUI(dettaglioFrame);
                dettaglioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
                dettaglioFrame.pack();
                dettaglioFrame.setLocationRelativeTo(null);
                dettaglioFrame.setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
