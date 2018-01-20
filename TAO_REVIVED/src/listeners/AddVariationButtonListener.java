package listeners;

import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class AddVariationButtonListener implements ActionListener
{
    private App app;
    
    public AddVariationButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        this.app.setOverallState(1);
        this.app.UpdateGui();
    }
}
