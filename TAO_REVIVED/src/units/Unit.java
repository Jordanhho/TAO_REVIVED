package units;

import game.BlockingInfo;
import game.Player;
import util.Location;

public interface Unit
{
    BaseStats baseStats();
    
    int armor();
    
    int blockingBonus();
    
    boolean stepsAside();
    
    Location location();
    
    Player getPlayer();
    
    String toDisplay();
    
    void attacked(final int p0);
    
    boolean attacked(final int p0, final Location p1, final BlockingInfo p2);
    
    void onEndTurn(final Player p0);
    
    char toChar();
    
    String toAbrev();
}
