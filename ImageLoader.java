// Greg Elgin
// 12/16/19
// Loads the images into buffered images
// Source: http://gamecodeschool.com/android/building-a-simple-game-engine/
// Source: https://github.com/ahmetcandiroglu/Super-Mario-Bros

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;


public class ImageLoader {

    // Construct ImageLoader, initially is empty
    public ImageLoader() {

    }

    // takes image path as parameter and returns buffered image
    public BufferedImage loadImage(String path) {
        BufferedImage imageToReturn = null;

        try {
            imageToReturn = ImageIO.read(getClass().getResource(path));
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
        return imageToReturn;
    }



}
