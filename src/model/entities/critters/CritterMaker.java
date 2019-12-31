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

        //ROUTE01
        if ( (WorldMap.Route.ROUTE01.getX0() < x) && (x <= (WorldMap.Route.ROUTE01.getX1() + Tile.WIDTH)) &&
                (WorldMap.Route.ROUTE01.getY0() < y) && (y <= (WorldMap.Route.ROUTE01.getY1() + Tile.HEIGHT)) ) {
            int randomNumberBetweenZeroAndNine = (int)(Math.random() * 10);
            System.out.println("CritterMaker.generateCritter(int, int)'s randomNumberBetweenZeroAndNine: " + randomNumberBetweenZeroAndNine);

            if (randomNumberBetweenZeroAndNine < 3) {
                species = Critter.Species.COLONIAL_MOUSE;
                level = (int)((Math.random() * 3) + 2); //2-4
            } else if (randomNumberBetweenZeroAndNine >= 3) {
                species = Critter.Species.MOTLEY_PIGEON;
                level = (int)((Math.random() * 6) + 2); //2-7
            } else {
                species = Critter.Species.COASTAL_GULL;
                level = 6;
            }

            Critter zygote = new Critter(null, species, level);

            return zygote;
        }
        //TODO: the rest of the world.
        //ROUTE02
        else if ( (WorldMap.Route.ROUTE02.getX0() < x) && (x <= (WorldMap.Route.ROUTE02.getX1() + Tile.WIDTH)) &&
                (WorldMap.Route.ROUTE02.getY0() < y) && (y <= (WorldMap.Route.ROUTE02.getY1() + Tile.HEIGHT)) ) {

            species = Critter.Species.COASTAL_GULL;
            level = 6;

            Critter zygote = new Critter(null, species, level);

            return zygote;
        }
        //DEFAULT: Species.COASTAL_GULL, 6
        else {
            System.out.println("CritterMaker.generateCritter(int, int)'s else block!");

            return new Critter(null, Critter.Species.COASTAL_GULL, 6);
        }
    }
    //!!!!WILL NEED TO SET HANDLER!!!!

}
