package effects;

import java.util.Iterator;
import units.BasicUnit;
import java.util.ArrayList;

public class Focus extends Effect
{
    private ArrayList<Effect> effects;
    
    public Focus(final BasicUnit unit) {
        super(unit);
        this.effects = new ArrayList<Effect>();
    }
    
    public void add(final Effect effect) {
        this.effects.add(effect);
    }
    
    public boolean isDone() {
        for (final Effect e : this.effects) {
            if (!e.isDone()) {
                return super.isDone();
            }
        }
        return true;
    }
    
    public boolean stopsBlocking() {
        return true;
    }
    
    public boolean stopsSteppingAside() {
        return true;
    }
    
    public void onMove() {
        this.End();
    }
    
    public void onDamage() {
        this.End();
    }
    
    public void onAttack() {
        this.End();
    }
    
    public void onTurn() {
        this.End();
    }
    
    public void onParalysis() {
        this.End();
    }
    
    public void End() {
        this.end();
        for (final Effect e : this.effects) {
            e.end();
        }
    }
    
    public String toString() {
        return "Focus";
    }
}
