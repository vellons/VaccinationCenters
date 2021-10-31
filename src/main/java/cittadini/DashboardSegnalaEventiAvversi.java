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

/**
 * La classe DashboardSegnalaEventiAvversi permette d'inviare gli eventi avversi non ancora segnalati dall'utente
 *
 * @author manuelmacaj
 */
public class DashboardSegnalaEventiAvversi {
    private JPanel panelListaNuoveSegnalazioni;
    public JPanel panelNewReport;
    private JButton btnInviaSegnalazione;
    private JPanel panelLogo;
    private JLabel lbInfo;
    protected static List<EventoAvversoPerLista> listEvento = new ArrayList<>();
    private final List<EventoAvversoPerLista> listApproveEvento = new ArrayList<>();
    private final List<EventoAvversoPerLista> listErrorEvento = new ArrayList<>();
    private final List<EventoAvversoPerLista> listNessunEvento = new ArrayList<>();

    /**
     * Costruttore della classe
     */
    public DashboardSegnalaEventiAvversi() {

        btnInviaSegnalazione.addActionListener(e -> {

            if (JOptionPane.showOptionDialog(null, "Confermi di voler segnalare gli eventi avversi?",
                    "Inoltra eventi avversi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null) == JOptionPane.YES_OPTION) {

                checkBeforeSend();

                if (listNessunEvento.size() == listEvento.size()) { // se l'utente non ha segnalato niente
                    jOptionPanelMessageDialog("Non hai segnalato nessun evento. Se non vuoi segnalare,\nchiudi la finestra.", "Nessun evento avverso segnalato");
                } else { // se l'utente ha segnalato qualcosa
                    if (!(listErrorEvento.size() > 0)) { // se non ci sono elementi (considerati errori), inoltro al server gli eventi avversi
                        sendToServer();
                        jOptionPanelMessageDialog("Segnalazione inviato con successo", "Invio riuscito");
                        Cittadini.closePreviousWindow(DashboardEventiAvversiElenco.segnalaEventiAvversiFrame);
                        chekListEventoCauseIsStatic();
                        try {
                            Cittadini.reloadDashboardEventiAvversiElenco(Login.elencoEventiAvversi);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        jOptionPanelMessageDialog("Attenzione: le note non devono contenere spazi iniziali. Correggere e riprovare", "Invio fallito");
                    }
                }
            }
        });
    }

    /**
     * Metodo che provvede a cancellare pulire <code>listEvento</code> se esso &egrave; maggiore di zero
     */
    private void chekListEventoCauseIsStatic() {
        if (listEvento.size() > 0)
            listEvento.clear();
    }

    /**
     * Metodo che verifica gli eventi avversi inseriti dall'utente
     */
    private void checkBeforeSend() {
        clearLists();

        for (EventoAvversoPerLista elem : listEvento) {
            if (elem.getValoreSeverita() > 0) { // se l'utente ha spostato lo slider di un evento avverso, verifico la nota
                if (!elem.getNota().isEmpty()) { // se la nota contiene qualcosa...
                    boolean res = firstLetter(elem.getNota()); //... verifico la stringa
                    if (res) // se composto da spazi...
                        listErrorEvento.add(elem); //... inserisco nella lista dedicata agli eventi avversi non approvati
                    else // altrimenti...
                        listApproveEvento.add(elem); // ...aggiungo nella lista degli eventi avversi approvati
                } else // se l'utente non è interessato ad aggiungere una nota a riguardo di un particolare evento avverso//...
                    listApproveEvento.add(elem); //  ...aggiungo nella lista degli eventi avversi approvati
            } else
                listNessunEvento.add(elem);
        }
    }

    /**
     * Metodo che controlla il primo carattere de stringa
     *
     * @param input la nota di un evento avverso
     * @return restituisce TRUE se il primo carattere &egrave; uno SPACECHAE, FALSE altrimenti
     */
    private boolean firstLetter(String input) {
        return Character.isSpaceChar(input.charAt(0));
    }

    /**
     * Metodo per la pulizia delle liste
     */
    private void clearLists() {
        if (!listApproveEvento.isEmpty())
            listApproveEvento.clear();
        if (!listErrorEvento.isEmpty())
            listErrorEvento.clear();
        if (!listNessunEvento.isEmpty())
            listNessunEvento.clear();
    }

    /**
     * Metodo per l'invio degli eventi avversi al server
     */
    private void sendToServer() {
        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance();
        for (EventoAvversoPerLista elem : listApproveEvento) {
            try {
                EventoAvverso ea = new EventoAvverso(Login.utenteLoggato.getId(), elem.getTipologiaEvento().getId(), elem.getValoreSeverita(), elem.getNota().trim());
                db.inserisciNuovoEventoAvversoCittadino(ea);
            } catch (RemoteException ex) {
                ex.printStackTrace();
                ServerConnectionSingleton.resetConnection();
            }
        }
    }

    /**
     * Metodo per la costruzione del Dialog message
     *
     * @param message messaggio da mostrare nel Dialog message
     * @param title   titolo del Dialog message
     */
    private void jOptionPanelMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Metodo utilizzato per quei componenti grafici che presenta la custom create a TRUE
     *
     * @throws IOException un eccezione che viene lanciata quando il programma non
     *                     trova il file che si vuole utilizzare
     */
    private void createUIComponents() throws IOException {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/ItaliaRinasce.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);
        panelListaNuoveSegnalazioni = new JPanel();
        panelListaNuoveSegnalazioni.add(new ListaSegnalaEventiAvversiPanel());
    }
}
