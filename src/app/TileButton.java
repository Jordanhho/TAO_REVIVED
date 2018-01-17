// 
// Decompiled by Procyon v0.5.30
// 

package app;

import java.util.Iterator;
import javax.swing.Icon;
import java.util.Set;
import units.BasicUnit;
import game.Setup;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.event.KeyListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import effects.Effect;
import java.util.HashSet;
import javax.swing.JLabel;
import util.Direction;
import units.Unit;
import util.Location;
import javax.swing.JPanel;

public class TileButton extends JPanel
{
    private static final long serialVersionUID = 1L;
    private App app;
    private Location loc;
    private Unit unit;
    private char setupUnit;
    private char displayedUnit;
    private Direction displayedDirection;
    private int displayedHP;
    private JLabel label;
    private HashSet<Effect> displayedEffects;
    
    public TileButton(final App app, final int x, final int y) {
        this.app = app;
        this.loc = new Location(x, y);
        this.unit = null;
        this.displayedUnit = ' ';
        this.displayedDirection = null;
        this.displayedHP = -1;
        this.displayedEffects = new HashSet<Effect>();
        this.setupUnit = ' ';
        this.label = new JLabel();
        this.setLayout(new GridLayout(1, 1));
        this.add(this.label);
        this.setFocusable(false);
        this.addKeyListener(app.getKeyListener());
        this.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
    }
    
    public Location getLoc() {
        return this.loc;
    }
    
    public void Update() {
        if (this.app.getCurrentSelection() != null && this.app.getCurrentSelection().location().equals(this.loc)) {
            this.setBorder(BorderFactory.createLoweredBevelBorder());
        }
        else {
            this.setBorder(BorderFactory.createRaisedBevelBorder());
        }
        this.setBackground(new Color(238, 238, 238));
        switch (this.app.getOverallState()) {
            case 0: {
                if (Setup.isValid(this.loc)) {
                    final Setup setup = this.app.getEditor().getSouthSetup();
                    final Character c = setup.get(this.loc);
                    if (c != null) {
                        this.setupUnit = c;
                    }
                    else {
                        this.setupUnit = ' ';
                    }
                }
                else if (Setup.isValid(Setup.mirror(this.loc))) {
                    final Setup setup = this.app.getEditor().getNorthSetup();
                    final Character c = setup.get(Setup.mirror(this.loc));
                    if (c != null) {
                        this.setupUnit = c;
                    }
                    else {
                        this.setupUnit = ' ';
                    }
                }
                else {
                    this.setupUnit = ' ';
                }
                this.unit = null;
                break;
            }
            case 1: {
                this.unit = this.app.getGame().getBoard().unitAt(this.loc);
                this.setupUnit = ' ';
                break;
            }
            case 2: {
                this.unit = this.app.getGame().getBoard().unitAt(this.loc);
                this.setupUnit = ' ';
                break;
            }
            case 3: {
                return;
            }
        }
        if (this.unit != null) {
            boolean sameDirection = true;
            boolean sameHP = true;
            boolean sameEffects = true;
            if (this.unit instanceof BasicUnit) {
                final BasicUnit u = (BasicUnit)this.unit;
                if (u.mobile() && (this.displayedDirection == null != (u.direction() == null) || (this.displayedDirection != null && u.direction() != null && !u.direction().equals(this.displayedDirection)))) {
                    sameDirection = false;
                }
                if (u.hitPoints() != this.displayedHP) {
                    sameHP = false;
                }
                for (final Effect e : ((BasicUnit)this.unit).getEffects()) {
                    if (!this.displayedEffects.contains(e)) {
                        sameEffects = false;
                        break;
                    }
                }
                for (final Effect e : this.displayedEffects) {
                    if (!((BasicUnit)this.unit).getEffects().contains(e)) {
                        sameEffects = false;
                        break;
                    }
                }
            }
            if (this.displayedUnit != this.unit.toChar() || !sameDirection || !sameHP || !sameEffects) {
                if (this.unit instanceof BasicUnit) {
                    final BasicUnit u = (BasicUnit)this.unit;
                    this.displayedDirection = u.direction();
                    this.displayedHP = u.hitPoints();
                    this.displayedEffects.clear();
                    for (final Effect e : ((BasicUnit)this.unit).getEffects()) {
                        this.displayedEffects.add(e);
                    }
                    this.label.setIcon(this.app.getImageManager().getImageIcon(this.unit.toChar(), this.unit.getPlayer() == this.app.getGame().getPlayer1(), u.direction(), u.hitPoints() / u.baseStats().maxHP, this.displayedEffects));
                }
                else {
                    this.displayedDirection = null;
                    this.displayedHP = -1;
                    this.label.setIcon(this.app.getImageManager().getImageIcon(this.unit.toChar(), this.unit.getPlayer() == this.app.getGame().getPlayer1(), null));
                }
                this.displayedUnit = this.unit.toChar();
            }
        }
        else if (this.setupUnit != ' ') {
            if (this.displayedUnit != this.setupUnit) {
                this.label.setIcon(this.app.getImageManager().getImageIcon(this.setupUnit, Setup.isValid(this.loc), null));
                this.displayedUnit = this.setupUnit;
                this.displayedHP = -1;
                this.displayedEffects.clear();
            }
            this.displayedDirection = null;
        }
        else {
            this.label.setIcon(null);
            this.displayedUnit = ' ';
            this.displayedDirection = null;
            this.displayedHP = -1;
            this.displayedEffects.clear();
        }
    }
}
