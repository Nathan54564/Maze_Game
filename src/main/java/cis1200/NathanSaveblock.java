package cis1200;

import java.awt.Color;
import java.awt.Graphics;

public class NathanSaveblock extends NathanSprite {
    private int spawn;
    private boolean activated;

    public NathanSaveblock(int x, int y, int w, int h, int s) {
        super(x, y, w, h, Color.WHITE);
        this.spawn = s;
        this.activated = false;
    }

    public int getSpawn() {
        return spawn;
    }

    public void setSpawn(int spawn) {
        this.spawn = spawn;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean a) {
        this.activated = a;
    }

    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}