package model.states.menu;

import main.Handler;
import main.gfx.Assets;
import model.entities.nabbers.Player;
import model.states.IState;
import model.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuStateMenu implements IState {

    private Handler handler;
    private Player player;

    private int indexMenu;
    private String[] menuList = { "MenuStateCritterDex", "MenuStateCritterBeltList",
            "MenuStateItemList", "MenuStatePlayerStats",
            "MenuStateSave", "MenuStateOption", "MenuStateExit" };

    public MenuStateMenu(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        indexMenu = 0;
    } // **** end MenuStateMenu(Handler, Player) constructor ****

    @Override
    public void tick() {
        System.out.println("MenuStateMenu.tick()");

        //UP
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
            System.out.println("MenuStateMenu.tick()... up");

            indexMenu--;
            if (indexMenu < 0) {
                indexMenu = (menuList.length-1);
            }
        }
        //DOWN
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
            System.out.println("MenuStateMenu.tick()... down");

            indexMenu++;
            if (indexMenu >= (menuList.length)) {
                indexMenu = 0;
            }
        }
        //LEFT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
            System.out.println("MenuStateMenu.tick()... left");
        }
        //RIGHT
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
            System.out.println("MenuStateMenu.tick()... right");
        }
        //startButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
            System.out.println("MenuStateMenu.tick()... startButton pressed (VK_ENTER).");

            /////////////////////////////////////////////
            Object[] args = { player };
            StateManager.change("GameState", args);
            /////////////////////////////////////////////
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuStateMenu.tick()... aButton");

            ///////////////////////////////
            Object[] args = { player };
            StateManager.change(menuList[indexMenu], args);
            ///////////////////////////////
        }
        //bButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuStateMenu.tick()... bButton");

            ///////////////////////////////
            Object[] args = { player };
            StateManager.change("GameState", args);
            ///////////////////////////////
        }
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.menuStateSpriteSheet.getSubimage(239, 3, 75, 124),
                325, 10,
                (handler.getGame().getWidth()-10), (handler.getGame().getHeight()-10),
                0, 0,
                Assets.menuStateSpriteSheet.getSubimage(239, 3, 75, 124).getWidth(),
                Assets.menuStateSpriteSheet.getSubimage(239, 3, 75, 124).getHeight(), null);

        if (menuList[indexMenu].equals("MenuStateCritterDex")) {
            g.drawImage(Assets.cursorSprite, 345, 70, 7 * 4, 7 * 4, null);
        } else if (menuList[indexMenu].equals("MenuStateCritterBeltList")) {
            g.drawImage(Assets.cursorSprite, 345, 137, 7 * 4, 7 * 4, null);
        } else if (menuList[indexMenu].equals("MenuStateItemList")) {
            g.drawImage(Assets.cursorSprite, 345, 204, 7 * 4, 7 * 4, null);
        } else if (menuList[indexMenu].equals("MenuStatePlayerStats")) {
            g.drawImage(Assets.cursorSprite, 345, 271, 7 * 4, 7 * 4, null);
        } else if (menuList[indexMenu].equals("MenuStateSave")) {
            g.drawImage(Assets.cursorSprite, 345, 338, 7 * 4, 7 * 4, null);
        } else if (menuList[indexMenu].equals("MenuStateOption")) {
            g.drawImage(Assets.cursorSprite, 345, 405, 7 * 4, 7 * 4, null);
        } else if (menuList[indexMenu].equals("MenuStateExit")) {
            g.drawImage(Assets.cursorSprite, 345, 472, 7 * 4, 7 * 4, null);
        }
    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

} // **** end MenuStateMenu class ****