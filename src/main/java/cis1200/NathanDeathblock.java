package cis1200;

import java.awt.Color;
import java.awt.Graphics;

public class NathanDeathblock extends NathanSprite {
    private boolean trick;

    public NathanDeathblock(int x, int y, int width, int height, boolean trick) {
        super(x, y, width, height, Color.RED);
        this.trick = trick;
    }

    public void draw(Graphics g) {
        if (isVisible()) {
            g.setColor(getColor());
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    public boolean getTrick() {
        return trick;
    }

    public void setTrick(boolean trick) {
        this.trick = trick;
    }
}