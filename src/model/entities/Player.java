package model.entities;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;
import model.entities.critters.Critter;
import model.entities.nabbers.INabber;
import model.entities.nabbers.James;
import model.entities.nabbers.Jessie;
import model.items.Item;
import model.states.StateManager;
import model.states.game.GameState;
import model.states.game.world.RoomPlayer;
import model.states.game.world.WorldManager;
import model.states.game.world.WorldMap;
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
        //bounds = new Rectangle(0, 0, Tile.WIDTH, Tile.HEIGHT);
        bounds = new Rectangle(0+1, 0+1, Tile.WIDTH-2, Tile.HEIGHT-2);

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
            //Object[] args = { this };
            handler.getStateManager().push(
                    handler.getStateManager().getIState("BattleState"),
                    null);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            //}
        }
    }

    private boolean checkTransferPointsCollision(Rectangle collisionBoundsFuture) {
        Map<String, Rectangle> transferPoints = ((GameState)handler.getStateManager().getIState("GameState")).getWorldManager().getCurrentWorld().getTransferPoints();
        for (String identifier : transferPoints.keySet()) {
            System.out.println("checking transfer points.");
            if (transferPoints.get(identifier).intersects(collisionBoundsFuture)) {
                System.out.println("CHANGING WORLD!!!!!!!!! (to @@@ " + identifier + " @@@)");
                WorldManager worldManager = ((GameState)handler.getStateManager().getIState("GameState")).getWorldManager();
                if (worldManager.getIWorld(identifier) != null) {

                    // If we are going to HomePlayer: record player's and game camera's
                    // last location when currentWorld was WorldMap.
                    if ( identifier.equals("HomePlayer") ) {
                        //////////////////////////////////////////////////////////////////////////////////////
                        if (worldManager.getCurrentWorld() instanceof WorldMap) {
                            ((WorldMap) worldManager.getIWorld("WorldMap")).recordLocationPriorTransfer();
                        }
                        //////////////////////////////////////////////////////////////////////////////////////

                        //setting player and game camera to new location and changing currentWorld to HomePlayer.
                        x = 4 * Tile.WIDTH;
                        y = 7 * Tile.HEIGHT;
                        for (INabber nabber : nabberList) {
                            if (nabber instanceof James) {
                                ((James)nabber).setX(3 * Tile.WIDTH);
                                ((James)nabber).setY(7 * Tile.HEIGHT);
                                ((James)nabber).setxScreenPosition( xScreenPosition - (2 * Tile.WIDTH) );
                                ((James)nabber).setyScreenPosition( yScreenPosition );
                            } else if (nabber instanceof Jessie) {
                                ((Jessie)nabber).setX(4 * Tile.WIDTH);
                                ((Jessie)nabber).setY(6 * Tile.HEIGHT);
                                ((Jessie)nabber).setxScreenPosition( xScreenPosition );
                                ((Jessie)nabber).setyScreenPosition( yScreenPosition - (2 * Tile.HEIGHT) );
                            }
                        }
                        handler.getGameCamera().setxOffset0( (-6 * Tile.WIDTH) );
                        handler.getGameCamera().setyOffset0( (-2 * Tile.HEIGHT) );
                        handler.getGameCamera().setxOffset1( ((-6 * Tile.WIDTH) + 320) );
                        handler.getGameCamera().setyOffset1( ((-2 * Tile.HEIGHT) + 272) );

                        ////////
                        worldManager.setCurrentWorld(worldManager.getIWorld(identifier));
                        return true;
                        ////////
                    }
                    // (for now) player going back to WorldMap.
                    else if (identifier.equals("WorldMap")) {
                        ////////////////////////////////////////////////////////////////////////////////////
                        ((WorldMap)worldManager.getIWorld("WorldMap")).loadLocationPriorTransfer();
                        ////////////////////////////////////////////////////////////////////////////////////

                        ////////
                        worldManager.setCurrentWorld(worldManager.getIWorld(identifier));
                        return true;
                        ////////
                    }
                    else if (identifier.equals("RoomPlayer")) {
                        //setting player and game camera to new location and changing currentWorld to RoomPlayer.
                        x = 4 * Tile.WIDTH;
                        y = 7 * Tile.HEIGHT;
                        for (INabber nabber : nabberList) {
                            if (nabber instanceof James) {
                                ((James)nabber).setX(3 * Tile.WIDTH);
                                ((James)nabber).setY(7 * Tile.HEIGHT);
                                ((James)nabber).setxScreenPosition( xScreenPosition - (2 * Tile.WIDTH) );
                                ((James)nabber).setyScreenPosition( yScreenPosition );
                            } else if (nabber instanceof Jessie) {
                                ((Jessie)nabber).setX(4 * Tile.WIDTH);
                                ((Jessie)nabber).setY(6 * Tile.HEIGHT);
                                ((Jessie)nabber).setxScreenPosition( xScreenPosition );
                                ((Jessie)nabber).setyScreenPosition( yScreenPosition - (2 * Tile.HEIGHT) );
                            }
                        }
                        handler.getGameCamera().setxOffset0( (-6 * Tile.WIDTH) );
                        handler.getGameCamera().setyOffset0( (-2 * Tile.HEIGHT) );
                        handler.getGameCamera().setxOffset1( ((-6 * Tile.WIDTH) + 320) );
                        handler.getGameCamera().setyOffset1( ((-2 * Tile.HEIGHT) + 272) );

                        ////////
                        worldManager.setCurrentWorld(worldManager.getIWorld(identifier));
                        return true;
                        ////////
                    }
                    else if ( identifier.equals("HomeRival") ) {
                        //////////////////////////////////////////////////////////////////////////////////////
                        if (worldManager.getCurrentWorld() instanceof WorldMap) {
                            ((WorldMap) worldManager.getIWorld("WorldMap")).recordLocationPriorTransfer();
                        }
                        //////////////////////////////////////////////////////////////////////////////////////

                        //setting player and game camera to new location and changing currentWorld to HomeRival.
                        x = 4 * Tile.WIDTH;
                        y = 7 * Tile.HEIGHT;
                        for (INabber nabber : nabberList) {
                            if (nabber instanceof James) {
                                ((James)nabber).setX(3 * Tile.WIDTH);
                                ((James)nabber).setY(7 * Tile.HEIGHT);
                                ((James)nabber).setxScreenPosition( xScreenPosition - (2 * Tile.WIDTH) );
                                ((James)nabber).setyScreenPosition( yScreenPosition );
                            } else if (nabber instanceof Jessie) {
                                ((Jessie)nabber).setX(4 * Tile.WIDTH);
                                ((Jessie)nabber).setY(6 * Tile.HEIGHT);
                                ((Jessie)nabber).setxScreenPosition( xScreenPosition );
                                ((Jessie)nabber).setyScreenPosition( yScreenPosition - (2 * Tile.HEIGHT) );
                            }
                        }
                        handler.getGameCamera().setxOffset0( (-6 * Tile.WIDTH) );
                        handler.getGameCamera().setyOffset0( (-2 * Tile.HEIGHT) );
                        handler.getGameCamera().setxOffset1( ((-6 * Tile.WIDTH) + 320) );
                        handler.getGameCamera().setyOffset1( ((-2 * Tile.HEIGHT) + 272) );

                        ////////
                        worldManager.setCurrentWorld(worldManager.getIWorld(identifier));
                        return true;
                        ////////
                    }
                    else if ( identifier.equals("Lab") ) {
                        //////////////////////////////////////////////////////////////////////////////////////
                        if (worldManager.getCurrentWorld() instanceof WorldMap) {
                            ((WorldMap) worldManager.getIWorld("WorldMap")).recordLocationPriorTransfer();
                        }
                        //////////////////////////////////////////////////////////////////////////////////////

                        //setting player and game camera to new location and changing currentWorld to HomeRival.
                        x = 5 * Tile.WIDTH;
                        y = 11 * Tile.HEIGHT;
                        for (INabber nabber : nabberList) {
                            if (nabber instanceof James) {
                                ((James)nabber).setX(3 * Tile.WIDTH);
                                ((James)nabber).setY(7 * Tile.HEIGHT);
                                ((James)nabber).setxScreenPosition( xScreenPosition - (2 * Tile.WIDTH) );
                                ((James)nabber).setyScreenPosition( yScreenPosition );
                            } else if (nabber instanceof Jessie) {
                                ((Jessie)nabber).setX(4 * Tile.WIDTH);
                                ((Jessie)nabber).setY(6 * Tile.HEIGHT);
                                ((Jessie)nabber).setxScreenPosition( xScreenPosition );
                                ((Jessie)nabber).setyScreenPosition( yScreenPosition - (2 * Tile.HEIGHT) );
                            }
                        }
                        handler.getGameCamera().setxOffset0( (-5 * Tile.WIDTH) );
                        handler.getGameCamera().setyOffset0( (2 * Tile.HEIGHT) );
                        handler.getGameCamera().setxOffset1( ((-5 * Tile.WIDTH) + 320) );
                        handler.getGameCamera().setyOffset1( ((2 * Tile.HEIGHT) + 272) );

                        ////////
                        worldManager.setCurrentWorld(worldManager.getIWorld(identifier));
                        return true;
                        ////////
                    }

                }
            }
        }

        return false;
    }

    protected void moveX() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING LEFT
        if (xDelta < 0) {
            int tx = (int)((x+bounds.x+xDelta) / Tile.WIDTH);                                        //LEFT

            //if top-LEFT AND bottom-LEFT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-LEFT
                    !(worldMap[((y+bounds.y+bounds.height) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-LEFT
                //CHECKING TallGrassTile
                if ( worldMap[((y+bounds.y) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's top-LEFT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y) / Tile.HEIGHT)][tx] );
                } else if ( worldMap[((y+bounds.y+bounds.height) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's bottom-LEFT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+bounds.height) / Tile.HEIGHT)][tx] );
                }

                //CHECKING TransferPoints
                Rectangle collisionBoundsFuture = new Rectangle(x+bounds.x+xDelta, y+bounds.y, Tile.WIDTH, Tile.HEIGHT);
                boolean transferPointCollision = checkTransferPointsCollision(collisionBoundsFuture);

                /////////////////////////////////////////
                //no collision with transferPoints
                if (!transferPointCollision) {
                    //moves Player's x-position.
                    x += xDelta;
                    directionFacing = DirectionFacing.LEFT;
                    handler.getGameCamera().setXDelta(xDelta);
                    for (INabber nabber : nabberList) {
                        nabber.setXDelta(xDelta);
                    }
                }
                /////////////////////////////////////////
            }
        }
        //MOVING RIGHT
        else if (xDelta > 0) {
            int tx = (int)((x+bounds.x+bounds.width+xDelta) / Tile.WIDTH);                             //RIGHT

            //if top-RIGHT AND bottom-RIGHT corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[((y+bounds.y) / Tile.HEIGHT)][tx].isSolid()) &&                   //TOP-RIGHT
                    !(worldMap[((y+bounds.y+bounds.height) / Tile.HEIGHT)][tx].isSolid()) ) {   //BOTTOM-RIGHT
                //CHECKING TallGrassTile
                if ( worldMap[((y+bounds.y) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's top-RIGHT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y) / Tile.HEIGHT)][tx] );
                } else if ( worldMap[((y+bounds.y+bounds.height) / Tile.HEIGHT)][tx] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's bottom-RIGHT.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[((y+bounds.y+bounds.height) / Tile.HEIGHT)][tx] );
                }

                //CHECKING TransferPoints
                Rectangle collisionBoundsFuture = new Rectangle(x+bounds.x+bounds.width+xDelta, y+bounds.y, Tile.WIDTH, Tile.HEIGHT);
                boolean transferPointCollision = checkTransferPointsCollision(collisionBoundsFuture);

                ///////////////////////////////////////////
                //no collision with transferPoints
                if (!transferPointCollision) {
                    //moves Player's x-position.
                    x += xDelta;
                    directionFacing = DirectionFacing.RIGHT;
                    handler.getGameCamera().setXDelta(xDelta);
                    for (INabber nabber : nabberList) {
                        nabber.setXDelta(xDelta);
                    }
                }
                ///////////////////////////////////////////
            }
        }
    }

    protected void moveY() {
        Tile[][] worldMap = handler.getWorldMapTileCollisionDetection();

        //MOVING UP
        if (yDelta < 0) {
            int ty = (int)((y+bounds.y+yDelta) / Tile.HEIGHT);                                       //TOP

            //if TOP-left AND TOP-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x) / Tile.WIDTH)].isSolid()) &&                    //TOP-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width) / Tile.WIDTH)].isSolid()) ) {     //TOP-RIGHT
                //CHECKING TallGrassTile
                if ( worldMap[ty][((x+bounds.x) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's TOP-left.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x) / Tile.WIDTH)] );
                } else if ( worldMap[ty][((x+bounds.x+bounds.width) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's TOP-right.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+bounds.width) / Tile.WIDTH)] );
                }

                //CHECKING TransferPoints
                Rectangle collisionBoundsFuture = new Rectangle(x+bounds.x, y+bounds.y+yDelta, Tile.WIDTH, Tile.HEIGHT);
                boolean transferPointCollision = checkTransferPointsCollision(collisionBoundsFuture);

                //////////////////////////////////////
                //no collision with transferPoints
                if (!transferPointCollision) {
                    //moves Player's y-position.
                    y += yDelta;
                    directionFacing = DirectionFacing.UP;
                    handler.getGameCamera().setYDelta(yDelta);
                    for (INabber nabber : nabberList) {
                        nabber.setYDelta(yDelta);
                    }
                }
                //////////////////////////////////////
            }
        }
        //MOVING DOWN
        else if (yDelta > 0) {
            int ty = (int)((y+bounds.y+bounds.height+yDelta) / Tile.HEIGHT);                           //BOTTOM

            //if BOTTOM-left AND BOTTOM-right corners of player-sprite moving into NOT solid tile, do stuff.
            if ( !(worldMap[ty][((x+bounds.x) / Tile.WIDTH)].isSolid()) &&                    //BOTTOM-LEFT
                    !(worldMap[ty][((x+bounds.x+bounds.width) / Tile.WIDTH)].isSolid()) ) {     //BOTTOM-RIGHT
                //CHECKING TallGrassTile
                if ( worldMap[ty][((x+bounds.x) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's BOTTOM-left.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x) / Tile.WIDTH)] );
                } else if ( worldMap[ty][((x+bounds.x+bounds.width) / Tile.WIDTH)] instanceof TallGrassTile ) {
                    System.out.println("Checking grass tile to player's BOTTOM-right.");
                    checkTallGrassTileCollision( (TallGrassTile)worldMap[ty][((x+bounds.x+bounds.width) / Tile.WIDTH)] );
                }

                //CHECKING TransferPoints
                Rectangle collisionBoundsFuture = new Rectangle(x+bounds.x, y+bounds.y+bounds.height+yDelta, Tile.WIDTH, Tile.HEIGHT);
                boolean transferPointCollision = checkTransferPointsCollision(collisionBoundsFuture);

                ////////////////////////////////////////
                //no collision with transferPoints
                if (!transferPointCollision) {
                    //moves Player's y-position.
                    y += yDelta;
                    directionFacing = DirectionFacing.DOWN;
                    handler.getGameCamera().setYDelta(yDelta);
                    for (INabber nabber : nabberList) {
                        nabber.setYDelta(yDelta);
                    }
                }
                ////////////////////////////////////////
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

    public int getX() { return x; }

    public void setX(int x) { this.x = x; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }

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