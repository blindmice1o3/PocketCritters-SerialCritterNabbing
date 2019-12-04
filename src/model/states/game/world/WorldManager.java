package model.states.game.world;

import main.Handler;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorldManager {

    private Handler handler;

    private Map<String, IWorld> worldCollection;
    private IWorld currentWorld;

    public WorldManager(Handler handler) {
        this.handler = handler;

        initWorldCollection();
    } // **** end WorldManager(Handler) constructor ****

    private void initWorldCollection() {
        worldCollection = new HashMap<String, IWorld>();
        worldCollection.put("WorldMap", new WorldMap(handler));
        worldCollection.put("HomePlayer", new HomePlayer(handler));
        worldCollection.put("RoomPlayer", new RoomPlayer(handler));
        worldCollection.put("HomeRival", new HomeRival(handler));

        currentWorld = worldCollection.get("WorldMap");
    }

    public void tick(long timeElapsed) {
        currentWorld.tick(timeElapsed);
    }

    public void render(Graphics g) {
        currentWorld.render(g);
    }

    public IWorld getCurrentWorld() {
        return currentWorld;
    }

    public void setCurrentWorld(IWorld currentWorld) {
        this.currentWorld = currentWorld;
    }

    public IWorld getIWorld(String identifier) {
        if (worldCollection.containsKey(identifier)) {
            return worldCollection.get(identifier);
        }

        return null;
    }

    //TODO: enter(Object[] args) and exit() ???

} // **** end WorldManager class ****