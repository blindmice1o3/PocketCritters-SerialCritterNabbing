package main.utils;

import main.Handler;
import main.gfx.GameCamera;
import model.entities.Player;
import model.entities.critters.Critter;
import model.entities.nabbers.INabber;
import model.entities.nabbers.James;
import model.entities.nabbers.Jessie;
import model.items.Item;
import model.states.IState;
import model.states.StateMachine;
import model.states.battle.BattleState;
import model.states.computer.ComputerState;
import model.states.game.GameState;
import model.states.game_console.GameConsoleState;
import model.states.menu.MenuState;
import model.states.television.TelevisionState;
import model.tiles.GameConsoleTile;

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
            os.writeObject( handler.getGame().getPlayer().getNabberList() );

            int sizeCritterBeltList = handler.getGame().getPlayer().getCritterBeltList().size();
            os.writeInt( sizeCritterBeltList );
            for (int i = 0; i < sizeCritterBeltList; i++) {
                os.writeObject( handler.getGame().getPlayer().getCritterBeltList().get(i) );
            }

            int sizeInventory = handler.getGame().getPlayer().getInventory().size();
            os.writeInt( sizeInventory );
            for (int i = 0; i < sizeInventory; i++) {
                os.writeObject( handler.getGame().getPlayer().getInventory().get(i) );
            }

            //os.writeObject( handler.getGame().getPlayer().getCritterBeltList() );
            //os.writeObject( handler.getGame().getPlayer().getInventory() );
            os.writeObject( ((GameState)handler.getStateManager().getIState("GameState")).getCritterStorageSystem() );

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
            player.setXScreenPosition( Player.X_SCREEN_POSITION );
            player.setYScreenPosition( Player.Y_SCREEN_POSITION );
            player.setNabberList( (ArrayList<INabber>)os.readObject() );
            for (INabber nabber : player.getNabberList()) {
                nabber.initAnimations();

                if (nabber instanceof James) {
                    ((James)nabber).setHandler(handler);
                    ((James)nabber).setRand( new Random() );
                } else if (nabber instanceof Jessie) {
                    ((Jessie)nabber).setHandler(handler);
                }
            }

            ArrayList<Critter> newCritterBeltList = new ArrayList<Critter>(6);
            int sizeCritterBeltList = os.readInt();
            for (int i = 0; i < sizeCritterBeltList; i++) {
                newCritterBeltList.add( (Critter)os.readObject() );
            }
            //player.setCritterBeltList( (ArrayList<Critter>)os.readObject() );
            player.setCritterBeltList( newCritterBeltList );
            for (Critter critter : player.getCritterBeltList()) {
                critter.setHandler(handler);
                critter.getMovesModule().setHandler(handler);
                critter.getStatsModule().setHandler(handler);
            }

            ArrayList<Item> newInventory = new ArrayList<Item>();
            int sizeInventory = os.readInt();
            for (int i = 0; i < sizeInventory; i++) {
                newInventory.add( (Item)os.readObject() );
            }
            //player.setInventory( (ArrayList<Item>)os.readObject() );
            player.setInventory( newInventory );
            for (Item item : player.getInventory()) {
                item.setHandler(handler);
            }

            handler.getStateManager().getIState("GameState").setPlayer( player );
            handler.getStateManager().getIState("BattleState").setPlayer( player );
            StateMachine battleStateMachine = ((BattleState)handler.getStateManager().getIState("BattleState")).getStateMachine();
            battleStateMachine.setPlayerForAllIState( player );
            handler.getStateManager().getIState("MenuState").setPlayer( player );
            StateMachine menuStateMachine = ((MenuState)handler.getStateManager().getIState("MenuState")).getStateMachine();
            menuStateMachine.setPlayerForAllIState( player );
            handler.getStateManager().getIState("ComputerState").setPlayer( player );
            handler.getStateManager().getIState("GameConsoleState").setPlayer( player );
            handler.getStateManager().getIState("TelevisionState").setPlayer( player );

            ((GameState)handler.getStateManager().getIState("GameState")).setCritterStorageSystem( (Critter[][])os.readObject() );
            Critter[][] critterStorageSystem = ((GameState)handler.getStateManager().getIState("GameState")).getCritterStorageSystem();
            for (int y = 0; y < critterStorageSystem.length; y++) {
                for (int x = 0; x < critterStorageSystem[0].length; x++) {
                    if (critterStorageSystem[y][x] != null) {
                        critterStorageSystem[y][x].setHandler(handler);
                    }
                }
            }


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