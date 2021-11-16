package cittadini;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import global.DatabaseCVInterface;
import global.ServerConnectionSingleton;
import models.EventoAvverso;
import models.TipologiaEvento;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * La classe EventoSegnalatoPerLista serve per gestire un elemento della lista di eventi avversi segnalati dagli utenti.
 *
 * @author Vellons
 * @see EventoAvverso
 */
public class EventoSegnalatoPerLista {

    /**
     * <code>panelEventoSegnalatoPerLista</code> &egrave; un panel
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    public JPanel panelEventoSegnalatoPerLista;

    /**
     * <code>lbSeverita</code> &egrave; label contenente la severit&agrave; da 1 a 5
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lbSeverita;

    /**
     * <code>lblNote</code> &egrave; una label contenente le note inserite dall'utente
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JLabel lblNote;

    /**
     * <code>lbTipologiaEventoAvverso</code> &egrave; una label contenente il nome della tipologia di evento avverso
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     *
     * @see EventoAvverso
     */
    private JLabel lbTipologiaEventoAvverso;

    /**
     * <code>btnModificaEventoSegnalato</code> &egrave; un bottone
     * <p>
     * &egrave; dichiarato <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */
    private JButton btnModificaEventoSegnalato;

    /**
     * <code>tipologie</code> &egrave; un ArrayList che contiene le tipologie di eventi avversi
     * <p>
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
        panelEventoSegnalatoPerLista = new JPanel();
        panelEventoSegnalatoPerLista.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        panelEventoSegnalatoPerLista.setBackground(new Color(-723724));
        panelEventoSegnalatoPerLista.setPreferredSize(new Dimension(420, 150));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-723724));
        panelEventoSegnalatoPerLista.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbSeverita = new JLabel();
        lbSeverita.setBackground(new Color(-723724));
        lbSeverita.setForeground(new Color(-16777216));
        lbSeverita.setText("Severità: -");
        panel1.add(lbSeverita, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblNote = new JLabel();
        lblNote.setBackground(new Color(-723724));
        Font lblNoteFont = this.$$$getFont$$$(null, Font.ITALIC, -1, lblNote.getFont());
        if (lblNoteFont != null) lblNote.setFont(lblNoteFont);
        lblNote.setForeground(new Color(-7368817));
        lblNote.setText("Note");
        panel1.add(lblNote, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbTipologiaEventoAvverso = new JLabel();
        Font lbTipologiaEventoAvversoFont = this.$$$getFont$$$(null, Font.BOLD, 16, lbTipologiaEventoAvverso.getFont());
        if (lbTipologiaEventoAvversoFont != null) lbTipologiaEventoAvverso.setFont(lbTipologiaEventoAvversoFont);
        lbTipologiaEventoAvverso.setForeground(new Color(-16777216));
        lbTipologiaEventoAvverso.setText("Nome evento avverso");
        panel1.add(lbTipologiaEventoAvverso, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnModificaEventoSegnalato = new JButton();
        btnModificaEventoSegnalato.setActionCommand("Cerca");
        btnModificaEventoSegnalato.setBackground(new Color(-3727837));
        btnModificaEventoSegnalato.setBorderPainted(false);
        btnModificaEventoSegnalato.setEnabled(true);
        btnModificaEventoSegnalato.setFocusPainted(false);
        btnModificaEventoSegnalato.setForeground(new Color(-1));
        btnModificaEventoSegnalato.setOpaque(true);
        btnModificaEventoSegnalato.setText("Modifica");
        panel1.add(btnModificaEventoSegnalato, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(250, -1), 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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
        return panelEventoSegnalatoPerLista;
    }
}
