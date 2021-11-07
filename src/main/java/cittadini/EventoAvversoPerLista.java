package cittadini;

import global.JTextFieldCharLimit;
import models.TipologiaEvento;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;

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
}
