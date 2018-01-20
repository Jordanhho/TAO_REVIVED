package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class ForwardButtonListener implements ActionListener
{
    private App app;
    
    public ForwardButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.app.getOverallState() == 2 && !this.app.getGame().atEnd()) {
            this.app.setOverallState(3);
            this.app.getGame().next(this.app.getVariationIndex());
            this.app.setVariationIndex(0);
            if (this.app.getGame().atEnd()) {
                this.app.setOverallState(1);
            }
            else {
                this.app.setOverallState(2);
            }
            this.app.UpdateGui();
        }
    }
}
