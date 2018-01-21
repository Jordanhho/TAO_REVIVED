package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class UndoButtonListener implements ActionListener
{
    private App app;
    
    public UndoButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        this.app.setOverallState(3);
        this.app.getGame().undo();
        if (this.app.getGame().atEnd()) {
            this.app.setOverallState(1);
        }
        else {
            this.app.setOverallState(2);
        }
        this.app.UpdateGui();
    }
}
