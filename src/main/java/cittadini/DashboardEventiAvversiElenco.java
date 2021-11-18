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
import com.intellij.uiDesigner.core.Spacer;
import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.EventoAvverso;
import models.TipologiaEvento;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Locale;

/**
 * La classe DashboardCentriVaccinaliElenco permette l'utilizzo di visualizzare l'elenco degli eventi avversi
 * del cittadino che si è loggato
 * *
 * * @author Vellons
 */
public class DashboardEventiAvversiElenco extends JFrame {
    /**
     * <code>panelDashboardCentriVaccinaliElenco</code> rappresenta un pannello.
     * <p>
     * &egrave; dichiarato <strong>public</strong> cos&igrave; da poter essere visibile anche alle altre classi
     */
    public JPanel panelDashboardCentriVaccinaliElenco;

    /**
     * <code>panelLogo</code> rappresenta un pannello per inserire il logo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelLogo;

    /**
     * <code>panelListaEventiAvversi</code> rappresenta un pannello.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelListaEventiAvversi;

    /**
     * <code>btnAggiungiEventoAvverso</code> rappresenta un bottone.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnAggiungiEventoAvverso;

    /**
     * <code>lblUtente</code> rappresenta una label per il nome dell'utente.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblUtente;

    /**
     * <code>lblDataVaccino</code> rappresenta una label per inserire la data di vaccinazione dell'utente.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblDataVaccino;

    /**
     * <code>lbBtnDisabilitato</code> rappresenta una label con del testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lbBtnDisabilitato;

    /**
     * <code>panelEventoAvversoModifica</code> rappresenta un pannello per gestire altri componenti.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelEventoAvversoModifica;

    /**
     * <codelbSeverita></code> rappresenta una label con del testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lbSeverita;

    /**
     * <code>sliderServerita</code> rappresenta uno slider della severit&agrave;
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JSlider sliderServerita;

    /**
     * <code>lbCounterCharacter</code> rappresenta una label con del testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lbCounterCharacter;

    /**
     * <code>txtNote</code> rappresenta un campo di testo inseribile dall'utente.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextArea txtNote;

    /**
     * <code>lbTipologiaEventoAvverso</code> rappresenta una label con del testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lbTipologiaEventoAvverso;

    /**
     * <code>btnModifica</code> rappresenta un bottone.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnModifica;

    /**
     * <code></code> rappresenta un bottone.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnAnnulla;

    /**
     * <code></code> rappresenta una label con del testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblTuoCentro;

    /**
     * <code></code> rappresenta un evento avverso da modificare.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     *
     * @see EventoAvverso
     */
    private EventoAvverso eventoAvversoModifica;

    /**
     * <code>segnalaEventiAvversiFrame</code> &egrave; una cornice Swing attivata nel momento nel
     * quale si vuole inserire un nuovo evento avverso.
     *
     * <p>
     * &egrave; dichiarata <strong>private</strong> in quanto deve essere accessibile solo all'interno della classe
     */
    public static JFrame segnalaEventiAvversiFrame;

    /**
     * Costruttore della classe.
     * Recupera le informazioni necessarie dal server e le mostra nel pannello.
     */
    public DashboardEventiAvversiElenco() throws Exception {
        $$$setupUI$$$();
        panelEventoAvversoModifica.setVisible(false);
        lbBtnDisabilitato.setVisible(false);
        lblUtente.setText("Ciao " + Login.utenteLoggato.getNome());
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            lblDataVaccino.setText("Ti sei vaccinato/a il " + LocalDateTime.parse(Login.utenteLoggato.getData_somministrazione().toString(), formatter).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            lblDataVaccino.setText("");
        }

        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();

        try { // se l'utente ha segnalato tutti i tipi esistenti di eventi avversi lo disabilito
            if (db.getTipologieEventi().size() == ListaEventiSegnalatiPanel.eventiUtenteCorrente.size()) {
                btnAggiungiEventoAvverso.setEnabled(false);
                lbBtnDisabilitato.setVisible(true);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            ServerConnectionSingleton.resetConnection();
        }

        try {
            String nomeCentro = db.getCentriVaccinali("WHERE id=" + Login.utenteLoggato.getCentro_vaccinale_id() + " ").get(0).getNome();
            lblTuoCentro.setText("Il tuo centro: " + nomeCentro.substring(0, Math.min(nomeCentro.length(), 30)));
        } catch (RemoteException e) {
            lblTuoCentro.setText("");
            e.printStackTrace();
            ServerConnectionSingleton.resetConnection();
        }

        btnAggiungiEventoAvverso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segnalaEventiAvversiFrame = new JFrame("Centri Vaccinali Cittadini - Segnala eventi avversi");
                try {
                    segnalaEventiAvversiFrame.setContentPane(new DashboardSegnalaEventiAvversi().panelNewReport);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                segnalaEventiAvversiFrame.pack();
                segnalaEventiAvversiFrame.setLocationRelativeTo(null);
                segnalaEventiAvversiFrame.setVisible(true);
                try {
                    Cittadini.initUI(segnalaEventiAvversiFrame);
                    segnalaEventiAvversiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        txtNote.setDocument(new JTextFieldCharLimit(256));
        txtNote.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                lbCounterCharacter.setText("Caratteri: " + txtNote.getText().length() + "/256");
            }
        });

        txtNote.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isDigit(e.getKeyChar())) && !(Character.isSpaceChar(e.getKeyChar())) && !(Character.valueOf(e.getKeyChar()).toString().equals("'")) && !(Character.valueOf(e.getKeyChar()).toString().equals("°")) && !(Character.valueOf(e.getKeyChar()).toString().equals(".")) && !(Character.valueOf(e.getKeyChar()).toString().equals(":")) && !(Character.valueOf(e.getKeyChar()).toString().equals(";")) && !(Character.valueOf(e.getKeyChar()).toString().equals(",")) && !(Character.valueOf(e.getKeyChar()).toString().equals("?")) && !(Character.valueOf(e.getKeyChar()).toString().equals("!")))
                    e.consume();
            }
        });
        btnAnnulla.addActionListener(e -> {
            if (jOptionPanelYesOrNo("Sei sicuro di annullare la modifica dell'evento avverso corrente?", "Annullare?")) {
                panelEventoAvversoModifica.setVisible(false);
                panelEventoAvversoModifica.invalidate(); // distruggo il pannello
                eventoAvversoModifica = null;
            }
        });
        btnModifica.addActionListener(e -> {
            if (jOptionPanelYesOrNo("Sei sicuro di voler modificare questo evento?", "Modificare?")) {
                try {
                    eventoAvversoModifica.setNote(txtNote.getText());
                    eventoAvversoModifica.setSeverita(sliderServerita.getValue());

                    boolean result = db.updateEventoAvverso(eventoAvversoModifica);
                    if (result) {
                        jOptionPanelMessageDialog("Modifica eseguita con successo", "Modifica eseguita");
                        panelEventoAvversoModifica.setVisible(false);
                        Cittadini.closePreviousWindow(Login.elencoEventiAvversi);
                        try {
                            Cittadini.reloadDashboardEventiAvversiElenco(Login.elencoEventiAvversi);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * <code>startModificaPanel</code> &egrave; un metodo abilitare la modifica di un evento avverso.
     * <br>&Egrave; dichiarato <strong>protected</strong> in quanto metodo utilizzato all'interno del package
     *
     * @throws RemoteException in caso di problemi con RMI
     */
    protected void startModificaPanel(EventoAvverso ea) throws RemoteException {
        if (panelEventoAvversoModifica.isVisible()) {
            if (jOptionPanelYesOrNo("Non hai terminato di modificare l'evento avverso precedente.\nContinuare? (Perderai i dati della modifica in corso)", "Attenzione")) {
                eventoAvversoModifica = ea;
                configureModificaPanel();
            }
        } else {
            eventoAvversoModifica = ea;
            panelEventoAvversoModifica.setVisible(true);
            configureModificaPanel();
        }
    }

    /**
     * <code>configureModificaPanel</code> &egrave; un metodo inizializzare il processo di modifica di un evento avverso.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @throws RemoteException in caso di problemi con RMI
     */
    private void configureModificaPanel() throws RemoteException {
        lbTipologiaEventoAvverso.setText(getTipologiaEventoAvverso(eventoAvversoModifica.getTipologia_evento_id()));
        sliderServerita.setValue(eventoAvversoModifica.getSeverita());
        lbSeverita.setText("Severità: " + sliderServerita.getValue());
        txtNote.setText(eventoAvversoModifica.getNote());
        sliderServerita.addChangeListener(e -> lbSeverita.setText("Severità: " + sliderServerita.getValue()));
    }


    /**
     * <code>getTipologiaEventoAvverso</code> &egrave; un metodo per il recupero del nome dell'evento avverso dal server.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param idTipologiaEvento id dell'evento
     * @return stringa contenente il nome dell'evento avverso
     * @throws RemoteException in caso di problemi con RMI
     */
    private String getTipologiaEventoAvverso(int idTipologiaEvento) throws RemoteException {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
        StringBuilder result = new StringBuilder();
        for (TipologiaEvento elem : db.getTipologieEventi()) {
            if (elem.getId() == idTipologiaEvento) {
                result.append(elem.getNome().substring(0, 1).toUpperCase()).append(elem.getNome().substring(1));
                break;
            }
        }
        return result.toString();
    }

    /**
     * <code>jOptionPanelMessageDialog</code> &egrave; un metodo per la costruzione del Dialog message.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param message messaggio da mostrare nel Dialog message
     * @param title   titolo del Dialog message
     */
    private boolean jOptionPanelYesOrNo(String message, String title) {
        return (JOptionPane.showOptionDialog(null, message,
                title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null) == JOptionPane.YES_OPTION);
    }

    /**
     * <code>jOptionPanelMessageDialog</code> &egrave; un metodo per la costruzione del Dialog message.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param message messaggio da mostrare nel Dialog message
     * @param title   titolo del Dialog message
     */
    private void jOptionPanelMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezione potrebbe
     *                   essere sollevata durante l'esecuzione del programma
     */
    private void createUIComponents() throws Exception {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/IconaFioreSicuro.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);

        // Creo la lista degli eventi avversi appartenenti all'utente loggato
        panelListaEventiAvversi = new JPanel();
        panelListaEventiAvversi.add(new ListaEventiSegnalatiPanel());

        sliderServerita = new JSlider(JSlider.HORIZONTAL, 1, 5, 1);
        sliderServerita.setMinorTickSpacing(1);
        sliderServerita.setPaintLabels(true);
        sliderServerita.setPaintTicks(true);

        // Add positions label in the slider
        Hashtable<Integer, JLabel> position = new Hashtable<>();
        position.put(1, new JLabel("1"));
        position.put(2, new JLabel("2"));
        position.put(3, new JLabel("3"));
        position.put(4, new JLabel("4"));
        position.put(5, new JLabel("5"));
        // Set the label to be drawn
        sliderServerita.setLabelTable(position);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() throws Exception {
        createUIComponents();
        panelDashboardCentriVaccinaliElenco = new JPanel();
        panelDashboardCentriVaccinaliElenco.setLayout(new GridLayoutManager(7, 4, new Insets(10, 10, 10, 10), -1, -1));
        panelDashboardCentriVaccinaliElenco.setBackground(new Color(-723724));
        panelDashboardCentriVaccinaliElenco.setMinimumSize(new Dimension(-1, -1));
        panelDashboardCentriVaccinaliElenco.setPreferredSize(new Dimension(1200, 700));
        panelLogo.setBackground(new Color(-723724));
        panelDashboardCentriVaccinaliElenco.add(panelLogo, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(-1, 200), 0, false));
        panelListaEventiAvversi.setBackground(new Color(-723724));
        panelListaEventiAvversi.setForeground(new Color(-723724));
        panelDashboardCentriVaccinaliElenco.add(panelListaEventiAvversi, new GridConstraints(2, 0, 5, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelDashboardCentriVaccinaliElenco.add(spacer1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnAggiungiEventoAvverso = new JButton();
        btnAggiungiEventoAvverso.setActionCommand("Applica filtri");
        btnAggiungiEventoAvverso.setBackground(new Color(-3727837));
        btnAggiungiEventoAvverso.setBorderPainted(false);
        btnAggiungiEventoAvverso.setFocusPainted(false);
        btnAggiungiEventoAvverso.setFocusable(false);
        btnAggiungiEventoAvverso.setForeground(new Color(-1));
        btnAggiungiEventoAvverso.setLabel("Segnala evento avverso");
        btnAggiungiEventoAvverso.setOpaque(true);
        btnAggiungiEventoAvverso.setText("Segnala evento avverso");
        panelDashboardCentriVaccinaliElenco.add(btnAggiungiEventoAvverso, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, -1), new Dimension(250, -1), 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 40), -1, -1));
        panel1.setBackground(new Color(-723724));
        panelDashboardCentriVaccinaliElenco.add(panel1, new GridConstraints(0, 3, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblUtente = new JLabel();
        lblUtente.setBackground(new Color(-723724));
        lblUtente.setDoubleBuffered(false);
        lblUtente.setFocusable(true);
        Font lblUtenteFont = this.$$$getFont$$$("Arial", Font.BOLD, 36, lblUtente.getFont());
        if (lblUtenteFont != null) lblUtente.setFont(lblUtenteFont);
        lblUtente.setForeground(new Color(-1631113));
        lblUtente.setText("Ciao");
        panel1.add(lblUtente, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        lblDataVaccino = new JLabel();
        lblDataVaccino.setBackground(new Color(-723724));
        lblDataVaccino.setDoubleBuffered(false);
        lblDataVaccino.setFocusable(true);
        Font lblDataVaccinoFont = this.$$$getFont$$$("Arial", Font.BOLD, 24, lblDataVaccino.getFont());
        if (lblDataVaccinoFont != null) lblDataVaccino.setFont(lblDataVaccinoFont);
        lblDataVaccino.setForeground(new Color(-1631113));
        lblDataVaccino.setText("Ti sei vaccinat*");
        panel1.add(lblDataVaccino, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTuoCentro = new JLabel();
        lblTuoCentro.setBackground(new Color(-723724));
        lblTuoCentro.setDoubleBuffered(false);
        lblTuoCentro.setFocusable(true);
        Font lblTuoCentroFont = this.$$$getFont$$$("Arial", Font.PLAIN, 20, lblTuoCentro.getFont());
        if (lblTuoCentroFont != null) lblTuoCentro.setFont(lblTuoCentroFont);
        lblTuoCentro.setForeground(new Color(-16777216));
        lblTuoCentro.setText("Il tuo centro: -");
        panel1.add(lblTuoCentro, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 72, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-3727837));
        label1.setText("Area riservata");
        panelDashboardCentriVaccinaliElenco.add(label1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        lbBtnDisabilitato = new JLabel();
        Font lbBtnDisabilitatoFont = this.$$$getFont$$$(null, Font.ITALIC, -1, lbBtnDisabilitato.getFont());
        if (lbBtnDisabilitatoFont != null) lbBtnDisabilitato.setFont(lbBtnDisabilitatoFont);
        lbBtnDisabilitato.setForeground(new Color(-16777216));
        lbBtnDisabilitato.setText("<html>\nHai segnalato tutti i tipi di eventi avversi.\n</html>");
        panelDashboardCentriVaccinaliElenco.add(lbBtnDisabilitato, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelEventoAvversoModifica = new JPanel();
        panelEventoAvversoModifica.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        panelEventoAvversoModifica.setBackground(new Color(-723724));
        panelEventoAvversoModifica.setPreferredSize(new Dimension(400, 200));
        panelDashboardCentriVaccinaliElenco.add(panelEventoAvversoModifica, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelEventoAvversoModifica.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panelEventoAvversoModifica.getFont()), new Color(-2105376)));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-723724));
        panelEventoAvversoModifica.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbSeverita = new JLabel();
        lbSeverita.setBackground(new Color(-723724));
        lbSeverita.setForeground(new Color(-16777216));
        lbSeverita.setText("Severità: 0");
        panel2.add(lbSeverita, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-723724));
        label2.setForeground(new Color(-16777216));
        label2.setText("Note");
        panel2.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sliderServerita.setBackground(new Color(-723724));
        sliderServerita.setForeground(new Color(-16777216));
        panel2.add(sliderServerita, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbCounterCharacter = new JLabel();
        lbCounterCharacter.setBackground(new Color(-723724));
        lbCounterCharacter.setForeground(new Color(-16777216));
        lbCounterCharacter.setText("Caratteri: 0/256");
        panel2.add(lbCounterCharacter, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel3.getFont()), new Color(-4473925)));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txtNote = new JTextArea();
        txtNote.setAutoscrolls(true);
        txtNote.setBackground(new Color(-1));
        txtNote.setColumns(0);
        txtNote.setDisabledTextColor(new Color(-8355712));
        Font txtNoteFont = this.$$$getFont$$$("Arial", Font.PLAIN, 11, txtNote.getFont());
        if (txtNoteFont != null) txtNote.setFont(txtNoteFont);
        txtNote.setLineWrap(true);
        txtNote.setTabSize(3);
        txtNote.setText("");
        txtNote.setWrapStyleWord(true);
        scrollPane1.setViewportView(txtNote);
        lbTipologiaEventoAvverso = new JLabel();
        Font lbTipologiaEventoAvversoFont = this.$$$getFont$$$(null, Font.BOLD, 16, lbTipologiaEventoAvverso.getFont());
        if (lbTipologiaEventoAvversoFont != null) lbTipologiaEventoAvverso.setFont(lbTipologiaEventoAvversoFont);
        lbTipologiaEventoAvverso.setForeground(new Color(-16777216));
        lbTipologiaEventoAvverso.setText("");
        panel2.add(lbTipologiaEventoAvverso, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-723724));
        panelEventoAvversoModifica.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnModifica = new JButton();
        btnModifica.setActionCommand("Cerca");
        btnModifica.setBackground(new Color(-3727837));
        btnModifica.setBorderPainted(false);
        btnModifica.setEnabled(true);
        btnModifica.setFocusPainted(false);
        btnModifica.setForeground(new Color(-1));
        btnModifica.setOpaque(true);
        btnModifica.setText("Conferma modifica");
        panel4.add(btnModifica, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        btnAnnulla = new JButton();
        btnAnnulla.setActionCommand("Cerca");
        btnAnnulla.setBackground(new Color(-3727837));
        btnAnnulla.setBorderPainted(false);
        btnAnnulla.setEnabled(true);
        btnAnnulla.setFocusPainted(false);
        btnAnnulla.setForeground(new Color(-1));
        btnAnnulla.setOpaque(true);
        btnAnnulla.setText("Annulla modifica");
        panel4.add(btnAnnulla, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        final Spacer spacer3 = new Spacer();
        panelDashboardCentriVaccinaliElenco.add(spacer3, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        return panelDashboardCentriVaccinaliElenco;
    }

}
