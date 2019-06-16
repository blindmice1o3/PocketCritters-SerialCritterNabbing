package main;

import main.gfx.GameCamera;
import main.input.KeyManager;
import model.tiles.Tile;

public class Handler {

    private Game game;

    public Handler(Game game) {
        this.game = game;
    } // **** end Handler(Game, KeyManager) constructor ****

    public Game getGame() {
        return game;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public Tile[][] getWorldMapTileCollisionDetection() {
        return game.getWorldMapTileCollisionDetection();
    }

} // **** end main.Handler class ****