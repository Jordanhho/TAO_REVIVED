package units;

import java.util.Iterator;
import game.BlockingInfo;
import game.Board;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class Assassin extends BasicUnit
{
    public Assassin(final Player owner, final Location loc) {
        super(new BaseStats("Assassin", 35, 18, 12, 70, 1, 4, true, false), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return list;
        }
        final int x = this.location().getX();
        final int y = this.location().getY();
        final Location x2 = new Location(x + 1, y);
        final Location x3 = new Location(x - 1, y);
        final Location x4 = new Location(x, y + 1);
        final Location x5 = new Location(x, y - 1);
        if (Board.isValid(x2)) {
            list.add(x2);
        }
        if (Board.isValid(x3)) {
            list.add(x3);
        }
        if (Board.isValid(x4)) {
            list.add(x4);
        }
        if (Board.isValid(x5)) {
            list.add(x5);
        }
        if (loc.equals(this.location())) {
            list.add(loc);
        }
        return list;
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        final ArrayList<Location> list = this.affectedByAttack(loc);
        this.setDirection(this.getLOSdirection(loc));
        if (loc.equals(this.location())) {
            for (final Location x : list) {
                final Unit unit = this.getPlayer().getBoard().unitAt(x);
                if (unit != null) {
                    if(!unit.baseStats().getName().equals("Shrub")) {
                        final BasicUnit target = (BasicUnit)unit;
                        target.attacked(9001);
                    }
                    else { //when attacking a shrub
                        unit.attacked(1);
                    }
                    this.die();
                }
            }
        }
        else {
            for (final Location x : list) {
                final Unit unit = this.getPlayer().getBoard().unitAt(x);
                if (unit != null) {
                    unit.attacked(this.power(), this.location(), info);
                }
            }
            info.doneAttacking();
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(this.location(), loc) == 1 || (loc.equals(this.location()) && this.isOnlyOnField() && this.hitPoints() < 5);
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'A';
    }
    
    public String toAbrev() {
        return "A";
    }
    
    public boolean canFocus() {
        return false;
    }
}
