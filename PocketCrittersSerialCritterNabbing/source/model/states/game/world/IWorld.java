package model.states.game.world;

import model.tiles.Tile;

import java.awt.*;
import java.util.Map;

public interface IWorld {
    void tick(long timeElapsed);
    void render(Graphics g);
    void enter(Object[] args);
    void exit();
    Tile[][] getWorldMapTileCollisionDetection();
    Map<String, Rectangle> getTransferPoints();
}
