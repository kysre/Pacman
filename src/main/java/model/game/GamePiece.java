package model.game;

public class GamePiece {
    protected MovementDirection direction;
    protected int x;
    protected int y;
    protected final double VELOCITY;


    protected GamePiece(double velocity) {
        this.VELOCITY = velocity;
    }


    public void setX(double x) {
        this.x = (int) x;
    }

    public void setY(double y) {
        this.y = (int) y;
    }

    public void setDirection(MovementDirection direction) {
        this.direction = direction;
    }

    public MovementDirection getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getVELOCITY() {
        return VELOCITY;
    }

    protected int getXOffset() {
        if (direction == MovementDirection.RIGHT) {
            return 1;
        } else if (direction == MovementDirection.LEFT) {
            return -1;
        } else {
            return 0;
        }
    }

    protected int getYOffset() {
        if (direction == MovementDirection.DOWN) {
            return 1;
        } else if (direction == MovementDirection.UP) {
            return -1;
        } else {
            return 0;
        }
    }
}
