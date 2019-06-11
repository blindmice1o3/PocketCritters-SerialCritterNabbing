package utils;

import model.Assets;

import java.awt.image.BufferedImage;

public class TileSpriteToRGBConverter {

    /**
     *  Load world by reading RGB values from BufferedImage object and writing to int[][][].
     *
     * @param image the BufferedImage object from which the game world (map/level/tile-layout) is modeled.
     *              Since the actual RGB values (e.g. red == 255, green == 0, and blue == 0) are largely
     *              arbitrary, some of the values can be utilized as meta-data.
     * @return a multi-dimensional array of int values which represent the game's world (map/level) as
     *          if it was a two-dimensional array of int (i.e. int[widthInTiles][heightInTiles]),
     *          where each element of the two-dimensional array is a reference to a third array of int
     *          values (i.e. int[3]), an array of the RGB values of each individual pixels from
     *          BufferedImage image. The values inside the array of int[] representing RGB will be
     *          parsed and translated to their corresponding Tile type using World class's
     *          translateTileFromRGB(int[][][] rgbArrayRelativeToMap) method.
     */
    public static int[][][] transcribeRGBFromImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int[][][] rgbArrayRelativeToMap = new int[width][height][3];

        int pixel;
        int red;
        int green;
        int blue;

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                pixel = image.getRGB(xx, yy);
                red = (pixel >> 16) & 0xff;
                green = (pixel >> 8) & 0xff;
                blue = (pixel) & 0xff;

                rgbArrayRelativeToMap[xx][yy][0] = red;
                rgbArrayRelativeToMap[xx][yy][1] = green;
                rgbArrayRelativeToMap[xx][yy][2] = blue;
            }
        }

        return rgbArrayRelativeToMap;
    } // **** end int[][][] transcribeRGBFromImage(BufferedImage) method ****

    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;
    public int[][][] translateTileSpriteToRGBImage() {
        BufferedImage tileSpriteImage = ImageLoader.loadImage("/pokemon-gsc-kanto.png");
        int widthNumberOfTile = (tileSpriteImage.getWidth() / TILE_WIDTH);
        int heightNumberOfTile = (tileSpriteImage.getHeight() / TILE_HEIGHT);

        int[][][] returner = new int[heightNumberOfTile][widthNumberOfTile][1];

        //checking each tile sprite image (64x64) within the entire world map (widthNumberOfTile by heightNumberOfTile).
        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {

                int xOffset = (x * TILE_WIDTH);
                int yOffset = (y * TILE_HEIGHT);
                //(figure out if blank tile or not).
                boolean filledTile = true;
                //check each individual pixel within each of the 64x64-pixeled tile.

                //@@@@@
                //label for outer-loop in case I can break out early.
                //@@@@@
                outerloop:
                for (int yy = 0; yy < TILE_HEIGHT; yy++) {
                    for (int xx = 0; xx < TILE_WIDTH; xx++) {

                        int pixel = tileSpriteImage.getRGB((xOffset + xx), (yOffset + yy));
                        int red = (pixel >> 16) & 0xff;
                        int green = (pixel >> 8) & 0xff;
                        int blue = (pixel) & 0xff;

                        //if blank tile OR not other blank tile
                        if ( ((red == 255) && (green == 255) && (blue == 255)) ||
                                //!!!THERE WERE OTHER RGB values FOR BLANK!!!
                                ((red == 254) && (green == 254) && (blue == 254)) ) {
                            filledTile = false;
                            //@@@@@@@@@@@@@@
                            break outerloop;
                            //@@@@@@@@@@@@@@
                        }
                    }
                }

                // filled tile will be set to have a value of 1.
                if (filledTile) {
                    returner[y][x][0] = 1;
                }
                // blank tile will be set to have a value of 0.
                else {
                    returner[y][x][0] = 0;
                }
            }
        }

        return returner;
    } // **** end BufferedImage translateTileSpriteToRGBImage(BufferedImage) method ****

    public void testConsoleOutput(int[][][] rgbImage) {
        for (int y = 0; y < rgbImage.length; y++) {
            for (int x = 0; x <rgbImage[y].length; x++) {
                if (x != (rgbImage[y].length-1)) {
                    System.out.print(Integer.toString(rgbImage[y][x][0]) + " ");
                } else {
                    System.out.println(Integer.toString(rgbImage[y][x][0]));
                }
            }
        }
    }

} // **** end TileSpriteToRGBConverter class ****