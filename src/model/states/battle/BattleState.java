package model.states.battle;

import main.Handler;
import model.entities.Player;
import model.entities.critters.Critter;
import model.states.IState;
import model.states.StateMachine;
import model.states.battle.belt_list_battle.BattleBeltList;
import model.states.battle.belt_list_battle.BattleBeltListAction;
import model.states.battle.belt_list_battle.BattleBeltListActionSummary;
import model.states.battle.belt_list_battle.BattleBeltListActionSwap;

import java.awt.*;

public class BattleState implements IState {

    private Handler handler;
    private Player player;

    private StateMachine stateMachine;

    private Critter critterOfOpponent;
    private Critter critterOfPlayer;
    //TODO: each Critter have In-battle stats: (1) evasion, (2) accuracy.

    public BattleState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        //TODO: random CritterFactory based on tile's x and y values compared to WorldMap's enum of City, Route, and Place.
        critterOfOpponent = new Critter(handler, Critter.Species.COASTAL_GULL, 6);

        //TODO: reward/experience/leveling.

        initStateMachine();
    } // **** end BattleState(Handler, Player) constructor ****

    private void initStateMachine() {
        stateMachine = new StateMachine(handler);

        stateMachine.addIStateToCollection("BattleStateIntro", new BattleStateIntro(handler, player));
        stateMachine.addIStateToCollection("BattleStateMenu", new BattleStateMenu(handler, player));
        stateMachine.addIStateToCollection("BattleStateFight", new BattleStateFight(handler, player));
        stateMachine.addIStateToCollection("BattleStateItemList", new BattleStateItemList(handler, player));
        stateMachine.addIStateToCollection("BattleBeltList", new BattleBeltList(handler, player));
        stateMachine.addIStateToCollection("BattleBeltListAction", new BattleBeltListAction(handler, player));
        stateMachine.addIStateToCollection("BattleBeltListActionSwap", new BattleBeltListActionSwap(handler, player));
        stateMachine.addIStateToCollection("BattleBeltListActionSummary", new BattleBeltListActionSummary(handler, player));
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
        //initialize critterOfOpponent (the ACTUAL one [not a dummy/filler]).
        if (args != null) {
            if (args[0] instanceof Critter) {
                critterOfOpponent = (Critter)args[0];
            }
        }

        //initialize critterOfPlayer (find FIRST Critter that is NOT FAINTED and SET it to be critterOfPlayer).
        for (int i = 0; i < player.getCritterBeltList().size(); i++) {
            if (player.getCritterBeltList().get(i).getStatus() != Critter.StatusConditionNonVolatile.FAINTED) {
                critterOfPlayer = player.getCritterBeltList().get(i);
                break;
            }
        }
    }

    @Override
    public void exit() {

    }

    // GETTERS AND SETTERS

    public Critter getCritterOfOpponent() { return critterOfOpponent; }

    public Critter getCritterOfPlayer() { return critterOfPlayer; }

    public void setCritterOfPlayer(Critter critterOfPlayer) { this.critterOfPlayer = critterOfPlayer; }

    public StateMachine getStateMachine() { return stateMachine; }

} // **** end BattleState class ****