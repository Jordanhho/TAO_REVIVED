// 
// Decompiled by Procyon v0.5.30
// 

package units;

import game.Board;
import effects.Barrier;
import effects.Effect;
import effects.Focus;
import game.BlockingInfo;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class BarrierWard extends BasicUnit
{
    public BarrierWard(final Player owner, final Location loc) {
        super(new BaseStats("Barrier Ward", 32, 0, 0, 100, 2, 0, false, false), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (this.canAttack(loc)) {
            list.add(loc);
        }
        return list;
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        final Unit unit = this.getPlayer().getBoard().unitAt(loc);
        if (unit instanceof BasicUnit) {
            final BasicUnit target = (BasicUnit)unit;
            final Focus focus = new Focus(this);
            this.addEffect(focus);
            final Barrier barrier = new Barrier(target);
            focus.add(barrier);
            target.addEffect(barrier);
        }
    }
    
    protected boolean canattack(final Location loc) {
        if(this.getPlayer().getBoard().unitAt(loc) != null && this.getPlayer().getBoard().unitAt(loc).equals("Shrub")) {
            return false;
        }
        else {
            return Board.distance(loc, this.location()) <= 6;
        }
    }
    
    public boolean initialRecoveryExemption() {
        return true;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() { return 'b'; }
    
    public String toAbrev() {
        return "BW";
    }
    
    public boolean canFocus() {
        return true;
    }
}
