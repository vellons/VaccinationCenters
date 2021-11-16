package cittadini;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
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
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;

/**
 * La classe DettaglioCentroVaccinale permette di visualizzare nel dettaglio
 * un centro vaccinale selezionato dall'utente.
 *
 * @author manuelmacaj
 */
public class DettaglioCentroVaccinale {

    /**
     * <code>cv</code> rappresenta il centro vaccinale selezionato.
     * <p>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     * </p>
     */
    private final CentroVaccinale cv;
    /**
     * <code>panelDettaglioCV</code> rappresenta il pannello principale.
     * <p>
     * &egrave; dichiarato <strong>protected</strong> cos&igrave; da poter essere visibile solo alle classi appartenenti al package cittadini
     * </p>
     */
    protected JPanel panelDettaglioCV;
    /**
     * <code>panelLogo</code> rappresenta un pannello per inserire il logo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JPanel panelLogo;
    /**
     * <code>lbCentroVaccinale</code> rappresenta la label dedicata nome del centro vaccinale.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbCentroVaccinale;
    /**
     * <code>lbIndirizzo</code> rappresenta la label dedicata all'indirizzo del centro vaccinale selezionato.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbIndirizzo;
    /**
     * <code>lbTotaleVaccinati</code> rapprenta la label dedicata alla visualizzazione del totale dei vaccinati nel centro vaccinale selezionato.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbTotaleVaccinati;
    /**
     * <code>panelEventiAvversi</code> rappresenta un pannello in cui è raggruppato una label che mostra alcune informazioni sugli eventi avversi del centro vaccinale selezionato.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JPanel panelEventiAvversi;
    /**
     * <code>lbEventiAvversi</code> rappresenta la label in cui vengono mostrati le tipologie di eventi avversi con il conteggio dei casi avversi del centro vaccinale selezionato.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbEventiAvversi;
    /**
     * <code>lbTipologia</code> rappresenta la label dedicata alla visualizzazione della tipologia (ospedaliero, aziendale od hub) del centro vaccinale selezionato.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lblTipologia;
    /**
     * <code>panelPieChart</code> rappresenta il panel su cui verrà costruito l'areogramma.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JPanel panelPieChart;
    /**
     * <code>lbTitoloEventi</code> rappresenta una label che mostra questo testo: "Eventi avversi segnalati:".
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbTitoloEventi;
    /**
     * <code>infoCV</code> rappresenta un oggetto di tipo DashboardCentroVaccinale che mi permette di accedere e ricavare informazioni in pi&ugrave; del centro vaccinale selezionato.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private DashboardCentroVaccinale infoCV;

    /**
     * <code>db</code> Singleton class con il server.
     * <p>
     * &Egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private final DatabaseCVInterface db;

    /**
     * <code>db</code> Usato per formattare 2 cifre dopo la virgola.
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
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */
    public DettaglioCentroVaccinale(CentroVaccinale cv) throws IOException {
        $$$setupUI$$$();
        db = ServerConnectionSingleton.getDatabaseInstance();
        this.cv = cv;
        setCVLabels(this.cv);
        setTotalVax();
        setEventiAvversiLabel();
    }

    /**
     * <code>setEventiAvversiLabel</code> &egrave; un metodo per visualizzare il conteggio di casi segnalati
     * per ogni evento avverso.<br>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */
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
            lbTitoloEventi.setText("Eventi avversi segnalati (" + count + "):");
        }
        lbEventiAvversi.setText(text + "</html>");
    }

    /**
     * <code>createPieChartPanel</code> &egrave; un metodo per la creazione di un PieChart.<br>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @return restituisce un pannello per la visualizzazione di un grafico a torta
     */
    private JPanel createPieChartPanel() { // funzione che mi permette la creazione di un Chart sul pannello panelPieChart
        JFreeChart chart = createChart(createDataset());
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("Vaccinati senza eventi avversi", new Color(231, 28, 119));
        plot.setSectionPaint("Vaccinati con almeno un evento avverso", new Color(255, 202, 24));
        return new ChartPanel(chart);
    }

    /**
     * <code>createDataset</code> &egrave; un metodo creazione dataset.<br>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @return restituisce il set di dati da utilizzare nel grafico a torta
     */
    private PieDataset createDataset() { // Specifica dei parametri che verranno visualizzati sul PieChart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Vaccinati senza eventi avversi", (infoCV.getVaccinati() - infoCV.getVaccinati_con_eventi_avversi())); // totale vaccinati
        dataset.setValue("Vaccinati con almeno un evento avverso", infoCV.getVaccinati_con_eventi_avversi()); // totale vaccinati con eventi avversi
        return dataset; // restituisco l'oggetto di tipo DefaultPieDataset
    }

    /**
     * <code>createChart</code> &egrave; un metodo per la creazione di un PieChart.<br>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param dataset sono gli "spicchi" da utilizzare nel grafico a torta
     * @return restituisce
     */
    private JFreeChart createChart(PieDataset dataset) { // funzione che permette la creazioe di un PieChart (diagramma a torta)
        return ChartFactory.createPieChart("Vaccinati", dataset, false, true, false);
    }

    /**
     * <code>setCVLabels</code> &egrave; un metodo per il "settaggio" delle label d'informazione sul centro vaccinale.<br>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param cv &egrave; il centro vaccinale che l'utente ha selezionato
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */
    private void setCVLabels(CentroVaccinale cv) throws RemoteException {
        lbCentroVaccinale.setText(cv.getNome().substring(0, Math.min(cv.getNome().length(), 25)));
        lbIndirizzo.setText(cv.getIndirizzoComposto());
        lblTipologia.setText("Tipologia: " + findTipologia());
    }

    /**
     * <code>findTipologia</code> &egrave; un metodo per ricavare la tipologia del centro vaccinale selezionato.<br>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @return restituisce la tipologia di centro vaccinale
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */
    private String findTipologia() throws RemoteException {
        String tipologia = "";
        for (TipologiaCentroVaccinale obj : db.getTipologiaCentroVaccinale()) {
            if (cv.getTipologia_id() == obj.getId()) {
                tipologia = obj.getNome();
            }
        }
        return tipologia;
    }

    /**
     * <code>setTotalVax</code> &egrave; un metodo che aggiunge alla label lbTotaleVaccinati, il totale dei vaccinati
     * nel centro vaccinale corrente.
     * Include anche l'invocazione per la creazione del grafico a torta.<br>
     * &Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     */
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

    /**
     * <code>createUIComponents</code> &egrave; una procedura per impostare la grafica
     * quando viene caricato il frame
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     *
     * @throws IOException &egrave; utilizzata quando si verificano errori nelle fasi di input e di output
     */
    private void createUIComponents() throws IOException {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelPieChart = new JPanel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() throws IOException {
        createUIComponents();
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelDettaglioCV = new JPanel();
        panelDettaglioCV.setLayout(new GridLayoutManager(3, 2, new Insets(15, 15, 15, 15), -1, -1));
        panelDettaglioCV.setBackground(new Color(-723724));
        panelDettaglioCV.setPreferredSize(new Dimension(1200, 700));
        panel1.add(panelDettaglioCV, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-723724));
        panelDettaglioCV.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-723724));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbIndirizzo = new JLabel();
        Font lbIndirizzoFont = this.$$$getFont$$$("Arial", Font.BOLD, 15, lbIndirizzo.getFont());
        if (lbIndirizzoFont != null) lbIndirizzo.setFont(lbIndirizzoFont);
        lbIndirizzo.setForeground(new Color(-16777216));
        lbIndirizzo.setText("Indirizzo");
        panel3.add(lbIndirizzo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbTotaleVaccinati = new JLabel();
        Font lbTotaleVaccinatiFont = this.$$$getFont$$$("Arial", Font.BOLD, 24, lbTotaleVaccinati.getFont());
        if (lbTotaleVaccinatiFont != null) lbTotaleVaccinati.setFont(lbTotaleVaccinatiFont);
        lbTotaleVaccinati.setForeground(new Color(-1631113));
        lbTotaleVaccinati.setText("Vaccinazioni totali: 0");
        panel3.add(lbTotaleVaccinati, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelEventiAvversi = new JPanel();
        panelEventiAvversi.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelEventiAvversi.setBackground(new Color(-723724));
        panel3.add(panelEventiAvversi, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbEventiAvversi = new JLabel();
        Font lbEventiAvversiFont = this.$$$getFont$$$("Arial", Font.BOLD, 15, lbEventiAvversi.getFont());
        if (lbEventiAvversiFont != null) lbEventiAvversi.setFont(lbEventiAvversiFont);
        lbEventiAvversi.setForeground(new Color(-16777216));
        lbEventiAvversi.setText("resoconto eventi");
        panelEventiAvversi.add(lbEventiAvversi, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbTitoloEventi = new JLabel();
        Font lbTitoloEventiFont = this.$$$getFont$$$("Arial", Font.BOLD, 20, lbTitoloEventi.getFont());
        if (lbTitoloEventiFont != null) lbTitoloEventi.setFont(lbTitoloEventiFont);
        lbTitoloEventi.setForeground(new Color(-13800));
        lbTitoloEventi.setText("Eventi avversi segnalati:");
        panelEventiAvversi.add(lbTitoloEventi, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelEventiAvversi.add(spacer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelEventiAvversi.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lblTipologia = new JLabel();
        Font lblTipologiaFont = this.$$$getFont$$$("Arial", Font.BOLD, 15, lblTipologia.getFont());
        if (lblTipologiaFont != null) lblTipologia.setFont(lblTipologiaFont);
        lblTipologia.setForeground(new Color(-16777216));
        lblTipologia.setText("Tipologia");
        panel3.add(lblTipologia, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelPieChart.setBackground(new Color(-723724));
        panelPieChart.setForeground(new Color(-7303024));
        panelDettaglioCV.add(panelPieChart, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-723724));
        panelDettaglioCV.add(panel4, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 175), new Dimension(-1, 175), 0, false));
        panelLogo.setBackground(new Color(-723724));
        panel4.add(panelLogo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbCentroVaccinale = new JLabel();
        Font lbCentroVaccinaleFont = this.$$$getFont$$$("Arial", Font.BOLD, 72, lbCentroVaccinale.getFont());
        if (lbCentroVaccinaleFont != null) lbCentroVaccinale.setFont(lbCentroVaccinaleFont);
        lbCentroVaccinale.setForeground(new Color(-3727837));
        lbCentroVaccinale.setText("Centro Vaccinale");
        panel4.add(lbCentroVaccinale, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panelDettaglioCV.add(spacer4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

}