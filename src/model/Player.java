package model;

import java.awt.*;

public class Player {

    protected int x, y;
    private int xFuture, yFuture;

    public Player() {
        x = 288;
        y = 256;
    } // **** end model.Player() constructor ****

    public void tick() {
        move();

        xFuture = 0;
        yFuture = 0;
    }

    public void move() {
        x += xFuture;
        y += yFuture;
    }

    public void render(Graphics g) {
        g.drawImage(Assets.player, x, y, x+32, y+32,
                0, 0, 196, 257, null);
    }

    // GETTERS & SETTERS

    public void setXFuture(int xFuture) {
        this.xFuture = xFuture;
    }

    public void setYFuture(int yFuture) {
        this.yFuture = yFuture;
    }

} // **** end model.Player class ****