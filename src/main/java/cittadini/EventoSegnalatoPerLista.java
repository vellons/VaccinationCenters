package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.EventoAvverso;
import models.TipologiaEvento;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EventoSegnalatoPerLista serve per gestire un elemento della lista di eventi avversi segnalati dagli utenti.
 *
 * @author Vellons
 * @see EventoAvverso
 */
public class EventoSegnalatoPerLista {

    /**
     * <code>panelEventoSegnalatoPerLista</code> &egrave; un panel
     *
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    public JPanel panelEventoSegnalatoPerLista;

    /**
     * <code>lbSeverita</code> &egrave; label contenente la severit&agrave; da 1 a 5
     *
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lbSeverita;

    /**
     * <code>lblNote</code> &egrave; una label contenente le note inserite dall'utente
     *
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblNote;

    /**
     * <code>lbTipologiaEventoAvverso</code> &egrave; una label contenente il nome della tipologia di evento avverso
     *
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * @see EventoAvverso
     */
    private JLabel lbTipologiaEventoAvverso;

    /**
     * <code>btnModificaEventoSegnalato</code> &egrave; un bottone
     *
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnModificaEventoSegnalato;

    /**
     * <code>tipologie</code> &egrave; un ArrayList che contiene le tipologie di eventi avversi
     *
     * &egrave; dichiarata <strong>protected</strong> in quanto l'attributo &egrave;utilizzabile anche da altre classi dello stesso package
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    protected static List<TipologiaEvento> tipologie = new ArrayList<>();

    /**
     * Costruttore della classe
     *
     * @param eventoAvverso oggetto contenente le informazioni sull'evento avverso da mostrare
     */
    public EventoSegnalatoPerLista(EventoAvverso eventoAvverso) {

        // Prendo le tipologie di eventi avversi
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologieEventi();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        String nomeEvento = getNomeTipologiaEventoAvverso(eventoAvverso.getTipologia_evento_id());
        StringBuilder eventoAvversoNome = new StringBuilder();
        lbTipologiaEventoAvverso.setText(String.valueOf(eventoAvversoNome.append(nomeEvento.substring(0, 1).toUpperCase()).append(nomeEvento.substring(1))));
        lbSeverita.setText("Severità: " + eventoAvverso.getSeverita());
        lblNote.setText(eventoAvverso.getNote());

        btnModificaEventoSegnalato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Login.dashboardEventiAvversiElenco.startModificaPanel(eventoAvverso);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * <code>getNomeTipologiaEventoAvverso</code> serve per recuperare il nome della tipologia di evento avverso.
     *
     * @param id identificativo a database dell'evento avverso
     * @return stringa riferita al nome dell'evento avverso
     */
    private String getNomeTipologiaEventoAvverso(int id) {
        for (TipologiaEvento obj : tipologie) {
            if (obj.getId() == id) return obj.getNome();
        }
        return "";
    }
}
