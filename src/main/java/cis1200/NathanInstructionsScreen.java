package cis1200;

import javax.swing.*;
import java.awt.*;

public class NathanInstructionsScreen extends JPanel {
    private JButton startButton;

    public NathanInstructionsScreen(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 200, 255)); // Light blue background

        JLabel titleLabel = new JLabel("Welcome to The Awesome Maze Game!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setText(getInstructionsText());
        instructionsArea.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(instructionsArea);
        add(scrollPane, BorderLayout.CENTER);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(e -> {
            Game main = (Game) SwingUtilities.getWindowAncestor(parentPanel);
            if (main != null) {
                main.showGameScreen();
                main.getLevel().ensureFocus();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(200, 200, 255));
        buttonPanel.add(startButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String getInstructionsText() {
        return "Game Instructions:\n\n"
                + "Objective:\n"
                + "Navigate the yellow player square through the maze to reach the "
                + "black and white goal flag.\n\n"
                + "Controls:\n"
                + "• Use the arrow keys to move the player:\n"
                + "  - Left Arrow: Move left\n"
                + "  - Right Arrow: Move right\n"
                + "  - Up Arrow: Move up\n"
                + "  - Down Arrow: Move down\n\n"
                + "Game Elements:\n"
                + "• Walls (#): Impassable barriers.\n"
                + "• Paths (.): Open spaces to move.\n"
                + "• Enemies (Green Blocks): Avoid or you'll respawn at last saveblock.\n"
                + "• Deathblocks (Red Blocks): Hazardous areas that kill on contact.\n"
                + "  - Blinking Deathblocks toggle visibility.\n"
                + "• Saveblocks (White Blocks): Activate these for respawn points.\n"
                + "• Winblock (Checkered): Reach this area to win.\n\n"
                + "Tips:\n"
                + "• Plan your path to avoid enemies and deathblocks.\n"
                + "• Activate multiple saveblocks for multiple respawn points.\n"
                + "• Explore the maze to find the best path.\n\n"
                + "Good luck and have fun!";
    }
}