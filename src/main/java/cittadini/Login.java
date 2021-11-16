package cittadini;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * La classe Login permette all'utente di entrare nell'area riservata per vedere i suoi eventi avversi o
 * aggiungerne di nuovi
 *
 * @author Vellons
 */
public class Login {

    /**
     * <code>panelLogin</code> &egrave; un pannello frame Swing che contiene un'altra scheda
     * <p>
     * &egrave; dichiarato <strong>static</strong> cos&igrave; da poterla utlizzare senza istanziare l'oggetto
     */
    public static JFrame elencoEventiAvversi;

    /**
     * <code>panelLogin</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie la parte che si occupa del login nell'area riservata
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; richiamabile da classi esterne
     */
    public JPanel panelLogin;

    /**
     * <code>panelLogo</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelLoginLogo;


    /**
     * <code>tfEmail</code> &egrave; un campo di testo Swing dedicato all'email
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfEmail;

    /**
     * <code>tfEmail</code> &egrave; un campo di testo Swing dedicato alla password
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfPassword;

    /**
     * <code>lblErrors</code> &egrave; un'etichetta Swing dedicata al campo per mostrare gli errori
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblErrors;

    /**
     * <code>btnLogin</code> &egrave; un bottone Swing che attiva la procedura di login
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnLogin;

    /**
     * <code>utenteLoggato</code> se l'utente &egrave; loggato nell'applicazione all'interno di questa variabile
     * saranno presenti le sue informazioni
     * <p>
     * &egrave; dichiarato <strong>protected</strong> in quanto l'attributo &egrave; utilizzabile ovunque nel package
     * &egrave; dichiarato <strong>static</strong> cos&igrave; da poterla utlizzare senza istanziare l'oggetto
     */
    protected static Vaccinato utenteLoggato = null;

    /**
     * <code>dashboardEventiAvversiElenco</code> è l'oggetto che contiene le informazioni sulla pagina da gestire
     * <p>
     * &egrave; dichiarato <strong>protected</strong> in quanto l'attributo &egrave; utilizzabile ovunque nel package
     * &egrave; dichiarato <strong>static</strong> cos&igrave; da poterla utlizzare senza istanziare l'oggetto
     */
    public static DashboardEventiAvversiElenco dashboardEventiAvversiElenco;

    /**
     * Costruttore della classe Login
     */
    public Login() throws IOException {

        $$$setupUI$$$();
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblErrors.setText("");
                utenteLoggato = null;
                if (tfEmail.getText() == null || tfEmail.getText().length() < 3 || tfPassword.getText() == null || tfPassword.getText().length() < 3) {
                    lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                    lblErrors.setText("Completare i campi correttamente");
                    lblErrors.setForeground(Color.RED);
                    return;
                }
                try {
                    DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                    Vaccinato v = db.getVaccinatoByEmailAndPasswordSha(tfEmail.getText(), tfPassword.getText());
                    if (v == null) {
                        lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                        lblErrors.setText("Email o password errati");
                        lblErrors.setForeground(Color.RED);
                    } else {
                        utenteLoggato = v;
                        tfEmail.setText("");
                        tfPassword.setText("");
                        openDashElencoEventiAvversi();
                    }
                } catch (Exception ex) {
                    lblErrors.setText("Ci sono stati problemi con il login. Prova a riavviare l'app");
                    ex.printStackTrace();
                    ServerConnectionSingleton.resetConnection();
                }
            }
        });
    }

    /**
     * <code>openDashElencoEventiAvversi</code> &egrave; una procedura aprire un altro frame dell'applicazione
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     */
    private void openDashElencoEventiAvversi() {
        try {
            elencoEventiAvversi = new JFrame("Centri Vaccinali Cittadini - Area riservata");
            dashboardEventiAvversiElenco = new DashboardEventiAvversiElenco();
            elencoEventiAvversi.setContentPane(dashboardEventiAvversiElenco.panelDashboardCentriVaccinaliElenco);
            Cittadini.initUI(elencoEventiAvversi);
            elencoEventiAvversi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
            elencoEventiAvversi.pack();
            elencoEventiAvversi.setLocationRelativeTo(null);
            elencoEventiAvversi.setVisible(true);
            Cittadini.closePreviousWindow(Cittadini.login);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * <code>createUIComponents</code> &egrave; una procedura per impostare la grafica
     * quando viene caricato il frame
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     *
     * @throws IOException &egrave; utilizzata quando si verificano errori nelle fasi di input o di output
     */
    private void createUIComponents() throws IOException {
        panelLoginLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLoginLogo.add(picLabel);

        tfEmail = new JTextField();
        tfPassword = new JPasswordField();
        tfEmail.setDocument(new JTextFieldCharLimit(40));
        tfPassword.setDocument(new JTextFieldCharLimit(20));
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
        panelLogin = new JPanel();
        panelLogin.setLayout(new GridLayoutManager(5, 3, new Insets(10, 10, 10, 10), -1, -1));
        panelLogin.setBackground(new Color(-723724));
        panelLoginLogo.setBackground(new Color(-723724));
        panelLogin.add(panelLoginLogo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-723724));
        panelLogin.add(panel1, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-723724));
        label1.setForeground(new Color(-3727837));
        label1.setText("Email");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(tfEmail, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-723724));
        label2.setForeground(new Color(-3727837));
        label2.setText("Password");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1.add(tfPassword, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-723724));
        panelLogin.add(panel2, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblErrors = new JLabel();
        lblErrors.setText("Compila tutti i campi per procedere con il login");
        panel2.add(lblErrors, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-723724));
        panelLogin.add(panel3, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnLogin = new JButton();
        btnLogin.setBackground(new Color(-3727837));
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(true);
        btnLogin.setDefaultCapable(true);
        btnLogin.setEnabled(true);
        btnLogin.setFocusPainted(false);
        btnLogin.setFocusable(true);
        Font btnLoginFont = this.$$$getFont$$$(null, Font.BOLD, -1, btnLogin.getFont());
        if (btnLoginFont != null) btnLogin.setFont(btnLoginFont);
        btnLogin.setForeground(new Color(-1));
        btnLogin.setOpaque(true);
        btnLogin.setText("Login");
        panel3.add(btnLogin, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-723724));
        panelLogin.add(panel4, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, Font.ITALIC, -1, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Ricorda che devi essere registrato nel tuo centro vaccinale per poter inserire eventi avversi");
        panel4.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.BOLD, 72, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-3727837));
        label4.setText("Login");
        panelLogin.add(label4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelLogin.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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
        return panelLogin;
    }

}
