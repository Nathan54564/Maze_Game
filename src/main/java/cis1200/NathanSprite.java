package cis1200;

import java.awt.Color;
import java.awt.Rectangle;

public class NathanSprite {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private boolean visible;

    public NathanSprite(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.visible = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters and Setters for x
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    // Getters and Setters for y
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Getters and Setters for width
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    // Getters and Setters for height
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    // Getters and Setters for color
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // Getters and Setters for visible
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}