package model.entities.critters;

import main.Handler;
import main.gfx.Assets;
import model.entities.critters.moves.MoveModule;

import java.awt.image.BufferedImage;

public class Critter {

    private Handler handler;
    private Species species;
    private Type type1, type2;
    private transient BufferedImage speciesIcon;

    private MoveModule moveModule;

    private int hpCurrent;
    private int expCurrent, expRewardedIfDefeated;
    private int level;
    private StatusConditionNonVolatile status;

    public Critter(Handler handler, Species species, int level) {
        this.handler = handler;
        this.species = species;
        parseSpeciesToInitType1AndType2();
        parseSpeciesToInitSpeciesIcon();

        moveModule = new MoveModule(handler);

        hpCurrent = (int)species.hpBase;
        expCurrent = 0;
        expRewardedIfDefeated = 10;
        this.level = level;
        status = StatusConditionNonVolatile.OK;
    } // **** end Critter(Handler, Species, int) constructor ****

    public void parseSpeciesToInitSpeciesIcon() {
        switch(species) {
            case THUNDER_MOUSE:
                speciesIcon = Assets.crittersBufferedImageNestedArray[2][0];
                break;
            case DINO_SPROUTLING:
                speciesIcon = Assets.crittersBufferedImageNestedArray[0][0];
                break;
            /* ... */
            default:
                System.out.println("Critter.parseSpeciesToInitSpeciesIcon() switch(species) construct's default block.");
                speciesIcon = Assets.critterBallSprite;
                break;
        }
    }

    private void parseSpeciesToInitType1AndType2() {
        switch(species) {
            case THUNDER_MOUSE:
                type1 = Type.ELECTRIC;
                type2 = Type.GROUND;
                break;
            case DINO_SPROUTLING:
                type1 = Type.GRASS;
                type1 = Type.GROUND;
                break;
            /* ... */
            default:
                System.out.println("Critter.parseSpeciesToInitType1AndType2() switch(species) construct's default block.");
                type1 = Type.NORMAL;
                type2 = Type.NORMAL;
                break;
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public enum Species {
        THUNDER_MOUSE(25, 190, ExpGroup.MEDIUM_FAST, 35, 55, 30, 90, 50),
        TOTIPOTENT_PUPPY(133, 45, ExpGroup.MEDIUM_FAST, 55, 55, 50, 55, 65),
        COLONIAL_MOUSE(19, 255, ExpGroup.MEDIUM_FAST, 30, 56, 35, 72, 25),
        MOTLEY_PIGEON(16, 255, ExpGroup.MEDIUM_SLOW, 40, 45, 40, 56, 35),
        COASTAL_GULL(21, 255, ExpGroup.MEDIUM_FAST, 40, 60, 30, 70, 31),
        STONE_MONKEY(56, 190, ExpGroup.MEDIUM_FAST, 40, 80, 35, 70, 35),
        FEMALE_LAPINE(29, 235, ExpGroup.MEDIUM_SLOW, 55, 47, 52, 41, 40),
        MALE_LAPINE(32, 235, ExpGroup.MEDIUM_SLOW, 46, 57, 40, 50, 40),
        HOOKAH_CATERPILLAR(10, 255, ExpGroup.MEDIUM_FAST, 45, 30, 35, 45, 20),
        GRUB_LARVAE(13, 255, ExpGroup.MEDIUM_FAST, 40, 35, 30, 50, 20),
        GROUND_HOG(50, 255, ExpGroup.MEDIUM_FAST, 10, 55, 25, 95, 45),
        SAND_MOUSE(27, 255, ExpGroup.MEDIUM_FAST, 50, 75, 85, 40, 30),
        ROCK_GOLEM(74, 255, ExpGroup.MEDIUM_SLOW, 40, 80, 100, 20, 30),
        LEGLESS_ROCK_LIZARD(95, 45, ExpGroup.MEDIUM_FAST, 35, 45, 160, 70, 30),
        BEARHUG_PYTHON(23, 255, ExpGroup.MEDIUM_FAST, 35, 60, 44, 55, 40),
        SMOKEY_KITTY(52, 255, ExpGroup.MEDIUM_FAST, 40, 45, 35, 90, 40),
        GASEOUS_GOLEM(109, 190, ExpGroup.MEDIUM_FAST, 40, 65, 95, 35, 60),
        KARAOKE_CREAMPUFF(39, 170, ExpGroup.FAST, 115, 45, 20, 20, 25),
        SPLASHILIC_TILAPIA(129, 255, ExpGroup.SLOW, 20, 10, 55, 80, 20), //from AQUAPONIC SALESMAN in hospital near Place.MT_MOON.
        DINO_SPROUTLING(1, 45, ExpGroup.MEDIUM_SLOW, 45, 49, 49, 45, 65);

        private final int id, catchRate;
        private ExpGroup expGroup;
        //stats
        private final float hpBase, attackBase, defenseBase, speedBase, specialBase;
        /*
        The box trick is
        a method in the core series Pokémon games
        that causes a Pokémon's
        stats to be
        recalculated based on its
        current effort values.

        This trick is applicable in Generations I through IV; from Generation V onward,
        the trick is obsolete, due to changes to a Pokémon's EVs automatically being
        applied to its stats.

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
        are not stored for Pokémon in the PC, o
        nly the values from which they are derived.

        From Generation III onward,
        a Pokémon's stats are also
        recalculated if its EVs are changed by a vitamin or EV-reducing Berry.

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

        Species(int id, int catchRate, ExpGroup expGroup,
                float hpBase, float attackBase, float defenseBase, float speedBase, float specialBase) {
            this.id = id;
            this.catchRate = catchRate;
            this.expGroup = expGroup;

            //stats
            this.hpBase = hpBase;
            this.attackBase = attackBase;
            this.defenseBase = defenseBase;
            this.speedBase = speedBase;
            this.specialBase = specialBase;
        }
    }

    /* !!!as long as it's not in Species (okay if in Critter)!!!
    public enum Statistic {
        ATTACK(),
        DEFENSE(),
        SPEED(),
        SPECIAL();

        private int base, individualValue, effortValue;

        Statistic(int base, int individualValue, int effortValue) {
            this.base = base;
            this.individualValue = individualValue;
            this.effortValue = effortValue;
        }
    }
    */

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
        SLEEP;
    }

    public enum StatusConditionVolatile {
        BOUND, CANT_ESCAPE, CONFUSION, CURSE, EMBARGO, ENCORE, FLINCH, HEAL_BLOCK, IDENTIFIED,
        INFATUATION, LEECH_SEED, NIGHTMARE, PERISH_SONG, TAUNT, TELEKINESIS, TORMENT;
    }

} // **** end Critter class ****