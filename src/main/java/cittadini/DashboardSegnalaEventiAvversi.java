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
    private final List<EventoAvversoPerLista> listFinalEvento = new ArrayList<>();


    public DashboardSegnalaEventiAvversi() {

        btnInviaSegnalazione.addActionListener(e -> {

            if (JOptionPane.showOptionDialog(null, "Confermi di voler segnalare gli eventi avversi?",
                    "Inoltra eventi avversi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null) == JOptionPane.YES_OPTION) {
                if (!listFinalEvento.isEmpty())
                    listFinalEvento.clear();
                boolean isAllOk = checkBeforeSend();
                if (isAllOk) {
                    sendToServer();
                    jOptionPanelMessageDialog("Segnalazione inviato con successo", "Invio riuscito");
                    Cittadini.closePreviousWindow(DashboardEventiAvversiElenco.segnalaEventiAvversiFrame);
                    try {
                        Cittadini.reloadDashboardEventiAvversiElenco(Login.elencoEventiAvversi);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else
                    jOptionPanelMessageDialog("Eventi avversi non inviati. Per favore inserire le note per ogni evento avverso evento avverso segnalato, con severità diversa da 0", "Invio fallito");
            }
        });
    }

    private boolean checkBeforeSend() {
        boolean check = false;
        for (EventoAvversoPerLista elem : listEvento) {
            if (elem.getValoreSeverita() > 0) {
                if (elem.getNota().isEmpty()) {
                    check = false;
                } else {
                    check = true;
                    listFinalEvento.add(elem);
                }
            }
        }
        return check;
    }

    private void sendToServer() {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
        for (EventoAvversoPerLista elem : listFinalEvento) {
            try {
                EventoAvverso ea = new EventoAvverso(Login.utenteLoggato.getId(), elem.getTipologiaEvento().getId(), elem.getValoreSeverita(), elem.getNota().trim());
                db.inserisciNuovoEventoAvversoCittadino(ea);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

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
