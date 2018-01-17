// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class NewGameButtonListener implements ActionListener
{
    private App app;
    
    public NewGameButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.app.getOverallState() != 0) {
            if (!this.app.getGame().canSave()) {
                this.app.NewGame();
                return;
            }
            switch (this.SaveChanges()) {
                case 0: {
                    if (SaveButtonListener.SaveGame(this.app)) {
                        this.app.NewGame();
                        break;
                    }
                    break;
                }
                case 1: {
                    this.app.NewGame();
                    break;
                }
            }
        }
    }
    
    private int SaveChanges() {
        return JOptionPane.showConfirmDialog(this.app, "Save Changes?", this.app.getTitle(), 1, 2);
    }
}
