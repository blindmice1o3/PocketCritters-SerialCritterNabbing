package model.states.battle;

import main.Handler;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;

public class BattleState implements IState {

    private Handler handler;
    private Player player;

    private StateMachine stateMachine;

    public BattleState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        stateMachine = new StateMachine(handler);
        initStateMachine();
    } // **** end BattleState(Handler, Player) constructor ****

    private void initStateMachine() {
        stateMachine.addIStateToCollection("BattleStateIntro", new BattleStateIntro(handler, player));
        stateMachine.addIStateToCollection("BattleStateMenu", new BattleStateMenu(handler, player));
        stateMachine.addIStateToCollection("BattleStateFight", new BattleStateFight(handler, player));
        stateMachine.addIStateToCollection("BattleStateItemList", new BattleStateItemList(handler, player));
        stateMachine.addIStateToCollection("BattleStateCritterBeltList", new BattleStateCritterBeltList(handler, player));
        stateMachine.addIStateToCollection("BattleStateRun", new BattleStateRun(handler, player));
        stateMachine.addIStateToCollection("BattleStateOutro", new BattleStateOutro(handler, player));

        stateMachine.push( stateMachine.getIState("BattleStateIntro"), null );
    }

    @Override
    public void tick(long timeElapsed) {
        stateMachine.getCurrentState().tick(timeElapsed);
    }

    @Override
    public void render(Graphics g) {
        stateMachine.getCurrentState().render(g);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    // GETTERS AND SETTERS

    public StateMachine getStateMachine() { return stateMachine; }

} // **** end BattleState class ****