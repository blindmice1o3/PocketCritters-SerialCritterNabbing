package model.states;

import java.awt.*;

public interface IState {

    void updateInput();

    void tick();

    void render(Graphics g);

    void enter(Object[] args);

    void exit();

}
