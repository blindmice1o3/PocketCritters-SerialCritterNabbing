package view;

import main.gfx.Assets;

import java.awt.*;

public class Cursor {

    /**
     * xOffset will be (xPanel + xOffsetCursor).
     * yOffset will be (yPanel + yOffsetCursor).
     */
    private int xOffset, yOffset;
    private int lineHeight;

    //is relative to the index of blahBlahCurrent.
    private int x, y;
    private int width, height;

    public Cursor(int xOffset, int yOffset, int lineHeight, int width, int height) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.lineHeight = lineHeight;
        this.width = width;
        this.height = height;

        updateCursorPosition(0);
    } // **** end Cursor(int, int, int, int, int) constructor ****

    public void updateCursorPosition(int currentIndexBlahBlah) {
        x = xOffset;
        y = yOffset + (currentIndexBlahBlah * lineHeight);
    }

    public void render(Graphics g) {
        g.drawImage(Assets.cursorSprite, x, y, width, height, null);
//        g.drawImage(Assets.critterBallSprite, x, y, 20, 20, null);
    }

    // GETTERS AND SETTERS

    public int getX() { return x; }

    public int getY() { return y; }

    public int getxOffset() { return xOffset; }

    public void setxOffset(int xOffset) { this.xOffset = xOffset; }

    public int getyOffset() { return yOffset; }

    public void setyOffset(int yOffset) { this.yOffset = yOffset; }

} // **** end Cursor class ****