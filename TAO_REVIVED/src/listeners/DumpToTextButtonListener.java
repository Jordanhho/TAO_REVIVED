// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import app.DumpToTextDialog;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class DumpToTextButtonListener implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private App app;
    
    public DumpToTextButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent event) {
        final DumpToTextDialog d = new DumpToTextDialog(this.app);
        d.setVisible(true);
    }
}
