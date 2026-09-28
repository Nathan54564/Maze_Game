package cis1200;

import javax.swing.*;
import java.awt.*;

public class End extends JFrame {
    private JLabel stats;
    private JLabel title;
    private JPanel screen;
    private int deaths;
    private double timeTaken;

    // Constructs the end screen
    public End(int deaths, double timeTaken) {
        this.deaths = deaths;
        this.timeTaken = timeTaken;
        setSize(400, 420);
        setTitle("The Awesome Game!");

        title = new JLabel(
                "<html><p>You won the game! " +
                        "Restart the program to play again.</p></html>");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        stats = new JLabel(String.format("<html><p>Deaths: %d<br>Time: %.2f seconds</p></html>",
                deaths, timeTaken));
        stats.setHorizontalAlignment(SwingConstants.CENTER);
        stats.setFont(new Font("Arial", Font.BOLD, 16));

        screen = new JPanel(new GridLayout(0, 1));
        screen.add(title);
        screen.add(stats);
        this.getContentPane().add(screen);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // Test here if needed:
        // End a = new End(0, 0);
    }
}