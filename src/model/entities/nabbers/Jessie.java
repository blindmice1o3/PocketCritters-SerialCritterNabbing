package model.entities.nabbers;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;
import model.entities.Player;
import model.entities.critters.Critter;
import model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Jessie extends Player
        implements INabber {

    private transient Map<String, Animation> anim;

    private int x, y;
    private int xScreenPosition, yScreenPosition;
    private transient int xDelta, yDelta;

    private DirectionFacing directionFacing;

    public Jessie(Handler handler) {
        super(handler);

        x = 1104;
        y = 3312-Tile.HEIGHT;
        xScreenPosition = 288;
        yScreenPosition = 256-(2*Tile.HEIGHT);

        directionFacing = DirectionFacing.DOWN;

        initAnimations();
    }

    private void initAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("up", new Animation(150, Assets.jessieUp));
        anim.put("down", new Animation(150, Assets.jessieDown));
        anim.put("left", new Animation(150, Assets.jessieLeft));
        anim.put("right", new Animation(150, Assets.jessieRight));
    }

    @Override
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

        //xScreenPosition +=(2*xDelta);
        moveX();
        //yScreenPosition += (2*yDelta);
        moveY();

        xDelta = 0;
        yDelta = 0;
    }

    @Override
    protected void moveX() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        if ( (worldMap[y/Tile.HEIGHT][x/Tile.WIDTH].isSolid()) ||
                (worldMap[y/Tile.HEIGHT][x/Tile.WIDTH] == null) ) {
            return;
        }

        //MOVING LEFT
        if (xDelta < 0) {
            int tx = (int)((x+bounds.x+xDelta) / Tile.WIDTH);                                           //LEFT

            //if top-LEFT AND bottom-LEFT corners of sprite is moving into a NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                      //TOP-LEFT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {    //BOTTOM-LEFT

                //moves (back-end) x-position.
                x += xDelta;
            }
        }
        //MOVING RIGHT
        else if (xDelta > 0) {
            int tx = (int)((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH);                              //RIGHT

            //if top-RIGHT AND bottom-RIGHT corners of sprite is moving into a NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                      //TOP-RIGHT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {    //BOTTOM-RIGHT

                //moves (back-end) x-position.
                x += xDelta;
            }
        }
    }

    @Override
    protected void moveY() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        if ( (worldMap[y/Tile.HEIGHT][x/Tile.WIDTH].isSolid()) ||
                (worldMap[y/Tile.HEIGHT][x/Tile.WIDTH] == null) ) {
            return;
        }

        //MOVING UP
        if (yDelta < 0) {
            int ty = (int)((y+bounds.y+yDelta) / Tile.HEIGHT);                                          //TOP

            //if TOP-left AND TOP-right corners of sprite is moving into a NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                       //TOP-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {      //TOP-RIGHT

                //moves (back-end) y-position.
                y += yDelta;
            }
        }
        //MOVING DOWN
        else if (yDelta > 0) {
            int ty = (int)((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT);                            //BOTTOM

            //if BOTTOM-left AND BOTTOM-right corners of sprite is moving into a NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                       //BOTTOM-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {      //BOTTOM-RIGHT

                //moves (back-end) y-position.
                y += yDelta;
            }
        }
    }

    @Override
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
            return Assets.jessieDown[0];
        }
    }

    @Override
    public void nab(Critter critter) {

    }

    @Override
    public void setXDelta(int xDelta) {
        this.xDelta = xDelta;
    }

    @Override public void setYDelta(int yDelta) {
        this.yDelta = yDelta;
    }

    @Override
    public void regroup() {

    }

    // GETTERS AND SETTERS

    public void setxScreenPosition(int xScreenPosition) { this.xScreenPosition = xScreenPosition; }

    public void setyScreenPosition(int yScreenPosition) { this.yScreenPosition = yScreenPosition; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

} // **** end Jessie class ****