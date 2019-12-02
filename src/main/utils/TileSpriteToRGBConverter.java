package main.utils;

import model.tiles.*;

import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

public class TileSpriteToRGBConverter {

    // CONSTANTS
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    // MEMBER VARIABLES
    private static ArrayList<BufferedImage> nonWalkableTileSpriteTargets;
    private static ArrayList<BufferedImage> walkableTileSpriteTargets;
    private static BufferedImage worldMapAsTileSprites;

    public TileSpriteToRGBConverter() {

    } // **** end TileSpriteToRGBConverter() constructor ****

    private void initWalkableTileSpriteTargets() {
        walkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //NON-SOLID TILES
        //Tall-Grass -> possible PocketMonster Encounter!
        walkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1088, 3184, TILE_WIDTH, TILE_HEIGHT) ); //tall-grass

    }

    private void initNonWalkableTileSpriteTargets() {
        nonWalkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //SOLID TILES
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(960, 3376, TILE_WIDTH, TILE_HEIGHT) ); //fence-blue
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1024, 3312, TILE_WIDTH, TILE_HEIGHT) ); //fence-brown
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1072, 3312, TILE_WIDTH, TILE_HEIGHT) ); //sign-post
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1024, 3392, TILE_WIDTH, TILE_HEIGHT) ); //NW-shore
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1040, 3392, TILE_WIDTH, TILE_HEIGHT) ); //N-shore
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1072, 3392, TILE_WIDTH, TILE_HEIGHT) ); //NE-shore
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1024, 3408, TILE_WIDTH, TILE_HEIGHT) ); //W-shore
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(1072, 3408, TILE_WIDTH, TILE_HEIGHT) ); //E-shore
        nonWalkableTileSpriteTargets.add(
                worldMapAsTileSprites.getSubimage(976, 3152, TILE_WIDTH, TILE_HEIGHT) ); //Bush

        // building_home, starting at x == 1024, y == 3216, width/number_of_columns == 4, height/number_of_rows == 3.
        ArrayList<BufferedImage> homeNoDoor = pullMultipleTiles(1024, 3216, 4, 3);
        homeNoDoor.remove(9);
        nonWalkableTileSpriteTargets.addAll(
            homeNoDoor
        );

        // building_home_roofTopOfSecondHome.
        nonWalkableTileSpriteTargets.addAll(
                pullMultipleTiles(1152, 3216, 4, 1)
        );

        //building_store, starting at x == 1120, y == 3296, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingStoreNoDoor = pullMultipleTiles(1120, 3296, 6, 4);
        buildingStoreNoDoor.remove(20);
        nonWalkableTileSpriteTargets.addAll(
                buildingStoreNoDoor
        );
    }

    private ArrayList<BufferedImage> pullMultipleTiles(int xInit, int yInit, int numOfCols, int numOfRows) {
        ArrayList<BufferedImage> returner = new ArrayList<BufferedImage>();

        for (int yy = 0; yy < numOfRows; yy++) {
            int yOffset = yy * TILE_HEIGHT;
            for (int xx = 0; xx < numOfCols; xx++) {
                int xOffset = xx * TILE_WIDTH;
                returner.add(
                        worldMapAsTileSprites.getSubimage((xInit + xOffset), (yInit + yOffset), TILE_WIDTH, TILE_HEIGHT)
                );
            }
        }

        return returner;
    }

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

    public int[][][] translateTileSpriteToRGBImage() {
        ////////////////////////////////////////////////////////
        initNonWalkableTileSpriteTargets(); //Used in final for-loop.
        initWalkableTileSpriteTargets();
        ////////////////////////////////////////////////////////


        int widthNumberOfTile = (worldMapAsTileSprites.getWidth() / TILE_WIDTH);
        int heightNumberOfTile = (worldMapAsTileSprites.getHeight() / TILE_HEIGHT);

        int[][][] returner = new int[heightNumberOfTile][widthNumberOfTile][1];

        //checking each tile sprite image (64x64) within the entire world map (widthNumberOfTile by heightNumberOfTile).
        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {

                int xOffset = (x * TILE_WIDTH);
                int yOffset = (y * TILE_HEIGHT);
                boolean blankTile = true;

                //!!!CHECK TILE - BLANK VERSUS FILLED!!!
                //check each individual pixel within each of the 64x64-pixeled tile.
                //@@@@@
                //label for outer-loop in case I can break out early.
                //@@@@@
                outerloop:
                for (int yy = 0; yy < TILE_HEIGHT; yy++) {
                    for (int xx = 0; xx < TILE_WIDTH; xx++) {

                        int pixel = worldMapAsTileSprites.getRGB((xOffset + xx), (yOffset + yy));
                        int red = (pixel >> 16) & 0xff;
                        int green = (pixel >> 8) & 0xff;
                        int blue = (pixel) & 0xff;

                        //if non-blank tile
                        if ( !((red == 255) && (green == 255) && (blue == 255)) &&
                                //!!!THERE WERE OTHER RGB values FOR BLANK!!!
                                !((red == 254) && (green == 254) && (blue == 254)) ) {
                            blankTile = false;
                            //@@@@@@@@@@@@@@
                            break outerloop;
                            //@@@@@@@@@@@@@@
                        }
                    }
                }

                // blank tile will be set to have a value of 9.
                if (blankTile) {
                    returner[y][x][0] = 9;
                }
                // non-blank tile will be set to have a value of 0.
                else {
                    returner[y][x][0] = 0;


                    //CHECK NON-BLANK TILE using collection of target (16x16pixels) model.tiles.
                    //target model.tiles - a collection of IWalkable tile sprite (16x16pixels).
                        //returner[y][x][0] = 0;


                }
            }
        }


        //CHECK TARGET TILE IMAGE TO DETERMINE IWALKABLE.
        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {

                int xOffset = (x * TILE_WIDTH);
                int yOffset = (y * TILE_HEIGHT);

                //if non-blank (equals 0)...
                if (returner[y][x][0] == 0) {
                    for (BufferedImage tileSpriteTarget : nonWalkableTileSpriteTargets) {

                        //it is the same as one of the target.
                        if ( compareTile(worldMapAsTileSprites.getSubimage(xOffset, yOffset, TILE_WIDTH, TILE_HEIGHT),
                                tileSpriteTarget) ) {
                            returner[y][x][0] = 1;
                            break;
                        }
                    }
                    for (BufferedImage tileSpriteTarget : walkableTileSpriteTargets) {
                        if ( compareTile(worldMapAsTileSprites.getSubimage(xOffset, yOffset, TILE_WIDTH, TILE_HEIGHT),
                                tileSpriteTarget) ) {
                            returner[y][x][0] = 2;
                            break;
                        }
                    }
                }
            }
        }

        return returner;
    } // **** end BufferedImage translateTileSpriteToRGBImage(BufferedImage) method ****



    private boolean compareTile(BufferedImage tileSpriteImage, BufferedImage tileSpriteTarget) {
        boolean sameImage = true;

        outerloop:
        for (int yy = 0; yy < TILE_HEIGHT; yy++) {
            for (int xx = 0; xx < TILE_WIDTH; xx++) {
                int pixelImage = tileSpriteImage.getRGB(xx, yy);
                int redImage = (pixelImage >> 16) & 0xff;
                int greenImage = (pixelImage >> 8) & 0xff;
                int blueImage = (pixelImage) & 0xff;

                int pixelTarget = tileSpriteTarget.getRGB(xx, yy);
                int redTarget = (pixelTarget >> 16) & 0xff;
                int greenTarget = (pixelTarget >> 8) & 0xff;
                int blueTarget = (pixelTarget) & 0xff;

                //if one pixel is not the same... sameImage set to false.
                if ( !((redImage == redTarget) && (greenImage == greenTarget) && (blueImage == blueTarget)) ) {
                    sameImage = false;
                    break outerloop;
                }
            }
        }

        return sameImage;
    }

    public Tile[][] generateWorldMapTileCollisionDetection(BufferedImage worldMapAsTileSprites) {
        //////////////////////////////////////////////////////////////////////////
        this.worldMapAsTileSprites = worldMapAsTileSprites;
        int[][][] rgbImage = translateTileSpriteToRGBImage();
        //////////////////////////////////////////////////////////////////////////
        int widthWorld = rgbImage[0].length;
        int heightWorld = rgbImage.length;

        Tile[][] returner = new Tile[heightWorld][widthWorld];

        for (int y = 0; y < rgbImage.length; y++) {
            for (int x = 0; x <rgbImage[y].length; x++) {
                if (rgbImage[y][x][0] == 1) {
                    returner[y][x] = new SolidTile(x, y);
                } else if (rgbImage[y][x][0] == 0) {
                    returner[y][x] = new NonSolidTile(x, y);
                } else if (rgbImage[y][x][0] == 2) {
                    returner[y][x] = new TallGrassTile(x, y);
                } else if (rgbImage[y][x][0] == 9) {
                    returner[y][x] = new NullTile(x, y);
                }
            }
        }

        return returner;
    }

    public void testConsoleOutput(int[][][] rgbImage) {
        for (int y = 0; y < rgbImage.length; y++) {
            for (int x = 0; x <rgbImage[y].length; x++) {
                //figure out if NOT last element in its group of columns,
                // otherwise it's the last element in its group of columns.
                if (x != (rgbImage[y].length-1)) {
                    System.out.print(Integer.toString(rgbImage[y][x][0]) + " ");
                } else {
                    System.out.println(Integer.toString(rgbImage[y][x][0]));
                }
            }
        }
    }

} // **** end TileSpriteToRGBConverter class ****