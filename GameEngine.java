// Greg Elgin
// 12/16/19
// Game engine for Snake
// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros

// Imports JFrame
//TODO: Organized: ButtonAction,
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;



// Implementing runnable allows threading within class
public class GameEngine implements Runnable {

    // Define uninitialized variables
    public static GameStatus status;
    private static long fps;
    private long timeThisFrame;
    private Thread gameThread;
    private ImageLoader imageLoader;
    private UIManager uiManager;
    private ButtonAction last;
    private boolean turning;
    private  ButtonAction action;
    private Queue <ButtonAction> directions = new LinkedList<>();

    // height is more because the top toolbar adds to the pixels needed
    public static final int WIDTH = 450, HEIGHT = 470;

    private Coordinates snakePosition = new Coordinates(2,6);
    public Snake snake;
    public static Apple apple;

    private boolean moveRight = false;
    private boolean moveLeft = false;
    private boolean moveUp = false;
    private boolean moveDown = false;

    // Create input manager to respond to user keystrokes
    InputManager inputManager = new InputManager(this);


    // Construct simple game engine
    private GameEngine() {

        snake = new Snake(snakePosition);
        apple = new Apple();

        // Construct frame
        imageLoader = new ImageLoader();
        uiManager = new UIManager(this);
        JFrame frame = new JFrame("Snake");
        frame.setBackground(Color.black);
        uiManager.setBackground(Color.black);
        frame.add(uiManager);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
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
            long startFrameTime = System.currentTimeMillis();

            if (turning) {
                receiveInput(action);
            }
            // Render and update the frame
            update();
            render();
            // Delay by 1 millisecond so that timeThisFrame is always > 0
            try
            {
                TimeUnit.MILLISECONDS.sleep(70);
            }
            catch(InterruptedException ex)
            {
                System.out.println("ERROR");
            }
            // This is the number of milliseconds it took to execute update and draw for this frame
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            //If the time is not zero, calculate the frames per second
            if (timeThisFrame > 0) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    //TODO can i get rid of last direction enum?

    // Updates everything that needs to change from the last frame
    public void update() {
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

    public void receiveInput(ButtonAction a) {
        action = a;
        if (snake.getSnakeL().getLast().getX() % 1 == 0 && snake.getSnakeL().getLast().getY() % 1 == 0) {
            turning = false;
            if (action == ButtonAction.UP && last != ButtonAction.DOWN) {
                moveRight = false;
                moveLeft = false;
                moveDown = false;
                moveUp = true;
            }
            if (action == ButtonAction.DOWN && last != ButtonAction.UP) {
                moveRight = false;
                moveLeft = false;
                moveUp = false;
                moveDown = true;
            }
            if (action == ButtonAction.RIGHT && last != ButtonAction.LEFT) {
                moveLeft = false;
                moveUp = false;
                moveDown = false;
                moveRight = true;
            }
            if (action == ButtonAction.LEFT && last != ButtonAction.RIGHT) {
                moveRight = false;
                moveUp = false;
                moveDown = false;
                moveLeft = true;
            }
        }
        else {
            turning = true;
        }
        if (action == ButtonAction.ENTER) {
            reset();
        }

    }

    // Call UIManager to draw frame
    private void render() {
        uiManager.repaint();
    }


    // Resets the game
    private void reset() {
        GameEngine engine = new GameEngine();

    }


    // Called from UIManager, returns the imageLoader in SimpleGameEngine
    public ImageLoader getImageLoader() {
        return imageLoader;
    }



    // Called by UI manager to paint the snake, returns the snake objects list
    public LinkedList<Coordinates> getSnakeList() {
        return snake.getSnakeL();
    }


    public static long getfps() {
        return fps;
    }

    public static Coordinates getAppleCoordinates() {
        return apple.getAppleCoords();
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getWidth() {
        return WIDTH;
    }
    public int getHeight() {
        return HEIGHT;
    }

    public String getScoreString() {
        return Integer.toString(snake.getScore());
    }


    // Main call
    public static void main(String[] args) {
        new GameEngine();
    }
}
