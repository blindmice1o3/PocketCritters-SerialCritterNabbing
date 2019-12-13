package model.states.menu.critter_belt_list;

import main.Handler;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.states.IState;
import model.states.StateMachine;
import model.states.menu.MenuState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuBeltListActionSummary implements IState {

    private enum Page { STATS, MOVES; }

    private Handler handler;
    private Player player;
    private int indexCritterSelected;

    private Page currentPage;

    public MenuBeltListActionSummary(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        currentPage = Page.STATS;
    } // **** end MenuBeltListActionSummary(Handler, Player) constructor ****

    @Override
    public void tick(long timeElapsed) {
        //bButton
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
            System.out.println("MenuBeltListActionSummary.tick(long)... bButton");

            switch(currentPage) {
                case STATS:
                    ///////////////////////////////
                    if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                        MenuState menuState = (MenuState)handler.getStateManager().getCurrentState();
                        StateMachine stateMachine = menuState.getStateMachine();

                        //pop self (MenuBeltListActionSummary).
                        stateMachine.pop();
                        //pop (MenuBeltListAction).
                        stateMachine.pop();
                        //now MenuBeltList.
                    }
                    ///////////////////////////////
                    break;
                case MOVES:
                    currentPage = Page.STATS;
                    break;
                default:
                    System.out.println("MenuBeltListActionSummary.tick(long) bButton switch(currentPage) construct's default block.");
                    break;
            }
        }
        //aButton
        else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
            System.out.println("MenuBeltListActionSummary.tick(long)... aButton");

            switch(currentPage) {
                case STATS:
                    currentPage = Page.MOVES;
                    break;
                case MOVES:
                    ///////////////////////////////
                    if (handler.getStateManager().getCurrentState() instanceof MenuState) {
                        MenuState menuState = (MenuState) handler.getStateManager().getCurrentState();
                        StateMachine stateMachine = menuState.getStateMachine();

                        //pop self (MenuBeltListActionSummary).
                        stateMachine.pop();
                        //pop (MenuBeltListAction).
                        stateMachine.pop();
                        //now MenuBeltList.
                    }
                    ///////////////////////////////
                    break;
                default:
                    System.out.println("MenuBeltListActionSummary.tick(long) aButton switch(currentPage) construct's default block.");
                    break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());
        g.setColor(Color.BLACK);

        int xHalfScreen = (handler.getGame().getWidth() / 2);
        int yHalfScreen = (handler.getGame().getHeight() / 2);
        Critter critter = player.getCritterBeltList().get(indexCritterSelected);

        switch(currentPage) {
            case STATS:
                //quadrant1
                g.drawImage(critter.getSpeciesIcon(), 5, 5, xHalfScreen-60, yHalfScreen-60, null);
                String id = "No." + String.format("%03d", critter.getSpecies().getId());
                FontGrabber.renderString(g, id, 5, 5+(yHalfScreen-60)+5, 20, 20);
                //quadrant2
                int xOffset = xHalfScreen-50;
                int yOffset = 5;
                String name = critter.getNameColloquial();
                FontGrabber.renderString(g, name, xOffset, yOffset, 20, 20);
                String level = ":L" + critter.getLevel();
                FontGrabber.renderString(g, level, xOffset+(xHalfScreen/2), yOffset+20, 20, 20);
                //INSERT hp bar image
                String hpCurrentAndMax = critter.getHpCurrent() + "/" + critter.getSpecies().getHpBase();
                int x = handler.getGame().getWidth()-(hpCurrentAndMax.length() * 20)-5;
                FontGrabber.renderString(g, hpCurrentAndMax, x, yOffset+50, 20, 20);
                String status = "STATUS/" + critter.getStatus().toString();
                FontGrabber.renderString(g, status, xOffset, yOffset+75, 20, 20);
                //quadrant3
                xOffset = 0;
                yOffset = yHalfScreen;
                g.drawRect(xOffset, yOffset, xHalfScreen, yHalfScreen);
                xOffset += 5;
                yOffset += 5;
                FontGrabber.renderString(g, "ATTACK", xOffset, yOffset, 20, 20);
                yOffset += 40;
                FontGrabber.renderString(g, "DEFENSE", xOffset, yOffset, 20, 20);
                yOffset += 40;
                FontGrabber.renderString(g, "SPEED", xOffset, yOffset, 20, 20);
                yOffset += 40;
                FontGrabber.renderString(g, "SPECIAL", xOffset, yOffset, 20, 20);

                yOffset = yHalfScreen+5+20;
                String attack = Integer.toString(critter.getSpecies().getAttackBase());
                xOffset = xHalfScreen-5-(attack.length() * 20);
                FontGrabber.renderString(g, attack, xOffset, yOffset, 20, 20);
                yOffset += 40;
                String defense = Integer.toString(critter.getSpecies().getDefenseBase());
                xOffset = xHalfScreen-5-(defense.length() * 20);
                FontGrabber.renderString(g, defense, xOffset, yOffset, 20, 20);
                yOffset += 40;
                String speed = Integer.toString(critter.getSpecies().getSpeedBase());
                xOffset = xHalfScreen-5-(speed.length() * 20);
                FontGrabber.renderString(g, speed, xOffset, yOffset, 20, 20);
                yOffset += 40;
                String special = Integer.toString(critter.getSpecies().getSpecialBase());
                xOffset = xHalfScreen-5-(special.length() * 20);
                FontGrabber.renderString(g, special, xOffset, yOffset, 20, 20);
                //quadrant4
                xOffset = xHalfScreen;
                yOffset = yHalfScreen;

                break;
            case MOVES:
                FontGrabber.renderString(g, "MOVES", handler.getGame().getWidth()/2, handler.getGame().getHeight()/2, 20, 20);
                break;
            default:
                System.out.println("MenuBeltListActionSummary.render(Graphics) switch(currentPage) construct's default block.");
                break;
        }
    }

    @Override
    public void enter(Object[] args) {
        if (args != null) {
            indexCritterSelected = (int)args[0];
        }
    }

    @Override
    public void exit() {

    }

} // **** end MenuBeltListActionSummary class ****