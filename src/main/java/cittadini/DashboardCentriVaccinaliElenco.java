package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.TipologiaCentroVaccinale;
import models.TipologiaVaccino;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe DashboardCentriVaccinaliElenco permette l'utilizzo di visualizzare l'elenco dei centri vaccinali
 * con la possibilit&agrave; di filtrarli.
 */
public class DashboardCentriVaccinaliElenco extends JFrame {
    /**
     * <code>panelDashboardCentriVaccinaliElenco</code> rappresenta un pannello.
     * <p>
     * &egrave; dichiarato <strong>public</strong> cos&igrave; da poter essere visibile anche alle altre classi
     */
    public JPanel panelDashboardCentriVaccinaliElenco;

    /**
     * <code>panelLogo</code> rappresenta un pannello.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelLogo;

    /**
     * <code>panelListaCentriVaccinali</code> rappresenta un pannello.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelListaCentriVaccinali;

    /**
     * <code>btnApplicaFiltri</code> rappresenta un bottone.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnApplicaFiltri;

    /**
     * <code>cboxTipologia</code> rappresenta un combo box.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JComboBox cboxTipologia;

    /**
     * <code>tfFiltroNomeCentroVaccinale</code> rappresenta un campo di testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfFiltroNomeCentroVaccinale;

    /**
     * <code>tfFiltroComune</code> rappresenta un campo di testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfFiltroComune;

    /**
     * <code>initialFiltroNome</code> rappresenta il filtro per il nome del centro vaccinale.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private String initialFiltroNome = "";

    /**
     * <code>initialFiltroComune</code> rappresenta il filtro per il comune del centro vaccinale.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private String initialFiltroComune = "";

    /**
     * <code>initialFiltroTipologia</code> rappresenta il filtro per la tipologia del centro vaccinale.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private String initialFiltroTipologia = "";

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
     * @param filtroNome      &egrave; il filtro che viene applicato al nome di un centro vaccinale, escludendo gli altri
     * @param filtroComune    &egrave; il filtro che viene applicato al comune di un centro vaccinale, escludendo gli altri
     * @param filtroTipologia &egrave; il filtro che viene applicato alla tipologia di un centro vaccinale, escludendo gli altri
     */
    public DashboardCentriVaccinaliElenco(String filtroNome, String filtroComune, String filtroTipologia) {
        // Prendo i valori dei filtri precedenti e popolo i filtri
        // Questo succede perch&egrave; faccio il reload di questo oggetto
        initialFiltroNome = filtroNome;
        initialFiltroComune = filtroComune;
        initialFiltroTipologia = filtroTipologia;

        btnApplicaFiltri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ricarico la dashboard dei centri vaccinali passando i filtri
                try {
                    Cittadini.reloadDashBoardCentriVaccinaliElencoConFiltri(
                            Cittadini.mainCittadini,
                            tfFiltroNomeCentroVaccinale.getText(),
                            tfFiltroComune.getText(),
                            Objects.requireNonNull(cboxTipologia.getSelectedItem()).toString()
                    );
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezione potrebbe
     *                   essere sollevata durante l'esecuzione del programma
     */
    private void createUIComponents() throws Exception {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);

        // Creo la lista dei centri vaccinali passando i filtri
        panelListaCentriVaccinali = new JPanel();
        panelListaCentriVaccinali.add(new ListaCentriVaccinaliPanel(initialFiltroNome, initialFiltroComune, initialFiltroTipologia));

        // Inizializzo e popolo i valori dei filtri
        tfFiltroNomeCentroVaccinale = new JTextField();
        tfFiltroComune = new JTextField();

        tfFiltroNomeCentroVaccinale.setText(initialFiltroNome);
        tfFiltroComune.setText(initialFiltroComune);

        // Prendo le tipologie di centro vaccinale
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaCentroVaccinale();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        tipologieCombo.add("TUTTI");
        for (TipologiaCentroVaccinale obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipologia = new JComboBox(tipologieCombo.toArray());
        cboxTipologia.setSelectedItem(initialFiltroTipologia);
    }
}
