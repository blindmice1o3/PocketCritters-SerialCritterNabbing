package main.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage[][] cropSpriteFromSpriteSheet(int cols, int rows, int width, int height,
                                                              int xOffset, int yOffset, BufferedImage spriteSheet) {
        BufferedImage[][] sprites = new BufferedImage[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                sprites[y][x] = spriteSheet.getSubimage((x*width)+(x*xOffset)+xOffset,
                        (y*height)+(y*yOffset)+yOffset, width, height);
            }
        }

        return sprites;
    }

} // **** end main.utils.ImageLoader class ****