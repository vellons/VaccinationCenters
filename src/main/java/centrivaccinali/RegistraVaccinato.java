package centrivaccinali;

import models.Vaccinato;
import serverCV.DatabaseCV;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class RegistraVaccinato{
    public JPanel panelRegistraVaccinato;
    private Vaccinato vax;
    private DatabaseCV dataCV;
    private JPanel panelLogo;
    private JPanel panelLogo2;
    private JTextField tfNomeCentro;
    private JTextField tfNome;
    private JTextField tfCognome;
    private JTextField tfCodiceFiscale;
    private JTextField tfDataVaccino;
    private JTextField tfIDUnivoco;
    private JButton btnRegistraVaccinato;
    private JLabel lblErrors;
    private JLabel lblNomeCentro;
    private JLabel lblNome;
    private JLabel lblCognome;
    private JLabel lblCodiceFiscale;
    private JLabel lblDataVaccino;
    private JLabel lblTipoVaccino;
    private JLabel lblIDUnivoco;
    private Timestamp dataVaccino= null;
    private JComboBox<String> cboxTipoVaccino;
    private int tipo=0;
    String[] tipologia = new String[]{"Pfizer", "Moderna", "AstraZeneca", "J&J"};
    private final String CF_REGEX = "/^(?:[A-Z][AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$/i";

    public RegistraVaccinato() throws Exception {

        btnRegistraVaccinato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTipo(getTipoVaccino());
                dataVaccino=stringToTimestamp(getTfDataVaccino());
                if (checkAllInputs()) {
                    try {
                        if (JOptionPane.showOptionDialog(null, "Confermi di voler registrare il nuovo utente?",
                                "Conferma registrazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, null, null) == JOptionPane.YES_OPTION) {
                            vax = new Vaccinato("",0,tipo,getTfNome(),getTfCognome(),getTfCodiceFiscale(),dataVaccino,"","");
                            dataCV.inserisciCittadinoVaccinato(vax);
                            CentriVaccinali.closePreviousWindow(CentriVaccinali.registraVaccinatoFrame);
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

    public String getTfNomeCentro() {
        return tfNomeCentro.getText();
    }

    public String getTfNome() {
        return tfNome.getText();
    }

    public String getTfCognome() {
        return tfCognome.getText();
    }

    public String getTfCodiceFiscale() {
        return tfCodiceFiscale.getText();
    }

    public String getTfDataVaccino() {return tfDataVaccino.getText();}

    public String getTfIDUnivoco() {
        return tfIDUnivoco.getText();
    }

    public String getTipoVaccino() { return Objects.requireNonNull(cboxTipoVaccino.getSelectedItem()).toString();}

    private boolean checkAllInputs(){
        boolean allFieldsValid;  // Tramite una variabile booleana, verifico se tutti i campi siano completi

        allFieldsValid = checkInput(getTfNome(), tfNome);
        allFieldsValid &= checkInput(getTfCognome(), tfCognome);
        allFieldsValid &= getTfCodiceFiscale().matches(CF_REGEX);
        allFieldsValid &= dataVaccino != null;

        return allFieldsValid;
    }

    public Timestamp stringToTimestamp(String stringa){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(stringa);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setTipo(String type){

        if(type.equals("Pfizer")){
            tipo=1;
        }
        if(type.equals("AstraZeneca")){
            tipo=2;
        }
        if(type.equals("Moderna")){
            tipo=3;
        }
        if(type.equals("J&J")){
            tipo=4;
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
        cboxTipoVaccino = new JComboBox<String>(tipologia);
    }
}
