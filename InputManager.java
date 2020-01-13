// Greg Elgin
// 12/16/19
// Input manager listens for user keystrokes
// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros

import sun.jvm.hotspot.utilities.GenericGrowableArray;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputManager implements KeyListener {
    private GameEngine engine;
    private GameStatus status;

    // Construct InputManager
    InputManager(GameEngine engine) {
        this.engine = engine;
    }


    public void keyPressed(KeyEvent event) {
        status = engine.getStatus();
        int keyCode = event.getKeyCode();
        ButtonAction action = ButtonAction.No_Action;

        if (status == GameStatus.RUNNING) {
            if (keyCode == KeyEvent.VK_RIGHT) {
                action = ButtonAction.Right;
            }
            else if (keyCode == KeyEvent.VK_LEFT) {
                action = ButtonAction.Left;
            }
            else if (keyCode == KeyEvent.VK_UP) {
                action = ButtonAction.Up;
            }
            else if (keyCode == KeyEvent.VK_DOWN) {
                action = ButtonAction.Down;
            }
        }
        else if (status == GameStatus.GAME_OVER) {
            if (keyCode == KeyEvent.VK_ENTER) {
                action = ButtonAction.ENTER;
            }
        }

            SendInput(action);
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}



    private void SendInput(ButtonAction input) {
        engine.receiveInput(input);
    }
}
