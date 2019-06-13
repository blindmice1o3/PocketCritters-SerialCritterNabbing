package model.states;

public interface IState {

    void tick();

    void handleInput();

    void enter(Object[] args);

    void exit();

}
