package model;

import main.Handler;
import tiles.Tile;

import java.awt.*;

public class Player {

    private Handler handler;
    protected int x, y;
    private int xDelta, yDelta;

    public Player(Handler handler) {
        this.handler = handler;

        x = 1104;
        y = 3312;
    } // **** end model.Player() constructor ****

    public void tick() {
        xDelta = 0;
        yDelta = 0;

        checkInput();

        move();
    }

    public void checkInput() {
        //UP
        if (handler.getGame().getKeyManager().up) {
            yDelta = -3;
        }
        //DOWN
        else if (handler.getGame().getKeyManager().down) {
            yDelta = 3;
        }
        //LEFT
        else if (handler.getGame().getKeyManager().left) {
            xDelta = -3;
        }
        //RIGHT
        else if (handler.getGame().getKeyManager().right) {
            xDelta = 3;
        }
    }

    public void move() {
        Tile[][] worldMap = handler.getGame().getWorldMapTileCollisionDetection();

        //System.out.println("Player.move() before if-conditional block");
        if ( !(worldMap[ ((y+yDelta) / 16) ][ ((x+xDelta) / 16) ].isSolid()) ) {
            x += xDelta;
            y += yDelta;
            handler.getGame().getGameCamera().move(xDelta, yDelta);
            //System.out.println("Player.move() WITHIN if-conditional block");
        }
    }

    public void render(Graphics g) {
        g.drawImage(Assets.player,
                288, 256, (288 + 32), (256 + 32),
                0, 0, 196, 257,
                null);
    }

    // GETTERS & SETTERS

    public void setXFuture(int xFuture) {
        this.xDelta = xFuture;
    }

    public void setYFuture(int yFuture) {
        this.yDelta = yFuture;
    }

} // **** end model.Player class ****