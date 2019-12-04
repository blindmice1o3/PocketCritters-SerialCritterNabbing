package model.states.game.world;

import main.Handler;
import main.gfx.Assets;
import main.utils.TileSpriteToRGBConverter;
import model.tiles.NonSolidTile;
import model.tiles.SolidTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePlayer implements IWorld {

    private Handler handler;

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private Tile[][] worldMapTileCollisionDetection;

    private Map<String, Rectangle> transferPoints;

    public HomePlayer(Handler handler) {
        this.handler = handler;

        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();
        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = initNonWalkableTileSpriteTargets();
        ArrayList<BufferedImage> walkableTileSpriteTargets = initWalkableTileSpriteTargets();
        Tile[][] unborderedTileCollisionDetection = tileSpriteToRGBConverter.generateWorldMapTileCollisionDetection(Assets.homePlayer,
                nonWalkableTileSpriteTargets, walkableTileSpriteTargets);

        worldMapTileCollisionDetection = new Tile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i == 0) || (i == 9) || (j == 0) || (j == 9)) {
                    worldMapTileCollisionDetection[i][j] = new SolidTile(j, i);
                } else {
                    worldMapTileCollisionDetection[i][j] = unborderedTileCollisionDetection[i-1][j-1];
                }
            }
        }

        initTransferPoints();
    } // **** end HomePlayer(Handler) constructor ****

    private ArrayList<BufferedImage> initWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> walkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //NON-SOLID TILES
        //staircase
        walkableTileSpriteTargets.add(
                Assets.homePlayer.getSubimage(112, 16, Tile.WIDTH, Tile.HEIGHT) ); //staircase

        return walkableTileSpriteTargets;
    }

    private ArrayList<BufferedImage> initNonWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //SOLID TILES
        for (int i = 0; i < 8; i++) {
            int xOffset = i * Tile.WIDTH;

            nonWalkableTileSpriteTargets.add(
                    Assets.homePlayer.getSubimage(xOffset, 0, Tile.WIDTH, Tile.HEIGHT) ); //first ROW.
        }

        nonWalkableTileSpriteTargets.add(
                Assets.homePlayer.getSubimage(0, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelfBL
        nonWalkableTileSpriteTargets.add(
                Assets.homePlayer.getSubimage(16, 16, Tile.WIDTH, Tile.HEIGHT) ); //bookShelfBR
        nonWalkableTileSpriteTargets.add(
                Assets.homePlayer.getSubimage(48, 16, Tile.WIDTH, Tile.HEIGHT) ); //tv

        // table, starting at x == 48, y == 64, width/number_of_columns == 2, height/number_of_rows == 2.
        ArrayList<BufferedImage> table = tileSpriteToRGBConverter.pullMultipleTiles(Assets.homePlayer,48, 64, 2, 2);
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

        g.drawImage(Assets.homePlayer,
                0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                /*
                14*Tile.WIDTH, 12*Tile.HEIGHT,
                14*Tile.WIDTH + handler.getGame().getWidth(), 12*Tile.HEIGHT + handler.getGame().getHeight(),
                */
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

} // **** end HomePlayer class ****