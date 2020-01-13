
//TODO: Need to change to a grid system
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
        if (head.getX() < appleCoords.getX() + 15 && head.getX() > appleCoords.getX() - 15) {
            if (head.getY() < appleCoords.getY() + 15 && head.getY() > appleCoords.getY() - 15) {
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