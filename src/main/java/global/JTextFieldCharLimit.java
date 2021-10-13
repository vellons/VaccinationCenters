package global;

import javax.swing.text.*;

public class JTextFieldCharLimit extends PlainDocument {
    private final int limit;

    public JTextFieldCharLimit(int limit) {
        this.limit = limit;
    }

    public void insertString(int offset, String str, AttributeSet set) throws BadLocationException {
        if (str == null) {
            return;
        } else if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, set);
        }
    }
}