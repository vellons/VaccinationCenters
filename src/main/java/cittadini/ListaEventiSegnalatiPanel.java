package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.EventoAvverso;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * La classe ListaEventiSegnalatiPanel serve per mostrare all'interno di un JPanel la lista di tutti gli eventi
 * avversi inseriti all'interno del database dall'utente
 *
 * @author Vellons
 */
public class ListaEventiSegnalatiPanel extends JPanel {

    /**
     * <code>mainList</code> &egrave; un pannello Swing che contiene l'elenco degli eventi avversi
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     */
    public JPanel mainList;

    /**
     * <code>eventiUtenteCorrente</code> &egrave; un ArrayList che contiene gli eventi avversi che l'utente ha segnalato
     * &egrave; dichiarata <strong>protected</strong> in quanto l'attributo &egrave; utilizzabile nel package cittadini
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    protected static List<EventoAvverso> eventiUtenteCorrente = new ArrayList<>();

    /**
     * Costruttore della classe
     */
    public ListaEventiSegnalatiPanel() {
        setLayout(new BorderLayout());

        // Inizializzazione panel
        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;
        mainList.add(new JPanel(), gridBagConstraints);
        JScrollPane jScrollPane = new JScrollPane(mainList);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane.setBorder(createEmptyBorder());
        add(jScrollPane);
        validate();
        repaint();

        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server

        try {
            eventiUtenteCorrente = db.getEventiAvversiCittadino(Login.utenteLoggato.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (eventiUtenteCorrente.size() > 0) {
            // Scorro la lista rimasta dei centri vaccinali
            for (EventoAvverso obj : eventiUtenteCorrente) {
                aggiungiEventoSegnalato(obj);
            }
        } else {
            JPanel panel = new JPanel();
            panel.add(new JLabel("<html><center>Non hai segnalato eventi avversi.<br/>" +
                    "Clicca il bottone \"Segnala evento avverso\" per aiutare a monitorare gli eventi avversi.<br/>" +
                    "Se non ti senti bene contatta il tuo medico.</center></html>"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            mainList.add(panel, gbc, 0);
            validate();
            repaint();
        }
    }

    /**
     * @return oggetto Dimension con le dimensioni della finestra
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(450, 490);
    }

    /**
     * @param eventoAvverso &egrave; evento avverso segnalato dall'utente loggato che viene passato
     *                      di cui inserire le informazioni nella lista
     */
    private void aggiungiEventoSegnalato(EventoAvverso eventoAvverso) {
        JPanel panel = new JPanel();
        panel.add(new EventoSegnalatoPerLista(eventoAvverso).panelEventoSegnalatoPerLista);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc, 0);
        validate();
        repaint();
    }
}
