// 
// Decompiled by Procyon v0.5.30
// 

package actions;

import units.BasicUnit;
import app.App;

public class EndTurn implements Action
{
    public void act(final App app) {
    }
    
    public boolean isValid() {
        return true;
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
        return null;
    }
    
    public String toString() {
        return "E";
    }
    
    public boolean equals(final Object other) {
        return other instanceof EndTurn && this.toString() == other.toString();
    }
}
