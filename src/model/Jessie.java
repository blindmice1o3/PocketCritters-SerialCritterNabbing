package model;

import java.awt.*;

public class Jessie extends Player {

    public Jessie() {
        x = 288+8;
        y = 256-32-32;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.jessieDown[0], x, y, 32, 32, null);
    }

} // **** end Jessie class ****