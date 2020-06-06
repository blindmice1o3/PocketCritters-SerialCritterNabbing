package model.states;

import model.entities.Player;

import java.awt.*;

public interface IState {

    void tick(long timeElapsed);

    void render(Graphics g);

    void enter(Object[] args);

    void exit();

    void setPlayer(Player player);

}
