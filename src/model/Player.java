package model;

import main.Handler;
import tiles.Tile;

import java.awt.*;

public class Player {

    private Handler handler;
    protected int x, y;
    private int xDelta, yDelta;
    private int moveSpeed;

    Tile[][] worldMap;

    public Player(Handler handler) {
        this.handler = handler;

        x = 1104;
        y = 3312;

        moveSpeed = 2;
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
            yDelta = -moveSpeed;
        }
        //DOWN
        else if (handler.getGame().getKeyManager().down) {
            yDelta = moveSpeed;
        }
        //LEFT
        else if (handler.getGame().getKeyManager().left) {
            xDelta = -moveSpeed;
        }
        //RIGHT
        else if (handler.getGame().getKeyManager().right) {
            xDelta = moveSpeed;
        }
    }

    public void move() {
        if (worldMap == null) { worldMap = handler.getGame().getWorldMapTileCollisionDetection(); }


        moveX();
        moveY();


        /*
        //System.out.println("Player.move() before if-conditional block");
        //CHECKS TOP-LEFT corner of player sprite.
        if ( !(worldMap[ ((y+yDelta) / Tile.HEIGHT) ][ ((x+xDelta) / Tile.WIDTH) ].isSolid()) ) {
            x += xDelta;
            y += yDelta;
            handler.getGame().getGameCamera().move(xDelta, yDelta);
            //System.out.println("Player.move() WITHIN if-conditional block");
        }
        */
    }

    private void moveX() {
        //LEFT
        if (xDelta < 0) {
            int tx = (int)((x+xDelta) / Tile.WIDTH);

            if ( !(worldMap[((y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&
                    !(worldMap[((y+yDelta+Tile.HEIGHT) / Tile.HEIGHT)][tx].isSolid()) ) {
                x += xDelta;
                handler.getGame().getGameCamera().move(xDelta, 0);
            }
        }
        //RIGHT
        else if (xDelta > 0) {
            int tx = (int)((x+xDelta+Tile.WIDTH) / Tile.WIDTH);

            if ( !(worldMap[((y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&
                    !(worldMap[((y+yDelta+Tile.HEIGHT) / Tile.HEIGHT)][tx].isSolid()) ) {
                x += xDelta;
                handler.getGame().getGameCamera().move(xDelta, 0);
            }
        }
    }

    private void moveY() {
        //UP
        if (yDelta < 0) {
            int ty = (int)((y+yDelta) / Tile.HEIGHT);

            if ( !(worldMap[ty][((x+xDelta) / Tile.WIDTH)].isSolid()) &&
                    !(worldMap[ty][((x+xDelta+Tile.WIDTH) / Tile.WIDTH)].isSolid()) ) {
                y += yDelta;
                handler.getGame().getGameCamera().move(0, yDelta);
            }
        }
        //DOWN
        else if (yDelta > 0) {
            int ty = (int)((y+yDelta+Tile.HEIGHT) / Tile.HEIGHT);

            if ( !(worldMap[ty][((x+xDelta) / Tile.WIDTH)].isSolid()) &&
                    !(worldMap[ty][((x+xDelta+Tile.WIDTH) / Tile.WIDTH)].isSolid()) ) {
                y += yDelta;
                handler.getGame().getGameCamera().move(0, yDelta);
            }
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