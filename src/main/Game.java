package main;

import main.gfx.GameCamera;
import main.input.KeyManager;
import main.gfx.Assets;
import main.utils.SerializationDoer;
import model.entities.nabbers.INabber;
import model.entities.nabbers.James;
import model.entities.nabbers.Jessie;
import model.entities.Player;
import model.states.StateManager;
import main.utils.TileSpriteToRGBConverter;
import view.Displayer;

public class Game implements Runnable {

    private Handler handler;
    private SerializationDoer serializationDoer;
    private KeyManager keyManager;

    private Thread thread;
    private boolean running = false;

    private TileSpriteToRGBConverter tileSpriteToRGBConverter;
    private StateManager stateManager;
    private GameCamera gameCamera;
    private Displayer displayer;

    private int width, height;

    private Player player;
    private James james;
    private Jessie jessie;

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
        Assets.init();

        keyManager = new KeyManager();
        gameCamera = new GameCamera(960, 3184, 1279, 3455);

        handler = new Handler(this);

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@
        player = new Player(handler);
        james = new James(handler);
        jessie = new Jessie(handler);
        player.addINabber((INabber)james);
        player.addINabber((INabber)jessie);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@

        tileSpriteToRGBConverter = new TileSpriteToRGBConverter();

        stateManager = new StateManager(handler, player);

        Object[] args = { player, james, jessie };
        stateManager.push(
                stateManager.getIState("GameState"),
                args);

        displayer = new Displayer(handler,"Pocket Critters - Serial Critter Nabbing", width, height);
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

    /*
    public Tile[][] getWorldMapTileCollisionDetection() {
        return worldMapTileCollisionDetection;
    }
    */

    public void gameLoop() {

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long timeElapsed = 0;
        long lastTime = System.nanoTime();

        long timer = 0;
        int ticks = 0;

        ////////////////////////////////////////////////////////
        while(running) {
            now = System.nanoTime();
            timeElapsed = now - lastTime;
            delta += (timeElapsed / timePerTick);
            timer += timeElapsed;
            lastTime = now;

            if (delta >= 1) {
                //@@@@@@@@
                tick(timeElapsed);
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

    private void tick(long timeElapsed) {
        keyManager.tick();                                      //getInput()
        stateManager.getCurrentState().tick(timeElapsed);       //update()
    }

    private void render() {
       displayer.getPanel().repaint();                          //render()
    }

    // GETTERS & SETTERS

    public KeyManager getKeyManager() { return keyManager; }
    public GameCamera getGameCamera() { return gameCamera; }
    public TileSpriteToRGBConverter getTileSpriteToRGBConverter() { return tileSpriteToRGBConverter; }
    public StateManager getStateManager() { return stateManager; }
    public void setGameCamera(GameCamera gameCamera) { this.gameCamera = gameCamera; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public James getJames() { return james; }
    public void setJames(James james) { this.james = james; }
    public Jessie getJessie() { return jessie; }
    public void setJessie(Jessie jessie) { this.jessie = jessie; }

    // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|

    public static void main(String[] args) {
        Game game = new Game(640, 544); //640x540
        game.start();
    }

} // **** end main.Game class ****