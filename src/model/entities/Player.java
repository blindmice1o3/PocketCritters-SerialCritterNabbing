package model.entities;

import main.Handler;
import main.gfx.Assets;
import model.states.StateManager;
import model.tiles.TallGrassTile;
import model.tiles.Tile;

import java.awt.*;
import java.util.Random;

public class Player {

    private Handler handler;
    protected int x, y;
    protected Rectangle bounds;
    private int xDelta, yDelta;
    private int moveSpeed;



    public Player(Handler handler) {
        this.handler = handler;

        x = 1104;
        y = 3312;

        bounds = new Rectangle(2, 2, 12, 12);

        moveSpeed = 16;
    } // **** end model.entities.Player() constructor ****

    public void tick() {
        moveX();
        moveY();

        xDelta = 0;
        yDelta = 0;
    }

    private void checkTallGrassTileCollision(TallGrassTile tile) {
        if (tile.getCurrentPhase() == TallGrassTile.Phase.ACTIVE) {
            ///////////////////////////////////////////////////
            tile.setCurrentPhase(TallGrassTile.Phase.INACTIVE);
            ///////////////////////////////////////////////////

            //Random r = new Random();

            //if (r.nextInt(4) < 1) {
            Object[] args = { this };
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            StateManager.change("BattleState", args);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //}
        }
    }
    private void moveX() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING LEFT
        if (xDelta < 0) {
            int tx = (int)((x+bounds.x+xDelta) / Tile.WIDTH);                                        //LEFT

            //if top-LEFT AND bottom-LEFT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-LEFT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-LEFT


                if ( worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's top-LEFT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] );
                } else if ( worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's bottom-LEFT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] );
                }


                //moves Player's x-position.
                x += xDelta;
                //moves GameCamera's x-position.
                handler.getGameCamera().move(xDelta, 0);
            }
        }
        //MOVING RIGHT
        else if (xDelta > 0) {
            int tx = (int)((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH);                             //RIGHT

            //if top-RIGHT AND bottom-RIGHT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-RIGHT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-RIGHT


                if ( worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's top-RIGHT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] );
                } else if ( worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's bottom-RIGHT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] );
                }


                //moves Player's x-position.
                x += xDelta;
                //moves GameCamera's x-position.
                handler.getGameCamera().move(xDelta, 0);
            }
        }
    }

    private void moveY() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING UP
        if (yDelta < 0) {
            int ty = (int)((y+bounds.y+yDelta) / Tile.HEIGHT);                                       //TOP

            //if TOP-left AND TOP-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //TOP-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {     //TOP-RIGHT


                if ( worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's TOP-left.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] );
                } else if ( worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's TOP-right.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] );
                }


                //moves Player's y-position.
                y += yDelta;
                //moves GameCamera's y-position.
                handler.getGameCamera().move(0, yDelta);
            }
        }
        //MOVING DOWN
        else if (yDelta > 0) {
            int ty = (int)((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT);                           //BOTTOM

            //if BOTTOM-left AND BOTTOM-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //BOTTOM-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {     //BOTTOM-RIGHT


                if ( worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's BOTTOM-left.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] );
                } else if ( worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's BOTTOM-right.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] );
                }


                //moves Player's y-position.
                y += yDelta;
                //moves GameCamera's y-position.
                handler.getGameCamera().move(0, yDelta);
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

    public void setXDelta(int xDelta) {
        this.xDelta = xDelta;
    }

    public void setYDelta(int yDelta) {
        this.yDelta = yDelta;
    }

    public int getMoveSpeed() { return moveSpeed; }

} // **** end model.entities.Player class ****