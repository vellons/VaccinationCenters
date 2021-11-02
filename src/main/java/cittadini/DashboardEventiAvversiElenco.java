package cittadini;

import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.EventoAvverso;
import models.TipologiaEvento;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

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
     * <code>panelLogo</code> rappresenta un pannello per inserire il logo
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
    private JLabel lbBtnDisabilitato;
    private JPanel panelEventoAvversoModifica;
    private JLabel lbSeverita;
    private JLabel lbScriviCommento;
    private JSlider sliderServerita;
    private JLabel lbCounterCharacter;
    private JTextArea txtNote;
    private JLabel lbTipologiaEventoAvverso;
    private JButton btnModifica;
    private JButton btnAnnulla;
    private JLabel lblTuoCentro;
    private EventoAvverso eventoAvversoModifica;

    /**
     * <code>segnalaEventiAvversiFrame</code> &egrave; una cornice Swing attivata nel momento nel
     * quale si vuole inserire un nuovo evento avverso
     *
     * <p>
     * &egrave; dichiarata <strong>private</strong> in quanto deve essere accessibile solo all'interno della classe
     */
    public static JFrame segnalaEventiAvversiFrame;

    /**
     * Costruttore della classe
     */
    public DashboardEventiAvversiElenco() {
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
            String nomeCentro = db.getCentriVaccinali("WHERE id=" + Login.utenteLoggato.getCentro_vaccinale_id()).get(0).getNome();
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
                segnalaEventiAvversiFrame.setContentPane(new DashboardSegnalaEventiAvversi().panelNewReport);
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

    public void startModificaPanel(EventoAvverso ea) throws RemoteException {
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

    private void configureModificaPanel() throws RemoteException {
        lbTipologiaEventoAvverso.setText(getTipologiaEventoAvverso(eventoAvversoModifica.getTipologia_evento_id()));
        sliderServerita.setValue(eventoAvversoModifica.getSeverita());
        lbSeverita.setText("Severità: " + sliderServerita.getValue());
        txtNote.setText(eventoAvversoModifica.getNote());
        sliderServerita.addChangeListener(e -> lbSeverita.setText("Severità: " + sliderServerita.getValue()));
    }

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

    private boolean jOptionPanelYesOrNo(String message, String title) {
        return (JOptionPane.showOptionDialog(null, message,
                title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null) == JOptionPane.YES_OPTION);
    }

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
}
