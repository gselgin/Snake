// Greg Elgin
// Last updated: 1/23/20
// Grid system coordinates used in Snake Game


import java.util.LinkedList;

public class Coordinates {

    private float x, y;
    private static Coordinates appleCoords;
    private static LinkedList<Coordinates> snakeList;

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

    public static boolean compareCoordsApple(Coordinates head) {
        appleCoords = GameEngine.getAppleCoordinates();
        if (head.getX() == appleCoords.getX()) {
            if (head.getY() == appleCoords.getY()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }


    }

}