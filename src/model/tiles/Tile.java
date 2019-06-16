package model.tiles;

import java.awt.*;

public class Tile {

    //CONSTANTS
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    //MEMBER FIELDS
    private boolean solid;
    private int x, y;
    private Rectangle bounds;

    public Tile(boolean solid, int x, int y) {
        this.solid = solid;
        bounds = new Rectangle(x+(WIDTH/4), y+(HEIGHT/4), (WIDTH/2), (HEIGHT/2));
    } // **** end Tile(boolean, int, int) constructor ****

    // GETTERS AND SETTERS

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public Rectangle getBounds() { return bounds; }

} // **** end Tile class *****