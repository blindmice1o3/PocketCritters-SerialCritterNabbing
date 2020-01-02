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

    //TODO: EncounterRate (https://gamefaqs.gamespot.com/gameboy/198314-pokemon-yellow-version-special-pikachu-edition/faqs/64175)
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
                Assets.world.getSubimage(1088, 3184, Tile.WIDTH, Tile.HEIGHT) ); //tall-grass (ROUTE01)
        walkableTileSpriteTargets.add(
                Assets.world.getSubimage(864, 1760, Tile.WIDTH, Tile.HEIGHT) ); //tall-grass (ROUTE02)
        walkableTileSpriteTargets.add(
                Assets.world.getSubimage(800, 1184, Tile.WIDTH, Tile.HEIGHT) ); //tall-grass (Place.VIRIDIAN_FOREST [but more like Town.PEWTER_CITY's south])
        walkableTileSpriteTargets.add(
                Assets.world.getSubimage(1600, 928, Tile.WIDTH, Tile.HEIGHT) ); //tall-grass (ROUTE03)

        return walkableTileSpriteTargets;
    }

    private ArrayList<BufferedImage> initNonWalkableTileSpriteTargets() {
        //SOLID TILES
        ArrayList<BufferedImage> nonWalkableTileSpriteTargets = new ArrayList<BufferedImage>();

        ////Town.PALLET_TOWN///////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(960, 3376, Tile.WIDTH, Tile.HEIGHT) );     //fence-blue (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1024, 3312, Tile.WIDTH, Tile.HEIGHT) );    //fence-brown (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 3312, Tile.WIDTH, Tile.HEIGHT) );    //sign-post (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1024, 3392, Tile.WIDTH, Tile.HEIGHT) );    //NW-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1040, 3392, Tile.WIDTH, Tile.HEIGHT) );    //N-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 3392, Tile.WIDTH, Tile.HEIGHT) );    //NE-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1024, 3408, Tile.WIDTH, Tile.HEIGHT) );    //W-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 3408, Tile.WIDTH, Tile.HEIGHT) );    //E-shore (Town.PALLET_TOWN)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(976, 3152, Tile.WIDTH, Tile.HEIGHT) );     //bush (Town.PALLET_TOWN)

        // building_home (Town.PALLET_TOWN), starting at x == 1024, y == 3216, width/number_of_columns == 4, height/number_of_rows == 3.
        ArrayList<BufferedImage> homeNoDoor = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1024, 3216, 4, 3);
        homeNoDoor.remove(9);
        nonWalkableTileSpriteTargets.addAll(
                homeNoDoor
        );

        // building_home_roofTopOfSecondHome (Town.PALLET_TOWN).
        nonWalkableTileSpriteTargets.addAll(
                handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                        Assets.world,1152, 3216, 4, 1)
        );

        //building_store (Town.PALLET_TOWN), starting at x == 1120, y == 3296, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingStoreNoDoor = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1120, 3296, 6, 4);
        buildingStoreNoDoor.remove(20);
        nonWalkableTileSpriteTargets.addAll(
                buildingStoreNoDoor
        );

        ////Town.VIRIDIAN_CITY/////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(992, 2400, Tile.WIDTH, Tile.HEIGHT) );     //W-building_window (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1040, 2400, Tile.WIDTH, Tile.HEIGHT) );    //E-building_window (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1024, 2416, Tile.WIDTH, Tile.HEIGHT) );    //building_pokecenter_sign (Town.VIRIDIAN_CITY)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1120, 2320, Tile.WIDTH, Tile.HEIGHT) );    //building_pokemart_sign (Town.VIRIDIAN_CITY)

        //building_no_door (Town.VIRIDIAN_CITY), starting at x == 960, y == 2080, width/number_of_columns == 4, height/number_of_rows == 2.
        ArrayList<BufferedImage> buildingNoDoor = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,960, 2080, 4, 2);
        nonWalkableTileSpriteTargets.addAll(
                buildingNoDoor
        );

        //building_door_roof (Town.VIRIDIAN_CITY), starting at x == 960, y == 2144, width/number_of_columns == 4, height/number_of_rows == 1.
        ArrayList<BufferedImage> buildingDoorRoof = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,960, 2144, 4, 1);
        nonWalkableTileSpriteTargets.addAll(
                buildingDoorRoof
        );

        //building_gym_viridian (Town.VIRIDIAN_CITY), starting at x == 1088, y == 2080, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingGymViridian = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1088, 2080, 6, 4);
        buildingGymViridian.remove(22);
        nonWalkableTileSpriteTargets.addAll(
                buildingGymViridian
        );

        ////ROUTE22////////////////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(640, 2512, Tile.WIDTH, Tile.HEIGHT) ); //S-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(688, 2512, Tile.WIDTH, Tile.HEIGHT) ); //SE-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(688, 2496, Tile.WIDTH, Tile.HEIGHT) ); //E-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(640, 2496, Tile.WIDTH, Tile.HEIGHT) ); //CENTER-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(688, 2304, Tile.WIDTH, Tile.HEIGHT) ); //NE-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(624, 2304, Tile.WIDTH, Tile.HEIGHT) ); //N-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(608, 2304, Tile.WIDTH, Tile.HEIGHT) ); //NW-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(608, 2320, Tile.WIDTH, Tile.HEIGHT) ); //W-mountain (ROUTE22)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(576, 2224, Tile.WIDTH, Tile.HEIGHT) ); //SW-mountain (ROUTE22)

        ////ROUTE02////////////////////////////////////////////////////////////////////////////////////////////////////

        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(800, 2000, Tile.WIDTH, Tile.HEIGHT) ); //bush (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(912, 1968, Tile.WIDTH, Tile.HEIGHT) ); //sign-post (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(896, 1984, Tile.WIDTH, Tile.HEIGHT) ); //fence-brown (ROUTE02)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(800, 1680, Tile.WIDTH, Tile.HEIGHT) ); //fence-blue (ROUTE02)

        ////Place.VIRIDIAN_FOREST//////////////////////////////////////////////////////////////////////////////////////

        // house_door_viridian_forest (Place.VIRIDIAN_FOREST), starting at x == 1024, y == 1376, width/number_of_columns == 4, height/number_of_rows == 2.
        ArrayList<BufferedImage> houseDoorViridianForest = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1024, 1376, 4, 2);
        houseDoorViridianForest.remove(5);
        nonWalkableTileSpriteTargets.addAll(
                houseDoorViridianForest
        );

        // building_guard_house_viridian_forest (Place.VIRIDIAN_FOREST), starting at x == 1024, y == 1600, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingGuardHouseViridianForest = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1024, 1600, 6, 4);
        buildingGuardHouseViridianForest.remove(19);
        nonWalkableTileSpriteTargets.addAll(
                buildingGuardHouseViridianForest
        );

        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(960, 1216, Tile.WIDTH, Tile.HEIGHT) );     //NW-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(960, 1232, Tile.WIDTH, Tile.HEIGHT) );     //W-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(960, 1264, Tile.WIDTH, Tile.HEIGHT) );     //SW-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(976, 1264, Tile.WIDTH, Tile.HEIGHT) );     //S-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 1264, Tile.WIDTH, Tile.HEIGHT) );    //SE-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 1248, Tile.WIDTH, Tile.HEIGHT) );    //E-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(976, 1248, Tile.WIDTH, Tile.HEIGHT) );     //CENTER-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(1072, 1216, Tile.WIDTH, Tile.HEIGHT) );    //NE-mountain (Place.VIRIDIAN_FOREST)
        nonWalkableTileSpriteTargets.add(
                Assets.world.getSubimage(976, 1216, Tile.WIDTH, Tile.HEIGHT) );     //N-mountain (Place.VIRIDIAN_FOREST)

        ////Town.PEWTER_CITY///////////////////////////////////////////////////////////////////////////////////////////

        // building_museum_west (Town.PEWTER_CITY), starting at x == 800, y == 608, width/number_of_columns == 8, height/number_of_rows == 6.
        ArrayList<BufferedImage> buildingMuseumWest = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,800, 608, 8, 6);
        nonWalkableTileSpriteTargets.addAll(
                buildingMuseumWest
        );

        // building_museum_east (Town.PEWTER_CITY), starting at x == 928, y == 608, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingMuseumEast = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,928, 608, 6, 4);
        nonWalkableTileSpriteTargets.addAll(
                buildingMuseumEast
        );

        // house_door_north (Town.PEWTER_CITY), starting at x == 1088, y == 768, width/number_of_columns == 4, height/number_of_rows == 2.
        ArrayList<BufferedImage> houseDoorNorth = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,1088, 768, 4, 2);
        houseDoorNorth.remove(5);
        nonWalkableTileSpriteTargets.addAll(
                houseDoorNorth
        );

        // house_door_south (Town.PEWTER_CITY), starting at x == 736, y == 1024, width/number_of_columns == 4, height/number_of_rows == 2.
        ArrayList<BufferedImage> houseDoorSouth = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,736, 1024, 4, 2);
        houseDoorSouth.remove(5);
        nonWalkableTileSpriteTargets.addAll(
                houseDoorSouth
        );

        // building_gym_pewter (Town.PEWTER_CITY), starting at x == 832, y == 800, width/number_of_columns == 6, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingGymPewter = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,832, 800, 6, 4);
        buildingGymPewter.remove(22);
        nonWalkableTileSpriteTargets.addAll(
                buildingGymPewter
        );

        // building_critter_center (Town.PEWTER_CITY),  starting at x == 832, y == 928, width/number_of_columns == 4, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingCritterCenter = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,832, 928, 4, 4);
        buildingCritterCenter.remove(13);
        nonWalkableTileSpriteTargets.addAll(
                buildingCritterCenter
        );

        // building_critter_mart (Town.PEWTER_CITY),  starting at x == 992, y == 800, width/number_of_columns == 4, height/number_of_rows == 4.
        ArrayList<BufferedImage> buildingCritterMart = handler.getTileSpriteToRGBConverter().pullMultipleTiles(
                Assets.world,992, 800, 4, 4);
        buildingCritterMart.remove(13);
        nonWalkableTileSpriteTargets.addAll(
                buildingCritterMart
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