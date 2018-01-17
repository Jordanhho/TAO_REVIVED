// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import app.App;
import java.awt.event.ActionListener;

public class ShortcutButtonListener implements ActionListener
{
    private static final String MESSAGE = "                           Setup Shortcuts\n K     Knight                         S   Scout\n A     Assassin                   C   Cleric\n Y     pYro                            D   Dragon speaker mage\n W   dark magic Witch    E   Enchantress\n L     Lightning ward         B   Barrier ward\n M    Mud golem                G   Golem ambusher\n F     Frost golem               O   stOne golem\n T     dragon Tyrant           Z   beserker\n R     beast Rider               P   Poison wisp\n U     fUrgon                             eraser\n                         Input Shortcuts\n 1   move                            up          face up\n 2   attack                          down    face down\n 3   rotate                           left        face left\n 4   end turn                       right     face right\n ctrl-z    Undo\n                        Playback Shortcuts\n right                             next move\n left                                previous move\n down                           next variation\n up                                 previous variation\n ctrl (+ right or left)   to end or beginning\n alt (+ right or left)     forward or backward 10 moves";
    private App app;
    
    public ShortcutButtonListener(final App app) {
        this.app = app;
    }
    
    public void actionPerformed(final ActionEvent e) {
        JOptionPane.showMessageDialog(this.app, "                           Setup Shortcuts\n K     Knight                         S   Scout\n A     Assassin                   C   Cleric\n Y     pYro                            D   Dragon speaker mage\n W   dark magic Witch    E   Enchantress\n L     Lightning ward         B   Barrier ward\n M    Mud golem                G   Golem ambusher\n F     Frost golem               O   stOne golem\n T     dragon Tyrant           Z   beserker\n R     beast Rider               P   Poison wisp\n U     fUrgon                             eraser\n                         Input Shortcuts\n 1   move                            up          face up\n 2   attack                          down    face down\n 3   rotate                           left        face left\n 4   end turn                       right     face right\n ctrl-z    Undo\n                        Playback Shortcuts\n right                             next move\n left                                previous move\n down                           next variation\n up                                 previous variation\n ctrl (+ right or left)   to end or beginning\n alt (+ right or left)     forward or backward 10 moves", "Shortcuts", 1);
    }
}
