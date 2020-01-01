package model.states;

import main.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachine {

    private Handler handler;
    private List<IState> stateStack;
    private Map<String, IState> stateCollection;

    public StateMachine(Handler handler) {
        this.handler = handler;
        stateStack = new ArrayList<IState>();
        stateCollection = new HashMap<String, IState>();
    } // **** end StateMachine(Handler) constructor ****

    public void push(IState futureState, Object[] args) {
        //futureState's enter().
        futureState.enter(args);
        //actual push.
        stateStack.add(futureState);
    }

    public void pop() {
        //topState's exit().
        stateStack.get( getIndexOfTop() ).exit();
        //actual pop.
        stateStack.remove( getIndexOfTop() );
    }

    public IState getCurrentState() {
        return stateStack.get( getIndexOfTop() );
    }

    public int getIndexOfTop() {
        return (stateStack.size() - 1);
    }

    public void addIStateToCollection(String identifier, IState stateToBeAdded) {
        if (!stateCollection.containsKey(identifier)) {
            stateCollection.put(identifier, stateToBeAdded);
        }
    }

    public IState getIState(String identifier) {
        return (stateCollection.containsKey(identifier)) ? (stateCollection.get(identifier)) : null;
    }

} // **** end StateMachine class ****