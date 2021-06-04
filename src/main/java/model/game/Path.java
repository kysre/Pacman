package model.game;

public class Path extends GamePiece {
    private boolean doesHavePoint;

    public Path() {
        super(0);
        direction = MovementDirection.CONSTANT;
        doesHavePoint = true;
    }

    public Path(boolean isPoint) {
        super(0);
        direction = MovementDirection.CONSTANT;
        doesHavePoint = isPoint;
    }

    public boolean isPoint() {
        return doesHavePoint;
    }

    public void eatPoint(Game game) {
        if (doesHavePoint) {
            game.increasePoint(1);
            doesHavePoint = false;
        }
    }

    public void makePoint() {
        doesHavePoint = true;
    }
}
