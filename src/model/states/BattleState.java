package model.states;

import main.Handler;
import model.entities.Player;

import java.awt.*;

public class BattleState implements IState {

    private Handler handler;

    private Player player;

    public BattleState(Handler handler) {
        this.handler = handler;
    } // **** end BattleState(Handler) constructor ****

    @Override
    public void tick() {
        getInput();
        update();
    }

    private void getInput() {
        //UP
        if (handler.getKeyManager().up) {
            //upButtomPressed();
        }
        //DOWN
        else if (handler.getKeyManager().down) {
            //downButtonPressed();
        }
        //LEFT
        else if (handler.getKeyManager().left) {
            //leftButtonPressed();
        }
        //RIGHT
        else if (handler.getKeyManager().right) {
            //rightButtonPressed();
        }
    }

    private void update() {

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