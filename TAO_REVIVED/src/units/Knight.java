// 
// Decompiled by Procyon v0.5.30
// 

package units;

import java.util.ArrayList;
import game.Board;
import game.BlockingInfo;
import util.Location;
import game.Player;

public class Knight extends BasicUnit
{
    public Knight(final Player owner, final Location loc) {
        super(new BaseStats("Knight", 50, 22, 25, 80, 1, 3, true, false), owner, loc);
    }
    
    public char toChar() {
        return 'K';
    }
    
    public int unitCount() {
        return 1;
    }
    
    public int maxNum() {
        return 0;
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
        return Board.distance(this.location(), loc) == 1;
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (this.canAttack(loc)) {
            list.add(loc);
        }
        return list;
    }
    
    public String toAbrev() {
        return "K";
    }
    
    public boolean canFocus() {
        return false;
    }
}
