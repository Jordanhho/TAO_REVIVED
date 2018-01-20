package listeners;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import util.TAOGameFileFilter;
import javax.swing.JFileChooser;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class OpenGameListener implements ActionListener
{
    private App app;
    
    public OpenGameListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent arg0) {
        if (this.app.getGame() != null && this.app.getGame().canSave()) {
            final int result = JOptionPane.showConfirmDialog(this.app, "Save before exiting?", this.app.getTitle(), 1, 2);
            switch (result) {
                case 0: {
                    if (!SaveButtonListener.SaveGame(this.app)) {
                        return;
                    }
                    break;
                }
                case -1:
                case 2: {
                    return;
                }
            }
        }
        final JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        final FileFilter filter = new TAOGameFileFilter();
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
        catch (FileNotFoundException e2) {
            throw new IllegalStateException("Something went wrong with I/O");
        }
        final BufferedReader br = new BufferedReader(fr);
        String lines = "";
        try {
            for (int i = 0; i < 6; ++i) {
                lines = String.valueOf(lines) + br.readLine() + "\n";
            }
        }
        catch (IOException e3) {
            throw new IllegalStateException("Something went wrong with I/O");
        }
        try {
            try {
                this.app.NewGame(lines);
                this.app.getGame().onSave();
            }
            catch (Exception e4) {
                this.app.NewGame(lines, true);
                if (open.exists()) {
                    open.delete();
                }
                try {
                    open.createNewFile();
                    final FileWriter fw = new FileWriter(open);
                    fw.write(this.app.getGame().toString());
                    fw.flush();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                JOptionPane.showMessageDialog(this.app, "File was updated to newer format", "TAO Game Recorder", 1);
            }
            this.app.getGame().onSave();
            this.app.setFile(open);
            this.app.UpdateGui();
        }
        catch (Exception e5) {
            this.app.setOverallState(3);
            JOptionPane.showMessageDialog(this.app, "Invalid or corrupt input file", "Error", 0);
            this.app.NewGame();
        }
    }
}
