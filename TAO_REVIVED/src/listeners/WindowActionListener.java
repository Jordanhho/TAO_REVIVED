package listeners;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;

public class WindowActionListener implements ActionListener, WindowListener
{
    private App app;
    
    public WindowActionListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent e) {
        if (this.app.getEditor() != null) {
            if (this.app.getEditor() != null) {
                this.app.getEditor().dispose();
            }
            this.app.dispose();
        }
        else {
            if (!this.app.getGame().canSave()) {
                this.app.dispose();
                return;
            }
            switch (this.SaveBeforeExiting()) {
                case 0: {
                    if (SaveButtonListener.SaveGame(this.app)) {
                        this.app.dispose();
                        break;
                    }
                    break;
                }
                case 1: {
                    this.app.dispose();
                    break;
                }
            }
        }
    }
    
    public void windowActivated(final WindowEvent e) {
    }
    
    public void windowDeactivated(final WindowEvent e) {
    }
    
    public void windowOpened(final WindowEvent e) {
    }
    
    public void windowClosed(final WindowEvent e) {
    }
    
    public void windowClosing(final WindowEvent e) {
        if (e.getSource() == this.app) {
            if (this.app.getEditor() != null) {
                if (this.app.getEditor() != null) {
                    this.app.getEditor().dispose();
                }
                this.app.dispose();
            }
            else {
                if (!this.app.getGame().canSave()) {
                    this.app.dispose();
                    return;
                }
                this.app.setExtendedState(0);
                switch (this.SaveBeforeExiting()) {
                    case 0: {
                        if (SaveButtonListener.SaveGame(this.app)) {
                            this.app.dispose();
                            break;
                        }
                        break;
                    }
                    case 1: {
                        this.app.dispose();
                        break;
                    }
                }
            }
        }
    }
    
    public void windowDeiconified(final WindowEvent e) {
        if (e.getSource() == this.app && this.app.getEditor() != null) {
            this.app.getEditor().setExtendedState(0);
        }
        if (e.getSource() == this.app.getEditor() && this.app.getEditor() != null) {
            this.app.setExtendedState(0);
        }
    }
    
    public void windowIconified(final WindowEvent e) {
        if (e.getSource() == this.app && this.app.getEditor() != null) {
            this.app.getEditor().setExtendedState(1);
        }
        if (e.getSource() == this.app.getEditor() && this.app.getEditor() != null) {
            this.app.setExtendedState(1);
        }
    }
    
    private int SaveBeforeExiting() {
        return JOptionPane.showConfirmDialog(this.app, "Save before exiting?", this.app.getTitle(), 1, 2);
    }
}
