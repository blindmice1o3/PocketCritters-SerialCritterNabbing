package model.states.menu;

import main.Handler;
import main.gfx.Assets;
import main.utils.FontGrabber;
import model.entities.Player;
import model.states.IState;
import model.states.StateMachine;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateItemList implements IState {

    private Handler handler;
    private Player player;

    private int indexCurrentItem;

    public MenuStateItemList(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        this.indexCurrentItem = 0;
    } // **** end MenuStateItemList(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuStateItemList.tick(long)... bButton");

            ///////////////////////////////
            if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                StateMachine stateMachine = menuState.getStateMachine();

                //pop self (MenuStateItemList).
                stateMachine.pop();
                //now MenuStateMenu.
            }
            ///////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuStateItemList.tick(long)... aButton");

            ///////////////////////////////
            player.getInventory().get(indexCurrentItem).execute();
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        //BACKGROUND PANEL
        g.setColor(Color.GRAY);
        g.fillRect(25, 25,
                handler.getGame().getWidth() - 50, handler.getGame().getHeight() - 50);



        if (player.getInventory().size() > 0) {
            g.setColor(Color.CYAN);
            int xOffset = 25 + 20;
            int yOffset = 25 + 20;
            int xOffsetFromRight = handler.getGame().getWidth() - 25 - 20;
            for (int i = 0; i < 5; i++) {
                //CURSOR
                if (i == indexCurrentItem) {
                    g.drawImage(Assets.cursorSprite, xOffset - 15 + 5, yOffset + 7, null);
                }

                //NAME and QUANTITY
                if (i < player.getInventory().size()) {
                    String itemName = player.getInventory().get(i).getIdentifier().toString();
                    String itemQuantity = "x" + Integer.toString( player.getInventory().get(i).getQuantity() );

                    FontGrabber.renderString(g, itemName,
                            xOffset, yOffset, 20, 20);
                    FontGrabber.renderString(g, itemQuantity,
                            xOffsetFromRight - (itemQuantity.length() * 20), yOffset, 20, 20);

                    yOffset += 25;
                }
            }
        }


    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end MenuStateItemList class ****