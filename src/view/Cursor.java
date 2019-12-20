package view;

import main.Handler;
import main.gfx.Assets;

import java.awt.*;

public class Cursor {

    /**
     * xOffset will be (xPanel + xOffsetCursor).
     * yOffset will be (yPanel + yOffsetCursor).
     */
    private final int xOffset, yOffset;
    private final int lineHeight;

    //is relative to the index of blahBlahCurrent.
    private int x, y;

    public Cursor(int xOffset, int yOffset, int lineHeight) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.lineHeight = lineHeight;

        updateCursorPosition(0);
    } // **** end Cursor(int, int, int) constructor ****

    public void updateCursorPosition(int currentIndexBlahBlah) {
        x = xOffset;
        y = yOffset + (currentIndexBlahBlah * lineHeight);
    }

    public void render(Graphics g) {
        g.drawImage(Assets.cursorSprite, x, y, 20, 20, null);
//        g.drawImage(Assets.critterBallSprite, x, y, 20, 20, null);
    }

    // GETTERS AND SETTERS

    public int getX() { return x; }

    public int getY() { return y; }

} // **** end Cursor class ****