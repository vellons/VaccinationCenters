package cittadini;

import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe RegistraCitt permette di completare la registrazione di un cittadino
 * vaccinato
 *
 * @author Pazienza Silvio
 */

public class RegistraCitt {

    /**
     * <code>panelCopletaRegistrazione</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie la parte che si occupa dell'inserimento
     * dei dati per registrare un cittadino
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; richiamabile da classi esterne
     */

    public JPanel panelCopletaRegistrazione;

    /**
     * <code>lblIdUnivoco</code> &egrave; una label che mostra il seguente testo = "Identificatore univoco"
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblIdUnivoco;

    /**
     * <code>panelLogo</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPanel panelLogo;

    /**
     * <code>tfIdUnivoco</code> &egrave; un campo di testo Swing dedicato all'id univoco del cittadino
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfIdUnivoco;

    /**
     * <code>lblNome</code> &egrave; una label Swing dedicata al campo nome
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblNome;

    /**
     * <code>tfNome</code> &egrave; un campo di testo Swing dedicato al nome del cittadino
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfNome;


    /**
     * <code>lblCognome</code> &egrave; una label Swing dedicata al campo cognome
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCognome;

    /**
     * <code>tfCognome</code> &egrave; un campo di testo Swing dedicato al cognome
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCognome;

    /**
     * <code>lblCodiceFiscale</code> &egrave; una label Swing dedicata al campo codice fiscale
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCodiceFiscale;

    /**
     * <code>tfCodiceFiscale</code> &egrave; un campo di testo Swing dedicato al codice fiscale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCodiceFiscale;

    /**
     * <code>lblEmail</code> &egrave; una label Swing dedicata al campo email
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblEmail;

    /**
     * <code>tfEmail</code> &egrave; un campo di testo Swing dedicato all'email
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfEmail;

    /**
     * <code>lblErrors</code> &egrave; una label Swing dedicata al campo di controllo errori
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblErrors;

    /**
     * <code>btnRegistraVaccinato</code> &egrave; un bottone Swing che attiva la procedura
     * di registrazione di un vaccinato
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnRegistraVaccinato;

    /**
     * <code>panelLogo2</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPanel panelLogo2;

    /**
     * <code>lblPassword</code> &egrave; una label Swing dedicata al campo password
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblPassword;


    /**
     * <code>tfPassword</code> &egrave; un campo password Swing dedicato alla password
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPasswordField tfPassword;

    /**
     * <code>btnCercaCittadino</code> &egrave; un bottone Swing che attiva la procedura
     * di ricerca di un cittadino
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnCercaCittadino;

    /**
     * <code>tfDataSomministrazione</code> &egrave; un campo di testo Swing dedicato alla data di somministrazione del vaccino
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfDataSomministrazione;

    /**
     * <code>lblDataSomministrazione</code> &egrave; una label Swing dedicata al campo data somministrazione
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblDataSomministrazione;

    /**
     * <code>lblHelpIdentificatore</code> &egrave; una label Swing che mostra il seguente
     * testo = "L'identicativo univoco ti viene fornito durante la vaccinazione"
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblHelpIdentificatore;

    /**
     * <code>db</code> &egrave;
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private final DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();

    /**
     * <code>showJOptionPane</code> &egrave; una variabile booleana che permette di mostrare il JOptionPane all'utente se impostato a true
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private boolean showJOptionPane = true;

    /**
     * Primo costruttore della classe
     */

    public RegistraCitt() {
        dontShowTextAndLabels();

        btnCercaCittadino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblErrors.setVisible(false);
                //controlli sulle stringhe (idUnivoco)
                if (!checkId()) {
                    lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                    lblErrors.setText("Assicurarsi che il campo non sia vuoto");
                    lblErrors.setForeground(Color.RED);
                    lblErrors.setVisible(true);
                    return;
                }
                try {
                    Vaccinato userVax = db.getVaccinatoByIDUnique(tfIdUnivoco.getText());
                    if (userVax != null) {
                        if (userVax.getEmail() != null) {
                            JOptionPane.showMessageDialog(null, "Hai già completato la registrazione in precedenza.",
                                    "Registrazione già effettuata", JOptionPane.PLAIN_MESSAGE);
                            Cittadini.closePreviousWindow(Cittadini.registraCittadinoCV);
                        } else {
                            try {
                                Cittadini.reloadRegistraCitt(Cittadini.registraCittadinoCV, tfIdUnivoco.getText(), userVax);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "L'id inserito non è prensente.\n" +
                                "Assicurarsi di aver digitato correttamente l'id\ne riprovare.", "ID non presente", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    ServerConnectionSingleton.resetConnection();
                }
            }
        });
    }

    /**
     * Secondo costruttore della classe
     *
     * @param idUnivoco &egrave; una stringa rappresentante l'id univoco dell'utente
     * @param userVax   &egrave; un oggetto di classe Vaccinato
     */

    public RegistraCitt(String idUnivoco, Vaccinato userVax) {
        tfIdUnivoco.setText(idUnivoco);
        tfIdUnivoco.setEditable(false);
        tfDataSomministrazione.setEditable(false);
        btnCercaCittadino.setVisible(false);
        lblHelpIdentificatore.setVisible(false);

        tfNome.setText(userVax.getNome());
        tfCognome.setText(userVax.getCognome());
        tfCodiceFiscale.setText(userVax.getCodice_fiscale());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        tfDataSomministrazione.setText(formatter.format(userVax.getData_somministrazione()));

        tfPassword.addFocusListener(new FocusAdapter() {
            /**
             * @param e &egrave; un oggetto di classe focusEvent
             */
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (showJOptionPane) {
                    JOptionPane.showMessageDialog(null,
                            "Inserisci una password con un minimo di 8 caratteri (massimo di 20), una lettera minuscola, una lettera " +
                                    "maiuscola e un numero.", "Regole inserimento della password", JOptionPane.PLAIN_MESSAGE);
                    showJOptionPane = false;
                }

            }
        });

        btnRegistraVaccinato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                checkEmail(email);
                //controlli sulle stringhe (email e password)
                if (!checkEmail(email)) {
                    JOptionPane.showMessageDialog(null, "L'email da te inserita non rispetta la sintassi adeguata.\n", "Email non valida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String pwd = String.valueOf(tfPassword.getPassword());
                if (!checkPassword(pwd)) {
                    JOptionPane.showMessageDialog(null, "La password da te inserita non rispetta la sintassi adeguata.\n" +
                            "La password deve contenere una lettera maiuscola, una minuscola e almeno una lettera. \n" +
                            "La lunghezza deve essere compresa tra 8 e 20 caratteri", "Password non valida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int result = db.updateRegistraVaccinato(tfEmail.getText(), String.valueOf(tfPassword.getPassword()), tfIdUnivoco.getText());
                    if (result == 0) {
                        JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo.", "Registrazione completata", JOptionPane.PLAIN_MESSAGE);
                        Cittadini.closePreviousWindow(Cittadini.registraCittadinoCV);
                    } else if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Registrazione non avvenuta con successo.\nL'email da te inserita esiste già", "Registrazione fallita", JOptionPane.ERROR_MESSAGE);
                    } else if (result == -1) {
                        JOptionPane.showMessageDialog(null, "Si è verificato un problema dal lato server.", "Registrazione fallita", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    ServerConnectionSingleton.resetConnection();
                }
            }
        });
    }

    /**
     * <code>dontShowTextAndLabels</code> &egrave; una procedura per non mostrare le label e
     * i campi di testo all'interno del JFrame
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     */

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
        lblErrors.setVisible(true);
        tfDataSomministrazione.setVisible(false);
        lblDataSomministrazione.setVisible(false);
        lblErrors.setText("Compila il campo per proseguire con la registrazione");
        btnRegistraVaccinato.setVisible(false);
    }

    /**
     * <code>checkId</code> &egrave; un metodo per controllare se il textfield dell'id univoco è diverso da vuoto
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @return valore booleano che indica se i dati inseriti nel textfield sono validi
     */

    private boolean checkId() {
        boolean res = false;
        String idUnivoco = tfIdUnivoco.getText();
        if (!idUnivoco.equals("")) {
            res = true;
        }
        return res;
    }

    /**
     * <code>checkEmail</code> &egrave; un metodo per controllare se l'email inserita nel textfield è valida o meno
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param email &egrave; una stringa rappresentante l'email da analizzare
     * @return valore booleano che indica se i dati inseriti nel textfield sono validi
     */

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

    /**
     * <code>checkPassword</code> &egrave; un metodo per controllare se la password inserita nel textfield è valida o meno
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param pwd &egrave; una stringa rappresentante la password da analizzare
     * @return valore booleano che indica se i dati inseriti nel textfield sono validi
     */

    private boolean checkPassword(String pwd) {
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
        panelLogo2 = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelLogo2.add(picLabel2);

        tfIdUnivoco = new JTextField();
        tfEmail = new JTextField();
        tfPassword = new JPasswordField();

        tfIdUnivoco.setDocument(new JTextFieldCharLimit(36));
        tfEmail.setDocument(new JTextFieldCharLimit(40));
        tfEmail.setDocument(new JTextFieldCharLimit(40));
        tfPassword.setDocument(new JTextFieldCharLimit(20));
    }
}
