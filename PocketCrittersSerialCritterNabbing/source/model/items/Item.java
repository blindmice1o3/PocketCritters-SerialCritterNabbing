package model.items;

import main.Handler;
import model.entities.critters.Critter;

import java.io.Serializable;

public abstract class Item
        implements Serializable {

    protected transient Handler handler;
    private Identifier identifier;
    protected boolean active;
    private int quantity;

    public Item(Handler handler, Identifier identifier){
        this.handler = handler;
        this.identifier = identifier;
        active = true;
        quantity = 1;
    }

    public abstract void execute(Critter targetedCritter);

    // GETTERS AND SETTERS

    public void setHandler(Handler handler) { this.handler = handler; }

    public Identifier getIdentifier() { return identifier; }

    public boolean isActive() { return active; }

    public int getQuantity() { return quantity; }

    public enum Identifier {
        //TODO: single-use (consumable) items vs multi-use (non-consumable) items.
        /*
        HM == Hidden Machine
        TM == Technical Machine
        */
        //TM_XX, HM_XX,
        CRITTER_NET,
        PARCEL_TO_BE_DELIVERED,
        TOWN_MAP,
        OLD_AMBER_FOSSIL,
        POTION,
        ANTIDOTE,
        PARLYZ_HEAL,
        BURN_HEAL,
        SLEEP_SMELLING_SALT,
        BAIL_BINER_ROPE,
        BICYCLE;
    }



    /*
    "In all core series games since Generation III, vitamins that affect stats increase a Pokémon's
    EVs for a given stat by 10 EV points,
    but can't increase the total EVs beyond a maximum of 510.
    Between Generations III and VII, vitamins will also have no effect on an individual stat that
    already has 100 or more EVs.
    Prior to Generation III, effort values were based on a system of
    stat experience and vitamins increased a stat by 2560 EV points,
    but could only be used until the stat had 25600 EVs."
    */

}