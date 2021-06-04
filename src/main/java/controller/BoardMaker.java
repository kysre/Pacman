package controller;

import model.game.*;

public class BoardMaker {
    public static GameBoardObject[][] getGameBoard(GamePiece[][] gamePieces) {
        GameBoardObject[][] gameBoardObjects = new GameBoardObject[gamePieces.length][gamePieces[0].length];
        for (int i = 0; i < gamePieces.length; i++) {
            for (int j = 0; j < gamePieces[i].length; j++) {
                if (gamePieces[i][j] instanceof Wall) {
                    gameBoardObjects[i][j] = GameBoardObject.WALL;
                } else if (gamePieces[i][j] instanceof Path) {
                    Path path = (Path) gamePieces[i][j];
                    if (path.isPoint()) {
                        gameBoardObjects[i][j] = GameBoardObject.POINT;
                    } else {
                        gameBoardObjects[i][j] = GameBoardObject.PATH;
                    }
                } else if (gamePieces[i][j] instanceof Energy) {
                    gameBoardObjects[i][j] = GameBoardObject.ENERGY;

                } else if (gamePieces[i][j] instanceof Ghost) {
                    Ghost ghost = (Ghost) gamePieces[i][j];
                    if (ghost.isScared()) {
                        gameBoardObjects[i][j] = GameBoardObject.GHOST_SCARED;
                    } else {
                        switch (ghost.getId()) {
                            case 1:
                                gameBoardObjects[i][j] = GameBoardObject.GHOST_1;
                                break;

                            case 2:
                                gameBoardObjects[i][j] = GameBoardObject.GHOST_2;
                                break;

                            case 3:
                                gameBoardObjects[i][j] = GameBoardObject.GHOST_3;
                                break;

                            case 4:
                                gameBoardObjects[i][j] = GameBoardObject.GHOST_4;
                                break;
                        }
                    }
                } else if (gamePieces[i][j] instanceof Pacman) {
                    if (gamePieces[i][j].getDirection() == MovementDirection.UP) {
                        gameBoardObjects[i][j] = GameBoardObject.PACMAN_UP;
                    } else if (gamePieces[i][j].getDirection() == MovementDirection.DOWN) {
                        gameBoardObjects[i][j] = GameBoardObject.PACMAN_DOWN;
                    } else if (gamePieces[i][j].getDirection() == MovementDirection.RIGHT) {
                        gameBoardObjects[i][j] = GameBoardObject.PACMAN_RIGHT;
                    } else if (gamePieces[i][j].getDirection() == MovementDirection.LEFT) {
                        gameBoardObjects[i][j] = GameBoardObject.PACMAN_LEFT;
                    } else if (gamePieces[i][j].getDirection() == MovementDirection.CONSTANT) {
                        gameBoardObjects[i][j] = GameBoardObject.PACMAN_CONSTANT;
                    }
                }
            }
        }
        return gameBoardObjects;
    }
}
