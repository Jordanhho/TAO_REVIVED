// 
// Decompiled by Procyon v0.5.30
// 

package effects;

import units.BasicUnit;

public class Stoned extends Effect
{
    public Stoned(final BasicUnit unit) {
        super(unit);
    }
    
    public int armorChange() {
        return 30;
    }
}
