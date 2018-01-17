// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class ActionButtonListener implements ActionListener
{
    private App app;
    private int action;
    
    public ActionButtonListener(final App app, final int action) {
        this.app = app;
        this.action = action;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.app.getOverallState() == 1 && this.app.getCurrentAction() != this.action) {
            this.app.setCurrentAction(this.action);
            this.app.UpdateGui();
        }
    }
}
