package model.entities.nabbers;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class James extends Player {

    private Map<String, Animation> anim;

    public James(Handler handler) {
        super(handler);

        initAnimations();

        x = 288-32-8;
        y = 256-32-32;
    }

    private void initAnimations() {
        anim = new HashMap<String, Animation>();

        anim.put("up", new Animation(150, Assets.jamesUp));
        anim.put("down", new Animation(150, Assets.jamesDown));
        anim.put("left", new Animation(150, Assets.jamesLeft));
        anim.put("right", new Animation(150, Assets.jamesRight));
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
            return Assets.jamesDown[0];
        }
    }

} // **** end James class ****