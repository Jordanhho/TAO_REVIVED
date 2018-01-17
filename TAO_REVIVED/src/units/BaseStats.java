// 
// Decompiled by Procyon v0.5.30
// 

package units;

public class BaseStats
{
    public final String name;
    public final int maxHP;
    public final int power;
    public final int armor;
    public final int blocking;
    public final int recovery;
    public final int movement;
    public final boolean stepsAside;
    public final boolean teleports;
    
    public BaseStats(final String name, final int hp, final int power, final int armor, final int blocking, final int recovery, final int movement, final boolean stepsAside, final boolean teleports) {
        this.name = name;
        this.maxHP = hp;
        this.power = power;
        this.armor = armor;
        this.blocking = blocking;
        this.recovery = recovery;
        this.movement = movement;
        this.stepsAside = stepsAside;
        this.teleports = teleports;
    }

    public String getName() {
        return name;
    }
}
