package cis1200;

import java.awt.Color;
import java.awt.Graphics;

public class NathanEnemy extends NathanSprite {
    private int speed;
    private boolean horizontal;

    public NathanEnemy(int x, int y, int w, int h, boolean orientation) {
        super(x, y, w, h, new Color(0, 128, 0));
        this.horizontal = orientation;
        this.speed = 1;
    }

    public NathanEnemy(int x, int y, int w, int h, boolean orientation, int speed) {
        super(x, y, w, h, new Color(0, 128, 0));
        this.horizontal = orientation;
        this.speed = speed;
    }

    public void move(double dx, double dy) {
        setX((int) (getX() + dx));
        setY((int) (getY() + dy));
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public void isLeftCollision(NathanSprite sprite) {
        boolean bool = (getY() < sprite.getY() + sprite.getHeight())
                && (getY() + getHeight() > sprite.getY())
                && (getX() + getWidth() > sprite.getX())
                && (getX() - 1 < sprite.getX() + sprite.getWidth());
        if (bool) {
            speed = Math.abs(speed);
        }
    }

    public void isRightCollision(NathanSprite sprite) {
        boolean bool = (getY() < sprite.getY() + sprite.getHeight())
                && (getY() + getHeight() > sprite.getY())
                && (getX() + getWidth() + 1 > sprite.getX())
                && (getX() < sprite.getX() + sprite.getWidth());
        if (bool) {
            speed = -Math.abs(speed);
        }
    }

    public void isTopCollision(NathanSprite sprite) {
        boolean bool = ((getY() - 1) < (sprite.getY() + sprite.getHeight()))
                && ((getY() + getHeight()) > sprite.getY())
                && ((getX() + getWidth()) > sprite.getX())
                && (getX() < (sprite.getX() + sprite.getWidth()));
        if (bool) {
            speed = Math.abs(speed);
        }
    }

    public void isBottomCollision(NathanSprite sprite) {
        boolean bool = (getY() < sprite.getY() + sprite.getHeight())
                && (getY() + getHeight() + 1 > sprite.getY())
                && (getX() + getWidth() > sprite.getX())
                && (getX() < sprite.getX() + sprite.getWidth());
        if (bool) {
            speed = -Math.abs(speed);
        }
    }

    public void draw(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}