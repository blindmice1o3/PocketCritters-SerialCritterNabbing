package main;

import main.gfx.GameCamera;
import main.input.KeyManager;
import main.utils.TileSpriteToRGBConverter;
import model.states.StateManager;
import model.states.game.GameState;
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

    public StateManager getStateManager() { return game.getStateManager(); }

    public Tile[][] getWorldMapTileCollisionDetection() {
        return ((GameState)game.getStateManager().getIState("GameState")).getWorldManager().getCurrentWorld().getWorldMapTileCollisionDetection();
    }

    public TileSpriteToRGBConverter getTileSpriteToRGBConverter() {
        return game.getTileSpriteToRGBConverter();
    }

} // **** end main.Handler class ****