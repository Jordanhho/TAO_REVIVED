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

public class DarkMagicWitch extends BasicUnit
{
    public DarkMagicWitch(final Player owner, final Location loc) {
        super(new BaseStats("Dark Magic Witch", 28, 24, 0, 20, 3, 3, true, false), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return list;
        }
        final int dx = loc.getX() - this.location().getX();
        final int dy = loc.getY() - this.location().getY();
        if (dx > 0) {
            for (int i = 1; i <= 4; ++i) {
                final Location x = new Location(this.location().getX() + i, this.location().getY());
                if (Board.isValid(x)) {
                    list.add(x);
                }
            }
        }
        else if (dx < 0) {
            for (int i = 1; i <= 4; ++i) {
                final Location x = new Location(this.location().getX() - i, this.location().getY());
                if (Board.isValid(x)) {
                    list.add(x);
                }
            }
        }
        else if (dy > 0) {
            for (int i = 1; i <= 4; ++i) {
                final Location x = new Location(this.location().getX(), this.location().getY() + i);
                if (Board.isValid(x)) {
                    list.add(x);
                }
            }
        }
        else if (dy < 0) {
            for (int i = 1; i <= 4; ++i) {
                final Location x = new Location(this.location().getX(), this.location().getY() - i);
                if (Board.isValid(x)) {
                    list.add(x);
                }
            }
        }
        return list;
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        this.setDirection(this.getLOSdirection(loc));
        final ArrayList<Location> list = this.affectedByAttack(loc);
        for (final Location x : list) {
            final Unit target = this.getPlayer().getBoard().unitAt(x);
            if (target != null) {
                target.attacked(this.power());
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        final int dx = loc.getX() - this.location().getX();
        final int dy = loc.getY() - this.location().getY();
        final int absx = Math.abs(dx);
        final int absy = Math.abs(dy);
        return (dx == 0 || dy == 0) && (dx != 0 || dy != 0) && absx < 5 && absy < 5;
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
        return 'W';
    }
    
    public String toAbrev() {
        return "W";
    }
    
    public boolean canFocus() {
        return false;
    }
}
