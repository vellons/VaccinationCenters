/*
 * Copyright (c) 2021. University Of Insubria, Varese.
 *
 * Authors:
 * - Vellone Alex 741527 VA
 * - Macaj Manuel 741854 VA
 * - Said Ibrahim Mahdi 741512 VA
 * - Pazienza Silvio 741486 VA
 */

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

/**
 * La classe ListaSegnalaEventiAvversiPanel serve per mostrare all'interno di un JPanel la lista di tutti gli eventi
 * avversi che l'utente loggato non ha ancora segnalato
 *
 * @author manuelmacaj
 */
public class ListaSegnalaEventiAvversiPanel extends JPanel {
    /**
     * <code>mainList</code> &egrave; un pannello Swing che contiene l'elenco dei centri vaccinali.
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     */
    public JPanel mainList;

    /**
     * Costruttore della classe
     */
    public ListaSegnalaEventiAvversiPanel() {
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

        List<TipologiaEvento> listTipologiaEvento;
        try {
            listTipologiaEvento = db.getTipologieEventi();
            if (ListaEventiSegnalatiPanel.eventiUtenteCorrente.size() == 0) { // entro se l'utente non ha mai segnalato nessun evento avverso
                for (TipologiaEvento obj : listTipologiaEvento) {
                    aggiungiEventoAvverso(obj);
                }
            } else { // entro se l'utente ha già segnalato almeno un evento
                // in questo caso elimino le tipologie di eventi avversi già segnalati in precedenza.
                try {
                    ListIterator<TipologiaEvento> i = listTipologiaEvento.listIterator();
                    while (i.hasNext()) {
                        int tmp = i.next().getId();
                        for (EventoAvverso ea : ListaEventiSegnalatiPanel.eventiUtenteCorrente) { // La lista è presente ListaEventiSegnalatiPanel, evito di rifare un'altra lettura degli eventi avversi segnalati
                            if (tmp == ea.getTipologia_evento_id())
                                i.remove();
                        }
                    }

                    while (i.hasPrevious()) { // leggo al contrario la lista
                        TipologiaEvento obj = i.previous();
                        System.out.println(obj.toString());
                        aggiungiEventoAvverso(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return oggetto Dimension con le dimensioni della finestra
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    /**
     * <code>aggiungiEventoAvverso</code> &egrave; un metodo che permette di creare un singolo pannello per
     * ogni evento avverso da segnalare.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @param tipologiaEvento &egrave; l'evento avverso che l'utente loggato non ha ancora segnalato
     */
    private void aggiungiEventoAvverso(TipologiaEvento tipologiaEvento) {
        JPanel panel = new JPanel();
        EventoAvversoPerLista avversoPerLista = new EventoAvversoPerLista(tipologiaEvento);
        panel.add(avversoPerLista.panelEventoAvversoPerLista);
        DashboardSegnalaEventiAvversi.listEvento.add(avversoPerLista);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc, 0);
        validate();
        repaint();
    }
}