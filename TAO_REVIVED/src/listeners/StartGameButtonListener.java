// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class StartGameButtonListener implements ActionListener
{
    private App app;
    
    public StartGameButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        this.app.StartGame();
    }
}
