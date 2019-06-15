package model.states;

import java.awt.*;

public interface IState {

    void tick();

    void render(Graphics G);

    void enter(Object[] args);

    void exit();

}
