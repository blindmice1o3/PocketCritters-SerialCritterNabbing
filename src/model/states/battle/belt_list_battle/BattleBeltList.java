package model.states.battle.belt_list_battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.stats.StatsModule;
import model.states.IState;
import model.states.StateMachine;
import model.states.battle.BattleState;
import view.Cursor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class BattleBeltList implements IState {

    private Handler handler;
    private Player player;

    private int index;
    private Cursor cursor;

    public BattleBeltList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        index = 0;
        cursor = new Cursor(3, 27, 60, 20, 20);
    } // **** end BattleBeltList(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //upButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleBeltList.tick(long)... upButton");

            index--;
            if (index < 0) {
                index = (player.getCritterBeltList().size() - 1);
            }

            cursor.updateCursorPosition(index);
        }
        //downButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleBeltList.tick(long)... downButton");

            index++;
            if (index > (player.getCritterBeltList().size() - 1)) {
                index = 0;
            }

            cursor.updateCursorPosition(index);
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleBeltList.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                //pop self (BattleBeltList).
                stateMachine.pop();
                //now BattleStateMenu.
            }
            ///////////////////////////////
        }
        //aButton
        //pass in xCursor and yCursor (and index) as an int[] for Object[] args during enter(Object[]).
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleBeltList.tick(long)... aButton");
            System.out.println("Critter selected: " + player.getCritterBeltList().get(index) );

            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();
                int widthSpeciesIcon = player.getCritterBeltList().get(index).getSpeciesIcon().getWidth();

                int xNewPanel = (25 + widthSpeciesIcon + cursor.getxOffset());
                ///////////////////////////////////////////////////////////////////////////////
                //if in top part of the screen, set the new IState's yStart BELOW nameColloquial
                //if in bottom part of screen, set the new IState's yStart ABOVE nameColloquial.
                int yNewPanel = (cursor.getY() < (handler.getGame().getWidth() / 3)) ?
                        (cursor.getY()+5) : (cursor.getY()+5-BattleBeltListAction.HEIGHT-20-3);
                ///////////////////////////////////////////////////////////////////////////////

                int[] cursorPositionAndIndexCritterBeltList = { xNewPanel, yNewPanel, index};
                Object[] args = { cursorPositionAndIndexCritterBeltList };

                ///////////////////////////////////////////////////////////////////////////////
                stateMachine.push(
                        stateMachine.getIState("BattleBeltListAction"), args
                );
                ///////////////////////////////////////////////////////////////////////////////
            }
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

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

} // **** end BattleBeltList class ****