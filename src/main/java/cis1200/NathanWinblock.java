package cis1200;

import java.awt.Color;
import java.awt.Graphics;

public class NathanWinblock extends NathanSprite {
    public NathanWinblock(int x, int y, int w, int h, Color c) {
        super(x, y, w, h, c);
    }

    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}