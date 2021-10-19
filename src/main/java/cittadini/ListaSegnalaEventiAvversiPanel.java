package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.EventoAvverso;
import models.TipologiaEvento;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ListIterator;

import static javax.swing.BorderFactory.createEmptyBorder;

public class ListaSegnalaEventiAvversiPanel extends JPanel {
    /**
     * <code>mainList</code> &egrave; un pannello Swing che contiene l'elenco dei centri vaccinali
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     */
    public JPanel mainList;

    public ListaSegnalaEventiAvversiPanel() throws RemoteException {
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

        List<EventoAvverso> list = db.getEventiAvversiCV(Login.utenteLoggato.getId());
        List<TipologiaEvento> listTipologiaEvento = db.getTipologieEventi();

        if (list.size() == 0) { // entro se l'utente non ha mai segnalato nessun evento avverso
            for (TipologiaEvento obj : listTipologiaEvento) {
                aggiungiEventoAvverso(obj);
            }
        } else { // entro se l'utente ha già segnalato almeno un evento
            // in questo caso elimino le tipologie di eventi avversi già segnalati in precedenza.
            ListIterator<TipologiaEvento> i = listTipologiaEvento.listIterator();
            while (i.hasNext()) {
                int tmp = i.next().getId();
                for (EventoAvverso ea : list) {
                    if (tmp == ea.getTipologia_evento_id())
                        i.remove();
                }
            }

            while (i.hasPrevious()) { // leggo al contrario la lista
                TipologiaEvento obj = i.previous();
                System.out.println(obj.toString());
                aggiungiEventoAvverso(obj);
            }
        }
    }

    /**
     * @return oggetto Dimension con le dimensioni della finestra
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    private void aggiungiEventoAvverso(TipologiaEvento tipologiaEvento) {
        JPanel panel = new JPanel();
        panel.add(new EventoAvversoPerLista(tipologiaEvento).panelEventoAvversoPerLista);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc, 0);
        validate();
        repaint();
    }
}