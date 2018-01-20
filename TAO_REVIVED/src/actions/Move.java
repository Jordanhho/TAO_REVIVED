package actions;

import app.App;
import units.Unit;
import units.BasicUnit;
import game.Board;
import util.Location;

public class Move implements Action
{
    private Location loc;
    private Location destination;
    private Board theBoard;
    
    public Move(final Board board, final Location loc, final Location destination) {
        this.theBoard = board;
        this.loc = loc;
        this.destination = destination;
    }
    
    public boolean isValid() {
        final Unit unit = this.theBoard.unitAt(this.loc);
        return unit != null && unit instanceof BasicUnit && ((BasicUnit)unit).canMove(this.destination);
    }
    
    public boolean act(final App app) {
        if (!this.isValid()) {
            throw new IllegalArgumentException("Can't perform invalid action");
        }
        final BasicUnit unit = (BasicUnit)this.theBoard.unitAt(this.loc);
        unit.Move(this.destination);
        return false;
    }
    
    public boolean endsTurn() {
        return false;
    }
    
    public boolean isAttack() {
        return false;
    }
    
    public boolean isMove() {
        return true;
    }
    
    public BasicUnit unit() {
        return (BasicUnit)this.theBoard.unitAt(this.loc);
    }
    
    public Location destination() {
        return this.destination;
    }
    
    public String toString() {
        final char x1 = (char)(this.loc.getX() + 48);
        final char y1 = (char)(this.loc.getY() + 48);
        final char x2 = (char)(this.destination.getX() + 48);
        final char y2 = (char)(this.destination.getY() + 48);
        return "M" + x1 + y1 + x2 + y2;
    }
    
    public boolean equals(final Object other) {
        return other instanceof Move && this.toString() == other.toString();
    }
}
