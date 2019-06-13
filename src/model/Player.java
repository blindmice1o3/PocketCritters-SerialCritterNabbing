package model;

import main.Handler;
import tiles.Tile;

import java.awt.*;

public class Player {

    private Handler handler;
    protected int x, y;
    private int xFuture, yFuture;

    public Player(Handler handler) {
        this.handler = handler;

        x = 288;
        y = 256;
    } // **** end model.Player() constructor ****

    public void tick() {
        xFuture = 0;
        yFuture = 0;

        checkInput();

        move();
    }

    public void checkInput() {
        //UP
        if (handler.getGame().getKeyManager().up) {
            yFuture = -3;
        }
        //DOWN
        else if (handler.getGame().getKeyManager().down) {
            yFuture = 3;
        }
        //LEFT
        else if (handler.getGame().getKeyManager().left) {
            xFuture = -3;
        }
        //RIGHT
        else if (handler.getGame().getKeyManager().right) {
            xFuture = 3;
        }
    }

    public void move() {
        Tile[][] worldMap = handler.getGame().getWorldMapTileCollisionDetection();

        if ( !worldMap[(int)((y+3184 + yFuture) / 16)][(int)((x+960 + xFuture) / 16)].isSolid() ) {
            x += xFuture;
            y += yFuture;
        }
    }

    public void render(Graphics g) {
        g.drawImage(Assets.player,
                x, y, (x + 32), (y + 32),
                0, 0, 196, 257,
                null);
    }

    // GETTERS & SETTERS

    public void setXFuture(int xFuture) {
        this.xFuture = xFuture;
    }

    public void setYFuture(int yFuture) {
        this.yFuture = yFuture;
    }

} // **** end model.Player class ****