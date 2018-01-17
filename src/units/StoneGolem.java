// 
// Decompiled by Procyon v0.5.30
// 

package units;

import java.util.Iterator;
import effects.Stoned;
import effects.Effect;
import effects.Focus;
import game.BlockingInfo;
import game.Board;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class StoneGolem extends BasicUnit
{
    public StoneGolem(final Player owner, final Location loc) {
        super(new BaseStats("Stone Golem", 60, 30, 30, 0, 4, 2, true, false), owner, loc);
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
        final Focus focus = new Focus(this);
        this.addEffect(focus);
        final ArrayList<Location> list = this.affectedByAttack(loc);
        for (final Location x : list) {
            final Unit unit = this.getPlayer().getBoard().unitAt(x);
            if (unit != null && unit instanceof BasicUnit) {
                final BasicUnit target = (BasicUnit)unit;
                final Stoned stoned = new Stoned(target);
                focus.add(stoned);
                target.addEffect(stoned);
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(this.location(), loc) < 2;
    }
    
    public boolean initialRecoveryExemption() {
        return true;
    }
    
    public int maxNum() {
        return 1;
    }
    
    public String toAbrev() {
        return "SG";
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 's';
    }
    
    public boolean canFocus() {
        return true;
    }
}
