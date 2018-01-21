// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class BackButtonListener implements ActionListener
{
    private App app;
    
    public BackButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.app.getOverallState() != 0 && !this.app.getGame().atBeginning()) {
            this.app.setOverallState(3);
            this.app.back(1);
            this.app.setOverallState(2);
            this.app.UpdateGui();
        }
    }
}
