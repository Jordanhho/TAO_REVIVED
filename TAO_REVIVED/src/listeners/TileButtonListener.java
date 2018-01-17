// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.MouseEvent;
import util.Location;
import app.App;
import java.awt.event.MouseListener;

public class TileButtonListener implements MouseListener
{
    private App app;
    private Location loc;
    private static Location mouseIsHere;
    
    public Location getMouseLocation() {
        return TileButtonListener.mouseIsHere;
    }
    
    public TileButtonListener(final App app, final Location loc) {
        this.app = app;
        this.loc = loc;
    }
    
    public void mousePressed(final MouseEvent arg0) {
        this.app.TilePressedAction(this.loc);
    }
    
    public void mouseEntered(final MouseEvent arg0) {
        TileButtonListener.mouseIsHere = this.loc;
        this.app.UpdateTextPanel();
        this.app.UpdateTiles();
    }
    
    public void mouseExited(final MouseEvent arg0) {
        if (TileButtonListener.mouseIsHere == this.loc) {
            TileButtonListener.mouseIsHere = null;
        }
        this.app.UpdateTextPanel();
        this.app.UpdateTiles();
    }
    
    public void mouseReleased(final MouseEvent arg0) {
    }
    
    public void mouseClicked(final MouseEvent arg0) {
    }
}
