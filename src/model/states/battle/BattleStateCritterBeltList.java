package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.stats.StatsModule;
import model.states.IState;
import model.states.StateMachine;
import view.Cursor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class BattleStateCritterBeltList implements IState {

    private Handler handler;
    private Player player;

    private int index;
    private Cursor cursor;

    public BattleStateCritterBeltList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        index = 0;
        cursor = new Cursor(3, 27, 60, 20, 20);
    } // **** end BattleStateCritterBeltList(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateCritterBeltList.tick(long)... upButton");

            index--;
            if (index < 0) {
                index = (player.getCritterBeltList().size() - 1);
            }

            cursor.updateCursorPosition(index);
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateCritterBeltList.tick(long)... downButton");

            index++;
            if (index > (player.getCritterBeltList().size() - 1)) {
                index = 0;
            }

            cursor.updateCursorPosition(index);
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateCritterBeltList.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                //pop self (BattleStateCritterBeltList).
                stateMachine.pop();
                //now BattleStateMenu.
            }
            ///////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateCritterBeltList.tick(long)... aButton");

            System.out.println("Critter selected: " + player.getCritterBeltList().get(index) );
        }
    }

    @Override
    public void render(Graphics g) {
        //BACKGROUND
        g.drawImage(Assets.backgroundCritterBeltList, 0, 0,
                handler.getGame().getWidth(), handler.getGame().getHeight(), null);

        //CritterBeltList
        BufferedImage speciesIcon = null;
        int numberOfCritters = player.getCritterBeltList().size();
        int xOffset = 25;
        int yOffset = 5;
        for (int i = 0; i < numberOfCritters; i++) {
            speciesIcon = player.getCritterBeltList().get(i).getSpeciesIcon();
            String name = player.getCritterBeltList().get(i).getNameColloquial();
            String level = String.format("%02d", player.getCritterBeltList().get(i).getLevel());
            String hpEffectiveCurrent = String.format("%3d", player.getCritterBeltList().get(i).getHpEffectiveCurrent());
            String hpEffectiveMax = String.format("%3d", player.getCritterBeltList().get(i).getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP));
            ///////////////////////////////
            g.drawImage(speciesIcon, xOffset, yOffset, null);
            FontGrabber.renderString(g, name, (xOffset + speciesIcon.getWidth() + 5), (yOffset + 5), 20, 20);
            FontGrabber.renderString(g, level, (xOffset + 430), (yOffset + 2), 20, 20);
            FontGrabber.renderString(g, hpEffectiveCurrent, (xOffset + 430), (yOffset + 32), 20, 20);
            FontGrabber.renderString(g, hpEffectiveMax, (xOffset + 520), (yOffset + 32), 20, 20);
            ///////////////////////////////
            yOffset += 60;
        }

        //CURSOR
        cursor.render(g);
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end BattleStateCritterBeltList class ****