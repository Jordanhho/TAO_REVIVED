// 
// Decompiled by Procyon v0.5.30
// 

package game;

import units.BasicUnit;

import java.util.*;

import util.Location;

public class Setup
{
    private Map<Location, Character> map = new HashMap<Location, Character>(); //used to use Hashmap object, now use Map

    public Setup(final boolean defaultSetup) {
        this.map = new HashMap<Location, Character>();
        if (defaultSetup) {
            this.add(new Location(4, 4), 'K');
            this.add(new Location(4, 5), 'p');
            this.add(new Location(4, 6), 'K');
            this.add(new Location(0, 5), 'C');
            this.add(new Location(1, 5), 'S');
            this.add(new Location(2, 5), 'L');
            this.add(new Location(3, 5), 'b');
            this.add(new Location(0, 6), 's');
            this.add(new Location(0, 4), 'F');
            this.add(new Location(3, 6), 'G');
        }
    }
    
    public void add(final Location loc, final Character unit) {

        this.map.put(loc, unit);
    }
    
    public void clear() {
        this.map.clear();
    }
    
    public char remove(final Location loc) {
        for (final Location temp : this.map.keySet()) {
            if (temp.equals(loc)) {
                return this.map.remove(temp);
            }
        }
        throw new NoSuchElementException("Location " + loc + " could not be removed");
    }
    
    public Set<Location> keySet() {
        return this.map.keySet();
    }
    
    public boolean containsKey(final Location loc) {
        final Iterator<Location> itr = this.map.keySet().iterator();
        while (itr.hasNext()) {
            if (itr.next().equals(loc)) {
                return true;
            }
        }
        return false;
    }
    
    public Character get(final Location loc) {
        for (final Location temp : this.map.keySet()) {
            if (temp.equals(loc)) {
                return this.map.get(temp);
            }
        }
        return null;
    }
    
    public boolean isValid() {
        int unitcount = 0;
        final Set<Location> locs = this.map.keySet();
        boolean containsMobile = false;
        for (final Location x : locs) {
            final BasicUnit unit = BasicUnit.GenerateUnit(this.map.get(x), new Player("temp"), x);
            unitcount += unit.unitCount();
            if (unit.mobile()) {
                containsMobile = true;
            }
            if (!isValid(x)) {
                return false;
            }
            if (unit.maxNum() <= 0) {
                continue;
            }
            int number = 0;
            for (final Location y : locs) {
                if (unit.getClass() == BasicUnit.GenerateUnit(this.map.get(y), new Player("temp"), y).getClass()) {
                    ++number;
                }
            }
            if (number > unit.maxNum()) {
                return false;
            }
        }
        return unitcount <= 10 && containsMobile;
    }
    
    public static boolean isValid(final Location loc) {
        final int x = loc.getX();
        final int y = loc.getY();
        return x >= 0 && x < 5 && y >= 0 && y < 11 && x + y > 1 && y - x < 9;
    }
    
    public static Location mirror(final Location loc) {
        return new Location(10 - loc.getX(), 10 - loc.getY());
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder("");
        for(Map.Entry<Location, Character> setupUnit : map.entrySet()) { //loops through the hashmap
            s.append(setupUnit.getKey().getX()).append(setupUnit.getKey().getY()).append(setupUnit.getValue().toString()); //appends each unit to player's list of unit
        }
        return s.toString();
    }
}
