package main.gfx;

import main.utils.FontGrabber;
import main.utils.ImageLoader;
import model.tiles.Tile;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Assets {

    public static Map<String, BufferedImage> fontHashMap;
    public static BufferedImage[][] crittersBufferedImageNestedArray;
    public static BufferedImage[][] nabbersBufferedImageNestedArray;
    public static BufferedImage[][] overworldSpritesBufferedImageNestedArray;

    public static BufferedImage teamRocket, critterSpriteSheet, nabberSpriteSheet,
            overworldSpritesSpriteSheet, indoorsHomeAndRoomSpriteSheet,
            battleStateSpriteSheet, menuStateSpriteSheet, menuStateInfoSpriteSheet,
            critterBallSprite, cursorSprite;

    public static BufferedImage backgroundCritterBeltList;

    public static BufferedImage world, roomPlayer, homePlayer, homeRival, lab;
    public static BufferedImage computerWithKeyboard, keyboardFromComputer, gameConsole, television;

    public static BufferedImage[] jamesDown, jamesUp, jamesLeft, jamesRight,
            jessieDown, jessieUp, jessieLeft, jessieRight,
            pikachuDown, pikachuUp, pikachuLeft, pikachuRight;

    public static void init() {
        teamRocket = ImageLoader.loadImage("/TeamRocket.png");
        critterSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Yellow - Pokemon.png");
        nabberSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Yellow - Trainers.png");
        overworldSpritesSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Yellow - Overworld Characters.png");
        indoorsHomeAndRoomSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Yellow - Pallet Town.png");
        battleStateSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Yellow - Battle Interface.png");
        menuStateSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Crystal - Start Menu.png");
        menuStateInfoSpriteSheet = ImageLoader.loadImage("/Game Boy GBC - Pokemon Yellow - Trainer Card.png");
        critterBallSprite = battleStateSpriteSheet.getSubimage(324, 269, 7, 7);
        cursorSprite = battleStateSpriteSheet.getSubimage(331, 270, 7, 7);

        backgroundCritterBeltList = battleStateSpriteSheet.getSubimage(320, 2, 159, 145);

        world = ImageLoader.loadImage("/pokemon-gsc-kanto.png");
        roomPlayer = indoorsHomeAndRoomSpriteSheet.getSubimage(32, 8, 128, 128);
        homePlayer = indoorsHomeAndRoomSpriteSheet.getSubimage(168, 8, 128, 128);
        homeRival = indoorsHomeAndRoomSpriteSheet.getSubimage(304, 9, 128, 128);
        lab = indoorsHomeAndRoomSpriteSheet.getSubimage(23, 544, 160, 192);

        computerWithKeyboard = roomPlayer.getSubimage( 0, 0, Tile.WIDTH, (Tile.HEIGHT + (Tile.HEIGHT / 2)) );
        keyboardFromComputer = roomPlayer.getSubimage( (0 * Tile.WIDTH), (1 * Tile.HEIGHT), Tile.WIDTH, (Tile.HEIGHT / 2) );
        gameConsole = roomPlayer.getSubimage( (3 * Tile.WIDTH), (5 * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT );
        television = homePlayer.getSubimage( (3 * Tile.WIDTH), (1 * Tile.HEIGHT), Tile.WIDTH, Tile.HEIGHT );

        crittersBufferedImageNestedArray = ImageLoader.cropSpriteFromSpriteSheet(12, 13,
                56, 56, 1, 1, critterSpriteSheet);
        nabbersBufferedImageNestedArray = ImageLoader.cropSpriteFromSpriteSheet(8, 6,
                68, 64, 1, 2, nabberSpriteSheet);
        overworldSpritesBufferedImageNestedArray = ImageLoader.cropSpriteFromSpriteSheet(8, 44,
                16, 16, 0, 0, overworldSpritesSpriteSheet);
        fontHashMap = FontGrabber.initFont();   //this line must come AFTER battleStateSpriteSheet gets initiated.

        ///////////////////////////////////////////////////////////////////////////
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
        ///////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////
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
        ///////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////
        pikachuDown = new BufferedImage[2];
        pikachuDown[0] = Assets.overworldSpritesBufferedImageNestedArray[38][6];
        pikachuDown[1] = Assets.overworldSpritesBufferedImageNestedArray[39][1];

        pikachuUp = new BufferedImage[2];
        pikachuUp[0] = Assets.overworldSpritesBufferedImageNestedArray[38][7];
        pikachuUp[1] = Assets.overworldSpritesBufferedImageNestedArray[39][2];

        pikachuLeft = new BufferedImage[2];
        pikachuLeft[0] = Assets.overworldSpritesBufferedImageNestedArray[39][0];
        pikachuLeft[1] = Assets.overworldSpritesBufferedImageNestedArray[39][3];

        pikachuRight = new BufferedImage[2];
        pikachuRight[0] = Assets.flipHorizontally( Assets.overworldSpritesBufferedImageNestedArray[39][0] );
        pikachuRight[1] = Assets.flipHorizontally( Assets.overworldSpritesBufferedImageNestedArray[39][3] );
        ///////////////////////////////////////////////////////////////////////////
    }

    public static BufferedImage flipHorizontally(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        return op.filter(image, null);
    }

}
