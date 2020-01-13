// Greg Elgin
// Last updated: 12/30/19
// Snake used in GameEngine snake game


// Imports
import java.util.LinkedList;

// Snake class
public class Snake {

    // Define initial size of snake and its speed
    private final int INITIAL_SIZE = 3;
    private static final int SNAKE_SPEED = 250;


    //TODO: static
    private static ButtonAction direction;
    private static LinkedList<Coordinates> snakeList;
    private static Coordinates head;
    private static float x;
    private static float y;
    private static float newX;
    private static float newY;
    private static int score;


    // Constructor creates the Snake object of a linked list filled with coordinate objects
    public Snake (Coordinates initialCoords) {
        // Create linked list
        snakeList = new LinkedList<>();

        // Create head
        head = initialCoords;
        snakeList.add(head);

        // Create body of predetermined size
        x = initialCoords.getX();
        y = initialCoords.getY();
        for (int i = 0; i < INITIAL_SIZE - 1; i++) {
            x -= 10;
            Coordinates body = new Coordinates(x,y);
            snakeList.add(0, body);
        }

        // Set Direction, initially not moving
        direction = ButtonAction.No_Action;





        score = 0;
    }


    // Add a part to the end of the snake object
    public static void grow() {
        // Get the tail coordinates of the snake
        Coordinates tail = snakeList.getFirst();
        x = tail.getX();
        y = tail.getY();

        // Change coordinate of new tail based on
        // direction of travel
        if (direction == ButtonAction.Right ) {
            x -= 10;
        }
        else if (direction == ButtonAction.Left) {
            x += 10;
        }
        else if (direction == ButtonAction.Up) {
            y += 10;
        }
        else if (direction == ButtonAction.Down) {
            y -= 10;
        }

        // Add new tail to snake object
        tail = new Coordinates(x,y);
        snakeList.add(0, tail);
    }


    // TODO: the snake briefly has 2 heads, should I bother to fix?, integer division in floating point context
    // Move the snake object
    public static void move(ButtonAction d) {
        // Set snake objects direction
        direction = d;
        // Get fps of the game
        long fps = GameEngine.getfps();
        // Get the head coordinates of the snake
        head = snakeList.getLast();

        // Create coordinates for the new head of the snake
        // based on the direction specified
        if (direction == ButtonAction.Right ) {
            newY = head.getY();
            newX = (SNAKE_SPEED / fps) + head.getX();
        }
        else if (direction == ButtonAction.Left) {
            newY = head.getY();
            newX = + head.getX() - SNAKE_SPEED / fps;
        }
        else if (direction == ButtonAction.Up) {
            newY = head.getY() - SNAKE_SPEED / fps;
            newX = + head.getX();
        }
        else if (direction == ButtonAction.Down) {
            newY = head.getY() + SNAKE_SPEED / fps;
            newX = + head.getX();
        }

        // Create the new head and add to the snake object
        Coordinates newHead = new Coordinates(newX, newY);
        snakeList.addLast(newHead);

        // Move each body part of the snake
        for (int i = 0; i < (snakeList.size() - 2); i++) {
            Coordinates next = snakeList.get(i + 1);
            snakeList.set(i, next);
        }

        // Remove the old head from the snake object
        snakeList.remove(snakeList.size()-2);
        checkCollisions();

    }


    // Called by the engine, returns the snake object's list
    public LinkedList<Coordinates> getSnakeL() {
        return snakeList;
    }


    //TODO: Should this be in GameEngine? Add comments
    public static void checkCollisions() {
        head = snakeList.getLast();
        if (Coordinates.compareCoordsApple(head)) {
            grow();
            GameEngine.apple = new Apple();
            score += 1;
        }

        if (head.getX() <= 0 || head.getX() >= GameEngine.WIDTH - 15) {
            GameEngine.status = GameStatus.GAME_OVER;
        }
        if (head.getY() <= 50 || head.getY() >= GameEngine.HEIGHT - 40) {
            GameEngine.status = GameStatus.GAME_OVER;
        }
        for (int i = 0; i < (snakeList.size() - 2); i++) {
            Coordinates body = snakeList.get(i);
            if (body.getX() < (head.getX() + 5) && body.getX() > (head.getX() - 5)) {
                if (body.getY() < (head.getY() + 5) && body.getY() > (head.getY() - 5)) {
                    GameEngine.status = GameStatus.GAME_OVER;
                }
            }
        }
    }


    public int getScore() {
        return score;
    }

}
