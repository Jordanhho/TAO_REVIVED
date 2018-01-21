// 
// Decompiled by Procyon v0.5.30
// 

package units;

import java.util.Iterator;
import game.BlockingInfo;
import game.Board;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class Pyromancer extends BasicUnit
{
    public Pyromancer(final Player owner, final Location loc) {
        super(new BaseStats("Pyromancer", 30, 15, 0, 33, 3, 3, true, false), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return list;
        }
        final int x = loc.getX();
        final int y = loc.getY();
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
        list.add(loc);
        return list;
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        this.setDirection(this.getLOSdirection(loc));
        final ArrayList<Location> list = this.affectedByAttack(loc);
        for (final Location x : list) {
            final Unit unit = this.getPlayer().getBoard().unitAt(x);
            if (unit != null) {
                unit.attacked(this.power());
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(loc, this.location()) <= 3;
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
        return 'P';
    }
    
    public String toAbrev() {
        return "P";
    }
    
    public boolean canFocus() {
        return false;
    }
}
