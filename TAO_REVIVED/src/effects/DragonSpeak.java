package effects;

import java.util.Iterator;
import units.Pyromancer;
import units.DragonSpeakerMage;
import units.DragonTyrant;
import units.Unit;
import units.BasicUnit;

public class DragonSpeak extends Effect
{
    private BasicUnit unit;
    
    public DragonSpeak(final BasicUnit unit) {
        super(unit);
        this.unit = unit;
    }
    
    public int powerChange() {
        final Iterator<Unit> itr = this.unit.getPlayer().getBoard().iterator();
        boolean hasDragon = false;
        boolean hasDSM = false;
        int numPyros = 0;
        while (itr.hasNext()) {
            final Unit u = itr.next();
            if (u.getPlayer() == this.unit.getPlayer()) {
                if (u instanceof DragonTyrant) {
                    hasDragon = true;
                }
                else if (u instanceof DragonSpeakerMage) {
                    hasDSM = true;
                }
                else {
                    if (!(u instanceof Pyromancer)) {
                        continue;
                    }
                    ++numPyros;
                }
            }
        }
        if (!hasDragon || !hasDSM) {
            this.end();
            return 0;
        }
        if (numPyros < 2) {
            final int transfer = 12;
            if (this.unit instanceof DragonTyrant) {
                return -transfer * (numPyros + 1);
            }
            return transfer;
        }
        else {
            final int transfer = (int)Math.round(28.0 / (numPyros + 1));
            if (this.unit instanceof DragonTyrant) {
                return -28;
            }
            return transfer;
        }
    }
}
