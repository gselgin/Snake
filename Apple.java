
import java.util.Random;

public class Apple {
    private int x;
    private int y;
    private Coordinates appleCoords;
    private Random rand = new Random();


    public Apple() {
        x = rand.nextInt(14);
        y = rand.nextInt(12) + 2;
        appleCoords = new Coordinates(x, y);
    }

    public Coordinates getAppleCoords() {
        return appleCoords;
    }

}
