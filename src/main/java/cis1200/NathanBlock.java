package cis1200;

import java.awt.Color;
import java.awt.Graphics;

public class NathanBlock extends NathanSprite {
    public NathanBlock(int x, int y, int width, int height, Color c) {
        super(x, y, width, height, c);
    }

    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}