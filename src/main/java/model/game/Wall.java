package model.game;

public class Wall extends GamePiece {
    public Wall() {
        super(0);
        direction = MovementDirection.CONSTANT;
    }
}
