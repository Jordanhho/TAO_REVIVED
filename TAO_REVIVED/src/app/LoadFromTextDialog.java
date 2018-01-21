package app;

import javax.swing.JOptionPane;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class LoadFromTextDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JTextArea jTextArea;
    private JButton jButton;
    private App app;
    
    public LoadFromTextDialog(final App app) {
        super(app);
        this.jContentPane = null;
        this.jTextArea = null;
        this.jButton = null;
        this.app = app;
        this.initialize();
    }
    
    private void initialize() {
        this.setSize(400, 500);
        this.setTitle("Enter TAO Game Data");
        this.setResizable(false);
        this.setModal(true);
        this.setDefaultCloseOperation(2);
        this.setContentPane(this.getJContentPane());
    }
    
    private JPanel getJContentPane() {
        if (this.jContentPane == null) {
            (this.jContentPane = new JPanel()).setLayout(new FlowLayout());
            this.jContentPane.setPreferredSize(new Dimension(475, 430));
            this.jContentPane.add(this.getJTextArea(), null);
            this.jContentPane.add(this.getJButton(), null);
        }
        return this.jContentPane;
    }
    
    private JTextArea getJTextArea() {
        if (this.jTextArea == null) {
            (this.jTextArea = new JTextArea()).setPreferredSize(new Dimension(390, 420));
            this.jTextArea.setBorder(null);
            this.jTextArea.setCursor(new Cursor(0));
            this.jTextArea.setLineWrap(true);
        }
        return this.jTextArea;
    }
    
    private JButton getJButton() {
        if (this.jButton == null) {
            (this.jButton = new JButton()).setPreferredSize(new Dimension(70, 30));
            this.jButton.setText("Load");
            this.jButton.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    try {
                        try {
                            LoadFromTextDialog.this.app.NewGame(LoadFromTextDialog.this.getJTextArea().getText());
                        }
                        catch (Exception e2) {
                            LoadFromTextDialog.this.app.NewGame(LoadFromTextDialog.this.getJTextArea().getText(), true);
                        }
                        LoadFromTextDialog.this.app.getGame().onSave();
                        LoadFromTextDialog.this.app.setFile(null);
                        LoadFromTextDialog.this.app.UpdateGui();
                        ((JButton)e.getSource()).getParent().getParent().getParent().getParent().setVisible(false);
                    }
                    catch (Exception ex) {
                        LoadFromTextDialog.this.app.setOverallState(3);
                        JOptionPane.showMessageDialog(LoadFromTextDialog.this.app, "Invalid or corrupt input", "Error", 0);
                        LoadFromTextDialog.this.app.NewGame();
                    }
                }
            });
        }
        return this.jButton;
    }
}
