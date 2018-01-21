// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import javax.swing.filechooser.FileFilter;
import java.io.FileWriter;
import util.TAOGameFileFilter;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.File;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

public class SaveAsButtonListener extends JFileChooser implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private App app;
    
    public SaveAsButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent event) {
        this.SaveGameAs();
    }
    
    public void approveSelection() {
        final File file = this.getSelectedFile();
        String name = file.getAbsolutePath();
        if (!name.endsWith(".taog")) {
            name = String.valueOf(name) + "." + "taog";
        }
        if (new File(name).exists()) {
            final int n = JOptionPane.showConfirmDialog(this, String.valueOf(name) + " already exists.\n Do you want to replace it?", "Save As", 0, 2);
            if (n == 0) {
                super.approveSelection();
            }
        }
        else {
            super.approveSelection();
        }
    }
    
    public boolean SaveGameAs() {
        this.setAcceptAllFileFilterUsed(false);
        final FileFilter filter = new TAOGameFileFilter();
        this.setFileFilter(filter);
        final int approveOption = this.showSaveDialog(this.app);
        if (approveOption != 0) {
            return false;
        }
        File file = this.getSelectedFile();
        String name = file.getAbsolutePath();
        if (!name.endsWith(".taog")) {
            name = String.valueOf(name) + "." + "taog";
            file = new File(name);
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            final FileWriter fr = new FileWriter(file);
            fr.write(this.app.getGame().toString());
            fr.append('\n');
            fr.flush();
            this.app.setFile(file);
            this.app.getGame().onSave();
            this.app.UpdateGui();
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
