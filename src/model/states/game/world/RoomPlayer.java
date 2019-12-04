package model.states.game.world;

import main.Handler;
import main.gfx.Assets;
import main.utils.TileSpriteToRGBConverter;
import model.tiles.NonSolidTile;
import model.tiles.SolidTile;
import model.tiles.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RoomPlayer implements IWorld {

    private Handler handler;

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private Tile[][] worldMapTileCollisionDetection;

    private Map<String, Rectangle> transferPoints;

    public RoomPlayer(Handler handler) {
        this.handler = handler;

        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();

        worldMapTileCollisionDetection = new Tile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i == 0) || (i == 9) || (j == 0) || (j == 9)) {
                    worldMapTileCollisionDetection[i][j] = new SolidTile(j, i);
                } else {
                    worldMapTileCollisionDetection[i][j] = new NonSolidTile(j, i);
                }
            }
        }

        initTransferPoints();
    } // **** end RoomPlayer(Handler) constructor ****

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