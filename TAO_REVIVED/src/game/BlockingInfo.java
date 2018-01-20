package game;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.Random;
import util.Location;
import app.App;

public class BlockingInfo
{
    private static final int BLOCKED = 1;
    private static final int HIT = 0;
    private static final int UNKNOWN = -1;
    private int[] attack;
    private int index;
    private App app;
    private boolean throwExceptionIfUsed;
    
    public BlockingInfo() {
        this.throwExceptionIfUsed = false;
        this.attack = new int[4];
        for (int i = 0; i < 4; ++i) {
            this.attack[i] = -1;
        }
    }
    
    public BlockingInfo(final char input) {
        this.throwExceptionIfUsed = false;
        this.attack = new int[4];
        final int value = input - '0';
        if (value >= 0 && value < 16) {
            this.attack[0] = value % 2;
            this.attack[1] = value / 2 % 2;
            this.attack[2] = value / 4 % 2;
            this.attack[3] = value / 8 % 2;
            return;
        }
        if (input != ' ') {
            throw new IllegalArgumentException();
        }
        this.throwExceptionIfUsed = true;
        this.attack[0] = 0;
        this.attack[1] = 0;
        this.attack[2] = 0;
        this.attack[3] = 0;
    }
    
    public String toString() {
        char value = '0';
        if (this.attack[0] != -1) {
            value += (char)(1 * this.attack[0]);
        }
        if (this.attack[1] != -1) {
            value += (char)(2 * this.attack[1]);
        }
        if (this.attack[2] != -1) {
            value += (char)(4 * this.attack[2]);
        }
        if (this.attack[3] != -1) {
            value += (char)(8 * this.attack[3]);
        }
        return String.valueOf(value);
    }
    
    public void setApp(final App app) {
        this.app = app;
    }
    
    public void doneAttacking() {
        if (this.index != 0) {
            if (this.throwExceptionIfUsed) {
                throw new IllegalStateException(String.valueOf(this.index));
            }
            this.index = 0;
        }
    }

    public boolean showAttackBlockingInfo(double percentBlock, final Location loc) {
        if (this.attack[this.index] == 0) {
            ++this.index;
            return false;
        }
        if (this.attack[this.index] == 1) {
            ++this.index;
            return true;
        }
        percentBlock = Math.round(percentBlock * 10.0) / 10.0;
        int result = JOptionPane.showConfirmDialog(this.app, "Blocking Chance: (" + percentBlock + "%)" + " Do you wish to Attack?", "Blocking Manager", 0, 3);
        return (result == 0);
    }

    public boolean block(double percentBlock, final Location loc) {
        if (this.attack[this.index] == 0) {
            ++this.index;
            return false;
        }
        if (this.attack[this.index] == 1) {
            ++this.index;
            return true;
        }
        percentBlock = Math.round(percentBlock * 10.0) / 10.0;
        boolean blocked;
        if (this.app.isBlockingRandomized()) {
            final Random rng = new Random();
            blocked = (rng.nextDouble() < percentBlock / 100.0);
        }
        else {
            this.app.UpdateTiles();
            this.app.getButton(loc.getX(), loc.getY()).setBackground(new Color(200, 100, 100));
            int result = JOptionPane.showConfirmDialog(this.app, "Is the attack blocked? (" + percentBlock + "%)", "Blocking Manager", 0, 3);
            blocked = (result == 0);
        }
        if (this.attack[this.index] == -1) {
            if (blocked) {
                this.attack[this.index] = 1;
            }
            else {
                this.attack[this.index] = 0;
            }
        }
        else {
            blocked = (this.attack[this.index] == 1);
        }
        ++this.index;
        return blocked;
    }




}
