package util;

public class Location
{
    private int x;
    private int y;
    
    public Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

    public boolean equals(final Object obj) {
        final Location other = (Location)obj;
        return other.x == this.x && other.y == this.y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
    
    public Location neighborOn(final Direction dir) {
        if (dir.equals(Direction.NORTH)) {
            return new Location(this.x + 1, this.y);
        }
        if (dir.equals(Direction.SOUTH)) {
            return new Location(this.x - 1, this.y);
        }
        if (dir.equals(Direction.EAST)) {
            return new Location(this.x, this.y + 1);
        }
        return new Location(this.x, this.y - 1);
    }
}
