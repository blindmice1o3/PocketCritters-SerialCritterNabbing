package main.gfx;

import main.utils.FontGrabber;
import main.utils.ImageLoader;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Assets {

    public static Map<String, BufferedImage> fontHashMap;

    public static BufferedImage world, player, teamRocket, battleStateSpriteSheet;
    public static BufferedImage[] jamesDown, jamesUp, jamesLeft, jamesRight,
            jessieDown, jessieUp, jessieLeft, jessieRight;

    public static void init() {
        world = ImageLoader.loadImage("/pokemon-gsc-kanto.png");
        player = ImageLoader.loadImage("/butters_profchaos.jpg");
        teamRocket = ImageLoader.loadImage("/TeamRocket.png");
        battleStateSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Yellow - Battle Interface.png");

        fontHashMap = FontGrabber.initFont();   //this line must come AFTER battleStateSpriteSheet gets initiated.

        jamesDown = new BufferedImage[2];
        jamesDown[0] = teamRocket.getSubimage(236, 24, 12, 16);
        jamesDown[1] = teamRocket.getSubimage(236, 42, 12, 15);

        jamesUp = new BufferedImage[2];
        jamesUp[0] = teamRocket.getSubimage(250, 24, 12, 16);
        jamesUp[1] = teamRocket.getSubimage(250, 42, 12, 15);

        jamesLeft = new BufferedImage[2];
        jamesLeft[0] = teamRocket.getSubimage(264, 24, 11, 16);
        jamesLeft[1] = teamRocket.getSubimage(264, 42, 11, 15);

        jamesRight = new BufferedImage[2];
        jamesRight[0] = flipHorizontally(jamesLeft[0]);
        jamesRight[1] = flipHorizontally(jamesLeft[1]);


        jessieDown = new BufferedImage[2];
        jessieDown[0] = teamRocket.getSubimage(235, 93, 15, 16);
        jessieDown[1] = teamRocket.getSubimage(235, 111, 15, 15);

        jessieUp = new BufferedImage[2];
        jessieUp[0] = teamRocket.getSubimage(252, 93, 15, 16);
        jessieUp[1] = teamRocket.getSubimage(252, 111, 15, 15);

        jessieLeft = new BufferedImage[2];
        jessieLeft[0] = teamRocket.getSubimage(269, 93, 15, 16);
        jessieLeft[1] = teamRocket.getSubimage(269, 111, 15, 15);

        jessieRight = new BufferedImage[2];
        jessieRight[0] = flipHorizontally(jessieLeft[0]);
        jessieRight[1] = flipHorizontally(jessieLeft[1]);

    }

    public static BufferedImage flipHorizontally(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return op.filter(image, null);
    }

}
