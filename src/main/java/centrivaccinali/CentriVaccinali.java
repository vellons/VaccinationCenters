package centrivaccinali;

import global.ServerConnectionSingleton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * La classe CentriVaccinali serve per inizializzare il loto dell'applicazione dal lato degli operatori
 *
 * @author Mahdi Said
 */

public class CentriVaccinali {

    /**
     * <code>registraCVFrame</code> &egrave; una cornice Swing attivata nel momento nel
     * quale si vuole registrare un centro vaccinale
     *
     * @see RegistraCV
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    public static JFrame registraCVFrame = new JFrame("Centri Vaccinali Operatori - Registrazione Centro Vaccinale");

    /**
     * <code>registraVaccinatoFrame</code> &egrave; una cornice Swing attivata nel momento nel
     * quale si vuole registrare un centro vaccinale
     *
     * @see RegistraVaccinato
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    public static JFrame registraVaccinatoFrame = new JFrame("Centri Vaccinali Operatori - Registrazione Vaccinato");

    /**
     * <code>panelCentriVaccinali</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie la parte che si occupa dell'interfaccia
     * di benvenuto degli operatori
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JPanel panelCentriVaccinali;

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
     * <code>btnRegistraVaccinato</code> &egrave; un bottone Swing che attiva la procedura
     * di registrazione di un nuovo utente vaccinato
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnRegistraVaccinato;

    /**
     * Costruttore della classe
     */

    public CentriVaccinali() {
        btnRegistraVaccinato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registraVaccinatoFrame.setContentPane(new RegistraVaccinato().panelRegistraVaccinato);
                    initUI(registraVaccinatoFrame);
                    registraVaccinatoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
                    registraVaccinatoFrame.pack();
                    registraVaccinatoFrame.setLocationRelativeTo(null);
                    registraVaccinatoFrame.setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnRegistraCV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registraCVFrame.setContentPane(new RegistraCV().panelRegistraCV);
                    initUI(registraCVFrame);
                    registraCVFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
                    registraCVFrame.pack();
                    registraCVFrame.setLocationRelativeTo(null);
                    registraCVFrame.setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Main della classe
     *
     * @throws UnsupportedLookAndFeelException &egrave; utilizzata quando le richieste di tipo "Look and Feel" non vanno a buon fine
     * @throws ClassNotFoundException &egrave; utilizzata quando i caricamenti delle classi non vanno a buon fine
     * @throws InstantiationException &egrave; utilizzata quando una classe non riesce ad essere istanziata
     * @throws IllegalAccessException &egrave; utilizzata quando non si dispongono degli accessi per fare operazioni
     */

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame mainCentriVaccinali = new JFrame("Centri Vaccinali Operatori");

        mainCentriVaccinali.setContentPane(new CentriVaccinali().panelCentriVaccinali);
        initUI(mainCentriVaccinali);

        mainCentriVaccinali.pack();
        mainCentriVaccinali.setLocationRelativeTo(null); // Mette la finestra al centro (da richiamare dopo .pack())
        mainCentriVaccinali.setVisible(true);
        ServerConnectionSingleton.getDatabaseInstance();  // Creo la prima istanza della connessione al DB e recupero l'indirizzo del server
    }

    /**
     * <code>initUI</code> &egrave; una procedura per inizializzare l'interfaccia
     * utente su una finestra e per finalizzarne le impostazioni
     *
     * @param frame &egrave; il frame sul quale applicare le impostazioni
     *              &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     *              &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     * @throws ClassNotFoundException          se non trova la classe da caricare
     * @throws UnsupportedLookAndFeelException e le classi look and feel richieste non sono presenti sul sistema
     * @throws InstantiationException          se per qualche motivo la classe non può essere istanziata
     * @throws IllegalAccessException          quando si cerca di effettuare l'accesso ad un campo laddove non &egrave; possibile
     */

    public static void initUI(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon("media/CVLogo.png");
        Image image = imageIcon.getImage();
        frame.setIconImage(image);

        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            System.setProperty("apple.awt.brushMetalLook", "true");
            // use the mac system menu bar
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            // set the "About" menu item name
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Centri Vaccinali Server");
            // use smoother fonts
            System.setProperty("apple.awt.antialiasing", "true");
            // ref: http://developer.apple.com/releasenotes/Java/Java142RNTiger/1_NewFeatures/chapter_2_section_3.html
            System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
            // use the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
    }

    /**
     * <code>closePreviousWindow</code> &egrave; una procedura per chiudere una
     * finestra non più utilizzata
     *
     * @param finestra &egrave; la finestra da chiudere
     *                 &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     *                 &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    public static void closePreviousWindow(JFrame finestra) {
        finestra.setVisible(false);
        finestra.dispose();
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
        BufferedImage myPicture = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelLogo2 = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelLogo2.add(picLabel2);
    }
}
