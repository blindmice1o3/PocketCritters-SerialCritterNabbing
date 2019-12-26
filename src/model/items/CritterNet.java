package model.items;

import main.Handler;
import model.entities.critters.Critter;
import model.states.battle.BattleState;

public class CritterNet extends Item {

    public CritterNet(Handler handler) {
        super(handler, Identifier.CRITTER_NET);
    }

    public void execute() {
        System.out.println("CritterNet.execute().");

        if (handler.getStateManager().getCurrentState() instanceof BattleState) {
            Critter critterOfOpponent = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();


        }
    }

} // **** end CritterNet class ****