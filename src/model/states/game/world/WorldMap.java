package model.states.game.world;

import main.Handler;
import main.gfx.Assets;
import main.utils.TileSpriteToRGBConverter;
import model.tiles.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorldMap implements IWorld {

    private Handler handler;

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private Tile[][] worldMapTileCollisionDetection;

    private Map<String, Rectangle> transferPoints;

    public WorldMap(Handler handler) {
        this.handler = handler;

        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();
        worldMapTileCollisionDetection = tileSpriteToRGBConverter.generateWorldMapTileCollisionDetection(Assets.world);

        initTransferPoints();
    } // **** end WorldMap(Handler) constructor ****

    private void initTransferPoints() {
        transferPoints = new HashMap<String, Rectangle>();

        transferPoints.put( "HomePlayer", new Rectangle(1040, 3248, Tile.WIDTH, Tile.HEIGHT) );
        transferPoints.put( "HomeRival", new Rectangle(1168, 3248, Tile.WIDTH, Tile.HEIGHT) );
        transferPoints.put( "Lab", new Rectangle(1152, 3344, Tile.WIDTH, Tile.HEIGHT) );
        transferPoints.put( "Secret", new Rectangle(1120, 3248, Tile.WIDTH, Tile.HEIGHT) );
    }

    @Override
    public void tick(long timeElapsed) {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.world, 0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
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

} // **** end WorldMap class ****