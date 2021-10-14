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
import java.util.Map;

public class DettaglioCentroVaccinale {

    private final CentroVaccinale cv;
    public JPanel panelDettaglioCV;
    private JPanel panelLogo;
    private JLabel lbCentroVaccinale;
    private JLabel lbIndirizzo;
    private JLabel lbTotaleVaccinati;
    private JPanel panelEventiAvversi;
    private JLabel lbEventiAvversi;
    private JLabel lblTipologia;
    private JPanel panelPieChart;
    private JLabel lbTitoloEventi;
    private DashboardCentroVaccinale infoCV;
    private final DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();

    public DettaglioCentroVaccinale(CentroVaccinale cv) throws RemoteException {
        this.cv = cv;
        setCVLabels(this.cv);
        setTotalVax();
        setEventiAvversiLabel();
    }

    private void setEventiAvversiLabel() throws RemoteException {
        StringBuilder text = new StringBuilder("<html>");
        for (Map.Entry<String, Integer> entry : db.getCountEventiCV(cv.getId()).entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            text.append("- ").append(key).append(": ").append(value).append("<br/>");
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
        lbCentroVaccinale.setText(cv.getNome());
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