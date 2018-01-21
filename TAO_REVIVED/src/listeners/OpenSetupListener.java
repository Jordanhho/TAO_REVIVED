// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.JOptionPane;
import util.Location;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.Component;
import util.TAOSetupFileFilter;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import game.Setup;
import app.App;
import java.awt.event.ActionListener;

public class OpenSetupListener implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private App app;
    private Setup setup;
    
    public OpenSetupListener(final App app, final Setup setup) {
        this.app = app;
        this.setup = setup;
    }
    
    public void actionPerformed(final ActionEvent event) {
        final JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        final FileFilter filter = new TAOSetupFileFilter();
        fc.setFileFilter(filter);
        final int approveOption = fc.showOpenDialog(this.app);
        final File open = fc.getSelectedFile();
        if (approveOption != 0) {
            return;
        }
        FileReader fr = null;
        try {
            fr = new FileReader(open);
        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException("Something went wrong with I/O");
        }
        final BufferedReader br = new BufferedReader(fr);
        String s = "";
        try {
            s = String.valueOf(s) + br.readLine() + "\n";
        }
        catch (IOException e2) {
            throw new IllegalStateException("Something went wrong with I/O");
        }
        try {
            this.setup.clear();
            int i = 0;
            while (s.charAt(i) != '\n') {
                final int x = s.charAt(i++) - '0';
                final int y = s.charAt(i++) - '0';
                final char unit = s.charAt(i++);
                this.setup.add(new Location(x, y), unit);
            }
        }
        catch (Exception e3) {
            this.app.setOverallState(3);
            JOptionPane.showMessageDialog(this.app, "Invalid or corrupt input file", "Error", 0);
            this.app.NewGame();
        }
        this.app.UpdateTiles();
        this.app.getEditor().UpdateButtons();
    }
}
