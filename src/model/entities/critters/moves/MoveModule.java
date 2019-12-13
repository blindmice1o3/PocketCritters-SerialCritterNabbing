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
        BIDE(117, Critter.Type.NORMAL, Category.PHYSICAL, 10, 0, 100, 0),
        BLIZZARD(59, Critter.Type.ICE, Category.SPECIAL, 5, 110, 70, 0),
        BODY_SLAM(34, Critter.Type.NORMAL, Category.PHYSICAL, 15, 85, 100, 0),
        BUBBLE_BEAM(61, Critter.Type.WATER, Category.SPECIAL, 20, 65, 100, 0),
        COUNTER(68, Critter.Type.FIGHTING, Category.PHYSICAL, 20, 0, 100, -1),
        DIG(91, Critter.Type.GROUND, Category.PHYSICAL, 10, 80, 100, 0),
        DOUBLE_TEAM(104, Critter.Type.NORMAL, Category.STATUS, 15, 0, 0, 0),
        DOUBLE_EDGE(38, Critter.Type.NORMAL, Category.PHYSICAL, 15, 120, 100, 0),
        DRAGON_RAGE(82, Critter.Type.DRAGON, Category.SPECIAL, 10, 0, 100, 0),  //always deal 40 hp damage.
        DREAM_EATER(138, Critter.Type.PSYCHIC, Category.SPECIAL, 15, 100, 100, 0),
        EARTHQUAKE(89, Critter.Type.GROUND, Category.PHYSICAL, 10, 100, 100, 0),
        EGG_BOMB(121, Critter.Type.NORMAL, Category.PHYSICAL, 10, 100, 75, 0),
        EXPLOSION(153, Critter.Type.NORMAL, Category.PHYSICAL, 5, 170, 100, 0),
        FIRE_BLAST(126, Critter.Type.FIRE, Category.SPECIAL, 5, 120, 85, 0),
        FISSURE(90, Critter.Type.GROUND, Category.PHYSICAL, 5, 0, 30, 0), //CANNOT hit target of higher speed.
        HORN_DRILL(32, Critter.Type.NORMAL, Category.PHYSICAL, 5, 0, 30, 0), //CANNOT hit target of higher speed.
        HYPER_BEAM(63, Critter.Type.NORMAL, Category.SPECIAL, 5, 150, 90, 0),
        ICE_BEAM(58, Critter.Type.ICE, Category.SPECIAL, 10, 95, 100, 0),
        MEGA_DRAIN(72, Critter.Type.GRASS, Category.SPECIAL, 10, 40, 100, 0),
        MEGA_KICK(25, Critter.Type.NORMAL, Category.PHYSICAL, 5, 120, 75, 0),
        MEGA_PUNCH(5, Critter.Type.NORMAL, Category.PHYSICAL, 20, 80, 85, 0),
        METRONOME(118, Critter.Type.NORMAL, Category.STATUS, 10, 0, 0, 0),
        MIMIC(102, Critter.Type.NORMAL, Category.STATUS, 10, 0, 100, 0),
        PAY_DAY(6, Critter.Type.NORMAL, Category.PHYSICAL, 20, 40, 100, 0),
        PSYCHIC(94, Critter.Type.PSYCHIC, Category.SPECIAL, 10, 90, 100, 0),
        PSYWAVE(149, Critter.Type.PSYCHIC, Category.SPECIAL, 15, 0, 80, 0),
        RAGE(99, Critter.Type.NORMAL, Category.PHYSICAL, 20, 20, 100, 0),
        RAZOR_WIND(13, Critter.Type.NORMAL, Category.SPECIAL, 10, 80, 75, 0),
        REFLECT(115, Critter.Type.PSYCHIC, Category.STATUS, 20, 0, 0, 0),
        REST(156, Critter.Type.PSYCHIC, Category.STATUS, 10, 0, 0, 0),
        ROCK_SLIDE(157, Critter.Type.ROCK, Category.PHYSICAL, 10, 75, 90, 0),
        SEISMIC_TOSS(69, Critter.Type.FIGHTING, Category.PHYSICAL, 20, 0, 100, 0),
        SELF_DESTRUCT(120, Critter.Type.NORMAL, Category.PHYSICAL, 5, 130, 100, 0),
        SKULL_BASH(130, Critter.Type.NORMAL, Category.PHYSICAL, 15, 100, 100, 0),
        SKY_ATTACK(143, Critter.Type.FLYING, Category.PHYSICAL, 5, 140, 90, 0),
        SOFT_BOILED(135, Critter.Type.NORMAL, Category.STATUS, 10, 0, 0, 0),
        SOLAR_BEAM(76, Critter.Type.GRASS, Category.SPECIAL, 10, 120, 100, 0),
        SUBMISSION(66, Critter.Type.FIGHTING, Category.PHYSICAL, 25, 80, 80, 0),
        SUBSTITUTE(164, Critter.Type.NORMAL, Category.STATUS, 10, 0, 0, 0),
        SWIFT(129, Critter.Type.NORMAL, Category.SPECIAL, 20, 60, 0, 0),
        SWORDS_DANCE(14, Critter.Type.NORMAL, Category.STATUS, 30, 0, 0, 0),
        TAKE_DOWN(36, Critter.Type.NORMAL, Category.PHYSICAL, 20, 90, 85, 0),
        TELEPORT(100, Critter.Type.PSYCHIC, Category.STATUS, 20, 0, 0, 0),
        THUNDER(87, Critter.Type.ELECTRIC, Category.SPECIAL, 10, 120, 70, 0),
        THUNDER_WAVE(86, Critter.Type.ELECTRIC, Category.STATUS, 20, 0, 100, 0),
        THUNDERBOLT(85, Critter.Type.ELECTRIC, Category.SPECIAL, 15, 95, 100, 0),
        TOXIC(92, Critter.Type.POISON, Category.STATUS, 10, 0, 85, 0),
        TRI_ATTACK(161, Critter.Type.NORMAL, Category.SPECIAL, 10, 80, 100, 0),
        WATER_GUN(55, Critter.Type.WATER, Category.SPECIAL, 25, 40, 100, 0),
        WHIRLWIND(18, Critter.Type.NORMAL, Category.STATUS, 20, 0, 85, 0),
        QUICK_ATTACK(98, Critter.Type.NORMAL, Category.PHYSICAL, 30, 40, 100, +1),
        TACKLE(33, Critter.Type.NORMAL, Category.PHYSICAL, 35, 35, 95, 0);

        private final int id, priority;
        private final Critter.Type type;
        private final Category category;
        private final int ppBase, power, accuracy;

        Move(int id, Critter.Type type, Category category, int ppBase, int power, int accuracy, int priority) {
            this.id = id;
            this.type = type;
            this.category = category;
            this.ppBase = ppBase;
            this.power = power;
            this.accuracy = accuracy;
            this.priority = priority;
        }

        public int getPpBase() { return ppBase; }
    }

    private Handler handler;

    //stores the specific Move-value in position 1-4.
    private int idMove1, idMove2, idMove3, idMove4;
    //TODO: track pp for each of the 4 moves.
    private int ppMove1, ppMove2, ppMove3, ppMove4;

    public MoveModule(Handler handler) {
        this.handler = handler;

        //for now, everyone starts with TACKLE and QUICK_ATTACK (and positions 3 and 4 are suppose to be "null"/unassigned).
        idMove1 = 33;
        idMove2 = 98;
        idMove3 = 0;
        idMove4 = 0;

        if (idMove1 != 0) {
            ppMove1 = lookUpMove(idMove1).getPpBase();
        }
        if (idMove2 != 0) {
            ppMove2 = lookUpMove(idMove2).getPpBase();
        }
        if (idMove3 != 0) {
            ppMove3 = lookUpMove(idMove3).getPpBase();
        }
        if (idMove4 != 0) {
            ppMove4 = lookUpMove(idMove4).getPpBase();
        }
    } // *** end MoveModule(Handler) constructor

    public int getIdMove1() { return idMove1; }

    public int getIdMove2() { return idMove2; }

    public int getIdMove3() { return idMove3; }

    public int getIdMove4() { return idMove4; }

    public int getPpMove1() { return ppMove1; }

    public int getPpMove2() { return ppMove2; }

    public int getPpMove3() { return ppMove3; }

    public int getPpMove4() { return ppMove4; }

    public Move lookUpMove(int idMove) {
        for (Move move : Move.values()) {
            if (idMove == move.id) {
                return move;
            }
        }
        //BY DEFAULT (if not one of the enum Move's value, return null.
        return null;
    }

} // **** end MoveModule class ****