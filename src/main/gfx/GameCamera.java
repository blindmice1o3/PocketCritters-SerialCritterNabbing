package main.gfx;

public class GameCamera {

    private float xOffset0, yOffset0;
    private float xOffset1, yOffset1;

    private int xDelta, yDelta;

    public GameCamera(float xOffset0, float yOffset0, float xOffset1, float yOffset1) {
        this.xOffset0 = xOffset0;
        this.yOffset0 = yOffset0;

        this.xOffset1 = xOffset1;
        this.yOffset1 = yOffset1;

        xDelta = 0;
        yDelta = 0;
    } // **** end GameCamera(float, float, float, float) constructor

    public void move() {
        xOffset0 += xDelta;
        yOffset0 += yDelta;

        xOffset1 += xDelta;
        yOffset1 += yDelta;

        xDelta = 0;
        yDelta = 0;
    }

    public void move(float xAmt, float yAmt) {
        xOffset0 += xAmt;
        yOffset0 += yAmt;

        xOffset1 += xAmt;
        yOffset1 += yAmt;
    }

    // GETTERS AND SETTERS

    public float getxOffset0() {
        return xOffset0;
    }

    public void setxOffset0(float xOffset0) {
        this.xOffset0 = xOffset0;
    }

    public float getyOffset0() {
        return yOffset0;
    }

    public void setyOffset0(float yOffset0) {
        this.yOffset0 = yOffset0;
    }

    public float getxOffset1() {
        return xOffset1;
    }

    public void setxOffset1(float xOffset1) {
        this.xOffset1 = xOffset1;
    }

    public float getyOffset1() {
        return yOffset1;
    }

    public void setyOffset1(float yOffset1) {
        this.yOffset1 = yOffset1;
    }

    public int getXDelta() { return xDelta; }

    public void setXDelta(int xDelta) { this.xDelta = xDelta; }

    public int getYDelta() { return yDelta; }

    public void setYDelta(int yDelta) { this.yDelta = yDelta; }

} // **** end GameCamera class ****