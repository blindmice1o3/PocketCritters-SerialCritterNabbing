package model.states;

import main.Handler;
import model.entities.Player;

import java.awt.*;

public class BattleState implements IState {



    private Handler handler;

    private int xIndex, yIndex;

    private Player player;

    public BattleState(Handler handler) {
        this.handler = handler;
        xIndex = 0;
        yIndex = 0;
    } // **** end BattleState(Handler) constructor ****

    @Override
    public void updateInput() {
        //UP
        if (handler.getKeyManager().up) {
            if (yIndex > 0) {
                yIndex--;
            }
        }
        //DOWN
        else if (handler.getKeyManager().down) {
            if (yIndex == 0) {
                yIndex++;
            }
        }
        //LEFT
        else if (handler.getKeyManager().left) {
            if (xIndex > 0) {
                xIndex--;
            }
        }
        //RIGHT
        else if (handler.getKeyManager().right) {
            if (xIndex == 0) {
                xIndex++;
            }
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void enter(Object[] args) {
        //if first time entering BattleState... set the player reference variables.
        if (player == null) {
            if (args[0] instanceof Player) {
                this.player = (Player)args[0];
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end BattleState class ****