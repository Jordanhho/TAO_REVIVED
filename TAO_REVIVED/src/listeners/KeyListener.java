package listeners;

import app.RotationDialog;
import javax.swing.JButton;
import app.Editor;
import java.awt.event.KeyEvent;
import app.App;
import java.util.HashSet;

public class KeyListener implements java.awt.event.KeyListener
{
    private HashSet<Integer> heldDown;
    private App app;
    
    public KeyListener(final App app) {
        this.app = app;
        this.heldDown = new HashSet<Integer>();
    }
    
    public void keyPressed(final KeyEvent event) {
        final int key = event.getKeyCode();
        if (this.heldDown.contains(key)) {
            return;
        }
        this.heldDown.add(key);
        if (this.app.getOverallState() == 0 && event.getModifiersEx() != 128) {
            final Editor e = this.app.getEditor();
            JButton b = null;
            switch (key) {
                case 75: {
                    b = e.getKnight();
                    break;
                }
                case 83: {
                    b = e.getScout();
                    break;
                }
                case 65: {
                    b = e.getAssassin();
                    break;
                }
                case 67: {
                    b = e.getCleric();
                    break;
                }
                case 89: {
                    b = e.getPyromancer();
                    break;
                }
                case 68: {
                    b = e.getDragonSpeakerMage();
                    break;
                }
                case 87: {
                    b = e.getDarkMagicWitch();
                    break;
                }
                case 69: {
                    b = e.getEnchantress();
                    break;
                }
                case 76: {
                    b = e.getLightningWard();
                    break;
                }
                case 66: {
                    b = e.getBarrierWard();
                    break;
                }
                case 77: {
                    b = e.getMudGolem();
                    break;
                }
                case 71: {
                    b = e.getGolemAmbusher();
                    break;
                }
                case 70: {
                    b = e.getFrostGolem();
                    break;
                }
                case 79: {
                    b = e.getStoneGolem();
                    break;
                }
                case 84: {
                    b = e.getDragonTyrant();
                    break;
                }
                case 90: {
                    b = e.getBerserker();
                    break;
                }
                case 82: {
                    b = e.getBeastRider();
                    break;
                }
                case 80: {
                    b = e.getPoisonWisp();
                    break;
                }
                case 85: {
                    b = e.getFurgon();
                    break;
                }
                case 32: {
                    b = e.getEmpty();
                    break;
                }
            }
            if (b != null) {
                b.doClick();
            }
        }
        if (this.app.getOverallState() == 1) {
            if (this.app.getCurrentAction() == 6) {
                final RotationDialog r = this.app.getRotationDialog();
                JButton b = null;
                switch (key) {
                    case 38: {
                        b = r.getNorth();
                        break;
                    }
                    case 37: {
                        b = r.getWest();
                        break;
                    }
                    case 39: {
                        b = r.getEast();
                        break;
                    }
                    case 40: {
                        b = r.getSouth();
                        break;
                    }
                }
                if (b != null) {
                    b.doClick();
                    return;
                }
            }
            JButton b2 = null;
            switch (key) {
                case 49: {
                    b2 = this.app.getMove();
                    break;
                }
                case 50: {
                    b2 = this.app.getAttack();
                    break;
                }
                case 51: {
                    b2 = this.app.getTurn();
                    break;
                }
                case 52: {
                    b2 = this.app.getEndTurn();
                    break;
                }
                case 90: {
                    if (event.getModifiers() == 2) {
                        b2 = this.app.getUndobutton();
                        break;
                    }
                    break;
                }
            }
            if (b2 != null) {
                b2.doClick();
            }
        }
        if (this.app.getOverallState() == 1 || this.app.getOverallState() == 2) {
            JButton b2 = null;
            Label_0830: {
                switch (key) {
                    case 37: {
                        switch (event.getModifiers()) {
                            case 2: {
                                this.app.toBeginning();
                                break Label_0830;
                            }
                            case 8: {
                                this.app.back(10);
                                break Label_0830;
                            }
                            default: {
                                b2 = this.app.getBack();
                                break Label_0830;
                            }
                        }
                    }
                    case 39: {
                        switch (event.getModifiers()) {
                            case 2: {
                                this.app.toEnd();
                                break Label_0830;
                            }
                            case 8: {
                                this.app.forward(10);
                                break Label_0830;
                            }
                            default: {
                                b2 = this.app.getForward();
                                break Label_0830;
                            }
                        }
                    }
                }
            }
            if (b2 != null) {
                b2.doClick();
            }
        }
        if (this.app.getOverallState() == 2) {
            JButton b2 = null;
            switch (key) {
                case 38: {
                    b2 = this.app.getUp();
                    break;
                }
                case 40: {
                    b2 = this.app.getDown();
                    break;
                }
            }
            if (b2 != null) {
                b2.doClick();
            }
        }
    }
    
    public void keyTyped(final KeyEvent event) {
    }
    
    public void keyReleased(final KeyEvent event) {
        final int key = event.getKeyCode();
        if (this.heldDown.contains(key)) {
            this.heldDown.remove(key);
        }
    }
}
