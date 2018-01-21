package units;

import java.util.Iterator;
import game.BlockingInfo;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class DragonTyrant extends BasicUnit
{
    public DragonTyrant(final Player owner, final Location loc) {
        super(new BaseStats("Dragon Tyrant", 68, 28, 16, 40, 3, 4, false, true), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> hitqueue = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return hitqueue;
        }
        final int dx = loc.getX() - this.location().getX();
        final int dy = loc.getY() - this.location().getY();
        final int absx = Math.abs(dx);
        final int absy = Math.abs(dy);
        int dirx = 0;
        int diry = 0;
        if (dx != 0) {
            dirx = dx / absx;
        }
        if (dy != 0) {
            diry = dy / absy;
        }
        if (absx == 0) {
            for (int y = this.location().getY() + diry; y != loc.getY(); y += diry) {
                hitqueue.add(new Location(this.location().getX(), y));
            }
            hitqueue.add(loc);
        }
        else if (absy == 0) {
            for (int x = this.location().getX() + dirx; x != loc.getX(); x += dirx) {
                hitqueue.add(new Location(x, this.location().getY()));
            }
            hitqueue.add(loc);
        }
        return hitqueue;
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
        return (dx == 0 || dy == 0) && (dx != 0 || dy != 0) && absx < 4 && absy < 4;
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public String toAbrev() {
        return "DT";
    }
    
    public int unitCount() {
        return 2;
    }
    
    public char toChar() {
        return 'T';
    }
    
    public boolean canFocus() {
        return false;
    }
}
