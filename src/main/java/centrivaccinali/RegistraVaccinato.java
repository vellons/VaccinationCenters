/*
 * Copyright (c) 2021. University Of Insubria, Varese.
 *
 * Authors:
 * - Vellone Alex 741527 VA
 * - Macaj Manuel 741854 VA
 * - Said Ibrahim Mahdi 741512 VA
 * - Pazienza Silvio 741486 VA
 */

package centrivaccinali;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.TipologiaVaccino;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * La classe CentriVaccinali permette la registrazione di un nuovo utente vaccinato presso un centro
 *
 * @author Mahdi Said
 */

public class RegistraVaccinato {

    /**
     * <code>panelRegistraVaccino</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie la parte che si occupa dell'inserimento
     * dei dati degli utenti vaccinati
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; richiamabile da classi esterne
     */

    public JPanel panelRegistraVaccinato;

    /**
     * <code>vax</code> &egrave; un'istanza della classe Vaccinato che
     * permette di usare le funzionalit&agrave; per la registrazione degli utenti vaccinati.
     *
     * @see Vaccinato
     * <p>
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private Vaccinato vax;

    /**
     * <code>panelLogo</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPanel panelLogo;

    /**
     * <code>panelLogo2</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPanel panelLogo2;

    /**
     * <code>tfNomeCentro</code> &egrave; un campo di testo Swing dedicato al nome del centro
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfNomeCentro;

    /**
     * <code>tfNome</code> &egrave; un campo di testo Swing dedicato al nome utente
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfNome;

    /**
     * <code>tfCognome</code> &egrave; un campo di testo Swing dedicato al cognome utente
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCognome;

    /**
     * <code>tfCodiceFiscale</code> &egrave; un campo di testo Swing dedicato al codice fiscale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCodiceFiscale;

    /**
     * <code>tfDataVaccino</code> &egrave; un campo di testo Swing dedicato alla data di somministrazione
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfDataVaccino;

    /**
     * <code>tfIDUnivoco</code> &egrave; un campo di testo Swing dedicato all'identificatore univoco
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfIDUnivoco;

    /**
     * <code>btnRegistraVaccinato</code> &egrave; un bottone Swing che attiva la procedura
     * di registrazione di un utente vaccinato
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnRegistraVaccinato;

    /**
     * <code>lblErrors</code> &egrave; un'etichetta Swing dedicata al campo di controllo errori
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblErrors;

    /**
     * <code>lblNomeCentro</code> &egrave; un'etichetta Swing dedicata al campo del nome del centro
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblNomeCentro;

    /**
     * <code>lblNome</code> &egrave; un'etichetta Swing dedicata al campo del nome utente
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblNome;

    /**
     * <code>lblCognome</code> &egrave; un'etichetta Swing dedicata al campo del cognome utente
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCognome;

    /**
     * <code>lblCodiceFiscale</code> &egrave; un'etichetta Swing dedicata al campo del codice fiscale
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCodiceFiscale;

    /**
     * <code>lblDataVaccino</code> &egrave; un'etichetta Swing dedicata al campo della data di somministrazione
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblDataVaccino;

    /**
     * <code>lblTipoVaccino</code> &egrave; un'etichetta Swing dedicata al campo della tipologia del vaccino
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblTipoVaccino;

    /**
     * <code>lblIDUnivoco</code> &egrave; un'etichetta Swing dedicata al campo dell'identificatore univoco
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblIDUnivoco;

    /**
     * <code>tipo</code> &egrave; un timestamp che contiene il valore
     * effettivo della data di somministrazione
     */

    private Timestamp dataVaccino = null;

    /**
     * <code>cboxTipoVaccino</code> &egrave; una combobox Swing usata per la selezione della
     * tipologia del vaccino.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox<String> cboxTipoVaccino;

    /**
     * <code>cboxNomeCentro</code> &egrave; una combobox Swing usata per la selezione del
     * nome del centro vaccinale.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox cboxNomeCentro;

    /**
     * <code>btnGeneraID</code> &egrave; un bottone Swing che attiva la procedura
     * di compilazione del campo dell'identificatore univoco.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnGeneraID;

    /**
     * <code>btnDataCorrente</code> &egrave; un bottone Swing che attiva la procedura
     * di compilazione del campo relativo alla corrente data di somministrazione.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnDataCorrente;

    /**
     * <code>tipo</code> &egrave; un intero che contiene il valore
     * effettivo della tipologia del vaccino.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int tipo = 0;

    /**
     * <code>tipo</code> &egrave; un intero che contiene il valore
     * effettivo dell'identificativo del centro vaccinale.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int centro_id = 0;

    /**
     * <code>nomi</code> &egrave; un' arraylist che contiene i
     * possibili valori dei nomi dei centri vaccinali registrati sul database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da non doverla istanziare creando un oggetto
     */

    private static List<CentroVaccinale> nomi = new ArrayList<>();

    /**
     * <code>tipologie</code> &egrave; un' arraylist che contiene i
     * possibili valori per la tipologia di vaccino registrati sul database.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da non doverla istanziare creando un oggetto
     */

    private static List<TipologiaVaccino> tipologie = new ArrayList<>();

    /**
     * <code>CF_REGEX</code> &egrave; un' espressione regolare che serve per verificare
     * l'integrit&agrave; del codice fiscale inserito in input.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>final</strong> in quanto il suo valore &egrave; immutabile nel corso dell'esecuzione
     */

    private final String CF_REGEX = "^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$";

    /**
     * Main della classe
     *
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezzione possa verificarsi
     */

    public RegistraVaccinato() throws Exception {
        $$$setupUI$$$();
        tfNome.setDocument(new JTextFieldCharLimit(64));
        tfCognome.setDocument(new JTextFieldCharLimit(64));
        tfCodiceFiscale.setDocument(new JTextFieldCharLimit(16));
        btnRegistraVaccinato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTipo(getTipoVaccino());
                setID(getNomeCentro());
                if (!checkInput(getTfDataVaccino(), tfDataVaccino)) {
                    generaData();
                }
                if (!checkInput(getTfIDUnivoco(), tfIDUnivoco)) {
                    generaID();
                }
                dataVaccino = stringToTimestamp(getTfDataVaccino());
                if (checkAllInputs()) {
                    try {
                        if (JOptionPane.showOptionDialog(null, "Confermi di voler registrare il nuovo utente?",
                                "Conferma registrazione", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, null, null) == JOptionPane.YES_OPTION) {
                            DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                            vax = new Vaccinato(getTfIDUnivoco(), centro_id, tipo, getTfNome(), getTfCognome(), getTfCodiceFiscale(), dataVaccino, "", "");
                            db.inserisciCittadinoVaccinato(vax);
                            CentriVaccinali.closePreviousWindow(CentriVaccinali.registraVaccinatoFrame);
                            JOptionPane.showInputDialog(null, "La registrazione è " +
                                    "andata a buon fine! Ecco il tuo codice identificativo. Attento a non perderlo!!!", getTfIDUnivoco());
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "C'è stato un problema. Prova a riavviare l'app",
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
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
        tfNome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isSpaceChar(e.getKeyChar())) && !(Character.valueOf(e.getKeyChar()).toString().equals("'"))) {
                    e.consume();
                }
            }
        });
        tfCognome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isSpaceChar(e.getKeyChar())) && !(Character.valueOf(e.getKeyChar()).toString().equals("'"))) {
                    e.consume();
                }
            }
        });
        tfCodiceFiscale.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isDigit(e.getKeyChar()))) {
                    e.consume();
                }
            }
        });
    }

    //METODI GETTERS

    /**
     * <code>getTfNomeCentro</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del nome del centro vaccinale
     */

    public String getNomeCentro() {
        return Objects.requireNonNull(cboxNomeCentro.getSelectedItem()).toString();
    }

    /**
     * <code>getTfNome</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del nome utente
     */

    public String getTfNome() {
        return tfNome.getText();
    }

    /**
     * <code>getTfCognome</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del cognome utente
     */

    public String getTfCognome() {
        return tfCognome.getText();
    }

    /**
     * <code>getTfCodiceFiscale</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del codice fiscale
     */

    public String getTfCodiceFiscale() {
        return tfCodiceFiscale.getText();
    }

    /**
     * <code>getTfDataVaccino</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore della data di somministrazione
     */

    public String getTfDataVaccino() {
        return tfDataVaccino.getText();
    }

    /**
     * <code>getTfIDUnivoco</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore dell'identificatore univoco
     */

    public String getTfIDUnivoco() {
        return tfIDUnivoco.getText();
    }

    /**
     * <code>getTipoVaccino</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del tipo del vaccino
     */

    public String getTipoVaccino() {
        return Objects.requireNonNull(cboxTipoVaccino.getSelectedItem()).toString();
    }

    /**
     * <code>checkAllInputs</code> &egrave; un metodo per controllare il contenuto dei textfield
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @return valore booleano che indica se i dati inseriti nei textfield sono validi
     */

    private boolean checkAllInputs() {
        boolean allFieldsValid;  // Tramite una variabile booleana, verifico se tutti i campi siano completi

        allFieldsValid = checkInput(getTfNome(), tfNome);
        if (allFieldsValid) {
            allFieldsValid = firstLetter(getTfNome());
        }
        allFieldsValid &= checkInput(getTfCognome(), tfCognome);
        if (allFieldsValid) {
            allFieldsValid = firstLetter(getTfCognome());
        }
        allFieldsValid &= getTfCodiceFiscale().matches(CF_REGEX);
        return allFieldsValid;
    }

    /**
     * <code>firstLetter</code> &egrave; un metodo per controllare se l'input di un textfield ha una lettera come primo carattere
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param input &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     * @return valore booleano che indica se il il primo carattere di una stringa &egrave; una lettera
     */

    private boolean firstLetter(String input) {
        return Character.isLetter(input.charAt(0));
    }

    /**
     * <code>stringToTimestamp</code> &egrave; un metodo per convertire una stringa in un timestamp
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param stringa &egrave; una stringa rappresentante il contenuto della stringa da convertire
     * &egrave; dichiarata <strong>Timestamp</strong> in quanto il metodo restituisce un valore di una data e di un'ora
     * @return Timestamp
     */


    private Timestamp stringToTimestamp(String stringa) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date parsedDate = dateFormat.parse(stringa);
            Timestamp timestamp = new Timestamp(parsedDate.getTime());
            return timestamp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <code>setTipo</code> &egrave; un metodo per impostare l'effettivo valore del tipo
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param type &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     *             &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void setTipo(String type) {

        for (TipologiaVaccino obj : tipologie) {
            if (Objects.equals(obj.getNome(), type)) {
                tipo = obj.getId();
            }
        }

    }

    /**
     * <code>setID</code> &egrave; un metodo per impostare il valore dell'ID di un centro
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param name &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     *             &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void setID(String name) {

        for (CentroVaccinale obj : nomi) {
            if (Objects.equals(obj.getNome(), name)) {
                centro_id = obj.getId();
            }
        }

    }

    /**
     * <code>generaData</code> &egrave; un metodo per scrivere la data di somministrazione nel caso in cui l'utente non lo avesse gi&agrave; fatto
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe*
     * &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void generaData() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        tfDataVaccino.setText(formatter.format(date));
    }

    /**
     * <code>generaID</code> &egrave; un metodo per generare un identiicativo nel caso l'utente non ne avesse uno
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe*
     * &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void generaID() {
        tfIDUnivoco.setText(UUID.randomUUID().toString());
    }


    /**
     * <code>checkInput</code> &egrave; un metodo per controllare l'input di un textfield
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param input     &egrave; una stringa rappresentante il contenuto del campo da analizzare
     * @param textField &egrave; il textfield di riferimento dell'input
     * @return valore booleano che indica se il dato &egrave; inserito nel textfield
     */

    private boolean checkInput(String input, JTextField textField) {
        boolean res;
        String tmp = "";
        tmp += input;
        // Se il campo e vuoto, visualizzo una scritta
        res = !tmp.equals("");
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
        // Prendo le tipologie di centro vaccinale
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaVaccino();
            } catch (RemoteException e) {
                e.printStackTrace();
                ServerConnectionSingleton.resetConnection();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        for (TipologiaVaccino obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipoVaccino = new JComboBox(tipologieCombo.toArray());
        try {
            DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
            nomi = db.getCentriVaccinali("");
        } catch (RemoteException e) {
            e.printStackTrace();
            ServerConnectionSingleton.resetConnection();
        }
        List<String> nomiCombo = new ArrayList<>();
        for (CentroVaccinale obj : nomi) {
            nomiCombo.add(obj.getNome());
        }

        cboxNomeCentro = new JComboBox(nomiCombo.toArray());

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
        panelRegistraVaccinato = new JPanel();
        panelRegistraVaccinato.setLayout(new GridLayoutManager(4, 2, new Insets(10, 10, 10, 10), -1, -1));
        panelRegistraVaccinato.setBackground(new Color(-723724));
        panelLogo.setBackground(new Color(-723724));
        panelRegistraVaccinato.add(panelLogo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-723724));
        panelRegistraVaccinato.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblNomeCentro = new JLabel();
        lblNomeCentro.setBackground(new Color(-723724));
        lblNomeCentro.setForeground(new Color(-3727837));
        lblNomeCentro.setText("Nome centro vaccinale");
        panel1.add(lblNomeCentro, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblNome = new JLabel();
        lblNome.setBackground(new Color(-723724));
        lblNome.setForeground(new Color(-3727837));
        lblNome.setText("Nome");
        panel1.add(lblNome, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfNome = new JTextField();
        panel1.add(tfNome, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCognome = new JLabel();
        lblCognome.setBackground(new Color(-723724));
        lblCognome.setForeground(new Color(-3727837));
        lblCognome.setText("Cognome");
        panel1.add(lblCognome, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCognome = new JTextField();
        panel1.add(tfCognome, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCodiceFiscale = new JLabel();
        lblCodiceFiscale.setBackground(new Color(-723724));
        lblCodiceFiscale.setForeground(new Color(-3727837));
        lblCodiceFiscale.setText("Codice fiscale");
        panel1.add(lblCodiceFiscale, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCodiceFiscale = new JTextField();
        panel1.add(tfCodiceFiscale, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblDataVaccino = new JLabel();
        lblDataVaccino.setBackground(new Color(-723724));
        lblDataVaccino.setForeground(new Color(-3727837));
        lblDataVaccino.setText("Data somministrazione");
        panel1.add(lblDataVaccino, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfDataVaccino = new JTextField();
        tfDataVaccino.setEditable(false);
        tfDataVaccino.setText("");
        tfDataVaccino.setToolTipText("");
        panel1.add(tfDataVaccino, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblTipoVaccino = new JLabel();
        lblTipoVaccino.setBackground(new Color(-723724));
        lblTipoVaccino.setForeground(new Color(-3727837));
        lblTipoVaccino.setText("Tipo vaccino");
        panel1.add(lblTipoVaccino, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblIDUnivoco = new JLabel();
        lblIDUnivoco.setBackground(new Color(-723724));
        lblIDUnivoco.setForeground(new Color(-3727837));
        lblIDUnivoco.setText("Identificatore univoco");
        panel1.add(lblIDUnivoco, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfIDUnivoco = new JTextField();
        tfIDUnivoco.setEditable(false);
        panel1.add(tfIDUnivoco, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        panel1.add(cboxTipoVaccino, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(cboxNomeCentro, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnGeneraID = new JButton();
        btnGeneraID.setBackground(new Color(-12828863));
        btnGeneraID.setBorderPainted(true);
        btnGeneraID.setForeground(new Color(-4473925));
        btnGeneraID.setText("");
        panel1.add(btnGeneraID, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(20, 20), 0, false));
        btnDataCorrente = new JButton();
        btnDataCorrente.setBackground(new Color(-12828863));
        btnDataCorrente.setBorderPainted(true);
        btnDataCorrente.setForeground(new Color(-4473925));
        btnDataCorrente.setOpaque(true);
        btnDataCorrente.setText("");
        panel1.add(btnDataCorrente, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(20, 20), 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-723724));
        panelRegistraVaccinato.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblErrors = new JLabel();
        lblErrors.setEnabled(true);
        lblErrors.setFocusable(false);
        lblErrors.setText("Compila tutti i campi per procedere con la registrazione");
        panel2.add(lblErrors, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-723724));
        panelRegistraVaccinato.add(panel3, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnRegistraVaccinato = new JButton();
        btnRegistraVaccinato.setBackground(new Color(-3727837));
        btnRegistraVaccinato.setBorderPainted(false);
        btnRegistraVaccinato.setEnabled(true);
        btnRegistraVaccinato.setFocusPainted(false);
        Font btnRegistraVaccinatoFont = this.$$$getFont$$$(null, Font.BOLD, -1, btnRegistraVaccinato.getFont());
        if (btnRegistraVaccinatoFont != null) btnRegistraVaccinato.setFont(btnRegistraVaccinatoFont);
        btnRegistraVaccinato.setForeground(new Color(-1));
        btnRegistraVaccinato.setOpaque(true);
        btnRegistraVaccinato.setText("Registra vaccinato");
        panel3.add(btnRegistraVaccinato, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        panelLogo2.setBackground(new Color(-723724));
        panelRegistraVaccinato.add(panelLogo2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelRegistraVaccinato;
    }
}
