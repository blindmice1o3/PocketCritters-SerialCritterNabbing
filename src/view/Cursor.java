package view;

import main.Handler;

public class Cursor {

    private Handler handler;

    /**
     * xOffset will be (xPanel + xOffsetCursor).
     * yOffset will be (yPanel + yOffsetCursor).
     */
    private final int xOffset, yOffset;
    private final int lineHeight;

    //is relative to the index of blahBlahCurrent.
    private int x, y;

    public Cursor(Handler handler, int xOffset, int yOffset, int lineHeight) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.lineHeight = lineHeight;

        updateCursorPosition(0);
    } // **** end Cursor(Handler, int, int, int) constructor ****

    public void updateCursorPosition(int indexBlahBlahCurrent) {
        x = xOffset;
        y = yOffset + (indexBlahBlahCurrent * lineHeight);
    }

} // **** end Cursor class ****