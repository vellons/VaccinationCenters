package cittadini;

import global.JTextFieldCharLimit;
import models.TipologiaEvento;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

public class EventoAvversoPerLista {
    public JPanel panelEventoAvversoPerLista;
    private JLabel lbSeverita;
    private JLabel lbScriviCommento;
    private JSlider sliderServerita;
    private JLabel lbCounterCharacter;
    private JTextArea txtNote;
    private JLabel lbTipologiaEventoAvverso;
    private TipologiaEvento tipologiaEvento;

    public EventoAvversoPerLista(TipologiaEvento tipologiaEvento) {
        this.tipologiaEvento = tipologiaEvento;

        StringBuilder eventoAvversoNome = new StringBuilder();
        lbTipologiaEventoAvverso.setText(String.valueOf(eventoAvversoNome.append(this.tipologiaEvento.getNome().substring(0, 1).toUpperCase()).append(this.tipologiaEvento.getNome().substring(1))));
        txtNote.setEnabled(false);
        //sliderServerita.addChangeListener(e -> lbSeverita.setText("Severità: " + sliderServerita.getValue()));

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

    public TipologiaEvento getTipologiaEvento() {
        return tipologiaEvento;
    }

    public int getValoreSeverita() {
        return sliderServerita.getValue();
    }

    public String getNota() {
        return txtNote.getText();
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
