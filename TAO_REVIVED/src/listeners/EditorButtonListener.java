// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import app.Editor;
import java.awt.event.ActionListener;

public class EditorButtonListener implements ActionListener
{
    private Editor editor;
    private char key;
    private JButton button;
    
    public EditorButtonListener(final Editor editor, final JButton button, final char key) {
        this.editor = editor;
        this.key = key;
        this.button = button;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        this.editor.getSelectedButton().setEnabled(true);
        this.editor.getSelectedButton().setBorder(BorderFactory.createBevelBorder(0));
        this.editor.setSelectedUnit(this.key);
        this.editor.setSelectedButton(this.button);
        this.button.setBorder(BorderFactory.createLoweredBevelBorder());
        this.button.setEnabled(false);
        this.editor.requestFocus();
    }
}
