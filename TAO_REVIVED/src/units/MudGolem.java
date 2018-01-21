// 
// Decompiled by Procyon v0.5.30
// 

package units;

import java.util.Iterator;
import util.Direction;
import game.BlockingInfo;
import game.Board;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class MudGolem extends BasicUnit
{
    public MudGolem(final Player owner, final Location loc) {
        super(new BaseStats("Mud Golem", 60, 20, 0, 0, 2, 5, true, true), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return list;
        }
        if (this.location().equals(loc)) {
            final int r = 3;
            final int x1 = this.location().getX();
            final int y1 = this.location().getY();
            for (int x2 = x1 - r; x2 <= x1 + r; ++x2) {
                for (int diff = Math.abs(x2 - x1), y2 = y1 - r + diff; y2 <= y1 + r - diff; ++y2) {
                    final Location temp = new Location(x2, y2);
                    if (Board.isValid(temp) && !temp.equals(this.location())) {
                        list.add(temp);
                    }
                }
            }
        }
        else {
            list.add(loc);
        }
        return list;
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        this.setDirection(this.getLOSdirection(loc));
        if (this.location().equals(loc)) {
            final ArrayList<Location> list = this.affectedByAttack(loc);
            for (final Location x : list) {
                final Unit unit = this.getPlayer().getBoard().unitAt(x);
                if (unit != null && !(unit instanceof PoisonWisp)) {
                    unit.attacked(20 - 5 * Board.distance(loc, x));
                }
            }
        }
        else {
            Direction dir;
            if (loc.getX() == this.location().getX()) {
                if (loc.getY() > this.location().getY()) {
                    dir = Direction.EAST;
                }
                else {
                    dir = Direction.WEST;
                }
            }
            else if (loc.getX() > this.location().getX()) {
                dir = Direction.NORTH;
            }
            else {
                dir = Direction.SOUTH;
            }
            this.setDirection(dir);
            final Unit target = this.getPlayer().getBoard().unitAt(loc);
            if (target != null) {
                target.attacked(this.power(), this.location(), info);
                info.doneAttacking();
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(this.location(), loc) == 1 || (this.location().equals(loc) && this.isOnlyOnField());
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public String toAbrev() {
        return "M";
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'M';
    }
    
    public boolean canFocus() {
        return false;
    }
}
