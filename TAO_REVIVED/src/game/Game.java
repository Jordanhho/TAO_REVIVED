// 
// Decompiled by Procyon v0.5.30
// 

package game;

import actions.Turn;
import util.Direction;
import actions.Move;
import units.Furgon;
import actions.Attack;
import java.util.Stack;
import actions.EndTurn;
import effects.Paralyze;
import effects.Effect;
import util.Location;
import java.util.Iterator;
import units.Unit;
import actions.Action;
import units.BasicUnit;
import actions.ActionTreeNode;
import app.App;
import java.awt.Color;

public class Game
{
    private Color color1;
    private Color color2;
    private final Board theBoard;
    private final Player player1;
    private final Player player2;
    private final boolean goesFirst;
    private final App app;
    private ActionTreeNode actions;
    private boolean whoseTurn;
    private BasicUnit acting;
    private boolean canMove;
    private boolean canAttack;
    private int halfturns;
    private String originalActions;
    
    public boolean canMove() {
        return this.canMove;
    }
    
    public boolean canAttack() {
        return this.canAttack;
    }
    
    public BasicUnit getActing() {
        return this.acting;
    }
    
    public int halfturns() {
        return this.halfturns;
    }
    
    public Action getAction(final int i) {
        return this.actions.getChild(i).getAction();
    }
    
    public int getNumActions() {
        return this.actions.numChildren();
    }
    
    public String getDescription(final int i) {
        return this.actions.getChild(i).getDescription();
    }
    
    public void setDescription(final int i, final String s) {
        this.actions.getChild(i).setDescription(s);
    }
    
    public Game(final App app, final Player p1, final Player p2, final boolean p1First) {
        this.color1 = Color.red;
        this.color2 = Color.blue;
        this.originalActions = null;
        this.app = app;
        this.player1 = p1;
        this.player2 = p2;
        this.theBoard = new Board(p1, p2);
        this.actions = new ActionTreeNode();
        this.halfturns = 0;
        this.goesFirst = p1First;
        this.whoseTurn = this.goesFirst;
        this.acting = null;
        this.canMove = true;
        this.canAttack = true;
        Player other;
        if (this.goesFirst) {
            other = this.player2;
        }
        else {
            other = this.player1;
        }
        for (final Unit next : this.theBoard.getUnits()) {
            if (next instanceof BasicUnit) {
                ((BasicUnit)next).onEndTurn(other);
            }
        }
    }
    
    public Board getBoard() {
        return this.theBoard;
    }
    
    public Player getPlayer1() {
        return this.player1;
    }
    
    public Player getPlayer2() {
        return this.player2;
    }
    
    public Color getColor(final Player p) {
        if (p == this.player1) {
            return this.color1;
        }
        if (p == this.player2) {
            return this.color2;
        }
        throw new IllegalArgumentException("Illegal Player");
    }
    
    public Player getCurrentPlayer() {
        if (this.whoseTurn) {
            return this.player1;
        }
        return this.player2;
    }
    
    private void NextTurn() {
        for (final Unit next : this.theBoard.getUnits()) {
            if (next instanceof BasicUnit) {
                ((BasicUnit)next).onEndTurn(this.getCurrentPlayer());
            }
        }
        int recovery = 0;
        if (this.acting != null) {
            if (this.acting.mobile()) {
                if (!this.canMove) {
                    recovery += this.acting.baseStats().recovery / 2;
                }
                if (!this.canAttack) {
                    recovery += (this.acting.baseStats().recovery + 1) / 2;
                }
            }
            else if (!this.canAttack) {
                recovery += this.acting.baseStats().recovery;
            }
            this.acting.setRecovery(recovery);
        }
        this.whoseTurn = !this.whoseTurn;
        this.canMove = true;
        this.canAttack = true;
        this.acting = null;
        ++this.halfturns;
    }
    
    public boolean canPlay(final Action action) {
        return (this.acting == null || action.unit() == null || this.acting == action.unit()) && (!action.isMove() || this.canMove) && (!action.isAttack() || this.canAttack) && (action.unit() == null || action.unit().getPlayer() == this.getCurrentPlayer()) && action.isValid();
    }
    
    public void Play(final int child) {
        final Action action = this.actions.getChild(child).getAction();
        if (!this.canPlay(action)) {
            throw new IllegalArgumentException("Illegal action: " + action);
        }
        if (action.unit() != null) {
            this.acting = action.unit();
        }
        action.act(this.app);
        if (action.isMove()) {
            this.canMove = false;
        }
        if (action.isAttack()) {
            this.canAttack = false;
        }
        for (final Unit next : this.theBoard.getUnits()) {
            if (next instanceof BasicUnit) {
                ((BasicUnit)next).onEndRound();
            }
        }
        if (action.endsTurn()) {
            this.NextTurn();
        }
        this.actions = this.actions.getChild(child);
    }
    
    public void Play(final Action action) {
        if (!this.canPlay(action)) {
            throw new IllegalArgumentException("Illegal action: " + action);
        }
        if (action.unit() != null) {
            this.acting = action.unit();
        }
        action.act(this.app);
        if (action.isMove()) {
            this.canMove = false;
        }
        if (action.isAttack()) {
            this.canAttack = false;
        }
        for (final Unit next : this.theBoard.getUnits()) {
            if (next instanceof BasicUnit) {
                ((BasicUnit)next).onEndRound();
            }
        }
        if (action.endsTurn()) {
            this.NextTurn();
        }
        this.actions = this.actions.Add(action);
        System.out.println("PRINTING OUT ACTION TREE!");
        System.out.println("-----------------------------------------\n");
        System.out.println(actions.toString());
        System.out.println("\n-----------------------------------------");
    }
    
    public boolean hasMoves(final BasicUnit unit) {
        if (!this.canMove) {
            return false;
        }
        for (int x = 0; x < 11; ++x) {
            for (int y = 0; y < 11; ++y) {
                final Location loc = new Location(x, y);
                if (Board.isValid(loc) && unit.canMove(loc)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean hasAttacks(final BasicUnit unit) {
        if (!this.canAttack) {
            return false;
        }
        for (int x = 0; x < 11; ++x) {
            for (int y = 0; y < 11; ++y) {
                final Location loc = new Location(x, y);
                if (Board.isValid(loc) && unit.canAttack(loc)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean gameOver() {
        boolean player1HasUnits = false;
        boolean player2HasUnits = false;
        for (final Unit unit : this.theBoard.getUnits()) {
            if (unit instanceof BasicUnit && ((BasicUnit)unit).mobile()) {
                boolean paralyzed = false;
                final BasicUnit u = (BasicUnit)unit;
                for (final Effect e : u.getEffects()) {
                    if (e instanceof Paralyze) {
                        paralyzed = true;
                        break;
                    }
                }
                if (paralyzed) {
                    continue;
                }
                if (u.getPlayer() == this.player1) {
                    player1HasUnits = true;
                }
                else {
                    player2HasUnits = true;
                }
            }
        }
        if (!player1HasUnits || !player2HasUnits) {
            return true;
        }
        if (this.actions.getDepth() >= 6) {
            ActionTreeNode temp = this.actions;
            for (int i = 0; i < 6; ++i) {
                temp = temp.getParent();
                if (!(temp.getAction() instanceof EndTurn)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public String toString() {
        int first;
        if (this.goesFirst) {
            first = 1;
        }
        else {
            first = 0;
        }
        String s = new StringBuilder().append(this.player1).append('\n').append(this.player2).append('\n').append(first).append('\n').toString();
        s = String.valueOf(s) + this.actions.getRoot().toString();
        return s;
    }
    
    public String dumpToText() {
        int first;
        if (this.goesFirst) {
            first = 1;
        }
        else {
            first = 0;
        }
        String s = new StringBuilder().append(this.player1).append('\n').append(this.player2).append('\n').append(first).append('\n').toString();
        String actionsString;
        int i;
        for (actionsString = this.actions.getRoot().toString(); actionsString.length() > 45; actionsString = actionsString.substring(45 + i)) {
            for (i = 0; actionsString.charAt(44 + i) == '\\'; ++i) {}
            s = String.valueOf(s) + actionsString.substring(0, 45 + i);
            s = String.valueOf(s) + "\n";
        }
        s = String.valueOf(s) + actionsString;
        return s;
    }
    
    public boolean canSave() {
        if (this.originalActions == null) {
            return true;
        }
        String sActions = "";
        sActions = String.valueOf(sActions) + this.actions.getRoot().toString();
        return !sActions.equals(this.originalActions);
    }
    
    public void onSave() {
        String sActions = "";
        sActions = String.valueOf(sActions) + this.actions.getRoot().toString();
        this.originalActions = sActions;
    }
    
    public int[] toBeginning() {
        final int[] path = this.actions.getPath();
        this.theBoard.toBeginning();
        this.actions = this.actions.getRoot();
        this.halfturns = 0;
        this.whoseTurn = this.goesFirst;
        this.acting = null;
        this.canMove = true;
        this.canAttack = true;
        Player other;
        if (this.goesFirst) {
            other = this.player2;
        }
        else {
            other = this.player1;
        }
        for (final Unit next : this.theBoard.getUnits()) {
            if (next instanceof BasicUnit) {
                ((BasicUnit)next).onEndTurn(other);
            }
        }
        return path;
    }
    
    public boolean atEnd() {
        return this.actions.numChildren() == 0;
    }
    
    public boolean atBeginning() {
        return this.actions.isRoot();
    }
    
    public int states() {
        return this.actions.getVariationLength() + 1;
    }
    
    public int currentState() {
        return this.actions.getVariationDepth();
    }
    
    public int getDepth() {
        return this.actions.getDepth();
    }
    
    public int prev() {
        final int[] path = this.actions.getPath();
        this.toBeginning();
        for (int i = 0; i < path.length - 1; ++i) {
            this.next(path[i]);
        }
        if (path.length > 0) {
            return path[path.length - 1];
        }
        return 0;
    }
    
    public int back(final int n) {
        final int[] path = this.actions.getPath();
        this.toBeginning();
        for (int i = 0; i < path.length - n; ++i) {
            this.next(path[i]);
        }
        if (path.length - n >= 0 && n != 0) {
            return path[path.length - n];
        }
        return 0;
    }
    
    public void undo() {
        this.actions = this.actions.Remove();
        this.back(0);
    }
    
    public void next(final int i) {
        this.Play(i);
    }
    
    public Game(final App app, String s) {
        this.color1 = Color.red;
        this.color2 = Color.blue;
        this.originalActions = null;
        this.app = app;
        int j;
        int i;
        for (i = (j = 0); s.charAt(j) != '\n'; ++j) {}
        String ID1 = s.substring(i, j);
        i = j + 1;
        final Setup s2 = new Setup(false);
        while (s.charAt(i) != '\n') {
            final int x = s.charAt(i++) - '0';
            final int y = s.charAt(i++) - '0';
            final char unit = s.charAt(i++);
            s2.add(new Location(x, y), unit);
        }
        if (ID1.length() > 20) {
            ID1 = ID1.substring(0, 20);
        }
        this.player1 = new Player(ID1, s2);
        for (j = ++i; s.charAt(j) != '\n'; ++j) {}
        String ID2 = s.substring(i, j);
        i = j + 1;
        final Setup s3 = new Setup(false);
        while (s.charAt(i) != '\n') {
            final int x2 = s.charAt(i++) - '0';
            final int y2 = s.charAt(i++) - '0';
            final char unit2 = s.charAt(i++);
            s3.add(new Location(x2, y2), unit2);
        }
        if (ID2.length() > 20) {
            ID2 = ID2.substring(0, 20);
        }
        this.player2 = new Player(ID2, s3);
        ++i;
        if (s.charAt(i) == '1') {
            this.goesFirst = true;
        }
        else {
            if (s.charAt(i) != '0') {
                throw new IllegalArgumentException("Bad or corrupted input");
            }
            this.goesFirst = false;
        }
        for (j = ++i; s.charAt(j) != '\n'; ++j) {}
        i = j + 1;
        this.theBoard = new Board(this.player1, this.player2);
        this.actions = new ActionTreeNode();
        this.whoseTurn = this.goesFirst;
        this.acting = null;
        this.canMove = true;
        this.canAttack = true;
        Player other;
        if (this.goesFirst) {
            other = this.player2;
        }
        else {
            other = this.player1;
        }
        for (final Unit next : this.theBoard.getUnits()) {
            if (next instanceof BasicUnit) {
                ((BasicUnit)next).onEndTurn(other);
            }
        }
        this.originalActions = "";
        j = i;
        final Stack<String> variations = new Stack<String>();
        final Stack<Integer> variationIndex = new Stack<Integer>();
        while (j < s.length() || !variations.empty()) {
            if (j >= s.length()) {
                s = variations.pop();
                i = 0;
                j = 0;
                final int branchStartsHere = variationIndex.pop();
                this.toBeginning();
                for (int depth = 0; depth < branchStartsHere; ++depth) {
                    this.next(this.actions.numChildren() - 1);
                }
            }
            switch (s.charAt(j++)) {
                case 'A': {
                    int input;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x4 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y4 = input - 48;
                    char info;
                    do {
                        info = s.charAt(j++);
                    } while (info == '\n');
                    final Location loc1 = new Location(x3, y3);
                    final Location loc2 = new Location(x4, y4);
                    Action action = new Attack(this.theBoard, loc1, loc2, info);
                    if (this.theBoard.unitAt(loc1) instanceof Furgon) {
                        final Furgon f = (Furgon)this.theBoard.unitAt(loc1);
                        if (!f.canAttack(loc2)) {
                            action = new Attack(this.theBoard, loc1, loc1, info);
                        }
                    }
                    this.Play(action);
                    continue;
                }
                case 'M': {
                    int input;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x4 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y4 = input - 48;
                    final Location loc1 = new Location(x3, y3);
                    final Location loc2 = new Location(x4, y4);
                    final Action action = new Move(this.theBoard, loc1, loc2);
                    this.Play(action);
                    continue;
                }
                case 'T': {
                    int input;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int d = input - 48;
                    final Location loc1 = new Location(x3, y3);
                    final Direction dir = new Direction(d);
                    final Action action = new Turn(this.theBoard, loc1, dir);
                    this.Play(action);
                    continue;
                }
                case 'E': {
                    final Action action = new EndTurn();
                    this.Play(action);
                    continue;
                }
                case '\n': {
                    String substring = s.substring(i, j - 1);
                    for (int count = 0; count < substring.length(); ++count) {
                        if (substring.charAt(count) == '\n') {
                            this.originalActions = String.valueOf(this.originalActions) + substring.substring(0, count);
                            substring = substring.substring(count + 1);
                            count = -1;
                        }
                    }
                    this.originalActions = String.valueOf(this.originalActions) + substring;
                    i = j;
                    continue;
                }
                case '(': {
                    int k = j;
                    int parenCount = 1;
                    while (parenCount > 0) {
                        if (s.charAt(k) == '(') {
                            ++parenCount;
                        }
                        else if (s.charAt(k) == ')') {
                            --parenCount;
                        }
                        ++k;
                    }
                    final String variation = s.substring(j, k - 1);
                    s = String.valueOf(s.substring(0, j - 1)) + s.substring(k);
                    --j;
                    variations.push(variation);
                    variationIndex.push(this.actions.getDepth());
                    continue;
                }
                case '\"': {
                    boolean done = false;
                    final int start = j;
                    while (!done) {
                        char c = s.charAt(j++);
                        if (c == '\\') {
                            c = s.charAt(j++);
                        }
                        else {
                            if (c != '\"') {
                                continue;
                            }
                            done = true;
                        }
                    }
                    String description = s.substring(start, j - 1);
                    description = description.replace("\n", "");
                    description = description.replace("\\\"", "\"");
                    description = description.replace("\\n", "\n");
                    description = description.replace("\\\\", "\\");
                    this.actions.setDescription(description);
                    continue;
                }
                default: {
                    throw new IllegalArgumentException("Bad or corrupted input");
                }
            }
        }
        this.toBeginning();
        while (this.actions.numChildren() > 0) {
            this.next(0);
        }
    }
    
    public Game(final App app, final String s, final boolean oldVersionFlag) {
        this.color1 = Color.red;
        this.color2 = Color.blue;
        this.originalActions = null;
        this.app = app;
        int j;
        int i;
        for (i = (j = 0); s.charAt(j) != '\n'; ++j) {}
        String ID1 = s.substring(i, j);
        i = j + 1;
        final Setup s2 = new Setup(false);
        while (s.charAt(i) != '\n') {
            final int x = s.charAt(i++) - '0';
            final int y = s.charAt(i++) - '0';
            final char unit = s.charAt(i++);
            s2.add(new Location(x, y), unit);
        }
        if (ID1.length() > 20) {
            ID1 = ID1.substring(0, 20);
        }
        this.player1 = new Player(ID1, s2);
        for (j = ++i; s.charAt(j) != '\n'; ++j) {}
        String ID2 = s.substring(i, j);
        i = j + 1;
        final Setup s3 = new Setup(false);
        while (s.charAt(i) != '\n') {
            final int x2 = s.charAt(i++) - '0';
            final int y2 = s.charAt(i++) - '0';
            final char unit2 = s.charAt(i++);
            s3.add(new Location(x2, y2), unit2);
        }
        if (ID2.length() > 20) {
            ID2 = ID2.substring(0, 20);
        }
        this.player2 = new Player(ID2, s3);
        ++i;
        if (s.charAt(i) == '1') {
            this.goesFirst = true;
        }
        else {
            if (s.charAt(i) != '0') {
                throw new IllegalArgumentException("Bad or corrupted input");
            }
            this.goesFirst = false;
        }
        for (j = ++i; s.charAt(j) != '\n'; ++j) {}
        final String blockingInfo = s.substring(i, j);
        int blockingIndex = 0;
        i = j + 1;
        this.theBoard = new Board(this.player1, this.player2);
        this.actions = new ActionTreeNode();
        this.whoseTurn = this.goesFirst;
        this.acting = null;
        this.canMove = true;
        this.canAttack = true;
        Player other;
        if (this.goesFirst) {
            other = this.player2;
        }
        else {
            other = this.player1;
        }
        for (final Unit next : this.theBoard.getUnits()) {
            if (next instanceof BasicUnit) {
                ((BasicUnit)next).onEndTurn(other);
            }
        }
        this.originalActions = "";
        j = i;
        Direction versionOneDirection = null;
        while (j < s.length()) {
            switch (s.charAt(j++)) {
                case 'A': {
                    int input;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x4 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y4 = input - 48;
                    final Location loc1 = new Location(x3, y3);
                    final Location loc2 = new Location(x4, y4);
                    try {
                        Action action = new Attack(this.theBoard, loc1, loc2, ' ');
                        if (this.theBoard.unitAt(loc1) instanceof Furgon) {
                            final Furgon f = (Furgon)this.theBoard.unitAt(loc1);
                            if (!f.canAttack(loc2)) {
                                action = new Attack(this.theBoard, loc1, loc1, '0');
                            }
                        }
                        this.Play(action);
                    }
                    catch (IllegalStateException e) {
                        final int send = Integer.valueOf(e.getMessage());
                        this.toBeginning();
                        while (!this.atEnd()) {
                            this.next(0);
                        }
                        final char data = this.ProcessBlockingInfo(blockingInfo.substring(blockingIndex, blockingIndex + send));
                        final Action action = new Attack(this.theBoard, loc1, loc2, data);
                        this.Play(action);
                        blockingIndex += send;
                    }
                    continue;
                }
                case 'M': {
                    int input;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x4 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y4 = input - 48;
                    final Location loc1 = new Location(x3, y3);
                    final Location loc2 = new Location(x4, y4);
                    versionOneDirection = ((BasicUnit)this.theBoard.unitAt(loc1)).direction();
                    final Action action = new Move(this.theBoard, loc1, loc2);
                    this.Play(action);
                    continue;
                }
                case 'T': {
                    int input;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int x3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int y3 = input - 48;
                    do {
                        input = s.charAt(j++);
                    } while (input == 10);
                    final int d = input - 48;
                    final Location loc1 = new Location(x3, y3);
                    final Direction dir = new Direction(d);
                    Action action;
                    if (dir.equals(this.acting.direction())) {
                        action = new EndTurn();
                    }
                    else {
                        action = new Turn(this.theBoard, loc1, dir);
                    }
                    this.Play(action);
                    continue;
                }
                case 'E': {
                    Action action;
                    if (this.acting == null) {
                        action = new EndTurn();
                    }
                    else if (!this.acting.direction().equals(versionOneDirection) && (this.actions.getAction() instanceof Move || (this.actions.getAction() instanceof Attack && Math.abs(((Attack)this.actions.getAction()).unit().location().getX() - ((Attack)this.actions.getAction()).target().getX()) == Math.abs(((Attack)this.actions.getAction()).unit().location().getY() - ((Attack)this.actions.getAction()).target().getY()) && this.actions.getParent().getAction() instanceof Move))) {
                        action = new Turn(this.theBoard, this.acting.location(), versionOneDirection);
                    }
                    else {
                        action = new EndTurn();
                    }
                    this.Play(action);
                    continue;
                }
                case '\n': {
                    String substring = s.substring(i, j - 1);
                    for (int count = 0; count < substring.length(); ++count) {
                        if (substring.charAt(count) == '\n') {
                            substring = substring.substring(count + 1);
                            count = -1;
                        }
                    }
                    i = j;
                    continue;
                }
                default: {
                    throw new IllegalArgumentException("Bad or corrupted input");
                }
            }
        }
    }
    
    private char ProcessBlockingInfo(final String s) {
        final int[] data = new int[4];
        for (int i = 0; i < 4; ++i) {
            data[i] = 0;
        }
        for (int i = 0; i < s.length(); ++i) {
            data[i] = s.charAt(i) - '0';
        }
        return (char)(48 + data[0] * 1 + data[1] * 2 + data[2] * 4 + data[3] * 8);
    }
}
