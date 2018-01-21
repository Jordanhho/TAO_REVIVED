// 
// Decompiled by Procyon v0.5.30
// 

package units;

import effects.Barrier;
import effects.Focus;
import effects.Paralyze;
import effects.Poison;
import game.BlockingInfo;
import java.util.ListIterator;
import game.Board;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import effects.Effect;
import java.util.ArrayList;
import util.Direction;
import util.Location;
import game.Player;

public abstract class BasicUnit implements Unit
{
    private BaseStats stats;
    private final Player owner;
    private Location loc;
    private Direction dir;
    private ArrayList<Effect> effects;
    private ArrayList<Effect> effectQueue;
    private double blockingBonus;
    private int hitPoints;
    private int recoveryTime;
    private BufferedImage image;
    
    public static BasicUnit GenerateUnit(final char type, final Player owner, final Location loc) {
        switch (type) {
            case 'C': {
                return new Cleric(owner, loc);
            }
            case 'K': {
                return new Knight(owner, loc);
            }
            case 'S': {
                return new Scout(owner, loc);
            }
            case 'L': {
                return new LightningWard(owner, loc);
            }
            case 'b': {
                return new BarrierWard(owner, loc);
            }
            case 'W': {
                return new DarkMagicWitch(owner, loc);
            }
            case 'E': {
                return new Enchantress(owner, loc);
            }
            case 'A': {
                return new Assassin(owner, loc);
            }
            case 'P': {
                return new Pyromancer(owner, loc);
            }
            case 'D': {
                return new DragonSpeakerMage(owner, loc);
            }
            case 'M': {
                return new MudGolem(owner, loc);
            }
            case 'G': {
                return new GolemAmbusher(owner, loc);
            }
            case 'f': {
                return new FrostGolem(owner, loc);
            }
            case 's': {
                return new StoneGolem(owner, loc);
            }
            case 'T': {
                return new DragonTyrant(owner, loc);
            }
            case 'Z': {
                return new Berserker(owner, loc);
            }
            case 'B': {
                return new BeastRider(owner, loc);
            }
            case 'p': {
                return new PoisonWisp(owner, loc);
            }
            case 'F': {
                return new Furgon(owner, loc);
            }
            default: {
                throw new IllegalArgumentException("No such unit: '" + type + "'");
            }
        }
    }


    
    public static String getAbrev(final char c) {
        return GenerateUnit(c, null, new Location(0, 5)).toAbrev();
    }
    
    public BasicUnit(final BaseStats stats, final Player owner, final Location loc) {
        this.stats = stats;
        this.owner = owner;
        this.effects = new ArrayList<Effect>();
        this.effectQueue = new ArrayList<Effect>();
        this.loc = loc;
        if (loc.getX() < 5) {
            this.dir = Direction.NORTH;
        }
        else {
            this.dir = Direction.SOUTH;
        }
        this.blockingBonus = 0.0;
        this.hitPoints = stats.maxHP;
        if (!this.initialRecoveryExemption()) {
            this.recoveryTime = stats.recovery / 2;
        }
        else {
            this.recoveryTime = 0;
        }
    }
    
    public Direction direction() {
        return this.dir;
    }
    
    public BaseStats baseStats() {
        return this.stats;
    }
    
    public Player getPlayer() {
        return this.owner;
    }
    
    public Location location() {
        return this.loc;
    }
    
    public int blockingBonus() {
        return (int)Math.round(this.blockingBonus);
    }
    
    public int hitPoints() {
        return this.hitPoints;
    }
    
    public int recovery() {
        return this.recoveryTime;
    }
    
    public BufferedImage image() {
        return this.image;
    }
    
    public boolean mobile() {
        return this.stats.movement > 0;
    }
    
    protected void setDirection(final Direction d) {
        this.dir = d;
    }
    
    protected void setBaseStats(final BaseStats b) {
        this.stats = b;
    }
    
    public void setImage(final BufferedImage nImage) {
        this.image = nImage;
    }
    
    public int armor() {
        int armor = this.stats.armor;
        for (final Effect e : this.effects) {
            armor += e.armorChange();
        }
        if(armor > 99) {
            return 99;
        }
        else {
            return armor;
        }
    }
    
    public int power() {
        int power = this.stats.power;
        for (final Effect e : this.effects) {
            power += e.powerChange();
        }
        return power;
    }
    
    public boolean stepsAside() {
        if (!this.stats.stepsAside) {
            return false;
        }
        for (final Effect e : this.effects) {
            if (e.stopsSteppingAside()) {
                return false;
            }
        }
        return true;
    }
    
    public void addEffect(final Effect e) {
        this.effectQueue.add(e);
    }
    
    public void removeEffect(final Effect e) {
        this.effects.remove(e);
    }
    
    public ArrayList<Effect> getEffects() {
        return this.effects;
    }
    
    public boolean canMove() {
        if (!this.mobile()) {
            return false;
        }
        if (this.recoveryTime != 0) {
            return false;
        }
        for (final Effect e : this.effects) {
            if (e.stopsMoving()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean canMove(final Location loc) {
        if (!Board.isValid(loc)) {
            throw new IllegalArgumentException("Location out of bounds");
        }
        if (!this.canMove()) {
            return false;
        }
        final ArrayList<Location> options = this.getMoves();
        for (final Location i : options) {
            if (i.equals(loc)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canRotate() {
        return this.canMove();
    }
    
    public void Rotate(final Direction dir) {
        this.dir = dir;
        for (final Effect e : this.effects) {
            e.onTurn();
        }
    }
    
    private Direction getDirection(final Location moveHere) {
        if (this.location().equals(moveHere)) {
            throw new IllegalArgumentException();
        }
        final int dx = moveHere.getX() - this.location().getX();
        final int dy = moveHere.getY() - this.location().getY();
        final int dx_ = Math.abs(dx);
        final int dy_ = Math.abs(dy);
        final int distance = dx_ + dy_;
        if (this.stats.teleports || distance == 1) {
            return this.getLOSdirection(moveHere);
        }
        if (distance == 2 & (dx == 0 || dy == 0)) {
            Location inbetween = null;
            if (dx == 0) {
                inbetween = new Location(this.location().getX(), this.location().getY() + dy / 2);
            }
            else {
                inbetween = new Location(this.location().getX() + dx / 2, this.location().getY());
            }
            if (!this.owner.getBoard().isEmpty(inbetween) && (this.owner != this.owner.getBoard().unitAt(inbetween).getPlayer() || !this.owner.getBoard().unitAt(inbetween).stepsAside())) {
                final Location[] path1 = new Location[3];
                final Location[] path2 = new Location[3];
                if (dx == 0) {
                    for (int i = 0; i < 3; ++i) {
                        path1[i] = new Location(this.location().getX() + 1, this.location().getY() + dy / dy_ * i);
                        path2[i] = new Location(this.location().getX() - 1, this.location().getY() + dy / dy_ * i);
                    }
                }
                else {
                    for (int i = 0; i < 3; ++i) {
                        path1[i] = new Location(this.location().getX() + dx / dx_ * i, this.location().getY() + 1);
                        path2[i] = new Location(this.location().getX() + dx / dx_ * i, this.location().getY() - 1);
                    }
                }
                boolean path1Works = true;
                boolean path2Works = true;
                for (int j = 0; j < 3; ++j) {
                    if (!this.owner.getBoard().isEmpty(path1[j]) && (this.owner != this.owner.getBoard().unitAt(path1[j]).getPlayer() || !this.owner.getBoard().unitAt(path1[j]).stepsAside())) {
                        path1Works = false;
                    }
                    if (!this.owner.getBoard().isEmpty(path2[j]) && (this.owner != this.owner.getBoard().unitAt(path2[j]).getPlayer() || !this.owner.getBoard().unitAt(path2[j]).stepsAside())) {
                        path2Works = false;
                    }
                }
                if (!path1Works && !path2Works) {
                    throw new IllegalStateException();
                }
                if (!path1Works) {
                    final int ddx = this.location().getX() - path2[0].getX();
                    final int ddy = this.location().getY() - path2[0].getY();
                    if (ddx == 0) {
                        if (ddy > 0) {
                            return Direction.EAST;
                        }
                        return Direction.WEST;
                    }
                    else {
                        if (ddx > 0) {
                            return Direction.NORTH;
                        }
                        return Direction.SOUTH;
                    }
                }
                else if (!path2Works) {
                    final int ddx = this.location().getX() - path1[0].getX();
                    final int ddy = this.location().getY() - path1[0].getY();
                    if (ddx == 0) {
                        if (ddy > 0) {
                            return Direction.EAST;
                        }
                        return Direction.WEST;
                    }
                    else {
                        if (ddx > 0) {
                            return Direction.NORTH;
                        }
                        return Direction.SOUTH;
                    }
                }
                else {
                    boolean parallel = false;
                    switch (this.direction().intValue()) {
                        case 0:
                        case 2: {
                            parallel = (dx != 0);
                            break;
                        }
                        case 1:
                        case 3: {
                            parallel = (dx == 0);
                            break;
                        }
                        default: {
                            throw new IllegalStateException();
                        }
                    }
                    if (parallel) {
                        if (dx > 0) {
                            return Direction.WEST;
                        }
                        if (dx < 0) {
                            return Direction.EAST;
                        }
                        if (dy > 0) {
                            return Direction.NORTH;
                        }
                        return Direction.SOUTH;
                    }
                    else {
                        switch (this.direction().intValue()) {
                            case 0: {
                                return Direction.SOUTH;
                            }
                            case 1: {
                                return Direction.WEST;
                            }
                            case 2: {
                                return Direction.NORTH;
                            }
                            case 3: {
                                return Direction.EAST;
                            }
                        }
                    }
                }
            }
        }
        if (dx == 0) {
            if (dy > 0) {
                return Direction.EAST;
            }
            return Direction.WEST;
        }
        else if (dy == 0) {
            if (dx > 0) {
                return Direction.NORTH;
            }
            return Direction.SOUTH;
        }
        else {
            Location optimal = null;
            if (dy_ > dx_) {
                if (dy > 0) {
                    optimal = new Location(moveHere.getX(), moveHere.getY() - 1);
                }
                else {
                    optimal = new Location(moveHere.getX(), moveHere.getY() + 1);
                }
            }
            else if (dx_ > dy_) {
                if (dx > 0) {
                    optimal = new Location(moveHere.getX() - 1, moveHere.getY());
                }
                else {
                    optimal = new Location(moveHere.getX() + 1, moveHere.getY());
                }
            }
            else {
                switch (this.direction().intValue()) {
                    case 0:
                    case 2: {
                        if (dx > 0) {
                            optimal = new Location(moveHere.getX() - 1, moveHere.getY());
                            break;
                        }
                        optimal = new Location(moveHere.getX() + 1, moveHere.getY());
                        break;
                    }
                    case 1:
                    case 3: {
                        if (dy > 0) {
                            optimal = new Location(moveHere.getX(), moveHere.getY() - 1);
                            break;
                        }
                        optimal = new Location(moveHere.getX(), moveHere.getY() + 1);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Invalid Direction");
                    }
                }
            }
            if (this.owner.getBoard().isEmpty(optimal) || (this.owner == this.owner.getBoard().unitAt(optimal).getPlayer() && this.owner.getBoard().unitAt(optimal).stepsAside())) {
                boolean canGetThere = false;
                ArrayList<Location> lastLevel = new ArrayList<Location>();
                ArrayList<Location> newLevel = new ArrayList<Location>();
                lastLevel.add(this.location());
                for (int level = 0; level < distance - 1; ++level) {
                    for (final Location loc : lastLevel) {
                        final ArrayList<Location> temp = Board.neighborsOf(loc);
                        for (final Location nextLoc : temp) {
                            if (!this.owner.getBoard().isEmpty(nextLoc)) {
                                if (this.owner != this.owner.getBoard().unitAt(nextLoc).getPlayer()) {
                                    continue;
                                }
                                if (!this.owner.getBoard().unitAt(nextLoc).stepsAside()) {
                                    continue;
                                }
                            }
                            newLevel.add(nextLoc);
                            if (nextLoc.equals(optimal)) {
                                canGetThere = true;
                            }
                        }
                    }
                    lastLevel = newLevel;
                    newLevel = new ArrayList<Location>();
                }
                if (canGetThere) {
                    return this.getLOSdirection(moveHere);
                }
            }
            if (dy_ > dx_) {
                if (dx > 0) {
                    return Direction.NORTH;
                }
                return Direction.SOUTH;
            }
            else if (dx_ > dy_) {
                if (dy > 0) {
                    return Direction.EAST;
                }
                return Direction.WEST;
            }
            else {
                switch (this.direction().intValue()) {
                    case 0:
                    case 2: {
                        if (dy > 0) {
                            return Direction.EAST;
                        }
                        return Direction.WEST;
                    }
                    case 1:
                    case 3: {
                        if (dx > 0) {
                            return Direction.NORTH;
                        }
                        return Direction.SOUTH;
                    }
                    default: {
                        throw new IllegalStateException("Invalid Direction");
                    }
                }
            }
        }
    }
    
    public ArrayList<Location> getMoves() {
        final ArrayList<Location> options = new ArrayList<Location>();
        ArrayList<Location> lastLevel = new ArrayList<Location>();
        ArrayList<Location> newLevel = new ArrayList<Location>();
        if (this.location() == null) {
            return options;
        }
        lastLevel.add(this.location());
        options.add(this.location());
        for (int level = 0; level < this.stats.movement; ++level) {
            for (final Location loc : lastLevel) {
                final ArrayList<Location> temp = Board.neighborsOf(loc);
                for (final Location nextLoc : temp) {
                    if (options.contains(nextLoc)) {
                        continue;
                    }
                    if (!this.stats.teleports && !this.owner.getBoard().isEmpty(nextLoc)) {
                        if (this.owner != this.owner.getBoard().unitAt(nextLoc).getPlayer()) {
                            continue;
                        }
                        if (!this.owner.getBoard().unitAt(nextLoc).stepsAside()) {
                            continue;
                        }
                    }
                    options.add(nextLoc);
                    newLevel.add(nextLoc);
                }
            }
            lastLevel = newLevel;
            newLevel = new ArrayList<Location>();
        }
        final ListIterator<Location> itr = options.listIterator();
        while (itr.hasNext()) {
            if (!this.owner.getBoard().isEmpty(itr.next())) {
                itr.remove();
            }
        }
        return options;
    }
    
    public void Move(final Location loc) {
        if (!this.canMove(loc)) {
            throw new IllegalStateException("Cannot move to " + loc);
        }
        this.setDirection(this.getDirection(loc));
        this.loc = loc;
        for (final Effect e : this.effects) {
            e.onMove();
        }
    }
    
    public final boolean canAttack() {
        if (this.recoveryTime != 0) {
            return false;
        }
        for (final Effect e : this.effects) {
            if (e.stopsAttacking()) {
                return false;
            }
        }
        return true;
    }
    
    public final boolean canAttack(final Location loc) {
        return this.canAttack() && this.canattack(loc);
    }
    
    protected abstract boolean canattack(final Location p0);
    
    public final void Attack(final Location loc, final BlockingInfo blockingInfo) {

        final Unit unit = this.getPlayer().getBoard().unitAt(loc);

        if (!this.canAttack()) {
            throw new IllegalStateException("Cannot attack");
        }
        if (!this.canAttack(loc)) {
            throw new IllegalStateException("Cannot attack location " + loc);
        }
        this.attack(loc, blockingInfo);
        for (final Effect e : this.effects) {
            e.onAttack();
        }
    }
    
    protected abstract void attack(final Location p0, final BlockingInfo p1);
    
    protected void die() {
        this.hitPoints = 0;
        for (final Effect e : this.effects) {
            e.end();
        }
        this.getPlayer().getBoard().remove(this);
        this.onDeath();
    }
    
    public void onEndTurn(final Player turnOwner) {
        if (turnOwner == this.getPlayer() && this.recoveryTime != 0) {
            --this.recoveryTime;
        }
        this.blockingBonus *= 0.9;
        if (Math.abs(this.blockingBonus) < 4.0) {
            this.blockingBonus = 0.0;
        }
        for (int i = 0; i < this.effects.size(); ++i) {
            if (this.effects.get(i).isDone()) {
                this.removeEffect(this.effects.get(i));
                --i;
            }
        }
        for (final Effect e : this.effects) {
            if (e instanceof Poison) {
                int damage = 4;
                if (((Poison)e).isFirstTurn()) {
                    damage *= 2;
                    ((Poison)e).endFirstTurn();
                }
                if (this.hitPoints <= damage) {
                    this.hitPoints = 1;
                }
                else {
                    this.hitPoints -= damage;
                }
            }
        }
    }
    
    public void onEndRound() {
        int i = 0;
        if (i < this.effectQueue.size() && this.effectQueue.get(i) instanceof Paralyze) {
            for (int j = 0; j < this.effects.size(); ++j) {
                this.effects.get(j).onParalysis();
            }
        }
        i = 0;
        if (i < this.effectQueue.size() && this.effectQueue.get(i) instanceof Poison) {
            for (int j = 0; j < this.effects.size(); ++j) {
                this.effects.get(j).onDamage();
            }
        }
        for (i = 0; i < this.effects.size(); ++i) {
            if (this.effects.get(i).isDone() && !this.effects.get(i).endsOnlyAfterTurnEnds()) {
                this.removeEffect(this.effects.get(i));
                --i;
            }
        }
        while (!this.effectQueue.isEmpty()) {
            this.effects.add(this.effectQueue.remove(0));
        }
    }
    
    public void setRecovery(final int turns) {
        this.recoveryTime = turns;
    }
    
    public boolean healed(final int power) {
        for (final Effect e : this.effects) {
            if (e.stopsDamage()) {
                return false;
            }
        }
        this.hitPoints += power;
        if (this.hitPoints > this.stats.maxHP) {
            this.hitPoints = this.stats.maxHP;
        }
        return true;
    }
    
    public void attacked(final int power) {
        for (final Effect e : this.effects) {
            if (e.stopsDamage()) {
                return;
            }
        }
        for (final Effect e : this.effects) {
            e.onDamage();
        }
        final int damage = (int)Math.round(power * (100 - this.armor()) / 100.0);
        this.hitPoints -= damage;
        if (this.hitPoints <= 0) {
            this.hitPoints = 0;
            for (final Effect e2 : this.effects) {
                e2.end();
            }
            this.getPlayer().getBoard().remove(this);
            this.onDeath();
        }
    }
    
    public boolean attacked(final int power, final Location by, final BlockingInfo info) {
        for (final Effect e : this.effects) {
            if (e.stopsDamage()) {
                return false;
            }
        }
        final int dx = by.getX() - this.loc.getX();
        final int dy = by.getY() - this.loc.getY();
        Direction dir;
        if (Math.abs(dx) != Math.abs(dy)) {
            if (dx > 0 && dx > Math.abs(dy)) {
                dir = Direction.NORTH;
            }
            else if (dx < 0 && -dx > Math.abs(dy)) {
                dir = Direction.SOUTH;
            }
            else if (dy > 0) {
                dir = Direction.EAST;
            }
            else {
                dir = Direction.WEST;
            }
        }
        else if (dx > 0 && (this.dir.equals(Direction.NORTH) || this.dir.equals(Direction.SOUTH))) {
            dir = Direction.NORTH;
        }
        else if (dx < 0 && (this.dir.equals(Direction.NORTH) || this.dir.equals(Direction.SOUTH))) {
            dir = Direction.SOUTH;
        }
        else if (dy > 0 && (this.dir.equals(Direction.EAST) || this.dir.equals(Direction.WEST))) {
            dir = Direction.EAST;
        }
        else {
            if (dy >= 0 || (!this.dir.equals(Direction.EAST) && !this.dir.equals(Direction.WEST))) {
                throw new IllegalStateException();
            }
            dir = Direction.WEST;
        }
        boolean blockingChange = true;
        double blocking;
        if (this.dir.equals(dir) || !this.mobile()) {
            blocking = this.stats.blocking + this.blockingBonus;
        }
        else if (this.dir.equals(dir.rotateHalfTurn())) {
            blocking = 0.0;
            blockingChange = false;
        }
        else {
            blocking = (this.stats.blocking + this.blockingBonus) / 2.0;
        }
        for (final Effect e2 : this.effects) {
            if (e2.stopsBlocking()) {
                blocking = 0.0;
                blockingChange = false;
            }
        }
        final boolean hit = blocking < 100.0 && (blocking <= 0.0 || !info.block(blocking, this.location()));
        if (!hit) {  //blocked attack
            if (blockingChange) {
                    //changing direction of barrier ward to last blocked attack's location of attacker
                    if (this.dir.equals(dir) || !this.mobile()) {
                        this.blockingBonus -= 100 - this.stats.blocking;
                    }
                    else if (!this.dir.equals(dir.rotateHalfTurn())) {
                        this.blockingBonus -= 200 - this.stats.blocking;
                    }
                    if(this.baseStats() != null && !this.baseStats().getName().equals("Barrier Ward")) {
                        this.dir = dir;  //change direction only when it isnt a ward
                    }
            }
            return false;
        }
        //attack is not blocked
        if (blockingChange && !this.dir.equals(dir.rotateHalfTurn())) {
            this.blockingBonus += this.stats.blocking;
        }
        for (final Effect e3 : this.effects) {
            e3.onDamage();
        }
        final int damage = (int)Math.round(power * (100 - this.armor()) / 100.0);
        this.hitPoints -= damage;
        if (this.hitPoints <= 0) {
            this.hitPoints = 0;
            for (final Effect e4 : this.effects) {
                e4.end();
            }
            this.getPlayer().getBoard().remove(this);
            this.onDeath();
        }
        return true;
    }
    
    public abstract int unitCount();
    
    public abstract int maxNum();
    
    public abstract boolean initialRecoveryExemption();
    
    public abstract ArrayList<Location> affectedByAttack(final Location p0);
    
    public abstract boolean canFocus();
    
    public String toString() {
        String output = "";
        output = String.valueOf(output) + this.stats.name + " - " + this.owner.ID() + "\n";
        output = String.valueOf(output) + this.loc + " " + this.dir + "\n";
        output = String.valueOf(output) + this.hitPoints + "/" + this.stats.maxHP;
        return output;
    }
    
    public String toDisplay() {
        String output = "";
        output = String.valueOf(output) + this.stats.name + " - " + this.owner.ID();
        output = String.valueOf(output) + " - HP: " + this.hitPoints + "/" + this.stats.maxHP + "\n";
        output = String.valueOf(output) + "Power: " + this.stats.power;
        if (this.power() - this.stats.power > 0) {
            output = String.valueOf(output) + " + " + (this.power() - this.stats.power);
        }
        else if (this.power() - this.stats.power < 0) {
            output = String.valueOf(output) + " - " + (this.stats.power - this.power());
        }
        output = String.valueOf(output) + ", Armor: " + this.stats.armor;
        if (this.armor() != this.stats.armor) {
            output = String.valueOf(output) + " + " + (this.armor() - this.stats.armor);
        }
        output = String.valueOf(output) + ", Blocking: " + this.stats.blocking;
        if (this.blockingBonus() > 0) {
            output = String.valueOf(output) + " + " + this.blockingBonus();
        }
        else if (this.blockingBonus() < 0) {
            output = String.valueOf(output) + " - " + -this.blockingBonus();
        }
        if (this.recoveryTime != 0 || this.effects.size() != 0) {
            boolean first = true;
            output = String.valueOf(output) + "\n";
            if (this.recoveryTime != 0) {
                output = String.valueOf(output) + "Wait " + this.recoveryTime + " turn";
                if (this.recoveryTime > 1) {
                    output = String.valueOf(output) + "s";
                }
                first = false;
            }
            for (int i = 0; i < this.effects.size(); ++i) {
                if (this.effects.get(i) instanceof Focus) {
                    if (!first) {
                        output = String.valueOf(output) + ", ";
                    }
                    output = String.valueOf(output) + this.effects.get(i).toString();
                    first = false;
                }
            }
            for (int i = 0; i < this.effects.size(); ++i) {
                if (this.effects.get(i) instanceof Barrier) {
                    if (!first) {
                        output = String.valueOf(output) + ", ";
                    }
                    output = String.valueOf(output) + this.effects.get(i).toString();
                    first = false;
                    break;
                }
            }
            for (int i = 0; i < this.effects.size(); ++i) {
                if (this.effects.get(i) instanceof Paralyze) {
                    if (!first) {
                        output = String.valueOf(output) + ", ";
                    }
                    output = String.valueOf(output) + this.effects.get(i).toString();
                    first = false;
                    break;
                }
            }
            for (int i = 0; i < this.effects.size(); ++i) {
                if (this.effects.get(i) instanceof Poison) {
                    if (!first) {
                        output = String.valueOf(output) + ", ";
                    }
                    output = String.valueOf(output) + this.effects.get(i).toString();
                    first = false;
                    break;
                }
            }
        }
        return output;
    }
    
    public boolean isOnlyOnField() {
        for (final Unit u : this.getPlayer().getBoard().getUnits()) {
            if (u.getClass() == this.getClass() && u.getPlayer() == this.getPlayer() && u != this) {
                return false;
            }
        }
        return true;
    }
    
    public void onDeath() {
        if (this.mobile()) {
            for (final Unit next : this.getPlayer().getBoard().getUnits()) {
                if (next instanceof BasicUnit && ((BasicUnit)next).getPlayer() == this.getPlayer() && next != this) {
                    ((BasicUnit)next).onMobileAllyDeath();
                }
            }
        }
    }
    
    public abstract String toAbrev();
    
    public void onMobileAllyDeath() {
    }
    
    public void init() {
    }
    
    protected Direction getLOSdirection(final Location loc) {
        Direction los = null;
        if (this.location().equals(loc)) {
            return this.direction();
        }
        final int dx = loc.getX() - this.location().getX();
        final int dy = loc.getY() - this.location().getY();
        if (Math.abs(dx) != Math.abs(dy)) {
            if (dx > 0 && dx > Math.abs(dy)) {
                los = Direction.NORTH;
            }
            else if (dx < 0 && -dx > Math.abs(dy)) {
                los = Direction.SOUTH;
            }
            else if (dy > 0) {
                los = Direction.EAST;
            }
            else {
                los = Direction.WEST;
            }
        }
        else if (dx > 0 && (this.dir.equals(Direction.NORTH) || this.dir.equals(Direction.SOUTH))) {
            los = Direction.NORTH;
        }
        else if (dx < 0 && (this.dir.equals(Direction.NORTH) || this.dir.equals(Direction.SOUTH))) {
            los = Direction.SOUTH;
        }
        else if (dy > 0 && (this.dir.equals(Direction.EAST) || this.dir.equals(Direction.WEST))) {
            los = Direction.EAST;
        }
        else {
            if (dy >= 0 || (!this.dir.equals(Direction.EAST) && !this.dir.equals(Direction.WEST))) {
                throw new IllegalStateException();
            }
            los = Direction.WEST;
        }
        return los;
    }
}
