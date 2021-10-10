package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.DashboardCentroVaccinale;
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
import java.util.*;
import java.util.stream.Collectors;

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
    private List<Vaccinato> vax = new ArrayList<>();
    private DashboardCentroVaccinale infoCV;
    private final DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
    private List<TipologiaCentroVaccinale> tipologie = new ArrayList<>();
    private Map<String, Integer> eventiAvversiCV = new HashMap<>();

    public DettaglioCentroVaccinale(CentroVaccinale cv) throws RemoteException {
        this.cv = cv;
        setCVLabels(this.cv);
        setTotalVax();
        setEventiAvversiLabel();
        panelPieChart.add(createPieChartPanel());
    }

    private void setEventiAvversiLabel() throws RemoteException {
        eventiAvversiCV = db.getCountEventiCV(cv.getId());

        StringBuilder text = new StringBuilder("<html>Eventi avversi segnalati:<br/>");
        for (Map.Entry<String, Integer> entry : eventiAvversiCV.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            text.append("- ").append(key).append(": ").append(value).append("<br/>");
        }
        lbEventiAvversi.setText(text + "</html>");
    }

    private JPanel createPieChartPanel() { // funzione che mi permette la creazione di un Chart sul pannello panelPieChart
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }

    private PieDataset createDataset() { // Specifica dei parametri che verranno visualizzati sul PieChart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Totale vaccinati", infoCV.getVaccinati()); // totale vaccinati
        dataset.setValue("Totale vaccin", infoCV.getVaccinati_con_eventi_avversi()); // totale vaccinati con eventi avversi
        return dataset; // restituisco l'oggetto di tipo DefaultPieDataset
    }

    private JFreeChart createChart(PieDataset dataset) { // funzione che permette la creazioe di un PieChart (diagramma a torta)
        return ChartFactory.createPieChart("Vaccinati", dataset, true, true, false);
    }

    private void setCVLabels(CentroVaccinale cv) {
        lbCentroVaccinale.setText(cv.getNome());
        lbIndirizzo.setText(cv.getIndirizzoComposto());
        lblTipologia.setText("Tipologia: " + findTipologia());
    }

    private String findTipologia() {
        try {
            DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
            tipologie = db.getTipologiaCentroVaccinale();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String tipologia = "";
        for (TipologiaCentroVaccinale obj : tipologie) {
            if (cv.getTipologia_id() == obj.getId()) {
                tipologia = obj.getNome();
            }
        }
        return tipologia;
    }

    private void setTotalVax() {
        try {
            vax = db.getVaccinatiCV(cv.getId());
            List<DashboardCentroVaccinale> info = new ArrayList<>();
            info = db.getDashboardCVInfo();

            for(DashboardCentroVaccinale list: info){
                if (list.getId() == cv.getId()){
                    infoCV = list;
                    break;
                }
            }
            lbTotaleVaccinati.setText("Vaccinazioni totali: " + infoCV.getVaccinati());
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

