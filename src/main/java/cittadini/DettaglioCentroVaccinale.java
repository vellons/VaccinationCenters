package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.DashboardCentroVaccinale;
import models.TipologiaCentroVaccinale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * La classe DettaglioCentroVaccinale permette di visualizzare nel dettaglio
 * un centro vaccinale selezionato dall'utente
 * 
 * @author manuelmacaj
 */
public class DettaglioCentroVaccinale {

    /**
     * <code>cv</code> rappresenta il centro vaccinale selezionato
     * <p>
     * &egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo alla classe DettaglioCentroVaccinale
     * </p>
     */
    private final CentroVaccinale cv;
    /**
     * <code>panelDettaglioCV</code> rappresenta il pannello principale
     * <p>
     * &egrave; dichiarato <strong>protected</strong> cos&igrave; da poter essere visibile solo alle classi appartenenti al package cittadini
     * </p>
     */
    protected JPanel panelDettaglioCV;
    /**
     * <code>panelLogo</code> rappresenta un pannello per inserire il logo
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JPanel panelLogo;
    /**
     * <code>lbCentroVaccinale</code> rappresenta la label dedicata nome del centro vaccianle
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbCentroVaccinale;
    /**
     * <code>lbIndirizzo</code> rappresenta la label dedicata all'indirizzo del centro vaccinale selezionato
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbIndirizzo;
    /**
     * <code>lbTotaleVaccinati</code> rapprenta la label dedicata alla visualizzazione del totale dei vaccinati nel centro vaccinale selezionato
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbTotaleVaccinati;
    /**
     * <code>panelEventiAvversi</code> rappresenta un pannello in cui è raggruppato una label che mostra alcune informazioni sugli eventi avversi del centro vaccinale selezionato
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JPanel panelEventiAvversi;
    /**
     * <code>lbEventiAvversi</code> rappresenta la label in cui vengono mostrati le tipologie di eventi avversi con il conteggio dei casi avversi del centro vaccinale selezionato
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbEventiAvversi;
    /**
     * <code>lbTipologia</code> rappresenta la label dedicata alla visualizzazione della tipologia (ospedaliero, aziendale od hub) del centro vaccinale selezionato
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lblTipologia;
    /**
     * <code>panelPieChart</code> rappresenta il panel su cui verrà costruito l'areogramma
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JPanel panelPieChart;
    /**
     * <code>lbTitoloEventi</code> rappresenta una label che mostra questo testo: "Eventi avversi segnalati:"
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbTitoloEventi;
    /**
     * <code>infoCV</code> rappresenta un oggetto di tipo DashboardCentroVaccinale che mi permette di accedere e ricavare informazioni specifiche inerente al centro vaccinale selezionato
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private DashboardCentroVaccinale infoCV;

    /**
     * <code>db</code> Singleton class con il server
     * <p>
     * &Egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private final DatabaseCVInterface db;

    /**
     * <code>db</code> Usato per formattare 2 cifre dopo la virgola
     * <p>
     * &Egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * </p>
     */
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * costruttore della classe
     *
     * @param cv &egrave; il centro vaccinale selezionato dall'utente
     * @throws RemoteException
     */
    public DettaglioCentroVaccinale(CentroVaccinale cv) throws RemoteException {
        db = ServerConnectionSingleton.getDatabaseInstance();
        this.cv = cv;
        setCVLabels(this.cv);
        setTotalVax();
        setEventiAvversiLabel();
    }

    private void setEventiAvversiLabel() throws RemoteException {
        StringBuilder text = new StringBuilder("<html>");
        int count = 0;
        for (Map.Entry<String, Integer> entry : db.getEventiAvversiCV(cv.getId()).entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (value > 0) count++;
            text.append("- ")
                    .append(key.substring(0, 1).toUpperCase())
                    .append(key.substring(1))
                    .append(": ")
                    .append(value)
                    .append("<br/>");
        }
        if (count > 0) {
            text.append("<br/>Severità media eventi: ").append(df.format(db.getMediaEventiAvversiCV(cv.getId())));
            lbTitoloEventi.setText("Eventi avversi segnalati (" + count +"):");
        }
        lbEventiAvversi.setText(text + "</html>");
    }

    private JPanel createPieChartPanel() { // funzione che mi permette la creazione di un Chart sul pannello panelPieChart
        JFreeChart chart = createChart(createDataset());
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Vaccinati senza eventi avversi", new Color(231, 28, 119));
        plot.setSectionPaint("Vaccinati con almeno un evento avverso", new Color(255, 202, 24));
        return new ChartPanel(chart);
    }

    private PieDataset createDataset() { // Specifica dei parametri che verranno visualizzati sul PieChart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Vaccinati senza eventi avversi", (infoCV.getVaccinati() - infoCV.getVaccinati_con_eventi_avversi())); // totale vaccinati
        dataset.setValue("Vaccinati con almeno un evento avverso", infoCV.getVaccinati_con_eventi_avversi()); // totale vaccinati con eventi avversi
        return dataset; // restituisco l'oggetto di tipo DefaultPieDataset
    }

    private JFreeChart createChart(PieDataset dataset) { // funzione che permette la creazioe di un PieChart (diagramma a torta)
        return ChartFactory.createPieChart("Vaccinati", dataset, false, true, false);
    }

    private void setCVLabels(CentroVaccinale cv) throws RemoteException {
        lbCentroVaccinale.setText(cv.getNome().substring(0, Math.min(cv.getNome().length(), 25)));
        lbIndirizzo.setText(cv.getIndirizzoComposto());
        lblTipologia.setText("Tipologia: " + findTipologia());
    }

    private String findTipologia() throws RemoteException {
        String tipologia = "";
        for (TipologiaCentroVaccinale obj : db.getTipologiaCentroVaccinale()) {
            if (cv.getTipologia_id() == obj.getId()) {
                tipologia = obj.getNome();
            }
        }
        return tipologia;
    }

    private void setTotalVax() {
        try {
            for (DashboardCentroVaccinale list : db.getDashboardCVInfo("WHERE id = " + cv.getId())) {
                if (list.getId() == cv.getId()) {
                    infoCV = list;
                    break;
                }
            }
            lbTotaleVaccinati.setText("Vaccinazioni totali: " + infoCV.getVaccinati());

            if (infoCV.getVaccinati() > 0)
                panelPieChart.add(createPieChartPanel());
            else {
                JLabel labelAvviso = new JLabel();
                labelAvviso.setText("<html>Nessuno si è ancora vaccinato in questo centro<br/>" +
                        "</html>");
                panelPieChart.add(labelAvviso);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            ServerConnectionSingleton.resetConnection();
        }
    }

    private void createUIComponents() throws Exception {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelPieChart = new JPanel();
    }
}