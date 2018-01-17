// 
// Decompiled by Procyon v0.5.30
// 

package listeners;

import java.awt.event.AdjustmentEvent;
import app.App;
import java.awt.event.AdjustmentListener;

public class SliderListener implements AdjustmentListener
{
    private App app;
    
    public SliderListener(final App app) {
        this.app = app;
    }
    
    public void adjustmentValueChanged(final AdjustmentEvent e) {
        final int cur = this.app.getJScrollBar().getValue();
        if (cur == this.app.getGame().currentState()) {
            return;
        }
        int[] path = null;
        int index = -1;
        while (cur != this.app.getGame().currentState()) {
            if (cur < this.app.getGame().currentState()) {
                this.app.setOverallState(3);
                final int depth = this.app.getGame().getDepth();
                final int state = this.app.getGame().currentState();
                path = this.app.getGame().toBeginning();
                for (index = 0; index < depth - state; ++index) {
                    this.app.getGame().next(path[index]);
                }
            }
            if (cur > this.app.getGame().currentState()) {
                this.app.setOverallState(3);
                if (path != null && index < path.length) {
                    this.app.getGame().next(path[index++]);
                }
                else {
                    this.app.getGame().next(0);
                }
                this.app.setVariationIndex(0);
                if (this.app.getGame().atEnd()) {
                    this.app.setOverallState(1);
                    this.app.UpdateGui();
                    return;
                }
                continue;
            }
        }
        this.app.setOverallState(2);
        this.app.UpdateGui();
    }
}
