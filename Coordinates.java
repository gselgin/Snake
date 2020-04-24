// Greg Elgin
// Last updated: 04/23/20
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

    void setX(float newX) {
        x = newX;
    }

    void setY(float newY) {
        y = newY;
    }
}
