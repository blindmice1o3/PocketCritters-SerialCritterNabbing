package model.entities.nabbers;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;
import model.entities.critters.Critter;
import model.items.Item;
import model.states.StateManager;
import model.tiles.TallGrassTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {

    public enum DirectionFacing { LEFT, RIGHT, UP, DOWN; }

    protected Handler handler;
    private Map<String, Animation> anim;

    protected int x, y;
    protected int xDelta, yDelta;
    protected int moveSpeed;
    protected int xScreenPosition, yScreenPosition;

    protected DirectionFacing directionFacing;
    protected Rectangle bounds;

    private ArrayList<Item> inventory;
    private Critter[] critterBeltList;

    public Player(Handler handler) {
        this.handler = handler;

        initAnimations();

        x = 1104;
        y = 3312;
        xScreenPosition = 288;
        yScreenPosition = 256;

        directionFacing = DirectionFacing.DOWN;
        bounds = new Rectangle(2, 2, 12, 12);

        moveSpeed = Tile.WIDTH;

        inventory = new ArrayList<Item>();
        critterBeltList = new Critter[6];
    } // **** end model.entities.nabbers.Player() constructor ****

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

        if (xDelta < 0) {
            directionFacing = DirectionFacing.LEFT;
        } else if (xDelta > 0) {
            directionFacing = DirectionFacing.RIGHT;
        } else if (yDelta < 0) {
            directionFacing = DirectionFacing.UP;
        } else if (yDelta > 0) {
            directionFacing = DirectionFacing.DOWN;
        }

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

    protected void moveY() {
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
        g.drawImage(currentAnimationFrame(),
                xScreenPosition, yScreenPosition, (2*Tile.WIDTH), (2*Tile.HEIGHT),
                null);
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

} // **** end model.entities.nabbers.Player class ****