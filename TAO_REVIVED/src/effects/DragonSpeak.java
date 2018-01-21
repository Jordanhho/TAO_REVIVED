package effects;

import java.util.Iterator;

import units.Pyromancer;
import units.DragonSpeakerMage;
import units.DragonTyrant;
import units.Unit;
import units.BasicUnit;

public class DragonSpeak extends Effect {
    private BasicUnit unit;

    public DragonSpeak(final BasicUnit unit) {
        super(unit);
        this.unit = unit;
    }

    public int powerChange() {
        final Iterator<Unit> itr = this.unit.getPlayer().getBoard().iterator();
        int numPyros = 0;
        int numDragons = 0;
        int numDSMs = 0;
        while (itr.hasNext()) {
            final Unit u = itr.next();
            if (u.getPlayer() == this.unit.getPlayer()) {
                if (u instanceof DragonTyrant) {
                    numDragons++;
                } else if (u instanceof DragonSpeakerMage) {
                    numDSMs++;
                } else {
                    if (!(u instanceof Pyromancer)) {
                        continue;
                    }
                    ++numPyros;
                }
            }
        }
        if (numDragons == 0 || numDSMs == 0) {
            this.end();
            return 0;
        }

        int numSharedUsers = numPyros + numDSMs;
        int numPowerToShare = numSharedUsers * numDragons * 12;
        int totalDragonPower = numDragons * 28;

        if (numPowerToShare > totalDragonPower) { //distribute total dragon power to all pyro and dsm
            int numPowerToDistribute = (int) Math.ceil((float) totalDragonPower / (float) numSharedUsers);
            if (this.unit instanceof DragonTyrant) {
                return -28;
            }
            return numPowerToDistribute;
        } else { //otherwise distribute numPowertoGrab
            int numPowerToDistribute = (int) Math.ceil((float) numPowerToShare / (float) numSharedUsers);
            if (this.unit instanceof DragonTyrant) {
                return -12 * numSharedUsers;
            }
            return numPowerToDistribute;
        }
    }
}