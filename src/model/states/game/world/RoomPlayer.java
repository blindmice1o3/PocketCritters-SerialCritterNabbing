package model.states.game.world;

import main.Handler;
import main.gfx.Assets;
import model.tiles.SolidTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomPlayer implements IWorld {

    private Handler handler;

    private Tile[][] worldMapTileCollisionDetection;
    private Map<String, Rectangle> transferPoints;

    public RoomPlayer(Handler handler) {
        this.handler = handler;

        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = initNonWalkableTileSpriteTargets();
        ArrayList<BufferedImage> walkableTileSpriteTargets = initWalkableTileSpriteTargets();
        Tile[][] unborderedTileCollisionDetection = handler.getTileSpriteToRGBConverter().generateTileMapForCollisionDetection(
                Assets.roomPlayer, nonWalkableTileSpriteTargets, walkableTileSpriteTargets);

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
    } // **** end RoomPlayer(Handler) constructor ****

    private ArrayList<BufferedImage> initWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> walkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //NON-SOLID TILES (!!!nevermind the non-solid part!!! just SPECIAL TILES)
        walkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(0, 16, Tile.WIDTH, Tile.HEIGHT) ); //computerKeyboard
        walkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(48, 80, Tile.WIDTH, Tile.HEIGHT) ); //gameConsole

        return walkableTileSpriteTargets;
    }

    private ArrayList<BufferedImage> initNonWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //SOLID TILES
        for (int i = 0; i < 8; i++) {
            int xOffset = i * Tile.WIDTH;

            nonWalkableTileSpriteTargets.add(
                    Assets.roomPlayer.getSubimage(xOffset, 0, Tile.WIDTH, Tile.HEIGHT) ); //first ROW.
        }

        nonWalkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(16, 16, Tile.WIDTH, Tile.HEIGHT) ); //tableBL
        nonWalkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(32, 16, Tile.WIDTH, Tile.HEIGHT) ); //tableBR
        nonWalkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(48, 64, Tile.WIDTH, Tile.HEIGHT) ); //tv
        nonWalkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(0, 96, Tile.WIDTH, Tile.HEIGHT) ); //bedTop
        nonWalkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(0, 112, Tile.WIDTH, Tile.HEIGHT) ); //bedBottom
        nonWalkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(96, 96, Tile.WIDTH, Tile.HEIGHT) ); //pottedTreeTop
        nonWalkableTileSpriteTargets.add(
                Assets.roomPlayer.getSubimage(96, 112, Tile.WIDTH, Tile.HEIGHT) ); //pottedTreeBottom

        return nonWalkableTileSpriteTargets;
    }



    private void initTransferPoints() {
        transferPoints = new HashMap<String, Rectangle>();

        transferPoints.put( "HomePlayer", new Rectangle((9 * Tile.WIDTH), (2 * Tile.HEIGHT), (Tile.WIDTH / 2), (Tile.HEIGHT / 2)) );
    }

    @Override
    public void tick(long timeElapsed) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());

        g.drawImage(Assets.roomPlayer,
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

} // **** end RoomPlayer class ****