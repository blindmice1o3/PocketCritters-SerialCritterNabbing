package model.states.battle;

import main.Handler;
import model.entities.Player;
import model.entities.critters.Critter;
import model.entities.critters.stats.StatsModule;
import model.states.IState;
import model.states.StateMachine;
import model.states.battle.belt_list_battle.BattleBeltList;
import model.states.battle.belt_list_battle.BattleBeltListAction;
import model.states.battle.belt_list_battle.BattleBeltListActionSummary;
import model.states.battle.belt_list_battle.BattleBeltListActionSwap;

import java.awt.*;

public class BattleState implements IState {

    private Handler handler;
    private Player player;

    private StateMachine stateMachine;

    private Critter critterOfOpponent;
    private Critter critterOfPlayer;
    //TODO: each Critter have In-battle stats: (1) evasion, (2) accuracy.

    public BattleState(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        critterOfOpponent = null;

        //TODO: reward/experience/leveling.

        initStateMachine();
    } // **** end BattleState(Handler, Player) constructor ****

    private void initStateMachine() {
        stateMachine = new StateMachine(handler);

        stateMachine.addIStateToCollection("BattleStateIntro", new BattleStateIntro(handler, player));
        stateMachine.addIStateToCollection("BattleStateMenu", new BattleStateMenu(handler, player));
        stateMachine.addIStateToCollection("BattleStateFight", new BattleStateFight(handler, player));
        stateMachine.addIStateToCollection("BattleStateItemList", new BattleStateItemList(handler, player));
        stateMachine.addIStateToCollection("BattleBeltList", new BattleBeltList(handler, player));
        stateMachine.addIStateToCollection("BattleBeltListAction", new BattleBeltListAction(handler, player));
        stateMachine.addIStateToCollection("BattleBeltListActionSwap", new BattleBeltListActionSwap(handler, player));
        stateMachine.addIStateToCollection("BattleBeltListActionSummary", new BattleBeltListActionSummary(handler, player));
        stateMachine.addIStateToCollection("BattleStateRun", new BattleStateRun(handler, player));
        stateMachine.addIStateToCollection("BattleStateOutro", new BattleStateOutro(handler, player));

        stateMachine.push( stateMachine.getIState("BattleStateIntro"), null );
    }

    @Override
    public void tick(long timeElapsed) {
        stateMachine.getCurrentState().tick(timeElapsed);
    }

    @Override
    public void render(Graphics g) {
        stateMachine.getCurrentState().render(g);
    }

    @Override
    public void enter(Object[] args) {
        //initialize critterOfOpponent (the ACTUAL one [not a dummy/filler]).
        if (args != null) {
            if (args[0] instanceof Critter) {
                System.out.println("BattleState.enter(Object[]) is NOT null and args[0] is a Critter.");

                //TODO: random CritterFactory based on tile's x and y values compared to WorldMap's enum of City, Route, and Place.
                critterOfOpponent = (Critter)args[0];
            }
        }

        //initialize critterOfPlayer (find FIRST Critter that is NOT FAINTED and SET it to be critterOfPlayer).
        for (int i = 0; i < player.getCritterBeltList().size(); i++) {
            if (player.getCritterBeltList().get(i).getStatus() != Critter.StatusConditionNonVolatile.FAINTED) {
                critterOfPlayer = player.getCritterBeltList().get(i);
                break;
            }
        }
    }

    @Override
    public void exit() {

    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    public int calculateExpYieldEffective(Critter critterFainted) {
        int expYieldEffective = 0;

        //trainer-battle is 1.5f, wild-encounter is 1f.
        float battleType = (critterFainted.getIdNumberOriginalTrainer() == Critter.WILD) ? (1f) : (1.5f);
        float originalTrainer = (critterOfPlayer.getIdNumberOriginalTrainer() == player.getIdNumber()) ? (1f) : (1.5f);
        int expYieldBase = critterFainted.getSpecies().getExpYieldBase();
        int notHoldingEgg = 1;
        int levelOfFaintedCritter = critterFainted.getLevel();
        int noExpPowerPoint = 1;
        int noAffectionFeature = 1;
        int notPastEvolveLevel = 1;
        //TODO: track numberCrittersBattled.
        int numberCrittersBattled = 1;

        expYieldEffective = (int)((battleType * originalTrainer * expYieldBase * notHoldingEgg * levelOfFaintedCritter *
                noExpPowerPoint * noAffectionFeature * notPastEvolveLevel) / (7 * numberCrittersBattled));

        return expYieldEffective;
    }

    public void awardLoot(Critter critterUnfainted) {
        System.out.println("BattleState.awardLoot(Critter) for: " + critterUnfainted.getNameColloquial());

        Critter critterFainted =
                (critterOfPlayer == critterUnfainted) ?
                        (critterOfOpponent) : (critterOfPlayer);
        //TODO: award loot (expYieldEffective and the different evExp).
        int expYieldEffective = calculateExpYieldEffective(critterFainted);
        int evHP = critterFainted.getSpecies().getHpBase();
        int evAttack = critterFainted.getSpecies().getAttackBase();
        int evDefense = critterFainted.getSpecies().getDefenseBase();
        int evSpecial = critterFainted.getSpecies().getSpecialBase();
        int evSpeed = critterFainted.getSpecies().getSpeedBase();

        System.out.println("expYieldEffective is " + expYieldEffective + ".");
        System.out.println("evHP: " + critterFainted.getSpecies().getHpBase() + ".");
        System.out.println("evAttack: " + critterFainted.getSpecies().getAttackBase() + ".");
        System.out.println("evDefense: " + critterFainted.getSpecies().getDefenseBase() + ".");
        System.out.println("evSpecial: " + critterFainted.getSpecies().getSpecialBase() + ".");
        System.out.println("evSpeed: " + critterFainted.getSpecies().getSpeedBase() + ".");

        critterUnfainted.incrementExpCurrent(expYieldEffective);
        critterUnfainted.getStatsModule().incrementEV(StatsModule.Type.HP, evHP);
        critterUnfainted.getStatsModule().incrementEV(StatsModule.Type.ATTACK, evAttack);
        critterUnfainted.getStatsModule().incrementEV(StatsModule.Type.DEFENSE, evDefense);
        critterUnfainted.getStatsModule().incrementEV(StatsModule.Type.SPECIAL, evSpecial);
        critterUnfainted.getStatsModule().incrementEV(StatsModule.Type.SPEED, evSpeed);

        System.out.println("BattleState.awardLoot(Critter critterUnfainted) level-before-checkLevelUp() is " + critterUnfainted.getLevel() + ".");
        critterUnfainted.checkLevelUp();
    }

    // GETTERS AND SETTERS

    public Critter getCritterOfOpponent() { return critterOfOpponent; }

    public Critter getCritterOfPlayer() { return critterOfPlayer; }

    public void setCritterOfPlayer(Critter critterOfPlayer) { this.critterOfPlayer = critterOfPlayer; }

    public StateMachine getStateMachine() { return stateMachine; }

} // **** end BattleState class ****