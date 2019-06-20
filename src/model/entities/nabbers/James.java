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

    private Map<String, Animation> anim;
    private Random rand;
    private int xScreenPosition, yScreenPosition;

    public James(Handler handler) {
        super(handler);

        x = 1104-Tile.WIDTH-Tile.WIDTH;
        y = 3312-Tile.HEIGHT-Tile.HEIGHT;
        xScreenPosition = 288-(2*Tile.WIDTH)-(2*Tile.HEIGHT);
        yScreenPosition = 256-(2*Tile.WIDTH)-(2*Tile.HEIGHT);

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
            moveX();
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
        xScreenPosition +=(2*xDelta);
        moveX();
        yScreenPosition += (2*yDelta);
        moveY();

        xDelta = 0;
        yDelta = 0;
    }

    @Override
    protected void moveX() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        if ( (worldMap[x/Tile.WIDTH][x/Tile.HEIGHT].isSolid()) ||
                (worldMap[x/Tile.WIDTH][x/Tile.HEIGHT] == null) ) {
            return;
        }

        //MOVING LEFT
        if (xDelta < 0) {
            int tx = (int)((x+bounds.x+xDelta) / Tile.WIDTH);                                        //LEFT

            //if top-LEFT AND bottom-LEFT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y+yDelta) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-LEFT
                    !(worldMap[((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-LEFT

                //moves Player's x-position.
                x += xDelta;

                //check within game screen
                if ( ((x+xDelta+bounds.x) < handler.getGameCamera().getxOffset0()) ||
                        ((x+xDelta+bounds.x+bounds.width) > handler.getGameCamera().getxOffset1()) ||
                        ((y+yDelta+bounds.y) < handler.getGameCamera().getyOffset0()) ||
                        ((y+yDelta+bounds.y+bounds.height) > handler.getGameCamera().getyOffset1()) ) {
                    x = super.x - 32;
                    y = super.y - 32;
                    xScreenPosition = super.xScreenPosition - 64;
                    yScreenPosition = super.yScreenPosition - 64;
                }

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

                //check within game screen
                if ( ((x+xDelta+bounds.x) < handler.getGameCamera().getxOffset0()) ||
                        ((x+xDelta+bounds.x+bounds.width) > handler.getGameCamera().getxOffset1()) ||
                        ((y+yDelta+bounds.y) < handler.getGameCamera().getyOffset0()) ||
                        ((y+yDelta+bounds.y+bounds.height) > handler.getGameCamera().getyOffset1()) ) {
                    x = super.x - 32;
                    y = super.y - 32;
                    xScreenPosition = super.xScreenPosition - 64;
                    yScreenPosition = super.yScreenPosition - 64;
                }

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

                //check within game screen
                if ( ((x+xDelta+bounds.x) < handler.getGameCamera().getxOffset0()) ||
                        ((x+xDelta+bounds.x+bounds.width) > handler.getGameCamera().getxOffset1()) ||
                        ((y+yDelta+bounds.y) < handler.getGameCamera().getyOffset0()) ||
                        ((y+yDelta+bounds.y+bounds.height) > handler.getGameCamera().getyOffset1()) ) {
                    x = super.x - 32;
                    y = super.y - 32;
                    xScreenPosition = super.xScreenPosition - 64;
                    yScreenPosition = super.yScreenPosition - 64;
                }

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

                //check within game screen
                if ( ((x+xDelta+bounds.x) < handler.getGameCamera().getxOffset0()) ||
                        ((x+xDelta+bounds.x+bounds.width) > handler.getGameCamera().getxOffset1()) ||
                        ((y+yDelta+bounds.y) < handler.getGameCamera().getyOffset0()) ||
                        ((y+yDelta+bounds.y+bounds.height) > handler.getGameCamera().getyOffset1()) ) {
                    x = super.x - 32;
                    y = super.y - 32;
                    xScreenPosition = super.xScreenPosition - 64;
                    yScreenPosition = super.yScreenPosition - 64;
                }

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
    public void regroup() {

    }
} // **** end James class ****