package effects;

import units.BasicUnit;

public abstract class Effect
{
    private boolean done;
    private BasicUnit unit;
    
    public Effect(final BasicUnit unit) {
        this.unit = unit;
        this.done = false;
    }
    
    public BasicUnit getUnit() {
        return this.unit;
    }
    
    public boolean isDone() {
        return this.done;
    }
    
    public final void end() {
        this.done = true;
    }
    
    public int armorChange() {
        return 0;
    }
    
    public int powerChange() {
        return 0;
    }
    
    public boolean stopsAttacking() {
        return false;
    }
    
    public boolean stopsBlocking() {
        return false;
    }
    
    public boolean stopsDamage() {
        return false;
    }
    
    public boolean stopsMoving() {
        return false;
    }
    
    public boolean stopsSteppingAside() {
        return false;
    }
    
    public boolean endsOnlyAfterTurnEnds() {
        return false;
    }
    
    public void onDamage() {
    }
    
    public void onParalysis() {
    }
    
    public void onAttack() {
    }
    
    public void onMove() {
    }
    
    public void onTurn() {
    }
}
