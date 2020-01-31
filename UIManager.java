// Greg Elgin
// Last Updated: 01/31/20
// User Interface Manager for Snake game's Game Engine

// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros
// Image Source: https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Red.svg/600px-Red.svg.png
// Image Source: https://www.freeiconspng.com/uploads/green-square-1.png
// Image Source: http://pluspng.com/img-png/teacher-with-apple-png-apple-tattoo-for-teacher-300.png
// Image Source: https://pngimg.com/uploads/game_over/game_over_PNG14.png
// Font Source: https://pngimg.com/uploads/game_over/game_over_PNG14.png


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.LinkedList;
import java.io.IOException;


public class UIManager extends JPanel {

    private GameEngine engine;
    private BufferedImage headImage;
    private BufferedImage appleImage;
    private BufferedImage bodyImage;
    private BufferedImage gameOverImage;
    private Font restartFont;


    // Constructor takes the game engine as a parameter, calls image loader to buffer images
    public UIManager(GameEngine engine) {
        this.engine = engine;
        ImageLoader loader = engine.getImageLoader();
        headImage = loader.loadImage("Resources/RedSquare.png");
        bodyImage = loader.loadImage("Resources/GreenSquare.png");
        appleImage = loader.loadImage("Resources/apple.png");
        gameOverImage = loader.loadImage("Resources/GameOver.png");

        // Load the font used when game starts, this improves runtime
        try {
            InputStream input = getClass().getResourceAsStream("Resources/ostrich-regular.ttf");
            restartFont = Font.createFont(Font.TRUETYPE_FONT, input);
        }
        catch (FontFormatException | IOException | IllegalArgumentException | NullPointerException e) {
            System.out.println("ERROR: FONT NOT FOUND. PROGRAM CAN NOT RUN");
            Thread gameThread = engine.getThread();
            gameThread.interrupt();
        }
    }


    // Called by game engine as repaint, paints the components as 2D graphics
    public void paintComponent(Graphics g) {
        // We are not printing on the entire component,
        // so calling this paints the area we are not using
        super.paintComponent(g);

        // Calls subclass of Graphics to be used, draws game, disposes g2
        Graphics2D g2 = (Graphics2D) g.create();
        drawGame(g2);
        g2.dispose();
    }


    // Grid, board size 15 X 15 grid squares, each grid square is 30 X 30 pixels big, so board is 450 X 450 pixels
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
        Coordinates appleCoordinates = GameEngine.getAppleCoordinates();
        float appleX = appleCoordinates.getX();
        float appleY = appleCoordinates.getY();
        // Gets grid coordinate and multiplies by 30 to get the pixel number. Adding 4 pixels places the apple
        // in the center of the grip square
        g2.drawImage(appleImage, (Math.round(appleX)*30) + 4, (Math.round(appleY)*30) + 4, 22, 22, null);

        // Draw each snake piece into game
        LinkedList<Coordinates> snakeList = engine.getSnakeList();
        for (int i =  0; i < snakeList.size(); i++) {
            Coordinates snakePart = snakeList.get(i);
            float x = snakePart.getX();
            float y = snakePart.getY();
            // If the piece is the head draw head, else draw body
            if (snakePart == snakeList.getLast()) {
                g2.drawImage(headImage, (Math.round(x*30)) + 3, (Math.round(y*30)) + 3, 23, 23, null);
            }
            else {
                g2.drawImage(bodyImage, (Math.round(x*30)) + 3, (Math.round(y*30)) + 3, 23, 23, null);
            }
        }

        //Draw the score to the top of the screen
        g2.setColor(Color.WHITE);
        g2.fillRect(0,0,708,60);
        g2.drawImage(appleImage, 180,10, 35, 35 ,null);
        g2.setColor(Color.BLACK);
        g2.setFont(restartFont.deriveFont(30f));
        String score = engine.getScoreString();
        g2.drawString(score, 220, 40);

        // If the game is over draw game over graphic
        if (GameEngine.status == GameStatus.GAME_OVER) {
            g2.drawImage(gameOverImage, 90, 70, 250, 250,null);
            g2.setFont(restartFont.deriveFont(30f));
            g2.setColor(Color.BLACK);
            String restartStr = "Press ENTER To Restart";
            g2.drawString(restartStr, 100, 350);
        }
    }
}


