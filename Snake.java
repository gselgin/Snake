// Greg Elgin
// Last updated: 1/23/20
// Snake used in GameEngine Snake Game


import java.util.LinkedList;

public class Snake {

    private final int INITIAL_SIZE = 3;
    private static final float SNAKE_SPEED = (float).5;

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

        // Create snake head
        head = initialCoords;
        snakeList.add(head);

        // Create body of predetermined size
        x = initialCoords.getX();
        y = initialCoords.getY();
        for (int i = 0; i < INITIAL_SIZE - 1; i++) {
            x -= .5;
            Coordinates body = new Coordinates(x,y);
            snakeList.add(0, body);
        }

        // Set Direction, initially not moving
        direction = ButtonAction.NO_ACTION;

        // initialize score
        score = 0;
    }


    // Add a part to the end of the snake object
    public static void grow() {
        // Execute twice
        for (int i = 0; i < 2; i++) {
            // Get the tail coordinates of the snake
            Coordinates tail = snakeList.getFirst();
            x = tail.getX();
            y = tail.getY();

            // Change coordinate of new tail based on
            // direction of travel
            if (direction == ButtonAction.RIGHT) {
                x -= .5;
            }
            else if (direction == ButtonAction.LEFT) {
                x += .5;
            }
            else if (direction == ButtonAction.UP) {
                y += .5;
            }
            else if (direction == ButtonAction.DOWN) {
                y -= .5;
            }

            // Add new tail to snake object
            tail = new Coordinates(x,y);
            snakeList.add(0, tail);
        }
    }


    // TODO: the snake briefly has 2 heads, should I bother to fix?
    // Move the snake object
    public static void move(ButtonAction d) {
        // Set snake objects direction
        direction = d;
        // Get fps of the game
        float fps = GameEngine.getfps();
        // Get the head coordinates of the snake
        head = snakeList.getLast();

        // Create coordinates for the new head of the snake
        // based on the direction specified
        if (direction == ButtonAction.RIGHT) {
            newY = head.getY();
            newX = SNAKE_SPEED + head.getX();
        }
        else if (direction == ButtonAction.LEFT) {
            newY = head.getY();
            newX = + head.getX() - SNAKE_SPEED;
        }
        else if (direction == ButtonAction.UP) {
            newY = head.getY() - SNAKE_SPEED;
            newX = + head.getX();
        }
        else if (direction == ButtonAction.DOWN) {
            newY = head.getY() + SNAKE_SPEED;
            newX = + head.getX();
        }

        // Create the new head and add to the snake object
        Coordinates newHead = new Coordinates(newX, newY);
        snakeList.addLast(newHead);

        // Move each body part of the snake
        for (int i = 0; i < (snakeList.size() - 2); i++) {
            Coordinates current = snakeList.get(i);
            Coordinates next = snakeList.get(i + 1);
            snakeList.set(i,next);

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
        if (GameEngine.checkAppleCollision(head)) {
            grow();
            GameEngine.apple = new Apple();
            score += 1;
        }

        if (head.getX() < 0 || head.getX() > 14) {
            GameEngine.status = GameStatus.GAME_OVER;
        }
        if (head.getY() < 2 || head.getY() > 14) {
            GameEngine.status = GameStatus.GAME_OVER;
        }
        for (int i = 0; i < (snakeList.size() - 2); i++) {
            Coordinates body = snakeList.get(i);
            if (body.getX() == head.getX()) {
                if (body.getY() == head.getY()) {
                    GameEngine.status = GameStatus.GAME_OVER;
                }
            }
        }
    }

    // Returns snake's score
    public int getScore() {
        return score;
    }


}
