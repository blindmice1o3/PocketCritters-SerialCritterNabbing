package model.states;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private static Map<String, IState> stateHashMap = new HashMap<String, IState>();
    private static IState currentState = new EmptyState();

    public static void add(String key, IState state) {
        stateHashMap.put(key, state);
    }

    public static void remove(String key) {
        stateHashMap.remove(key);
    }


    public static void change(String key, Object[] args) {
        //current IState object "closes down shop for the night".
        currentState.exit();

        //future IState object "opens up shop for the day".
        IState nextState = stateHashMap.get(key);
        nextState.enter(args);

        //when closing AND opening preparations are complete, switch future IState to become current IState.
        currentState = nextState;
    }

    public static IState getCurrentState() { return currentState; }

} // **** end StateManager class ****