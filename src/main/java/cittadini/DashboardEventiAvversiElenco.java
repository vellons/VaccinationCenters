package cittadini;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
     * <code>panelTitle</code> rappresenta un pannello per inserire il titolo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelTitle;

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
     * <code>lblUtente</code> rappresenta una label per inserire il totale di vaccinati.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblUtente;
    private JLabel lblDataVaccino;

    /**
     * <code>segnalaEventiAvversiFrame</code> &egrave; una cornice Swing attivata nel momento nel
     * quale si vuole inserire un nuovo evento avverso
     *
     * <p>
     * &egrave; dichiarata <strong>private</strong> in quanto deve essere accessibile solo all'interno della classe
     */
    private JFrame segnalaEventiAvversiFrame;

    /**
     * Costruttore della classe
     */
    public DashboardEventiAvversiElenco() {
        lblUtente.setText("Ciao " + Login.utenteLoggato.getNome());
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            lblDataVaccino.setText("Ti sei vaccinat* il " + LocalDateTime.parse(Login.utenteLoggato.getData_somministrazione().toString(), formatter).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            lblDataVaccino.setText("");
        }

        btnAggiungiEventoAvverso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segnalaEventiAvversiFrame = new JFrame("Segnala eventi avversi");
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
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelTitle = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/Cittadini.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelTitle.add(picLabel2);

        // Creo la lista degli eventi avversi appartenenti all'utente loggato
        panelListaEventiAvversi = new JPanel();
        panelListaEventiAvversi.add(new ListaEventiSegnalatiPanel());
    }
}
