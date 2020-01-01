package model.entities.critters;

import main.Handler;
import main.gfx.Assets;
import model.entities.critters.levels.ExpLookUpTable;
import model.entities.critters.moves.MovesModule;
import model.entities.critters.stats.StatsModule;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Critter
        implements Serializable {

    public static final int WILD = 65536;

    private transient Handler handler;
    private Species species;
    private int level;

    private String nameColloquial;
    private int idNumberOriginalTrainer;

    private MovesModule movesModule;
    private StatsModule statsModule;

    private int expCurrent;

    private int hpEffectiveCurrent;
    private StatusConditionNonVolatile status;

    public Critter(Handler handler, Species species, int level) {
        this.handler = handler;
        this.species = species;
        this.level = level;

        nameColloquial = this.species.toString().replace('_', ' ');
        idNumberOriginalTrainer = WILD;

        movesModule = new MovesModule(handler);
        statsModule = new StatsModule(handler, this.species, this.level);

        expCurrent = 0;

        hpEffectiveCurrent = statsModule.getStatsEffectiveMap().get(StatsModule.Type.HP);
        status = StatusConditionNonVolatile.OK;
    } // **** end Critter(Handler, Species, int) constructor ****

    public void doDamage(Critter targetCritter, int damage) {
        System.out.println(targetCritter.getNameColloquial() + "'s hpEffectiveCurrent PRIOR TO " +
                "doDamage(Critter, int)'s code-block's body: " + targetCritter.getHpEffectiveCurrent());
        targetCritter.takeDamage(damage);
        System.out.println(targetCritter.getNameColloquial() + "'s hpEffectiveCurrent AFTER " +
                "doDamage(Critter, int)'s code-block's body: " + targetCritter.getHpEffectiveCurrent());
    }

    public void takeDamage(int damage) {
        hpEffectiveCurrent -= damage;
        System.out.println("Remaining hpEffectiveCurrent after takeDamage(int): " + hpEffectiveCurrent);

        if (hpEffectiveCurrent <= 0) {
            System.out.println("SQUAWK!!!");
            System.out.println("(Critter.takeDamage(int) sets hpEffectiveCurrent to 0 if it's negative)");
            ////////////////////////////////////////////
            status = StatusConditionNonVolatile.FAINTED;
            hpEffectiveCurrent = 0;
            ////////////////////////////////////////////
        }
    }

    public int calculateDamage(int power, int opponentDefenseEffective) {
        //TODO: attackEffective is used if the move's Type is PHYSICAL
        // if it's SPECIAL, use specialEffective.
        int playerAttackEffective = statsModule.getStatsEffectiveMap().get(StatsModule.Type.ATTACK);
        int damage = (int)((((((2f * level) / 5) + 2) * power * ((float)playerAttackEffective/opponentDefenseEffective)) / 50) + 2);

        /*
        System.out.println("damage: " + damage);
        float damageAsFloat = ((((((2f * level) / 5) + 2) * power * (((float)playerAttackEffective)/opponentDefenseEffective)) / 50) + 2);
        System.out.println("damageAsFloat: " + damageAsFloat);
        System.out.println("(((double)playerAttackEffective)/opponentDefenseEffective): " + (((double)playerAttackEffective)/opponentDefenseEffective));
        */

        //TODO: everything before + (* Modifier)
        //Modifier == Targets * Weather * Badge * Critical * random * STAB * Type * Burn * other.
        return damage;
    }

    // GETTERS AND SETTERS

    public void setHandler(Handler handler) { this.handler = handler; }

    public BufferedImage getSpeciesIcon() {
        return species.speciesIcon;
    }

    public String getNameColloquial() {
        return nameColloquial;
    }

    public int getIdNumberOriginalTrainer() { return idNumberOriginalTrainer; }

    public void setIdNumberOriginalTrainer(int idNumberOriginalTrainer) { this.idNumberOriginalTrainer = idNumberOriginalTrainer; }

    public int getLevel() {
        return level;
    }

    public int getHpEffectiveCurrent() {
        return hpEffectiveCurrent;
    }

    public void setHpEffectiveCurrent(int hpEffectiveCurrent) { this.hpEffectiveCurrent = hpEffectiveCurrent; }

    public int getExpCurrent() { return expCurrent; }

    public MovesModule getMovesModule() { return movesModule; }

    public StatsModule getStatsModule() { return statsModule; }

    public Species getSpecies() {
        return species;
    }

    public void setStatus(StatusConditionNonVolatile status) { this.status = status; }

    public StatusConditionNonVolatile getStatus() { return status; }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public enum Species {
        THUNDER_MOUSE(25, Type.ELECTRIC, Type.ELECTRIC, 190, 82, ExpGroup.MEDIUM_FAST, 35, 55, 30, 90, 50),
        TOTIPOTENT_PUPPY(133, Type.NORMAL, Type.NORMAL, 45, 92, ExpGroup.MEDIUM_FAST, 55, 55, 50, 55, 65),
        COLONIAL_MOUSE(19, Type.NORMAL, Type.NORMAL, 255, 57, ExpGroup.MEDIUM_FAST, 30, 56, 35, 72, 25),
        MOTLEY_PIGEON(16, Type.NORMAL, Type.FLYING, 255, 55, ExpGroup.MEDIUM_SLOW, 40, 45, 40, 56, 35),
        COASTAL_GULL(21, Type.NORMAL, Type.FLYING, 255, 58, ExpGroup.MEDIUM_FAST, 40, 60, 30, 70, 31),
        STONE_MONKEY(56, Type.FIGHTING, Type.FIGHTING, 190, 74, ExpGroup.MEDIUM_FAST, 40, 80, 35, 70, 35),
        FEMALE_LAPINE(29, Type.POISON, Type.POISON, 235, 59, ExpGroup.MEDIUM_SLOW, 55, 47, 52, 41, 40),
        MALE_LAPINE(32, Type.POISON, Type.POISON, 235, 60, ExpGroup.MEDIUM_SLOW, 46, 57, 40, 50, 40),
        HOOKAH_CATERPILLAR(10, Type.BUG, Type.BUG, 255, 53, ExpGroup.MEDIUM_FAST, 45, 30, 35, 45, 20),
        GRUB_LARVAE(13, Type.BUG, Type.POISON, 255, 52, ExpGroup.MEDIUM_FAST, 40, 35, 30, 50, 20),
        GROUND_HOG(50, Type.GROUND, Type.GROUND, 255, 81, ExpGroup.MEDIUM_FAST, 10, 55, 25, 95, 45),
        SAND_MOUSE(27, Type.GROUND, Type.GROUND, 255, 93, ExpGroup.MEDIUM_FAST, 50, 75, 85, 40, 30),
        ROCK_GOLEM(74, Type.ROCK, Type.GROUND, 255, 86, ExpGroup.MEDIUM_SLOW, 40, 80, 100, 20, 30),
        LEGLESS_ROCK_LIZARD(95, Type.ROCK, Type.GROUND, 45, 108, ExpGroup.MEDIUM_FAST, 35, 45, 160, 70, 30),
        BEARHUG_PYTHON(23, Type.POISON, Type.POISON, 255, 62, ExpGroup.MEDIUM_FAST, 35, 60, 44, 55, 40),
        SMOKEY_KITTY(52, Type.NORMAL, Type.NORMAL, 255, 69, ExpGroup.MEDIUM_FAST, 40, 45, 35, 90, 40),
        GASEOUS_GOLEM(109, Type.POISON, Type.POISON, 190, 114, ExpGroup.MEDIUM_FAST, 40, 65, 95, 35, 60),
        KARAOKE_CREAMPUFF(39, Type.NORMAL, Type.NORMAL, 170, 76, ExpGroup.FAST, 115, 45, 20, 20, 25),
        SPLASHILIC_TILAPIA(129, Type.WATER, Type.WATER, 255, 20, ExpGroup.SLOW, 20, 10, 55, 80, 20), //from AQUAPONIC SALESMAN in hospital near Place.MT_MOON.
        DINO_SPROUTLING(1, Type.GRASS, Type.POISON, 45, 64, ExpGroup.MEDIUM_SLOW, 45, 49, 49, 45, 65);

        private final int id;
        private Type type1, type2;
        private final int expYieldBase, catchRate;
        private final ExpGroup expGroup;
        //stats
        private final float hpBase, attackBase, defenseBase, speedBase, specialBase;
        //icon (method initializes speciesIcon with switch construct based on int id)
        private BufferedImage speciesIcon;
        /*
        A Pokémon's stats are derived values, based on the Pokémon's
        level, EVs (effort values), IVs (individual values), base stats,
        and (from Generation III onward) nature.

        If a Pokémon levels up or evolves,
        its level or base stats change,
        and all of its stats are recalculated;
        however, prior to Generation V, a Pokémon's
        stats are not always recalculated when its EVs are changed.
        From Generation I to IV, even if a Pokémon gains EVs in battle,
        its stats remain unchanged unless it leveled up or evolved;
        in Generation I and II, gaining EVs by consuming vitamins
        does not cause a Pokémon's stats to be recalculated either.

        If a Pokémon is placed in the PC,
        its stats will be recalculated,
        without the need to level up or evolve.
        Doing this intentionally is known as the box trick.

        This occurs because the derived stats
        are not stored for Pokémon in the PC,
        only the values from which they are derived.

        1	List of stats
            1.1	Permanent stats                 !!!!!!!!!!!
                1.1.1	Hit Points
                1.1.2	Attack
                1.1.3	Defense
                1.1.4	Special
                    1.1.4.1	Special Attack
                    1.1.4.2	Special Defense
                1.1.5	Speed
            1.2	In-battle stats                 !!!!!!!!!!!
                1.2.1	Evasion (see Stage [-6 to +6])
                1.2.2	Accuracy (see Stage [-6 to +6])

        2	Determination of stats
            2.1	Base stat values
            2.2	Level
            2.3	Nature
            2.4	Individual values
            2.5	Effort values
            2.6	Awakening values
            2.7	Formula
            2.8	In battle
                2.8.1	Accuracy and evasion
            2.9	Combat Power

        3	Stat modifiers
            3.1	In-battle modification
            3.2	Stage modification quotes
            3.3	Stage multipliers
         */


        /* CATCH RATE
        The formula also takes into account the following factors:

        The health of the Pokémon (relative to its full health)
        The type of Poké Ball
        Any status condition of the wild Pokémon

        Poké Balls that guarantee capture (the Master Ball) bypass the catching formula entirely.
         */


        Species(int id, Type type1, Type type2, int catchRate, int expYieldBase, ExpGroup expGroup,
                float hpBase, float attackBase, float defenseBase, float speedBase, float specialBase) {
            this.id = id;
            this.type1 = type1;
            this.type2 = type2;
            this.catchRate = catchRate;
            this.expYieldBase = expYieldBase;
            this.expGroup = expGroup;

            //stats
            this.hpBase = hpBase;
            this.attackBase = attackBase;
            this.defenseBase = defenseBase;
            this.speedBase = speedBase;
            this.specialBase = specialBase;

            //BufferedImage speciesIcon
            initSpeciesIcon();
        }

        //intentionally public in case BufferedImage has to be re-initialized after loading.
        public void initSpeciesIcon() {
            switch(id) {
                case 25: //THUNDER_MOUSE
                    speciesIcon = Assets.crittersBufferedImageNestedArray[2][0];
                    break;
                case 1: //DINO_SPROUTLING
                    speciesIcon = Assets.crittersBufferedImageNestedArray[0][0];
                    break;
                case 133: //TOTIPOTENT_PUPPY
                    speciesIcon = Assets.crittersBufferedImageNestedArray[11][0];
                    break;
                case 56: //STONE_MONKEY
                    speciesIcon = Assets.crittersBufferedImageNestedArray[4][7];
                    break;
                case 129: //SPLASHILIC_TILAPIA
                    speciesIcon = Assets.crittersBufferedImageNestedArray[10][8];
                    break;
                case 74: //ROCK_GOLEM
                    speciesIcon = Assets.crittersBufferedImageNestedArray[6][1];
                    break;
                case 21: //COASTAL_GULL
                    speciesIcon = Assets.crittersBufferedImageNestedArray[1][8];
                    break;
                case 16: //MOTLEY_PIGEON
                    speciesIcon = Assets.crittersBufferedImageNestedArray[1][3];
                    break;
                case 19: //COLONIAL_MOUSE
                    speciesIcon = Assets.crittersBufferedImageNestedArray[1][6];
                    break;
                case 29: //FEMALE_LAPINE
                    speciesIcon = Assets.crittersBufferedImageNestedArray[2][4];
                    break;
                case 32: //MALE_LAPINE
                    speciesIcon = Assets.crittersBufferedImageNestedArray[2][7];
                    break;
                case 10: //HOOKAH_CATERPILLAR
                    speciesIcon = Assets.crittersBufferedImageNestedArray[0][9];
                    break;
                case 13: //GRUB_LARVAE
                    speciesIcon = Assets.crittersBufferedImageNestedArray[1][0];
                    break;
                case 27: //SAND_MOUSE
                    speciesIcon = Assets.crittersBufferedImageNestedArray[2][2];
                    break;
                case 50: //GROUND_HOG
                    speciesIcon = Assets.crittersBufferedImageNestedArray[4][1];
                    break;
                case 95: //LEGLESS_ROCK_LIZARD
                    speciesIcon = Assets.crittersBufferedImageNestedArray[7][10];
                    break;
                case 23: //BEARHUG_PYTHON
                    speciesIcon = Assets.crittersBufferedImageNestedArray[1][10];
                    break;
                case 52: //SMOKEY_KITTY
                    speciesIcon = Assets.crittersBufferedImageNestedArray[4][3];
                    break;
                case 109: //GASEOUS_GOLEM
                    speciesIcon = Assets.crittersBufferedImageNestedArray[9][0];
                    break;
                case 39: //KARAOKE_CREAMPUFF
                    speciesIcon = Assets.crittersBufferedImageNestedArray[3][2];
                    break;
                default:
                    System.out.println("Critter.initSpeciesIcon() switch(id) construct's default block.");
                    System.out.println("THIS Critter is NOT listed in Critter.initSpeciesIcon()!!!");
                    speciesIcon = Assets.critterBallSprite;
                    break;
            }
        }

        public int getId() { return id; }

        public Type getType1() { return type1; }

        public Type getType2() { return type2; }

        public int getExpYieldBase() { return expYieldBase; }

        public int getHpBase() { return (int)hpBase; }

        public int getAttackBase() { return (int)attackBase; }

        public int getDefenseBase() { return (int)defenseBase; }

        public int getSpeedBase() { return (int)speedBase; }

        public int getSpecialBase() { return (int)specialBase; }

    }

    public enum ExpGroup {
        FAST, MEDIUM_FAST, MEDIUM_SLOW, SLOW;
    }

    public enum Type {
        BUG,
        DRAGON,
        ELECTRIC,
        FIGHTING,
        FIRE,
        FLYING,
        GHOST,
        GRASS,
        GROUND,
        ICE,
        NORMAL,
        POISON,
        PSYCHIC,
        ROCK,
        WATER;
    }

    public enum StatusConditionNonVolatile {
        OK,
        BURN,
        FREEZE,
        PARALYSIS,
        POISON,
        BADLY_POISONED,
        SLEEP,
        FAINTED;
    }

    public enum StatusConditionVolatile {
        BOUND, CANT_ESCAPE, CONFUSION, CURSE, EMBARGO, ENCORE, FLINCH, HEAL_BLOCK, IDENTIFIED,
        INFATUATION, LEECH_SEED, NIGHTMARE, PERISH_SONG, TAUNT, TELEKINESIS, TORMENT;
    }

} // **** end Critter class ****