package listeners;

import app.LoadFromTextDialog;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

public class LoadFromTextButtonListener extends JFileChooser implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private App app;
    
    public LoadFromTextButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent event) {
        if (this.app.getGame() != null && this.app.getGame().canSave()) {
            final int result = JOptionPane.showConfirmDialog(this.app, "Save before exiting?", this.app.getTitle(), 1, 2);
            switch (result) {
                case 0: {
                    if (!SaveButtonListener.SaveGame(this.app)) {
                        return;
                    }
                    break;
                }
                case -1:
                case 2: {
                    return;
                }
            }
        }
        final LoadFromTextDialog d = new LoadFromTextDialog(this.app);
        d.setVisible(true);
    }
}
