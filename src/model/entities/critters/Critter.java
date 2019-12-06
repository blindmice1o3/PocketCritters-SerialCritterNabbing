package model.entities.critters;

public class Critter {

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
        BURN,
        FREEZE,
        PARALYSIS,
        POISON,
        SLEEP;
    }

    public static final int HEALTH_DEFAULT = 10;
    private int healthCurrent;
    private int healthMax;

    private Ailment statusCurrent;

    private int level;

    //stats related to battle
    private int attack, defense, speed, special;

    public Critter() {
        healthMax = HEALTH_DEFAULT;
        healthCurrent = healthMax;

        statusCurrent = null;

        level = 2;

        attack = 1;
        speed = 1;
    } // **** end Critter() constructor ****



} // **** end Critter class ****