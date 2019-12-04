package model.states.game.world;

import main.Handler;
import main.gfx.Assets;
import model.tiles.NonSolidTile;
import model.tiles.SolidTile;
import model.tiles.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Lab implements IWorld {

    private Handler handler;

    private Tile[][] worldMapTileCollisionDetection;
    private Map<String, Rectangle> transferPoints;

    public Lab(Handler handler) {
        this.handler = handler;

        worldMapTileCollisionDetection = new Tile[14][12];
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 12; j++) {
                if ((i == 0) || (i == 13) || (j == 0) || (j == 11)) {
                    worldMapTileCollisionDetection[i][j] = new SolidTile(i, j);
                } else {
                    worldMapTileCollisionDetection[i][j] = new NonSolidTile(i, j);
                }
            }
        }

        initTransferPoints();
    } // **** end Lab(Handler) constructor ****

    private void initTransferPoints() {
        transferPoints = new HashMap<String, Rectangle>();

        transferPoints.put( "WorldMap", new Rectangle((5 * Tile.WIDTH), (13 * Tile.HEIGHT), Tile.WIDTH, (Tile.HEIGHT / 2)) );
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