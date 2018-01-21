package units;

import java.util.ArrayList;
import game.Board;
import java.util.LinkedList;
import game.BlockingInfo;
import util.Location;
import game.Player;

public class Scout extends BasicUnit
{
    public Scout(final Player owner, final Location loc) {
        super(new BaseStats("Scout", 40, 18, 8, 60, 2, 4, true, false), owner, loc);
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        this.setDirection(this.getLOSdirection(loc));
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
        final LinkedList<Location> hitqueue = new LinkedList<Location>();
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
        else if (Board.distance(loc, this.location()) <= 3) {
            hitqueue.add(loc);
        }
        else if (absx == absy) {
            for (int x = this.location().getX() + dirx, y2 = this.location().getY() + diry; x != loc.getX(); x += dirx, y2 += diry) {
                hitqueue.add(new Location(x, y2));
            }
            hitqueue.add(loc);
        }
        else if (absx == 1) {
            hitqueue.add(new Location(this.location().getX(), this.location().getY() + diry));
            hitqueue.add(new Location(loc.getX(), loc.getY() - diry));
            hitqueue.add(loc);
        }
        else if (absy == 1) {
            hitqueue.add(new Location(this.location().getX() + dirx, this.location().getY()));
            hitqueue.add(new Location(loc.getX() - dirx, loc.getY()));
            hitqueue.add(loc);
        }
        else if (absx + absy == 6) {
            final int avex = (loc.getX() + this.location().getX()) / 2;
            final int avey = (loc.getY() + this.location().getY()) / 2;
            hitqueue.add(new Location(avex, avey));
            hitqueue.add(loc);
        }
        else {
            hitqueue.add(new Location(this.location().getX() + dirx, this.location().getY() + diry));
            hitqueue.add(new Location(loc.getX() - dirx, loc.getY() - diry));
            hitqueue.add(loc);
        }
        boolean done = false;
        while (!hitqueue.isEmpty() && !done) {
            final Location next = hitqueue.remove();
            final Unit target = this.getPlayer().getBoard().unitAt(next);
            if (target != null) {
                done = true;
                target.attacked(this.power(), this.location(), info);
                info.doneAttacking();
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(loc, this.location()) <= 6 && !loc.equals(this.location());
    }
    
    public int maxNum() {
        return 0;
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'S';
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
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
        final ArrayList<Location> hitqueue = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return hitqueue;
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
        else if (Board.distance(loc, this.location()) <= 3) {
            hitqueue.add(loc);
        }
        else if (absx == absy) {
            for (int x = this.location().getX() + dirx, y2 = this.location().getY() + diry; x != loc.getX(); x += dirx, y2 += diry) {
                hitqueue.add(new Location(x, y2));
            }
            hitqueue.add(loc);
        }
        else if (absx == 1) {
            hitqueue.add(new Location(this.location().getX(), this.location().getY() + diry));
            hitqueue.add(new Location(loc.getX(), loc.getY() - diry));
            hitqueue.add(loc);
        }
        else if (absy == 1) {
            hitqueue.add(new Location(this.location().getX() + dirx, this.location().getY()));
            hitqueue.add(new Location(loc.getX() - dirx, loc.getY()));
            hitqueue.add(loc);
        }
        else if (absx + absy == 6) {
            final int avex = (loc.getX() + this.location().getX()) / 2;
            final int avey = (loc.getY() + this.location().getY()) / 2;
            hitqueue.add(new Location(avex, avey));
            hitqueue.add(loc);
        }
        else {
            hitqueue.add(new Location(this.location().getX() + dirx, this.location().getY() + diry));
            hitqueue.add(new Location(loc.getX() - dirx, loc.getY() - diry));
            hitqueue.add(loc);
        }
        return hitqueue;
    }
    
    public String toAbrev() {
        return "S";
    }
    
    public boolean canFocus() {
        return false;
    }
}
