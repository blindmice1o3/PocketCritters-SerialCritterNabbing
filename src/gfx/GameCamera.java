package gfx;

public class GameCamera {

    private float xOffset, yOffset;

    public GameCamera(float xOffset, float yOffSet) {
        this.xOffset = xOffset;
        this.yOffset = yOffSet;
    } // **** end GameCamera(float, float) constructor

    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    // GETTERS AND SETTERS

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

} // **** end GameCamera class ****