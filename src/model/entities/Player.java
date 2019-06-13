package model.entities;

import main.Handler;
import main.gfx.Assets;
import model.tiles.Tile;

import java.awt.*;

public class Player {

    private Handler handler;
    protected int x, y;
    //protected Rectangle bounds;
    private int xDelta, yDelta;
    private int moveSpeed;

    Tile[][] worldMap;

    public Player(Handler handler) {
        this.handler = handler;

        x = 1104;
        y = 3312;

        //bounds = new Rectangle(2, 2, 12, 12);

        moveSpeed = 2;
    } // **** end model.entities.Player() constructor ****

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
        if (worldMap == null) {
            worldMap = handler.getGame().getWorldMapTileCollisionDetection();
        }

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
        //MOVING LEFT
        if (xDelta < 0) {
            int tx = (int)((x+xDelta) / Tile.WIDTH);                                        //LEFT

            if ( !(worldMap[((y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-LEFT
                    !(worldMap[((y+yDelta+Tile.HEIGHT) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-LEFT
                x += xDelta;
                handler.getGame().getGameCamera().move(xDelta, 0);
            }
        }
        //MOVING RIGHT
        else if (xDelta > 0) {
            int tx = (int)((x+xDelta+Tile.WIDTH) / Tile.WIDTH);                             //RIGHT

            if ( !(worldMap[((y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-RIGHT
                    !(worldMap[((y+yDelta+Tile.HEIGHT) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-RIGHT
                x += xDelta;
                handler.getGame().getGameCamera().move(xDelta, 0);
            }
        }
    }

    private void moveY() {
        //MOVING UP
        if (yDelta < 0) {
            int ty = (int)((y+yDelta) / Tile.HEIGHT);                                       //TOP

            if ( !(worldMap[ty][((x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //TOP-LEFT
                    !(worldMap[ty][((x+xDelta+Tile.WIDTH) / Tile.WIDTH)].isSolid()) ) {     //TOP-RIGHT
                y += yDelta;
                handler.getGame().getGameCamera().move(0, yDelta);
            }
        }
        //MOVING DOWN
        else if (yDelta > 0) {
            int ty = (int)((y+yDelta+Tile.HEIGHT) / Tile.HEIGHT);                           //BOTTOM

            if ( !(worldMap[ty][((x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //BOTTOM-LEFT
                    !(worldMap[ty][((x+xDelta+Tile.WIDTH) / Tile.WIDTH)].isSolid()) ) {     //BOTTOM-RIGHT
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

} // **** end model.entities.Player class ****