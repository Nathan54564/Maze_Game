package cis1200;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;

public class NathanPlayer extends NathanSprite {
    private int dx;
    private int dy;
    private int speed = 2;
    private NathanDisplay display;

    public NathanPlayer(int x, int y, int width, int height, Color color, NathanDisplay display) {
        super(x, y, width, height, color);
        this.display = display;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int s) {
        this.speed = s;
    }

    public NathanDisplay getDisplay() {
        return display;
    }

    public void setDisplay(NathanDisplay display) {
        this.display = display;
    }

    public void draw(java.awt.Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    public void killed() {
        // Respawn at the latest activated saveblock
        List<NathanSaveblock> saves = null;
        if (display != null) {
            saves = display.getSaveblocks();
        }
        if (saves != null) {
            for (int i = 0; i < saves.size(); i++) {
                NathanSaveblock sb = saves.get(i);
                if (sb.isActivated() && i == 0) {
                    setX(10);
                    setY(320);
                    return;
                } else if (sb.isActivated() && i == 1) {
                    setX(320);
                    setY(630);
                    return;
                } else if (sb.isActivated() && i == 2) {
                    setX(630);
                    setY(320);
                    return;
                }
            }
        }
        // Default respawn if no saveblock activated
        setX(330);
        setY(10);
    }

    public boolean isLeftCollision(NathanSprite sprite) {
        boolean bool = (getX() == sprite.getX() + sprite.getWidth())
                && (getY() > sprite.getY() - getWidth())
                && (getY() < sprite.getY() + sprite.getHeight());
        if (sprite instanceof NathanBlock) {
            return bool;
        } else if (sprite instanceof NathanDeathblock && bool && sprite.isVisible()) {
            killed();
            if (display != null) {
                display.incrementDeaths();
            }
            return bool;
        } else if (sprite instanceof NathanDeathblock && !sprite.isVisible()) {
            return false;
        } else if (sprite instanceof NathanEnemy && bool) {
            killed();
            if (display != null) {
                display.incrementDeaths();
            }
            return bool;
        }
        return bool;
    }

    public boolean isRightCollision(NathanSprite sprite) {
        boolean bool = (getX() == sprite.getX() - getWidth())
                && (getY() > sprite.getY() - getWidth())
                && (getY() < sprite.getY() + sprite.getHeight());
        if (sprite instanceof NathanBlock) {
            return bool;
        } else if (sprite instanceof NathanDeathblock && bool && sprite.isVisible()) {
            killed();
            if (display != null) {
                display.incrementDeaths();
            }
            return bool;
        } else if (sprite instanceof NathanDeathblock && !sprite.isVisible()) {
            return false;
        } else if (sprite instanceof NathanEnemy && bool) {
            killed();
            if (display != null) {
                display.incrementDeaths();
            }
            return bool;
        }
        return bool;
    }

    public boolean isTopCollision(NathanSprite sprite) {
        boolean bool = (getY() == sprite.getY() + sprite.getHeight())
                && (getX() + getWidth() > sprite.getX())
                && (getX() < sprite.getX() + sprite.getWidth());
        if (sprite instanceof NathanBlock) {
            return bool;
        } else if (sprite instanceof NathanDeathblock && bool && sprite.isVisible()) {
            killed();
            if (display != null) {
                display.incrementDeaths();
            }
            return bool;
        } else if (sprite instanceof NathanDeathblock && !sprite.isVisible()) {
            return false;
        }
        return bool;
    }

    public boolean isBottomCollision(NathanSprite sprite) {
        boolean bool = (getY() == sprite.getY() - getHeight())
                && (getX() + getWidth() > sprite.getX())
                && (getX() < sprite.getX() + sprite.getWidth());
        if (sprite instanceof NathanBlock) {
            return bool;
        } else if (sprite instanceof NathanDeathblock && bool && sprite.isVisible()) {
            killed();
            if (display != null) {
                display.incrementDeaths();
            }
            return bool;
        } else if (sprite instanceof NathanDeathblock && !sprite.isVisible()) {
            return false;
        }
        return bool;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        } else if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        } else if (key == KeyEvent.VK_UP) {
            dy = 0;
        } else if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}