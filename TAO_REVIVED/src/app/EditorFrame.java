package app;

import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class EditorFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane;
    private JPanel pallette;
    private JButton knight;
    private JButton cleric;
    private JButton scout;
    private JButton lightningWard;
    private JButton empty;
    private JPanel north;
    private JPanel south;
    private JButton saveNorth;
    private JButton openNorth;
    private JButton saveSouth;
    private JButton openSouth;
    private JLabel northLabel;
    private JLabel southLabel;
    private JTextField southPlayerName;
    private JTextField northPlayerName;
    private JButton startGame;
    private JRadioButton northGoesFirst;
    private JRadioButton southGoesFirst;
    private JLabel northgoesFirstLabel;
    private JLabel southGoesFirstLabel;
    private JButton barrierWard;
    private JButton darkMagicWitch;
    private JButton enchantress;
    private JButton assassin;
    private JButton clearNorth;
    private JButton clearSouth;
    private JButton pyromancer;
    private JButton dragonSpeakerMage;
    private JButton mudGolem;
    private JButton golemAmbusher;
    private JButton frostGolem;
    private JButton stoneGolem;
    private JButton dragonTyrant;
    private JButton berserker;
    private JButton beastRider;
    private JButton poisonWisp;
    private JButton furgon;
    
    public EditorFrame(final App app) {
        this.jContentPane = null;
        this.pallette = null;
        this.knight = null;
        this.cleric = null;
        this.scout = null;
        this.lightningWard = null;
        this.empty = null;
        this.north = null;
        this.south = null;
        this.saveNorth = null;
        this.openNorth = null;
        this.saveSouth = null;
        this.openSouth = null;
        this.northLabel = null;
        this.southLabel = null;
        this.southPlayerName = null;
        this.northPlayerName = null;
        this.startGame = null;
        this.northGoesFirst = null;
        this.southGoesFirst = null;
        this.northgoesFirstLabel = null;
        this.southGoesFirstLabel = null;
        this.barrierWard = null;
        this.darkMagicWitch = null;
        this.enchantress = null;
        this.assassin = null;
        this.clearNorth = null;
        this.clearSouth = null;
        this.pyromancer = null;
        this.dragonSpeakerMage = null;
        this.mudGolem = null;
        this.golemAmbusher = null;
        this.frostGolem = null;
        this.stoneGolem = null;
        this.dragonTyrant = null;
        this.berserker = null;
        this.beastRider = null;
        this.poisonWisp = null;
        this.furgon = null;
        this.initialize();
    }
    
    private void initialize() {

        this.setSize(220, 690);
        this.setLocation(new Point(660, 5));
        this.setResizable(false);
        this.setDefaultCloseOperation(0);
        this.setContentPane(this.getJContentPane());
        this.setTitle("Editor");
    }
    
    public JPanel getJContentPane() {
        if (this.jContentPane == null) {
            final BorderLayout borderLayout = new BorderLayout();
            (this.jContentPane = new JPanel()).setLayout(borderLayout);
            this.jContentPane.add(this.getPallette(), "Center");
            this.jContentPane.add(this.getNorthLabel(), "North");
            this.jContentPane.add(this.getSouth(), "South");
        }
        return this.jContentPane;
    }
    
    public JPanel getPallette() {
        if (this.pallette == null) {
            (this.pallette = new JPanel()).setLayout(new FlowLayout());
            this.pallette.add(this.getKnight(), null);
            this.pallette.add(this.getScout(), null);
            this.pallette.add(this.getAssassin(), null);
            this.pallette.add(this.getCleric(), null);
            this.pallette.add(this.getPyromancer(), null);
            this.pallette.add(this.getDragonSpeakerMage(), null);
            this.pallette.add(this.getDarkMagicWitch(), null);
            this.pallette.add(this.getEnchantress(), null);
            this.pallette.add(this.getLightningWard(), null);
            this.pallette.add(this.getBarrierWard(), null);
            this.pallette.add(this.getMudGolem(), null);
            this.pallette.add(this.getGolemAmbusher(), null);
            this.pallette.add(this.getFrostGolem(), null);
            this.pallette.add(this.getStoneGolem(), null);
            this.pallette.add(this.getDragonTyrant(), null);
            this.pallette.add(this.getBerserker(), null);
            this.pallette.add(this.getBeastRider(), null);
            this.pallette.add(this.getPoisonWisp(), null);
            this.pallette.add(this.getFurgon(), null);
            this.pallette.add(this.getEmpty(), null);
            this.pallette.add(this.getStartGame(), null);
        }
        return this.pallette;
    }
    
    public JButton getKnight() {
        if (this.knight == null) {
            (this.knight = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.knight.setBackground(new Color(238, 238, 238));
            this.knight.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.knight;
    }
    
    public JButton getCleric() {
        if (this.cleric == null) {
            (this.cleric = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.cleric.setBackground(new Color(238, 238, 238));
            this.cleric.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.cleric;
    }
    
    public JButton getScout() {
        if (this.scout == null) {
            (this.scout = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.scout.setBackground(new Color(238, 238, 238));
            this.scout.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.scout;
    }
    
    public JButton getLightningWard() {
        if (this.lightningWard == null) {
            (this.lightningWard = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.lightningWard.setBackground(new Color(238, 238, 238));
            this.lightningWard.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.lightningWard;
    }
    
    public JButton getEmpty() {
        if (this.empty == null) {
            (this.empty = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.empty.setBorder(BorderFactory.createLoweredBevelBorder());
            this.empty.setBackground(new Color(238, 238, 238));
            this.empty.setEnabled(false);
        }
        return this.empty;
    }
    
    public JPanel getNorthLabel() {
        if (this.north == null) {
            (this.northgoesFirstLabel = new JLabel()).setText("Plays first");
            (this.northLabel = new JLabel()).setText("North");
            (this.north = new JPanel()).setLayout(new FlowLayout());
            this.north.setPreferredSize(new Dimension(150, 85));
            this.north.add(this.northLabel, null);
            this.north.add(this.getNorthPlayerName(), null);
            this.north.add(this.getSaveNorth(), null);
            this.north.add(this.getOpenNorth(), null);
            this.north.add(this.getClearNorth(), null);
            this.north.add(this.northgoesFirstLabel, null);
            this.north.add(this.getNorthGoesFirst(), null);
        }
        return this.north;
    }
    
    public JPanel getSouth() {
        if (this.south == null) {
            (this.southGoesFirstLabel = new JLabel()).setText("Plays first");
            (this.southLabel = new JLabel()).setText("South");
            (this.south = new JPanel()).setLayout(new FlowLayout());
            this.south.setPreferredSize(new Dimension(150, 85));
            this.south.add(this.southLabel, null);
            this.south.add(this.getSouthPlayerName(), null);
            this.south.add(this.getSaveSouth(), null);
            this.south.add(this.getOpenSouth(), null);
            this.south.add(this.getClearSouth(), null);
            this.south.add(this.southGoesFirstLabel, null);
            this.south.add(this.getSouthGoesFirst(), null);
        }
        return this.south;
    }
    
    public JButton getSaveNorth() {
        if (this.saveNorth == null) {
            (this.saveNorth = new JButton()).setText("Save");
            this.saveNorth.setEnabled(false);
        }
        return this.saveNorth;
    }
    
    public JButton getOpenNorth() {
        if (this.openNorth == null) {
            (this.openNorth = new JButton()).setText("Open");
        }
        return this.openNorth;
    }
    
    public JButton getSaveSouth() {
        if (this.saveSouth == null) {
            (this.saveSouth = new JButton()).setText("Save");
            this.saveSouth.setEnabled(false);
        }
        return this.saveSouth;
    }
    
    public JButton getOpenSouth() {
        if (this.openSouth == null) {
            (this.openSouth = new JButton()).setText("Open");
        }
        return this.openSouth;
    }
    
    public JTextField getSouthPlayerName() {
        if (this.southPlayerName == null) {
            (this.southPlayerName = new JTextField()).setText("Player 1");
            this.southPlayerName.setPreferredSize(new Dimension(150, 20));
        }
        return this.southPlayerName;
    }
    
    public JTextField getNorthPlayerName() {
        if (this.northPlayerName == null) {
            (this.northPlayerName = new JTextField()).setPreferredSize(new Dimension(150, 20));
            this.northPlayerName.setText("Player 2");
        }
        return this.northPlayerName;
    }
    
    public JButton getStartGame() {
        if (this.startGame == null) {
            (this.startGame = new JButton()).setText("Start Game");
            this.startGame.setEnabled(false);
        }
        return this.startGame;
    }
    
    public JRadioButton getNorthGoesFirst() {
        if (this.northGoesFirst == null) {
            (this.northGoesFirst = new JRadioButton()).setEnabled(true);
        }
        return this.northGoesFirst;
    }
    
    public JRadioButton getSouthGoesFirst() {
        if (this.southGoesFirst == null) {
            this.southGoesFirst = new JRadioButton();
        }
        return this.southGoesFirst;
    }
    
    public JButton getClearNorth() {
        if (this.clearNorth == null) {
            (this.clearNorth = new JButton()).setText("Clear");
        }
        return this.clearNorth;
    }
    
    public JButton getClearSouth() {
        if (this.clearSouth == null) {
            (this.clearSouth = new JButton()).setText("Clear");
        }
        return this.clearSouth;
    }
    
    public JButton getBarrierWard() {
        if (this.barrierWard == null) {
            (this.barrierWard = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.barrierWard.setBackground(new Color(238, 238, 238));
            this.barrierWard.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.barrierWard;
    }
    
    public JButton getDarkMagicWitch() {
        if (this.darkMagicWitch == null) {
            (this.darkMagicWitch = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.darkMagicWitch.setBackground(new Color(238, 238, 238));
            this.darkMagicWitch.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.darkMagicWitch;
    }
    
    public JButton getEnchantress() {
        if (this.enchantress == null) {
            (this.enchantress = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.enchantress.setBackground(new Color(238, 238, 238));
            this.enchantress.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.enchantress;
    }
    
    public JButton getAssassin() {
        if (this.assassin == null) {
            (this.assassin = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.assassin.setBackground(new Color(238, 238, 238));
            this.assassin.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.assassin;
    }
    
    public JButton getPyromancer() {
        if (this.pyromancer == null) {
            (this.pyromancer = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.pyromancer.setBackground(new Color(238, 238, 238));
            this.pyromancer.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.pyromancer;
    }
    
    public JButton getDragonSpeakerMage() {
        if (this.dragonSpeakerMage == null) {
            (this.dragonSpeakerMage = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.dragonSpeakerMage.setBackground(new Color(238, 238, 238));
            this.dragonSpeakerMage.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.dragonSpeakerMage;
    }
    
    public JButton getMudGolem() {
        if (this.mudGolem == null) {
            (this.mudGolem = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.mudGolem.setBackground(new Color(238, 238, 238));
            this.mudGolem.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.mudGolem;
    }
    
    public JButton getGolemAmbusher() {
        if (this.golemAmbusher == null) {
            (this.golemAmbusher = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.golemAmbusher.setBackground(new Color(238, 238, 238));
            this.golemAmbusher.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.golemAmbusher;
    }
    
    public JButton getFrostGolem() {
        if (this.frostGolem == null) {
            (this.frostGolem = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.frostGolem.setBackground(new Color(238, 238, 238));
            this.frostGolem.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.frostGolem;
    }
    
    public JButton getStoneGolem() {
        if (this.stoneGolem == null) {
            (this.stoneGolem = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.stoneGolem.setBackground(new Color(238, 238, 238));
            this.stoneGolem.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.stoneGolem;
    }
    
    public JButton getDragonTyrant() {
        if (this.dragonTyrant == null) {
            (this.dragonTyrant = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.dragonTyrant.setBackground(new Color(238, 238, 238));
            this.dragonTyrant.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.dragonTyrant;
    }
    
    public JButton getBerserker() {
        if (this.berserker == null) {
            (this.berserker = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.berserker.setBackground(new Color(238, 238, 238));
            this.berserker.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.berserker;
    }
    
    public JButton getBeastRider() {
        if (this.beastRider == null) {
            (this.beastRider = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.beastRider.setBackground(new Color(238, 238, 238));
            this.beastRider.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.beastRider;
    }
    
    public JButton getPoisonWisp() {
        if (this.poisonWisp == null) {
            (this.poisonWisp = new JButton()).setPreferredSize(new Dimension(60, 60));
            this.poisonWisp.setBackground(new Color(238, 238, 238));
            this.poisonWisp.setBorder(BorderFactory.createBevelBorder(0));
        }
        return this.poisonWisp;
    }
    
    public JButton getFurgon() {
        if (this.furgon == null) {
            (this.furgon = new JButton()).setBorder(BorderFactory.createBevelBorder(0));
            this.furgon.setBackground(new Color(238, 238, 238));
            this.furgon.setPreferredSize(new Dimension(60, 60));
        }
        return this.furgon;
    }
}
