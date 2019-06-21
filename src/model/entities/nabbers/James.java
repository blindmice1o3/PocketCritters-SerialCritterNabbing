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
import java.util.Random;

public class James extends Player
        implements INabber {

    private transient Map<String, Animation> anim;
    private transient Random rand;

    private int x, y;
    private int xScreenPosition, yScreenPosition;

    private DirectionFacing directionFacing;

    public James(Handler handler) {
        super(handler);

        x = 1104-Tile.WIDTH;
        y = 3312-Tile.HEIGHT;
        xScreenPosition = 288-(2*Tile.WIDTH);
        yScreenPosition = 256-(2*Tile.HEIGHT);

        directionFacing = DirectionFacing.DOWN;

        initAnimations();

        rand = new Random();
    }

    private void initAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("up", new Animation(150, Assets.jamesUp));
        anim.put("down", new Animation(150, Assets.jamesDown));
        anim.put("left", new Animation(150, Assets.jamesLeft));
        anim.put("right", new Animation(150, Assets.jamesRight));
    }

    int targetTime = 4000;
    long lastTime = System.currentTimeMillis();
    long nowTime = lastTime;
    int elapsedTime;
    @Override
    public void tick() {
        for (Animation animation : anim.values()) {
            animation.tick();
        }

        //AUTO-MOVEMENT (random)
        nowTime = System.currentTimeMillis();
        elapsedTime += (int)(nowTime - lastTime);
        if (elapsedTime >= targetTime) {
            int randDirection = rand.nextInt(4);

            if (randDirection == 0) {
                xDelta = -moveSpeed;
                directionFacing = DirectionFacing.LEFT;
            } else if (randDirection == 1) {
                xDelta = moveSpeed;
                directionFacing = DirectionFacing.RIGHT;
            } else if (randDirection == 2) {
                yDelta = -moveSpeed;
                directionFacing = DirectionFacing.UP;
            } else if (randDirection == 3) {
                yDelta = moveSpeed;
                directionFacing = DirectionFacing.DOWN;
            }

            //auto-movement based on rand.nextInt(4) method call.
            xScreenPosition +=(2*xDelta);
            moveX();
            yScreenPosition += (2*yDelta);
            moveY();

            elapsedTime = 0;
        }
        lastTime = nowTime;

        //USER-CONTROLLED-MOVEMENT (from GameState.tick()'s setXDelta()/setYDelta() based on KeyManager's input)
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
            return Assets.jamesDown[0];
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
} // **** end James class ****