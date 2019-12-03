package model.states.game.world;

import model.tiles.Tile;

import java.awt.*;

public interface IWorld {
    void tick(long timeElapsed);
    void render(Graphics g);
    void enter(Object[] args);
    void exit();
    Tile[][] getWorldMapTileCollisionDetection();
}
