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

public class HomePlayer implements IWorld {

    private Handler handler;

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private Tile[][] worldMapTileCollisionDetection;

    private Map<String, Rectangle> transferPoints;

    public HomePlayer(Handler handler) {
        this.handler = handler;

        //tileSpriteToRGBConverter = new TileSpriteToRGBConverter();
        //worldMapTileCollisionDetection = tileSpriteToRGBConverter.generateWorldMapTileCollisionDetection(Assets.homePlayer);

        worldMapTileCollisionDetection = new Tile[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i == 0) || (i == 7) || (j == 0) || (j == 7)) {
                    worldMapTileCollisionDetection[i][j] = new SolidTile(j, i);
                } else {
                    worldMapTileCollisionDetection[i][j] = new NonSolidTile(j, i);
                }
            }
        }
        initTransferPoints();
    } // **** end HomePlayer(Handler) constructor ****

    private void initTransferPoints() {
        transferPoints = new HashMap<String, Rectangle>();

        transferPoints.put( "WorldMap", new Rectangle(32, 112, (2 * Tile.WIDTH), Tile.HEIGHT) );
    }

    @Override
    public void tick(long timeElapsed) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());

        g.drawImage(Assets.homePlayer,
                10*Tile.WIDTH, 11*Tile.HEIGHT,
                10*Tile.WIDTH + handler.getGame().getWidth(), 11*Tile.HEIGHT + handler.getGame().getHeight(),
                (int)(handler.getGame().getGameCamera().getxOffset0()),
                (int)(handler.getGame().getGameCamera().getyOffset0()),
                (int)(handler.getGame().getGameCamera().getxOffset1()),
                (int)(handler.getGame().getGameCamera().getyOffset1()),
                /*
                (14 * Tile.WIDTH),
                (12 * Tile.HEIGHT),
                ((14 * Tile.WIDTH) + handler.getGame().getWidth()) ,
                ((12 * Tile.HEIGHT) + handler.getGame().getHeight()),
                */
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