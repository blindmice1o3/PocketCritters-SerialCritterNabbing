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
        //ROUTE22
        else if ( (WorldMap.Route.ROUTE22.getX0() < x) && (x <= (WorldMap.Route.ROUTE22.getX1() + Tile.WIDTH)) &&
                (WorldMap.Route.ROUTE22.getY0() < y) && (y <= (WorldMap.Route.ROUTE22.getY1() + Tile.HEIGHT)) ) {
            int randomNumberBetweenZeroAndNintyNine = (int)(Math.random() * 100);

            if (randomNumberBetweenZeroAndNintyNine < 35) {
                species = Critter.Species.MALE_LAPINE;
                level = (int)((Math.random() * 3) + 2); //2-4 //TODO: should be 2, 4.
            } else if ((35 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 60)) {
                species = Critter.Species.FEMALE_LAPINE;
                level = (int)((Math.random() * 3) + 2); //2-4 //TODO: should be 2, 4.
            } else if ((60 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 80)) {
                species = Critter.Species.STONE_MONKEY;
                level = (int)((Math.random() * 3) + 3); //3-5 //TODO: should be 3, 5.
            } else if ((80 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 90)) {
                species = Critter.Species.COLONIAL_MOUSE;
                level = 3; //3
            } else if ((90 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 100)) {
                species = Critter.Species.COASTAL_GULL;
                level = (int)((Math.random() * 5) + 2); //2-6 //TODO: should be 2, 4, 6.
            } else {
                species = Critter.Species.COASTAL_GULL;
                level = 6;
            }
        }
        //TODO: the rest of the world.
        //VIRIDIAN_FOREST (even though it's more like PEWTER_CITY [south])
        else if ( (WorldMap.Place.VIRIDIAN_FOREST.getX0() < x) && (x <= (WorldMap.Place.VIRIDIAN_FOREST.getX1() + Tile.WIDTH)) &&
                (WorldMap.Place.VIRIDIAN_FOREST.getY0() < y) && (y <= (WorldMap.Place.VIRIDIAN_FOREST.getY1() + Tile.HEIGHT)) ) {
            int randomNumberBetweenZeroAndNintyNine = (int)(Math.random() * 100);

            if (randomNumberBetweenZeroAndNintyNine < 55) {
                species = Critter.Species.HOOKAH_CATERPILLAR;
                level = (int)((Math.random() * 4) + 3); //3-6
            } else if ((55 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 79)) {
                species = Critter.Species.MOTLEY_PIGEON;
                level = (int)((Math.random() * 5) + 4); //4-8 //TODO: should be 4, 6, 8.
            } else if ((79 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 99)) {
                species = Critter.Species.GRUB_LARVAE; //TODO: should be HOOKAH_CATERPILLAR's evolved form.
                level = (int)((Math.random() * 3) + 4); //4-6 //TODO: should be 4, 6.
            } else if ((99 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 100)) {
                species = Critter.Species.THUNDER_MOUSE; //TODO: should be MOTLEY_PIGEON's evolved form.
                level = 9; //9
            } else {
                species = Critter.Species.COASTAL_GULL;
                level = 6;
            }
        }
        //ROUTE03
        else if ( (WorldMap.Route.ROUTE03.getX0() < x) && (x <= (WorldMap.Route.ROUTE03.getX1() + Tile.WIDTH)) &&
                (WorldMap.Route.ROUTE03.getY0() < y) && (y <= (WorldMap.Route.ROUTE03.getY1() + Tile.HEIGHT)) ) {
            int randomNumberBetweenZeroAndNintyNine = (int)(Math.random() * 100);

            if (randomNumberBetweenZeroAndNintyNine < 55) {
                species = Critter.Species.COASTAL_GULL;
                level = (int)((Math.random() * 5) + 8); //8-12
            } else if ((55 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 70)) {
                species = Critter.Species.COLONIAL_MOUSE;
                level = (int)((Math.random() * 3) + 10); //10-12 //TODO: should be 10, 12.
            } else if ((70 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 85)) {
                species = Critter.Species.SAND_MOUSE;
                level = (int)((Math.random() * 3) + 8); //8-10 //TODO: should be 8, 10.
            } else if ((85 <= randomNumberBetweenZeroAndNintyNine) && (randomNumberBetweenZeroAndNintyNine < 100)) {
                species = Critter.Species.STONE_MONKEY;
                level = 9; //9
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
