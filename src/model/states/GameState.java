package model.states;

import main.Handler;
import main.gfx.Assets;
import model.entities.James;
import model.entities.Jessie;
import model.entities.Player;

import java.awt.*;

public class GameState implements IState {

    private Handler handler;

    private Player player, james, jessie;

    public GameState(Handler handler) {
        this.handler = handler;
    } // **** end GameState(Handler, int, int) constructor ****

    @Override
    public void tick() {
        updateInput();

        player.tick();
        james.tick();
        jessie.tick();
    }

    public void updateInput() {
        player.setXDelta(0);
        player.setYDelta(0);

        //UP
        if (handler.getKeyManager().up) {
            player.setYDelta( -player.getMoveSpeed() );
        }
        //DOWN
        else if (handler.getKeyManager().down) {
            player.setYDelta( player.getMoveSpeed() );
        }
        //LEFT
        else if (handler.getKeyManager().left) {
            player.setXDelta( -player.getMoveSpeed() );
        }
        //RIGHT
        else if (handler.getKeyManager().right) {
            player.setXDelta( player.getMoveSpeed() );
        }
    }

    @Override
    public void render(Graphics g) {
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
        james.render(g);
        jessie.render(g);
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