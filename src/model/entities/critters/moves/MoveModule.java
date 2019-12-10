package model.entities.critters.moves;

import main.Handler;
import model.entities.critters.Critter;

public class MoveModule {

    public enum Category {
        PHYSICAL, SPECIAL, STATUS;
    }

    public enum Physical {
        NORMAL, FIGHTING, FLYING, POISON, GROUND, ROCK, BUG, GHOST;
    }

    public enum Special {
        FIRE, WATER, GRASS, ELECTRIC, PSYCHIC, ICE, DRAGON;
    }

    public enum Status {

    }

    /*
    "Certain actions always occur before any moves can be performed.
    Switching out, rotating, using items, escaping, and the charging
    messages of Focus Punch, Beak Blast and Shell Trap are displayed
    or performed next.

    GENERATION I:
    QUICK_ATTACK(+1),
    ALL_OTHER_MOVES(0),
    COUNTER(-1);

    "
     */
    public enum Move {
        //TODO: oops right now int id is actually the move's TM-xx number... actual id is in "moves list.txt".
        BIDE(117, Critter.Type.NORMAL, 0),
        BLIZZARD(59, Critter.Type.ICE, 0),
        BODY_SLAM(34, Critter.Type.NORMAL, 0),
        BUBBLE_BEAM(61, Critter.Type.WATER, 0),
        COUNTER(68, Critter.Type.FIGHTING, -1),
        DIG(91, Critter.Type.GROUND, 0),
        DOUBLE_TEAM(104, Critter.Type.NORMAL, 0),
        DOUBLE_EDGE(38, Critter.Type.NORMAL, 0),
        DRAGON_RAGE(82, Critter.Type.DRAGON, 0),
        DREAM_EATER(138, Critter.Type.PSYCHIC, 0),
        EARTHQUAKE(89, Critter.Type.GROUND, 0),
        EGG_BOMB(121, Critter.Type.NORMAL, 0),
        EXPLOSION(153, Critter.Type.NORMAL, 0),
        FIRE_BLAST(126, Critter.Type.FIRE, 0),
        FISSURE(90, Critter.Type.GROUND, 0),
        HORN_DRILL(32, Critter.Type.NORMAL, 0),
        HYPER_BEAM(63, Critter.Type.NORMAL, 0),
        ICE_BEAM(58, Critter.Type.ICE, 0),
        MEGA_DRAIN(72, Critter.Type.GRASS, 0),
        MEGA_KICK(25, Critter.Type.NORMAL, 0),
        MEGA_PUNCH(5, Critter.Type.NORMAL, 0),
        METRONOME(118, Critter.Type.NORMAL, 0),
        MIMIC(102, Critter.Type.NORMAL, 0),
        PAY_DAY(6, Critter.Type.NORMAL, 0),
        PSYCHIC(94, Critter.Type.PSYCHIC, 0),
        PSYWAVE(149, Critter.Type.PSYCHIC, 0),
        RAGE(99, Critter.Type.NORMAL, 0),
        RAZOR_WIND(13, Critter.Type.NORMAL, 0),
        REFLECT(115, Critter.Type.PSYCHIC, 0),
        REST(156, Critter.Type.PSYCHIC, 0),
        ROCK_SLIDE(157, Critter.Type.ROCK, 0),
        SEISMIC_TOSS(69, Critter.Type.FIGHTING, 0),
        SELF_DESTRUCT(120, Critter.Type.NORMAL, 0),
        SKULL_BASH(130, Critter.Type.NORMAL, 0),
        SKY_ATTACK(143, Critter.Type.FLYING, 0),
        SOFT_BOILED(135, Critter.Type.NORMAL, 0),
        SOLAR_BEAM(76, Critter.Type.GRASS, 0),
        SUBMISSION(66, Critter.Type.FIGHTING, 0),
        SUBSTITUTE(164, Critter.Type.NORMAL, 0),
        SWIFT(129, Critter.Type.NORMAL, 0),
        SWORDS_DANCE(14, Critter.Type.NORMAL, 0),
        TAKE_DOWN(36, Critter.Type.NORMAL, 0),
        TELEPORT(100, Critter.Type.PSYCHIC, 0),
        THUNDER(87, Critter.Type.ELECTRIC, 0),
        THUNDER_WAVE(86, Critter.Type.ELECTRIC, 0),
        THUNDERBOLT(85, Critter.Type.ELECTRIC, 0),
        TOXIC(92, Critter.Type.POISON, 0),
        TRI_ATTACK(161, Critter.Type.NORMAL, 0),
        WATER_GUN(55, Critter.Type.WATER, 0),
        WHIRLWIND(18, Critter.Type.NORMAL, 0),
        QUICK_ATTACK(98, Critter.Type.NORMAL, +1),
        TACKLE(33, Critter.Type.NORMAL, 0);

        private final Critter.Type type;
        private final int id, priority;

        Move(int id, Critter.Type type, int priority) {
            this.id = id;
            this.type = type;
            this.priority = priority;
        }
    }

    private Handler handler;

    private int idMove1, idMove2, idMove3, idMove4;

    public MoveModule(Handler handler) {
        this.handler = handler;

        idMove1 = 33;
        idMove2 = 98;
        idMove3 = 0;
        idMove4 = 0;
    } // *** end MoveModule(Handler) constructor

} // **** end MoveModule class ****