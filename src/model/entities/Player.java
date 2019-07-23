package model.entities;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;
import model.entities.critters.Critter;
import model.entities.nabbers.INabber;
import model.items.Item;
import model.states.StateManager;
import model.tiles.TallGrassTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player
        implements Serializable {

    public enum DirectionFacing { LEFT, RIGHT, UP, DOWN; }

    protected transient Handler handler;
    private transient Map<String, Animation> anim;

    private int x, y;
    private int xScreenPosition, yScreenPosition;
    private transient int xDelta, yDelta;
    protected transient int moveSpeed;

    private DirectionFacing directionFacing;
    protected transient Rectangle bounds;

    private transient ArrayList<Item> inventory;
    private transient Critter[] critterBeltList;
    //////////////////////////////////////
    private transient ArrayList<INabber> nabberList;
    //////////////////////////////////////

    public void addINabber(INabber nabber) {
        nabberList.add(nabber);
    }
    public void removeINabber(INabber nabber) {
        nabberList.remove(nabber);
    }

    public Player(Handler handler) {
        this.handler = handler;

        initAnimations();

        x = 1104;
        y = 3312;
        xScreenPosition = 288;
        yScreenPosition = 256;

        directionFacing = DirectionFacing.DOWN;
        bounds = new Rectangle(0, 0, Tile.WIDTH, Tile.HEIGHT);

        moveSpeed = Tile.WIDTH;

        inventory = new ArrayList<Item>();
        critterBeltList = new Critter[6];
        nabberList = new ArrayList<INabber>();
    } // **** end model.entities.Player() constructor ****

    private void initAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("down", new Animation(120, Assets.pikachuDown));
        anim.put("up", new Animation(120, Assets.pikachuUp));
        anim.put("left", new Animation(120, Assets.pikachuLeft));
        anim.put("right", new Animation(120, Assets.pikachuRight));
    }

    public void tick() {
        for (Animation animation : anim.values()) {
            animation.tick();
        }

        moveX();    //this sets xDelta for ArrayList<INabber> nabber and game camera.
        moveY();    //this sets yDelta for ArrayList<INabber> nabber and game camera.

        for (INabber nabber : nabberList) {
            nabber.tick();
        }
        handler.getGameCamera().move();

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
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            Object[] args = { this };
            StateManager.change("BattleState", args);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //}
        }
    }
    protected void moveX() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING LEFT
        if (xDelta < 0) {
            int tx = (int)((x+bounds.x+xDelta) / Tile.WIDTH);                                        //LEFT

            //if top-LEFT AND bottom-LEFT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-LEFT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-LEFT

                /////////////////////////////////////////////
                for (INabber nabber : nabberList) {
                    nabber.setXDelta(xDelta);
                }
                ////////////////////////////////////////////

                if ( worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's top-LEFT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] );
                } else if ( worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's bottom-LEFT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] );
                }

                /////////////////////////////////////////
                //moves Player's x-position.
                x += xDelta;
                handler.getGameCamera().setXDelta(xDelta);
                directionFacing = DirectionFacing.LEFT;
                /////////////////////////////////////////
                //moves GameCamera's x-position.
                //handler.getGameCamera().move(xDelta, 0);
            }
        }
        //MOVING RIGHT
        else if (xDelta > 0) {
            int tx = (int)((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH);                             //RIGHT

            //if top-RIGHT AND bottom-RIGHT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-RIGHT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-RIGHT

                ////////////////////////////////////////////
                for (INabber nabber : nabberList) {
                    nabber.setXDelta(xDelta);
                }
                ////////////////////////////////////////////

                if ( worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's top-RIGHT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx] );
                } else if ( worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's bottom-RIGHT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx] );
                }

                ///////////////////////////////////////////
                //moves Player's x-position.
                x += xDelta;
                handler.getGameCamera().setXDelta(xDelta);
                directionFacing = DirectionFacing.RIGHT;
                ///////////////////////////////////////////
                //moves GameCamera's x-position.
                //handler.getGameCamera().move(xDelta, 0);
            }
        }
    }

    protected void moveY() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING UP
        if (yDelta < 0) {
            int ty = (int)((y+bounds.y+yDelta) / Tile.HEIGHT);                                       //TOP

            //if TOP-left AND TOP-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //TOP-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {     //TOP-RIGHT

                /////////////////////////////////////////////
                for (INabber nabber : nabberList) {
                    nabber.setYDelta(yDelta);
                }
                /////////////////////////////////////////////

                if ( worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's TOP-left.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] );
                } else if ( worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's TOP-right.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] );
                }

                //////////////////////////////////////
                //moves Player's y-position.
                y += yDelta;
                handler.getGameCamera().setYDelta(yDelta);
                directionFacing = DirectionFacing.UP;
                //////////////////////////////////////
                //moves GameCamera's y-position.
                //handler.getGameCamera().move(0, yDelta);
            }
        }
        //MOVING DOWN
        else if (yDelta > 0) {
            int ty = (int)((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT);                           //BOTTOM

            //if BOTTOM-left AND BOTTOM-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //BOTTOM-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {     //BOTTOM-RIGHT

                //////////////////////////////////////////////
                for (INabber nabber : nabberList) {
                    nabber.setYDelta(yDelta);
                }
                //////////////////////////////////////////////

                if ( worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's BOTTOM-left.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)] );
                } else if ( worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's BOTTOM-right.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)] );
                }

                ////////////////////////////////////////
                //moves Player's y-position.
                y += yDelta;
                handler.getGameCamera().setYDelta(yDelta);
                directionFacing = DirectionFacing.DOWN;
                ////////////////////////////////////////
                //moves GameCamera's y-position.
                //handler.getGameCamera().move(0, yDelta);
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(currentAnimationFrame(),
                xScreenPosition, yScreenPosition, (2*Tile.WIDTH), (2*Tile.HEIGHT),
                null);

        ////////////////////////////////////
        for (INabber nabber : nabberList) {
            nabber.render(g);
        }
        ////////////////////////////////////
    }

    private BufferedImage currentAnimationFrame() {
        //getInput()
        if (directionFacing == DirectionFacing.UP) {
            return anim.get("up").getCurrentFrame();
        } else if (directionFacing == DirectionFacing.DOWN) {
            return anim.get("down").getCurrentFrame();
        } else if (directionFacing == DirectionFacing.LEFT) {
            return anim.get("left").getCurrentFrame();
        } else if (directionFacing == DirectionFacing.RIGHT) {
            return anim.get("right").getCurrentFrame();
        }
        //STANDING-STILL
        else {
            return Assets.pikachuDown[0];
        }
    }

    // GETTERS & SETTERS

    public int getXDelta() { return xDelta; }

    public void setXDelta(int xDelta) {
        this.xDelta = xDelta;
    }

    public int getYDelta() { return yDelta; }

    public void setYDelta(int yDelta) {
        this.yDelta = yDelta;
    }

    public int getMoveSpeed() { return moveSpeed; }

    public DirectionFacing getDirectionFacing() { return directionFacing; }

    public void setDirectionFacing(DirectionFacing directionFacing) { this.directionFacing = directionFacing; }

    public ArrayList<Item> getInventory() { return inventory; }

    public Critter[] getCritterBeltList() { return critterBeltList; }

} // **** end model.entities.Player class ****