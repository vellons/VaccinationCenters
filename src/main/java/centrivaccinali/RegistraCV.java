package centrivaccinali;

import models.CentroVaccinale;
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
import java.util.Objects;

public class RegistraCV {
    public JPanel panelRegistraCV;
    private CentroVaccinale cv;
    private DatabaseCV dataCV;
    private JPanel panelLogo;
    private JPanel panelLogo2;
    private JButton btnRegistraCV;
    private JTextField tfNomeCentro;
    private JTextField tfQualificatore;
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
    private int tipo=0;
    String[] tipologia = new String[]{"Ospedaliero", "Aziendale", "Hub"};

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
                            cv = new CentroVaccinale(getTfNomeCentro(),tipo,1,getTfQualificatore(),getTfIndirizzo(),getTfCivico(),getTfComune(),getTfSiglaProvincia(),getTfCap());
                            dataCV.inserisciCentroVaccinale(cv);
                            CentriVaccinali.closePreviousWindow(CentriVaccinali.registraCVFrame);
                            JOptionPane.showMessageDialog(null, "La registrazione e'" +
                                    "andata a buon fine!", "Registrazione effettuate", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "C'e'; stato un problema. Prova a riavviare l'app",
                                "Attenzione", JOptionPane.PLAIN_MESSAGE);
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

    public String getTfQualificatore() { return tfQualificatore.getText();}

    public String getTfIndirizzo() {return tfIndirizzo.getText();}

    public String getTfCivico() {return tfCivico.getText();}

    public String getTfComune() {return tfComune.getText();}

    public String getTfSiglaProvincia() {return tfSiglaProvincia.getText();}

    public String getTfCap() {return tfCap.getText();}

    public String getTipoCentro() { return Objects.requireNonNull(cboxTipoCentro.getSelectedItem()).toString();}

    public void setTipo(String type){

        if(type.equals("Ospedaliero")){
            tipo=1;
        }
        if(type.equals("Aziendale")){
            tipo=2;
        }
        if(type.equals("Hub")){
            tipo=3;
        }

    }

    private boolean checkAllInputs(){
        boolean allFieldsValid;  // Tramite una variabile booleana, verifico se tutti i campi siano completi

        allFieldsValid = checkInput(getTfNomeCentro(), tfNomeCentro);
        allFieldsValid &= checkInput(getTfQualificatore(), tfQualificatore);
        allFieldsValid &= checkInput(getTfIndirizzo(), tfIndirizzo);
        allFieldsValid &= checkInput(getTfCivico(), tfCivico);
        allFieldsValid &= isNumeric(getTfCivico());
        allFieldsValid &= checkInput(getTfComune(), tfComune);
        allFieldsValid &= checkInput(getTfSiglaProvincia(), tfSiglaProvincia);
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
        cboxTipoCentro = new JComboBox<String>(tipologia);
    }
}
