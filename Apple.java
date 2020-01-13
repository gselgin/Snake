
import java.util.Random;

public class Apple {
    private int x;
    private int y;
    private Coordinates appleCoords;
    private Random rand = new Random();


    public Apple() {
        x = rand.nextInt(660);
        y = rand.nextInt(590);
        appleCoords = new Coordinates(x + 10, y + 60);
    }

    public Coordinates getAppleCoords() {
        return appleCoords;
    }

}
