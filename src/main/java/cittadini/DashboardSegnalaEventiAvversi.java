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
    /**
     * <code>panelListaNuoveSegnalazioni</code> rappresenta il pannello di visualizzazione, dedicato alla lista di eventi avversi che l'utente non ha ancora segnalato
     * <p>
     * &egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo alla classe DashboardSegnalaEventiAvversi
     * </p>
     */
    private JPanel panelListaNuoveSegnalazioni;
    /**
     * <code>panelNewReport</code> il pannello principale
     * <p>
     * &egrave; dichiarato <strong>protected</strong> cos&igrave; da poter essere visibile solo alle classi appartenenti al package cittadini
     * </p>
     */
    protected JPanel panelNewReport;
    /**
     * <code>btnInviaSegnalazione</code> rappresenta il bottone che permette all'utente d'inviare la/le segnalazione/i
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JButton btnInviaSegnalazione;
    /**
     * <code>panelLogo</code> rappresenta un pannello per inserire il logo
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JPanel panelLogo;
    /**
     * <code>lbInfo</code> rappresenta una label che mostra le istruzioni per la segnalazione di un evento avverso
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private JLabel lbInfo;
    /**
     * <code>listEvento</code> rappresenter&agrave; la lista di eventi non ancora segnalati dall'utente
     * <p>
     * &egrave; dichiarata <strong>protected</strong> in quanto l'attributo &egrave; utilizzabile nel package cittadini
     * </p>
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve:
     * in questo caso serve per ricavare una lista degli eventi avversi non ancora segnalati
     */
    protected static List<EventoAvversoPerLista> listEvento = new ArrayList<>();
    /**
     * <code>listApproveEvento</code> rappresenta la lista di eventi approvati, in fase di controllo
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private final List<EventoAvversoPerLista> listApproveEvento = new ArrayList<>();
    /**
     * <code>listErrorEvento</code> rappresenta la lista di eventi non approvati, in fase di controllo
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private final List<EventoAvversoPerLista> listErrorEvento = new ArrayList<>();
    /**
     * <code>listEventoNonToccato</code> rappresenta la lista di eventi avversi non presi in considerazione dall'utente
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * </p>
     */
    private final List<EventoAvversoPerLista> listEventoNonToccato = new ArrayList<>();

    /**
     * Costruttore della classe
     */
    public DashboardSegnalaEventiAvversi() {

        btnInviaSegnalazione.addActionListener(e -> {

            if (JOptionPane.showOptionDialog(null, "Confermi di voler segnalare gli eventi avversi?",
                    "Inoltra eventi avversi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, null) == JOptionPane.YES_OPTION) {

                checkBeforeSend();

                if (listEventoNonToccato.size() == listEvento.size()) { // se l'utente non ha segnalato niente
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
     * <code>chekListEventoCauseIsStatic</code> &egrave; un metodo che provvede a cancellare pulire <code>listEvento</code> se esso &egrave; maggiore di zero.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     */
    private void chekListEventoCauseIsStatic() {
        if (listEvento.size() > 0)
            listEvento.clear();
    }

    /**
     * <code>checkBeforeSend</code> &egrave; un metodo che verifica gli eventi avversi inseriti dall'utente
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
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
                listEventoNonToccato.add(elem);
        }
    }

    /**
     * <code>firstLetter</code> &egrave; un metodo che controlla il primo carattere de stringa
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param input la nota di un evento avverso
     * @return restituisce TRUE se il primo carattere &egrave; uno SPACECHAR, FALSE altrimenti
     */
    private boolean firstLetter(String input) {
        return Character.isSpaceChar(input.charAt(0));
    }

    /**
     * <code>clearLists</code> &egrave; un metodo per la pulizia delle liste impiegate nella fase di controllo
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     */
    private void clearLists() {
        if (!listApproveEvento.isEmpty())
            listApproveEvento.clear();
        if (!listErrorEvento.isEmpty())
            listErrorEvento.clear();
        if (!listEventoNonToccato.isEmpty())
            listEventoNonToccato.clear();
    }

    /**
     * <code>sendToServer</code> &egrave; un metodo per l'invio degli eventi avversi segnalati al server
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
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
     * <code>jOptionPanelMessageDialog</code> &egrave; un metodo per la costruzione del Dialog message
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param message messaggio da mostrare nel Dialog message
     * @param title   titolo del Dialog message
     */
    private void jOptionPanelMessageDialog(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * <code>createUIComponents</code> &egrave; una procedura per impostare la grafica
     * quando viene caricato il frame
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     *
     * @throws IOException &egrave; utilizzata quando si verificano errori nelle fasi di input e di output
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
