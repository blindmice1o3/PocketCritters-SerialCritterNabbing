package model.states;

import main.Handler;
import model.entities.Player;
import model.states.battle.*;
import model.states.game.GameState;
import model.states.menu.*;

import java.util.HashMap;
import java.util.Map;

public class StateManager {

    private Handler handler;
    private Map<String, IState> stateHashMap;
    private IState currentState;

    public StateManager(Handler handler, Player player) {
        this.handler = handler;
        currentState =  new EmptyState();

        stateHashMap = new HashMap<String, IState>();
        stateHashMap.put("GameState", new GameState(handler));
        stateHashMap.put("BattleState", new BattleState(handler, player));
        stateHashMap.put("MenuState", new MenuState(handler, player));

        stateHashMap.put("BattleStateIntro", new BattleStateIntro(handler, player));
        stateHashMap.put("BattleStateMenu", new BattleStateMenu(handler, player));
        stateHashMap.put("BattleStateFight", new BattleStateFight(handler, player));
        stateHashMap.put("BattleStateItemList", new BattleStateItemList(handler, player));
        stateHashMap.put("BattleStateCritterBeltList", new BattleStateCritterBeltList(handler, player));
        stateHashMap.put("BattleStateRun", new BattleStateRun(handler, player));
        stateHashMap.put("BattleStateOutro", new BattleStateOutro(handler, player));

        stateHashMap.put("MenuStateMenu", new MenuStateMenu(handler, player));
        stateHashMap.put("MenuStateCritterDex", new MenuStateCritterDex(handler, player));
        stateHashMap.put("MenuStateCritterBeltList", new MenuStateCritterBeltList(handler, player));
        stateHashMap.put("MenuStateItemList", new MenuStateItemList(handler, player));
        stateHashMap.put("MenuStatePlayerStats", new MenuStatePlayerStats(handler, player));
        stateHashMap.put("MenuStateSave", new MenuStateSave(handler, player));
        stateHashMap.put( "MenuStateLoad", new MenuStateLoad(handler, player));
        stateHashMap.put("MenuStateExit", new MenuStateExit(handler, player));
    } // **** end StateManager(Handler, Player) constructor ****

    public void add(String key, IState state) {
        stateHashMap.put(key, state);
    }

    public void remove(String key) {
        stateHashMap.remove(key);
    }

    public IState get(String key) { return stateHashMap.get(key); }


    public void change(String key, Object[] args) {
        //current IState object "closes down shop for the night".
        currentState.exit();

        //future IState object "opens up shop for the day".
        IState nextState = stateHashMap.get(key);
        nextState.enter(args);

        //when closing AND opening preparations are complete, switch future IState to become current IState.
        currentState = nextState;
    }

    public IState getCurrentState() { return currentState; }

} // **** end StateManager class ****