package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.Vaccinato;

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
    private JPanel JPanelLeft;
    private JLabel lbCentroVaccinale;
    private JLabel lbIndirizzo;
    private JLabel lbValutazioni;
    private JPanel panelNumValutazioni;
    private JLabel lb5Stelle;
    private JLabel lb4Stelle;
    private JLabel lb3Stelle;
    private JLabel lb2Stelle;
    private JLabel lb1Stella;
    private JLabel lbNumeroRecensioni;
    private JLabel lblTipologia;

    public DettaglioCentroVaccinale(CentroVaccinale cv) {
        this.cv = cv;
        setLabels(this.cv);
        setTotalVax();
    }

    private void setLabels(CentroVaccinale cv){
        lbCentroVaccinale.setText(cv.getNome());
        lbIndirizzo.setText(cv.getIndirizzoComposto());
    }

    private void setTotalVax() {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
        List<Vaccinato> vax = new ArrayList<>();
        try {
            vax = db.getVaccinatiCV(cv.getId());
            lbValutazioni.setText("Vaccinazioni totali: " + vax.size());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void createUIComponents() throws Exception {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);

        panelNumValutazioni = new JPanel();
        lb2Stelle = new JLabel();
        lb4Stelle = new JLabel();
        lb1Stella = new JLabel();
        lb5Stelle = new JLabel();
        lb3Stelle = new JLabel();

        lbValutazioni = new JLabel();
        lbNumeroRecensioni = new JLabel();

    }
}

