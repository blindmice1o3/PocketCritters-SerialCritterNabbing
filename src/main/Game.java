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

public class Game implements Runnable {

    private Handler handler;
    private KeyManager keyManager;

    private Thread thread;
    private boolean running = false;

    private GameCamera gameCamera;
    private Displayer displayer;

    private int width, height;
    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private Tile[][] worldMapTileCollisionDetection;
    private Player player, james, jessie;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
    } // **** end Game(int, int) constructor ****

    public synchronized void start() {
        //In case the game is already running and this start() method gets accidentally called somewhere.
        if (running) {
            return;
        }   //To prevent re-initializing the thread.

        //running controls the while-loop in gameLoop() method.
        running = true;

        //Pass a Runnable object to Thread class's constructor.
        thread = new Thread(this);   //Game class is a Runnable.
        thread.start();                     //Thread class's start() method calls the Runnable's run() method.
        //The Runnable's run() method is where the majority of our game code will go.
    }

    //THIS IS CALLED BY Thread class's start() method... !!!TO BE RUN IN A SEPARATE THREAD!!!
    @Override
    public void run() {

        init();

        gameLoop();

        //In case stop() doesn't get called inside the while-loop of gameLoop() method.
        stop();

    }

    private void init() {
        handler = new Handler(this);
        Assets.init();

        gameCamera = new GameCamera(960, 3184, 1279, 3455);

        //@@@@@ Initializing Tile[][] worldMapTileCollisionDetection @@@@@
        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();
        worldMapTileCollisionDetection = tileSpriteToRGBConverter.generateWorldMapTileCollisionDetection(Assets.world);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@
        player = new Player(handler);
        james = new James(handler);
        jessie = new Jessie(handler);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@

        initStateManager();

        keyManager = new KeyManager();
        displayer = new Displayer(handler, keyManager,
                "Pocket Critters - Serial Critter Nabbing", width, height);
    }

    public synchronized void stop() {
        //In case the game is already stopped and this stop() method gets accidentally called somewhere.
        if (!running) {
            return;
        }   //Prevents error, in case the game is already stopped and (it will seem like) we're trying to stop it again.

        //running controls the while-loop in gameLoop() method.
        running = false;

        //Safely joins/stops the thread.
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initStateManager() {
        StateManager.add("GameState", new GameState(handler, width, height));
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

        ////////////////////////////////////////////////////////
        while(running) {
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
        ////////////////////////////////////////////////////////
    }

    public void tick() {
        keyManager.tick();
        player.tick();
    }

    public void render() {
       displayer.getCurrentPanel().repaint();
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
        Game game = new Game(640, 540);
        game.start();
    }

} // **** end main.Game class ****