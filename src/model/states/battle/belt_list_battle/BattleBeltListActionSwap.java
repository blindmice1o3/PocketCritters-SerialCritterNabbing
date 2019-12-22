package model.states.battle.belt_list_battle;

import main.Handler;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.states.IState;
import model.states.StateMachine;
import model.states.battle.BattleState;
import view.Cursor;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleBeltListActionSwap implements IState {

    private Handler handler;
    private Player player;

    private int indexCurrentCritterSelected;
    private boolean isBattling;
    private Cursor cursor;

    public BattleBeltListActionSwap(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        indexCurrentCritterSelected = 0;
        isBattling = false;
        cursor = new Cursor(3 , 27, 60, 20, 20);
        cursor.updateCursorPosition(indexCurrentCritterSelected);
    } // **** end BattleBeltListActionSwap(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleBeltListActionSwap.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                //pop self (BattleBeltListActionSwap).
                stateMachine.pop();
                //pop (BattleBeltListAction).
                stateMachine.pop();
                //now BattleBeltList.
            }
            ///////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleBeltListActionSwap.tick(long)... aButton");

            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState) handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                Critter critterCurrentlyOut = battleState.getCritterOfPlayer();
                Critter critterNext = player.getCritterBeltList().get(indexCurrentCritterSelected);

                /////////////////////////////////////////////////////////////////////
                isBattling = (critterNext == critterCurrentlyOut) ? (true) : (false);
                /////////////////////////////////////////////////////////////////////

                if (!isBattling) {
                    ///////////////////////////////////////////////
                    battleState.setCritterOfPlayer(critterNext);

                    //pop self (BattleBeltListActionSwap).
                    stateMachine.pop();
                    //pop (BattleBeltListAction).
                    stateMachine.pop();
                    //pop (BattleBeltList).
                    stateMachine.pop();
                    //now BattleStateMenu.
                    ///////////////////////////////////////////////
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        //re-draw previous layer.
        ((BattleState)handler.getStateManager().getCurrentState()).getStateMachine().getIState("BattleBeltListAction").render(g);

        //PANEL
        g.setColor(Color.GRAY);
        g.fillRect(200, 200, 2*(handler.getGame().getWidth()-400), handler.getGame().getHeight()-400);

        //TEXT (first line)
        if (!isBattling) {
            g.setColor(Color.GREEN);
            FontGrabber.renderString(g, "aButton to confirm", 250, 225, 20, 20);
        } else {
            g.setColor(Color.CYAN);
            FontGrabber.renderString(g,
                    player.getCritterBeltList().get(indexCurrentCritterSelected).getNameColloquial() + " is ",
                    250, 225, 10, 10);
            FontGrabber.renderString(g, "CURRENTLY battling.",
                    250, 237, 10, 10);
        }
        //TEXT (second line)
        g.setColor(Color.RED);
        FontGrabber.renderString(g, "bButton to cancel", 250, 250, 20, 20);
    }

    @Override
    public void enter(Object[] args) {
        if (args != null) {
            indexCurrentCritterSelected = (int)args[0];
            cursor.updateCursorPosition(indexCurrentCritterSelected);
        }
    }

    @Override
    public void exit() {
        isBattling = false;
    }

} // **** end BattleBeltListActionSwap class ****