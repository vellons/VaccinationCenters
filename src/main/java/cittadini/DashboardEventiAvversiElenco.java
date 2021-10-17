package cittadini;

import models.TipologiaCentroVaccinale;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * <code>tipologie</code> &egrave; un ArrayList che contiene le tipologie di centro vaccinale
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    public static List<TipologiaCentroVaccinale> tipologie = new ArrayList<>();


    /**
     * Costruttore della classe
     */
    public DashboardEventiAvversiElenco() {
        lblUtente.setText("Ciao " + Login.utenteLoggato.getNome());
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

        // Creo la lista degli eventi avversi
        panelListaEventiAvversi = new JPanel();
        panelListaEventiAvversi.add(new ListaCentriVaccinaliPanel("", "", 0));  // TODO: nuova lista eventi avversi
    }
}
