// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class VariationButtonListener implements ActionListener
{
    private App app;
    private int direction;
    
    public VariationButtonListener(final App app, final int direction) {
        this.app = app;
        this.direction = direction;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.app.getOverallState() == 2 && this.app.getGame().getNumActions() > 1 && this.app.getVariationIndex() + this.direction >= 0 && this.app.getVariationIndex() + this.direction < this.app.getGame().getNumActions()) {
            this.app.setOverallState(3);
            this.app.setVariationIndex(this.app.getVariationIndex() + this.direction);
            this.app.setOverallState(2);
            this.app.UpdateGui();
        }
    }
}
