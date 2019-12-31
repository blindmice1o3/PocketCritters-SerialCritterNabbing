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
            int randomNumberBetweenZeroAndNintyNine = (int)(Math.random() * 100);
            System.out.println("CritterMaker.generateCritter(int, int)'s randomNumberBetweenZeroAndNintyNine: " + randomNumberBetweenZeroAndNintyNine);

            if (randomNumberBetweenZeroAndNintyNine < 30) {
                species = Critter.Species.COLONIAL_MOUSE;
                level = (int)((Math.random() * 3) + 2); //2-4
            } else if ((30 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 100)) {
                species = Critter.Species.MOTLEY_PIGEON;
                level = (int)((Math.random() * 6) + 2); //2-7
            } else {
                species = Critter.Species.COASTAL_GULL;
                level = 6;
            }
        }
        //TODO: the rest of the world.
        //ROUTE02
        else if ( (WorldMap.Route.ROUTE02.getX0() < x) && (x <= (WorldMap.Route.ROUTE02.getX1() + Tile.WIDTH)) &&
                (WorldMap.Route.ROUTE02.getY0() < y) && (y <= (WorldMap.Route.ROUTE02.getY1() + Tile.HEIGHT)) ) {
            int randomNumberBetweenZeroAndNintyNine = (int)(Math.random() * 100);

            if (randomNumberBetweenZeroAndNintyNine < 40) {
                species = Critter.Species.COLONIAL_MOUSE;
                level = (int)((Math.random() * 2) + 3); //3-4
            } else if ((40 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 70)) {
                species = Critter.Species.MOTLEY_PIGEON;
                level = (int)((Math.random() * 5) + 3); //3-7 //TODO: should be 3, 5, 7.
            } else if ((70 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 85)) {
                species = Critter.Species.FEMALE_LAPINE;
                level = (int)((Math.random() * 3) + 4); //4-6 //TODO: should be 4, 6.
            } else if ((85 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 100)) {
                species = Critter.Species.MALE_LAPINE;
                level = (int)((Math.random() * 3) + 4); //4-6 //TODO: should be 4, 6.
            } else {
                species = Critter.Species.COASTAL_GULL;
                level = 6;
            }
        }
        //DEFAULT: Species.COASTAL_GULL, 6
        else {
            System.out.println("CritterMaker.generateCritter(int, int)'s else block!");

            species = Critter.Species.COASTAL_GULL;
            level = 6;
        }

        Critter zygote = new Critter(null, species, level);

        return zygote;
    }
    //!!!!WILL NEED TO SET HANDLER!!!!

}
