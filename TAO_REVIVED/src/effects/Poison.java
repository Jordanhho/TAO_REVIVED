// 
// Decompiled by Procyon v0.5.30
// 

package effects;

import units.BasicUnit;

public class Poison extends Effect //effect for poison wisp
{
    public static final int DAMAGE = 5; //used to be 5
    private boolean firstTurn;
    
    public Poison(final BasicUnit unit) {
        super(unit);
        this.firstTurn = true;
    }
    
    public boolean isFirstTurn() {
        return this.firstTurn;
    }
    
    public void endFirstTurn() {
        this.firstTurn = false;
    }
    
    public boolean stopsAttacking() {
        return true;
    }
    
    public boolean stopsSteppingAside() {
        return true;
    }
    
    public boolean endsOnlyAfterTurnEnds() {
        return true;
    }
    
    public String toString() {
        return "Poison";
    }
    
    public void onMove() {
        this.end();
    }
}
