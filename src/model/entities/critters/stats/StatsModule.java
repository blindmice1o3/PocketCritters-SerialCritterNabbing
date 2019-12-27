package model.entities.critters.stats;

import main.Handler;
import model.entities.critters.Critter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StatsModule {

    public enum Type { ATTACK, DEFENSE, SPEED, SPECIAL, HP; }

    private Handler handler;
    private Critter.Species species;
    private int level;

    /* statsIV: Each of these is used to determine their associated stat, with
    the hpIV determined from the IVs stored for the other four.

    As such, a Pokémon with
    an odd-number Attack IV
    has 8 added to its HP IV,
    an odd-number Defense IV
    has 4 added,
    an odd-number Speed IV
    has 2 added,
    and an odd-number Special IV
    has 1 added.

    IVs are fixed when it is generated by the game
    (i.e. when it is obtained as an Egg from the Pokémon Day Care,
    encountered in the wild, or given to the player by an NPC),
    and CANNOT be changed. */

    //(IV == individual value [GENES]... IVs range from 0-15)
    private final Map<Type, Integer> ivMap;
    /* private final int attackIV, defenseIV, speedIV, specialIV, hpIV; */

    //(EV == effort value [STATs EXPERIENCE]... EP == effort points)
    private Map<Type, Integer> evMap;
    /* private int attackEP, defenseEP, speedEP, specialEP, hpEP; */

    /* DETERMINATION OF STATS:
        hp =        ( ((((base + iv) * 2) + (sqrt(ev) / 4)) * level) / 100 ) + level + 10
        otherStat = ( ((((base + iv) * 2) + (sqrt(ev) / 4)) * level) / 100 ) + 5
        damage = ((((((2 * Level) / 5) + 2) * Power  * (A/D)) / 50) + 2) * Modifier
            Modifier == Targets * Weather * Badge * Critical * random * STAB * Type * Burn * other. */
    // STATS (attackBase, defenseBase, speedBase, and specialBase can be found in Critter.Species)

    //(statsEffective == calculated stats, determined stats, NOT base stats)
    private Map<Type, Integer> statsEffectiveMap;
    /* private int attackEffective, defenseEffective, speedEffective, specialEffective, hpEffective; */

    public StatsModule(Handler handler, Critter.Species species, int level) {
        this.handler = handler;
        this.species = species;
        this.level = level;

        Random random = new Random();
        //IVs
        ivMap = new HashMap<Type, Integer>();
//        ivMap.put(Type.ATTACK, 8);
//        ivMap.put(Type.DEFENSE, 13);
//        ivMap.put(Type.SPECIAL, 9);
//        ivMap.put(Type.SPEED, 5);
        ivMap.put(Type.ATTACK, random.nextInt(16));
        ivMap.put(Type.DEFENSE, random.nextInt(16));
        ivMap.put(Type.SPEED, random.nextInt(16));
        ivMap.put(Type.SPECIAL, random.nextInt(16));
        ivMap.put(Type.HP,  ((ivMap.get(Type.ATTACK) % 2 == 1)    ? 8 : 0) +
                            ((ivMap.get(Type.DEFENSE) % 2 == 1)   ? 4 : 0) +
                            ((ivMap.get(Type.SPEED) % 2 == 1)     ? 2 : 0) +
                            ((ivMap.get(Type.SPECIAL) % 2 == 1)   ? 1 : 0) );

        //EVs (EP == effort points) (Stat Experience, or "Stat Exp")
        evMap = new HashMap<Type, Integer>();
//        evMap.put(Type.ATTACK, 23140);
//        evMap.put(Type.DEFENSE, 17280);
//        evMap.put(Type.SPECIAL, 19625);
//        evMap.put(Type.SPEED, 24795);
//        evMap.put(Type.HP, 22850);
        evMap.put(Type.ATTACK, 0);
        evMap.put(Type.DEFENSE, 0);
        evMap.put(Type.SPEED, 0);
        evMap.put(Type.SPECIAL, 0);
        evMap.put(Type.HP, 0);

        //statsEffective (calculated stats, determined stats, NOT base stats)
        //NOTE: Type.HP uses a different formula than the other stats types.
        statsEffectiveMap = new HashMap<Type, Integer>();
        statsEffectiveMap.put(Type.ATTACK, updateStatsEffective(Type.ATTACK, this.level));
        statsEffectiveMap.put(Type.DEFENSE, updateStatsEffective(Type.DEFENSE, this.level));
        statsEffectiveMap.put(Type.SPEED, updateStatsEffective(Type.SPEED, this.level));
        statsEffectiveMap.put(Type.SPECIAL, updateStatsEffective(Type.SPECIAL, this.level));
        statsEffectiveMap.put(Type.HP, updateHpEffective(this.level));
    } // **** end StatsModule(Handler) constructor ****

    private int updateStatsEffective(Type statsType, int level) {
        int statsTypeEffective = 0;
        int statsBase = 0;
        switch (statsType) {
            case ATTACK:
                statsBase = species.getAttackBase();
                break;
            case DEFENSE:
                statsBase = species.getDefenseBase();
                break;
            case SPEED:
                statsBase = species.getSpeedBase();
                break;
            case SPECIAL:
                statsBase = species.getSpecialBase();
                break;
            default:
                System.out.println("StatsModule.updateStatsEffective(Type, int) switch(statsType) construct's default block.");
                statsBase = 0;
                break;
        }

        statsTypeEffective = (int)((((((statsBase + ivMap.get(statsType)) * 2) + (Math.sqrt(evMap.get(statsType)) / 4)) * level) / 100) + 5);
        return statsTypeEffective;
    }

    private int updateHpEffective(int level) {
        int hpEffective = (int)((((((species.getHpBase() + ivMap.get(Type.HP)) * 2) + (Math.sqrt( evMap.get(Type.HP) ) / 4)) * level) / 100) + level + 10);
        return hpEffective;
    }

    public void consoleOutIVsAndEVs(String nameColloquial) {
        System.out.println(nameColloquial + "'s attackIV: " + ivMap.get(Type.ATTACK) + ".");
        System.out.println(nameColloquial + "'s defenseIV: " + ivMap.get(Type.DEFENSE) + ".");
        System.out.println(nameColloquial + "'s speedIV: " + ivMap.get(Type.SPEED) + ".");
        System.out.println(nameColloquial + "'s specialIV: " + ivMap.get(Type.SPECIAL) + ".");
        System.out.println(nameColloquial + "'s hpIV: " + ivMap.get(Type.HP) + ".");
        System.out.println("////////////////////////////////");
        System.out.println(nameColloquial + "'s attackEP: " + evMap.get(Type.ATTACK) + ".");
        System.out.println(nameColloquial + "'s defenseEP: " + evMap.get(Type.DEFENSE) + ".");
        System.out.println(nameColloquial + "'s speedEP: " + evMap.get(Type.SPEED) + ".");
        System.out.println(nameColloquial + "'s specialEP: " + evMap.get(Type.SPECIAL) + ".");
        System.out.println(nameColloquial + "'s hpEP: " + evMap.get(Type.HP) + ".");
        System.out.println("////////////////////////////////");
        System.out.println(nameColloquial + "'s attackEffective: " + statsEffectiveMap.get(Type.ATTACK) + ".");
        System.out.println(nameColloquial + "'s defenseEffective: " + statsEffectiveMap.get(Type.DEFENSE) + ".");
        System.out.println(nameColloquial + "'s speedEffective: " + statsEffectiveMap.get(Type.SPEED) + ".");
        System.out.println(nameColloquial + "'s specialEffective: " + statsEffectiveMap.get(Type.SPECIAL) + ".");
        System.out.println(nameColloquial + "'s hpEffectiev: " + statsEffectiveMap.get(Type.HP) + ".");
    }

    // GETTERS AND SETTERS


    public Map<Type, Integer> getIvMap() {
        return ivMap;
    }

    public Map<Type, Integer> getEvMap() {
        return evMap;
    }

    public Map<Type, Integer> getStatsEffectiveMap() {
        return statsEffectiveMap;
    }

    /* public int getHpEffective() { return hpEffective; } */

} // *** end StatsModule class ****