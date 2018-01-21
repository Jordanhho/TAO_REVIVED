package effects;

import units.BasicUnit;

public class Barrier extends Effect
{
    public Barrier(final BasicUnit unit) {
        super(unit);
    }
    
    public boolean stopsDamage() {
        return true;
    }
    
    public boolean stopsSteppingAside() {
        return true;
    }
    
    public void onMove() {
        this.end();
    }
    
    public void onAttack() {
        this.end();
    }
    
    public String toString() {
        return "Barrier";
    }
}
