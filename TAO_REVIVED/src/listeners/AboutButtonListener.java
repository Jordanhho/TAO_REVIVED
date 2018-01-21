package listeners;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class AboutButtonListener implements ActionListener
{
    private static final String MESSAGE = "TAO - Game Recorder\nVersion: 2.0\nLast Update: 1/6/09\nAuthor: Sean Lourette\nContact: slourette@wustl.edu\n\nCopyright � 2007 Sean Lourette,\nAll Rights Reserved\n\nThanks to Digital Seed Entertainment for\nthe creation of this awesome game. Images\nare property of Digital Seed Entertainment.";
    private App app;
    
    public AboutButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent e) {
        JOptionPane.showMessageDialog(this.app, "TAO - Game Recorder\nVersion: 2.0\nLast Update: 1/6/09\nAuthor: Sean Lourette\nContact: slourette@wustl.edu\n\nCopyright � 2007 Sean Lourette,\nAll Rights Reserved\n\nThanks to Digital Seed Entertainment for\nthe creation of this awesome game. Images\nare property of Digital Seed Entertainment.", "About", 1);
    }
}
