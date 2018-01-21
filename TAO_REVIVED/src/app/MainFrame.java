package app;

import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.KeyStroke;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Font;
import java.awt.ComponentOrientation;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JButton back;
    private JButton forward;
    private JMenuBar Menu;
    private JMenu fileButton;
    private JMenuItem NewGameButton;
    private JMenuItem SaveButton;
    private JMenuItem OpenButton;
    private JMenuItem SaveAsButton;
    private JPanel gameArea;
    private JPanel statepanel;
    private JTextPane description;
    private JButton move;
    private JButton attack;
    private JButton turn;
    private JButton endTurn;
    private JMenuItem ExitButton;
    private JMenu Help;
    private JMenuItem AboutButton;
    private JPanel playbackpanel;
    private JPanel jPanel;
    private JScrollBar jScrollBar;
    private JPanel recordingPanel;
    private JButton undobutton;
    private JMenuItem DumpToTextButton;
    private JMenuItem LoadFromTextButton;
    private JMenu OptionsMenu;
    private JCheckBoxMenuItem RandomizeBlocking;
    private JMenuItem ShortcutButton;
    private JMenuItem AddVariation;
    private JButton up;
    private JButton down;
    
    public MainFrame() {
        this.jContentPane = null;
        this.back = null;
        this.forward = null;
        this.Menu = null;
        this.fileButton = null;
        this.NewGameButton = null;
        this.SaveButton = null;
        this.OpenButton = null;
        this.SaveAsButton = null;
        this.gameArea = null;
        this.statepanel = null;
        this.description = null;
        this.move = null;
        this.attack = null;
        this.turn = null;
        this.endTurn = null;
        this.ExitButton = null;
        this.Help = null;
        this.AboutButton = null;
        this.playbackpanel = null;
        this.jPanel = null;
        this.jScrollBar = null;
        this.recordingPanel = null;
        this.undobutton = null;
        this.DumpToTextButton = null;
        this.LoadFromTextButton = null;
        this.OptionsMenu = null;
        this.RandomizeBlocking = null;
        this.ShortcutButton = null;
        this.AddVariation = null;
        this.up = null;
        this.down = null;
        this.initialize();
    }
    
    private void initialize() {
        this.setSize(new Dimension(660, 745));
        this.setDefaultCloseOperation(0);
        this.setResizable(false);
        this.setJMenuBar(this.getMenu());
        this.setContentPane(this.getJContentPane());
        this.setTitle("TAO - Game Recorder");
        this.setVisible(false);
    }
    
    public JPanel getJContentPane() {
        if (this.jContentPane == null) {
            (this.jContentPane = new JPanel()).setLayout(new FlowLayout());
            this.jContentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            this.jContentPane.setFont(new Font("Dialog", 0, 12));
            this.jContentPane.add(this.getRecordingPanel(), null);
            this.jContentPane.add(this.getGameArea(), null);
            this.jContentPane.setFocusable(false);
        }
        return this.jContentPane;
    }
    
    public JButton getBack() {
        if (this.back == null) {
            (this.back = new JButton()).setIcon(new ImageIcon(this.getClass().getResource("/images/arrow3.GIF")));
            this.back.setEnabled(false);
            this.back.setMnemonic(0);
            this.back.setMargin(new Insets(0, 0, 0, 0));
            this.back.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            this.back.setFocusable(false);
        }
        return this.back;
    }
    
    public JButton getForward() {
        if (this.forward == null) {
            (this.forward = new JButton()).setName("");
            this.forward.setEnabled(false);
            this.forward.setMargin(new Insets(0, 0, 0, 0));
            this.forward.setIcon(new ImageIcon(this.getClass().getResource("/images/arrow1.GIF")));
            this.forward.setFocusable(false);
        }
        return this.forward;
    }
    
    public JMenuBar getMenu() {
        if (this.Menu == null) {
            (this.Menu = new JMenuBar()).setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            this.Menu.add(this.getFileButton());
            this.Menu.add(this.getOptionsMenu());
            this.Menu.add(this.getHelp());
            this.Menu.setFocusable(false);
        }
        return this.Menu;
    }
    
    public JMenu getFileButton() {
        if (this.fileButton == null) {
            (this.fileButton = new JMenu()).setText("File");
            this.fileButton.setMnemonic(70);
            this.fileButton.add(this.getNewGameButton());
            this.fileButton.add(this.getOpenButton());
            this.fileButton.add(this.getSaveButton());
            this.fileButton.add(this.getSaveAsButton());
            this.fileButton.add(this.getLoadFromTextButton());
            this.fileButton.add(this.getDumpToTextButton());
            this.fileButton.add(this.getExitButton());
        }
        return this.fileButton;
    }
    
    public JMenuItem getNewGameButton() {
        if (this.NewGameButton == null) {
            (this.NewGameButton = new JMenuItem()).setText("New Game");
            this.NewGameButton.setMnemonic(78);
        }
        return this.NewGameButton;
    }
    
    public JMenuItem getSaveButton() {
        if (this.SaveButton == null) {
            (this.SaveButton = new JMenuItem()).setText("Save");
            this.SaveButton.setMnemonic(83);
            this.SaveButton.setEnabled(false);
            this.SaveButton.setAccelerator(KeyStroke.getKeyStroke(83, 2, false));
        }
        return this.SaveButton;
    }
    
    public JMenuItem getOpenButton() {
        if (this.OpenButton == null) {
            (this.OpenButton = new JMenuItem()).setText("Open");
            this.OpenButton.setMnemonic(79);
            this.OpenButton.setAccelerator(KeyStroke.getKeyStroke(79, 2, false));
        }
        return this.OpenButton;
    }
    
    public JMenuItem getSaveAsButton() {
        if (this.SaveAsButton == null) {
            (this.SaveAsButton = new JMenuItem()).setText("Save As");
            this.SaveAsButton.setEnabled(false);
            this.SaveAsButton.setMnemonic(65);
        }
        return this.SaveAsButton;
    }
    
    public JPanel getGameArea() {
        if (this.gameArea == null) {
            final GridLayout gridLayout = new GridLayout();
            gridLayout.setRows(11);
            (this.gameArea = new JPanel()).setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            this.gameArea.setMaximumSize(new Dimension(650, 650));
            this.gameArea.setMinimumSize(new Dimension(650, 650));
            this.gameArea.setEnabled(false);
            this.gameArea.setLayout(gridLayout);
            this.gameArea.setPreferredSize(new Dimension(640, 620));
            this.gameArea.setFocusable(false);
        }
        return this.gameArea;
    }
    
    public JPanel getStatepanel() {
        if (this.statepanel == null) {
            final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.gridx = 3;
            gridBagConstraints5.fill = 0;
            gridBagConstraints5.weightx = 0.5;
            gridBagConstraints5.weighty = 0.5;
            gridBagConstraints5.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints5.gridy = 0;
            final GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 2;
            gridBagConstraints6.fill = 0;
            gridBagConstraints6.weightx = 0.5;
            gridBagConstraints6.weighty = 0.5;
            gridBagConstraints6.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints6.gridy = 0;
            final GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 1;
            gridBagConstraints7.fill = 0;
            gridBagConstraints7.weightx = 0.5;
            gridBagConstraints7.weighty = 0.5;
            gridBagConstraints7.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints7.gridy = 0;
            final GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.fill = 0;
            gridBagConstraints8.weightx = 0.5;
            gridBagConstraints8.weighty = 0.5;
            gridBagConstraints8.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints8.gridy = 0;
            (this.statepanel = new JPanel()).setLayout(new GridBagLayout());
            this.statepanel.setBorder(BorderFactory.createBevelBorder(1));
            this.statepanel.setBackground(Color.black);
            this.statepanel.add(this.getMove(), gridBagConstraints8);
            this.statepanel.add(this.getAttack(), gridBagConstraints7);
            this.statepanel.add(this.getTurn(), gridBagConstraints6);
            this.statepanel.add(this.getEndTurn(), gridBagConstraints5);
        }
        return this.statepanel;
    }
    
    public JTextPane getDescription() {
        if (this.description == null) {
            (this.description = new JTextPane()).setBackground(new Color(238, 238, 238));
            this.description.setFont(new Font("Dialog", 0, 11));
            this.description.setEditable(false);
            this.description.setFocusable(true);
        }
        return this.description;
    }
    
    public JButton getMove() {
        if (this.move == null) {
            (this.move = new JButton()).setIcon(new ImageIcon(this.getClass().getResource("/images/move.PNG")));
            this.move.setDisabledIcon(new ImageIcon(this.getClass().getResource("/images/move_d.PNG")));
            this.move.setPreferredSize(new Dimension(38, 38));
            this.move.setBackground(Color.black);
            this.move.setForeground(Color.black);
            this.move.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            this.move.setMnemonic(0);
            this.move.setFocusable(false);
        }
        return this.move;
    }
    
    public JButton getAttack() {
        if (this.attack == null) {
            (this.attack = new JButton()).setIcon(new ImageIcon(this.getClass().getResource("/images/attack.PNG")));
            this.attack.setPreferredSize(new Dimension(38, 38));
            this.attack.setBackground(Color.black);
            this.attack.setForeground(Color.black);
            this.attack.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            this.attack.setDisabledIcon(new ImageIcon(this.getClass().getResource("/images/attack_d.PNG")));
            this.attack.setFocusable(false);
        }
        return this.attack;
    }
    
    public JButton getTurn() {
        if (this.turn == null) {
            (this.turn = new JButton()).setIcon(new ImageIcon(this.getClass().getResource("/images/turn.PNG")));
            this.turn.setPreferredSize(new Dimension(38, 38));
            this.turn.setBackground(Color.black);
            this.turn.setForeground(Color.black);
            this.turn.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            this.turn.setDisabledIcon(new ImageIcon(this.getClass().getResource("/images/turn_d.PNG")));
        }
        return this.turn;
    }
    
    public JButton getEndTurn() {
        if (this.endTurn == null) {
            (this.endTurn = new JButton()).setIcon(new ImageIcon(this.getClass().getResource("/images/endturn.PNG")));
            this.endTurn.setDisabledIcon(new ImageIcon(this.getClass().getResource("/images/endturn_d.PNG")));
            this.endTurn.setPreferredSize(new Dimension(38, 38));
            this.endTurn.setBackground(Color.black);
            this.endTurn.setForeground(Color.black);
            this.endTurn.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            this.endTurn.setFocusable(false);
        }
        return this.endTurn;
    }
    
    public JMenuItem getExitButton() {
        if (this.ExitButton == null) {
            (this.ExitButton = new JMenuItem()).setMnemonic(88);
            this.ExitButton.setText("Exit");
        }
        return this.ExitButton;
    }
    
    public JMenu getHelp() {
        if (this.Help == null) {
            (this.Help = new JMenu()).setMnemonic(72);
            this.Help.setText("Help");
            this.Help.add(this.getShortcutButton());
            this.Help.add(this.getAboutButton());
        }
        return this.Help;
    }
    
    public JMenuItem getAboutButton() {
        if (this.AboutButton == null) {
            (this.AboutButton = new JMenuItem()).setMnemonic(65);
            this.AboutButton.setText("About");
        }
        return this.AboutButton;
    }
    
    public JPanel getPlaybackpanel() {
        if (this.playbackpanel == null) {
            final GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.weightx = 0.3;
            gridBagConstraints1.weighty = 0.5;
            gridBagConstraints1.fill = 0;
            gridBagConstraints1.gridy = 1;
            final GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.weightx = 0.3;
            gridBagConstraints2.weighty = 0.5;
            gridBagConstraints2.fill = 0;
            gridBagConstraints2.gridy = 0;
            final GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 2;
            gridBagConstraints3.ipadx = 10;
            gridBagConstraints3.ipady = 5;
            gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
            gridBagConstraints3.weightx = 0.5;
            gridBagConstraints3.weighty = 0.5;
            gridBagConstraints3.gridy = 0;
            final GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = 2;
            gridBagConstraints4.gridy = 1;
            gridBagConstraints4.weighty = 0.5;
            gridBagConstraints4.gridwidth = 3;
            gridBagConstraints4.weightx = 0.5;
            gridBagConstraints4.insets = new Insets(0, 10, 0, 10);
            gridBagConstraints4.gridx = 1;
            final GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints5.gridy = 0;
            gridBagConstraints5.weightx = 0.5;
            gridBagConstraints5.weighty = 0.5;
            gridBagConstraints5.gridx = 3;
            final GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
            gridBagConstraints6.gridy = 0;
            gridBagConstraints6.fill = 0;
            gridBagConstraints6.weightx = 0.5;
            gridBagConstraints6.weighty = 0.5;
            gridBagConstraints6.gridx = 1;
            (this.playbackpanel = new JPanel()).setLayout(new GridBagLayout());
            this.playbackpanel.setPreferredSize(new Dimension(20, 55));
            this.playbackpanel.add(this.getBack(), gridBagConstraints6);
            this.playbackpanel.add(this.getForward(), gridBagConstraints5);
            this.playbackpanel.add(this.getJScrollBar(), gridBagConstraints4);
            this.playbackpanel.add(this.getUndobutton(), gridBagConstraints3);
            this.playbackpanel.add(this.getUp(), gridBagConstraints2);
            this.playbackpanel.add(this.getDown(), gridBagConstraints1);
        }
        return this.playbackpanel;
    }
    
    public JPanel getJPanel() {
        if (this.jPanel == null) {
            final GridLayout gridLayout1 = new GridLayout();
            gridLayout1.setRows(1);
            gridLayout1.setColumns(1);
            (this.jPanel = new JPanel()).setLayout(gridLayout1);
            this.jPanel.setPreferredSize(new Dimension(260, 55));
            this.jPanel.setBorder(BorderFactory.createEtchedBorder(0));
            this.jPanel.add(this.getDescription(), null);
            this.jPanel.setFocusable(false);
        }
        return this.jPanel;
    }
    
    public JScrollBar getJScrollBar() {
        if (this.jScrollBar == null) {
            (this.jScrollBar = new JScrollBar()).setOrientation(0);
            this.jScrollBar.setValue(0);
            this.jScrollBar.setVisibleAmount(1);
            this.jScrollBar.setMinimum(0);
            this.jScrollBar.setMaximum(0);
            this.jScrollBar.setEnabled(false);
            this.jScrollBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            this.jScrollBar.setFocusable(false);
        }
        return this.jScrollBar;
    }
    
    public JPanel getRecordingPanel() {
        if (this.recordingPanel == null) {
            final GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.ipadx = 20;
            gridBagConstraints12.weighty = 0.5;
            gridBagConstraints12.gridx = 1;
            gridBagConstraints12.gridy = 0;
            gridBagConstraints12.gridwidth = 1;
            gridBagConstraints12.fill = 1;
            gridBagConstraints12.insets = new Insets(0, 5, 0, 0);
            gridBagConstraints12.weightx = 0.9;
            final GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.weightx = 0.1;
            gridBagConstraints13.gridx = 2;
            gridBagConstraints13.gridy = 0;
            gridBagConstraints13.weighty = 0.5;
            final GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
            (this.recordingPanel = new JPanel()).setLayout(new GridBagLayout());
            this.recordingPanel.setPreferredSize(new Dimension(630, 56));
            this.recordingPanel.add(this.getJPanel(), gridBagConstraints14);
            this.recordingPanel.add(this.getPlaybackpanel(), gridBagConstraints12);
            this.recordingPanel.add(this.getStatepanel(), gridBagConstraints13);
        }
        return this.recordingPanel;
    }
    
    public JButton getUndobutton() {
        if (this.undobutton == null) {
            (this.undobutton = new JButton()).setText("Undo");
            this.undobutton.setBorder(BorderFactory.createBevelBorder(0));
            this.undobutton.setEnabled(false);
        }
        return this.undobutton;
    }
    
    public JMenuItem getDumpToTextButton() {
        if (this.DumpToTextButton == null) {
            (this.DumpToTextButton = new JMenuItem()).setMnemonic(68);
            this.DumpToTextButton.setText("Dump to Text");
            this.DumpToTextButton.setEnabled(false);
        }
        return this.DumpToTextButton;
    }
    
    public JMenuItem getLoadFromTextButton() {
        if (this.LoadFromTextButton == null) {
            (this.LoadFromTextButton = new JMenuItem()).setMnemonic(76);
            this.LoadFromTextButton.setText("Load from Text");
        }
        return this.LoadFromTextButton;
    }
    
    public JMenu getOptionsMenu() {
        if (this.OptionsMenu == null) {
            (this.OptionsMenu = new JMenu()).setText("Options");
            this.OptionsMenu.setMnemonic(79);
            this.OptionsMenu.add(this.getAddVariation());
            this.OptionsMenu.add(this.getRandomizeBlocking());
        }
        return this.OptionsMenu;
    }
    
    public JCheckBoxMenuItem getRandomizeBlocking() {
        if (this.RandomizeBlocking == null) {
            (this.RandomizeBlocking = new JCheckBoxMenuItem()).setText("Randomize Blocking");
            this.RandomizeBlocking.setMnemonic(82);
            this.RandomizeBlocking.setSelected(true); //sets default randomized blocking as On
        }
        return this.RandomizeBlocking;
    }
    
    public JMenuItem getShortcutButton() {
        if (this.ShortcutButton == null) {
            (this.ShortcutButton = new JMenuItem()).setMnemonic(83);
            this.ShortcutButton.setText("Shortcuts");
        }
        return this.ShortcutButton;
    }
    
    public JMenuItem getAddVariation() {
        if (this.AddVariation == null) {
            (this.AddVariation = new JMenuItem()).setMnemonic(65);
            this.AddVariation.setText("Add Variation");
        }
        return this.AddVariation;
    }
    
    public JButton getUp() {
        if (this.up == null) {
            (this.up = new JButton()).setIcon(new ImageIcon(this.getClass().getResource("/images/arrow0.GIF")));
            this.up.setMnemonic(0);
            this.up.setMargin(new Insets(0, 0, 0, 0));
            this.up.setEnabled(false);
            this.up.setFocusable(false);
        }
        return this.up;
    }
    
    public JButton getDown() {
        if (this.down == null) {
            (this.down = new JButton()).setIcon(new ImageIcon(this.getClass().getResource("/images/arrow2.GIF")));
            this.down.setMargin(new Insets(0, 0, 0, 0));
            this.down.setEnabled(false);
            this.down.setFocusable(false);
        }
        return this.down;
    }
}
