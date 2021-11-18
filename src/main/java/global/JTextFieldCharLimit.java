package global;

import javax.swing.text.*;

/**
 * La classe JTextFieldCharLimit permette di settare un numero massimo di
 * caratteri dentro un JTextField
 *
 * @author Silvio Pazienza
 */

public class JTextFieldCharLimit extends PlainDocument {

    /**
     * <code>limit</code> &egrave; una variabile final che permette di settare il limite massimo di caratteri
     * &egrave; dichiarata <strong>private</strong> in quanto l'attributo &egrave; utilizzabile all'interno della classe
     */

    private final int limit;

    /**
     * Costruttore della classe
     *
     * @param limit &egrave; un intero che rappresenta il limite passato come argomento
     */

    public JTextFieldCharLimit(int limit) {
        this.limit = limit;
    }

    /**
     * <code>insertString</code> &egrave; un metodo ereditato dalla classe PlainDocument che permette di
     * bloccare l'inserimento dei caratteri in base al limite stabilito dallo sviluppatore
     *
     * @param offset &egrave; un intero che rappresenta l'inizio dell'offset che deve essere &gt;= 0
     * @param str    &egrave; una stringa che l'utente inserisce nel JTextField
     * @param set    &egrave; l'attributo per il contenuto inserito
     * @throws BadLocationException &egrave; la posizione di inserimento data non &egrave; una posizione valida
     *                              all'interno del documento
     */

    public void insertString(int offset, String str, AttributeSet set) throws BadLocationException {
        if (str == null) {
            return;
        } else if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, set);
        }
    }
}