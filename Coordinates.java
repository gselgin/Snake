// Greg Elgin
// Last updated: 04/19/20
// Grid system coordinates used in Snake Game


class Coordinates {
    private float x, y;

    // Construct coordinate object using x and y position given
    Coordinates(float xPosition, float yPosition) {
        x = xPosition;
        y = yPosition;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }
}
