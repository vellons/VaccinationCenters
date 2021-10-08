package cittadini;


/*
Registra cittadino presso un centro vaccinale.

1) costruire la gui
    - simile all'interfaccia che ha fatto mahdi
    - aggiungiamo il bottone a fianco al campo id univoco (custom in seguito)


2) come fa il programma a capire se c'è l'utente nella tabella dei vaccinati?
Operazione di select sulla tabella dei vaccinati. Where idUnivoco = ID inserito
    - se c'è match allora continuo con la registrazione; altrimenti se non c'è nella tabella: id inserito in modo sbagliato o id inesistente (JoptionPanel)

3)Compilare gli altri campi vuoti: email e password)

4) effettuare una query di aggiornamento sulla tabella dei vaccinati -> tupla dell'utente corrente

 */

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.Vaccinato;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class RegistraCitt {

    public JPanel panelCopletaRegistrazione;
    private JPanel panelLogo;
    private JLabel lblIdUnivoco;
    private JTextField tfIdUnivoco;
    private JLabel lblNome;
    private JTextField tfNome;
    private JLabel lblCognome;
    private JTextField tfCognome;
    private JLabel lblCodiceFiscale;
    private JTextField tfCodiceFiscale;
    private JLabel lblEmail;
    private JTextField tfEmail;
    private JLabel lblErrors;
    private JButton btnRegistraVaccinato;
    private JPanel panelLogo2;
    private JLabel lblPassword;
    private JPasswordField tfPassword;
    private JButton btnCercaCittadino;


    public RegistraCitt() {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();

        btnCercaCittadino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(tfIdUnivoco.getText());
                    Vaccinato userVax = db.getVaccinatoByIDUnique(tfIdUnivoco.getText());
                    if (userVax != null) {
                        tfNome.setText(userVax.getNome());
                    }

                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    private void createUIComponents() throws IOException {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelLogo2 = new JPanel();
        BufferedImage myPicture2 = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        panelLogo2.add(picLabel2);
    }

}
