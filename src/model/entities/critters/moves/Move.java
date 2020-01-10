package model.entities.critters.moves;

public class Move {

    private int id;
    private String name;
    private Type type;
    private Category category;
    private int ppBase;
    private int power;
    private int accuracy;

    public Move(int id, String name, Type type, Category category, int ppBase, int power, int accuracy) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.ppBase = ppBase;
        this.power = power;
        this.accuracy = accuracy;
    } // **** end Move(int, String, Type, Category, int, int, int) constructor ****

    // GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public int getPpBase() {
        return ppBase;
    }

    public int getPower() {
        return power;
    }

    public int getAccuracy() {
        return accuracy;
    }

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

} // **** end Move class ****