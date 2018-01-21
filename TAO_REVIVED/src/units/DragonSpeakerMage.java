package units;

import game.BlockingInfo;
import java.util.Iterator;
import effects.Effect;
import effects.DragonSpeak;
import game.Board;
import java.util.ArrayList;
import util.Location;
import game.Player;

public class DragonSpeakerMage extends BasicUnit
{
    public DragonSpeakerMage(final Player owner, final Location loc) {
        super(new BaseStats("Dragonspeaker Mage", 30, 15, 0, 33, 3, 3, true, false), owner, loc);
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
    
    public void init() {
        Iterator<Unit> itr = this.getPlayer().getBoard().iterator();
        boolean hasDragon = false;
        while (itr.hasNext()) {
            final Unit u = itr.next();
            if (u.getPlayer() == this.getPlayer() && u instanceof DragonTyrant) {
                hasDragon = true;
                break;
            }
        }
        if (hasDragon) {
            itr = this.getPlayer().getBoard().iterator();
            while (itr.hasNext()) {
                final Unit u = itr.next();
                if (u.getPlayer() == this.getPlayer() && (u instanceof DragonTyrant || u instanceof DragonSpeakerMage || u instanceof Pyromancer)) {
                    ((BasicUnit)u).addEffect(new DragonSpeak((BasicUnit)u));
                }
            }
        }
    }
    
    protected void attack(final Location loc, final BlockingInfo info) {
        this.setDirection(this.getLOSdirection(loc));
        final ArrayList<Location> list = this.affectedByAttack(loc);
        for (final Location x : list) {
            final Unit unit = this.getPlayer().getBoard().unitAt(x);
            if (unit != null ) {
                if(!unit.baseStats().getName().equals("Shrub")) {
                    final BasicUnit target = (BasicUnit)unit;
                    target.attacked(this.power());
                }
                else {
                    unit.attacked(100);
                }
            }
        }
    }
    
    protected boolean canattack(final Location loc) {
        return Board.distance(loc, this.location()) <= 3;
    }
    
    public boolean initialRecoveryExemption() {
        return false;
    }
    
    public int maxNum() {
        return 0;
    }
    
    public int unitCount() {
        return 1;
    }
    
    public char toChar() {
        return 'D';
    }
    
    public String toAbrev() {
        return "D";
    }
    
    public boolean canFocus() {
        return false;
    }
}
