// 
// Decompiled by Procyon v0.5.30
// 

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



//        int powerPerUser = (numDSMs + numPyros) * numDragons * 12;
//        int numUsers = numDSMs + numPyros;
//        int totalDragonPower = numDragons * 28;

        if (numDragons == 0 || numDSMs == 0) {
            this.end();
            return 0;
        }

        int numSharedUsers = numPyros + numDSMs;
        int numPowerToShare = numSharedUsers * numDragons * 12;
        int totalDragonPower = numDragons * 28;


        if(numPyros > 0 && numDSMs == 1) {  //Only 1 DSM and 1 pyro
            final int transfer = 12;
            if (this.unit instanceof DragonTyrant) {
                return -transfer * (numPyros + 1);
            }
            return transfer;
        }
//        else if(numPyros > 1 && numDSMs == 1) { //Only 1 DSM and many pyros
//
//        }
        else {
            if(numPowerToShare > totalDragonPower) { //distribute total dragon power to all pyro and dsm
                int numPowerToDistribute = (int)Math.ceil((float)totalDragonPower / (float)numSharedUsers);
                if (this.unit instanceof DragonTyrant) {
                    return -28;
                }
                return numPowerToDistribute;
            }
            else { //otherwise distribute numPowertoGrab
                int numPowerToDistribute = (int)Math.ceil((float)numPowerToShare / (float)numSharedUsers);
                if (this.unit instanceof DragonTyrant) {
                    //if(12 * numSharedUsers > numPowerToShare) {
                    return -12 * numSharedUsers;
                    //}
                    //else {
                    //return -12 * numSharedUsers;
                    //}
                }
                return numPowerToDistribute;
            }
        }











//        if (numPyros < 2 && numDSMs == 1) {  //works!
//            final int transfer = 12;
//            if (this.unit instanceof DragonTyrant) {
//                return -transfer * (numPyros + 1);
//            }
//            return transfer;
//        }
//        else {  //more than one dragon speaker
//
//
//
//            final int transfer = (int) Math.ceil(28.0 / (numPyros + numDSMs));
//            System.out.println("transfer is: " + transfer);
//            if (this.unit instanceof DragonTyrant) {
//                return -100;
//            }
//            return transfer;
//        }
//        else if(numPyros > 1 && numDSMs > 1) {
//
//        }
//
//        else {
//            final int transfer = (int) Math.ceil(28.0 / (numPyros + 1));
//            if (this.unit instanceof DragonTyrant) {
//                return -28;
//            }
//            return transfer;
//        }
    }
}


        /*
        DSM = 2
        Pyro = 0
        Dragons = 4
        powerShare = 2 * 12 * 4 =
         */
//        if (numPowerToShare < totalDragonPower) {
//            final int transfer = 12;
//            //final int transfer = (int)Math.ceil(28.0 / (numPyros + 1));
//            if (this.unit instanceof DragonTyrant) {
//                return -28;
//            }
//            return transfer;
//            if (this.unit instanceof DragonTyrant) {
//                return -transfer * (numPyros + 1);
//            }
//            return transfer;


//        } else {
//            final int transfer = (int)Math.ceil(28.0 / (numPyros + 1));
//            if (this.unit instanceof DragonTyrant) {
//                return -28;
//            }
//            return transfer;
//        }
//    }
