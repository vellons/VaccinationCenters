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

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.CentroVaccinale;
import models.DashboardCentroVaccinale;
import models.TipologiaCentroVaccinale;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * La classe CentroVaccinalePerLista permette il caricamento dei singoli centri vaccinali
 * da caricare nell'elenco dei centri vaccinali.
 *
 * @author Vellons
 * @see ListaCentriVaccinaliPanel
 */

public class CentroVaccinalePerLista extends JPanel {

    /**
     * <code>dettaglioFrame</code> &egrave; una cornice Swing attivata nel momento nel
     * quale si vuole visualizzare le informazioni dettagliate di un singolo
     * centro vaccinale
     *
     * <p>
     * &egrave; dichiarata <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da non doverla istanziare creando un oggetto
     */
    public static JFrame dettaglioFrame = new JFrame("Centri Vaccinali Cittadini - Dettaglio centro vaccinale");

    /**
     * <code>panelCentroVaccinalePerLista</code> &egrave; un pannello Swing che compone
     * l'interfaccia grafica, nella fattispecie uno dei singoli centri vaccinali
     * caricati sulla dashboard
     * <p>
     * &egrave; dichiarato <strong>public</strong> in quanto l'attributo &egrave; utilizzabile all'esterno della classe
     */
    public JPanel panelCentroVaccinalePerLista;

    /**
     * <code>lblNome</code> &egrave; un'etichetta Swing dedicata al campo nome
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblNome;

    /**
     * <code>lblIndirizzo</code> &egrave; un'etichetta Swing dedicata al campo indirizzo
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblIndirizzo;

    /**
     * <code>lblVaccinazioni</code> &egrave; un'etichetta Swing dedicata al campo vaccinazioni
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblVaccinazioni;

    /**
     * <code>btnDettaglio</code> &egrave; un bottone Swing che attiva visualizzazione
     * delle informazioni dettagliate di un centro vaccinale a scelta.
     * <p>
     * Cliccandolo si aprirà un box per visualizzare i dettagli del centro vaccinale.
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnDettaglio;

    /**
     * <code>lblTipologia</code> &egrave; un'etichetta Swing dedicata al campo tipologia
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblTipologia;

    /**
     * <code>lblEventiAvversi</code> &egrave; un'etichetta Swing dedicata al campo degli eventi avversi
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblEventiAvversi;

    /**
     * <code>dashboardData</code> &egrave; un ArrayList che contiene il numero di vaccinati per ogni centro vaccinale,
     * il numero di eventi avversi totale e il numero di persone che hanno avuto eventi avversi.
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    private static List<DashboardCentroVaccinale> dashboardData = new ArrayList<>();

    /**
     * Costruttore della classe
     *
     * @param cv insieme di dati relativi al centro vaccinale da visualizzare
     */
    public CentroVaccinalePerLista(CentroVaccinale cv) {
        if (dashboardData.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                dashboardData = db.getDashboardCVInfo("");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        // Cerco la tipologia di centro vaccinale che combacia
        String tipologia = "";
        for (TipologiaCentroVaccinale obj : DashboardCentriVaccinaliElenco.tipologie) {
            if (cv.getTipologia_id() == obj.getId()) {
                tipologia = obj.getNome();
            }
        }
        lblTipologia.setText("Tipologia: " + tipologia);

        // Cerco le informazioni in dashboardData riferite al numero di vaccini
        String vaccinazioniInfoData = "";
        for (DashboardCentroVaccinale obj : dashboardData) {
            if (cv.getId() == obj.getId()) {
                vaccinazioniInfoData = "Vaccinazioni: " + obj.getVaccinati();
            }
        }
        lblVaccinazioni.setText(vaccinazioniInfoData);

        // Cerco le informazioni in dashboardData riferite al numero di eventi avversi
        String eventiAvversiInfoData = "";
        for (DashboardCentroVaccinale obj : dashboardData) {
            if (cv.getId() == obj.getId()) {
                eventiAvversiInfoData = "Avversità segnalate: " + obj.getSomma_eventi_avversi();
                if (obj.getVaccinati_con_eventi_avversi() == 1) {
                    eventiAvversiInfoData += " (" + obj.getVaccinati_con_eventi_avversi() + " persona coinvolta)";
                } else if (obj.getVaccinati_con_eventi_avversi() > 1) {
                    eventiAvversiInfoData += " (" + obj.getVaccinati_con_eventi_avversi() + " persone coinvolte)";
                }
            }
        }
        lblEventiAvversi.setText(eventiAvversiInfoData);

        // Completo le informazioni del centro vaccinale
        lblNome.setText(cv.getNome().substring(0, Math.min(cv.getNome().length(), 30)));
        lblIndirizzo.setText(cv.getIndirizzoComposto().substring(0, Math.min(cv.getIndirizzoComposto().length(), 60)));

        btnDettaglio.addActionListener(e -> {
            try {
                dettaglioFrame.setContentPane(new DettaglioCentroVaccinale(cv).panelDettaglioCV);
                Cittadini.initUI(dettaglioFrame);
                dettaglioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Definisce il comportamento della finestra
                dettaglioFrame.pack();
                dettaglioFrame.setLocationRelativeTo(null);
                dettaglioFrame.setVisible(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelCentroVaccinalePerLista = new JPanel();
        panelCentroVaccinalePerLista.setLayout(new GridLayoutManager(4, 6, new Insets(15, 15, 15, 15), -1, -1));
        panelCentroVaccinalePerLista.setBackground(new Color(-723724));
        panelCentroVaccinalePerLista.setEnabled(false);
        panelCentroVaccinalePerLista.setForeground(new Color(-723724));
        panelCentroVaccinalePerLista.setOpaque(true);
        panelCentroVaccinalePerLista.setPreferredSize(new Dimension(765, 150));
        panelCentroVaccinalePerLista.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-6579301)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        lblNome = new JLabel();
        lblNome.setBackground(new Color(-723724));
        lblNome.setFocusable(false);
        Font lblNomeFont = this.$$$getFont$$$("Arial", Font.BOLD, 26, lblNome.getFont());
        if (lblNomeFont != null) lblNome.setFont(lblNomeFont);
        lblNome.setForeground(new Color(-16777216));
        lblNome.setText("Nome centro vaccinale");
        panelCentroVaccinalePerLista.add(lblNome, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelCentroVaccinalePerLista.add(spacer1, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        lblIndirizzo = new JLabel();
        lblIndirizzo.setBackground(new Color(-16777216));
        lblIndirizzo.setFocusable(false);
        Font lblIndirizzoFont = this.$$$getFont$$$("Arial", Font.BOLD, 15, lblIndirizzo.getFont());
        if (lblIndirizzoFont != null) lblIndirizzo.setFont(lblIndirizzoFont);
        lblIndirizzo.setForeground(new Color(-16777216));
        lblIndirizzo.setText("Indirizzo");
        panelCentroVaccinalePerLista.add(lblIndirizzo, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblTipologia = new JLabel();
        lblTipologia.setBackground(new Color(-16777216));
        lblTipologia.setFocusable(false);
        Font lblTipologiaFont = this.$$$getFont$$$("Arial", Font.BOLD, 15, lblTipologia.getFont());
        if (lblTipologiaFont != null) lblTipologia.setFont(lblTipologiaFont);
        lblTipologia.setForeground(new Color(-16777216));
        lblTipologia.setText("Tipologia");
        panelCentroVaccinalePerLista.add(lblTipologia, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-723724));
        panel1.setForeground(new Color(-723724));
        panelCentroVaccinalePerLista.add(panel1, new GridConstraints(3, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblVaccinazioni = new JLabel();
        lblVaccinazioni.setBackground(new Color(-723724));
        lblVaccinazioni.setDoubleBuffered(false);
        lblVaccinazioni.setFocusable(true);
        Font lblVaccinazioniFont = this.$$$getFont$$$("Arial", Font.BOLD, 15, lblVaccinazioni.getFont());
        if (lblVaccinazioniFont != null) lblVaccinazioni.setFont(lblVaccinazioniFont);
        lblVaccinazioni.setForeground(new Color(-1631113));
        lblVaccinazioni.setText("Vaccinazioni");
        panel1.add(lblVaccinazioni, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblEventiAvversi = new JLabel();
        lblEventiAvversi.setBackground(new Color(-723724));
        lblEventiAvversi.setDoubleBuffered(false);
        lblEventiAvversi.setFocusable(false);
        Font lblEventiAvversiFont = this.$$$getFont$$$("Arial", Font.BOLD, 15, lblEventiAvversi.getFont());
        if (lblEventiAvversiFont != null) lblEventiAvversi.setFont(lblEventiAvversiFont);
        lblEventiAvversi.setForeground(new Color(-13800));
        lblEventiAvversi.setText("Eventi avversi");
        panel1.add(lblEventiAvversi, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDettaglio = new JButton();
        btnDettaglio.setBackground(new Color(-3727837));
        btnDettaglio.setBorderPainted(false);
        btnDettaglio.setFocusPainted(false);
        btnDettaglio.setFocusable(false);
        btnDettaglio.setForeground(new Color(-1));
        btnDettaglio.setLabel("Dettaglio");
        btnDettaglio.setOpaque(true);
        btnDettaglio.setText("Dettaglio");
        panelCentroVaccinalePerLista.add(btnDettaglio, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), new Dimension(250, -1), 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelCentroVaccinalePerLista;
    }
}
