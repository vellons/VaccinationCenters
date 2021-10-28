package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.EventoAvverso;
import models.TipologiaEvento;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class EventoSegnalatoPerLista {
    public JPanel panelEventoSegnalatoPerLista;
    private JLabel lbSeverita;
    private JLabel lblNote;
    private JLabel lbTipologiaEventoAvverso;
    private JButton btnModificaEventoSegnalato;

    /**
     * <code>tipologie</code> &egrave; un ArrayList che contiene le tipologie di eventi avversi
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave;utilizzabile anche da altre classi dello stesso package
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    protected static List<TipologiaEvento> tipologie = new ArrayList<>();

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
    }

    private String getNomeTipologiaEventoAvverso(int id) {
        for (TipologiaEvento obj : tipologie) {
            if (obj.getId() == id) return obj.getNome();
        }
        return "";
    }
}
