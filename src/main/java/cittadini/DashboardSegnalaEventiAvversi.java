package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.EventoAvverso;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class DashboardSegnalaEventiAvversi {
    private JPanel panelListaNuoveSegnalazioni;
    public JPanel panelNewReport;
    private JButton btnInviaSegnalazione;
    private JPanel panelLogo;
    protected static List<EventoAvversoPerLista> listEvento = new ArrayList<>();


    public DashboardSegnalaEventiAvversi() {


        btnInviaSegnalazione.addActionListener(e -> {
            System.out.println("TODO: CLICK");

            if (JOptionPane.showOptionDialog(null, "Confermi di voler segnalare gli eventi avversi?",
                    "Inoltra eventi avversi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null) == JOptionPane.YES_OPTION) {
               // checkBeforeSend();
                sendToServer();
            }
        });
    }

    private void sendToServer() {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
        for (EventoAvversoPerLista elem : listEvento) {
            try {
                EventoAvverso ea = new EventoAvverso(Login.utenteLoggato.getId(), elem.getTipologiaEvento().getId(), elem.getValoreSeverita(), elem.getNota());
                db.inserisciNuovoEventoAvversoCittadino(ea);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

/*    private void checkBeforeSend() {
        for (EventoAvversoPerLista elem : listEvento) {
            if (elem.getValoreSeverita() > 0) {
                if (elem.getNota().isEmpty()) {
                    jOptionPanelMessageDialog("Per favore, inserire una nota per l'evento avverso " + elem.getTipologiaEvento().getNome(), "Nota non disponibile");
                    break;
                }
            }
            else {
                sendToServer();
            }
        }
    }*/

    private void jOptionPanelMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    private void createUIComponents() throws IOException {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelListaNuoveSegnalazioni = new JPanel();
        panelListaNuoveSegnalazioni.add(new ListaSegnalaEventiAvversiPanel());
    }
}
