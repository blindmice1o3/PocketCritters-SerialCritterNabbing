package model.entities.critters.levels;

import main.utils.Util;

public class ExpLookUpTable {

    public static int[][] expLookUpTable;

    public static void initExpLookUpTable() {
        String wholeFile = Util.loadFileAsString("res/experience group list.txt");
        System.out.println(wholeFile);
        //expLookUpTable = new int[][];

        String[] lines = wholeFile.split("\n");
        for (String line : lines) {
            System.out.println(line);
            System.out.println("*******************************");
        }
    }

} // **** end ExpLookUpTable class ****