package cis1200;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private NathanDisplay level;
    private NathanInstructionsScreen instructionsScreen;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public static final String INSTRUCTIONS_SCREEN = "InstructionsScreen";
    public static final String GAME_SCREEN = "GameScreen";

    public Game() {
        setSize(NathanDisplay.WIDTH, NathanDisplay.HEIGHT + 20);
        setTitle("The Awesome Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        instructionsScreen = new NathanInstructionsScreen(cardLayout, mainPanel);
        level = new NathanDisplay();

        mainPanel.add(instructionsScreen, INSTRUCTIONS_SCREEN);
        mainPanel.add(level, GAME_SCREEN);

        add(mainPanel);

        cardLayout.show(mainPanel, INSTRUCTIONS_SCREEN);
    }

    public NathanDisplay getLevel() {
        return level;
    }

    public void showGameScreen() {
        cardLayout.show(mainPanel, GAME_SCREEN);
        SwingUtilities.invokeLater(() -> {
            level.requestFocusInWindow();
            level.setFocusable(true);
            level.grabFocus();
            level.revalidate();
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}