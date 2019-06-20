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

    private Map<String, Animation> anim;

    public Jessie(Handler handler) {
        super(handler);

        initAnimations();

        x = 288+8;
        y = 256-32-32;
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

        xDelta = 0;
        yDelta = 0;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimationFrame(), x, y, 32, 32, null);

        //////////////////////////////////////////////////////////////////////////////
        g.setColor(Color.RED);
        g.fillRect(xScreenPosition, yScreenPosition, (2* Tile.WIDTH), (2*Tile.HEIGHT));

        g.setColor(Color.GREEN);
        g.fillRect(x, y, Tile.WIDTH, Tile.HEIGHT);
        //////////////////////////////////////////////////////////////////////////////
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
} // **** end Jessie class ****