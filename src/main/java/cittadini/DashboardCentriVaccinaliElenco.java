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
import models.TipologiaCentroVaccinale;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * La classe DashboardCentriVaccinaliElenco permette l'utilizzo di visualizzare l'elenco dei centri vaccinali
 * con la possibilit&agrave; di filtrarli.
 * *
 * * @author Vellons
 */
public class DashboardCentriVaccinaliElenco extends JFrame {
    /**
     * <code>panelDashboardCentriVaccinaliElenco</code> rappresenta un pannello.
     * <p>
     * &egrave; dichiarato <strong>public</strong> cos&igrave; da poter essere visibile anche alle altre classi
     */
    public JPanel panelDashboardCentriVaccinaliElenco;

    /**
     * <code>panelLogo</code> rappresenta un pannello per inserire il logo
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelLogo;

    /**
     * <code>panelListaCentriVaccinali</code> rappresenta un pannello.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JPanel panelListaCentriVaccinali;

    /**
     * <code>btnApplicaFiltri</code> rappresenta un bottone.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnApplicaFiltri;

    /**
     * <code>cboxTipologia</code> rappresenta un combo box.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JComboBox cboxTipologia;

    /**
     * <code>tfFiltroNomeCentroVaccinale</code> rappresenta un campo di testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfFiltroNomeCentroVaccinale;

    /**
     * <code>tfFiltroComune</code> rappresenta un campo di testo.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JTextField tfFiltroComune;

    /**
     * <code>lblVaccinazioniTotale</code> rappresenta una label per inserire il totale di vaccinati.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblVaccinazioniTotale;

    /**
     * <code>lblVaccinazioniOggi</code> rappresenta una label per inserire il totale di vaccinati nel giorno corrente.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private JLabel lblVaccinazioniOggi;

    /**
     * <code>initialFiltroNome</code> rappresenta il filtro per il nome del centro vaccinale.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private final String initialFiltroNome;

    /**
     * <code>initialFiltroComune</code> rappresenta il filtro per il comune del centro vaccinale.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private final String initialFiltroComune;

    /**
     * <code>initialFiltroTipologia</code> rappresenta il filtro per la tipologia del centro vaccinale.
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private final int initialFiltroTipologia;

    /**
     * <code>tipologie</code> &egrave; un ArrayList che contiene le tipologie di centro vaccinale
     * &egrave; dichiarata <strong>protected</strong> in quanto l'attributo &egrave; utilizzabile anche da altre classi dello stesso package
     * &egrave; dichiarata <strong>static</strong> cos&igrave; da poter riutilizzare il valore quando serve,
     * chiamando solo una volta il server per ottenere l'elenco
     */
    protected static List<TipologiaCentroVaccinale> tipologie = new ArrayList<>();


    /**
     * Costruttore della classe
     *
     * @param filtroNome      &egrave; il filtro che viene applicato al nome di un centro vaccinale, escludendo gli altri
     * @param filtroComune    &egrave; il filtro che viene applicato al comune di un centro vaccinale, escludendo gli altri
     * @param filtroTipologia &egrave; il filtro che viene applicato alla tipologia di un centro vaccinale, escludendo gli altri
     */
    public DashboardCentriVaccinaliElenco(String filtroNome, String filtroComune, int filtroTipologia) throws Exception {
        // Prendo i valori dei filtri precedenti e popolo i filtri
        // Questo succede perch&egrave; faccio il reload di questo oggetto
        initialFiltroNome = filtroNome;
        initialFiltroComune = filtroComune;
        initialFiltroTipologia = filtroTipologia;

        $$$setupUI$$$();
        btnApplicaFiltri.addActionListener(e -> {
            // Ricarico la dashboard dei centri vaccinali passando i filtri
            try {
                Cittadini.reloadDashBoardCentriVaccinaliElencoConFiltri(
                        Cittadini.elencoCentriVaccinali,
                        tfFiltroNomeCentroVaccinale.getText(),
                        tfFiltroComune.getText(),
                        cboxTipologia.getSelectedIndex()
                );
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        try {
            lblVaccinazioniTotale.setText("Totale vaccinati: " +
                    ServerConnectionSingleton.getDatabaseInstance().rowCounterInTable("vaccinati"));
        } catch (RemoteException e) {
            e.printStackTrace();
            ServerConnectionSingleton.resetConnection();
        }
        try {
            lblVaccinazioniOggi.setText("Vaccinati oggi: " +
                    ServerConnectionSingleton.getDatabaseInstance().vaccinatiOggi());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws Exception &egrave; utilizzata quando non si sa che tipo di eccezione potrebbe
     *                   essere sollevata durante l'esecuzione del programma
     */
    private void createUIComponents() throws Exception {
        panelLogo = new JPanel();
        BufferedImage myPicture = ImageIO.read(new File("media/CVLogo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        panelLogo.add(picLabel);

        // Prendo le tipologie di centro vaccinale
        if (tipologie.size() == 0) { // Tipologie è static, recupero i dati dal server solo la prima volta
            try {
                DatabaseCVInterface db = ServerConnectionSingleton.getDatabaseInstance(); // Singleton class con il server
                tipologie = db.getTipologiaCentroVaccinale();
            } catch (RemoteException e) {
                e.printStackTrace();
                ServerConnectionSingleton.resetConnection();
            }
        }
        List<String> tipologieCombo = new ArrayList<>();
        tipologieCombo.add("TUTTI");
        for (TipologiaCentroVaccinale obj : tipologie) {
            tipologieCombo.add(obj.getNome());
        }

        cboxTipologia = new JComboBox(tipologieCombo.toArray());
        cboxTipologia.setSelectedIndex(initialFiltroTipologia);


        // Creo la lista dei centri vaccinali passando i filtri
        panelListaCentriVaccinali = new JPanel();
        panelListaCentriVaccinali.add(new ListaCentriVaccinaliPanel(initialFiltroNome, initialFiltroComune, initialFiltroTipologia));

        // Inizializzo e popolo i valori dei filtri
        tfFiltroNomeCentroVaccinale = new JTextField();
        tfFiltroComune = new JTextField();

        tfFiltroNomeCentroVaccinale.setText(initialFiltroNome);
        tfFiltroComune.setText(initialFiltroComune);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() throws Exception {
        createUIComponents();
        panelDashboardCentriVaccinaliElenco = new JPanel();
        panelDashboardCentriVaccinaliElenco.setLayout(new GridLayoutManager(3, 4, new Insets(10, 10, 10, 10), -1, -1));
        panelDashboardCentriVaccinaliElenco.setBackground(new Color(-723724));
        panelDashboardCentriVaccinaliElenco.setMinimumSize(new Dimension(-1, -1));
        panelDashboardCentriVaccinaliElenco.setPreferredSize(new Dimension(1200, 700));
        panelLogo.setBackground(new Color(-723724));
        panelDashboardCentriVaccinaliElenco.add(panelLogo, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(-1, 200), 0, false));
        panelListaCentriVaccinali.setBackground(new Color(-723724));
        panelListaCentriVaccinali.setForeground(new Color(-723724));
        panelDashboardCentriVaccinaliElenco.add(panelListaCentriVaccinali, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(7, 1, new Insets(30, 30, 30, 30), -1, -1));
        panel1.setBackground(new Color(-2876));
        panelDashboardCentriVaccinaliElenco.add(panel1, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 500), new Dimension(-1, 500), 0, false));
        btnApplicaFiltri = new JButton();
        btnApplicaFiltri.setActionCommand("Applica filtri");
        btnApplicaFiltri.setBackground(new Color(-3727837));
        btnApplicaFiltri.setBorderPainted(false);
        btnApplicaFiltri.setFocusPainted(false);
        btnApplicaFiltri.setFocusable(false);
        btnApplicaFiltri.setForeground(new Color(-1));
        btnApplicaFiltri.setLabel("Applica filtri");
        btnApplicaFiltri.setOpaque(true);
        btnApplicaFiltri.setText("Applica filtri");
        panel1.add(btnApplicaFiltri, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, -1), new Dimension(250, -1), 0, false));
        panel1.add(cboxTipologia, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, -1), null, 0, false));
        panel1.add(tfFiltroNomeCentroVaccinale, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, -1), null, 0, false));
        panel1.add(tfFiltroComune, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(250, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(new Color(-16777216));
        label1.setFocusable(false);
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 15, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-16777216));
        label1.setText("Nome centro vaccinale:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setBackground(new Color(-16777216));
        label2.setFocusable(false);
        Font label2Font = this.$$$getFont$$$("Arial", Font.BOLD, 15, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-16777216));
        label2.setText("Comune:");
        panel1.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-16777216));
        label3.setFocusable(false);
        Font label3Font = this.$$$getFont$$$("Arial", Font.BOLD, 15, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-16777216));
        label3.setText("Tipologia:");
        label3.setVerticalAlignment(0);
        label3.setVerticalTextPosition(0);
        panel1.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblVaccinazioniTotale = new JLabel();
        lblVaccinazioniTotale.setBackground(new Color(-723724));
        lblVaccinazioniTotale.setDoubleBuffered(false);
        lblVaccinazioniTotale.setFocusable(true);
        Font lblVaccinazioniTotaleFont = this.$$$getFont$$$("Arial", Font.BOLD, 26, lblVaccinazioniTotale.getFont());
        if (lblVaccinazioniTotaleFont != null) lblVaccinazioniTotale.setFont(lblVaccinazioniTotaleFont);
        lblVaccinazioniTotale.setForeground(new Color(-1631113));
        lblVaccinazioniTotale.setText("");
        panelDashboardCentriVaccinaliElenco.add(lblVaccinazioniTotale, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelDashboardCentriVaccinaliElenco.add(spacer1, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.BOLD, 72, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-3727837));
        label4.setText("Centri vaccinali");
        panelDashboardCentriVaccinaliElenco.add(label4, new GridConstraints(0, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 100), null, 0, false));
        lblVaccinazioniOggi = new JLabel();
        lblVaccinazioniOggi.setBackground(new Color(-723724));
        lblVaccinazioniOggi.setDoubleBuffered(false);
        lblVaccinazioniOggi.setFocusable(true);
        Font lblVaccinazioniOggiFont = this.$$$getFont$$$("Arial", Font.BOLD, 26, lblVaccinazioniOggi.getFont());
        if (lblVaccinazioniOggiFont != null) lblVaccinazioniOggi.setFont(lblVaccinazioniOggiFont);
        lblVaccinazioniOggi.setForeground(new Color(-1631113));
        lblVaccinazioniOggi.setText("");
        panelDashboardCentriVaccinaliElenco.add(lblVaccinazioniOggi, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return panelDashboardCentriVaccinaliElenco;
    }

}
