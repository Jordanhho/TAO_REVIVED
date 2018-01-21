package actions;

import app.App;
import units.BasicUnit;

public class Cancel implements Action {

    @Override
    public void act(App p0) {

    }

    @Override
    public boolean endsTurn() {
        return false;
    }

    @Override
    public boolean isAttack() {
        return false;
    }

    @Override
    public boolean isMove() {
        return false;
    }

    @Override
    public BasicUnit unit() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
