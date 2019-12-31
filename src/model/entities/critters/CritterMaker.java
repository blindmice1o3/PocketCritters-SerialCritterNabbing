package model.entities.critters;

import model.states.game.world.WorldMap;
import model.tiles.Tile;

public class CritterMaker {

    /**
     * //!!!!WILL NEED TO SET HANDLER!!!!
     */
    public static Critter generateCritter(int x, int y) {
        Critter.Species species;
        int level;

        System.out.println("CritterMaker.generateCritter(int, int) x and y: " + x + " and " + y + ".");

        if ( (WorldMap.Route.ROUTE01.getX0() < x) && (x <= (WorldMap.Route.ROUTE01.getX1() + Tile.WIDTH)) &&
                (WorldMap.Route.ROUTE01.getY0() < y) && (y <= (WorldMap.Route.ROUTE01.getY1() + Tile.HEIGHT)) ) {
            int randomNumberBetweenZeroAndNine = (int)(Math.random() * 10);

            System.out.println("CritterMaker.generateCritter(int, int)'s randomNumberBetweenZeroAndNine: " + randomNumberBetweenZeroAndNine);

            species = (randomNumberBetweenZeroAndNine < 3) ? (Critter.Species.COLONIAL_MOUSE) : (Critter.Species.MOTLEY_PIGEON);
            level = (species == Critter.Species.COLONIAL_MOUSE) ? ((int)((Math.random() * 3) + 2)) : ((int)((Math.random() * 6) + 2));

            Critter zygote = new Critter(null, species, level);

            return zygote;
        } else {
            System.out.println("CritterMaker.generateCritter(int, int)'s else block!");

            return new Critter(null, Critter.Species.COASTAL_GULL, 6);
        }
    }
    //!!!!WILL NEED TO SET HANDLER!!!!

}
