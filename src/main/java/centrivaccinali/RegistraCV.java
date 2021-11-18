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
import models.TipologiaCentroVaccinale;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * La classe RegistrazioneRistorante permette la registrazione di
 * un centro vaccinale sull'applicazione
 *
 * @author Mahdi Said
 */

public class RegistraCV {

    /**
     * <code>panelRegistraCV</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie la parte che si occupa dell'inserimento
     * dei dati dei centri vaccinali
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; richiamabile da classi esterne
     */

    public JPanel panelRegistraCV;

    /**
     * <code>cv</code> &egrave; un'istanza della classe CentroVaccinale che
     * permette di usare le funzionalit&agrave; per la costruzione dei centri vaccinali.
     *
     * @see CentroVaccinale
     * <p>
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private CentroVaccinale cv;

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
     * <code>btnRegistraCV</code> &egrave; un bottone Swing che attiva la procedura
     * di registrazione di un centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnRegistraCV;

    /**
     * <code>tfNomeCentro</code> &egrave; un campo di testo Swing dedicato al nome del centro
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfNomeCentro;

    /**
     * <code>tfIndirizzo</code> &egrave; un campo di testo Swing dedicato all'indirizzo
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfIndirizzo;

    /**
     * <code>tfCivico</code> &egrave; un campo di testo Swing dedicato al numero civico
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCivico;

    /**
     * <code>tfComune</code> &egrave; un campo di testo Swing dedicato al comune
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfComune;

    /**
     * <code>tfSiglaProvincia</code> &egrave; un campo di testo Swing dedicato alla sigla provincia
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfSiglaProvincia;

    /**
     * <code>tfCap</code> &egrave; un campo di testo Swing dedicato al cap
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JTextField tfCap;

    /**
     * <code>cboxTipoCentro</code> &egrave; una combobox Swing usata per la selezione della
     * tipologia del centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox<String> cboxTipoCentro;

    /**
     * <code>lblNomeCentro</code> &egrave; un'etichetta Swing dedicata al campo nome centro
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblNomeCentro;

    /**
     * <code>lblQualificatore</code> &egrave; un'etichetta Swing dedicata al campo qualificatore
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblQualificatore;

    /**
     * <code>lblIndirizzo</code> &egrave; un'etichetta Swing dedicata al campo indirizzo
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblIndirizzo;

    /**
     * <code>lblCivico</code> &egrave; un'etichetta Swing dedicata al campo numero civico
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCivico;

    /**
     * <code>lblComune</code> &egrave; un'etichetta Swing dedicata al campo comune
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblComune;

    /**
     * <code>lblSiglaProvincia</code> &egrave; un'etichetta Swing dedicata al campo sigla provincia
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblSiglaProvincia;

    /**
     * <code>lblCap</code> &egrave; un'etichetta Swing dedicata al campo cap
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblCap;

    /**
     * <code>lblTipoCentro</code> &egrave; un'etichetta Swing dedicata al campo del tipo del centro
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblTipoCentro;

    /**
     * <code>lblErrors</code> &egrave; un'etichetta Swing dedicata al campo di controllo errori
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblErrors;

    /**
     * <code>cboxQualificatore</code> &egrave; una combobox Swing usata per la selezione del
     * qualificatore dell'indirizzo
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JComboBox cboxQualificatore;

    /**
     * <code>tipo</code> &egrave; un intero che contiene il valore
     * effettivo della tipologia del centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private int tipo = 0;

    /**
     * <code>tipologie</code> &egrave; un' arraylist che contiene i
     * possibili valori per la tipologia di centro vaccinale registrati sul database
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    private static List<TipologiaCentroVaccinale> tipologie = new ArrayList<>();

    /**
     * <code>qualificatore</code> &egrave; un array di stringhe che contiene i
     * possibili valori per il qualificatore d'indirizzo
     */

    String[] qualificatore = new String[]{"Via", "Viale", "Piazza", "Corso", "Vicolo"};

    /**
     * Main della classe
     *
     * @throws RemoteException &egrave; utilizzata quando si presentano errori nelle comunicazioni remote
     */

    public RegistraCV() throws IOException {
        $$$setupUI$$$();
        tfNomeCentro.setDocument(new JTextFieldCharLimit(128));
        tfIndirizzo.setDocument(new JTextFieldCharLimit(128));
        tfCivico.setDocument(new JTextFieldCharLimit(8));
        tfComune.setDocument(new JTextFieldCharLimit(64));
        tfSiglaProvincia.setDocument(new JTextFieldCharLimit(2));
        tfCap.setDocument(new JTextFieldCharLimit(5));
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
                            cv = new CentroVaccinale(getTfNomeCentro(), tipo, 1, getQualificatore(), getTfIndirizzo(), getTfCivico(), getTfComune(), getTfSiglaProvincia().toUpperCase(), getTfCap());
                            db.inserisciCentroVaccinale(cv);
                            CentriVaccinali.closePreviousWindow(CentriVaccinali.registraCVFrame);
                            JOptionPane.showMessageDialog(null, "La registrazione è " +
                                    "andata a buon fine!", "Registrazione effettuate", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "C'è stato un problema. Prova a riavviare l'app",
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
        tfIndirizzo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isSpaceChar(e.getKeyChar())) && !(Character.valueOf(e.getKeyChar()).toString().equals("'"))) {
                    e.consume();
                }
            }
        });
        tfComune.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isSpaceChar(e.getKeyChar())) && !(Character.valueOf(e.getKeyChar()).toString().equals("'"))) {
                    e.consume();
                }
            }
        });
        tfSiglaProvincia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar()))) {
                    e.consume();
                }
            }
        });
        tfCivico.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isDigit(e.getKeyChar()))) {
                    e.consume();
                }
            }
        });
        tfCap.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isDigit(e.getKeyChar()))) {
                    e.consume();
                }
            }
        });
        tfNomeCentro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isDigit(e.getKeyChar())) && !(Character.isSpaceChar(e.getKeyChar())) && !(Character.valueOf(e.getKeyChar()).toString().equals("'"))) {
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

    public String getTfNomeCentro() {
        return tfNomeCentro.getText();
    }

    /**
     * <code>getTfQualificatore</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del qualificatore d'indirizzo
     */

    public String getQualificatore() {
        return Objects.requireNonNull(cboxQualificatore.getSelectedItem()).toString();
    }

    /**
     * <code>getTfIndirizzo</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del nome d'indirizzo
     */

    public String getTfIndirizzo() {
        return tfIndirizzo.getText();
    }

    /**
     * <code>getTfCivico</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del numero civico
     */

    public String getTfCivico() {
        return tfCivico.getText();
    }

    /**
     * <code>getTfComune</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del comune
     */

    public String getTfComune() {
        return tfComune.getText();
    }

    /**
     * <code>getTfSiglaProvincia</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore della sigla provincia
     */

    public String getTfSiglaProvincia() {
        return tfSiglaProvincia.getText();
    }

    /**
     * <code>getTfCap</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore del cap
     */

    public String getTfCap() {
        return tfCap.getText();
    }

    /**
     * <code>getTipoCentro</code> &egrave; un metodo getter
     * &egrave; dichiarato <strong>public</strong> in quanto il metodo &egrave; utilizzabile all'esterno della classe
     *
     * @return il valore della tipologia del centro vaccinale
     */

    public String getTipoCentro() {
        return Objects.requireNonNull(cboxTipoCentro.getSelectedItem()).toString();
    }

    /**
     * <code>setTipo</code> &egrave; un metodo per impostare il valore effettivo del tipo
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @param type &egrave; una stringa rappresentante il contenuto della stringa da analizzare
     *             &egrave; dichiarata <strong>void</strong> in quanto il metodo non restituisce alcun valore
     */

    private void setTipo(String type) {

        for (TipologiaCentroVaccinale obj : tipologie) {
            if (Objects.equals(obj.getNome(), type)) {
                tipo = obj.getId();
            }
        }

    }

    /**
     * <code>checkAllInputs</code> &egrave; un metodo per controllare il contenuto dei textfield
     * &egrave; dichiarato <strong>private</strong> in quanto il metodo &egrave; utilizzabile all'interno della classe
     *
     * @return valore booleano che indica se i dati inseriti nei textfield sono validi
     */

    private boolean checkAllInputs() {
        boolean allFieldsValid;  // Tramite una variabile booleana, verifico se tutti i campi siano completi

        allFieldsValid = checkInput(getTfNomeCentro(), tfNomeCentro);
        if (allFieldsValid) {
            allFieldsValid = firstLetter(getTfNomeCentro());
        }
        allFieldsValid &= checkInput(getTfIndirizzo(), tfIndirizzo);
        if (allFieldsValid) {
            allFieldsValid = firstLetter(getTfIndirizzo());
        }
        allFieldsValid &= checkInput(getTfCivico(), tfCivico);
        allFieldsValid &= checkInput(getTfComune(), tfComune);
        if (allFieldsValid) {
            allFieldsValid = firstLetter(getTfComune());
        }
        allFieldsValid &= checkInput(getTfSiglaProvincia(), tfSiglaProvincia);
        allFieldsValid &= checkInput(getTfCap(), tfCap);

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
        cboxQualificatore = new JComboBox<String>(qualificatore);
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaCentroVaccinale();
            } catch (RemoteException e) {
                e.printStackTrace();
                ServerConnectionSingleton.resetConnection();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        for (TipologiaCentroVaccinale obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipoCentro = new JComboBox(tipologieCombo.toArray());
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
        panelRegistraCV = new JPanel();
        panelRegistraCV.setLayout(new GridLayoutManager(4, 2, new Insets(10, 10, 10, 10), -1, -1));
        panelRegistraCV.setBackground(new Color(-723724));
        panelLogo.setBackground(new Color(-723724));
        panelRegistraCV.add(panelLogo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-723724));
        panelRegistraCV.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblNomeCentro = new JLabel();
        lblNomeCentro.setBackground(new Color(-723724));
        lblNomeCentro.setForeground(new Color(-3727837));
        lblNomeCentro.setText("Nome centro");
        panel1.add(lblNomeCentro, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfNomeCentro = new JTextField();
        panel1.add(tfNomeCentro, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblQualificatore = new JLabel();
        lblQualificatore.setBackground(new Color(-723724));
        lblQualificatore.setForeground(new Color(-3727837));
        lblQualificatore.setText("Qualificatore indirizzo");
        panel1.add(lblQualificatore, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblIndirizzo = new JLabel();
        lblIndirizzo.setBackground(new Color(-723724));
        lblIndirizzo.setForeground(new Color(-3727837));
        lblIndirizzo.setText("Nome indirizzo");
        panel1.add(lblIndirizzo, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfIndirizzo = new JTextField();
        panel1.add(tfIndirizzo, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCivico = new JLabel();
        lblCivico.setBackground(new Color(-723724));
        lblCivico.setForeground(new Color(-3727837));
        lblCivico.setText("Numero civico");
        panel1.add(lblCivico, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCivico = new JTextField();
        tfCivico.setText("");
        panel1.add(tfCivico, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblComune = new JLabel();
        lblComune.setBackground(new Color(-723724));
        lblComune.setForeground(new Color(-3727837));
        lblComune.setText("Comune");
        panel1.add(lblComune, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfComune = new JTextField();
        panel1.add(tfComune, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblSiglaProvincia = new JLabel();
        lblSiglaProvincia.setBackground(new Color(-723724));
        lblSiglaProvincia.setForeground(new Color(-3727837));
        lblSiglaProvincia.setText("Sigla provincia");
        panel1.add(lblSiglaProvincia, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfSiglaProvincia = new JTextField();
        panel1.add(tfSiglaProvincia, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblCap = new JLabel();
        lblCap.setBackground(new Color(-723724));
        lblCap.setEnabled(true);
        lblCap.setForeground(new Color(-3727837));
        lblCap.setText("Cap");
        panel1.add(lblCap, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCap = new JTextField();
        panel1.add(tfCap, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblTipoCentro = new JLabel();
        lblTipoCentro.setBackground(new Color(-723724));
        Font lblTipoCentroFont = this.$$$getFont$$$(null, -1, -1, lblTipoCentro.getFont());
        if (lblTipoCentroFont != null) lblTipoCentro.setFont(lblTipoCentroFont);
        lblTipoCentro.setForeground(new Color(-3727837));
        lblTipoCentro.setText("Tipo centro");
        panel1.add(lblTipoCentro, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(cboxTipoCentro, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(cboxQualificatore, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-723724));
        panelRegistraCV.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblErrors = new JLabel();
        lblErrors.setText("Compila tutti i campi per procedere con la registrazione");
        panel2.add(lblErrors, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-723724));
        panelRegistraCV.add(panel3, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnRegistraCV = new JButton();
        btnRegistraCV.setBackground(new Color(-3727837));
        btnRegistraCV.setBorderPainted(false);
        btnRegistraCV.setContentAreaFilled(true);
        btnRegistraCV.setDefaultCapable(true);
        btnRegistraCV.setEnabled(true);
        btnRegistraCV.setFocusPainted(false);
        btnRegistraCV.setFocusable(true);
        Font btnRegistraCVFont = this.$$$getFont$$$(null, Font.BOLD, -1, btnRegistraCV.getFont());
        if (btnRegistraCVFont != null) btnRegistraCV.setFont(btnRegistraCVFont);
        btnRegistraCV.setForeground(new Color(-1));
        btnRegistraCV.setOpaque(true);
        btnRegistraCV.setText("Registra centro");
        panel3.add(btnRegistraCV, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        panelLogo2.setBackground(new Color(-723724));
        panelRegistraCV.add(panelLogo2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        return panelRegistraCV;
    }
}
