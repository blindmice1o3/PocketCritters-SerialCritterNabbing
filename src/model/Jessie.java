package model;

import main.Handler;

import java.awt.*;

public class Jessie extends Player {

    public Jessie(Handler handler) {
        super(handler);

        x = 288+8;
        y = 256-32-32;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.jessieDown[0], x, y, 32, 32, null);
    }

} // **** end Jessie class ****