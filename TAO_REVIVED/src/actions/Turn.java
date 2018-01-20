//
// Decompiled by Procyon v0.5.30
//

package actions;

import app.App;
import units.Unit;
import units.BasicUnit;
import game.Board;
import util.Direction;
import util.Location;

public class Turn implements Action
{
    private Location loc;
    private Direction direction;
    private Board theBoard;
    
    public Turn(final Board board, final Location loc, final Direction direction) {
        this.theBoard = board;
        this.loc = loc;
        this.direction = direction;
    }
    
    public boolean isValid() {
        final Unit unit = this.theBoard.unitAt(this.loc);
        return unit != null && unit instanceof BasicUnit && ((BasicUnit)unit).canRotate();
    }
    
    public void act(final App app) {
        if (!this.unit().canRotate()) {
            throw new IllegalArgumentException("Can't rotate");
        }
        this.unit().Rotate(this.direction);
    }
    
    public boolean endsTurn() {
        return true;
    }
    
    public boolean isAttack() {
        return false;
    }
    
    public boolean isMove() {
        return false;
    }
    
    public BasicUnit unit() {
        return (BasicUnit)this.theBoard.unitAt(this.loc);
    }
    
    public String toString() {
        final char x1 = (char)(this.loc.getX() + 48);
        final char y1 = (char)(this.loc.getY() + 48);
        final int d = this.direction.intValue();
        return "T" + x1 + y1 + d;
    }
    
    public boolean equals(final Object other) {
        return other instanceof Turn && this.toString() == other.toString();
    }
}
