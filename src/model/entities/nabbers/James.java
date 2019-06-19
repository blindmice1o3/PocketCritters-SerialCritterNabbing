package model.entities.nabbers;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;
import model.tiles.TallGrassTile;
import model.tiles.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class James extends Player {

    private Map<String, Animation> anim;
    private Random rand;
    int xScreenPosition = 288-32-32gi;
    int yScreenPosition = 256-32-32;

    public James(Handler handler) {
        super(handler);

        x = 1104-16-16;
        y = 3312-16-16;

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

    int targetTime = 1000;
    long lastTime = System.currentTimeMillis();
    long nowTime = lastTime;
    int elapsedTime;
    @Override
    public void tick() {
        for (Animation animation : anim.values()) {
            animation.tick();
        }

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

            moveX();
            moveY();

            elapsedTime = 0;
        }
        lastTime = nowTime;

        xDelta = 0;
        yDelta = 0;
    }

    @Override
    protected void moveX() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING LEFT
        if (xDelta < 0) {
            int tx = (int)((x+bounds.x+xDelta) / Tile.WIDTH);                                        //LEFT

            //if top-LEFT AND bottom-LEFT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-LEFT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-LEFT

                //moves Player's x-position.
                x += xDelta;
                xScreenPosition +=(2*xDelta);
            }
        }
        //MOVING RIGHT
        else if (xDelta > 0) {
            int tx = (int)((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH);                             //RIGHT

            //if top-RIGHT AND bottom-RIGHT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-RIGHT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-RIGHT

                //moves Player's x-position.
                x += xDelta;
                xScreenPosition += (2*xDelta);
            }
        }
    }

    @Override
    protected void moveY() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING UP
        if (yDelta < 0) {
            int ty = (int)((y+bounds.y+yDelta) / Tile.HEIGHT);                                       //TOP

            //if TOP-left AND TOP-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //TOP-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {     //TOP-RIGHT

                //moves Player's y-position.
                y += yDelta;
                yScreenPosition += (2*yDelta);
            }
        }
        //MOVING DOWN
        else if (yDelta > 0) {
            int ty = (int)((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT);                           //BOTTOM

            //if BOTTOM-left AND BOTTOM-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x+xDelta) / Tile.WIDTH)].isSolid()) &&                    //BOTTOM-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH)].isSolid()) ) {     //BOTTOM-RIGHT

                //moves Player's y-position.
                y += yDelta;
                yScreenPosition += (2*yDelta);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimationFrame(), xScreenPosition, yScreenPosition, 32, 32, null);
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

} // **** end James class ****