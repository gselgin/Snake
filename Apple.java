// Greg Elgin
// Last Updated: 04/19/20
// Apple object generated at random grid point, used in Snake game

import java.util.Random;


class Apple {
    private Coordinates appleCoords;

    // Construct an apple object containing a random set of coordinates
    Apple() {
        Random rand = new Random();
        int x = rand.nextInt(14);
        int y = rand.nextInt(12) + 2;
        appleCoords = new Coordinates(x, y);
    }

    Coordinates getAppleCoords() {
        return appleCoords;
    }
}
