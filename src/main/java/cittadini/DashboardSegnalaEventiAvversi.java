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
    private JLabel lbInfo;
    protected static List<EventoAvversoPerLista> listEvento = new ArrayList<>();
    private final List<EventoAvversoPerLista> listFinalEvento = new ArrayList<>();
    private final List<EventoAvversoPerLista> listErrorEvento = new ArrayList<>();
    private final List<EventoAvversoPerLista> listNessunEvento = new ArrayList<>();

    public DashboardSegnalaEventiAvversi() {

        btnInviaSegnalazione.addActionListener(e -> {

            if (JOptionPane.showOptionDialog(null, "Confermi di voler segnalare gli eventi avversi?",
                    "Inoltra eventi avversi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null) == JOptionPane.YES_OPTION) {

                checkBeforeSend();

                if (listNessunEvento.size() == listEvento.size()) { // se l'utente non ha segnalato niente
                    jOptionPanelMessageDialog("Non hai segnalato nessun evento. Se non vuoi segnalare,\nchiudi la finestra.", "Nessun evento avverso segnalato");
                } else { // se l'utente ha segnalato qualcosa
                    //   if (!(listErrorEvento.size() > 0)) { // se non ci sono elementi (considerati errori), inoltro al server gli eventi avversi
                    sendToServer();
                    jOptionPanelMessageDialog("Segnalazione inviato con successo", "Invio riuscito");
                    Cittadini.closePreviousWindow(DashboardEventiAvversiElenco.segnalaEventiAvversiFrame);
                    chekListEventoCauseIsStatic();
                    try {
                        Cittadini.reloadDashboardEventiAvversiElenco(Login.elencoEventiAvversi);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //   } else
                    //     jOptionPanelMessageDialog("", "Invio fallito");
                }
            }
        });
    }

    private void chekListEventoCauseIsStatic() {
        if (listEvento.size() > 0)
            listEvento.clear();
    }

    private void checkBeforeSend() {
        clearLists();

        for (EventoAvversoPerLista elem : listEvento) {
            if (elem.getValoreSeverita() > 0) {
                // if (elem.getNota().isEmpty())
                //     listErrorEvento.add(elem);
                //  else
                listFinalEvento.add(elem);
            } else
                listNessunEvento.add(elem);
        }
    }

    private void clearLists() {
        if (!listFinalEvento.isEmpty())
            listFinalEvento.clear();
        if (!listErrorEvento.isEmpty())
            listErrorEvento.clear();
        if (!listNessunEvento.isEmpty())
            listNessunEvento.clear();
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
