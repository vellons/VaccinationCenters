package cittadini;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import global.JTextFieldCharLimit;
import models.TipologiaEvento;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Locale;

/**
 * La classe EventoAvversoPerLista permette creare un pannello
 * di un evento avverso non ancora segnalato dall'utente vaccinato.
 *
 * @author manuelmacaj
 */
public class EventoAvversoPerLista {
    /**
     * <code>panelEventoAvversoPerLista</code> rappresenta un pannello su cui verr&agrave; costruita un'interfaccia per ogni evento avverso da segnalare.
     * <p>
     * &Egrave; dichiarato <strong>protected</strong> cos&igrave; da poter essere visibile solo nel package cittadini
     * </p>
     */
    protected JPanel panelEventoAvversoPerLista;
    /**
     * <code>lbSeverita</code> rappresenta una label in cui verr&agrave; mostrato il livello di severit&agrave; di un evento avverso.
     * <p>
     * &Egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo nella classe EventoAvversoPerLista
     * </p>
     */
    private JLabel lbSeverita;
    /**
     * <code>lbScriviCommento</code> rappresenta una label che mostra solo il seguente testo: "Note".
     * <p>
     * &egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo nella classe EventoAvversoPerLista
     * </p>
     */
    private JLabel lbScriviCommento;
    /**
     * <code>sliderServerita</code> rappresenta uno slider che permette all'utente di selezionare il livello di severit&agrave; di un evento avverso.
     * <p>
     * &egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo nella classe EventoAvversoPerLista
     * </p>
     */
    private JSlider sliderServerita;
    /**
     * <code>lbCounterCharacter</code> rappresenta una label in cui viene mostrato il conteggio dei caratteri della textArea (max 256).
     * <p>
     * &egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo nella classe EventoAvversoPerLista
     * </p>
     */
    private JLabel lbCounterCharacter;
    /**
     * <code>txtNote</code> rappresenta una textArea che permette all'utente d'inserire una nota a riguardo di un evento avverso.
     * <p>
     * &Egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo nella classe EventoAvversoPerLista
     * </p>
     */
    private JTextArea txtNote;
    /**
     * <code>lbTipologiaEventoAvverso</code> rappresenta una label che mostra nome dell'evento avverso da segnalare.
     * <p>
     * &Egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo alla classe classe EventoAvversoPerLista
     * </p>
     */
    private JLabel lbTipologiaEventoAvverso;

    /**
     * <code>tipologiaEvento</code> rappresenta un oggetto di tipo TipologiaEventoAvverso che permette di accedere alle informazioni inerenti a una tipologia di evento avverso (id, nome evento avverso).
     * <p>
     * &Egrave; dichiarato <strong>private</strong> cos&igrave; da poter essere visibile solo nella classe EventoAvversoPerLista
     * </p>
     */
    private final TipologiaEvento tipologiaEvento;

    /**
     * Costruttore della classe.
     *
     * @param tipologiaEvento rappresenta la tipologia di evento avverso che l'utente non ha mai segnalato
     */
    public EventoAvversoPerLista(TipologiaEvento tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;

        StringBuilder eventoAvversoNome = new StringBuilder();
        $$$setupUI$$$();
        lbTipologiaEventoAvverso.setText(String.valueOf(eventoAvversoNome.append(this.tipologiaEvento.getNome().substring(0, 1).toUpperCase()).append(this.tipologiaEvento.getNome().substring(1))));
        txtNote.setEnabled(false);

        sliderServerita.addChangeListener(e -> {
            lbSeverita.setText("Severità: " + sliderServerita.getValue());
            txtNote.setEnabled(sliderServerita.getValue() > 0);
        });

        txtNote.setDocument(new JTextFieldCharLimit(256));
        txtNote.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                lbCounterCharacter.setText("Caratteri: " + txtNote.getText().length() + "/256");
            }
        });

        txtNote.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (!(Character.isLetter(e.getKeyChar())) && !(Character.isDigit(e.getKeyChar())) && !(Character.isSpaceChar(e.getKeyChar())) && !(Character.valueOf(e.getKeyChar()).toString().equals("'")) && !(Character.valueOf(e.getKeyChar()).toString().equals("°")) && !(Character.valueOf(e.getKeyChar()).toString().equals(".")) && !(Character.valueOf(e.getKeyChar()).toString().equals(":")) && !(Character.valueOf(e.getKeyChar()).toString().equals(";")) && !(Character.valueOf(e.getKeyChar()).toString().equals(",")) && !(Character.valueOf(e.getKeyChar()).toString().equals("?")) && !(Character.valueOf(e.getKeyChar()).toString().equals("!")))
                    e.consume();
            }
        });
    }

    /**
     * <code>getTipologiaEvento</code> &egrave; un metodo per la restituzione della tipologia dell'evento avverso.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @return restituisce la tipologia evento avverso
     */
    public TipologiaEvento getTipologiaEvento() {
        return tipologiaEvento;
    }

    /**
     * <code>getValoreSeverita</code> &egrave; un metodo per la restituzione della severit&agrave;.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @return restituisce il valore della severit&agrave;
     */
    public int getValoreSeverita() {
        return sliderServerita.getValue();
    }

    /**
     * <code>getNota</code> &egrave; un metodo per la restituzione delle note.
     * <br>&Egrave; dichiarato <strong>private</strong> in quanto metodo utilizzato solo all'interno della classe
     *
     * @return restituisce la nota che l'utente ha scritto
     */
    public String getNota() {
        return txtNote.getText();
    }

    /**
     * <code>createUIComponents</code> &egrave; una procedura per impostare la grafica
     * quando viene caricato il frame.<br>
     * &egrave; dichiarato <strong>void</strong> in quanto non restituisce alcun valore
     */
    private void createUIComponents() {
        sliderServerita = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
        sliderServerita.setMinorTickSpacing(1);
        sliderServerita.setPaintLabels(true);
        sliderServerita.setPaintTicks(true);

        // Add positions label in the slider
        Hashtable<Integer, JLabel> position = new Hashtable<>();
        position.put(0, new JLabel("0"));
        position.put(1, new JLabel("1"));
        position.put(2, new JLabel("2"));
        position.put(3, new JLabel("3"));
        position.put(4, new JLabel("4"));
        position.put(5, new JLabel("5"));
        // Set the label to be drawn
        sliderServerita.setLabelTable(position);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panelEventoAvversoPerLista = new JPanel();
        panelEventoAvversoPerLista.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        panelEventoAvversoPerLista.setBackground(new Color(-723724));
        panelEventoAvversoPerLista.setPreferredSize(new Dimension(400, 200));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-723724));
        panelEventoAvversoPerLista.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbSeverita = new JLabel();
        lbSeverita.setBackground(new Color(-723724));
        lbSeverita.setForeground(new Color(-16777216));
        lbSeverita.setText("Severità: 0");
        panel1.add(lbSeverita, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbScriviCommento = new JLabel();
        lbScriviCommento.setBackground(new Color(-723724));
        lbScriviCommento.setForeground(new Color(-16777216));
        lbScriviCommento.setText("Note");
        panel1.add(lbScriviCommento, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sliderServerita.setBackground(new Color(-723724));
        sliderServerita.setForeground(new Color(-16777216));
        panel1.add(sliderServerita, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbCounterCharacter = new JLabel();
        lbCounterCharacter.setBackground(new Color(-723724));
        lbCounterCharacter.setForeground(new Color(-16777216));
        lbCounterCharacter.setText("Caratteri: 0/256");
        panel1.add(lbCounterCharacter, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16777216)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel2.getFont()), new Color(-4473925)));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        txtNote = new JTextArea();
        txtNote.setAutoscrolls(true);
        txtNote.setBackground(new Color(-1));
        txtNote.setColumns(0);
        txtNote.setDisabledTextColor(new Color(-8355712));
        Font txtNoteFont = this.$$$getFont$$$("Arial", Font.PLAIN, 11, txtNote.getFont());
        if (txtNoteFont != null) txtNote.setFont(txtNoteFont);
        txtNote.setLineWrap(true);
        txtNote.setTabSize(3);
        txtNote.setText("");
        txtNote.setWrapStyleWord(true);
        scrollPane1.setViewportView(txtNote);
        lbTipologiaEventoAvverso = new JLabel();
        Font lbTipologiaEventoAvversoFont = this.$$$getFont$$$(null, Font.BOLD, 16, lbTipologiaEventoAvverso.getFont());
        if (lbTipologiaEventoAvversoFont != null) lbTipologiaEventoAvverso.setFont(lbTipologiaEventoAvversoFont);
        lbTipologiaEventoAvverso.setForeground(new Color(-16777216));
        lbTipologiaEventoAvverso.setText("Nome evento avverso");
        panel1.add(lbTipologiaEventoAvverso, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return panelEventoAvversoPerLista;
    }
}
