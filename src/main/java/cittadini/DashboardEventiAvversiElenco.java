package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     * <code>lblUtente</code> rappresenta una label per inserire la data di vaccinazione dell'utente.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblDataVaccino;
    private JLabel lbBtnDisabilitato;

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
    }
}
