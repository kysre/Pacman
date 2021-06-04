package model.game;

public class Pacman extends GamePiece {

    private double distance;

    public Pacman() {
        super(0.1);
        direction = MovementDirection.CONSTANT;
        x = GameBoard.getMaxX() / 2;
        y = GameBoard.getMaxY() / 2;
    }

    public void nextMove(Game game) {
        distance += VELOCITY;
        if (((int) distance) > 0) {
            move(game);
            distance = 0;
        }
    }

    private void move(Game game) {
        if (direction != MovementDirection.CONSTANT) {
            GamePiece[][] gamePieces = game.getGameBoard().getGamePieces();
            int xOffset = getXOffset();
            int yOffset = getYOffset();

            if (gamePieces[y + yOffset][x + xOffset] instanceof Ghost) {
                Ghost ghost = (Ghost) gamePieces[y + yOffset][x + xOffset];
                if (ghost.canEat()) {
                    if (ghost.isScared()) {
                        ghost.eatGhost(game);
                        game.getGameController().playEatGhostSound();
                        game.increasePointsForEatingGhost();
                    } else {
                        game.reduceLife();
                        ghost.resetPosition(game);
                        game.getGameController().playDeathSound();
                        game.pauseGame();
                        direction = MovementDirection.CONSTANT;
                    }
                }

            } else if (gamePieces[y + yOffset][x + xOffset] instanceof Path) {
                gamePieces[y][x] = new Path(false);
                if (((Path) gamePieces[y + yOffset][x + xOffset]).isPoint()) {
                    game.increasePath();
                    game.increasePoint(5);
                    game.getGameController().playEatPointSound();
                }

                gamePieces[y + yOffset][x + xOffset] = this;
                y += yOffset;
                x += xOffset;
            } else if (gamePieces[y + yOffset][x + xOffset] instanceof Energy) {
                gamePieces[y][x] = new Path(false);
                game.setGhostsScared();
                game.getGameController().playEatEnergySound();
                game.increasePath();
                gamePieces[y + yOffset][x + xOffset] = this;
                y += yOffset;
                x += xOffset;
            }
        }
    }
}
