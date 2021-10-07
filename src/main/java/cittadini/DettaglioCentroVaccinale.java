package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaCentroVaccinale;
import models.Vaccinato;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class DettaglioCentroVaccinale {

    private final CentroVaccinale cv;
    public JPanel panelDettaglioCV;
    private JPanel panelLogo;
    private JLabel lbCentroVaccinale;
    private JLabel lbIndirizzo;
    private JLabel lbTotaleVaccinati;
    private JLabel lb5Stelle;
    private JLabel lb4Stelle;
    private JLabel lb3Stelle;
    private JLabel lb2Stelle;
    private JLabel lb1Stella;
    private JPanel panelEventiAvversi;
    private JLabel lblTipologia;
    private JPanel panelPieChart;
    private List<Vaccinato> vax = new ArrayList<>();
    private final DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();

    public DettaglioCentroVaccinale(CentroVaccinale cv) {
        this.cv = cv;
        setLabels(this.cv);
        setTotalVax();
        panelPieChart.add(createPieChartPanel());
    }

    private JPanel createPieChartPanel() { // funzione che mi permette la creazione di un Chart sul pannello panelPieChart
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    private PieDataset createDataset() { // Specifica dei parametri che verranno visualizzati sul PieChart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Vaccinati", vax.size()); // totale vaccinati
        dataset.setValue("Vaccinati con eventi avversi", 0); // totale vaccinati con eventi avversi
        return dataset; // restituisco l'oggetto di tipo DefaultPieDataset
    }

    private JFreeChart createChart(PieDataset dataset) { // funzione che permette la creazioe di un PieChart (diagramma a torta)
        return ChartFactory.createPieChart("Vaccinati", dataset, true, true, false);
    }

    private void setLabels(CentroVaccinale cv) {
        lbCentroVaccinale.setText(cv.getNome());
        lbIndirizzo.setText(cv.getIndirizzoComposto());
        lblTipologia.setText("Tipologia: " + findTipologia());
    }

    private String findTipologia() {
        String tipologia = "";
        for (TipologiaCentroVaccinale obj : CentroVaccinalePerLista.tipologie) {
            if (cv.getTipologia_id() == obj.getId()) {
                tipologia = obj.getNome();
            }
        }
        return tipologia;
    }

    private void setTotalVax() {
        try {
            vax = db.getVaccinatiCV(cv.getId());
            lbTotaleVaccinati.setText("Vaccinazioni totali: " + vax.size());
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

        panelEventiAvversi = new JPanel();
        lb2Stelle = new JLabel();
        lb4Stelle = new JLabel();
        lb1Stella = new JLabel();
        lb5Stelle = new JLabel();
        lb3Stelle = new JLabel();
    }
}

