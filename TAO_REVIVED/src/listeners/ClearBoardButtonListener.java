// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class ClearBoardButtonListener implements ActionListener
{
    private App app;
    private boolean south;
    
    public ClearBoardButtonListener(final App app, final boolean south) {
        this.app = app;
        this.south = south;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.south) {
            this.app.getEditor().getSouthSetup().clear();
        }
        else {
            this.app.getEditor().getNorthSetup().clear();
        }
        this.app.UpdateTiles();
        this.app.getEditor().UpdateButtons();
    }
}
