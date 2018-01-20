package units;

import game.Board;
import game.BlockingInfo;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class GolemAmbusher extends BasicUnit
{
    public GolemAmbusher(final Player owner, final Location loc) {
        super(new BaseStats("Golem Ambusher", 60, 20, 0, 0, 3, 2, true, false), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (this.canAttack(loc)) {
            list.add(loc);
        }
        return list;
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        this.setDirection(this.getLOSdirection(loc));
        final Unit target = this.getPlayer().getBoard().unitAt(loc);
        if (target != null) {
            target.attacked(this.power(), this.location(), info);
            info.doneAttacking();
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(loc, this.location()) == 4 || Board.distance(loc, this.location()) == 5;
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public int maxNum() {
        return 1;
    }
    
    public String toAbrev() {
        return "G";
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'G';
    }
    
    public boolean canFocus() {
        return false;
    }
}
