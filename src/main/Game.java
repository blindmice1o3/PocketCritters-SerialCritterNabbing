package main;

import main.gfx.GameCamera;
import main.input.KeyManager;
import main.gfx.Assets;
import model.entities.James;
import model.entities.Jessie;
import model.entities.Player;
import model.states.BattleState;
import model.states.GameState;
import model.states.StateManager;
import model.tiles.Tile;
import main.utils.TileSpriteToRGBConverter;
import view.Displayer;

public class Game {

    private Handler handler;
    private KeyManager keyManager;
    private GameCamera gameCamera;
    private Displayer displayer;

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private Tile[][] worldMapTileCollisionDetection;
    private Player player, james, jessie;
    private boolean gameOver;

    public Game() {
        Assets.init();

        handler = new Handler(this);
        keyManager = new KeyManager();
        gameCamera = new GameCamera(960, 3184, 1279, 3455);
        displayer = new Displayer(handler, "Pocket Critters - Serial Critter Nabbing", 640, 540);
        displayer.getFrame().addKeyListener(keyManager);

        player = new Player(handler);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@
        james = new James(handler);
        jessie = new Jessie(handler);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //@@@@@
        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();
        worldMapTileCollisionDetection = tileSpriteToRGBConverter.generateWorldMapTileCollisionDetection(Assets.world);
        //@@@@@

        initStateManager();

        gameOver = false;

        gameLoop();
    } // **** end main.Game() constructor ****

    private void initStateManager() {
        StateManager.add("GameState", new GameState());
        StateManager.add("BattleState", new BattleState());

        StateManager.change( "GameState", null );
    }

    public Tile[][] getWorldMapTileCollisionDetection() {
        return worldMapTileCollisionDetection;
    }

    public void gameLoop() {

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        long timer = 0;
        int ticks = 0;

        while(!gameOver) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += (now - lastTime);
            lastTime = now;

            if (delta >= 1) {
                //@@@@@@@@
                tick();
                render();
                //@@@@@@@@
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
                System.out.println("Ticks: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }
    }

    public void tick() {
        keyManager.tick();
        player.tick();
    }

    public void render() {
       displayer.getPanel().repaint();
    }

    // GETTERS & SETTERS

    public Player getPlayer() {
        return player;
    }
    public Player getJames() {
        return james;
    }
    public Player getJessie() {
        return jessie;
    }

    public KeyManager getKeyManager() { return keyManager; }
    public GameCamera getGameCamera() { return gameCamera; }

    // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|

    public static void main(String[] args) {
        new Game();
    }

} // **** end main.Game class ****