package cittadini;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cittadini{

    private JPanel panelCittadini;
    private JButton cercaCentroVaccinaleButton;
    private JButton registratiPressoUnCButton;
    private JButton segnalaEventoAvversoButton;
    private JPanel panelLogo;
    private JPanel panelLogo2;
    private JPanel panelLogo3;

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame mainCittadini = new JFrame("Centri Vaccinali Cittadini");

        mainCittadini.setContentPane(new Cittadini().panelCittadini);
        initUI(mainCittadini);

        mainCittadini.pack();
        mainCittadini.setLocationRelativeTo(null); // Mette la finestra al centro (da richiamare dopo .pack())
        mainCittadini.setVisible(true);
    }

    public static void initUI(JFrame frame) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        BufferedImage myPicture = ImageIO.read(new File("media/PrimulaLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelLogo2 = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/EatAdvisorLogo.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelLogo2.add(picLabel2);
        panelLogo3 = new JPanel();
        BufferedImage myPicture3 = ImageIO.read(new File("media/Cittadini.png"));
        JLabel picLabel3 = new JLabel(new ImageIcon(myPicture3));
        panelLogo3.add(picLabel3);
    }
}
