// 
// Decompiled by Procyon v0.5.30
// 

package app;

import game.Setup;
import game.Player;
import units.Unit;
import java.util.Iterator;
import java.util.ArrayList;
import actions.Action;
import actions.EndTurn;
import actions.Turn;
import actions.Attack;
import javax.swing.BorderFactory;
import actions.Move;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.event.MouseListener;
import listeners.TileButtonListener;
import game.Board;
import util.Location;
import java.awt.Component;
import java.awt.event.AdjustmentListener;
import listeners.SliderListener;
import listeners.ShortcutButtonListener;
import listeners.AboutButtonListener;
import listeners.EndTurnListener;
import listeners.ActionButtonListener;
import listeners.TextBoxListener;
import listeners.DumpToTextButtonListener;
import listeners.LoadFromTextButtonListener;
import listeners.SaveAsButtonListener;
import listeners.SaveButtonListener;
import listeners.OpenGameListener;
import listeners.NewGameButtonListener;
import listeners.VariationButtonListener;
import listeners.UndoButtonListener;
import listeners.ForwardButtonListener;
import listeners.BackButtonListener;
import java.awt.event.ActionListener;
import listeners.AddVariationButtonListener;
import java.awt.event.WindowListener;
import listeners.WindowActionListener;
import java.io.File;
import util.ImageManager;
import listeners.KeyListener;
import game.Game;
import units.BasicUnit;
import javax.swing.JComponent;

public class App extends MainFrame
{
    public static final int SETUP = 0;
    public static final int INPUT = 1;
    public static final int PLAYBACK = 2;
    public static final int PROCESSING = 3;
    public static final int MOVEMENT = 4;
    public static final int ATTACK = 5;
    public static final int ROTATE = 6;
    public static final int NONE = 7;
    private static final long serialVersionUID = 1L;
    private JComponent[][] button;
    private RotationDialog rotationDialog;
    private BasicUnit currentSelection;
    private Game game;
    private int currentAction;
    private int overallState;
    private int variationIndex;
    private KeyListener keylistener;
    private ImageManager iManager;
    private Editor editor;
    private File currentFile;
    
    public int getVariationIndex() {
        return this.variationIndex;
    }
    
    public void setVariationIndex(final int i) {
        if (i == 0 || (i > 0 && i < this.game.getNumActions())) {
            this.variationIndex = i;
            return;
        }
        throw new IllegalStateException("Set variationIndex out of bounds");
    }
    
    public KeyListener getKeyListener() {
        if (this.keylistener == null) {
            this.keylistener = new KeyListener(this);
        }
        return this.keylistener;
    }
    
    public Editor getEditor() {
        return this.editor;
    }
    
    public Game getGame() {
        return this.game;
    }
    
    public ImageManager getImageManager() {
        return this.iManager;
    }
    
    public boolean isBlockingRandomized() {
        return this.getRandomizeBlocking().isSelected();
    }
    
    public int getOverallState() {
        return this.overallState;
    }
    
    public void setOverallState(final int i) {
        switch (this.overallState) {
            case 0: {
                if (this.editor == null) {
                    throw new IllegalStateException("Editor was null");
                }
                this.editor.dispose();
                this.editor = null;
                break;
            }
            case 1: {
                if (this.currentAction == 6) {
                    this.rotationDialog.setVisible(false);
                }
                this.setCurrentAction(7);
                this.setCurrentSelection(null);
                break;
            }
            case 2: {
                if (this.getDescription().isEditable()) {
                    this.game.setDescription(this.variationIndex, this.getDescription().getText());
                }
                this.getDescription().setEditable(false);
                break;
            }
        }
        switch (this.overallState = i) {
            case 0: {
                this.game = null;
                (this.editor = new Editor(this)).setVisible(true);
                this.setFile(null);
                break;
            }
            case 1: {
                this.UpdateCurrentAction();
                break;
            }
            case 2: {
                this.getDescription().setText(this.game.getDescription(this.variationIndex));
                break;
            }
            case 3: {
                this.getBack().setEnabled(false);
                this.getForward().setEnabled(false);
                this.getJScrollBar().setEnabled(false);
                this.getUndobutton().setEnabled(false);
                this.getUp().setEnabled(false);
                this.getDown().setEnabled(false);
                break;
            }
        }
    }
    
    public int getCurrentAction() {
        return this.currentAction;
    }
    
    public void setCurrentAction(final int action) {
        if (this.overallState != 1) {
            throw new IllegalStateException("Precondition not met: overallState != INPUT");
        }
        if (this.game == null) {
            throw new IllegalStateException("Game is null");
        }
        if (this.currentAction == 6 && action != 6) {
            this.rotationDialog.setVisible(false);
        }
        if (this.currentAction != 7 && action == 7) {
            this.setCurrentSelection(this.game.getActing());
        }
        this.currentAction = action;
        if (this.currentSelection == null || this.game.getActing() != null) {
            this.setCurrentSelection(this.game.getActing());
        }
    }
    
    public void UpdateCurrentAction() {
        if (this.overallState != 1) {
            throw new IllegalStateException("Precondition not met: overallState != INPUT");
        }
        if (this.game == null) {
            throw new IllegalStateException("Game is null");
        }
        if (this.game.gameOver()) {
            this.setCurrentAction(7);
        }
        else if (this.game.canMove() && (this.game.getActing() == null || this.game.hasMoves(this.game.getActing()))) {
            this.setCurrentAction(4);
        }
        else if (this.game.canAttack() && (this.game.getActing() == null || this.game.hasAttacks(this.game.getActing()))) {
            this.setCurrentAction(5);
        }
        else {
            this.setCurrentAction(6);
        }
    }
    
    public BasicUnit getCurrentSelection() {
        return this.currentSelection;
    }
    
    public void setCurrentSelection(final BasicUnit unit) {
        this.currentSelection = unit;
    }
    
    public File getFile() {
        return this.currentFile;
    }
    
    public void setFile(final File f) {
        this.currentFile = f;
        if (this.currentFile != null) {
            this.setTitle("TAO - Game Recorder - " + this.currentFile.getName());
        }
        else {
            this.setTitle("TAO - Game Recorder");
        }
    }
    
    public RotationDialog getRotationDialog() {
        return this.rotationDialog;
    }
    
    public App() {
        this.button = new JComponent[11][11];
        this.rotationDialog = null;
        this.currentSelection = null;
        this.game = null;
        this.currentAction = 7;
        this.overallState = 0;
        this.variationIndex = 0;
        this.editor = null;
        this.currentFile = null;
        this.initialize();
    }

    private void initialize() {
        this.iManager = ImageManager.getInstance();
        this.rotationDialog = new RotationDialog(this);
        this.editor = new Editor(this);
        this.addWindowListener(new WindowActionListener(this));
        this.addKeyListener(this.getKeyListener());
        this.setFocusable(true);
        this.getAddVariation().addActionListener(new AddVariationButtonListener(this));
        this.getBack().addActionListener(new BackButtonListener(this));
        this.getForward().addActionListener(new ForwardButtonListener(this));
        this.getUndobutton().addActionListener(new UndoButtonListener(this));
        this.getUp().addActionListener(new VariationButtonListener(this, -1));
        this.getDown().addActionListener(new VariationButtonListener(this, 1));
        this.getNewGameButton().addActionListener(new NewGameButtonListener(this));
        this.getOpenButton().addActionListener(new OpenGameListener(this));
        this.getSaveButton().addActionListener(new SaveButtonListener(this));
        this.getSaveAsButton().addActionListener(new SaveAsButtonListener(this));
        this.getLoadFromTextButton().addActionListener(new LoadFromTextButtonListener(this));
        this.getDumpToTextButton().addActionListener(new DumpToTextButtonListener(this));
        this.getDescription().addKeyListener(new TextBoxListener(this));
        this.getMove().addActionListener(new ActionButtonListener(this, 4));
        this.getAttack().addActionListener(new ActionButtonListener(this, 5));
        this.getTurn().addActionListener(new ActionButtonListener(this, 6));
        this.getEndTurn().addActionListener(new EndTurnListener(this));
        this.getExitButton().addActionListener(new WindowActionListener(this));
        this.getAboutButton().addActionListener(new AboutButtonListener(this));
        this.getShortcutButton().addActionListener(new ShortcutButtonListener(this));
        this.getJScrollBar().addAdjustmentListener(new SliderListener(this));
        for (int x = 10; x >= 0; --x) {
            for (int y = 0; y < 11; ++y) {
                this.getGameArea().add(this.getButton(x, y), null);
            }
        }
        this.NewGame();
    }
    
    public JComponent getButton(final int x, final int y) {
        if (this.button[x][y] == null) {
            if (x == 10 && y == 10) {
                this.button[x][y] = new MoveCounter(this);
            }
            else if (Board.isValid(new Location(x, y))) {
                (this.button[x][y] = new TileButton(this, x, y)).addMouseListener(new TileButtonListener(this, new Location(x, y)));
                this.button[x][y].setBackground(Color.green);
            }
            else {
                this.button[x][y] = new JPanel();
            }
        }
        return this.button[x][y];
    }
    
    public void UpdateMenu() {
        if (this.game != null) {
            this.getSaveAsButton().setEnabled(true);
            this.getDumpToTextButton().setEnabled(true);
            if (this.game.canSave()) {
                this.getSaveButton().setEnabled(true);
            }
            else {
                this.getSaveButton().setEnabled(false);
            }
        }
        else {
            this.getSaveAsButton().setEnabled(false);
            this.getSaveButton().setEnabled(false);
            this.getDumpToTextButton().setEnabled(false);
        }
        if (this.getOverallState() == 2) {
            this.getAddVariation().setEnabled(true);
        }
        else {
            this.getAddVariation().setEnabled(false);
        }
    }
    
    public void UpdateTiles() {
        for (int x = 10; x >= 0; --x) {
            for (int y = 0; y < 11; ++y) {
                if (this.getButton(x, y) instanceof TileButton) {
                    ((TileButton)this.getButton(x, y)).Update();
                }
                else if (this.getButton(x, y) instanceof MoveCounter) {
                    ((MoveCounter)this.getButton(x, y)).Update();
                }
            }
        }
        if (this.overallState == 1 && (this.currentAction == 4 || this.currentAction == 5)) {
            for (int x = 10; x >= 0; --x) {
                for (int y = 0; y < 11; ++y) {
                    if (this.getButton(x, y) instanceof TileButton) {
                        switch (this.currentAction) {
                            case 4: {
                                if (this.currentSelection != null && this.currentSelection.canMove(new Location(x, y))) {
                                    this.button[x][y].setBackground(new Color(200, 200, 255));
                                    break;
                                }
                                break;
                            }
                            case 5: {
                                if (this.currentSelection != null && this.currentSelection.canAttack(new Location(x, y))) {
                                    this.button[x][y].setBackground(new Color(200, 200, 255));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        else if (this.overallState == 2) {
            this.getMove().setEnabled(this.game.canMove());
            this.getAttack().setEnabled(this.game.canAttack());
            this.getTurn().setEnabled(true);
            this.getEndTurn().setEnabled(true);
            final Action action = this.game.getAction(this.variationIndex);
            if (action instanceof Move) {
                final Move move = (Move)action;
                final int x2 = move.unit().location().getX();
                final int y2 = move.unit().location().getY();
                final int x3 = move.destination().getX();
                final int y3 = move.destination().getY();
                this.button[x2][y2].setBackground(new Color(140, 140, 178));
                this.button[x3][y3].setBackground(new Color(140, 140, 178));
                this.getMove().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
            else if (action instanceof Attack) {
                final Attack attack = (Attack)action;
                final ArrayList<Location> affected = attack.unit().affectedByAttack(attack.target());
                final int x4 = attack.unit().location().getX();
                final int y4 = attack.unit().location().getY();
                this.button[x4][y4].setBackground(new Color(140, 140, 178));
                for (final Location loc : affected) {
                    final int x5 = loc.getX();
                    final int y5 = loc.getY();
                    if (this.getButton(x5, y5) instanceof TileButton) {
                        if (loc.equals(attack.unit().location())) {
                            this.button[x5][y5].setBackground(new Color(140, 70, 70));
                        }
                        else {
                            this.button[x5][y5].setBackground(new Color(200, 100, 100));
                        }
                    }
                }
                this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                this.getAttack().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                if (attack.endsTurn()) {
                    this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                }
                else {
                    this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
            }
            else if (this.game.getActing() != null) {
                if (action instanceof Turn) {
                    this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    this.getTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                    this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                }
                final int x6 = this.game.getActing().location().getX();
                final int y6 = this.game.getActing().location().getY();
                this.button[x6][y6].setBackground(new Color(200, 200, 100));
            }
            if (action instanceof EndTurn) {
                this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
            }
        }
        if (this.overallState == 1) {
            final Location loc2 = ((TileButtonListener)this.getButton(0, 2).getMouseListeners()[0]).getMouseLocation();
            if (loc2 != null) {
                if (this.highlight(loc2)) {
                    final Color current = this.getButton(loc2.getX(), loc2.getY()).getBackground();
                    this.getButton(loc2.getX(), loc2.getY()).setBackground(current.darker());
                }
                if (this.getCurrentAction() == 5 && this.getCurrentSelection() != null) {
                    final ArrayList<Location> affected2 = this.getCurrentSelection().affectedByAttack(loc2);
                    for (final Location i : affected2) {
                        this.getButton(i.getX(), i.getY()).setBackground(new Color(200, 100, 100));
                    }
                }
            }
        }
    }
    
    public void UpdateScrollBar() {
        if (this.game != null) {
            this.getJScrollBar().removeAdjustmentListener(this.getJScrollBar().getAdjustmentListeners()[0]);
            this.getJScrollBar().setMaximum(this.game.states() - 1);
            this.getJScrollBar().setValue(this.game.currentState());
            this.getJScrollBar().addAdjustmentListener(new SliderListener(this));
        }
    }
    
    public void UpdateSelection() {
        if (this.game == null) {
            throw new IllegalStateException("Can't update selection: game is null");
        }
        switch (this.overallState) {
            case 0: {
                this.setCurrentSelection(null);
                break;
            }
            case 1: {
                if (this.currentSelection == null) {
                    this.setCurrentSelection(this.game.getActing());
                    break;
                }
                break;
            }
            case 2: {
                this.setCurrentSelection(null);
                break;
            }
            case 3: {
                this.setCurrentSelection(null);
                break;
            }
        }
    }
    
    public boolean highlight(final Location loc) {
        if (this.game == null) {
            return false;
        }
        if (this.getCurrentSelection() == null) {
            final Unit unit = this.getGame().getBoard().unitAt(loc);
            if (unit == null || !(unit instanceof BasicUnit)) {
                return false;
            }
            final BasicUnit bu = (BasicUnit)unit;
            return bu.getPlayer() == this.getGame().getCurrentPlayer() && (bu.canAttack() || bu.canMove());
        }
        else {
            if (this.overallState == 1) {
                switch (this.getCurrentAction()) {
                    case 4: {
                        if (this.getCurrentSelection().canMove(loc)) {
                            return true;
                        }
                        break;
                    }
                    case 5: {
                        if (this.getCurrentSelection().canAttack(loc)) {
                            return true;
                        }
                        break;
                    }
                }
                return false;
            }
            return false;
        }
    }
    
    public void NewGame() {
        this.setOverallState(0);
        this.UpdateGui();
        this.setVisible(true);
    }
    
    public void NewGame(final String s) {
        this.setCurrentSelection(null);
        this.setOverallState(3);
        this.game = new Game(this, s);
        this.setOverallState(1);
    }
    
    public void NewGame(final String s, final boolean oldVersionFlag) {
        this.setCurrentSelection(null);
        this.setOverallState(3);
        this.game = new Game(this, s, true);
        this.setOverallState(1);
    }
    
    public void StartGame() {
        String s1 = this.editor.getSouthPlayerName().getText();
        String s2 = this.editor.getNorthPlayerName().getText();
        if (s1.length() > 20) {
            s1 = s1.substring(0, 20);
        }
        if (s2.length() > 20) {
            s2 = s2.substring(0, 20);
        }
        final Player p1 = new Player(s1, this.editor.getSouthSetup());
        final Player p2 = new Player(s2, this.editor.getNorthSetup());
        final boolean goesFirst = this.editor.goesFirst();
        this.setOverallState(3);
        this.game = new Game(this, p1, p2, goesFirst);
        this.setOverallState(1);
        this.UpdateGui();
    }
    
    public void back(final int moves) {
        if (this.getOverallState() != 0 && !this.getGame().atBeginning()) {
            this.setOverallState(3);
            final int var = this.getGame().back(moves);
            this.setVariationIndex(var);
            this.setOverallState(2);
            this.UpdateGui();
        }
    }
    
    public void toBeginning() {
        if (this.getOverallState() != 0 && !this.getGame().atBeginning()) {
            this.setOverallState(3);
            this.getGame().toBeginning();
            this.setOverallState(2);
            this.UpdateGui();
        }
    }
    
    public void forward(final int moves) {
        if (this.getOverallState() == 2 && !this.getGame().atEnd()) {
            this.setOverallState(3);
            for (int i = 0; i < moves && !this.game.atEnd(); ++i) {
                this.getGame().next(0);
            }
            if (this.getGame().atEnd()) {
                this.setOverallState(1);
            }
            else {
                this.setOverallState(2);
            }
            this.UpdateGui();
        }
    }
    
    public void toEnd() {
        if (this.getOverallState() == 2 && !this.getGame().atEnd()) {
            this.setOverallState(3);
            while (!this.game.atEnd()) {
                this.getGame().next(0);
            }
            this.setOverallState(1);
            this.setVariationIndex(0);
            this.UpdateGui();
        }
    }
    
    public void UpdateFocus() {
        this.setFocusable(true);
        if (this.isFocused() || (this.editor != null && this.editor.isFocused()) || (this.getDescription().hasFocus() && this.overallState == 2) || (this.rotationDialog != null && this.rotationDialog.isFocused())) {
            if (!this.rotationDialog.isVisible()) {
                this.requestFocus();
            }
            return;
        }
        if (this.overallState == 0) {
            this.editor.requestFocus();
        }
        else if (this.currentAction == 6) {
            this.rotationDialog.requestFocus();
        }
        else {
            this.requestFocus();
        }
    }
    
    public void UpdateGui() {
        this.UpdateTextPanel();
        this.UpdateButtons();
        this.UpdateMenu();
        this.UpdateScrollBar();
        if (this.game != null) {
            this.UpdateSelection();
        }
        this.UpdateTiles();
        this.UpdateFocus();
    }
    
    public void TilePressedAction(final Location loc) {  //UPDATES WHEN TILE IS PRESSED
        switch (this.getOverallState()) {
            case 0: {
                if (Setup.isValid(loc)) {
                    final Setup setup = this.getEditor().getSouthSetup();
                    final char newChar = this.getEditor().getSelectedUnit();
                    final Character cur = setup.get(loc);
                    if (cur == null && newChar != ' ') {
                        setup.add(loc, newChar);
                    }
                    else if (cur != null && newChar == ' ') {
                        setup.remove(loc);
                    }
                    else if (cur != null && newChar != ' ') {
                        setup.remove(loc);
                        if (cur != newChar) {
                            setup.add(loc, newChar);
                        }
                    }
                }
                else if (Setup.isValid(Setup.mirror(loc))) {
                    final Setup setup = this.getEditor().getNorthSetup();
                    final char newChar = this.getEditor().getSelectedUnit();
                    final Location mirror = Setup.mirror(loc);
                    final Character cur2 = setup.get(mirror);
                    if (cur2 == null && newChar != ' ') {
                        setup.add(mirror, newChar);
                    }
                    else if (cur2 != null && newChar == ' ') {
                        setup.remove(mirror);
                    }
                    else if (cur2 != null && newChar != ' ') {
                        setup.remove(mirror);
                        if (cur2 != newChar) {
                            setup.add(mirror, newChar);
                        }
                    }
                }
                this.UpdateTiles();
                this.getEditor().UpdateButtons();
                break;
            }
            case 1: {
                final Unit unit = this.getGame().getBoard().unitAt(loc);
                if (!this.highlight(loc)) {
                    if (this.getCurrentSelection() != null && this.getCurrentSelection().location().equals(loc)) {
                        if (this.getGame().getActing() == null) {
                            this.setCurrentSelection(null);
                        }
                    }
                    else if (this.getGame().getActing() == null) {
                        this.setCurrentSelection(null);
                        if (unit instanceof BasicUnit && ((BasicUnit)unit).getPlayer() == this.getGame().getCurrentPlayer() && (((BasicUnit)unit).canMove() || ((BasicUnit)unit).canAttack())) {
                            this.setCurrentSelection((BasicUnit)unit);
                        }
                    }
                }
                else if (this.getCurrentSelection() == null) {
                    if (unit instanceof BasicUnit && ((BasicUnit)unit).getPlayer() == this.getGame().getCurrentPlayer() && (((BasicUnit)unit).canMove() || ((BasicUnit)unit).canAttack())) {
                        this.setCurrentSelection((BasicUnit)unit);
                    }
                }
                else {
                    Action action = null;
                    switch (this.getCurrentAction()) {
                        case 4: {
                            action = new Move(this.getGame().getBoard(), this.getCurrentSelection().location(), loc);
                            break;
                        }
                        case 5: {
                            action = new Attack(this.getGame().getBoard(), this.getCurrentSelection().location(), loc);
                            break;
                        }
                        default: {
                            return;
                        }
                    }
                    if (this.getGame().canPlay(action)) {
                        this.setOverallState(3);
                        this.getGame().Play(action);
                        this.setOverallState(1);
                        this.UpdateGui();
                    }
                }
                this.UpdateTiles();
                break;
            }
        }
        this.UpdateButtons();         //updates the buttons for current selection
    }
    
    public void UpdateButtons() {
        Label_1543: {
            switch (this.overallState) {
                case 0: {
                    this.getBack().setEnabled(false);
                    this.getForward().setEnabled(false);
                    this.getJScrollBar().setEnabled(false);
                    this.getUndobutton().setEnabled(false);
                    this.getUp().setEnabled(false);
                    this.getDown().setEnabled(false);
                    this.getMove().setEnabled(true);
                    this.getAttack().setEnabled(true);
                    this.getTurn().setEnabled(true);
                    this.getEndTurn().setEnabled(true);
                    this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    break;
                }
                case 1: {
                    if (!this.game.atBeginning()) {
                        this.getUndobutton().setEnabled(this.game.atEnd());
                        this.getBack().setEnabled(true);
                    }
                    this.getJScrollBar().setEnabled(true);
                    this.getForward().setEnabled(false);
                    this.getUp().setEnabled(false);
                    this.getDown().setEnabled(false);
                    this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    if (this.currentAction != 7) {
                        this.getMove().setEnabled(true);
                        this.getAttack().setEnabled(true);
                        this.getTurn().setEnabled(true);
                        this.getEndTurn().setEnabled(true);
                    }
                    switch (this.currentAction) {
                        case 4: {   // case of moving
                            if (!this.game.canMove()) {
                                throw new IllegalArgumentException("Can't move");
                            }
                            this.getMove().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                            if (this.game.canAttack()) {
                                this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            else {
                                this.getAttack().setEnabled(false);
                                this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            this.getEndTurn().setEnabled(true);
                            break Label_1543;
                        }
                        case 5: {  //case of attacking
                            if (this.game.canMove()) {
                                this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            if (!this.game.canAttack()) {  //used to be flipped with move above
                                throw new IllegalArgumentException("Can't attack");
                            }
                            else {
                                this.getMove().setEnabled(false);
                                this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            this.getAttack().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                            this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            this.getEndTurn().setEnabled(true);
                            break Label_1543;
                        }
                        case 6: { //case of changing directions
                            if (this.game.canMove()) {
                                this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            else {
                                this.getMove().setEnabled(false);
                                this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            if (this.game.canAttack()) {
                                this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            else {
                                this.getAttack().setEnabled(false);
                                this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            }
                            this.getTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                            this.getEndTurn().setEnabled(true);

                            if (this.currentSelection != null && this.currentSelection.canRotate()) {
                                this.rotationDialog.setVisible(true);
                                break Label_1543;
                            }
                            break Label_1543;
                        }
                        default: {
                            break Label_1543;
                        }
                    }
                }
                case 2: {
                    if (!this.game.atBeginning()) {
                        this.getBack().setEnabled(true);
                    }
                    this.getForward().setEnabled(true);
                    this.getJScrollBar().setEnabled(true);
                    this.getUndobutton().setEnabled(false);
                    this.getDown().setEnabled(this.game.getNumActions() > this.variationIndex + 1);
                    this.getUp().setEnabled(this.variationIndex > 0);
                    this.getMove().setEnabled(this.game.canMove());
                    this.getAttack().setEnabled(this.game.canAttack());
                    this.getTurn().setEnabled(true);
                    this.getEndTurn().setEnabled(true);
                    final Action action = this.game.getAction(this.variationIndex);
                    if (action instanceof Move) {
                        final Move move = (Move)action;
                        final int x1 = move.unit().location().getX();
                        final int y1 = move.unit().location().getY();
                        final int x2 = move.destination().getX();
                        final int y2 = move.destination().getY();
                        this.button[x2][y2].setBackground(new Color(140, 140, 178));
                        this.getMove().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                        this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                    }
                    else if (action instanceof Attack) {
                        final Attack attack = (Attack)action;
                        final ArrayList<Location> affected = attack.unit().affectedByAttack(attack.target());
                        final int x3 = attack.unit().location().getX();
                        final int y3 = attack.unit().location().getY();
                        this.button[x3][y3].setBackground(new Color(140, 140, 178));
                        for (final Location loc : affected) {
                            final int x4 = loc.getX();
                            final int y4 = loc.getY();
                            if (this.getButton(x4, y4) instanceof TileButton) {
                                if (loc.equals(attack.unit().location())) {
                                    this.button[x4][y4].setBackground(new Color(140, 70, 70));
                                }
                                else {
                                    this.button[x4][y4].setBackground(new Color(200, 100, 100));
                                }
                            }
                        }
                        this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        this.getAttack().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                        this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        if (attack.endsTurn()) {
                            this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                        }
                        else {
                            this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        }
                    }
                    else if (this.game.getActing() != null) {
                        if (action instanceof Turn) {
                            this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                            this.getTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                            this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                        }
                        final int x5 = this.game.getActing().location().getX();
                        final int y5 = this.game.getActing().location().getY();
                        this.button[x5][y5].setBackground(new Color(200, 200, 100));
                    }
                    if (action instanceof EndTurn) {
                        this.getMove().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        this.getAttack().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        this.getTurn().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        this.getEndTurn().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                        break;
                    }
                    break;
                }
                case 3: {
                    this.getBack().setEnabled(false);
                    this.getForward().setEnabled(false);
                    this.getJScrollBar().setEnabled(false);
                    this.getUndobutton().setEnabled(false);
                    this.getUp().setEnabled(false);
                    this.getDown().setEnabled(false);
                    break;
                }
            }
        }
    }
    
    public void UpdateTextPanel() {
        if (this.game != null) {
            final Location loc = ((TileButtonListener)this.getButton(0, 2).getMouseListeners()[0]).getMouseLocation();
            if (loc != null) {
                final Unit unit = this.game.getBoard().unitAt(loc);
                if (unit != null) {
                    if (this.getDescription().isEditable()) {
                        this.game.setDescription(this.variationIndex, this.getDescription().getText());
                    }
                    this.getDescription().setText(unit.toDisplay());
                    this.getDescription().setEditable(false);
                    return;
                }
            }
            if (this.overallState == 2) {
                if (!this.getDescription().isEditable()) {
                    this.getDescription().setText(this.game.getDescription(this.variationIndex));
                }
                this.getDescription().setEditable(true);
            }
            else {
                this.getDescription().setText("");
                this.getDescription().setEditable(false);
            }
        }
    }
}
