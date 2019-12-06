package model.entities.critters;

import main.Handler;

public class Critter {

    public static final int HEALTH_DEFAULT = 10;

    private Handler handler;
    private Species species;
    private int level;

    private Type type1, type2;
    private Ailment status;

    private int expCurrent, expRewardedIfDefeated;

    private int healthCurrent, healthMax;

    //stats related to battle
    private int attack, defense, speed, special;

    private String move1, move2, move3, move4;

    public Critter(Handler handler, Species species, int level) {
        this.handler = handler;
        this.species = species;
        this.level = level;

        parseSpeciesToInitType1AndType2();
        status = Ailment.OK;

        expCurrent = 0;
        expRewardedIfDefeated = 10;

        healthMax = HEALTH_DEFAULT;
        healthCurrent = healthMax;

        attack = 1;
        defense = 1;
        speed = 1;
        special = 1;

        move1 = "Tackle";
        move2 = "Growl";
        move3 = "null";
        move4 = "null";
    } // **** end Critter(Handler, Species, int) constructor ****

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
        THUNDER_MOUSE,
        TOTIPOTENT_PUPPY,
        COLONIAL_MOUSE,
        MOTLEY_PIGEON,
        COASTAL_GULL,
        STONE_MONKEY,
        FEMALE_LAPINE,
        MALE_LAPINE,
        HOOKAH_CATERPILLAR,
        GRUB_LARVAE,
        GROUND_HOG,
        SAND_MOUSE,
        ROCK_GOLEM,
        LEGLESS_ROCK_LIZARD,
        BEARHUG_PYTHON,
        SMOKEY_KITTY,
        GASEOUS_GOLEM,
        KARAOKE_CREAMPUFF,
        SPLASHILIC_TILAPIA, //from AQUAPONIC SALESMAN in hospital near Place.MT_MOON.
        DINO_SPROUTLING;
    }

    public enum Type {
        NORMAL,
        ELECTRIC,
        WATER,
        FIRE,
        GRASS,
        FLYING,
        FIGHTING,
        GROUND,
        ROCK,
        POISON;
    }

    public enum Ailment {
        OK,
        BURN,
        FREEZE,
        PARALYSIS,
        POISON,
        SLEEP;
    }

} // **** end Critter class ****