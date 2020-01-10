package model.entities.critters.moves;

import main.Handler;
import main.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoveLookUpTable {

    private Handler handler;

    private ArrayList<String> nameBank;
    private ArrayList<String> typeBank;
    private ArrayList<String> categoryBank;
    private ArrayList<String> ppBaseBank;
    private ArrayList<String> powerBank;
    private ArrayList<String> accuracyBank;

    private Map<String, Move> moveLookUpTable;

    public MoveLookUpTable(Handler handler) {
        this.handler = handler;

        initMoveLookUpTable();
    } // **** end MoveLookUpTable(Handler) constructor ***8

    public void initMoveLookUpTable() {
        String rawDataFromTextFile = Util.loadFileAsString("res/listOfMovesGen1CopiedAndPastedFromWebsite.txt");
        System.out.println(rawDataFromTextFile);
        System.out.println("*****************************************************");
        String[] rawDataAfterSplitCalled = rawDataFromTextFile.split("\\s+");
        System.out.println("*****************************************************");

        for (int i = 0; i < rawDataAfterSplitCalled.length; i++) {
            System.out.print(  rawDataAfterSplitCalled[i] );
            System.out.print( " *** " );
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        nameBank = new ArrayList<String>();
        typeBank = new ArrayList<String>();
        categoryBank = new ArrayList<String>();
        ppBaseBank = new ArrayList<String>();
        powerBank = new ArrayList<String>();
        accuracyBank = new ArrayList<String>();

        int indexStart = 10;
        for (int i = indexStart; i < rawDataAfterSplitCalled.length; i++) {
            if (i % 9 == 2) {
                nameBank.add( rawDataAfterSplitCalled[i] );
            } else if (i % 9 == 3) {
                typeBank.add( rawDataAfterSplitCalled[i] );
            } else if (i % 9 == 4) {
                categoryBank.add( rawDataAfterSplitCalled[i] );
            } else if (i % 9 == 6) {
                ppBaseBank.add( rawDataAfterSplitCalled[i] );
            } else if (i % 9 == 7) {
                powerBank.add( rawDataAfterSplitCalled[i] );
            } else if (i % 9 == 8) {
                accuracyBank.add( rawDataAfterSplitCalled[i] );
            }
        }

        System.out.println("nameBank has *" + nameBank.size() + "* elements.");
        System.out.println("typeBank has *" + typeBank.size() + "* elements.");
        System.out.println("categoryBank has *" + categoryBank.size() + "* elements.");
        System.out.println("ppBaseBank has *" + ppBaseBank.size() + "* elements.");
        System.out.println("powerBank has *" + powerBank.size() + "* elements.");
        System.out.println("accuracyBank has *" + accuracyBank.size() + "* elements.");

        for (int i = 0; i < nameBank.size(); i++) {
            System.out.println("-------------------------------");
            System.out.println("@@@ " + (i+1) + " @@@");
            System.out.println("@@@ " + nameBank.get(i) + " @@@");
            System.out.println("@@@ " + typeBank.get(i) + " @@@");
            System.out.println("@@@ " + categoryBank.get(i) + " @@@");
            System.out.println("@@@ ppBase: " + ppBaseBank.get(i) + " @@@");
            System.out.println("@@@ power: " + powerBank.get(i) + " @@@");
            System.out.println("@@@ accuracy: " + accuracyBank.get(i) + " @@@");
            System.out.println("-------------------------------");
        }


        int idNumber = 1;
        //////////////////////////////////////////////////////////////////////
        moveLookUpTable = new HashMap<String, Move>();
        //////////////////////////////////////////////////////////////////////
        for (int i = 0; i < nameBank.size(); i++) {
            Move.Type type = parseType(typeBank.get(i));
            Move.Category category = parseCategory(categoryBank.get(i));
            int ppBase = Util.parseInt( ppBaseBank.get(i) );
            int power = Util.parseInt( powerBank.get(i) );
            int accuracy = Util.parseInt( accuracyBank.get(i) );

            Move move = new Move(idNumber, nameBank.get(i), type, category, ppBase, power, accuracy);
            ///////////////////////////////
            moveLookUpTable.put(nameBank.get(i), move);
            ///////////////////////////////
            idNumber++;
        }

        for (String name : moveLookUpTable.keySet()) {
            System.out.println("-------------------------------");
            System.out.println("@@@ " + name + "'s name: " + moveLookUpTable.get(name).getName());
            System.out.println("@@@ " + name + "'s type: " + moveLookUpTable.get(name).getType());
            System.out.println("@@@ " + name + "'s category: " + moveLookUpTable.get(name).getCategory());
            System.out.println("@@@ " + name + "'s ppBase: " + moveLookUpTable.get(name).getPpBase());
            System.out.println("@@@ " + name + "'s power: " + moveLookUpTable.get(name).getPower());
            System.out.println("@@@ " + name + "'s accuracy: " + moveLookUpTable.get(name).getAccuracy());
            System.out.println("-------------------------------");
        }
    }

    public Move getMove(String name) {
        return moveLookUpTable.get(name);
    }

    private Move.Type parseType(String typeAsString) {
        if (typeAsString.equals("Bug")) {
            return Move.Type.BUG;
        } else if (typeAsString.equals("Dragon")) {
            return Move.Type.DRAGON;
        } else if (typeAsString.equals("Electric")) {
            return Move.Type.ELECTRIC;
        } else if (typeAsString.equals("Fighting")) {
            return Move.Type.FIGHTING;
        } else if (typeAsString.equals("Fire")) {
            return Move.Type.FIRE;
        } else if (typeAsString.equals("Flying")) {
            return Move.Type.FLYING;
        } else if (typeAsString.equals("Ghost")) {
            return Move.Type.GHOST;
        } else if (typeAsString.equals("Grass")) {
            return Move.Type.GRASS;
        } else if (typeAsString.equals("Ground")) {
            return Move.Type.GROUND;
        } else if (typeAsString.equals("Ice")) {
            return Move.Type.ICE;
        } else if (typeAsString.equals("Normal")) {
            return Move.Type.NORMAL;
        } else if (typeAsString.equals("Psychic")) {
            return Move.Type.PSYCHIC;
        } else if (typeAsString.equals("Poison")) {
            return Move.Type.POISON;
        } else if (typeAsString.equals("Rock")) {
            return Move.Type.ROCK;
        } else if (typeAsString.equals("Water")) {
            return Move.Type.WATER;
        } else {
            System.out.println("AHHHHH MoveLookUpTable.parseType(String)'s else block!!!!!");
            return null;
        }
    }

    private Move.Category parseCategory(String categoryAsString) {
        if (categoryAsString.equals("Physical")) {
            return Move.Category.PHYSICAL;
        } else if (categoryAsString.equals("Special")) {
            return Move.Category.SPECIAL;
        } else if (categoryAsString.equals("Status")) {
            return Move.Category.STATUS;
        } else {
            System.out.println("AHHHHH MoveLookUpTable.parseCategory(String)'s else block!!!!!");
            return null;
        }
    }

} // **** end MoveLookUpTable class ****