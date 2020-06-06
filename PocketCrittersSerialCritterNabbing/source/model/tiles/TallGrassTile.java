package model.tiles;

public class TallGrassTile extends NonSolidTile {

    public enum Phase { ACTIVE, INACTIVE; }
    private Phase currentPhase;

    public TallGrassTile(int x, int y) {
        super(x, y);

        currentPhase = Phase.ACTIVE;
    } // **** end TallGrassTile(int, int) constructor ****

    public Phase getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(Phase currentPhase) { this.currentPhase = currentPhase; }

} // **** end TallGrassTile class ****