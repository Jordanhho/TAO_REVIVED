package units;

import game.Board;
import effects.Paralyze;
import effects.Effect;
import effects.Focus;
import game.BlockingInfo;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class FrostGolem extends BasicUnit
{
    public FrostGolem(final Player owner, final Location loc) {
        super(new BaseStats("Frost Golem", 60, 0, 0, 0, 2, 2, true, false), owner, loc);
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
        final Focus focus = new Focus(this);
        this.addEffect(focus);
        final Unit unit = this.getPlayer().getBoard().unitAt(loc);
        if (unit != null && unit instanceof BasicUnit) {
            final BasicUnit target = (BasicUnit)unit;
            final Paralyze paralyze = new Paralyze(target);
            focus.add(paralyze);
            target.addEffect(paralyze);
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(loc, this.location()) < 5 && !this.location().equals(loc);
    }
    
    public boolean initialRecoveryExemption() {
        return true;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public String toAbrev() {
        return "FG";
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'f';
    }
    
    public boolean canFocus() {
        return true;
    }
}
