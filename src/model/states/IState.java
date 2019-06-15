package model.states;

import javax.swing.*;

public interface IState {

    void tick();

    void handleInput();

    void enter(Object[] args);

    void exit();

    JPanel getPanel();

}
