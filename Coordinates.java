// Greg Elgin
// Last updated: 1/31/20
// Grid system coordinates used in Snake Game


public class Coordinates {
    private float x, y;

    // Construct coordinate object using x and y position given
    public Coordinates(float xPosition, float yPosition) {
        x = xPosition;
        y = yPosition;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}