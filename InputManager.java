// Greg Elgin
// Last Updated: 1/30/20
// Input manager listens for user keystrokes

// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputManager implements KeyListener {
    private GameEngine engine;

    // Construct InputManager
    InputManager(GameEngine engine) {
        this.engine = engine;
    }

    // Listens for keys being pressed
    public void keyPressed(KeyEvent event) {
        GameStatus status = engine.getStatus();
        int keyCode = event.getKeyCode();
        ButtonAction action = ButtonAction.NO_ACTION;

        // If the game is running set action equal to the direction key pressed
        if (status == GameStatus.RUNNING) {
            if (keyCode == KeyEvent.VK_RIGHT) {
                action = ButtonAction.RIGHT;
            }
            else if (keyCode == KeyEvent.VK_LEFT) {
                action = ButtonAction.LEFT;
            }
            else if (keyCode == KeyEvent.VK_UP) {
                action = ButtonAction.UP;
            }
            else if (keyCode == KeyEvent.VK_DOWN) {
                action = ButtonAction.DOWN;
            }
        }
        // If the game is over listen for enter key pressed
        else if (status == GameStatus.GAME_OVER) {
            if (keyCode == KeyEvent.VK_ENTER) {
                action = ButtonAction.ENTER;
            }
        }
        SendInput(action);
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    // Send action input to GameEngine receiveInput function
    private void SendInput(ButtonAction input) {
        engine.receiveInput(input);
    }
}
