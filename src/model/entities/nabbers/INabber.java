package model.entities.nabbers;

import model.entities.critters.Critter;

import java.awt.*;

public interface INabber {
    void tick();
    void render(Graphics g);
    void nab(Critter critter);
    void setXDelta(int xDelta);
    void setYDelta(int yDelta);
    void regroup();
}
