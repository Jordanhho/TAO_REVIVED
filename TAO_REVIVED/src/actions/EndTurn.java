package actions;

import units.BasicUnit;
import app.App;

public class EndTurn implements Action
{
    public boolean act(final App app) { return false; }
    
    public boolean isValid() {
        return true;
    }
    
    public boolean endsTurn() {
        return true;
    }
    
    public boolean isAttack() { return false; }
    
    public boolean isMove() {
        return false;
    }
    
    public BasicUnit unit() {
        return null;
    }
    
    public String toString() {
        return "E";
    }
    
    public boolean equals(final Object other) {
        return other instanceof EndTurn && this.toString().equals(other.toString());
    }
}
