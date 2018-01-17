// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.KeyEvent;
import app.App;
import java.awt.event.KeyListener;

public class TextBoxListener implements KeyListener
{
    private App app;
    
    public TextBoxListener(final App app) {
        this.app = app;
    }
    
    public void keyPressed(final KeyEvent event) {
    }
    
    public void keyTyped(final KeyEvent event) {
    }
    
    public void keyReleased(final KeyEvent e) {
        if (this.app.getDescription().isEditable()) {
            this.app.getGame().setDescription(this.app.getVariationIndex(), this.app.getDescription().getText());
            this.app.UpdateMenu();
        }
    }
}
