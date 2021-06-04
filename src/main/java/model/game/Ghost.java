package model.game;

import java.util.ArrayList;
import java.util.Random;

public class Ghost extends GamePiece {

    private final int id;
    private double distance;
    private GamePiece lastGamePiece;
    private double cantMoveTime;
    private boolean isScared;
    private double scaredTime;

    public Ghost(int id) {
        super(0.07);
        this.id = id;
        setInitialCoordinates();
        lastGamePiece = new Path();
        cantMoveTime = Game.getStartWaitTime();
    }

    private void setInitialCoordinates() {
        switch (id) {
            case 1:
                this.x = 1;
                this.y = 1;
                this.direction = MovementDirection.DOWN;
                break;

            case 2:
                this.x = GameBoard.getMaxX() - 2;
                this.y = 1;
                this.direction = MovementDirection.DOWN;
                break;

            case 3:
                this.x = 1;
                this.y = GameBoard.getMaxY() - 2;
                this.direction = MovementDirection.UP;
                break;

            case 4:
                this.x = GameBoard.getMaxX() - 2;
                this.y = GameBoard.getMaxY() - 2;
                this.direction = MovementDirection.UP;
                break;
        }
    }

    public void setCantMoveTime(double time) {
        cantMoveTime += time;
    }

    public void setScared() {
        isScared = true;
        scaredTime += Game.getScaredTime();
    }

    public int getId() {
        return id;
    }

    public boolean isScared() {
        return isScared;
    }

    public void resetPosition(Game game) {
        GamePiece[][] gamePieces = game.getGameBoard().getGamePieces();
        gamePieces[y][x] = lastGamePiece;
        setInitialCoordinates();
        lastGamePiece = gamePieces[y][x];
        gamePieces[y][x] = this;
    }

    public void eatGhost(Game game) {
        resetPosition(game);
        setCantMoveTime(Game.getEatWaitTime());
    }

    public boolean canEat() {
        return cantMoveTime <= 0.000001;
    }

    public void nextMove(Game game) {
        if (scaredTime > 0) {
            scaredTime -= Game.getTimeFrame();
        } else {
            isScared = false;
        }

        if (canEat()) {
            distance += VELOCITY;
            if (((int) distance) > 0) {
                move(game);
                distance = 0;
            }
        } else {
            cantMoveTime -= Game.getTimeFrame();
        }
    }

    private void move(Game game) {
        getNextDirection(game);

        GamePiece[][] gamePieces = game.getGameBoard().getGamePieces();
        int xOffset = getXOffset();
        int yOffset = getYOffset();

        if (gamePieces[y + yOffset][x + xOffset] instanceof Pacman) {
            if (this.isScared()) {
                this.eatGhost(game);
                game.getGameController().playEatGhostSound();
                game.increasePointsForEatingGhost();

            } else {
                game.reduceLife();
                this.resetPosition(game);
                game.getGameController().playDeathSound();
                game.pauseGame();
                direction = MovementDirection.CONSTANT;

            }
        } else {
            gamePieces[y][x] = lastGamePiece;
            lastGamePiece = gamePieces[y + yOffset][x + xOffset];

            gamePieces[y + yOffset][x + xOffset] = this;
            y += yOffset;
            x += xOffset;
        }
    }

    private void getNextDirection(Game game) {
        GamePiece[][] gamePieces = game.getGameBoard().getGamePieces();

        ArrayList<MovementDirection> possibleMoves = new ArrayList<>();
        if (!(gamePieces[y + 1][x] instanceof Wall) &&
                !(gamePieces[y + 1][x] instanceof Ghost)) {
            possibleMoves.add(MovementDirection.DOWN);
        }
        if (!(gamePieces[y - 1][x] instanceof Wall) &&
                !(gamePieces[y - 1][x] instanceof Ghost)) {
            possibleMoves.add(MovementDirection.UP);
        }
        if (!(gamePieces[y][x + 1] instanceof Wall) &&
                !(gamePieces[y][x + 1] instanceof Ghost)) {
            possibleMoves.add(MovementDirection.RIGHT);
        }
        if (!(gamePieces[y][x - 1] instanceof Wall) &&
                !(gamePieces[y][x - 1] instanceof Ghost)) {
            possibleMoves.add(MovementDirection.LEFT);
        }
        if (possibleMoves.size() == 0) {
            possibleMoves.add(MovementDirection.CONSTANT);
        }

        Random rand = new Random();
        int move = rand.nextInt(possibleMoves.size());
        direction = possibleMoves.get(move);
    }
}
