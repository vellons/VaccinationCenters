package cittadini;

import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cittadini {

    public static JFrame mainCittadini;
    public static JFrame elencoCentriVaccinali;
    public static JFrame registraCittadinoCV;
    public static JFrame login;
    private JPanel panelCittadini;
    private JButton btnCercaCentro;
    private JButton btnRegistrati;
    private JButton btnSegnalaEvento;
    private JPanel panelLogo;
    private JPanel panelLogo2;

    public Cittadini() {
        btnCercaCentro.addActionListener(e -> openDashBoardCentriVaccinaliElenco());
        btnRegistrati.addActionListener(e -> openRegistraCittadinoCV());
        btnSegnalaEvento.addActionListener(e -> openLogin());
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        mainCittadini = new JFrame("Centri Vaccinali Cittadini - Home");

        mainCittadini.setContentPane(new Cittadini().panelCittadini);
        initUI(mainCittadini);

        mainCittadini.pack();
        mainCittadini.setLocationRelativeTo(null); // Mette la finestra al centro (da richiamare dopo .pack())
        mainCittadini.setVisible(true);
        ServerConnectionSingleton.getDatabaseInstance();  // Creo la prima istanza della connessione al DB e recupero l'indirizzo del server
    }

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

    public static void closePreviousWindow(JFrame finestra) {
        finestra.setVisible(false);
        finestra.dispose();
    }
}
