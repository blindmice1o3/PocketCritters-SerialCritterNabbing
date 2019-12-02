package model.states.game;

import main.Handler;
import main.gfx.Assets;
import model.entities.nabbers.James;
import model.entities.nabbers.Jessie;
import model.entities.Player;
import model.states.IState;
import model.states.StateManager;
import model.tiles.SolidTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameState implements IState {

    private Handler handler;
    private Player player, james, jessie;

    public GameState(Handler handler) {
        this.handler = handler;
    } // **** end GameState(Handler) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //getInput()
        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            player.setYDelta( -player.getMoveSpeed() );
            //james.setYDelta( -james.getMoveSpeed() );
            //player.setDirectionFacing(Player.DirectionFacing.UP);
            //jessie.setDirectionFacing(Player.DirectionFacing.UP);
            //handler.getGameCamera().setYDelta( player.getYDelta() );
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            player.setYDelta( player.getMoveSpeed() );
            //james.setYDelta( james.getMoveSpeed() );
            //player.setDirectionFacing(Player.DirectionFacing.DOWN);
            //jessie.setDirectionFacing(Player.DirectionFacing.DOWN);
            //handler.getGameCamera().setYDelta( player.getYDelta() );
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            player.setXDelta( -player.getMoveSpeed() );
            //james.setXDelta( -james.getMoveSpeed() );
            //player.setDirectionFacing(Player.DirectionFacing.LEFT);
            //jessie.setDirectionFacing(Player.DirectionFacing.LEFT);
            //handler.getGameCamera().setXDelta( player.getXDelta() );
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            player.setXDelta( player.getMoveSpeed() );
            //james.setXDelta( james.getMoveSpeed() );
            //player.setDirectionFacing(Player.DirectionFacing.RIGHT);
            //jessie.setDirectionFacing(Player.DirectionFacing.RIGHT);
            //handler.getGameCamera().setXDelta( player.getXDelta() );
        }
        //startButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            System.out.println("GameState.tick()... startButton pressed (VK_ENTER).");

            /////////////////////////////////////////////
            Object[] args = { player };
            handler.getStateManager().change("MenuState", args);
            /////////////////////////////////////////////
        }

        //update()
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        //render()
        renderBackground(g);
        renderEntities(g);
    }

    private void renderBackground(Graphics g) {
        g.drawImage(Assets.world, 0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                (int)(handler.getGame().getGameCamera().getxOffset0()),
                (int)(handler.getGame().getGameCamera().getyOffset0()),
                (int)(handler.getGame().getGameCamera().getxOffset1()),
                (int)(handler.getGame().getGameCamera().getyOffset1()),
                null);
        //g.setColor(Color.YELLOW);
        //g.drawString("Pocket Critters - Serial Critter Nabbing!!!", 10, 10);
    }

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

} // **** end GameState class ****