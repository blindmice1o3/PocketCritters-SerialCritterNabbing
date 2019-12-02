package model.states;

import java.awt.*;

public interface IState {

    void tick(long timeElapsed);

    void render(Graphics g);

    void enter(Object[] args);

    void exit();

}
