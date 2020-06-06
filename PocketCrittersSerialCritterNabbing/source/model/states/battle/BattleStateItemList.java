package model.states.battle;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.items.CritterNet;
import model.items.Item;
import model.items.Potion;
import model.states.IState;
import model.states.StateMachine;
import view.Cursor;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleStateItemList implements IState {

    private Handler handler;
    private Player player;

    private Cursor cursor;
    private int index;

    public BattleStateItemList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        cursor = new Cursor(150, 110, 30, 20, 20);
        index = 0;
    } // **** end BattleStateItemList(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("BattleStateItemList.tick(long)... up");

            index--;

            if (index < 0) {
                index = (player.getInventory().size() - 1);
            }

            cursor.updateCursorPosition(index);
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("BattleStateItemList.tick(long)... down");

            index++;

            if (index > (player.getInventory().size() - 1)) {
                index = 0;
            }

            cursor.updateCursorPosition(index);
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("BattleStateItemList.tick(long)... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("BattleStateItemList.tick(long)... right");
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("BattleStateItemList.tick(long)... aButton");
            Item itemSelected = player.getInventory().get(index);
            System.out.println("Item selected for use: " + itemSelected);

            Critter critterSelected = null;
            //TODO: select target (opponent's or player's critter) based on which item is selected.
            if (itemSelected instanceof CritterNet) {
                critterSelected = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfOpponent();
            } else if (itemSelected instanceof Potion) {
                critterSelected = ((BattleState)handler.getStateManager().getIState("BattleState")).getCritterOfPlayer();
            }

            if (critterSelected != null) {
                //////////////////////////////////////
                itemSelected.execute(critterSelected);
                //////////////////////////////////////
            }

            player.refreshInventory();
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("BattleStateItemList.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof BattleState) {
                BattleState battleState = (BattleState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = battleState.getStateMachine();

                stateMachine.pop();
            }
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        ((BattleState)handler.getStateManager().getIState("BattleState")).getStateMachine().getIState("BattleStateMenu").render(g);

        //BACKGROUND PANEL
        g.drawImage(Assets.backgroundBattleStateItemList, 0, 0,
                handler.getGame().getWidth(), handler.getGame().getHeight(), null);

        if (player.getInventory().size() > 0) {
            g.setColor(Color.CYAN);
            int xOffset = 150 + 25;
            int yOffset = 110;
            int xOffsetFromRight = handler.getGame().getWidth() - 25 - 20;
            for (int i = 0; i < 8; i++) {
                //NAME and QUANTITY
                if (i < player.getInventory().size()) {
                    String itemName = player.getInventory().get(i).getIdentifier().toString();
                    String itemQuantity = "x" + Integer.toString( player.getInventory().get(i).getQuantity() );

                    FontGrabber.renderString(g, itemName,
                            xOffset, yOffset, 20, 20);
                    FontGrabber.renderString(g, itemQuantity,
                            xOffsetFromRight - (itemQuantity.length() * 20), yOffset, 20, 20);

                    yOffset += 30;
                }
            }
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

} // **** end BattleStateItemList class ****