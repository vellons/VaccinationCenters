package cittadini;

import global.DatabaseCVInterface;
import global.JTextFieldCharLimit;
import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

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
     * <code>panelLogo2</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie una parte del logo dell'appicazione.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelLoginLogo2;

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

    public Login() {

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
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                try {
                    Vaccinato v = db.getVaccinatoByEmailAndPasswordSha(tfEmail.getText(), tfPassword.getText());
                    if (v == null) {
                        lblErrors.setFont(new Font("Default", Font.BOLD, 14));
                        lblErrors.setText("Email o password errata");
                        lblErrors.setForeground(Color.RED);
                    } else {
                        utenteLoggato = v;
                        tfEmail.setText("");
                        tfPassword.setText("");
                        openDashElencoEventiAvversi();
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
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
            elencoEventiAvversi.setContentPane(new DashboardEventiAvversiElenco().panelDashboardCentriVaccinaliElenco);
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
        panelLoginLogo2 = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/Cittadini.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelLoginLogo2.add(picLabel2);

        tfEmail = new JTextField();
        tfPassword = new JPasswordField();
        tfEmail.setDocument(new JTextFieldCharLimit(40));
        tfPassword.setDocument(new JTextFieldCharLimit(20));
    }
}
