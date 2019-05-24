package main;

import gfx.GameCamera;
import model.Assets;
import model.James;
import model.Jessie;
import model.Player;
import view.Displayer;

public class Game {

    private Handler handler;
    private Displayer displayer;
    private Player player, james, jessie;
    private boolean gameOver;

    private GameCamera gameCamera;

    public Game() {
        Assets.init();
        handler = new Handler(this);
        player = new Player();

        //@@@@@@@@@@@@@@@@@@@@
        james = new James();
        jessie = new Jessie();
        //@@@@@@@@@@@@@@@@@@@@

        gameOver = false;

        displayer = new Displayer(handler, "Pocket Critters - Serial Critter Nabbing");

        gameCamera = new GameCamera(0, 0);

        gameLoop();
    } // **** end main.Game() constructor ****

    public void gameLoop() {
        while(!gameOver) {
            tick();
            render();
        }
    }

    public void tick() {
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

    public GameCamera getGameCamera() { return gameCamera; }

    // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|

    public static void main(String[] args) {
        new Game();
    }

} // **** end main.Game class ****