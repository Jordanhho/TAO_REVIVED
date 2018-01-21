package util;

import javax.swing.ImageIcon;

public class Direction
{
    public static final Direction NORTH;
    public static final Direction EAST;
    public static final Direction SOUTH;
    public static final Direction WEST;
    private int dir;
    
    static {
        NORTH = new Direction(0);
        EAST = new Direction(1);
        SOUTH = new Direction(2);
        WEST = new Direction(3);
    }
    
    public Direction(final int i) {
        this.dir = i;
    }
    
    public boolean equals(final Object obj) {
        if (obj instanceof Direction) {
            final Direction other = (Direction)obj;
            return this.dir == other.dir;
        }
        return false;
    }
    
    public Direction rotateClockwise() {
        return new Direction((this.dir + 1) % 4);
    }
    
    public Direction rotateCounterClockwise() {
        return new Direction((this.dir + 3) % 4);
    }
    
    public Direction rotateHalfTurn() {
        return new Direction((this.dir + 2) % 4);
    }
    
    public ImageIcon getImage() {
        return new ImageIcon(this.getClass().getResource("/images/arrow" + this.dir + ".GIF"));
    }
    
    public String toString() {
        switch (this.dir) {
            case 0: {
                return "(1,0)";
            }
            case 1: {
                return "(0,1)";
            }
            case 2: {
                return "(-1,0)";
            }
            case 3: {
                return "(0,-1)";
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    public int intValue() {
        return this.dir;
    }
}
