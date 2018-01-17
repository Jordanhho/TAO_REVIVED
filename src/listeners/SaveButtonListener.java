// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.io.File;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class SaveButtonListener implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private App app;
    
    public SaveButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent event) {
        if (this.app.getFile() == null) {
            new SaveAsButtonListener(this.app).actionPerformed(event);
            return;
        }
        final File file = this.app.getFile();
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            final FileWriter fr = new FileWriter(file);
            fr.write(this.app.getGame().toString());
            fr.flush();
            this.app.setFile(file);
            this.app.getGame().onSave();
            this.app.UpdateGui();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static boolean SaveGame(final App app) {
        if (app.getFile() == null || !app.getGame().canSave()) {
            return new SaveAsButtonListener(app).SaveGameAs();
        }
        final File file = app.getFile();
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            final FileWriter fr = new FileWriter(file);
            fr.write(app.getGame().toString());
            fr.flush();
            app.setFile(file);
            app.getGame().onSave();
            app.UpdateMenu();
            app.UpdateTiles();
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
