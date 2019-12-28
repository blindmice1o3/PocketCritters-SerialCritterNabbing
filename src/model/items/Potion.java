package model.items;

import main.Handler;
import model.entities.critters.Critter;
import model.entities.critters.stats.StatsModule;

public class Potion extends Item {

    private final int hpStash = 10;

    public Potion(Handler handler) {
        super(handler, Identifier.POTION);
    } // **** end Potion(Handler) constructor ****

    @Override
    public void execute(Critter targetedCritter) {
        System.out.println("Potion.execute(Critter).");

        StatsModule targetedCritterStats = targetedCritter.getStatsModule();
        int hpEffectiveMax = targetedCritterStats.getStatsEffectiveMap().get(StatsModule.Type.HP);
        int hpEffectiveCurrent = targetedCritter.getHpEffectiveCurrent();

        //////////////////////////////
        hpEffectiveCurrent += hpStash;
        //////////////////////////////

        if (hpEffectiveCurrent > hpEffectiveMax) {
            hpEffectiveCurrent = hpEffectiveMax;
        }

        //////////////////////////////////////////////////////////
        targetedCritter.setHpEffectiveCurrent(hpEffectiveCurrent);
        //////////////////////////////////////////////////////////

        active = false;
    }

} // **** end Potion class ****