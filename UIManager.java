// Greg Elgin
// Last Updated: 01/12/20
// User Interface Manager for Game Engine
// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros
// Image Source: http://pluspng.com/img-png/teacher-with-apple-png-apple-tattoo-for-teacher-300.png
// Image Source: https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQt7t4yMYMBZpURY8ocJJJjiD8zEWIOdFVTK9zExv4hWIK-UlztGg&s
// Image Source: https://www.freeiconspng.com/uploads/green-square-1.png


// Imports
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import sun.jvm.hotspot.utilities.GenericGrowableArray;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.LinkedList;
import java.io.IOException;


// User Interface Manager class
public class UIManager extends JPanel {

    // Variables
    private GameEngine engine;
    private BufferedImage headImage;
    private BufferedImage appleImage;
    private BufferedImage bodyImage;
    private BufferedImage gameOverImage;
    private float x;
    private float y;
    private Font restartFont;
    private String score;


    // Constructor takes the game engine as a parameter, calls image loader to buffer images
    public UIManager(GameEngine engine) {
        this.engine = engine;
        ImageLoader loader = engine.getImageLoader();
        headImage = loader.loadImage("RedSquare.png");
        bodyImage = loader.loadImage("GreenSquare.png");
        appleImage = loader.loadImage("apple.png");
        gameOverImage = loader.loadImage("GameOver.png");
        try {
            InputStream input = getClass().getResourceAsStream("ostrich-regular.ttf");
            restartFont = Font.createFont(Font.TRUETYPE_FONT, input);
        }
        catch (FontFormatException | IOException e) {
            System.out.println("ERROR");
        }

    }


    // Called by game engine as repaint, paints the components at 2D graphics
    public void paintComponent(Graphics g) {
        // We are not printing on the entire component,
        // so calling this paints the area we are not using
        super.paintComponent(g);

        // Calls subclass of Graphics to be used, draw game, dispose g2
        Graphics2D g2 = (Graphics2D) g.create();
        drawGame(g2);
        g2.dispose();
    }

    //TODO: fix static accessed via instance reference

    // Draws the game

    //TODO: Grid, Board Size 15X15 grid squares, each grid square is 30X30 pixels big, so board is 450X450 pixels
    private void drawGame(Graphics2D g2) {

        // Draws checkerboard pattern
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if ((i + j) % 2 == 0) {
                    g2.setColor(Color.GRAY);
                    g2.fillRect(i*30, j*30, 30, 30);
                }
                else {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRect(i*30, j*30, 30, 30);

                }
            }

        }

        // Get apple coordinates and draw into game
        Coordinates appleCoordinates = engine.getAppleCoordinates();
        float appleX = appleCoordinates.getX();
        float appleY = appleCoordinates.getY();
        // Gets grid coordinate and multiplies by 30 to get the pixel number. Adding 4 places the apple
        // in the center of the grip square
        g2.drawImage(appleImage, (Math.round(appleX)*30) + 4, (Math.round(appleY)*30) + 4, 22, 22, null);

        // Draw snake head into game
        LinkedList<Coordinates> snakeList = engine.getSnakeList();
        Coordinates snakeHead = snakeList.getLast();
        x = snakeHead.getX();
        y = snakeHead.getY();
        g2.drawImage(headImage, (Math.round(x*30)) + 3, (Math.round(y*30)) + 3, 23, 23, null);

        // Draw each snake body piece into game

        for (int i =  0; i < snakeList.size() - 1; i++) {
            Coordinates snakePart = snakeList.get(i);
            x = snakePart.getX();
            y = snakePart.getY();
            g2.drawImage(bodyImage, (Math.round(x*30)) + 3, (Math.round(y*30)) + 3, 23, 23, null);

        }

        // If the game is over draw game over graphic
        if (GameEngine.status == GameStatus.GAME_OVER) {
            g2.drawImage(gameOverImage, 90, 70, 250, 250,null);
            g2.setFont(restartFont.deriveFont(30f));
            g2.setColor(Color.BLACK);
            String restartStr = "Press ENTER To Restart";
            g2.drawString(restartStr, 100, 350);
        }

        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,708,60);

        g2.drawImage(appleImage, 180,10, 35, 35 ,null);
        g2.setColor(Color.BLACK);
        g2.setFont(restartFont.deriveFont(30f));
        score = engine.getScoreString();
        g2.drawString(score, 220, 40);


    }
}


