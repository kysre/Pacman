package controller;

public enum GameBoardObject {
    PACMAN_UP,
    PACMAN_DOWN,
    PACMAN_RIGHT,
    PACMAN_LEFT,
    PACMAN_CONSTANT,
    WALL,
    POINT,
    ENERGY,
    PATH,
    GHOST_1,
    GHOST_2,
    GHOST_3,
    GHOST_4,
    GHOST_SCARED;

    public String toString() {
        switch (this) {
            case PACMAN_UP:
                return "PacmanUp";

            case PACMAN_DOWN:
                return "PacmanDown";

            case PACMAN_RIGHT:
                return "PacmanRight";

            case PACMAN_LEFT:
                return "PacmanLeft";

            case PACMAN_CONSTANT:
                return "PacmanConstant";

            case WALL:
                return "Wall";

            case POINT:
                return "Point";

            case ENERGY:
                return "Energy";

            case PATH:
                return "Path";

            case GHOST_1:
                return "Ghost1";

            case GHOST_2:
                return "Ghost2";

            case GHOST_3:
                return "Ghost3";

            case GHOST_4:
                return "Ghost4";

            case GHOST_SCARED:
                return "GhostScared";
        }
        return "";
    }
}
