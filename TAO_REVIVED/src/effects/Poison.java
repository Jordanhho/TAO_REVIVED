package effects;

import units.BasicUnit;

public class Poison extends Effect
{
    public static final int DAMAGE = 4;
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
