package app;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import util.Direction;
import listeners.EditorButtonListener;
import listeners.OpenSetupListener;
import listeners.SaveSetupListener;
import listeners.ClearBoardButtonListener;
import java.awt.event.ActionListener;
import listeners.StartGameButtonListener;
import java.awt.event.WindowListener;
import listeners.WindowActionListener;
import java.awt.event.KeyListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import game.Setup;

public class Editor extends EditorFrame
{
    private static final long serialVersionUID = 1L;
    private Setup northSetup;
    private Setup southSetup;
    private char selectedUnit;
    private JButton selectedButton;
    private App app;
    private ButtonGroup buttonGroup;
    
    public Editor(final App app) {
        super(app);
        this.northSetup = null;
        this.southSetup = null;
        this.selectedUnit = ' ';
        this.selectedButton = null;
        this.app = null;
        this.buttonGroup = null;
        this.app = app;
        this.initialize();
    }
    
    private void initialize() {
        this.northSetup = new Setup(false);
        this.southSetup = new Setup(false);
        this.selectedButton = this.getEmpty();
        this.getButtonGroup();
        this.addKeyListener(this.app.getKeyListener());
        this.addWindowListener(new WindowActionListener(this.app));
        this.getStartGame().addActionListener(new StartGameButtonListener(this.app));
        this.getClearNorth().addActionListener(new ClearBoardButtonListener(this.app, false));
        this.getClearSouth().addActionListener(new ClearBoardButtonListener(this.app, true));
        this.getSaveNorth().addActionListener(new SaveSetupListener(this.app, this.northSetup));
        this.getSaveSouth().addActionListener(new SaveSetupListener(this.app, this.southSetup));
        this.getOpenNorth().addActionListener(new OpenSetupListener(this.app, this.northSetup));
        this.getOpenSouth().addActionListener(new OpenSetupListener(this.app, this.southSetup));
        this.getKnight().addActionListener(new EditorButtonListener(this, this.getKnight(), 'K'));
        this.getKnight().setIcon(this.app.getImageManager().getImageIcon('K', true, null));
        this.getKnight().setDisabledIcon(this.app.getImageManager().getImageIcon('K', true, null));
        this.getCleric().addActionListener(new EditorButtonListener(this, this.getCleric(), 'C'));
        this.getCleric().setIcon(this.app.getImageManager().getImageIcon('C', true, null));
        this.getCleric().setDisabledIcon(this.app.getImageManager().getImageIcon('C', true, null));
        this.getScout().addActionListener(new EditorButtonListener(this, this.getScout(), 'S'));
        this.getScout().setIcon(this.app.getImageManager().getImageIcon('S', true, null));
        this.getScout().setDisabledIcon(this.app.getImageManager().getImageIcon('S', true, null));
        this.getLightningWard().addActionListener(new EditorButtonListener(this, this.getLightningWard(), 'L'));
        this.getLightningWard().setIcon(this.app.getImageManager().getImageIcon('L', true, null));
        this.getLightningWard().setDisabledIcon(this.app.getImageManager().getImageIcon('L', true, null));
        this.getBarrierWard().addActionListener(new EditorButtonListener(this, this.getBarrierWard(), 'b'));
        this.getBarrierWard().setIcon(this.app.getImageManager().getImageIcon('b', true, null));
        this.getBarrierWard().setDisabledIcon(this.app.getImageManager().getImageIcon('b', true, null));
        this.getDarkMagicWitch().addActionListener(new EditorButtonListener(this, this.getDarkMagicWitch(), 'W'));
        this.getDarkMagicWitch().setIcon(this.app.getImageManager().getImageIcon('W', true, null));
        this.getDarkMagicWitch().setDisabledIcon(this.app.getImageManager().getImageIcon('W', true, null));
        this.getEnchantress().addActionListener(new EditorButtonListener(this, this.getEnchantress(), 'E'));
        this.getEnchantress().setIcon(this.app.getImageManager().getImageIcon('E', true, null));
        this.getEnchantress().setDisabledIcon(this.app.getImageManager().getImageIcon('E', true, null));
        this.getAssassin().addActionListener(new EditorButtonListener(this, this.getAssassin(), 'A'));
        this.getAssassin().setIcon(this.app.getImageManager().getImageIcon('A', true, null));
        this.getAssassin().setDisabledIcon(this.app.getImageManager().getImageIcon('A', true, null));
        this.getPyromancer().addActionListener(new EditorButtonListener(this, this.getPyromancer(), 'P'));
        this.getPyromancer().setIcon(this.app.getImageManager().getImageIcon('P', true, null));
        this.getPyromancer().setDisabledIcon(this.app.getImageManager().getImageIcon('P', true, null));
        this.getDragonSpeakerMage().addActionListener(new EditorButtonListener(this, this.getDragonSpeakerMage(), 'D'));
        this.getDragonSpeakerMage().setIcon(this.app.getImageManager().getImageIcon('D', true, null));
        this.getDragonSpeakerMage().setDisabledIcon(this.app.getImageManager().getImageIcon('D', true, null));
        this.getMudGolem().addActionListener(new EditorButtonListener(this, this.getMudGolem(), 'M'));
        this.getMudGolem().setIcon(this.app.getImageManager().getImageIcon('M', true, null));
        this.getMudGolem().setDisabledIcon(this.app.getImageManager().getImageIcon('M', true, null));
        this.getGolemAmbusher().addActionListener(new EditorButtonListener(this, this.getGolemAmbusher(), 'G'));
        this.getGolemAmbusher().setIcon(this.app.getImageManager().getImageIcon('G', true, null));
        this.getGolemAmbusher().setDisabledIcon(this.app.getImageManager().getImageIcon('G', true, null));
        this.getFrostGolem().addActionListener(new EditorButtonListener(this, this.getFrostGolem(), 'f'));
        this.getFrostGolem().setIcon(this.app.getImageManager().getImageIcon('f', true, null));
        this.getFrostGolem().setDisabledIcon(this.app.getImageManager().getImageIcon('f', true, null));
        this.getStoneGolem().addActionListener(new EditorButtonListener(this, this.getStoneGolem(), 's'));
        this.getStoneGolem().setIcon(this.app.getImageManager().getImageIcon('s', true, null));
        this.getStoneGolem().setDisabledIcon(this.app.getImageManager().getImageIcon('s', true, null));
        this.getDragonTyrant().addActionListener(new EditorButtonListener(this, this.getDragonTyrant(), 'T'));
        this.getDragonTyrant().setIcon(this.app.getImageManager().getImageIcon('T', true, null));
        this.getDragonTyrant().setDisabledIcon(this.app.getImageManager().getImageIcon('T', true, null));
        this.getBerserker().addActionListener(new EditorButtonListener(this, this.getBerserker(), 'Z'));
        this.getBerserker().setIcon(this.app.getImageManager().getImageIcon('Z', true, null));
        this.getBerserker().setDisabledIcon(this.app.getImageManager().getImageIcon('Z', true, null));
        this.getBeastRider().addActionListener(new EditorButtonListener(this, this.getBeastRider(), 'B'));
        this.getBeastRider().setIcon(this.app.getImageManager().getImageIcon('B', true, null));
        this.getBeastRider().setDisabledIcon(this.app.getImageManager().getImageIcon('B', true, null));
        this.getPoisonWisp().addActionListener(new EditorButtonListener(this, this.getPoisonWisp(), 'p'));
        this.getPoisonWisp().setIcon(this.app.getImageManager().getImageIcon('p', true, null));
        this.getPoisonWisp().setDisabledIcon(this.app.getImageManager().getImageIcon('p', true, null));
        this.getFurgon().addActionListener(new EditorButtonListener(this, this.getFurgon(), 'F'));
        this.getFurgon().setIcon(this.app.getImageManager().getImageIcon('F', true, null));
        this.getFurgon().setDisabledIcon(this.app.getImageManager().getImageIcon('F', true, null));
        this.getEmpty().addActionListener(new EditorButtonListener(this, this.getEmpty(), ' '));
    }
    
    public Setup getNorthSetup() {
        return this.northSetup;
    }
    
    public Setup getSouthSetup() {
        return this.southSetup;
    }
    
    public char getSelectedUnit() {
        return this.selectedUnit;
    }
    
    public void setSelectedUnit(final char c) {
        this.selectedUnit = c;
    }
    
    public void setSelectedButton(final JButton button) {
        this.selectedButton = button;
    }
    
    public JButton getSelectedButton() {
        return this.selectedButton;
    }
    
    public boolean goesFirst() {
        return this.getButtonGroup().isSelected(this.getSouthGoesFirst().getModel());
    }
    
    public void UpdateButtons() {
        this.getSaveNorth().setEnabled(this.northSetup.isValid());
        this.getSaveSouth().setEnabled(this.southSetup.isValid());
        this.getStartGame().setEnabled(this.northSetup.isValid() && this.southSetup.isValid());
    }
    
    private ButtonGroup getButtonGroup() {
        if (this.buttonGroup == null) {
            (this.buttonGroup = new ButtonGroup()).add(this.getNorthGoesFirst());
            this.buttonGroup.add(this.getSouthGoesFirst());
            this.buttonGroup.setSelected(this.getSouthGoesFirst().getModel(), true);
        }
        return this.buttonGroup;
    }
    
    public App getApp() {
        return this.app;
    }
}
