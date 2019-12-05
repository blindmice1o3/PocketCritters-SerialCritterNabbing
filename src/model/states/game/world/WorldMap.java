package model.states.game.world;

import main.Handler;
import main.gfx.Assets;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldMap implements IWorld {

    public enum City {
        PALLET_TOWN(960, 3184, 1279, 3455),
        VIRIDIAN_CITY(640, 2016, 1279, 2591),
        PEWTER_CITY(640, 576, 1279, 1151);

        private final int x0, y0, x1, y1;

        City(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        public int getX0() { return x0; }
        public int getY0() { return y0; }
        public int getX1() { return x1; }
        public int getY1() { return y1; }
    }

    public enum Route {
        ROUTE01(960, 2592, 1279, 3183),
        ROUTE02(800, 1664, 1119, 2015),
        ROUTE22(288, 2144, 639, 2431),
        ROUTE23(0, 2144, 287, 2431),
        ROUTE03(1280, 736, 2191, 1023);

        private final int x0, y0, x1, y1;

        Route(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        public int getX0() { return x0; }
        public int getY0() { return y0; }
        public int getX1() { return x1; }
        public int getY1() { return y1; }
    }

    public enum Place {
        VIRIDIAN_FOREST(800, 1152, 1119, 1663) /*,
        MT_MOON */;

        private final int x0, y0, x1, y1;

        Place(int x0, int y0, int x1, int y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
        }

        public int getX0() { return x0; }
        public int getY0() { return y0; }
        public int getX1() { return x1; }
        public int getY1() { return y1; }
    }

    private Handler handler;

    private Tile[][] worldMapTileCollisionDetection;
    private Map<String, Rectangle> transferPoints;

    public WorldMap(Handler handler) {
        this.handler = handler;

        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = initNonWalkableTileSpriteTargets();
        ArrayList<BufferedImage> walkableTileSpriteTargets = initWalkableTileSpriteTargets();
        worldMapTileCollisionDetection = handler.getTileSpriteToRGBConverter().generateTileMapForCollisionDetection(
                Assets.world, nonWalkableTileSpriteTargets, walkableTileSpriteTargets);

        initTransferPoints();
    } // **** end WorldMap(Handler) constructor ****

    private ArrayList<BufferedImage> initWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> walkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //NON-SOLID TILES
        //Tall-Grass -> possible PocketMonster Encounter!
        walkableTileSpriteTargets.add(
                Assets.world.getSubimage(1088, 3184, Tile.WIDTH, Tile.HEIGHT) ); //tall-grass

        return walkableTileSpriteTargets;
    }

    private ArrayList<BufferedImage> initNonWalkableTileSpriteTargets() {
        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = new ArrayList<BufferedImage>();

        //SOLID TILES
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(960, 3376, Tile.WIDTH, Tile.HEIGHT) ); //fence-blue
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1024, 3312, Tile.WIDTH, Tile.HEIGHT) ); //fence-brown
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 3312, Tile.WIDTH, Tile.HEIGHT) ); //sign-post
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1024, 3392, Tile.WIDTH, Tile.HEIGHT) ); //NW-shore
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1040, 3392, Tile.WIDTH, Tile.HEIGHT) ); //N-shore
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 3392, Tile.WIDTH, Tile.HEIGHT) ); //NE-shore
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1024, 3408, Tile.WIDTH, Tile.HEIGHT) ); //W-shore
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 3408, Tile.WIDTH, Tile.HEIGHT) ); //E-shore
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(976, 3152, Tile.WIDTH, Tile.HEIGHT) ); //Bush

        // building_home, starting at x == 1024, y == 3216, width/number_of_columns == 4, height/number_of_rows == 3.
        ArrayList<BufferedImage> homeNoDoor = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1024, 3216, 4, 3);
        homeNoDoor.remove(9);
        nonWalkableTileSpriteTargets.addAll(
                homeNoDoor
        );

        // building_home_roofTopOfSecondHome.
        nonWalkableTileSpriteTargets.addAll(
                handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                        Assets.world,1152, 3216, 4, 1)
        );

        //building_store, starting at x == 1120, y == 3296, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingStoreNoDoor = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1120, 3296, 6, 4);
        buildingStoreNoDoor.remove(20);
        nonWalkableTileSpriteTargets.addAll(
                buildingStoreNoDoor
        );

        return nonWalkableTileSpriteTargets;
    }



    private void initTransferPoints() {
        transferPoints = new HashMap<String, Rectangle>();

        transferPoints.put( "HomePlayer", new Rectangle(1040, 3248, Tile.WIDTH, Tile.HEIGHT) );
        transferPoints.put( "HomeRival", new Rectangle(1168, 3248, Tile.WIDTH, Tile.HEIGHT) );
        transferPoints.put( "Lab", new Rectangle(1152, 3344, Tile.WIDTH, Tile.HEIGHT) );
        //transferPoints.put( "Secret", new Rectangle(1120, 3248, Tile.WIDTH, Tile.HEIGHT) );
    }

    @Override
    public void tick(long timeElapsed) {

    }

    private float xPlayerPriorTransfer, yPlayerPriorTransfer,
            x0GameCameraPriorTransfer, y0GameCameraPriorTransfer,
            x1GameCameraPriorTransfer, y1GameCameraPriorTransfer;
    public void recordLocationPriorTransfer() {
        xPlayerPriorTransfer = handler.getGame().getPlayer().getX();
        yPlayerPriorTransfer = handler.getGame().getPlayer().getY();
        x0GameCameraPriorTransfer = handler.getGameCamera().getxOffset0();
        y0GameCameraPriorTransfer = handler.getGameCamera().getyOffset0();
        x1GameCameraPriorTransfer = handler.getGameCamera().getxOffset1();
        y1GameCameraPriorTransfer = handler.getGameCamera().getyOffset1();
    }
    public void loadLocationPriorTransfer() {
        handler.getGame().getPlayer().setX((int)xPlayerPriorTransfer);
        handler.getGame().getPlayer().setY((int)yPlayerPriorTransfer);
        handler.getGameCamera().setxOffset0(x0GameCameraPriorTransfer);
        handler.getGameCamera().setyOffset0(y0GameCameraPriorTransfer);
        handler.getGameCamera().setxOffset1(x1GameCameraPriorTransfer);
        handler.getGameCamera().setyOffset1(y1GameCameraPriorTransfer);
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