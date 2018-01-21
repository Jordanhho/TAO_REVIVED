// 
// Decompiled by Procyon v0.5.30
// 

package units;

import java.util.ArrayList;
import game.Board;
import game.BlockingInfo;
import util.Location;
import game.Player;

public class LightningWard extends BasicUnit
{
    public LightningWard(final Player owner, final Location loc) {
        super(new BaseStats("Lightning Ward", 56, 30, 18, 100, 4, 0, false, false), owner, loc);
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        final Unit target = this.getPlayer().getBoard().unitAt(loc);
        if (target != null) {
            target.attacked(this.power());
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(this.location(), loc) <= 3;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'L';
    }
    
    public boolean initialRecoveryExemption() {
        return true;
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (this.canAttack(loc)) {
            list.add(loc);
        }
        return list;
    }
    
    public String toAbrev() {
        return "LW";
    }
    
    public boolean canFocus() {
        return false;
    }
}
