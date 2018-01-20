package units;

import game.BlockingInfo;
import game.Board;
import game.Player;
import java.util.ArrayList;
import java.util.Iterator;
import util.Location;





public class Furgon
        extends BasicUnit
{
    private static final BaseStats DEFAULT = new BaseStats("Furgon", 48, 0, 0, 50, 1, 3, true, false);
    private static final BaseStats EXHAUSTED = new BaseStats("Exhausted Furgon", 48, 0, 0, 0, 12, 3, true, false);
    private static final BaseStats ENRAGED = new BaseStats("Enraged Furgon", 48, 0, 0, 50, 1, 3, true, false);

    public Furgon(Player owner, Location loc)
    {
        super(new BaseStats("Furgon", 48, 0, 0, 50, 1, 3, true, false), owner, loc);
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

            surrounded = (Board.isValid(f1)) &&
                    (Board.isValid(f2)) &&
                    (Board.isValid(f3)) &&
                    (Board.isValid(f4)) &&
                    ((b.unitAt(f1) instanceof Shrub)) &&
                    ((b.unitAt(f2) instanceof Shrub)) &&
                    ((b.unitAt(f3) instanceof Shrub)) &&
                    ((b.unitAt(f4) instanceof Shrub));
        }

        if (surrounded)
        {
            Iterator<Unit> itr = getPlayer().getBoard().iterator();
            while (itr.hasNext())
            {
                Unit next = (Unit)itr.next();
                if (((next instanceof BasicUnit)) &&
                        (((BasicUnit)next).getPlayer() != getPlayer()) &&
                        (((BasicUnit)next).mobile()))
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
            surrounded = (Board.isValid(f1)) &&
                    (Board.isValid(f2)) &&
                    (Board.isValid(f3)) &&
                    (Board.isValid(f4)) &&
                    ((b.unitAt(f1) instanceof Shrub)) &&
                    ((b.unitAt(f2) instanceof Shrub)) &&
                    ((b.unitAt(f3) instanceof Shrub)) &&
                    ((b.unitAt(f4) instanceof Shrub));
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
        if (enraged)
        {
            surrounded = (Board.isValid(f1)) &&
                    (Board.isValid(f2)) &&
                    (Board.isValid(f3)) &&
                    (Board.isValid(f4)) &&
                    ((b.unitAt(f1) instanceof Shrub)) &&
                    ((b.unitAt(f2) instanceof Shrub)) &&
                    ((b.unitAt(f3) instanceof Shrub)) &&
                    ((b.unitAt(f4) instanceof Shrub));
        }



        if (surrounded)
            return location().equals(loc);
        if (Board.distance(loc, location()) <= 2)
        {
            ArrayList<Location> list = new ArrayList();

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
