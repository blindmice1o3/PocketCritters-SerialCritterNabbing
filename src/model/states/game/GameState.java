package model.states.game;

import main.Handler;
import model.entities.critters.Critter;
import model.entities.nabbers.James;
import model.entities.nabbers.Jessie;
import model.entities.Player;
import model.states.IState;
import model.states.game.world.WorldManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameState implements IState {

    public static final int MAX_NUMBER_OF_BOXES = 12, MAX_NUMBER_OF_CRITTERS_PER_BOX = 20;

    private Handler handler;

    private WorldManager worldManager;
    private Player player, james, jessie;

    private Critter[][] critterStorageSystem;
    private int indexCurrentBox, indexCurrentCritterInBox;

    public GameState(Handler handler) {
        this.handler = handler;
        worldManager = new WorldManager(handler);

        critterStorageSystem = new Critter[MAX_NUMBER_OF_BOXES][MAX_NUMBER_OF_CRITTERS_PER_BOX];
        indexCurrentBox = 0;
        indexCurrentCritterInBox = 0;
    } // **** end GameState(Handler) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //getInput()
        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            player.setYDelta( -player.getMoveSpeed() );
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            player.setYDelta( player.getMoveSpeed() );
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            player.setXDelta( -player.getMoveSpeed() );
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            player.setXDelta( player.getMoveSpeed() );
        }

        //startButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            System.out.println("GameState.tick()... startButton pressed (VK_ENTER).");

            /////////////////////////////////////////////
            handler.getStateManager().push(
                    handler.getStateManager().getIState("MenuState"),
                    null );
            /////////////////////////////////////////////
        }
        //aButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("GameState.tick()... aButton pressed (VK_COMMA).");

            /////////////////////////
            player.checkTileFacing();
            /////////////////////////
        }

        //update()
        worldManager.tick(timeElapsed);
        //TODO: move player.tick() (an Entity) into WorldManager's or WorldMap.
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        // render background
        worldManager.render(g);

        //renderBackground(g);
        // render entities
        renderEntities(g);
    }

    /*
    private void renderBackground(Graphics g) {
        g.drawImage(Assets.world, 0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                (int)(handler.getGame().getGameCamera().getxOffset0()),
                (int)(handler.getGame().getGameCamera().getyOffset0()),
                (int)(handler.getGame().getGameCamera().getxOffset1()),
                (int)(handler.getGame().getGameCamera().getyOffset1()),
                null);
    }
    */

    private void renderEntities(Graphics g) {
        player.render(g);
    }

    @Override
    public void enter(Object[] args) {
        //if first time entering GameState... set the player, james, and jessie reference variables.
        if ( (player == null) && (james == null) && (jessie == null) ) {
            if ((args[0] instanceof Player) &&
                    (args[1] instanceof James) &&
                    (args[2] instanceof Jessie)) {
                this.player = (Player) args[0];
                this.james = (James) args[1];
                this.jessie = (Jessie) args[2];
            }
        }

    }

    @Override
    public void exit() {

    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    // GETTERS AND SETTERS

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public Critter[][] getCritterStorageSystem() { return critterStorageSystem; }

    public void setCritterStorageSystem(Critter[][] critterStorageSystem) { this.critterStorageSystem = critterStorageSystem; }

    public int getIndexCurrentBox() { return indexCurrentBox; }

} // **** end GameState class ****