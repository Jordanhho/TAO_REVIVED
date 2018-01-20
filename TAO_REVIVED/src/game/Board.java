// 
// Decompiled by Procyon v0.5.30
// 

package game;

import java.util.Set;

import units.DragonSpeakerMage;
import util.Direction;
import util.ImageManager;
import units.BasicUnit;
import util.Location;
import java.util.Iterator;
import units.Unit;
import java.util.ArrayList;

public class Board
{
    private ArrayList<Unit> units;
    private Player player1;
    private Player player2;
    private boolean dragonSpeakInit = false;

    public boolean isDragonSpeakInit() {
        return dragonSpeakInit;
    }

    public Iterator<Unit> iterator() {
        return this.units.iterator();
    }
    
    public Board(final Player p1, final Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.player1.setBoard(this);
        this.player2.setBoard(this);
        final Setup s1 = p1.getSetup();
        final Setup s2 = p2.getSetup();
        this.units = new ArrayList<Unit>();
        if (!s1.isValid() || !s2.isValid()) {
            throw new IllegalArgumentException("One or more of the setups is not valid");
        }
        Set<Location> locations = s1.keySet();
        for (final Location loc : locations) {
            final BasicUnit unit = BasicUnit.GenerateUnit(s1.get(loc), p1, loc);
            unit.setImage(ImageManager.getInstance().getImage(s1.get(loc), true, Direction.NORTH));
            this.units.add(unit);
        }
        locations = s2.keySet();
        for (final Location loc : locations) {
            final BasicUnit unit = BasicUnit.GenerateUnit(s2.get(loc), p2, Setup.mirror(loc));
            this.units.add(unit);
        }
        for (final Unit u : this.units) {
            if(!(u instanceof DragonSpeakerMage)) {
                ((BasicUnit)u).init();
            }
            if(!dragonSpeakInit && u instanceof DragonSpeakerMage) {
                ((BasicUnit)u).init();
                System.out.println("init dragonSpeak!");
                dragonSpeakInit = true;
            }
//            if (u instanceof BasicUnit) {
//                ((BasicUnit)u).init();
//            }
        }
        for (final Unit u : this.units) {
            if (u instanceof BasicUnit) {
                ((BasicUnit)u).onEndRound();
            }
        }
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void add(final Unit u) {
        this.units.add(u);
    }
    
    public void toBeginning() {
        final Setup s1 = this.player1.getSetup();
        final Setup s2 = this.player2.getSetup();
        this.units = new ArrayList<Unit>();
        Set<Location> locations = s1.keySet();
        for (final Location loc : locations) {
            final BasicUnit unit = BasicUnit.GenerateUnit(s1.get(loc), this.player1, loc);
            this.units.add(unit);
        }
        locations = s2.keySet();
        for (final Location loc : locations) {
            final BasicUnit unit = BasicUnit.GenerateUnit(s2.get(loc), this.player2, Setup.mirror(loc));
            this.units.add(unit);
        }
        for (final Unit u : this.units) {
            if (u instanceof BasicUnit) {
                if(!(u instanceof DragonSpeakerMage)) {
                    ((BasicUnit)u).init();
                }
//                if(!dragonSpeakInit && u instanceof DragonSpeakerMage) {
//                    ((BasicUnit)u).init();
//                    System.out.println("init dragonSpeak!");
//                    dragonSpeakInit = true;
//                }
          }
        }
        for (final Unit u : this.units) {
            if (u instanceof BasicUnit) {
                ((BasicUnit)u).onEndRound();
            }
        }
    }
    
    public boolean isEmpty(final Location loc) {
        for (final Unit u : this.units) {
            if (u.location().equals(loc)) {
                return false;
            }
        }
        return true;
    }
    
    public Unit unitAt(final Location loc) {
        for (final Unit u : this.units) {
            if (u.location().equals(loc)) {
                return u;
            }
        }
        return null;
    }
    
    public static boolean isValid(final Location loc) {
        final int x = loc.getX();
        final int y = loc.getY();
        return x >= 0 && x < 11 && y >= 0 && y < 11 && x + y > 1 && x + y < 19 && x - y < 9 && y - x < 9;
    }
    
    public static int distance(final Location loc1, final Location loc2) {
        return Math.abs(loc1.getX() - loc2.getX()) + Math.abs(loc1.getY() - loc2.getY());
    }
    
    public static ArrayList<Location> neighborsOf(final Location loc) {
        final ArrayList<Location> array = new ArrayList<Location>();
        Location temp = new Location(loc.getX() + 1, loc.getY());
        if (isValid(temp)) {
            array.add(temp);
        }
        temp = new Location(loc.getX() - 1, loc.getY());
        if (isValid(temp)) {
            array.add(temp);
        }
        temp = new Location(loc.getX(), loc.getY() + 1);
        if (isValid(temp)) {
            array.add(temp);
        }
        temp = new Location(loc.getX(), loc.getY() - 1);
        if (isValid(temp)) {
            array.add(temp);
        }
        return array;
    }
    
    public void remove(final Unit unit) {
        this.units.remove(unit);
    }
}
