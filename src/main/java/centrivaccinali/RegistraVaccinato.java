package centrivaccinali;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaCentroVaccinale;
import models.TipologiaVaccino;
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
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
    private JComboBox cboxNomeCentro;
    private JButton btnGeneraID;
    private JButton btnDataCorrente;
    private int tipo=0;
    private int centro_id=0;
    private static List<CentroVaccinale> nomi = new ArrayList<>();
    private static List<TipologiaVaccino> tipologie = new ArrayList<>();
    private final String CF_REGEX = "/^(?:[A-Z][AEIOU][AEIOUX]|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$/i";

    public RegistraVaccinato() throws Exception {

        btnRegistraVaccinato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTipo(getTipoVaccino());
                setID(getNomeCentro());
                dataVaccino=stringToTimestamp(getTfDataVaccino());
                if (checkAllInputs()) {
                    try {
                        if (JOptionPane.showOptionDialog(null, "Confermi di voler registrare il nuovo utente?",
                                "Conferma registrazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, null, null) == JOptionPane.YES_OPTION) {
                            DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                            vax = new Vaccinato(getTfIDUnivoco(),centro_id,tipo,getTfNome(),getTfCognome(),getTfCodiceFiscale(),dataVaccino,"","");
                            db.inserisciCittadinoVaccinato(vax);
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
        btnDataCorrente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
                Date date = new Date();
                tfDataVaccino.setText(formatter.format(date));
            }
        });
        btnGeneraID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfIDUnivoco.setText(UUID.randomUUID().toString());
            }
        });
    }

    //METODI GETTERS

    public String getNomeCentro() { return Objects.requireNonNull(cboxNomeCentro.getSelectedItem()).toString();}

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
        allFieldsValid &= isAlphabetic(getTfNome());
        allFieldsValid &= checkInput(getTfCognome(), tfCognome);
        allFieldsValid &= isAlphabetic(getTfCognome());
        allFieldsValid &= getTfCodiceFiscale().matches(CF_REGEX);
        allFieldsValid &= dataVaccino != null;
        allFieldsValid &= checkInput(getTfIDUnivoco(), tfIDUnivoco);

        return allFieldsValid;
    }

    public static boolean isAlphabetic(String input) {
        for (int i = 0; i != input.length(); ++i) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public Timestamp stringToTimestamp(String stringa){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(stringa);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            return timestamp;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void setTipo(String type){

        for (TipologiaVaccino obj : tipologie) {
            if(Objects.equals(obj.getNome(), type)){
                tipo=obj.getId();
            }
        }

    }

    public void setID(String name){

        for (CentroVaccinale obj : nomi) {
            if(Objects.equals(obj.getNome(), name)){
                centro_id=obj.getId();
            }
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
        // Prendo le tipologie di centro vaccinale
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaVaccino();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        for (TipologiaVaccino obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipoVaccino = new JComboBox(tipologieCombo.toArray());
        if (nomi.size() == 0) {
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
                nomi = db.getCentriVaccinali("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        List<String> nomiCombo = new ArrayList<>();
        for (CentroVaccinale obj : nomi) {
            nomiCombo.add(obj.getNome());
        }

        cboxNomeCentro = new JComboBox(nomiCombo.toArray());

    }
}
