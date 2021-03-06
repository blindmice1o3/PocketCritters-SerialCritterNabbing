package model.states.game.world.pallet_town;

import main.Handler;
import main.gfx.Assets;
import model.states.game.world.IWorld;
import model.tiles.NonSolidTile;
import model.tiles.SolidTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lab implements IWorld {

    private Handler handler;

    private Tile[][] worldMapTileCollisionDetection;
    private Map<String, Rectangle> transferPoints;

    public Lab(Handler handler) {
        this.handler = handler;

        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = initNonWalkableTileSpriteTargets();
        ArrayList<BufferedImage> walkableTileSpriteTargets = initWalkableTileSpriteTargets();
        Tile[][] unborderedTileCollisionDetection = handler.getTileSpriteToRGBConverter().generateTileMapForCollisionDetection(
                Assets.lab, nonWalkableTileSpriteTargets, walkableTileSpriteTargets);

        worldMapTileCollisionDetection = new Tile[14][12];
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 12; j++) {
                if ((i == 0) || (i == 13) || (j == 0) || (j == 11)) {
                    worldMapTileCollisionDetection[i][j] = new SolidTile(i, j);
                } else {
                    worldMapTileCollisionDetection[i][j] = unborderedTileCollisionDetection[i-1][j-1];
                }
            }
        }

        initTransferPoints();
    } // **** end Lab(Handler) constructor ****

    private ArrayList<BufferedImage> initWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> walkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //NON-SOLID TILES
        /*
        walkableTileSpriteTargets.add(
                Assets.lab.getSubimage(144, 48, Tile.WIDTH, Tile.HEIGHT) ); //trashcan
        */

        return walkableTileSpriteTargets;
    }

    private ArrayList<BufferedImage> initNonWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //SOLID TILES
        for (int i = 0; i < 10; i++) {
            int xOffset = i * Tile.WIDTH;

            nonWalkableTileSpriteTargets.add(
                    Assets.lab.getSubimage(xOffset, 0, Tile.WIDTH, Tile.HEIGHT) ); //first ROW.
        }

        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(0, 16, Tile.WIDTH, Tile.HEIGHT) ); //computerLeft
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(16, 16, Tile.WIDTH, Tile.HEIGHT) ); //computerRight
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(32, 16, Tile.WIDTH, Tile.HEIGHT) ); //tableLeft
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(48, 16, Tile.WIDTH, Tile.HEIGHT) ); //tableRight
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(96, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelf1Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(112, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelf2Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(128, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelf3Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(144, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelf4Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.lab.getSubimage(144, 48, Tile.WIDTH, Tile.HEIGHT) ); //trashcan

        // table, starting at x == 96, y == 48, width/number_of_columns == 3, height/number_of_rows == 1.
        ArrayList<BufferedImage> table = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.lab,96, 48, 3, 1);
        nonWalkableTileSpriteTargets.addAll(
                table
        );

        // bookshelf1, starting at x == 0, y == 96, width/number_of_columns == 4, height/number_of_rows == 2.
        ArrayList<BufferedImage> bookshelf1 = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.lab,0, 96, 4, 2);
        nonWalkableTileSpriteTargets.addAll(
                bookshelf1
        );

        // bookshelf2, starting at x == 96, y == 96, width/number_of_columns == 4, height/number_of_rows == 2.
        ArrayList<BufferedImage> bookshelf2 = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.lab,96, 96, 4, 2);
        nonWalkableTileSpriteTargets.addAll(
                bookshelf2
        );

        return nonWalkableTileSpriteTargets;
    }



    private void initTransferPoints() {
        transferPoints = new HashMap<String, Rectangle>();

        transferPoints.put( "WorldMap", new Rectangle((6 * Tile.WIDTH), (13 * Tile.HEIGHT), Tile.WIDTH, (Tile.HEIGHT / 2)) );
    }

    @Override
    public void tick(long timeElapsed) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());

        g.drawImage(Assets.lab,
                0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                (int)(handler.getGame().getGameCamera().getxOffset0()),
                (int)(handler.getGame().getGameCamera().getyOffset0()),
                (int)(handler.getGame().getGameCamera().getxOffset1()),
                (int)(handler.getGame().getGameCamera().getyOffset1()),
                null);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public Tile[][] getWorldMapTileCollisionDetection() {
        return worldMapTileCollisionDetection;
    }

    @Override
    public Map<String, Rectangle> getTransferPoints() {
        return transferPoints;
    }

} // **** end Lab class ****