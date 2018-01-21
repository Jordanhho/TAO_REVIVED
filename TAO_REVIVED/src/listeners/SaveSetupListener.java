// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import javax.swing.filechooser.FileFilter;
import java.io.FileWriter;
import java.io.File;
import java.awt.Component;
import util.TAOSetupFileFilter;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import game.Setup;
import app.App;
import java.awt.event.ActionListener;

public class SaveSetupListener implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private App app;
    private Setup setup;
    
    public SaveSetupListener(final App app, final Setup setup) {
        this.app = app;
        this.setup = setup;
    }
    
    public void actionPerformed(final ActionEvent event) {
        final JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        final FileFilter filter = new TAOSetupFileFilter();
        fc.setFileFilter(filter);
        final int approveOption = fc.showSaveDialog(this.app);
        if (approveOption != 0) {
            return;
        }
        File file = fc.getSelectedFile();
        String name = file.getAbsolutePath();
        if (!name.endsWith(".taos")) {
            name = String.valueOf(name) + "." + "taos";
            file = new File(name);
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            final FileWriter fr = new FileWriter(file);
            fr.write(this.setup.toString());
            fr.append('\n');
            fr.flush();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
