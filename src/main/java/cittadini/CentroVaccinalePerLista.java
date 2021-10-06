package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaCentroVaccinale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public static JFrame dettaglioFrame = new JFrame("Cittadini - Dettaglio centro vaccinale");

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
     * <code>tipologie</code> &egrave; un ArrayList che contiene le tipologie di centro vaccinale
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    private static List<TipologiaCentroVaccinale> tipologie = new ArrayList<>(); // TODO: change to TipologiaCentroVaccinale

    /**
     * Costruttore della classe
     *
     * @param cv insieme di dati relativi al centro vaccinale da visualizzare
     */
    public CentroVaccinalePerLista(CentroVaccinale cv) {
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaCentroVaccinale();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        lblNome.setText(cv.getNome().substring(0, Math.min(cv.getNome().length(), 30)));
        lblIndirizzo.setText(cv.getIndirizzoComposto().substring(0, Math.min(cv.getIndirizzoComposto().length(), 60)));
        lblVaccinazioni.setText("2 vaccinazioni con eventi avversi su 77 - TODO");  // TODO @vellons
        // Cerco la tipologia di centro vaccinale che combacia
        String tipologia = "";
        for (TipologiaCentroVaccinale obj : tipologie) { // TODO: change to TipologiaCentroVaccinale
            if (cv.getTipologia_id() == obj.getId()) {
                tipologia = obj.getNome();
            }
        }
        if (!Objects.equals(tipologia, "")) {
            lblTipologia.setText("Tipologia: " + tipologia);
        }

        btnDettaglio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                     dettaglioFrame.setContentPane(new DettaglioCentroVaccinale(cv).panelDettaglioCV);
                     Cittadini.initUI(dettaglioFrame);
                     dettaglioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
                     dettaglioFrame.pack();
                     dettaglioFrame.setLocationRelativeTo(null);
                     dettaglioFrame.setVisible(true);
                    System.out.println("TODO: click");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }
}
