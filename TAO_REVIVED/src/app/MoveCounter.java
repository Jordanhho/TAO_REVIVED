package app;

import java.awt.Color;
import java.awt.event.KeyListener;
import javax.swing.border.Border;
import javax.swing.text.Highlighter;
import java.awt.Font;
import util.ImageManager;
import javax.swing.JTextField;

public class MoveCounter extends JTextField
{
    private static final long serialVersionUID = 1L;
    private App app;
    
    public MoveCounter(final App app) {
        this.app = app;
        this.setEditable(false);
        this.setBackground(ImageManager.BACKGROUND);
        this.setFont(new Font("Times New Roman", 1, 45));
        this.setHorizontalAlignment(0);
        this.setHighlighter(null);
        this.setBorder(null);
        this.addKeyListener(app.getKeyListener());
        this.Update();
    }
    
    public void Update() {
        if (this.app.getGame() == null || this.app.getGame().gameOver()) {
            this.setText(null);
            this.setForeground(ImageManager.BACKGROUND);
        }
        else {
            final int numTurn = this.app.getGame().halfturns() / 2 + 1;
            this.setText(new StringBuilder().append(numTurn).toString());
            if (this.app.getGame().getCurrentPlayer() == this.app.getGame().getPlayer1()) {
                final int rgb = Color.HSBtoRGB(0.0f, 1.0f, 1.0f);
                this.setForeground(new Color(rgb));
            }
            else {
                final int rgb = Color.HSBtoRGB(0.67f, 1.0f, 1.0f);
                this.setForeground(new Color(rgb));
            }
        }
    }
}
