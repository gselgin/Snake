// Greg Elgin
// Last Updated: 1/31/20
// Apple object generated at random grid point, used in Snake game

import java.util.Random;


public class Apple {
    private Coordinates appleCoords;
    private Random rand = new Random();

    // Construct an apple object containing a random set of coordinates
    public Apple() {
        int x = rand.nextInt(14);
        int y = rand.nextInt(12) + 2;
        appleCoords = new Coordinates(x, y);
    }

    public Coordinates getAppleCoords() {
        return appleCoords;
    }
}
