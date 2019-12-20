package model.entities.critters.moves;

import main.Handler;

public class MovesModule {

    public enum Category {
        PHYSICAL,
        SPECIAL,
        STATUS;
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
        PSYCHIC,
        POISON,
        ROCK,
        WATER;
    }

    /*
    "Certain actions always occur before any movesCurrent can be performed.
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
        BIDE(117, Type.NORMAL, Category.PHYSICAL, 10, 0, 100, 0),
        BLIZZARD(59, Type.ICE, Category.SPECIAL, 5, 110, 70, 0),
        BODY_SLAM(34, Type.NORMAL, Category.PHYSICAL, 15, 85, 100, 0),
        BUBBLE_BEAM(61, Type.WATER, Category.SPECIAL, 20, 65, 100, 0),
        COUNTER(68, Type.FIGHTING, Category.PHYSICAL, 20, 0, 100, -1),
        DIG(91, Type.GROUND, Category.PHYSICAL, 10, 80, 100, 0),
        DOUBLE_TEAM(104, Type.NORMAL, Category.STATUS, 15, 0, 0, 0),
        DOUBLE_EDGE(38, Type.NORMAL, Category.PHYSICAL, 15, 120, 100, 0),
        DRAGON_RAGE(82, Type.DRAGON, Category.SPECIAL, 10, 0, 100, 0),  //always deal 40 hp damage.
        DREAM_EATER(138, Type.PSYCHIC, Category.SPECIAL, 15, 100, 100, 0),
        EARTHQUAKE(89, Type.GROUND, Category.PHYSICAL, 10, 100, 100, 0),
        EGG_BOMB(121, Type.NORMAL, Category.PHYSICAL, 10, 100, 75, 0),
        EXPLOSION(153, Type.NORMAL, Category.PHYSICAL, 5, 170, 100, 0),
        FIRE_BLAST(126, Type.FIRE, Category.SPECIAL, 5, 120, 85, 0),
        FISSURE(90, Type.GROUND, Category.PHYSICAL, 5, 0, 30, 0), //CANNOT hit target of higher speed.
        HORN_DRILL(32, Type.NORMAL, Category.PHYSICAL, 5, 0, 30, 0), //CANNOT hit target of higher speed.
        HYPER_BEAM(63, Type.NORMAL, Category.SPECIAL, 5, 150, 90, 0),
        ICE_BEAM(58, Type.ICE, Category.SPECIAL, 10, 95, 100, 0),
        MEGA_DRAIN(72, Type.GRASS, Category.SPECIAL, 10, 40, 100, 0),
        MEGA_KICK(25, Type.NORMAL, Category.PHYSICAL, 5, 120, 75, 0),
        MEGA_PUNCH(5, Type.NORMAL, Category.PHYSICAL, 20, 80, 85, 0),
        METRONOME(118, Type.NORMAL, Category.STATUS, 10, 0, 0, 0),
        MIMIC(102, Type.NORMAL, Category.STATUS, 10, 0, 100, 0),
        PAY_DAY(6, Type.NORMAL, Category.PHYSICAL, 20, 40, 100, 0),
        PSYCHIC(94, Type.PSYCHIC, Category.SPECIAL, 10, 90, 100, 0),
        PSYWAVE(149, Type.PSYCHIC, Category.SPECIAL, 15, 0, 80, 0),
        RAGE(99, Type.NORMAL, Category.PHYSICAL, 20, 20, 100, 0),
        RAZOR_WIND(13, Type.NORMAL, Category.SPECIAL, 10, 80, 75, 0),
        REFLECT(115, Type.PSYCHIC, Category.STATUS, 20, 0, 0, 0),
        REST(156, Type.PSYCHIC, Category.STATUS, 10, 0, 0, 0),
        ROCK_SLIDE(157, Type.ROCK, Category.PHYSICAL, 10, 75, 90, 0),
        SEISMIC_TOSS(69, Type.FIGHTING, Category.PHYSICAL, 20, 0, 100, 0),
        SELF_DESTRUCT(120, Type.NORMAL, Category.PHYSICAL, 5, 130, 100, 0),
        SKULL_BASH(130, Type.NORMAL, Category.PHYSICAL, 15, 100, 100, 0),
        SKY_ATTACK(143, Type.FLYING, Category.PHYSICAL, 5, 140, 90, 0),
        SOFT_BOILED(135, Type.NORMAL, Category.STATUS, 10, 0, 0, 0),
        SOLAR_BEAM(76, Type.GRASS, Category.SPECIAL, 10, 120, 100, 0),
        SUBMISSION(66, Type.FIGHTING, Category.PHYSICAL, 25, 80, 80, 0),
        SUBSTITUTE(164, Type.NORMAL, Category.STATUS, 10, 0, 0, 0),
        SWIFT(129, Type.NORMAL, Category.SPECIAL, 20, 60, 0, 0),
        SWORDS_DANCE(14, Type.NORMAL, Category.STATUS, 30, 0, 0, 0),
        TAKE_DOWN(36, Type.NORMAL, Category.PHYSICAL, 20, 90, 85, 0),
        TELEPORT(100, Type.PSYCHIC, Category.STATUS, 20, 0, 0, 0),
        THUNDER(87, Type.ELECTRIC, Category.SPECIAL, 10, 120, 70, 0),
        THUNDER_WAVE(86, Type.ELECTRIC, Category.STATUS, 20, 0, 100, 0),
        THUNDERBOLT(85, Type.ELECTRIC, Category.SPECIAL, 15, 95, 100, 0),
        TOXIC(92, Type.POISON, Category.STATUS, 10, 0, 85, 0),
        TRI_ATTACK(161, Type.NORMAL, Category.SPECIAL, 10, 80, 100, 0),
        WATER_GUN(55, Type.WATER, Category.SPECIAL, 25, 40, 100, 0),
        WHIRLWIND(18, Type.NORMAL, Category.STATUS, 20, 0, 85, 0),
        //////////////////////////////////////////////////////////////////////////
        QUICK_ATTACK(98, Type.NORMAL, Category.PHYSICAL, 30, 40, 100, +1),
        TACKLE(33, Type.NORMAL, Category.PHYSICAL, 35, 35, 95, 0);
        //////////////////////////////////////////////////////////////////////////

        private final int id, priority;
        private final Type type;
        private final Category category;
        private final int ppBase, power, accuracy;

        Move(int id, Type type, Category category, int ppBase, int power, int accuracy, int priority) {
            this.id = id;
            this.type = type;
            this.category = category;
            this.ppBase = ppBase;
            this.power = power;
            this.accuracy = accuracy;
            this.priority = priority;
        }

        public Type getType() {
            return type;
        }

        public Category getCategory() { return category; }

        public int getPpBase() { return ppBase; }

        public int getPower() { return power; }

        public int getAccuracy() { return accuracy; }

        public int getPriority() { return priority; }
    }

    private Handler handler;

    //stores the idMove in index 0-3.
    private int[] movesCurrent;
    //stores the ppCurrent in index 0-3.
    private int[] ppMovesCurrent;

    public MovesModule(Handler handler) {
        this.handler = handler;


        //for now, everyone starts with TACKLE and QUICK_ATTACK (and positions 3 and 4 are suppose to be "null"/unassigned/default is 0).
        movesCurrent = new int[4];
        movesCurrent[0] = 33;
        movesCurrent[1] = 98;

        //look up value for pp
        ppMovesCurrent = new int[4];
        for (int i = 0; i < movesCurrent.length; i++) {
            if (movesCurrent[i] != 0) {
                ppMovesCurrent[i] = lookUpMove(movesCurrent[i]).getPpBase();
            }
        }
    } // *** end MovesModule(Handler) constructor

    public int getNumberMovesKnown() {
        int numberMovesKnown = 0;

        for (int idMove : movesCurrent) {
            if (idMove != 0) {
                numberMovesKnown++;
            }
        }

        return numberMovesKnown;
    }

    public void decrementPpMovesCurrent(int index) {
        ppMovesCurrent[index]--;

        if (ppMovesCurrent[index] < 0) {
            ppMovesCurrent[index] = 0;
        }
    }

    public int[] getMovesCurrent() { return movesCurrent; }

    public int[] getPpMovesCurrent() { return ppMovesCurrent; }

    public Move lookUpMove(int idMove) {
        for (Move move : Move.values()) {
            if (idMove == move.id) {
                return move;
            }
        }
        //BY DEFAULT (if not one of the enum Move's value, return null.
        return null;
    }

} // **** end MovesModule class ****