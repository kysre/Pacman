package model.game;

import java.util.ArrayList;
import java.util.Random;

// 7 is for unvisited nodes, after visiting will put 8 and at the end put path in all of them
public class Maze {

    private final int MAX_X;
    private final int MAX_Y;
    private int[][] maze;

    public Maze(int maxX, int maxY) {
        MAX_X = maxX;
        MAX_Y = maxY;
    }


    public int[][] generate() {
        maze = new int[MAX_Y][MAX_X];
        drawWalls();

        // make random starting point:
        int firstX = 2 * randomGenerator((MAX_X - 1) / 2) + 1;
        int firstY = 2 * randomGenerator((MAX_Y - 1) / 2) + 1;
        mazeGenerator(firstX, firstY);
        putPath();

        return maze;
    }

    private void drawWalls() {
        for (int i = 0; i < MAX_Y; i++) {
            for (int j = 0; j < MAX_X; j++) {
                if (i == 0 || i == MAX_Y - 1 || j == 0 || j == MAX_X - 1) {
                    maze[i][j] = 1;
                } else if ((i % 2 == 1) && (j % 2 == 1)) {
                    maze[i][j] = 7;
                } else {
                    maze[i][j] = 1;
                }
            }
        }
    }

    private int randomGenerator(int x) {
        Random rand = new Random();
        return rand.nextInt(x);
    }

    private int randomMove(int x, int y) {
        // return -1 if there wasn't any new valid moves:
        int move = -1;

        // initialize valid moves ArrayList and fill it with valid moves:
        ArrayList<Integer> validMoves = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++) {
            if (isMoveValid(i, x, y)) validMoves.add(i);
        }

        // find a random move in validMoves ArrayList:
        if (validMoves.size() > 0) move = validMoves.get(randomGenerator(validMoves.size()));
        return move;
    }

    private boolean isMoveValid(int move, int x, int y) {
        // check if next cell is in maze scope and is NOT visited:
        switch (move) {
            case 2:
                return (x + 2 < maze[y].length) && (maze[y][x + 2] == 7);

            case 4:
                return (x - 2 > 0) && (maze[y][x - 2] == 7);

            case 1:
                return (y + 2 < maze.length) && (maze[y + 2][x] == 7);

            case 3:
                return (y - 2 > 0) && (maze[y - 2][x] == 7);

            default:
                return false;
        }
    }

    private void destroyWall(int wallX, int wallY) {
        maze[wallY][wallX] = 0;
    }

    private void mazeGenerator(int x, int y) {
        int nextMove = randomMove(x, y);

        // loop until there isn't any new moves available:
        while (nextMove != -1) {

            // do next move (change cell in maze and destroy wall between 2 cells and call itself again):
            if (nextMove == 2) {
                maze[y][x] = 8;
                destroyWall(x + 1, y);
                mazeGenerator(x + 2, y);

            } else if (nextMove == 4) {
                maze[y][x] = 8;
                destroyWall(x - 1, y);
                mazeGenerator(x - 2, y);

            } else if (nextMove == 1) {
                maze[y][x] = 8;
                destroyWall(x, y + 1);
                mazeGenerator(x, y + 2);

            } else if (nextMove == 3) {
                maze[y][x] = 8;
                destroyWall(x, y - 1);
                mazeGenerator(x, y - 2);
            }

            // generate next random move
            nextMove = randomMove(x, y);
        }

        // if there isn't any new moves:
        maze[y][x] = 8;
    }

    private void putPath() {
        for (int i = 0; i < MAX_Y; i++) {
            for (int j = 0; j < MAX_X; j++) {
                if (maze[i][j] == 8) {
                    maze[i][j] = 0;
                }
            }
        }
    }
}
