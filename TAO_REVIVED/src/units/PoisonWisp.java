package units;

import java.util.Iterator;
import effects.Poison;
import effects.Effect;
import effects.Focus;
import game.BlockingInfo;
import game.Board;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class PoisonWisp extends BasicUnit
{
    public PoisonWisp(final Player owner, final Location loc) {
        super(new BaseStats("Poison Wisp", 30, 0, 0, 0, 2, 6, true, true), owner, loc);
    }
    
    public ArrayList<Location> affectedByAttack(final Location loc) {
        final ArrayList<Location> list = new ArrayList<Location>();
        if (!this.canAttack(loc)) {
            return list;
        }
        for (int i = 1; i <= 2; ++i) {
            final Location x = new Location(this.location().getX() + i, this.location().getY());
            if (Board.isValid(x)) {
                list.add(x);
            }
        }
        for (int i = 1; i <= 2; ++i) {
            final Location x = new Location(this.location().getX() - i, this.location().getY());
            if (Board.isValid(x)) {
                list.add(x);
            }
        }
        for (int i = 1; i <= 2; ++i) {
            final Location x = new Location(this.location().getX(), this.location().getY() + i);
            if (Board.isValid(x)) {
                list.add(x);
            }
        }
        for (int i = 1; i <= 2; ++i) {
            final Location x = new Location(this.location().getX(), this.location().getY() - i);
            if (Board.isValid(x)) {
                list.add(x);
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
                final Poison poison = new Poison(target);
                focus.add(poison);
                target.addEffect(poison);
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        final int dx = loc.getX() - this.location().getX();
        final int dy = loc.getY() - this.location().getY();
        final int absx = Math.abs(dx);
        final int absy = Math.abs(dy);
        return (dx == 0 || dy == 0) && (dx != 0 || dy != 0) && absx < 3 && absy < 3;
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public String toAbrev() {
        return "p";
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'p';
    }
    
    public boolean canFocus() {
        return true;
    }
}
