package main.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Util stands for utilities. It just contains a bunch of helper functions (methods) that can assist us anywhere in our
 * game.
 */
public class Util {

    // We're going to make a function (method) that loads in a file for us, it takes in String path (the location of the
    // file on the computer).
    public static String loadFileAsString(String path) {
        // StringBuilder is an object that allows you to add characters to a String very easily.
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader( new FileReader(path) );

            String line;
            // Use BufferedReader to read in the next line and assign it to the String variable called line.
            // As long as (while) the next line is NOT null...
            // we append (add to the end) that line (plus a newline character "\n") to our StringBuilder called builder.
            while( (line = br.readLine()) != null ) {
                builder.append(line + "\n");
            }

            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return builder.toString();
    }

/*
    // loadWorld(String) method is responsible for getting the file that we want to load as our world and storing it in
    // the multi-dimensional array called tiles, so we'll know which tiles are in which position.
    private void loadWorld(String path) {
        // Using Utils class's loadFileAsString(String) method to store (in String called file) the location (on our
        // computer) of the world1.txt file.
        String file = loadFileAsString(path);

        // In our world1.txt file, we had split everything up either by a space or a newline.
        // What we want to do is convert all the Strings within our world1.txt into actual numbers (integers).
        // We have to split up the String that we loaded from world1.txt... we have to split up every individual number.

        // We'll take a String[] array (an array of every single number inside of world1.txt) called tokens
        // and call split on our String representation of the loaded world1.txt file (the String variable called file).

        // @@@@ To split it on any amount of white space (so a space character or a newline character) "\\s+" @@@@
        String[] tokens = file.split("\\s+");

        // What we just did was we took our file and we splitted each number into its own little String and stored each
        // of them into an index in the tokens array. That way we can access all of them separately.

        // Now we have to set the width and height of our world. (This is in terms of number of Tiles, NOT pixels.)
        width = parseInt( tokens[0] );    // The first number in our world1.txt file (aka first number in tokens).
        height = parseInt( tokens[1] );
        // Next will be WHERE the player will SPAWN at the start of the handler.
        spawnX = parseInt( tokens[2] );
        spawnY = parseInt( tokens[3] );   // We just read in the first four numbers of our file (world1.txt).


        // Now every single number after this is actual world data (id numbers of Tiles).
        // We have to read all that data into the World class's int[][] tiles array.

        // Initialize the multi-dimensional int array called tiles by the world's width and height (number of Tiles).
        tiles = new int[width][height];
        // Use nested for-loops (just like in the render() method) to store the int id into the tiles array.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Here's where it gets a bit tricky. First, it has to be an integer, so use the Utils class's parseInt().

                // Parsing something from the tokens array into an int, but the tokens is a 1-D array while tiles is 2-D.
                // We have to convert the x and y for-loop position into whatever proper position it is into the proper
                // position of our tokens array.
                // @@@@ In parentheses... (x + y * width) and that will appropriately convert the x and y of the
                // for-loop into the 1-dimensional array index. BUT ALSO HAVE TO ADD 4 because we are setting the
                // first 4 elements in the world1.txt file (array indexes [0], [1], [2], [3]) into variables width,
                // height, spawnX, spawnY... they're not actual world data!!!! @@@@
                tiles[x][y] = parseInt( tokens[ (x + (y * width)) + 4] );
            }
        }
    }
*/

    // This second helper method is going to take in a String (like a String with the number 5 in it, "5") and it's
    // going to convert that into an integer value of 5 ("5" -> 5).
    public static int parseInt(String number) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < number.length(); i++) {
                if ( !number.substring(i, (i+1)).equals(",") ) {
                    sb.append(number.substring(i, (i+1)));
                }
            }

            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException ex) {
            // If we try to pass in a String that is not a number (like "ABC"), it'll throw an error.
            ex.printStackTrace();   // Print the error to the screen (that way we know something happened).
            // @@@@ But we also want our program to NOT crash, so we'll return 0 as default. @@@@
            return 0;
        }
    }

} // **** end Util class ****
