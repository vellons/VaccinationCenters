package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistraCitt {

    public JPanel panelCopletaRegistrazione;
    private JPanel panelLogo;
    private JLabel lblIdUnivoco;
    private JTextField tfIdUnivoco;
    private JLabel lblNome;
    private JTextField tfNome;
    private JLabel lblCognome;
    private JTextField tfCognome;
    private JLabel lblCodiceFiscale;
    private JTextField tfCodiceFiscale;
    private JLabel lblEmail;
    private JTextField tfEmail;
    private JLabel lblErrors;
    private JButton btnRegistraVaccinato;
    private JPanel panelLogo2;
    private JLabel lblPassword;
    private JPasswordField tfPassword;
    private JButton btnCercaCittadino;
    private final DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();


    public RegistraCitt() {
        dontShowTextAndLabels();

        btnCercaCittadino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblErrors.setVisible(false);
                //controlli sulle stringhe (idUnivoco)
                if(!checkId()){
                    lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                    lblErrors.setText("Assicurarsi che il campo non sia vuoto");
                    lblErrors.setForeground(Color.RED);
                    lblErrors.setVisible(true);
                    return;
                }
                try {
                    System.out.println(tfIdUnivoco.getText());
                    Vaccinato userVax = db.getVaccinatoByIDUnique(tfIdUnivoco.getText());
                    if (userVax != null) {
                        try {
                            Cittadini.reloadRegistraCitt(Cittadini.registraCittadinoCV, tfIdUnivoco.getText(), userVax);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "L'id inserito non e' prensente.\n" +
                                "Assicurarsi di aver digitato correttamente l'id\ne riprovare.", "ID non presente", JOptionPane.PLAIN_MESSAGE);
                    }

                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public RegistraCitt(String idUnivoco, Vaccinato userVax) {
        tfIdUnivoco.setText(idUnivoco);
        tfIdUnivoco.setEditable(false);
        btnCercaCittadino.setVisible(false);

        tfNome.setText(userVax.getNome());
        tfCognome.setText(userVax.getCognome());
        tfCodiceFiscale.setText(userVax.getCodice_fiscale());

        btnRegistraVaccinato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                checkEmail(email);
                //controlli sulle stringhe (email e password)
                if(!checkEmail(email)){
                    JOptionPane.showMessageDialog(null, "L'email da te inserita non rispetta la sintassi adeguata.\n", "Email non valida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String pwd = String.valueOf(tfPassword.getPassword());
                if(!checkPassword(pwd)){
                    JOptionPane.showMessageDialog(null, "La password da te inserita non rispetta la sintassi adeguata.\n", "Password non valida", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    if (db.updateRegistraVaccinato(tfEmail.getText(), String.valueOf(tfPassword.getPassword()), tfIdUnivoco.getText())) {
                        JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo", "Registrazione completata", JOptionPane.PLAIN_MESSAGE);
                        Cittadini.closePreviousWindow(Cittadini.registraCittadinoCV);
                    } else {
                        JOptionPane.showMessageDialog(null, "Registrazione non avvenuta con successo.\n", "Registrazione fallita", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void dontShowTextAndLabels() {
        lblNome.setVisible(false);
        tfNome.setVisible(false);
        lblCognome.setVisible(false);
        tfCognome.setVisible(false);
        lblCodiceFiscale.setVisible(false);
        tfCodiceFiscale.setVisible(false);
        lblEmail.setVisible(false);
        tfEmail.setVisible(false);
        lblPassword.setVisible(false);
        tfPassword.setVisible(false);
        lblErrors.setVisible(false);
        btnRegistraVaccinato.setVisible(false);
    }

    private boolean checkId(){
        boolean res = false;
        String idUnivoco = tfIdUnivoco.getText();
        if(!idUnivoco.equals("")){
            res = true;
        }
        return res;
    }

    private boolean checkEmail(String email) {
        boolean res = false;
        String espressione = "^[0-9a-z]([-_.]?[0-9a-z])*@[0-9a-z]([-.]?[0-9a-z])*\\.[a-z]{2,4}$";
        Pattern p = Pattern.compile(espressione);

        Matcher m = p.matcher(email);

        boolean matchFound = m.matches();

        if (matchFound) {
            res = true;
        }
        return res;
    }

    private boolean checkPassword(String pwd){
        boolean res = false;
        String espressione = ("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
        Pattern p = Pattern.compile(espressione);

        Matcher m = p.matcher(pwd);

        boolean matchFound = m.matches();

        if (matchFound) {
            res = true;
        }
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
    }
}
