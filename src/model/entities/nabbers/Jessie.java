package model.entities.nabbers;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Jessie extends Player {

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
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimationFrame(), x, y, 32, 32, null);
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

} // **** end Jessie class ****