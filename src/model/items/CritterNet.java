package model.items;

import main.Handler;
import model.entities.critters.Critter;
import model.entities.critters.stats.StatsModule;
import model.states.StateMachine;
import model.states.battle.BattleState;
import model.states.game.GameState;

public class CritterNet extends Item {

    public CritterNet(Handler handler) {
        super(handler, Identifier.CRITTER_NET);
    }

    /* Capture method (Generation 1) (https://bulbapedia.bulbagarden.net/wiki/Catch_rate)
     5. If not, generate a random value, M, between 0 and 255.
     6. Calculate f:
            f = (HPmax * 255 * 4) / (HPcurrent * Ball),
                where all divisions are rounded down to the nearest integer.
            The minimum value of f is 1 and its maximum value is 255.
            The value of Ball is 8 if a Great Ball is used or 12 otherwise.
     7. If f is greater than or equal to M, the Pokémon is caught.
         Otherwise, the Pokémon breaks free.
         In practical terms, lowering the target's HP to 1/3 of its maximum will
         guarantee capture with a Poké Ball, while lowering it to 1/2 will
         guarantee capture with a Great Ball.
     */
    @Override
    public void execute(Critter targetedCritter) {
        System.out.println("CritterNet.execute(Critter).");

        if (handler.getStateManager().getCurrentState() instanceof BattleState) {
            BattleState battleState = (BattleState)handler.getStateManager().getIState("BattleState");


            if (targetedCritter.getIdNumberOriginalTrainer() == Critter.WILD) {
                StatsModule statsModuleOfOpponent = targetedCritter.getStatsModule();

                int mValue = (int)(Math.random() * 256);
                System.out.println("mValue: " + mValue);

                int fValue = (statsModuleOfOpponent.getStatsEffectiveMap().get(StatsModule.Type.HP) * 255 * 4) /
                        (targetedCritter.getHpEffectiveCurrent() * 12);
                System.out.println("fValue: " + fValue);

                //maybe Math.ceiling and Math.floor
                if (fValue < 1) {
                    fValue = 1;
                } else if (fValue > 255) {
                    fValue = 255;
                }

                boolean isCaught = (fValue >= mValue);
                System.out.println("Is fValue >= mValue? " + isCaught);

                if (isCaught) {
                    int idNumberPlayer = handler.getGame().getPlayer().getIdNumber();
                    targetedCritter.setIdNumberOriginalTrainer( idNumberPlayer );

                    //add caught critter to player.critterBeltList.
                    if (handler.getGame().getPlayer().getCritterBeltList().size() < 6) {
                        System.out.println("CritterNet.execute(Critter) NABBED: " + targetedCritter.getNameColloquial() + ", adding to critterBeltList.");
                        //////////////////////////////////////////////////////////////////////////
                        handler.getGame().getPlayer().getCritterBeltList().add(targetedCritter);
                        //////////////////////////////////////////////////////////////////////////
                    }
                    //move caught critter to GameState.critterStorageSystem.
                    else {
                        System.out.println("CritterNet.execute(Critter) NABBE: " + targetedCritter.getNameColloquial() + ", but critterBeltList was at MAX... SENDING TO GameState.critterStorageSystem.");
                        //////////////////////////////////////////////////////////////////////////
                        GameState gameState = (GameState) handler.getStateManager().getIState("GameState");
                        Critter[][] critterStorageSystem = gameState.getCritterStorageSystem();

                        for (int i = 0; i < GameState.MAX_NUMBER_OF_CRITTERS_PER_BOX; i++) {
                            if (critterStorageSystem[gameState.getIndexCurrentBox()][i] == null) {
                                critterStorageSystem[gameState.getIndexCurrentBox()][i] = targetedCritter;
                                System.out.println("storing critter in: " + gameState.getIndexCurrentBox() + ", " + i);
                                break;
                            }

                        }
                        //////////////////////////////////////////////////////////////////////////
                    }



                    StateMachine stateMachineBattleState = battleState.getStateMachine();
                    //pop out of BattleStateItemList.
                    stateMachineBattleState.pop();
                    //pop out of BattleStateMenu.
                    stateMachineBattleState.pop();
                    //now at BattleStateIntro.

                    //pop BattleState off the root (Game's stateManager).
                    handler.getStateManager().pop();
                    //should be in GameState at this point.
                    System.out.println(handler.getStateManager().getCurrentState().getClass().getSimpleName());


                } else {
                    System.out.println("AWWWW, CritterNet was not able to nab: " + targetedCritter.getNameColloquial() + ".");
                }
            }
        }
    }

} // **** end CritterNet class ****