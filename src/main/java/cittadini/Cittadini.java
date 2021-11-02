package cittadini;

import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * La classe Cittadini permette di ricercare un centro vaccinale, registrarsi presso un centro vacciale
 * e segnalare un evento avverso
 *
 * @author Pazienza Silvio
 */

public class Cittadini {

    /**
     * <code>mainCittadini</code> &egrave; una cornice Swing di partenza in cui vengono
     * mostrati i tre bottoni principali
     *
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    public static JFrame mainCittadini;

    /**
     * <code>elencoCentriVaccinali</code> &egrave; una cornice Swing che mostra l'elenco
     * dei centri vaccinali presenti nel database
     *
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    public static JFrame elencoCentriVaccinali;

    /**
     * <code>registraCittadinoCV</code> &egrave; una cornice Swing che permette all'utente
     * di completare la registrazione
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    public static JFrame registraCittadinoCV;

    /**
     * <code>login</code> &egrave; una cornice Swing dedicata alla login di un
     * cittadino
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    public static JFrame login;

    /**
     * <code>panelCittadini</code> &egrave; il pannello principale del JFrame MainCittadini
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     */

    private JPanel panelCittadini;

    /**
     * <code>btnCercaCentro</code> &egrave; un bottone Swing che attiva la procedura
     * di ricerca di un centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnCercaCentro;

    /**
     * <code>btnRegistrati</code> &egrave; un bottone Swing che attiva la procedura
     * di registrazione presso un centro vaccinale
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnRegistrati;

    /**
     * <code>btnRegistrati</code> &egrave; un bottone Swing che attiva la procedura
     * di segnalazione di un evento avverso
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JButton btnSegnalaEvento;

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
     * Costruttore della classe
     */
    public Cittadini() {
        btnCercaCentro.addActionListener(e -> openDashBoardCentriVaccinaliElenco());
        btnRegistrati.addActionListener(e -> openRegistraCittadinoCV());
        btnSegnalaEvento.addActionListener(e -> openLogin());
    }

    /**
     * Main classe Cittadini
     *
     * @param args
     * @throws UnsupportedLookAndFeelException &egrave; utilizzata quando le richieste di tipo "Look and Feel" non vanno a buon fine
     * @throws ClassNotFoundException          &egrave; utilizzata quando i caricamenti delle classi non vanno a buon fine
     * @throws InstantiationException          &egrave; utilizzata quando una classe non riesce ad essere istanziata
     * @throws IllegalAccessException          &egrave; utilizzata quando non si dispongono degli accessi per fare operazioni
     */

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        mainCittadini = new JFrame("Centri Vaccinali Cittadini - Home");

        mainCittadini.setContentPane(new Cittadini().panelCittadini);
        initUI(mainCittadini);

        mainCittadini.pack();
        mainCittadini.setLocationRelativeTo(null); // Mette la finestra al centro (da richiamare dopo .pack())
        mainCittadini.setVisible(true);
        ServerConnectionSingleton.getDatabaseInstance();  // Creo la prima istanza della connessione al DB e recupero l'indirizzo del server
    }

    /**
     * <code>initUI</code> &egrave; una procedura per inizializzare l'interfaccia
     * utente su una finestra e per finalizzarne le impostazioni
     *
     * @param frame &egrave; il frame sul quale applicare le impostazioni
     *              <br> &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     *              <br> &egrave; dichiarata <strong>static</strong> così da non doverla istanziare creando un oggetto
     * @throws ClassNotFoundException          se non trova la classe da caricare
     * @throws UnsupportedLookAndFeelException e le classi look and feel richieste non sono presenti sul sistema
     * @throws InstantiationException          se per qualche motivo la classe non può essere istanziata
     * @throws IllegalAccessException          quando si cerca di effettuare l'accesso ad un campo laddove non &egrave; possibile
     */

    public static void initUI(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        ImageIcon imageIcon = new ImageIcon("media/CVLogo.png");
        Image image = imageIcon.getImage();
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Definisce il comportamento della finestra

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

    /**
     * <code>openDashBoardCentriVaccinaliElenco</code> &egrave; una procedura per aprire la dashboard
     * contenente l'elenco dei centri vaccinali
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     */

    private void openDashBoardCentriVaccinaliElenco() {
        try {
            elencoCentriVaccinali = new JFrame("Centri Vaccinali Cittadini - Elenco");
            elencoCentriVaccinali.setContentPane(new DashboardCentriVaccinaliElenco("", "", 0).panelDashboardCentriVaccinaliElenco);
            initUI(elencoCentriVaccinali);
            elencoCentriVaccinali.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
            elencoCentriVaccinali.pack();
            elencoCentriVaccinali.setLocationRelativeTo(null);
            elencoCentriVaccinali.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * <code>openRegistraCittadinoCV<code> &egrave; una procedura per aprire la finestra
     * dedicata alla registrazione di un cittadino presso un centro vaccinale
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     */

    private void openRegistraCittadinoCV() {
        try {
            registraCittadinoCV = new JFrame("Centri Vaccinali Cittadini - Completa registrazione");
            registraCittadinoCV.setContentPane(new RegistraCitt().panelCopletaRegistrazione);
            initUI(registraCittadinoCV);
            registraCittadinoCV.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
            registraCittadinoCV.pack();
            registraCittadinoCV.setLocationRelativeTo(null);
            registraCittadinoCV.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * <code>openLogin<code> &egrave; una procedura per aprire la finestra
     * dedicata alla login di un cittadino
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     */

    private void openLogin() {
        try {
            login = new JFrame("Centri Vaccinali Cittadini - Login");
            login.setContentPane(new Login().panelLogin);
            initUI(login);
            login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
            login.pack();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * <code>reloadDashBoardCentriVaccinaliElencoConFiltri</code> &egrave; un metodo che permette di aggiornare il JFrame
     * dedicato alla lista dei centri vaccinali in base ai filtri applicati
     *
     * @param dashboardFrame  &egrave; la dashboard che contiene i filtri
     * @param filtroNome      &egrave; il filtro per il nome
     * @param filtroComune    &egrave; il filtro per il comune
     * @param filtroTipologia &egrave; il filtro per la tipologia
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezione potrebbe
     *                   essere sollevata durante l'esecuzione del programma
     */
    public static void reloadDashBoardCentriVaccinaliElencoConFiltri(JFrame dashboardFrame, String filtroNome, String filtroComune, int filtroTipologia) throws Exception {
        dashboardFrame.setVisible(false);
        dashboardFrame.dispose();
        dashboardFrame.invalidate();
        dashboardFrame.setContentPane(new DashboardCentriVaccinaliElenco(filtroNome, filtroComune, filtroTipologia).panelDashboardCentriVaccinaliElenco);
        initUI(dashboardFrame);
        dashboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dashboardFrame.pack();
        dashboardFrame.setLocationRelativeTo(null);
        dashboardFrame.setVisible(true);
    }

    /**
     * <code>reloadRegistraCitt</code> &egrave; un metodo che permette di aggiornare il JFrame
     * dedicato alla registrazione per il cittadino
     *
     * @param signUpCitt &egrave;
     * @param idUnivoco  &egrave; una stringa che rappresenta l'id univoco
     * @param userVax    &egrave; un oggetto di classe Vaccinato
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezione potrebbe
     *                   essere sollevata durante l'esecuzione del programma
     */

    public static void reloadRegistraCitt(JFrame signUpCitt, String idUnivoco, Vaccinato userVax) throws Exception {
        signUpCitt.setVisible(false);
        signUpCitt.dispose();
        signUpCitt.invalidate();
        signUpCitt.setContentPane(new RegistraCitt(idUnivoco, userVax).panelCopletaRegistrazione);
        initUI(signUpCitt);
        signUpCitt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpCitt.pack();
        signUpCitt.setLocationRelativeTo(null);
        signUpCitt.setVisible(true);
    }

    /**
     * <code>reloadDashboardEventiAvversiElenco</code> &egrave; un metodo che permette di aggiornare il JFrame
     * dedicato alla lista degli eventi avversi
     *
     * @param elencoEventiAvversi &egrave; un elenco che contiene tutti gli eventi avversi segnalati
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezione potrebbe
     *                   essere sollevata durante l'esecuzione del programma
     */

    public static void reloadDashboardEventiAvversiElenco(JFrame elencoEventiAvversi) throws Exception {
        elencoEventiAvversi.setVisible(false);
        elencoEventiAvversi.dispose();
        elencoEventiAvversi.invalidate();
        elencoEventiAvversi.setContentPane(new DashboardEventiAvversiElenco().panelDashboardCentriVaccinaliElenco);
        initUI(elencoEventiAvversi);
        elencoEventiAvversi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        elencoEventiAvversi.pack();
        elencoEventiAvversi.setLocationRelativeTo(null);
        elencoEventiAvversi.setVisible(true);
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
}
