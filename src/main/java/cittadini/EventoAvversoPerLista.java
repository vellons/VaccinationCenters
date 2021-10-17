package cittadini;

import global.JTextFieldCharLimit;
import models.EventoAvverso;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Hashtable;

public class EventoAvversoPerLista {
    public JPanel panelEventoAvversoPerLista;
    private JLabel lbSeverita;
    private JLabel lbScriviCommento;
    private JSlider sliderServerita;
    private JLabel lbCounterCharacter;
    private JTextArea txtNote;
    private JLabel lbTipologiaEventoAvverso;
    private EventoAvverso ea;

    public EventoAvversoPerLista() {

        sliderServerita.addChangeListener(e -> lbSeverita.setText("Severità: " + sliderServerita.getValue()));

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
    }

    private void createUIComponents() {
        sliderServerita = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
        sliderServerita.setMinorTickSpacing(1);
        sliderServerita.setPaintLabels(true);
        sliderServerita.setPaintTicks(true);

        // Add positions label in the slider
        Hashtable position = new Hashtable();
        position.put(0, new JLabel("0"));
        position.put(1, new JLabel("1"));
        position.put(2, new JLabel("2"));
        position.put(3, new JLabel("3"));
        position.put(4, new JLabel("4"));
        position.put(5, new JLabel("5"));
        // Set the label to be drawn
        sliderServerita.setLabelTable(position);
    }
}
