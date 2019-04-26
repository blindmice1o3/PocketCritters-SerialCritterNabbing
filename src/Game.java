public class Game {

    private Handler handler;
    private Displayer displayer;
    private Player player;
    private boolean gameOver;

    public Game() {
        Assets.init();
        handler = new Handler(this);
        player = new Player();
        gameOver = false;

        displayer = new Displayer(handler, "Pocket Critters - Serial Critter Nabbing");

        gameLoop();
    } // **** end Game() constructor ****

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

    // |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|

    public static void main(String[] args) {
        new Game();
    }

} // **** end Game class ****