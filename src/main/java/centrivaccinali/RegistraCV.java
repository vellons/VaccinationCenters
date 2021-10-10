package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaCentroVaccinale;
import serverCV.DatabaseCV;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegistraCV {
    public JPanel panelRegistraCV;
    private CentroVaccinale cv;
    private JPanel panelLogo;
    private JPanel panelLogo2;
    private JButton btnRegistraCV;
    private JTextField tfNomeCentro;
    private JTextField tfIndirizzo;
    private JTextField tfCivico;
    private JTextField tfComune;
    private JTextField tfSiglaProvincia;
    private JTextField tfCap;
    private JComboBox<String> cboxTipoCentro;
    private JLabel lblNomeCentro;
    private JLabel lblQualificatore;
    private JLabel lblIndirizzo;
    private JLabel lblCivico;
    private JLabel lblComune;
    private JLabel lblSiglaProvincia;
    private JLabel lblCap;
    private JLabel lblTipoCentro;
    private JLabel lblErrors;
    private JComboBox cboxQualificatore;
    private int tipo=0;
    private static List<TipologiaCentroVaccinale> tipologie = new ArrayList<>();
    String[] qualificatore = new String[]{"Via", "Viale", "Piazza", "Corso", "Vicolo"};

    public RegistraCV() throws RemoteException {
        btnRegistraCV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTipo(getTipoCentro());
                if (checkAllInputs()) {
                    try {
                        if (JOptionPane.showOptionDialog(null, "Confermi di voler registrare il nuovo centro vaccinale?",
                                "Conferma registrazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, null, null) == JOptionPane.YES_OPTION) {
                            DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                            cv = new CentroVaccinale(getTfNomeCentro(),tipo,1,getQualificatore(),getTfIndirizzo(),getTfCivico(),getTfComune(),getTfSiglaProvincia(),getTfCap());
                            db.inserisciCentroVaccinale(cv);
                            CentriVaccinali.closePreviousWindow(CentriVaccinali.registraCVFrame);
                            JOptionPane.showMessageDialog(null, "La registrazione e'" +
                                    "andata a buon fine!", "Registrazione effettuate", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "C'e' stato un problema. Prova a riavviare l'app",
                                "Attenzione", JOptionPane.PLAIN_MESSAGE);
                        exception.printStackTrace();
                    }
                } else {
                    lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                    lblErrors.setText("Assicurarsi che tutti campi siano stati compilati correttamente");
                    lblErrors.setForeground(Color.RED);
                    lblErrors.setVisible(true);
                }
            }
        });
    }

    //METODI GETTERS

    public String getTfNomeCentro() {return tfNomeCentro.getText();}

    public String getQualificatore() { return Objects.requireNonNull(cboxQualificatore.getSelectedItem()).toString();}

    public String getTfIndirizzo() {return tfIndirizzo.getText();}

    public String getTfCivico() {return tfCivico.getText();}

    public String getTfComune() {return tfComune.getText();}

    public String getTfSiglaProvincia() {return tfSiglaProvincia.getText();}

    public String getTfCap() {return tfCap.getText();}

    public String getTipoCentro() { return Objects.requireNonNull(cboxTipoCentro.getSelectedItem()).toString();}

    public void setTipo(String type){

        for (TipologiaCentroVaccinale obj : tipologie) {
            if(Objects.equals(obj.getNome(), type)){
                tipo=obj.getId();
            }
        }

    }

    private boolean checkAllInputs(){
        boolean allFieldsValid;  // Tramite una variabile booleana, verifico se tutti i campi siano completi

        allFieldsValid = checkInput(getTfNomeCentro(), tfNomeCentro);
        allFieldsValid &= checkInput(getTfIndirizzo(), tfIndirizzo);
        allFieldsValid &= isAlphabetic(getTfIndirizzo());
        allFieldsValid &= checkInput(getTfCivico(), tfCivico);
        allFieldsValid &= isNumeric(getTfCivico());
        allFieldsValid &= checkInput(getTfComune(), tfComune);
        allFieldsValid &= isAlphabetic(getTfComune());
        allFieldsValid &= checkInput(getTfSiglaProvincia(), tfSiglaProvincia);
        allFieldsValid &= isAlphabetic(getTfSiglaProvincia());
        allFieldsValid &= getTfSiglaProvincia().length()==2;
        allFieldsValid &= checkInput(getTfCap(), tfCap);
        allFieldsValid &= getTfCap().length()==5;
        allFieldsValid &= isNumeric(getTfCap());

        return allFieldsValid;
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static boolean isAlphabetic(String input) {
        for (int i = 0; i != input.length(); ++i) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private boolean checkInput(String input, JTextField textField){
        boolean res;
        String tmp = "";
        tmp += input;
        // Se il campo e vuoto, visualizzo una scritta
        res = !tmp.equals("");
        return res;
    }

    private void createUIComponents() throws IOException {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelLogo2 = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelLogo2.add(picLabel2);
        cboxQualificatore = new JComboBox<String>(qualificatore);
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaCentroVaccinale();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        for (TipologiaCentroVaccinale obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipoCentro = new JComboBox(tipologieCombo.toArray());
    }
}
