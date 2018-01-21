// 
// Decompiled by Procyon v0.5.30
// 

package actions;

import java.util.Iterator;
import effects.Effect;
import units.DragonSpeakerMage;
import units.Pyromancer;
import units.Assassin;
import app.App;
import units.Unit;
import units.BasicUnit;
import game.BlockingInfo;
import game.Board;
import util.Location;

public class Attack implements Action
{
    private Location loc;
    private Location target;
    private Board theBoard;
    private BlockingInfo blockingInfo;
    
    public Attack(final Board board, final Location loc, final Location target, final char blockingInfo) {
        this.theBoard = board;
        this.loc = loc;
        this.target = target;
        this.blockingInfo = new BlockingInfo(blockingInfo);
    }
    
    public Attack(final Board board, final Location loc, final Location target) {
        this.theBoard = board;
        this.loc = loc;
        this.target = target;
        this.blockingInfo = new BlockingInfo();
    }
    
    public boolean isValid() {
        final Unit unit = this.theBoard.unitAt(this.loc);
        return unit != null && unit instanceof BasicUnit && this.unit().canAttack(this.target);
    }
    
    public void act(final App app) {
        if (!this.isValid()) {
            throw new IllegalArgumentException("Can't perform invalid action");
        }
        final BasicUnit unit = (BasicUnit)this.theBoard.unitAt(this.loc);
        this.blockingInfo.setApp(app);
        unit.Attack(this.target, this.blockingInfo);
    }
    
    public boolean endsTurn() {
        if (this.unit() == null) {
            return true;
        }
        if (this.unit() instanceof Assassin && this.loc.equals(this.target)) {
            return true;
        }
        if (this.unit() instanceof Pyromancer || this.unit() instanceof DragonSpeakerMage) {
            final int dx = Math.abs(this.loc.getX() - this.target.getX());
            final int dy = Math.abs(this.loc.getY() - this.target.getY());
            if (dx + dy <= 1) {
                int damage = (int)Math.round(this.unit().power() * (100 - this.unit().armor()) / 100.0);
                for (final Effect e : this.unit().getEffects()) {
                    if (e.stopsDamage()) {
                        damage = 0;
                    }
                }
                if (damage >= this.unit().hitPoints()) {
                    return true;
                }
            }
        }
        return this.unit().canFocus() || !this.unit().mobile();
    }
    
    public boolean isAttack() {
        return true;
    }
    
    public boolean isMove() {
        return false;
    }
    
    public BasicUnit unit() {
        return (BasicUnit)this.theBoard.unitAt(this.loc);
    }
    
    public Location target() {
        return this.target;
    }
    
    public BlockingInfo getBlockingInfo() {
        return this.blockingInfo;
    }
    
    public String toString() {
        final char x1 = (char)(this.loc.getX() + 48);
        final char y1 = (char)(this.loc.getY() + 48);
        final char x2 = (char)(this.target.getX() + 48);
        final char y2 = (char)(this.target.getY() + 48);
        return "A" + x1 + y1 + x2 + y2 + this.blockingInfo;
    }
    
    public boolean equals(final Object other) {
        return other instanceof Attack && this.toString() == other.toString();
    }
}
