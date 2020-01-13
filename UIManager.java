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
    private void drawGame(Graphics2D g2) {
        // Get apple coordinates and draw into game
        Coordinates appleCoordinates = engine.getAppleCoordinates();
        float appleX = appleCoordinates.getX();
        float appleY = appleCoordinates.getY();
        g2.drawImage(appleImage, Math.round(appleX), Math.round(appleY), 20, 20, null);

        // Draw snake head into game
        LinkedList<Coordinates> snakeList = engine.getSnakeList();
        Coordinates snakeHead = snakeList.getLast();
        x = snakeHead.getX();
        y = snakeHead.getY();
        g2.drawImage(headImage, Math.round(x), Math.round(y), 20, 20, null);

        // Draw each snake body piece into game

        for (int i =  0; i < snakeList.size() - 1; i++) {
            Coordinates snakePart = snakeList.get(i);
            x = snakePart.getX();
            y = snakePart.getY();
            g2.drawImage(bodyImage, Math.round(x), Math.round(y), 20, 20, null);

        }

        // If the game is over draw game over graphic
        if (GameEngine.status == GameStatus.GAME_OVER) {
            g2.drawImage(gameOverImage, 100, 60, 500, 500,null);
            g2.setFont(restartFont.deriveFont(30f));
            g2.setColor(Color.WHITE);
            String restartStr = "Press ENTER To Restart";
            g2.drawString(restartStr, 250, 630);
        }

        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,708,50);

        g2.drawImage(appleImage, 200,10, 30, 30 ,null);
        g2.setColor(Color.BLACK);
        g2.setFont(restartFont.deriveFont(30f));
        score = engine.getScoreString();
        g2.drawString(score, 240, 35);


    }
}


