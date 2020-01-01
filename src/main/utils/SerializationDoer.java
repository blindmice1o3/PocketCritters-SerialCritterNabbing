package main.utils;

import main.Handler;
import main.gfx.GameCamera;
import model.entities.Player;
import model.entities.critters.Critter;
import model.entities.nabbers.INabber;
import model.entities.nabbers.James;
import model.entities.nabbers.Jessie;
import model.items.Item;
import model.states.game.GameState;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class SerializationDoer {

    private static int counter = 1;
    private String defaultName = "default";

    private Handler handler;

    public SerializationDoer(Handler handler) {
        this.handler = handler;
    } // **** end SerializationDoer(Handler) constructor ****

    public void saveWriteToFile() {
        //counter++;

        String counterAsString = String.format("%05d", counter);
        String concatenatedDefaultName = defaultName + counterAsString + ".bin";

        //Try-with-resources (automatically calls close() method of fs... java7+).
        try ( FileOutputStream fs = new FileOutputStream(concatenatedDefaultName) ) {
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject( handler.getGame().getGameCamera() );
            os.writeObject( handler.getGame().getPlayer() );
            os.writeObject( ((GameState)handler.getStateManager().getIState("GameState")).getCritterStorageSystem() );
            os.writeObject( handler.getGame().getPlayer().getNabberList() );
            os.writeObject( handler.getGame().getPlayer().getCritterBeltList() );
            os.writeObject( handler.getGame().getPlayer().getInventory() );
//            os.writeObject( handler.getGame().getJames() );
//            os.writeObject( handler.getGame().getJessie() );

            ////////////
            os.close();
            ////////////
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* //PRIOR TO java7.
        try {
            FileOutputStream fs = new FileOutputStream(concatenatedDefaultName);



            ///////////
            fs.close();
            ///////////
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    public void loadReadFromFile() {
        String counterAsString = String.format("%05d", counter);
        String concatenatedDefaultName = defaultName + counterAsString + ".bin";

        try (FileInputStream fi = new FileInputStream(concatenatedDefaultName)) {
            ObjectInputStream os = new ObjectInputStream(fi);

            handler.getGame().setGameCamera( (GameCamera)os.readObject() );
            handler.getGame().setPlayer( (Player)os.readObject() );

            Player player = handler.getGame().getPlayer();
            player.setHandler(handler);
            player.initAnimations();
            ((GameState)handler.getStateManager().getIState("GameState")).setPlayer( player );
            for (Critter critter : player.getCritterBeltList()) {
                critter.setHandler(handler);
                critter.getMovesModule().setHandler(handler);
                critter.getStatsModule().setHandler(handler);
            }
            for (Item item : player.getInventory()) {
                item.setHandler(handler);
            }

            ((GameState)handler.getStateManager().getIState("GameState")).setCritterStorageSystem( (Critter[][])os.readObject() );
            Critter[][] critterStorageSystem = ((GameState)handler.getStateManager().getIState("GameState")).getCritterStorageSystem();
            for (int y = 0; y < critterStorageSystem.length; y++) {
                for (int x = 0; x < critterStorageSystem[0].length; x++) {
                    if (critterStorageSystem[y][x] != null) {
                        critterStorageSystem[y][x].setHandler(handler);
                    }
                }
            }

            handler.getGame().getPlayer().setNabberList( (ArrayList<INabber>)os.readObject() );
            for (INabber nabber : player.getNabberList()) {
                nabber.initAnimations();

                if (nabber instanceof James) {
                    ((James)nabber).setHandler(handler);
                    ((James)nabber).setRand( new Random() );
                } else if (nabber instanceof Jessie) {
                    ((Jessie)nabber).setHandler(handler);
                }
            }
            handler.getGame().getPlayer().setCritterBeltList( (ArrayList<Critter>)os.readObject() );
            handler.getGame().getPlayer().setInventory( (ArrayList<Item>)os.readObject() );
//            handler.getGame().setJames( (James)os.readObject() );
//            handler.getGame().setJessie( (Jessie)os.readObject() );

            ////////////
            os.close();
            ////////////
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

} // **** end SerializationDoer class ****