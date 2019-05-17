package tiles;

public class SolidGenericTile {

    private boolean solid;

    public SolidGenericTile() {
        solid = true;
    } // **** end SolidGenericTile() constructor ****

    // GETTERS AND SETTERS

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

} // **** end SolidGenericTile class ****