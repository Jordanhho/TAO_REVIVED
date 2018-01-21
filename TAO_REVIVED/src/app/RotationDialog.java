// 
// Decompiled by Procyon v0.5.30
// 

package app;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import listeners.TurnButtonListener;
import util.Direction;
import java.awt.Insets;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.KeyListener;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;

public class RotationDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JButton north;
    private JButton south;
    private JButton west;
    private JButton east;
    private JPanel buttons;
    private App app;
    
    public RotationDialog(final App app) {
        super(app);
        this.jContentPane = null;
        this.north = null;
        this.south = null;
        this.west = null;
        this.east = null;
        this.buttons = null;
        this.app = null;
        this.app = app;
        this.initialize();
    }
    
    private void initialize() {
        this.setTitle("Direction Chooser");
        this.setLocation(new Point(660, 0));
        this.setDefaultCloseOperation(0);
        this.setSize(new Dimension(138, 166));
        this.setContentPane(this.getJContentPane());
        this.addKeyListener(this.app.getKeyListener());
    }
    
    private JPanel getJContentPane() {
        if (this.jContentPane == null) {
            final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridy = 0;
            gridBagConstraints5.weighty = 0.5;
            gridBagConstraints5.weightx = 0.5;
            gridBagConstraints5.fill = 1;
            gridBagConstraints5.gridx = 0;
            (this.jContentPane = new JPanel()).setLayout(new GridBagLayout());
            this.jContentPane.add(this.getButtons(), gridBagConstraints5);
            this.jContentPane.setFocusable(false);
        }
        return this.jContentPane;
    }
    
    public JButton getNorth() {
        if (this.north == null) {
            (this.north = new JButton()).setMargin(new Insets(0, 0, 0, 0));
            this.north.addActionListener(new TurnButtonListener(this.app, Direction.NORTH));
            this.north.setIcon(new ImageIcon(this.getClass().getResource("/images/arrow0.GIF")));
            this.north.setFocusable(false);
        }
        return this.north;
    }
    
    public JButton getSouth() {
        if (this.south == null) {
            (this.south = new JButton()).setMargin(new Insets(0, 0, 0, 0));
            this.south.addActionListener(new TurnButtonListener(this.app, Direction.SOUTH));
            this.south.setIcon(new ImageIcon(this.getClass().getResource("/images/arrow2.GIF")));
            this.south.setFocusable(false);
        }
        return this.south;
    }
    
    public JButton getWest() {
        if (this.west == null) {
            (this.west = new JButton()).setMargin(new Insets(0, 0, 0, 0));
            this.west.addActionListener(new TurnButtonListener(this.app, Direction.WEST));
            this.west.setIcon(new ImageIcon(this.getClass().getResource("/images/arrow3.GIF")));
            this.west.setFocusable(false);
        }
        return this.west;
    }
    
    public JButton getEast() {
        if (this.east == null) {
            (this.east = new JButton()).setMargin(new Insets(0, 0, 0, 0));
            this.east.addActionListener(new TurnButtonListener(this.app, Direction.EAST));
            this.east.setIcon(new ImageIcon(this.getClass().getResource("/images/arrow1.GIF")));
            this.east.setFocusable(false);
        }
        return this.east;
    }
    
    private JPanel getButtons() {
        if (this.buttons == null) {
            final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 2;
            gridBagConstraints4.weightx = 0.5;
            gridBagConstraints4.weighty = 0.1;
            gridBagConstraints4.anchor = 13;
            gridBagConstraints4.fill = 3;
            gridBagConstraints4.ipady = 20;
            gridBagConstraints4.gridy = 1;
            final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.weightx = 0.5;
            gridBagConstraints5.weighty = 0.1;
            gridBagConstraints5.anchor = 17;
            gridBagConstraints5.fill = 3;
            gridBagConstraints5.ipady = 20;
            gridBagConstraints5.gridy = 1;
            final GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 1;
            gridBagConstraints6.weightx = 0.1;
            gridBagConstraints6.weighty = 0.5;
            gridBagConstraints6.anchor = 15;
            gridBagConstraints6.fill = 2;
            gridBagConstraints6.ipadx = 20;
            gridBagConstraints6.gridy = 3;
            final GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 1;
            gridBagConstraints7.weightx = 0.1;
            gridBagConstraints7.weighty = 0.5;
            gridBagConstraints7.anchor = 11;
            gridBagConstraints7.fill = 2;
            gridBagConstraints7.ipadx = 20;
            gridBagConstraints7.gridy = 0;
            (this.buttons = new JPanel()).setLayout(new GridBagLayout());
            this.buttons.add(this.getNorth(), gridBagConstraints7);
            this.buttons.add(this.getSouth(), gridBagConstraints6);
            this.buttons.add(this.getWest(), gridBagConstraints5);
            this.buttons.add(this.getEast(), gridBagConstraints4);
            this.buttons.setFocusable(false);
        }
        return this.buttons;
    }
}
