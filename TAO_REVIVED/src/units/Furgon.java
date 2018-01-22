package units;

import effects.Barrier;
import effects.Effect;
import effects.Paralyze;
import effects.Poison;
import game.BlockingInfo;
import game.Board;
import game.Player;
import java.util.ArrayList;
import java.util.Iterator;
import util.Location;

import javax.swing.*;


public class Furgon
        extends BasicUnit
{
    /*
    default stats:
    private static final BaseStats DEFAULT = new BaseStats("Furgon", 48, 0, 0, 50, 1, 3, true, false);
    private static final BaseStats EXHAUSTED = new BaseStats("Exhausted Furgon", 48, 0, 0, 0, 12, 3, true, false);
    private static final BaseStats ENRAGED = new BaseStats("Enraged Furgon", 48, 0, 0, 50, 1, 3, true, false);
     */
    private static final BaseStats DEFAULT = new BaseStats("Furgon", 72, 0, 0, 0, 1, 3, true, false);
    private static final BaseStats EXHAUSTED = new BaseStats("Exhausted Furgon", 72, 0, 0, 0, 12, 3, true, false);
    private static final BaseStats ENRAGED = new BaseStats("Enraged Furgon", 72, 0, 0, 0, 2, 3, true, false); //now lasts 2 turns

    public Furgon(Player owner, Location loc)
    {
        //super(new BaseStats("Furgon", 48, 0, 0, 50, 1, 3, true, false), owner, loc); default
        super(new BaseStats("Furgon", 72, 0, 0, 0, 1, 3, true, false), owner, loc);
    }


    public ArrayList<Location> affectedByAttack(Location loc)
    {
        ArrayList<Location> list = new ArrayList();
        if (!canAttack(loc)) {
            return list;
        }
        Location f1 = new Location(location().getX() + 1, location().getY());
        Location f2 = new Location(location().getX() - 1, location().getY());
        Location f3 = new Location(location().getX(), location().getY() + 1);
        Location f4 = new Location(location().getX(), location().getY() - 1);

        boolean enraged = baseStats() == ENRAGED;

        boolean surrounded = false;

        Board b = getPlayer().getBoard();
        if (enraged)
        {

            surrounded = isSurrounded(b, f1, f2, f3, f4);
//            surrounded = (Board.isValid(f1)) &&
//                    (Board.isValid(f2)) &&
//                    (Board.isValid(f3)) &&
//                    (Board.isValid(f4)) &&
//                    ((b.unitAt(f1) instanceof Shrub)) &&
//                    ((b.unitAt(f2) instanceof Shrub)) &&
//                    ((b.unitAt(f3) instanceof Shrub)) &&
//                    ((b.unitAt(f4) instanceof Shrub));
        }
        if (surrounded)
        {
            Iterator<Unit> itr = getPlayer().getBoard().iterator();
            while (itr.hasNext())
            {
                Unit next = (Unit)itr.next();
                if (((next instanceof BasicUnit)) &&
                        (((BasicUnit)next).getPlayer() != getPlayer())) //only put shrubs around enemy units
                {
                    Location center = next.location();
                    Location y1 = new Location(center.getX() + 1, center.getY());
                    Location y2 = new Location(center.getX() - 1, center.getY());
                    Location y3 = new Location(center.getX(), center.getY() + 1);
                    Location y4 = new Location(center.getX(), center.getY() - 1);

                    if ((Board.isValid(y1)) && (getPlayer().getBoard().isEmpty(y1)))
                        list.add(y1);
                    if ((Board.isValid(y2)) && (getPlayer().getBoard().isEmpty(y2)))
                        list.add(y2);
                    if ((Board.isValid(y3)) && (getPlayer().getBoard().isEmpty(y3)))
                        list.add(y3);
                    if ((Board.isValid(y4)) && (getPlayer().getBoard().isEmpty(y4)))
                        list.add(y4);
                    if (getPlayer().getBoard().isEmpty(loc)) {
                        list.add(loc);
                    }
                }
            }
        }
        else
        {
            int x = loc.getX();
            int y = loc.getY();

            Location x1 = new Location(x + 1, y);
            Location x2 = new Location(x - 1, y);
            Location x3 = new Location(x, y + 1);
            Location x4 = new Location(x, y - 1);

            if ((Board.isValid(x1)) && (getPlayer().getBoard().isEmpty(x1)))
                list.add(x1);
            if ((Board.isValid(x2)) && (getPlayer().getBoard().isEmpty(x2)))
                list.add(x2);
            if ((Board.isValid(x3)) && (getPlayer().getBoard().isEmpty(x3)))
                list.add(x3);
            if ((Board.isValid(x4)) && (getPlayer().getBoard().isEmpty(x4)))
                list.add(x4);
            if (getPlayer().getBoard().isEmpty(loc)) {
                list.add(loc);
            }
        }
        return list;
    }

    private boolean isSurrounded(Board b, Location f1, Location f2, Location f3, Location f4) {

        boolean surrounded = false;
        if((Board.isValid(f1)) && //if surrounded by all shrubs
                (Board.isValid(f2)) &&
                (Board.isValid(f3)) &&
                (Board.isValid(f4)) &&
                ((b.unitAt(f1) instanceof Shrub)) &&
                ((b.unitAt(f2) instanceof Shrub)) &&
                ((b.unitAt(f3) instanceof Shrub)) &&
                ((b.unitAt(f4) instanceof Shrub))) {
            surrounded = true;
        }
        else { //if surrounded by a combination of shrubs and board boarder
            int numSurroundedBy = 4;
            int currentNumSurrounded = 0;

            //adding all invalid blocks (edge borders)
            if(!Board.isValid(f1)) {
                currentNumSurrounded++;
            }
            if(!Board.isValid(f2)) {
                currentNumSurrounded++;
            }
            if(!Board.isValid(f3)) {
                currentNumSurrounded++;
            }
            if(!Board.isValid(f4)) {
                currentNumSurrounded++;
            }
            //adding all shrubs surround
            if((b.unitAt(f1) instanceof Shrub)) {
                currentNumSurrounded++;
            }
            if((b.unitAt(f2) instanceof Shrub)) {
                currentNumSurrounded++;
            }
            if((b.unitAt(f3) instanceof Shrub)) {
                currentNumSurrounded++;
            }
            if((b.unitAt(f4) instanceof Shrub)) {
                currentNumSurrounded++;
            }
            if(currentNumSurrounded == 4) {  //if surrounded by 4 of the above options, return true
                surrounded = true;
            }
        }
        return surrounded;
    }

    protected void attack(Location loc, BlockingInfo info)
    {
        setDirection(getLOSdirection(loc));

        ArrayList<Location> list = affectedByAttack(loc);
        for (Location x : list)
        {
            if (getPlayer().getBoard().unitAt(x) == null) {
                getPlayer().getBoard().add(new Shrub(x, this));
            }
        }
        Location f1 = new Location(location().getX() + 1, location().getY());
        Location f2 = new Location(location().getX() - 1, location().getY());
        Location f3 = new Location(location().getX(), location().getY() + 1);
        Location f4 = new Location(location().getX(), location().getY() - 1);

        Board b = getPlayer().getBoard();

        boolean surrounded = false;
        boolean enraged = baseStats() == ENRAGED;
        if (enraged)
        {
            surrounded = isSurrounded(b, f1, f2, f3, f4);

//            surrounded = (Board.isValid(f1)) &&
//                    (Board.isValid(f2)) &&
//                    (Board.isValid(f3)) &&
//                    (Board.isValid(f4)) &&
//                    ((b.unitAt(f1) instanceof Shrub)) &&
//                    ((b.unitAt(f2) instanceof Shrub)) &&
//                    ((b.unitAt(f3) instanceof Shrub)) &&
//                    ((b.unitAt(f4) instanceof Shrub));
        }
        if (surrounded)
        {
            setBaseStats(EXHAUSTED);
        }
    }

    protected boolean canattack(Location loc)
    {
        Location f1 = new Location(location().getX() + 1, location().getY());
        Location f2 = new Location(location().getX() - 1, location().getY());
        Location f3 = new Location(location().getX(), location().getY() + 1);
        Location f4 = new Location(location().getX(), location().getY() - 1);

        boolean enraged = baseStats() == ENRAGED;
        boolean surrounded = false;

        Board b = getPlayer().getBoard();

        ArrayList<Location> list = new ArrayList();
        if (enraged)
        {
            surrounded = isSurrounded(b, f1, f2, f3, f4);

//            surrounded = (Board.isValid(f1)) &&
//                    (Board.isValid(f2)) &&
//                    (Board.isValid(f3)) &&
//                    (Board.isValid(f4)) &&
//                    ((b.unitAt(f1) instanceof Shrub)) &&
//                    ((b.unitAt(f2) instanceof Shrub)) &&
//                    ((b.unitAt(f3) instanceof Shrub)) &&
//                    ((b.unitAt(f4) instanceof Shrub));
        }

        if (surrounded)
            return location().equals(loc);
        if (Board.distance(loc, location()) <= 2)
        {

            int x = loc.getX();
            int y = loc.getY();

            Location x1 = new Location(x + 1, y);
            Location x2 = new Location(x - 1, y);
            Location x3 = new Location(x, y + 1);
            Location x4 = new Location(x, y - 1);

            if ((Board.isValid(x1)) && (getPlayer().getBoard().isEmpty(x1)))
                list.add(x1);
            if ((Board.isValid(x2)) && (getPlayer().getBoard().isEmpty(x2)))
                list.add(x2);
            if ((Board.isValid(x3)) && (getPlayer().getBoard().isEmpty(x3)))
                list.add(x3);
            if ((Board.isValid(x4)) && (getPlayer().getBoard().isEmpty(x4)))
                list.add(x4);
            if (getPlayer().getBoard().isEmpty(loc)) {
                list.add(loc);
            }
            return !list.isEmpty();
        }
        return false;
    }

    private void removeFocusEffectsOnEnraged() { //TODO remove effect on source focuser
        //this.getEffects();
//        for(Effect e: this.getEffects()) {
//            if(e instanceof Paralyze || e instanceof  Poison || e instanceof  Barrier) {
//                this.removeEffect(e);
//            }
//        }
    }

    public void onEndTurn(Player p)
    {
        super.onEndTurn(p);
        if ((baseStats() == EXHAUSTED) && (getPlayer() == p) && (recovery() == 1))
            setBaseStats(DEFAULT);
        if ((baseStats() == ENRAGED) && (getPlayer() == p)) {
            setBaseStats(DEFAULT);
        }
    }

    public void onMobileAllyDeath() {
        setRecovery(0);
        setBaseStats(ENRAGED);
        removeFocusEffectsOnEnraged();
    }

    public boolean initialRecoveryExemption()
    {
        return true;
    }

    public int maxNum()
    {
        return 0;
    }

    public String toAbrev()
    {
        return "f";
    }

    public int unitCount()
    {
        return 1;
    }

    public char toChar() {
        return 'F';
    }

    public boolean canFocus() {
        return false;
    }
}
