// 
// Decompiled by Procyon v0.5.30
// 

package units;

import java.util.Iterator;
import effects.Paralyze;
import effects.Effect;
import effects.Focus;
import game.BlockingInfo;
import game.Board;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class Enchantress extends BasicUnit
{
    public Enchantress(final Player owner, final Location loc) {
        super(new BaseStats("Enchantress", 35, 0, 0, 0, 3, 3, true, false), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return list;
        }
        final int r = 2;
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
                final Paralyze paralyze = new Paralyze(target);
                focus.add(paralyze);
                target.addEffect(paralyze);
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(loc, this.location()) <= 2 && !loc.equals(this.location());
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
    
    public char toChar() {
        return 'E';
    }
    
    public String toAbrev() {
        return "E";
    }
    
    public boolean canFocus() {
        return true;
    }
}
