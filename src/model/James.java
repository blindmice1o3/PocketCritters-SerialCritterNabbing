package model;

import java.awt.*;

public class James extends Player {

    public James() {
        x = 288-32-8;
        y = 256-32-32;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.jamesDown[0], x, y, 32, 32, null);
    }

} // **** end James class ****