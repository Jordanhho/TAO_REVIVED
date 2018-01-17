package units;

import game.BlockingInfo;
import game.Board;
import game.Player;
import util.Location;


public class Shrub
        implements Unit
{
    private Location loc;
    private Board board;
    private BaseStats baseStats;

    public Shrub(Location loc, Furgon furgon)
    {
        this.loc = loc;
        board = furgon.getPlayer().getBoard();
        baseStats = new BaseStats("Shrub", 1, 0, 0, 0, 0, 0, false, false);
    }

    public int armor() {
        return 0;
    }

    public void attacked(int power)
    {
        board.remove(this);
    }

    public boolean attacked(int power, Location loc, BlockingInfo info)
    {
        board.remove(this);
        return true;
    }

    public BaseStats baseStats()
    {
        return baseStats;
    }

    public int blockingBonus() {
        return 0;
    }

    public Player getPlayer() {
        return null;
    }

    public Location location() {
        return loc;
    }



    public void onEndTurn(Player turnOwner) {}


    public boolean stepsAside()
    {
        return false;
    }

    public String toAbrev()
    {
        return "Shrub";
    }

    public char toChar()
    {
        return '*';
    }

    public String toDisplay() {
        return "Shrub";
    }
}
