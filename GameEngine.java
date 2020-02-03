// Greg Elgin
// Last Updated: 02/03/20
// Game Engine for snake game

// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


// Implementing runnable allows threading within class
public class GameEngine implements Runnable {

    // height is greater because the top toolbar adds to the pixels needed
    private static final int WIDTH = 450, HEIGHT = 470;
    private final int DELAY_TIME = 70;
    private final Coordinates START_POSITION = new Coordinates(2,6);

    public static GameStatus status;
    public static Apple apple;
    public static int highScore;
    private static Snake snake;
    private Thread gameThread;
    private ImageLoader imageLoader;
    private UIManager uiManager;
    private ButtonAction last;
    private Queue <ButtonAction> directions = new LinkedList<>();
    private boolean moveRight = false;
    private boolean moveLeft = false;
    private boolean moveUp = false;
    private boolean moveDown = false;


    // Construct simple game engine
    private GameEngine() {
        snake = new Snake(START_POSITION);
        apple = new Apple();

        // Construct frame with all components
        imageLoader = new ImageLoader();
        uiManager = new UIManager(this);
        JFrame frame = new JFrame("Snake");
        frame.setBackground(Color.black);
        uiManager.setBackground(Color.black);
        frame.add(uiManager);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        InputManager inputManager = new InputManager(this);
        frame.addKeyListener(inputManager);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Begin infinite loop that starts game
        start();
    }


    // starts game if not already running
    // Creates thread that calls run on infinite loop
    private void start() {
        if (status == GameStatus.RUNNING)
            return;

        status = GameStatus.RUNNING;
        last = ButtonAction.NO_ACTION;
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void run() {
        while (status == GameStatus.RUNNING) {
            // Render and update the frame
            update();
            render();
            // Delay by millisecond so that the speed of the snake is not too fast
            try
            {
                TimeUnit.MILLISECONDS.sleep(DELAY_TIME);
            }
            catch(InterruptedException ex)
            {
                System.out.println("ERROR");
            }
        }
    }


    // Updates everything that needs to change from the last frame
    private void update() {
        // If the snake is in the center of a grid square then allow it to take the next direciton
        // from the directions queue
        if (snake.getSnakeL().getLast().getX() % 1 == 0 && snake.getSnakeL().getLast().getY() % 1 == 0) {
            if (directions.size() > 0) {
                ButtonAction action = directions.remove();
                // Set the snakes direction of travel based on the action taken from the directions queue
                if (action == ButtonAction.UP && last != ButtonAction.DOWN) {
                    moveRight = false;
                    moveLeft = false;
                    moveDown = false;
                    moveUp = true;
                } else if (action == ButtonAction.DOWN && last != ButtonAction.UP) {
                    moveRight = false;
                    moveLeft = false;
                    moveUp = false;
                    moveDown = true;
                } else if (action == ButtonAction.RIGHT && last != ButtonAction.LEFT) {
                    moveLeft = false;
                    moveUp = false;
                    moveDown = false;
                    moveRight = true;
                } else if (action == ButtonAction.LEFT && last != ButtonAction.RIGHT) {
                    moveRight = false;
                    moveUp = false;
                    moveDown = false;
                    moveLeft = true;
                }
            }
        }
        // Send the call to the snake object to move
        if (moveRight) {
            Snake.move(ButtonAction.RIGHT);
            last = ButtonAction.RIGHT;
        }
        else if (moveLeft) {
            Snake.move(ButtonAction.LEFT);
            last = ButtonAction.LEFT;
        }
        else if (moveUp) {
            Snake.move(ButtonAction.UP);
            last = ButtonAction.UP;
        }
        else if (moveDown) {
            Snake.move(ButtonAction.DOWN);
            last = ButtonAction.DOWN;
        }
    }

    // Receive the inout from the input manager
    public void receiveInput(ButtonAction action) {
        if (status == GameStatus.RUNNING) {
            // Directions queue added to optimize input flow
            // Capped at size 3 so that user does not input too many steps ahead
            if (directions.size() < 4) {
                directions.add(action);
            }
        }
        else {
            if (action == ButtonAction.ENTER) {
                reset();
            }
        }
    }


    // Call UIManager to draw frame
    private void render() {
        uiManager.repaint();
    }


    private void reset() {
        // Reset snake to starting position and clear all movement
        snake = new Snake(START_POSITION);
        while (directions.size() > 0){
            directions.remove();
        }
        moveRight = false;
        moveLeft = false;
        moveDown = false;
        moveUp = false;

        // Start new game
        start();
    }


    // Compares coordinates for the head of the snake to the apple position
    // returns true if they collide, false if not
    public static boolean checkAppleCollision(Coordinates head) {
        Coordinates appleCoord = getAppleCoordinates();
        if (head.getX() == appleCoord.getX()) {
            if (head.getY() == appleCoord.getY()) {
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


    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    // Called by UI manager to paint the snake, returns the snake objects list
    public LinkedList<Coordinates> getSnakeList() {
        return snake.getSnakeL();
    }

    public Thread getThread() {
        return gameThread;
    }

    public static Coordinates getAppleCoordinates() {
        return apple.getAppleCoords();
    }

    public GameStatus getStatus() {
        return status;
    }

    public static int getScore() {
        return Snake.getScore();
    }

    public static int getHighScore() {
        return highScore;
    }


    // Main call
    public static void main(String[] args) {
        new GameEngine();
    }
}
