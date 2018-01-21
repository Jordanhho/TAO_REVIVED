// 
// Decompiled by Procyon v0.5.30
// 

package game;

public class Player
{
    private Board board;
    private final Setup setup;
    private String ID;
    
    public Player(final String ID) {
        this.ID = ID;
        this.board = null;
        this.setup = new Setup(true);
    }
    
    public Player(final String ID, final Setup setup) {
        this.ID = ID;
        this.board = null;
        this.setup = setup;
    }

    public void setID(final String ID) {
        this.ID = ID;
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public Setup getSetup() {
        return this.setup;
    }
    
    public void setBoard(final Board board) {
        this.board = board;
    }
    
    public String ID() {
        return this.ID;
    }
    
    public String toString() {
        return "" + this.ID + "\n" + this.setup.toString(); //builds player profile string
    }
}
