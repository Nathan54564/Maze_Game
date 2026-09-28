package cis1200;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;

public class NathanMazeGenerator {
    public void generateMaze(String[][] array) {
        Stack<Point> stack = new Stack<>();
        Random random = new Random();
        StringBuffer possible = new StringBuffer();
        Point location = new Point();
        final int ROWS = array.length;
        final int COLS = array[0].length;
        final int totalCells = (ROWS / 2) * (COLS / 2);
        int x = 1 + (2 * random.nextInt(ROWS / 2));
        int y = 1 + (2 * random.nextInt(COLS / 2));
        int visitedCells = 1;

        createRawArray(array);

        while (visitedCells < totalCells) {
            possible.setLength(0);

            // Check neighbors
            if (y > 1 && checkWalls(x, y - 2, array)) {
                possible.append('N');
            }
            if (y < array[0].length - 2 && checkWalls(x, y + 2, array)) {
                possible.append('S');
            }
            if (x < array.length - 2 && checkWalls(x + 2, y, array)) {
                possible.append('E');
            }
            if (x > 1 && checkWalls(x - 2, y, array)) {
                possible.append('W');
            }

            if (possible.length() != 0) {
                stack.push(new Point(x, y));
                int direction = random.nextInt(possible.length());
                char dirChar = possible.charAt(direction);

                switch (dirChar) {
                    case 'N':
                        array[x][y - 1] = ".";
                        y = y - 2;
                        break;
                    case 'S':
                        array[x][y + 1] = ".";
                        y = y + 2;
                        break;
                    case 'E':
                        array[x + 1][y] = ".";
                        x = x + 2;
                        break;
                    case 'W':
                        array[x - 1][y] = ".";
                        x = x - 2;
                        break;
                    default:
                        break;
                }
                visitedCells++;
            } else {
                location = stack.pop();
                x = location.x;
                y = location.y;
            }
        }

        // Make entrance and exit
        array[1][0] = ".";
        array[array.length - 2][array[0].length - 1] = ".";
    }

    private boolean checkWalls(int x, int y, String[][] array) {
        return array[x - 1][y].equals("#")
                && array[x + 1][y].equals("#")
                && array[x][y - 1].equals("#")
                && array[x][y + 1].equals("#");
    }

    private void createRawArray(String[][] emptyArray) {
        for (int i = 0; i < emptyArray.length; i++) {
            for (int j = 0; j < emptyArray[0].length; j++) {
                if ((i % 2) == 1 && (j % 2) == 1) {
                    emptyArray[i][j] = ".";
                } else {
                    emptyArray[i][j] = "#";
                }
            }
        }
    }
}