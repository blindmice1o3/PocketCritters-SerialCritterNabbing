package main.utils;

import main.gfx.Assets;
import model.tiles.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TileSpriteToRGBConverter {

    // CONSTANTS
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    // MEMBER VARIABLES
    private static ArrayList<BufferedImage> nonWalkableTileSpriteTargets;
    private static ArrayList<BufferedImage> walkableTileSpriteTargets;
    private static BufferedImage worldBackground;

    public TileSpriteToRGBConverter() {
        //@VERIFIER@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //TODO: This section is for Android Studio's version of PocketCrittersCartridge.
        //TODO: Instead of parsing the world map image for each run, create something similar to rgbTileFarm.
        //String wholeFile = Util.loadFileAsString("tiles_world_map_3x8.txt");
//        String wholeFile = Util.loadFileAsString("tiles_world_map.txt");
//        System.out.println(wholeFile);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    } // **** end TileSpriteToRGBConverter() constructor ****

    public ArrayList<BufferedImage> pullMultipleTiles(BufferedImage spriteSheet, int xInit, int yInit, int numOfCols, int numOfRows) {
        ArrayList<BufferedImage> returner = new ArrayList<BufferedImage>();

        for (int yy = 0; yy < numOfRows; yy++) {
            int yOffset = yy * TILE_HEIGHT;
            for (int xx = 0; xx < numOfCols; xx++) {
                int xOffset = xx * TILE_WIDTH;
                returner.add(
                        spriteSheet.getSubimage((xInit + xOffset), (yInit + yOffset), TILE_WIDTH, TILE_HEIGHT)
                );
            }
        }

        return returner;
    }

    //@GENERATOR_FOR_INDOORS_UNBORDERED@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //TODO: This section is for Android Studio's version of PocketCrittersCartridge.
    //TODO: Instead of parsing the world map image for each run, create something similar to rgbTileFarm.
    /**
     * Invoked within generateTileMapForCollisionDetection().
     */
    private void generateStringMapFileIndoorsUnbordered(int[][][] rgbImage, String fileName) {
        String fileFullName = "C:\\Users\\James\\Downloads\\" + fileName;

        // Create file to store tile map data (similar to Assets.rgbTileFarm, but not with pixels)
        try {
            File tilesWorldMap = new File(fileFullName);
            if (tilesWorldMap.createNewFile()) {
                System.out.println("TileSpriteToRGBConverter.generateStringMapFileIndoorsUnbordered(int[][][], String) CREATED NEW FILE: " + tilesWorldMap.getName());
            } else {
                System.out.println("TileSpriteToRGBConverter.generateStringMapFileIndoorsUnbordered(int[][][], String) FILE ALREADY EXISTS.");
            }
        } catch (IOException e) {
            System.out.println("TileSpriteToRGBConverter.generateStringMapFileIndoorsUnbordered(int[][][], String) AN ERROR OCCURRED WHILE CREATING A FILE.");
            e.printStackTrace();
        }

        // Transcribe the generated int[][][] into String to be stored in a file.
        // (The first two elements [width and height values] are NOT a part of the map)
        StringBuilder sb = new StringBuilder();
        /////////////////////////////////////
        //!!!@@@@@Because it's UNBORDERED, should surround with solid border@@@@@!!!
        int widthInTile = rgbImage[0].length;
        int heightInTile = rgbImage.length;
        System.out.println("widthInTile: " + widthInTile);
        System.out.println("heightInTile: " + heightInTile);
        sb.append((widthInTile+2) + " ");
        sb.append((heightInTile+2) + " \n");
        /////////////////////////////////////

        //HORIZONTAL BORDER (TOP)
        for (int x = 0; x < (widthInTile+2); x++) {
            sb.append("1 ");    //SolidTile (solid border)
        }
        sb.append("\n");

        for (int y = 0; y < heightInTile; y++) {
            sb.append("1 ");    //VERTICAL BORDER (LEFT)

            //ACTUAL IMAGE CONVERSION (NOT BORDER)
            for (int x = 0; x < widthInTile; x++) {
                if (rgbImage[y][x][0] == 1) {
                    sb.append("1 ");    //SolidTile (actual solid tile [e.g. table, shelf])
                } else if (rgbImage[y][x][0] == 0) {
                    sb.append("0 ");    //NonSolidTile
                } else if (rgbImage[y][x][0] == 2) {
                    sb.append("2 ");    //TallGrassTile/UNIQUE_TILES //TODO: indoors?
                } else if (rgbImage[y][x][0] == 9) {
                    sb.append("9 ");    //NullTile (blank tile) //TODO: indoors?
                } else {
                    sb.append("8");     //!!!@@@@@USED FOR ANOMALIES@@@@@!!!
                }
            }

            sb.append("1 \n");  //VERTICAL BORDER (RIGHT)
        }
        //HORIZONTAL BORDER (BOTTOM)
        for (int x = 0; x < (widthInTile+2); x++) {
            sb.append("1 ");    //SolidTile (solid border)
        }

        // Write to file (store the tile map data)
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileFullName);
            fileWriter.write(sb.toString());
            fileWriter.close();
            System.out.println("TileSpriteToRGBConverter.generateStringMapFileIndoorsUnbordered(int[][][], String) SUCCESSFULLY WROTE TO THE FILE.");
        } catch (IOException e) {
            System.out.println("TileSpriteToRGBConverter.generateStringMapFileIndoorsUnbordered(int[][][], String) AN ERROR OCCURED WHILE WRITING TO FILE.");
            e.printStackTrace();
        }
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



    //@GENERATOR@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //TODO: This section is for Android Studio's version of PocketCrittersCartridge.
    //TODO: Instead of parsing the world map image for each run, create something similar to rgbTileFarm.
    /**
     * Invoked within generateTileMapForCollisionDetection().
     */
    private void generateStringMapFileForCollisionDetection(int[][][] rgbImage) {
        for (int ySection = 0; ySection < 10; ySection++) {
            for (int xSection = 0; xSection < 10; xSection++) {
                ////////////////////////////////////////////////////////////////////////////////////////////
                //DIVIDE INTO 10x10 SECTIONS//////////////////////////
                int xStart = xSection * (rgbImage[ySection].length / 10);
                int yStart = ySection * (rgbImage.length / 10);
                int xEnd = (xSection + 1) * (rgbImage[ySection].length / 10);
                int yEnd = (ySection + 1) * (rgbImage.length / 10);
                String fileNameCropped = "tiles_world_map_" + (xSection+1) + "x" + (ySection+1) + ".txt";
                System.out.println(fileNameCropped);
                //////////////////////////////////////////////////////

                // Create file to store tile map data (similar to Assets.rgbTileFarm, but not with pixels)
                String fileFullName = "C:\\Users\\James\\Downloads\\worldmap10x10\\" + fileNameCropped;
                //String fileFullName = "C:\\Users\\James\\Downloads\\tiles_world_map.txt";
                try {
                    File tilesWorldMap = new File(fileFullName);
                    if (tilesWorldMap.createNewFile()) {
                        System.out.println("TileMap.initTilesPocketCritters() CREATED NEW FILE: " + tilesWorldMap.getName());
                    } else {
                        System.out.println("TileMap.initTilesPocketCritters() FILE ALREADY EXISTS.");
                    }
                } catch (IOException e) {
                    System.out.println("TileMap.initTilesPocketCritters() AN ERROR OCCURRED WHILE CREATING A FILE.");
                    e.printStackTrace();
                }

                // Transcribe the generated int[][][] into String to be stored in a file.
                // (The first two elements [width and height values] are NOT a part of the map)
                StringBuilder sb = new StringBuilder();
                //////////////////////////////////////////////////////
                System.out.println("x (aka widthInTiles) == xEnd - xStart: " + (xEnd - xStart));
                System.out.println("y (aka heightInTiles) == yEnd - yStart: " + (yEnd - yStart));
                sb.append((xEnd - xStart) + " ");
                sb.append((yEnd - yStart) + " \n");
//                System.out.println("x (aka widthInTiles) == rgbImage[0].length: " + rgbImage[0].length);
//                System.out.println("y (aka heightInTiles) == rgbImage.length: " + rgbImage.length);
//                sb.append(rgbImage[0].length + " ");
//                sb.append(rgbImage.length + " \n");
                //////////////////////////////////////////////////////
                for (int y = yStart; y < yEnd; y++) {
                    for (int x = xStart; x < xEnd; x++) {
                        //for (int y = 0; y < rgbImage.length; y++) {
                        //    for (int x = 0; x < rgbImage[y].length; x++) {
                        if (rgbImage[y][x][0] == 1) {
                            sb.append("1 ");    //SolidTile
                        } else if (rgbImage[y][x][0] == 0) {
                            sb.append("0 ");    //NonSolidTile
                        } else if (rgbImage[y][x][0] == 2) {
                            sb.append("2 ");    //TallGrassTile
                        } else if (rgbImage[y][x][0] == 9) {
                            sb.append("9 ");    //NullTile (blank tile)
                        }
                    }
                    sb.append("\n");
                }

                // Write to file (store the tile map data)
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(fileFullName);
                    fileWriter.write(sb.toString());
                    fileWriter.close();
                    System.out.println("TileMap.initTilesPocketCritters() SUCCESSFULLY WROTE TO THE FILE.");
                } catch (IOException e) {
                    System.out.println("TileMap.initTilesPocketCritters() AN ERROR OCCURED WHILE WRITING TO FILE.");
                    e.printStackTrace();
                }
                ///////////////////////////////////////////////////////////////////////////////////////////
            }
        }

    }
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public Tile[][] generateTileMapForCollisionDetection(BufferedImage worldBackground,
                                                         ArrayList<BufferedImage> nonWalkableTileSpriteTargets,
                                                         ArrayList<BufferedImage> walkableTileSpriteTargets) {
        //////////////////////////////////////////////////////////////////////////
        this.worldBackground = worldBackground;
        //Used in translateTileSpriteToRGBImage() for its final for-loop.
        this.nonWalkableTileSpriteTargets = nonWalkableTileSpriteTargets;
        //Used in translateTileSpriteToRGBImage() for its final for-loop.
        this.walkableTileSpriteTargets = walkableTileSpriteTargets;
        int[][][] rgbImage = translateTileSpriteToRGBImage();
        //////////////////////////////////////////////////////////////////////////
        int widthNumberOfTile = rgbImage[0].length;
        int heightNumberOfTile = rgbImage.length;

        System.out.println("TileSpriteToRGBConverter.generateTileMapForCollisionDetection(BufferedImage)'s widthWorld: " + widthNumberOfTile);
        System.out.println("TileSpriteToRGBConverter.generateTileMapForCollisionDetection(BufferedImage)'s heightWorld: " + heightNumberOfTile);


        //@INVOKER@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //TODO: This section is for Android Studio's version of PocketCrittersCartridge.
        //TODO: Instead of parsing the world map image for each run, create something similar to rgbTileFarm.
//        if (worldBackground == Assets.world) {
//            generateStringMapFileForCollisionDetection(rgbImage);
//        }

//        if (worldBackground == Assets.homePlayer) {
//            generateStringMapFileIndoorsUnbordered(rgbImage, "tile_home01.txt");
//        }

//        if (worldBackground == Assets.roomPlayer) {
//            generateStringMapFileIndoorsUnbordered(rgbImage, "tile_home02.txt");
//        }

//        if (worldBackground == Assets.homeRival) {
//            generateStringMapFileIndoorsUnbordered(rgbImage, "tile_home_rival.txt");
//        }

//        if (worldBackground == Assets.lab) {
//            generateStringMapFileIndoorsUnbordered(rgbImage, "tile_lab.txt");
//        }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


        Tile[][] returner = new Tile[heightNumberOfTile][widthNumberOfTile];

        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {
                if (rgbImage[y][x][0] == 1) {
                    returner[y][x] = new SolidTile(x, y);
                } else if (rgbImage[y][x][0] == 0) {
                    returner[y][x] = new NonSolidTile(x, y);
                } else if (rgbImage[y][x][0] == 2) {
                    if (this.worldBackground == Assets.world) {
                        returner[y][x] = new TallGrassTile(x, y);
                    } else if (this.worldBackground == Assets.homePlayer) {
                        returner[y][x] = new TelevisionTile(x, y);
                    } else if (this.worldBackground == Assets.roomPlayer) {
                        if (x == 0) {
                            returner[y][x] = new ComputerKeyboardTile(x, y);
                        } else {
                            returner[y][x] = new GameConsoleTile(x, y);
                        }
                    } else if (this.worldBackground == Assets.homeRival) {
                        //filler-space
                        returner[y][x] = new NonSolidTile(x, y);
                    }
                } else if (rgbImage[y][x][0] == 9) {
                    returner[y][x] = new NullTile(x, y);
                }
            }
        }

        return returner;
    }

    public int[][][] translateTileSpriteToRGBImage() {
        int widthNumberOfTile = (worldBackground.getWidth() / TILE_WIDTH);
        int heightNumberOfTile = (worldBackground.getHeight() / TILE_HEIGHT);

        System.out.println("TileSpriteToRGBConverter.translateTileSpriteToRGBImage()'s widthNumberOfTile: " + widthNumberOfTile);
        System.out.println("TileSpriteToRGBConverter.translateTileSpriteToRGBImage()'s heightNumberOfTile: " + heightNumberOfTile);

        int[][][] returner = new int[heightNumberOfTile][widthNumberOfTile][1];

        //checking each tile sprite image (16x16) within the entire world map (widthNumberOfTile by heightNumberOfTile).
        for (int y = 0; y < heightNumberOfTile; y++) {
            for (int x = 0; x < widthNumberOfTile; x++) {

                int xOffset = (x * TILE_WIDTH);
                int yOffset = (y * TILE_HEIGHT);

                boolean blankTile = false;

                //ONLY FOR WORLDMAP (pokemon-gsc-kanto.png)
                if (worldBackground == Assets.world) {

                    blankTile = true;
                    //!!!CHECK TILE - BLANK VERSUS FILLED!!!
                    //check each individual pixel within each of the 64x64-pixeled tile.
                    //@@@@@
                    //label for outer-loop in case I can break out early.
                    //@@@@@
                    outerloop:
                    for (int yy = 0; yy < TILE_HEIGHT; yy++) {
                        for (int xx = 0; xx < TILE_WIDTH; xx++) {

                            int pixel = worldBackground.getRGB((xOffset + xx), (yOffset + yy));
                            int red = (pixel >> 16) & 0xff;
                            int green = (pixel >> 8) & 0xff;
                            int blue = (pixel) & 0xff;

                            //if non-blank tile
                            if (!((red == 255) && (green == 255) && (blue == 255)) &&
                                    //!!!THERE WERE OTHER RGB values FOR BLANK!!!
                                    !((red == 254) && (green == 254) && (blue == 254))) {
                                blankTile = false;
                                //@@@@@@@@@@@@@@
                                break outerloop;
                                //@@@@@@@@@@@@@@
                            }
                        }
                    }

                }

                // non-blank tile will be set to have a value of 0.
                if (!blankTile) {
                    returner[y][x][0] = 0;
                }
                // blank tile will be set to have a value of 9.
                else {
                    returner[y][x][0] = 9;
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
                    if (nonWalkableTileSpriteTargets.size() > 0) {
                        for (BufferedImage tileSpriteTarget : nonWalkableTileSpriteTargets) {

                            //it is the same as one of the target.
                            if (compareTile(worldBackground.getSubimage(xOffset, yOffset, TILE_WIDTH, TILE_HEIGHT),
                                    tileSpriteTarget)) {
                                returner[y][x][0] = 1;
                                break;
                            }
                        }
                    }
                    if (walkableTileSpriteTargets.size() > 0) {
                        for (BufferedImage tileSpriteTarget : walkableTileSpriteTargets) {
                            if (compareTile(worldBackground.getSubimage(xOffset, yOffset, TILE_WIDTH, TILE_HEIGHT),
                                    tileSpriteTarget)) {
                                returner[y][x][0] = 2;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return returner;
    } // **** end BufferedImage translateTileSpriteToRGBImage() method ****

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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
     *          parsed and translated to their corresponding Tile type using IWorld class's
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