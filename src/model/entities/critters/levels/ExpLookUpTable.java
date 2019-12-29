package model.entities.critters.levels;

import main.utils.Util;

import java.util.HashMap;
import java.util.Map;

public class ExpLookUpTable {

    public static int[][] expLookUpTable;

    public static Map<Integer, Integer> expByLevelFast;
    public static Map<Integer, Integer> expByLevelMediumFast;
    public static Map<Integer, Integer> expByLevelMediumSlow;
    public static Map<Integer, Integer> expByLevelSlow;

    //called in Game.init().
    public static void initExpLookUpTable() {
        String wholeFile = Util.loadFileAsString("res/experience group list.txt");
        System.out.println(wholeFile);
        //expLookUpTable = new int[][];

        String[] lines = wholeFile.split("\n");
        for (String line : lines) {
            System.out.println(line);
            System.out.println("*******************************");
        }

        //starting at index of 3 because 0-2 do NOT have data (they're labels used for organization of the data).
        expByLevelFast = new HashMap<Integer, Integer>();
        expByLevelMediumFast = new HashMap<Integer, Integer>();
        expByLevelMediumSlow = new HashMap<Integer, Integer>();
        expByLevelSlow = new HashMap<Integer, Integer>();
        int level = 1;
        int xFast = 1;          //skipping Erratic and Fluctuating.
        int xMediumFast = 2;    //skipping Erratic and Fluctuating.
        int xMediumSlow = 3;    //skipping Erratic and Fluctuating.
        int xSlow = 4;          //skipping Erratic and Fluctuating.
        for (int y = 3; y < lines.length; y++) {
            String[] oneLine = lines[y].split("\\s+");

            int value = Util.parseInt(oneLine[xFast]);
            expByLevelFast.put(level, value);

            value = Util.parseInt(oneLine[xMediumFast]);
            expByLevelMediumFast.put(level, value);

            value = Util.parseInt(oneLine[xMediumSlow]);
            expByLevelMediumSlow.put(level, value);

            value = Util.parseInt(oneLine[xSlow]);
            expByLevelSlow.put(level, value);

            level++;
        }

        //verifying expGroup.SLOW grabbed the correct values.
        for (int i = 1; i < 100; i++) {
            System.out.println( "Level " + i + " for expGroupSlow requires: " + expByLevelSlow.get(i) + " exp points.");
        }
    }

} // **** end ExpLookUpTable class ****