// 
// Decompiled by Procyon v0.5.30
// 

package units;

import game.Board;
import game.BlockingInfo;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class Berserker extends BasicUnit
{
    public Berserker(final Player owner, final Location loc) {
        super(new BaseStats("Berserker", 42, 22, 0, 25, 1, 3, false, false), owner, loc);
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
            final boolean hit = target.attacked(this.power(), this.location(), info);
            if (hit && target instanceof BasicUnit) {
                final BasicUnit u = (BasicUnit)target;
                u.setRecovery(u.recovery() + 1);
            }
            info.doneAttacking();
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(this.location(), loc) == 1;
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public String toAbrev() {
        return "Z";
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'Z';
    }
    
    public boolean canFocus() {
        return false;
    }
}
