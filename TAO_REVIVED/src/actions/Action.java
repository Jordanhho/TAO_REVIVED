package actions;

import units.BasicUnit;
import app.App;

public interface Action
{
    boolean act(final App p0);
    
    boolean endsTurn();
    
    boolean isAttack();
    
    boolean isMove();
    
    BasicUnit unit();
    
    boolean isValid();
}
