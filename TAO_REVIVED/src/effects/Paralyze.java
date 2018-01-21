package effects;

import units.BasicUnit;

public class Paralyze extends Effect
{
    public Paralyze(final BasicUnit unit) {
        super(unit);
    }
    
    public boolean stopsAttacking() {
        return true;
    }
    
    public boolean stopsBlocking() {
        return true;
    }
    
    public boolean stopsMoving() {
        return true;
    }
    
    public boolean stopsSteppingAside() {
        return true;
    }
    
    public String toString() {
        return "Paralyze";
    }
}
