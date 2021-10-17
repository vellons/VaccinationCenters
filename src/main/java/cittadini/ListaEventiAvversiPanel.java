package cittadini;

import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.EventoAvverso;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BorderFactory.createEmptyBorder;

public class ListaEventiAvversiPanel extends JPanel {
    /**
     * <code>mainList</code> &egrave; un pannello Swing che contiene l'elenco dei centri vaccinali
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     */
    public JPanel mainList;

    public ListaEventiAvversiPanel() {
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
    }
}
