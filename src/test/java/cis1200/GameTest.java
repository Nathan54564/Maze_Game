package cis1200;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;

public class GameTest {
    // Fields remain the same
    private NathanPlayer player;
    private NathanBlock block;
    private NathanEnemy enemy;
    private NathanDeathblock deathBlock;
    private NathanSaveblock saveBlock;
    private NathanWinblock winBlock;
    private NathanDisplay display;
    private NathanMazeGenerator mazeGenerator;
    private String[][] maze;

    /**
     * A helper method to initialize game objects.
     * Since we can't use @Before or @BeforeEach, we'll call this method at the start of each test.
     */
    private void initGameObjects() {
        player = new NathanPlayer(100, 100, 10, 10, Color.YELLOW, null);
        block = new NathanBlock(200, 200, 10, 10, Color.BLACK);
        enemy = new NathanEnemy(300, 300, 10, 10, true);
        deathBlock = new NathanDeathblock(400, 400, 10, 10, true);
        saveBlock = new NathanSaveblock(500, 500, 10, 10, 1);
        winBlock = new NathanWinblock(600, 600, 10, 10, Color.WHITE);
        mazeGenerator = new NathanMazeGenerator();
        maze = new String[33][33];
    }

    // Player Movement Tests
    @Test
    public void testPlayerInitialPosition() {
        initGameObjects();
        assertEquals(100, player.getX());
        assertEquals(100, player.getY());
    }

    @Test
    public void testPlayerMovement() {
        initGameObjects();
        int initialX = player.getX();
        player.move(5, 0);
        assertEquals(initialX + 5, player.getX());
    }

    // Enemy Tests
    @Test
    public void testEnemyInitialPosition() {
        initGameObjects();
        assertEquals(300, enemy.getX());
        assertEquals(300, enemy.getY());
    }

    @Test
    public void testEnemyMovementDirection() {
        initGameObjects();
        assertTrue(enemy.getHorizontal());
        int initialSpeed = enemy.getSpeed();
        assertTrue(initialSpeed != 0);
    }

    // Block Tests
    @Test
    public void testBlockDimensions() {
        initGameObjects();
        assertEquals(10, block.getWidth());
        assertEquals(10, block.getHeight());
    }

    @Test
    public void testBlockVisibility() {
        initGameObjects();
        assertTrue(block.isVisible());
    }

    // Death Block Tests
    @Test
    public void testDeathBlockVisibility() {
        initGameObjects();
        assertTrue(deathBlock.isVisible());
    }

    @Test
    public void testDeathBlockTrickState() {
        initGameObjects();
        assertTrue(deathBlock.getTrick());
    }

    // Save Block Tests
    @Test
    public void testSaveBlockPosition() {
        initGameObjects();
        assertEquals(500, saveBlock.getX());
        assertEquals(500, saveBlock.getY());
    }

    // Win Block Tests
    @Test
    public void testWinBlockDimensions() {
        initGameObjects();
        assertEquals(10, winBlock.getWidth());
        assertEquals(10, winBlock.getHeight());
    }

    // Collision Tests
    @Test
    public void testPlayerBlockCollision() {
        initGameObjects();
        player.setX(190);
        player.setY(200);
        boolean collision = player.isRightCollision(block);
        assertTrue(collision);
    }

    // Maze Generation Tests
    @Test
    public void testMazeInitialization() {
        initGameObjects();
        mazeGenerator.generateMaze(maze);
        assertNotNull(maze[0][0]);
    }

    @Test
    public void testMazeBoundaries() {
        initGameObjects();
        mazeGenerator.generateMaze(maze);
        assertEquals(33, maze.length);
        assertEquals(33, maze[0].length);
    }

    // Edge Cases
    @Test
    public void testZeroDimensionBlock() {
        initGameObjects();
        NathanBlock zeroBlock = new NathanBlock(0, 0, 0, 0, Color.BLACK);
        assertEquals(0, zeroBlock.getWidth());
        assertEquals(0, zeroBlock.getHeight());
    }

    @Test
    public void testLargeDimensionBlock() {
        initGameObjects();
        NathanBlock largeBlock = new NathanBlock(0, 0, 1000, 1000, Color.BLACK);
        assertEquals(1000, largeBlock.getWidth());
        assertEquals(1000, largeBlock.getHeight());
    }

    @Test
    public void testMazeEntrancesAndExits() {
        initGameObjects();
        mazeGenerator.generateMaze(maze);

        // Check entrance (assuming entrance is at [1][0])
        assertEquals(".", maze[1][0]);

        // Check exit (assuming exit is at [maze.length - 2][maze[0].length - 1])
        assertEquals(".", maze[maze.length - 2][maze[0].length - 1]);
    }
}