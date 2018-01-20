package listeners;

import actions.Action;
import actions.EndTurn;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class EndTurnListener implements ActionListener
{
    private App app;
    
    public EndTurnListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.app.getOverallState() == 1 && !this.app.getGame().gameOver()) {
            this.app.setOverallState(3);
            if (this.app.getCurrentAction() == 6) {
                this.app.getRotationDialog().setVisible(false);
            }
            this.app.getGame().Play(new EndTurn());
            this.app.setOverallState(1);
            this.app.UpdateGui();
        }
    }
}
