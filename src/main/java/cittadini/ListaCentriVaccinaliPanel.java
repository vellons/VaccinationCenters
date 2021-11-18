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
import models.CentroVaccinale;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * La classe ListaCentriVaccinaliPanel serve per mostrare all'interno di un JPanel la lista di tutti i centri vaccinali
 *
 * @author Vellons
 */
public class ListaCentriVaccinaliPanel extends JPanel {

    /**
     * <code>mainList</code> &egrave; un pannello Swing che contiene l'elenco dei centri vaccinali
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     */
    public JPanel mainList;

    /**
     * Costruttore della classe
     *
     * @param filtroNome      &egrave; il filtro per il nome del centro vaccinale
     * @param filtroComune    &egrave; il filtro per il comune del centro vaccinale
     * @param filtroTipologia &egrave; il filtro per la tipologia del centro vaccinale
     */
    public ListaCentriVaccinaliPanel(String filtroNome, String filtroComune, int filtroTipologia) {
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

        String clausolaWhere = "";

        // Caricamento centri vaccinali, se i filtri sono presenti li applico
        if (filtroTipologia != 0) {
            clausolaWhere += "WHERE tipologia_id = '" + filtroTipologia + "'";
        }
        if (!filtroNome.equals("")) {
            if (clausolaWhere.length() == 0) clausolaWhere = "WHERE ";
            else clausolaWhere += " AND ";
            clausolaWhere += "LOWER(nome) LIKE '%" + filtroNome.toLowerCase() + "%'";
        }
        if (!filtroComune.equals("")) {
            if (clausolaWhere.length() == 0) clausolaWhere = "WHERE ";
            else clausolaWhere += " AND ";
            clausolaWhere += "LOWER(indirizzo_comune) LIKE '%" + filtroComune.toLowerCase() + "%'";
        }
        if (clausolaWhere.length() > 0) clausolaWhere += " ";

        DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
        List<CentroVaccinale> centriV = new ArrayList<>();
        try {
            centriV = db.getCentriVaccinali(clausolaWhere);
        } catch (RemoteException e) {
            e.printStackTrace();
            ServerConnectionSingleton.resetConnection();
        }

        // Controllo se dopo aver applicato i filtri ci sono ancora centri vaccinali
        if (centriV.size() > 0) {
            // Scorro la lista rimasta dei centri vaccinali
            for (CentroVaccinale obj : centriV) {
                aggiungiCentroVaccinale(obj);
            }
        } else {
            JPanel panel = new JPanel();
            panel.add(new JLabel("<html><center>Non ci sono centri vaccinali corrispondenti a questi criteri di ricerca." +
                    "<br/>Prova a rimuovere i filtri e schiaccia il tasto \"Applica filtri\"</center></html>"));
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
        return new Dimension(800, 490);
    }

    /**
     * @param cv &egrave; il centro vaccinato che viene passato di cui inserire le informazioni
     */
    private void aggiungiCentroVaccinale(CentroVaccinale cv) {
        JPanel panel = new JPanel();
        panel.add(new CentroVaccinalePerLista(cv).panelCentroVaccinalePerLista);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(panel, gbc, 0);
        validate();
        repaint();
    }
}
