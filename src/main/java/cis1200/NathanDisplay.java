package cis1200;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NathanDisplay extends JPanel implements ActionListener {
    private NathanPlayer player;
    private NathanMazeGenerator mazeGenerator;
    private String[][] maze;
    private List<NathanBlock> blocks;
    private List<NathanDeathblock> deathblocks;
    private List<NathanEnemy> enemies;
    private static List<NathanSaveblock> saveblocks;
    private List<NathanWinblock> winblocks;
    private Timer timer;
    private static final int DELAY = 30;
    private boolean finish = false;
    private boolean check = true;

    // static variables
    public static final int WIDTH = 800;
    public static final int HEIGHT = 650;
    public static final Color BG_COLOR = new Color(135, 206, 235);
    private static final Color PLAYER_COLOR = new Color(255, 255, 0);
    private static final Color BLOCK_COLOR = new Color(57, 61, 71);

    private int deathCount = 0;
    private long startTime;
    private DecimalFormat df = new DecimalFormat("#.##");

    public NathanDisplay() {
        startTime = System.currentTimeMillis();
        setFocusable(true);
        setDoubleBuffered(true);
        requestFocusInWindow();
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new TAdapter());
        setSize(WIDTH, HEIGHT);
        setBackground(BG_COLOR);
        setFocusable(true);

        player = new NathanPlayer(330, 10, 10, 10, PLAYER_COLOR, this);
        blocks = new ArrayList<>();
        deathblocks = new ArrayList<>();
        enemies = new ArrayList<>();
        saveblocks = new ArrayList<>();
        winblocks = new ArrayList<>();
        maze = new String[33][33];
        mazeGenerator = new NathanMazeGenerator();
        mazeGenerator.generateMaze(maze);

        // add maze as blocks
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].equals("#")) {
                    NathanBlock block = new NathanBlock(320 + 10 * i, 10 * j, 10, 10, BLOCK_COLOR);
                    blocks.add(block);
                } else {
                    double random = Math.random();
                    if (random < 0.04) {
                        NathanDeathblock deathblock = new NathanDeathblock(
                                320 + 10 * i, 10 * j, 10, 10, true);
                        deathblocks.add(deathblock);
                    }
                }
            }
        }

        // Border walls
        for (int i = 0; i < 65; i++) {
            NathanBlock object1 = new NathanBlock(10 * i, 0, 10, 10, BLOCK_COLOR);
            blocks.add(object1);
        }
        for (int i = 0; i < 65; i++) {
            NathanBlock object1 = new NathanBlock(10 * i, HEIGHT - 10, 10, 10, BLOCK_COLOR);
            blocks.add(object1);
        }
        for (int i = 0; i < 63; i++) {
            NathanBlock object1 = new NathanBlock(0, 10 + 10 * i, 10, 10, BLOCK_COLOR);
            blocks.add(object1);
        }
        for (int i = 0; i < 63; i++) {
            NathanBlock object1 = new NathanBlock(WIDTH - 160, 10 + 10 * i, 10, 10, BLOCK_COLOR);
            blocks.add(object1);
        }
        for (int i = 0; i < 65; i++) {
            if (i != 63) {
                NathanBlock object1 = new NathanBlock(320, 10 * i, 10, 10, BLOCK_COLOR);
                blocks.add(object1);
            }
        }
        for (int i = 0; i < 65; i++) {
            if (!(i == 1 || i == 63 || i == 31)) {
                NathanBlock object1 = new NathanBlock(10 * i, 320, 10, 10, BLOCK_COLOR);
                blocks.add(object1);
            }
        }

        // Add saveblocks
        NathanSaveblock respawn3 = new NathanSaveblock(10, 320, 10, 10, 3);
        saveblocks.add(respawn3);
        NathanSaveblock respawn2 = new NathanSaveblock(320, 630, 10, 10, 2);
        saveblocks.add(respawn2);
        NathanSaveblock respawn1 = new NathanSaveblock(630, 320, 10, 10, 1);
        saveblocks.add(respawn1);

        // Add winblocks
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i + j) % 2 == 0) {
                    NathanWinblock winblock = new NathanWinblock(310 + 2 * i, 10 + 2 * j, 2, 2,
                            Color.WHITE);
                    winblocks.add(winblock);
                } else {
                    NathanWinblock winblock = new NathanWinblock(310 + 2 * i, 10 + 2 * j, 2, 2,
                            Color.BLACK);
                    winblocks.add(winblock);
                }
            }
        }

        // Add enemies in the 2nd quadrant
        for (int i = 0; i < 10; i++) {
            NathanEnemy monster = new NathanEnemy(330, 340 + 30 * i, 10, 10, true);
            enemies.add(monster);
        }
        for (int i = 0; i < 10; i++) {
            NathanEnemy monster = new NathanEnemy(WIDTH - 20, 330 + 30 * i, 10, 10, true);
            enemies.add(monster);
        }
        for (int i = 0; i < 10; i++) {
            NathanEnemy monster = new NathanEnemy(330 + 30 * i, 330, 10, 10, false);
            enemies.add(monster);
        }
        for (int i = 0; i < 10; i++) {
            NathanEnemy monster = new NathanEnemy(340 + 30 * i, 630, 10, 10, false);
            enemies.add(monster);
        }

        // Add 2nd game deathblocks
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 31; j++) {
                double random = Math.random();
                if (random < 0.03) {
                    NathanDeathblock deathblock = new NathanDeathblock(
                            330 + 10 * i, 330 + 10 * j, 10, 10, false);
                    deathblocks.add(deathblock);
                }
            }
        }

        // Add lava river in 3rd quadrant
        NathanDeathblock river1 = new NathanDeathblock(150, 330, 30, 140, false);
        deathblocks.add(river1);
        NathanDeathblock river3 = new NathanDeathblock(150, 500, 30, 140, false);
        deathblocks.add(river3);

        // Add barriers to lava river
        NathanBlock riverside1 = new NathanBlock(180, 330, 10, 140, BLOCK_COLOR);
        NathanBlock riverside2 = new NathanBlock(180, 500, 10, 140, BLOCK_COLOR);
        NathanBlock riverside3 = new NathanBlock(140, 330, 10, 140, BLOCK_COLOR);
        NathanBlock riverside4 = new NathanBlock(140, 500, 10, 140, BLOCK_COLOR);
        blocks.add(riverside1);
        blocks.add(riverside2);
        blocks.add(riverside3);
        blocks.add(riverside4);

        // Other barriers in 3rd quadrant
        NathanBlock passageRight1 = new NathanBlock(220, 330, 10, 230, BLOCK_COLOR);
        NathanBlock passageRight2 = new NathanBlock(220, 590, 10, 50, BLOCK_COLOR);
        blocks.add(passageRight1);
        blocks.add(passageRight2);

        NathanBlock passageRightRight1 = new NathanBlock(260, 330, 10, 70, BLOCK_COLOR);
        NathanBlock passageRightRight2 = new NathanBlock(260, 430, 10, 210, BLOCK_COLOR);
        blocks.add(passageRightRight1);
        blocks.add(passageRightRight2);

        NathanBlock passageRightRightRight1 = new NathanBlock(300, 330, 10, 100, BLOCK_COLOR);
        NathanBlock passageRightRightRight2 = new NathanBlock(300, 460, 10, 180, BLOCK_COLOR);
        blocks.add(passageRightRightRight1);
        blocks.add(passageRightRightRight2);

        // Add enemies to 3rd quadrant
        NathanEnemy rightVertical1 = new NathanEnemy(190, 330, 10, 10, false);
        NathanEnemy rightVertical2 = new NathanEnemy(210, 630, 10, 10, false);
        enemies.add(rightVertical1);
        enemies.add(rightVertical2);

        NathanEnemy rightHorizontal1 = new NathanEnemy(190, 560, 10, 10, true);
        NathanEnemy rightHorizontal2 = new NathanEnemy(250, 580, 10, 10, true);
        enemies.add(rightHorizontal1);
        enemies.add(rightHorizontal2);

        NathanEnemy rightVertical3 = new NathanEnemy(230, 480, 10, 10, false);
        NathanEnemy rightVertical4 = new NathanEnemy(250, 480, 10, 10, false);
        enemies.add(rightVertical3);
        enemies.add(rightVertical4);

        NathanEnemy rightHorizontal3 = new NathanEnemy(230, 400, 10, 10, true);
        NathanEnemy rightHorizontal4 = new NathanEnemy(290, 420, 10, 10, true);
        enemies.add(rightHorizontal3);
        enemies.add(rightHorizontal4);

        NathanEnemy rightVertical5 = new NathanEnemy(270, 630, 10, 10, false);
        NathanEnemy rightVertical6 = new NathanEnemy(290, 330, 10, 10, false);
        enemies.add(rightVertical5);
        enemies.add(rightVertical6);

        NathanEnemy rightHorizontal5 = new NathanEnemy(270, 440, 10, 10, true);
        enemies.add(rightHorizontal5);

        // final lava block in front of exit
        NathanDeathblock blocker = new NathanDeathblock(10, 330, 10, 10, false);
        deathblocks.add(blocker);

        // Blinking lava blocks in quad 3
        for (int i = 0; i < 6; i++) {
            NathanDeathblock blink = new NathanDeathblock(30 + 20 * i, 330, 10, 10, true);
            deathblocks.add(blink);
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 16; j++) {
                NathanDeathblock blink = new NathanDeathblock(10 + 20 * i, 330 + 20 * j,
                        10, 10, true);
                deathblocks.add(blink);
            }
        }

        // 4th quad barriers
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 28; j++) {
                if (i % 2 == 0) {
                    NathanBlock block = new NathanBlock(10 + 10 * j, 256 - 64 * i,
                            10, 10, BLOCK_COLOR);
                    blocks.add(block);
                } else {
                    NathanBlock block = new NathanBlock(40 + 10 * j, 256 - 64 * i,
                            10, 10, BLOCK_COLOR);
                    blocks.add(block);
                }
            }
        }

        // 4th quad enemies
        for (int i = 0; i < 5; i++) {
            NathanEnemy enemy = new NathanEnemy(280 - 60 * i, 266 + 5 * i, 10, 10, false);
            enemies.add(enemy);
        }
        for (int i = 0; i < 9; i++) {
            NathanEnemy enemy = new NathanEnemy(280 - 30 * i, 202 + 5 * i, 10, 10, false);
            enemies.add(enemy);
        }
        for (int i = -1; i < 2; i++) {
            NathanEnemy enemy = new NathanEnemy(160 + 149 * i, 138 + 22 * (i + 1), 10, 10, true);
            enemies.add(enemy);
        }
        for (int i = -2; i < 3; i++) {
            NathanEnemy enemy = new NathanEnemy(160 + 74 * i, 74 + 11 * (i + 2), 10, 10, true);
            enemies.add(enemy);
        }
        NathanEnemy lastFloor = new NathanEnemy(160, 32, 10, 10, true);
        enemies.add(lastFloor);

        // 4th quad deathblocks
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                if (i % 2 == 0) {
                    NathanDeathblock lava = new NathanDeathblock(40 + 30 * i, 10 + 10 * j,
                            10, 10, false);
                    deathblocks.add(lava);
                } else {
                    NathanDeathblock lava = new NathanDeathblock(40 + 30 * i, 24 + 10 * j,
                            10, 10, false);
                    deathblocks.add(lava);
                }
            }
        }

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void incrementDeaths() {
        deathCount++;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    public void ensureFocus() {
        if (!hasFocus()) {
            requestFocusInWindow();
        }
    }

    private void drawStatusPanel(Graphics g) {
        g.setColor(new Color(200, 200, 200));
        g.fillRect(650, 0, 100, HEIGHT);

        g.setColor(Color.BLACK);
        g.drawLine(650, 0, 650, HEIGHT);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        long currentTime = System.currentTimeMillis();
        double elapsedSeconds = (currentTime - startTime) / 1000.0;
        g.drawString("Stats", 660, 30);
        g.drawString("Deaths: " + deathCount, 660, 60);
        g.drawString("Time: " + df.format(elapsedSeconds) + "s", 660, 90);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw and activate saveblocks if player contacts them
        for (NathanSaveblock saveblock : saveblocks) {
            saveblock.draw(g);
            boolean bool = (player.getY() < saveblock.getY() + saveblock.getHeight())
                    && (player.getY() + player.getHeight() > saveblock.getY())
                    && (player.getX() + player.getWidth() > saveblock.getX())
                    && (player.getX() < saveblock.getX() + saveblock.getWidth());
            if (bool) {
                saveblock.setActivated(true);
            }
        }

        drawStatusPanel(g);
        player.draw(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect(310, 320, 10, 10);
        g2d.setColor(BLOCK_COLOR);
        g2d.fillRect(310, 320, 10, 11);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        Toolkit.getDefaultToolkit().sync();

        for (NathanBlock block : blocks) {
            block.draw(g);
        }

        long elapsed = System.nanoTime() - startTime;
        long interval = 3000000000L;

        for (NathanDeathblock deathblock : deathblocks) {
            if (!deathblock.getTrick() && deathblock.isVisible()) {
                deathblock.draw(g);
                boolean bool = (player.getY() < deathblock.getY() + deathblock.getHeight())
                        && (player.getY() + player.getHeight() > deathblock.getY())
                        && (player.getX() + player.getWidth() > deathblock.getX())
                        && (player.getX() < deathblock.getX() + deathblock.getWidth());
                if (bool) {
                    player.killed();
                    incrementDeaths();
                }
            } else if (deathblock.getTrick()
                    && elapsed % interval >= 0
                    && elapsed % interval < interval / 3) {
                deathblock.setVisible(true);
                deathblock.draw(g);
                boolean bool = (player.getY() < deathblock.getY() + deathblock.getHeight())
                        && (player.getY() + player.getHeight() > deathblock.getY())
                        && (player.getX() + player.getWidth() > deathblock.getX())
                        && (player.getX() < deathblock.getX() + deathblock.getWidth());
                if (bool) {
                    player.killed();
                    incrementDeaths();
                }
            } else if (deathblock.getTrick()
                    && elapsed % interval >= interval / 3
                    && elapsed % interval < interval - 1) {
                deathblock.setVisible(false);
                deathblock.draw(g);
            } else {
                deathblock.draw(g);
            }
        }

        for (NathanEnemy enemy : enemies) {
            enemy.draw(g);
            boolean bool = (player.getY() < enemy.getY() + enemy.getHeight())
                    && (player.getY() + player.getHeight() > enemy.getY())
                    && (player.getX() + player.getWidth() > enemy.getX())
                    && (player.getX() < enemy.getX() + enemy.getWidth());
            if (bool) {
                player.killed();
                incrementDeaths();
            }

            if (enemy.getHorizontal()) {
                enemy.move(enemy.getSpeed(), 0);
                for (NathanBlock block : blocks) {
                    if (enemy.getSpeed() < 0) {
                        enemy.isLeftCollision(block);
                    } else {
                        enemy.isRightCollision(block);
                    }
                }
            } else {
                enemy.move(0, enemy.getSpeed());
                for (NathanBlock block : blocks) {
                    if (enemy.getSpeed() < 0) {
                        enemy.isTopCollision(block);
                    } else {
                        enemy.isBottomCollision(block);
                    }
                }
            }
        }

        for (NathanWinblock winblock : winblocks) {
            winblock.draw(g);
            if (check) {
                finish = (player.getY() < winblock.getY() + winblock.getHeight())
                        && (player.getY() + player.getHeight() > winblock.getY())
                        && (player.getX() + player.getWidth() > winblock.getX())
                        && (player.getX() < winblock.getX() + winblock.getWidth());
            }
            if (finish && check) {
                check = false;
                long currentTimenow = System.currentTimeMillis();
                double elapsedSecondss = (currentTimenow - startTime) / 1000.0;
                new End(deathCount, elapsedSecondss);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public List<NathanBlock> getBlocks() {
        return blocks;
    }

    public List<NathanSaveblock> getSaveblocks() {
        return saveblocks;
    }

    private class TAdapter extends KeyAdapter {
        private static final int LEFT_COLLISION = 1;
        private static final int RIGHT_COLLISION = 2;
        private static final int TOP_COLLISION = 3;
        private static final int BOTTOM_COLLISION = 4;

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    if (checkWallCollision(player, LEFT_COLLISION)) {
                        return;
                    }
                    player.move(-player.getSpeed(), 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (checkWallCollision(player, RIGHT_COLLISION)) {
                        return;
                    }
                    player.move(player.getSpeed(), 0);
                    break;
                case KeyEvent.VK_UP:
                    if (checkWallCollision(player, TOP_COLLISION)) {
                        return;
                    }
                    player.move(0, -player.getSpeed());
                    break;
                case KeyEvent.VK_DOWN:
                    if (checkWallCollision(player, BOTTOM_COLLISION)) {
                        return;
                    }
                    player.move(0, player.getSpeed());
                    break;
                default:
                    break;
            }
        }

        private boolean checkWallCollision(NathanSprite sprite, int type) {
            switch (type) {
                case LEFT_COLLISION:
                    for (NathanBlock block : blocks) {
                        if (player.isLeftCollision(block)) {
                            return true;
                        }
                    }
                    for (NathanDeathblock deathblock : deathblocks) {
                        if (player.isLeftCollision(deathblock)) {
                            return true;
                        }
                    }
                    return false;
                case RIGHT_COLLISION:
                    for (NathanBlock block : blocks) {
                        if (player.isRightCollision(block)) {
                            return true;
                        }
                    }
                    for (NathanDeathblock deathblock : deathblocks) {
                        if (player.isRightCollision(deathblock)) {
                            return true;
                        }
                    }
                    return false;
                case TOP_COLLISION:
                    for (NathanBlock block : blocks) {
                        if (player.isTopCollision(block)) {
                            return true;
                        }
                    }
                    for (NathanDeathblock deathblock : deathblocks) {
                        if (player.isTopCollision(deathblock)) {
                            return true;
                        }
                    }
                    return false;
                case BOTTOM_COLLISION:
                    for (NathanBlock block : blocks) {
                        if (player.isBottomCollision(block)) {
                            return true;
                        }
                    }
                    for (NathanDeathblock deathblock : deathblocks) {
                        if (player.isBottomCollision(deathblock)) {
                            return true;
                        }
                    }
                    return false;
                default:
                    return false;
            }
        }
    }
}