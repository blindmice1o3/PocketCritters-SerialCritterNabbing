package model.states.game.world.pallet_town;

import main.Handler;
import main.gfx.Assets;
import model.states.game.world.IWorld;
import model.tiles.SolidTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeRival implements IWorld {

    private Handler handler;

    private Tile[][] worldMapTileCollisionDetection;
    private Map<String, Rectangle> transferPoints;

    public HomeRival(Handler handler) {
        this.handler = handler;

        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = initNonWalkableTileSpriteTargets();
        ArrayList<BufferedImage> walkableTileSpriteTargets = initWalkableTileSpriteTargets();
        Tile[][] unborderedTileCollisionDetection = handler.getTileSpriteToRGBConverter().generateTileMapForCollisionDetection(
                Assets.homeRival, nonWalkableTileSpriteTargets, walkableTileSpriteTargets);

        worldMapTileCollisionDetection = new Tile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i == 0) || (i == 9) || (j == 0) || (j == 9)) {
                    worldMapTileCollisionDetection[i][j] = new SolidTile(i, j);
                } else {
                    worldMapTileCollisionDetection[i][j] = unborderedTileCollisionDetection[i-1][j-1];
                }
            }
        }

        initTransferPoints();
    } // **** end HomeRival(Handler) constructor ****

    private ArrayList<BufferedImage> initWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> walkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //NON-SOLID TILES
        /*
        walkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(32, 48, Tile.WIDTH, Tile.HEIGHT) ); //chair
        */

        return walkableTileSpriteTargets;
    }

    private ArrayList<BufferedImage> initNonWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //SOLID TILES
        for (int i = 0; i < 8; i++) {
            int xOffset = i * Tile.WIDTH;

            nonWalkableTileSpriteTargets.add(
                    Assets.homeRival.getSubimage(xOffset, 0, Tile.WIDTH, Tile.HEIGHT) ); //first ROW.
        }

        nonWalkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(0, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelf1Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(16, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelf2Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(112, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelf3Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(0, 96, Tile.WIDTH, Tile.HEIGHT) ); //pottedTree1Top
        nonWalkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(0, 112, Tile.WIDTH, Tile.HEIGHT) ); //pottedTree1Bottom
        nonWalkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(112, 96, Tile.WIDTH, Tile.HEIGHT) ); //pottedTree2Top
        nonWalkableTileSpriteTargets.add(
                Assets.homeRival.getSubimage(112, 112, Tile.WIDTH, Tile.HEIGHT) ); //pottedTree2Bottom

        // table, starting at x == 48, y == 48, width/number_of_columns == 2, height/number_of_rows == 2.
        ArrayList<BufferedImage> table = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.homeRival,48, 48, 2, 2);
        nonWalkableTileSpriteTargets.addAll(
                table
        );

        return nonWalkableTileSpriteTargets;
    }




    private void initTransferPoints() {
        transferPoints = new HashMap<String, Rectangle>();

        transferPoints.put( "WorldMap", new Rectangle((4 * Tile.WIDTH), (9 * Tile.HEIGHT), (Tile.WIDTH), (Tile.HEIGHT / 2)) );
    }

    @Override
    public void tick(long timeElapsed) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());

        g.drawImage(Assets.homeRival,
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

} // **** end HomeRival class ****