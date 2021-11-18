/*
 * Copyright (c) 2021. University Of Insubria, Varese.
 *
 * Authors:
 * - Vellone Alex 741527 VA
 * - Macaj Manuel 741854 VA
 * - Said Ibrahim Mahdi 741512 VA
 * - Pazienza Silvio 741486 VA
 */

package cittadini;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
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
import java.util.Locale;
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

    public RegistraCitt() throws IOException {
        $$$setupUI$$$();
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

    public RegistraCitt(String idUnivoco, Vaccinato userVax) throws IOException {
        $$$setupUI$$$();
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() throws IOException {
        createUIComponents();
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelCopletaRegistrazione = new JPanel();
        panelCopletaRegistrazione.setLayout(new GridLayoutManager(4, 2, new Insets(10, 10, 10, 10), -1, -1));
        panelCopletaRegistrazione.setBackground(new Color(-723724));
        panel1.add(panelCopletaRegistrazione, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelLogo.setBackground(new Color(-723724));
        panelCopletaRegistrazione.add(panelLogo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-723724));
        panelCopletaRegistrazione.add(panel2, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblIdUnivoco = new JLabel();
        lblIdUnivoco.setBackground(new Color(-723724));
        lblIdUnivoco.setForeground(new Color(-3727837));
        lblIdUnivoco.setText("Identificatore univoco");
        panel2.add(lblIdUnivoco, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(tfIdUnivoco, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblNome = new JLabel();
        lblNome.setBackground(new Color(-723724));
        lblNome.setForeground(new Color(-3727837));
        lblNome.setText("Nome");
        panel2.add(lblNome, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfNome = new JTextField();
        tfNome.setEditable(false);
        panel2.add(tfNome, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCognome = new JLabel();
        lblCognome.setBackground(new Color(-723724));
        lblCognome.setForeground(new Color(-3727837));
        lblCognome.setText("Cognome");
        panel2.add(lblCognome, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCognome = new JTextField();
        tfCognome.setEditable(false);
        panel2.add(tfCognome, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCodiceFiscale = new JLabel();
        lblCodiceFiscale.setBackground(new Color(-723724));
        lblCodiceFiscale.setForeground(new Color(-3727837));
        lblCodiceFiscale.setText("Codice fiscale");
        panel2.add(lblCodiceFiscale, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCodiceFiscale = new JTextField();
        tfCodiceFiscale.setEditable(false);
        panel2.add(tfCodiceFiscale, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblEmail = new JLabel();
        lblEmail.setBackground(new Color(-723724));
        lblEmail.setForeground(new Color(-3727837));
        lblEmail.setText("Email");
        panel2.add(lblEmail, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(tfEmail, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblPassword = new JLabel();
        lblPassword.setBackground(new Color(-723724));
        lblPassword.setForeground(new Color(-3727837));
        lblPassword.setText("Password");
        panel2.add(lblPassword, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfPassword.setText("");
        panel2.add(tfPassword, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnCercaCittadino = new JButton();
        btnCercaCittadino.setActionCommand("Cerca");
        btnCercaCittadino.setBackground(new Color(-3727837));
        btnCercaCittadino.setBorderPainted(false);
        btnCercaCittadino.setEnabled(true);
        btnCercaCittadino.setFocusPainted(false);
        btnCercaCittadino.setForeground(new Color(-1));
        btnCercaCittadino.setOpaque(true);
        btnCercaCittadino.setText("Cerca");
        panel2.add(btnCercaCittadino, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        tfDataSomministrazione = new JTextField();
        panel2.add(tfDataSomministrazione, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblDataSomministrazione = new JLabel();
        lblDataSomministrazione.setForeground(new Color(-3727837));
        lblDataSomministrazione.setText("Data Somministrazione");
        panel2.add(lblDataSomministrazione, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-723724));
        panelCopletaRegistrazione.add(panel3, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblErrors = new JLabel();
        lblErrors.setEnabled(true);
        lblErrors.setFocusable(false);
        lblErrors.setText("Compila tutti i campi per procedere con la registrazione");
        panel3.add(lblErrors, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-723724));
        panelCopletaRegistrazione.add(panel4, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnRegistraVaccinato = new JButton();
        btnRegistraVaccinato.setBackground(new Color(-3727837));
        btnRegistraVaccinato.setBorderPainted(false);
        btnRegistraVaccinato.setEnabled(true);
        btnRegistraVaccinato.setFocusPainted(false);
        btnRegistraVaccinato.setForeground(new Color(-1));
        btnRegistraVaccinato.setOpaque(true);
        btnRegistraVaccinato.setText("Completa registrazione");
        panel4.add(btnRegistraVaccinato, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        lblHelpIdentificatore = new JLabel();
        lblHelpIdentificatore.setEnabled(true);
        lblHelpIdentificatore.setFocusable(false);
        Font lblHelpIdentificatoreFont = this.$$$getFont$$$(null, Font.ITALIC, -1, lblHelpIdentificatore.getFont());
        if (lblHelpIdentificatoreFont != null) lblHelpIdentificatore.setFont(lblHelpIdentificatoreFont);
        lblHelpIdentificatore.setText("L'identicativo univoco ti viene fornito durante la vaccinazione");
        panel4.add(lblHelpIdentificatore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelLogo2.setBackground(new Color(-723724));
        panelCopletaRegistrazione.add(panelLogo2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

}
