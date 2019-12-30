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

    /*
    Gain formula (https://bulbapedia.bulbagarden.net/wiki/Experience)

    The variables in these formulas evaluate as follows (presented in alphabetical order)...

a is equal to...
Prior to Generation VII:
1 if the fainted Pokémon is wild
1.5 if the fainted Pokémon is owned by a Trainer
In Generation VII: 1
b is the base experience yield of the fainted Pokémon's species; values for the current Generation are listed here
e is equal to...
1.5 if the winning Pokémon is holding a Lucky Egg
1 otherwise
f is equal to...
1.2 if the Pokémon has an Affection of two hearts or more
1 otherwise
L is the level of the fainted/caughtGen VI+ Pokémon
Lp is the level of the victorious Pokémon
p is equal to...
1 if no Exp. Point Power (Pass PowerGen V, O-PowerGen VI, Roto PowerUSUM) is active
If Exp. Point Power [x] is active...
0.5 for ↓↓↓, 0.66 for ↓↓, 0.8 for ↓, 1.2 for ↑, 1.5 for ↑↑, or 2 for ↑↑↑, S, or MAX
1.5 for Roto Exp. Points
s is equal to...
In Generation I...
If Exp. All is not in the player's Bag...
The number of Pokémon that participated in the battle and have not fainted
If Exp. All is in the player's Bag...
Twice the number of Pokémon that participated and have not fainted, when calculating the experience of a Pokémon that participated in battle
Twice the number of Pokémon that participated and have not fainted times the number of Pokémon in the player's party, when calculating the experience given by Exp. All
In Generations II-V...
If no Pokémon in the party is holding an Exp. Share...
The number of Pokémon that participated in the battle and have not fainted
If at least one Pokémon in the party is holding an Exp. Share...
Twice the number of Pokémon that participated and have not fainted, when calculating the experience of a Pokémon that participated in battle
Twice the number of Pokémon holding an Exp. Share, when calculating the experience of a Pokémon holding Exp. Share
In Generation VI and later...
1 when calculating the experience of a Pokémon that participated in battle
2 when calculating the experience of a Pokémon that did not participate in battle and if Exp. Share is turned on
t is equal to...
1 if the winning Pokémon's current owner is its Original Trainer
1.5 if the Pokémon was gained in a domestic trade
Generation IV+ only: 1.7 if the Pokémon was gained in an international trade
v is equal to...
Generation VI+ only: 1.2 if the winning Pokémon is at or past the level where it would be able to evolve, but it has not
1 otherwise
     */

} // **** end ExpLookUpTable class ****