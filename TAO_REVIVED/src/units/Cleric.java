// 
// Decompiled by Procyon v0.5.30
// 

package units;

import java.util.ArrayList;
import java.util.Iterator;
import game.BlockingInfo;
import util.Location;
import game.Player;

public class Cleric extends BasicUnit
{
    public Cleric(final Player owner, final Location loc) {
        super(new BaseStats("Cleric", 24, 12, 0, 0, 5, 3, true, false), owner, loc);
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        this.setDirection(this.getLOSdirection(loc));
        for (final Unit next : this.getPlayer().getBoard().getUnits()) {
            if (next instanceof BasicUnit && ((BasicUnit)next).getPlayer() == this.getPlayer()) {
                ((BasicUnit)next).healed(this.power());
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        for (final Unit next : this.getPlayer().getBoard().getUnits()) {
            if (next.location().equals(loc) && next instanceof BasicUnit && ((BasicUnit)next).getPlayer() == this.getPlayer()) {
                return true;
            }
        }
        return false;
    }
    
    public int maxNum() {
        return 0; //used to be -1
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'C';
    }
    
    public boolean initialRecoveryExemption() {
        return true;
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return list;
        }
        for (final Unit next : this.getPlayer().getBoard().getUnits()) {
            if (next instanceof BasicUnit && ((BasicUnit)next).getPlayer() == this.getPlayer()) {
                list.add(next.location());
            }
        }
        return list;
    }
    
    public String toAbrev() {
        return "C";
    }
    
    public boolean canFocus() {
        return false;
    }
}
