// Greg Elgin
// 1/31/20
// Loads images into buffered images to be used by UIManager
// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;


public class ImageLoader {

    // Takes image path as parameter and returns buffered image
    // Catches exceptions and continues game with files found
    public BufferedImage loadImage(String path) {
        BufferedImage imageToReturn = null;

        try {
            imageToReturn = ImageIO.read(getClass().getResource(path));
        }
        catch (IOException | IllegalArgumentException e) {
            System.out.println(path + " file not found");
        }
        return imageToReturn;
    }
}
