// 
// Decompiled by Procyon v0.5.30
// 

package actions;

import units.BasicUnit;
import app.App;

public interface Action
{
    void act(final App p0);
    
    boolean endsTurn();
    
    boolean isAttack();
    
    boolean isMove();
    
    BasicUnit unit();
    
    boolean isValid();
}
