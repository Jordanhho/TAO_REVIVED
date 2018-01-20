package listeners;

import units.BasicUnit;
import actions.Turn;
import actions.Action;
import actions.EndTurn;
import java.awt.event.ActionEvent;
import util.Direction;
import app.App;
import java.awt.event.ActionListener;

public class TurnButtonListener implements ActionListener
{
    private App app;
    private Direction dir;
    
    public TurnButtonListener(final App app, final Direction dir) {
        this.app = app;
        this.dir = dir;
    }
    
    public void actionPerformed(final ActionEvent e) {
        if (this.app.getCurrentSelection() == null || !this.app.getCurrentSelection().mobile() || this.app.getOverallState() != 1 || this.app.getCurrentAction() != 6) {
            return;
        }
        if (this.app.getCurrentSelection().direction().equals(this.dir)) {
            this.app.setOverallState(3);
            this.app.getGame().Play(new EndTurn());
        }
        else {
            final BasicUnit cur = this.app.getCurrentSelection();
            this.app.getGame().Play(new Turn(this.app.getGame().getBoard(), cur.location(), this.dir));
        }
        this.app.setOverallState(1);
        this.app.UpdateGui();
    }
}
