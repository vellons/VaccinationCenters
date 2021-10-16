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

    public Login() {

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblErrors.setText("");
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
                        System.out.println(v);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

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
