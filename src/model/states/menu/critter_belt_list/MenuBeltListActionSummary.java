package model.states.menu.critter_belt_list;

import main.Handler;
import main.utils.FontGrabber;
import model.entities.Player;
import model.entities.critters.Critter;
import model.entities.critters.moves.MovesModule;
import model.entities.critters.stats.StatsModule;
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
        String id = "No." + String.format("%03d", critter.getSpecies().getId());
        String name = critter.getNameColloquial();
        int xOffset = 0;
        int yOffset = 0;
        switch(currentPage) {
            case STATS:
                //quadrant1
                g.drawImage(critter.getSpeciesIcon(), 5, 5, xHalfScreen-60, yHalfScreen-60, null);
                FontGrabber.renderString(g, id, 5, 5+(yHalfScreen-60)+5, 20, 20);
                //quadrant2
                xOffset = xHalfScreen-50;
                yOffset = 5;
                FontGrabber.renderString(g, name, xOffset, yOffset, 20, 20);
                String level = ":L" + critter.getLevel();
                FontGrabber.renderString(g, level, xOffset+(xHalfScreen/2), yOffset+20, 20, 20);
                //INSERT hp bar image
                String hpCurrentAndMax = critter.getHpEffectiveCurrent() + "/" + critter.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.HP);
                int x = handler.getGame().getWidth()-(hpCurrentAndMax.length() * 20)-5;
                FontGrabber.renderString(g, hpCurrentAndMax, x, yOffset+50, 20, 20);
                String status = "STATUS/" + critter.getStatus().toString();
                FontGrabber.renderString(g, status, xOffset, yOffset+75, 20, 20);
                //quadrant3
                xOffset = 5;
                yOffset = yHalfScreen+5;
                g.drawRect(xOffset, yOffset, xHalfScreen-10, yHalfScreen-10);
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
                String attack = Integer.toString(critter.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.ATTACK));
                xOffset = xHalfScreen-5-(attack.length() * 20);
                FontGrabber.renderString(g, attack, xOffset, yOffset, 20, 20);
                yOffset += 40;
                String defense = Integer.toString(critter.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.DEFENSE));
                xOffset = xHalfScreen-5-(defense.length() * 20);
                FontGrabber.renderString(g, defense, xOffset, yOffset, 20, 20);
                yOffset += 40;
                String speed = Integer.toString(critter.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.SPEED));
                xOffset = xHalfScreen-5-(speed.length() * 20);
                FontGrabber.renderString(g, speed, xOffset, yOffset, 20, 20);
                yOffset += 40;
                String special = Integer.toString(critter.getStatsModule().getStatsEffectiveMap().get(StatsModule.Type.SPECIAL));
                xOffset = xHalfScreen-5-(special.length() * 20);
                FontGrabber.renderString(g, special, xOffset, yOffset, 20, 20);
                //quadrant4
                xOffset = xHalfScreen+5;
                yOffset = yHalfScreen+5;
                String type1 = critter.getSpecies().getType1().toString();
                String type2 = critter.getSpecies().getType2().toString();
                FontGrabber.renderString(g, "TYPE1/", xOffset, yOffset, 20, 20);
                yOffset += 20;
                xOffset += 20;
                FontGrabber.renderString(g, type1, xOffset, yOffset, 20, 20);
                yOffset += 20;
                xOffset = xHalfScreen+5;
                FontGrabber.renderString(g, "TYPE2/", xOffset, yOffset, 20, 20);
                yOffset += 20;
                xOffset += 20;
                FontGrabber.renderString(g, type2, xOffset, yOffset, 20, 20);
                yOffset +=20;
                xOffset = xHalfScreen+5;
                FontGrabber.renderString(g, "IDNo/", xOffset, yOffset, 20, 20);
                yOffset += 20;
                xOffset += (20+20);
                FontGrabber.renderString(g, Integer.toString(critter.hashCode()), xOffset, yOffset, 20, 20);
                yOffset += 20;
                xOffset = xHalfScreen+5;
                FontGrabber.renderString(g, "OT/", xOffset, yOffset, 20, 20);
                yOffset += 20;
                xOffset += (20+20);
                //TODO: switch player.toString() to unimplemented Player.name
                FontGrabber.renderString(g, player.toString(), xOffset, yOffset, 20, 20);

                break;
            case MOVES:
                //quadrant1
                g.drawImage(critter.getSpeciesIcon(), 5, 5, xHalfScreen-60, yHalfScreen-60, null);
                FontGrabber.renderString(g, id, 5, 5+(yHalfScreen-60)+5, 20, 20);
                //quadrant2
                xOffset = xHalfScreen-50;
                yOffset = 5;
                FontGrabber.renderString(g, name, xOffset, yOffset, 20, 20);
                yOffset += 20;
                FontGrabber.renderString(g, "EXP POINTS", xOffset, yOffset, 20, 20);
                yOffset += 20;
                String expCurrent = Integer.toString(critter.getExpCurrent());
                x = handler.getGame().getWidth()-(expCurrent.length() * 20)-5;
                FontGrabber.renderString(g, expCurrent, x, yOffset, 20, 20);
                yOffset += 20;
                FontGrabber.renderString(g, "LEVEL UP", xOffset, yOffset, 20, 20);
                yOffset += 20;
                //TODO: "experience group list.txt" look-up table for expNextLevel.
                //String levelUp = (/*TODO: look-up table for expNextLevel*/ - critter.getExpCurrent()) + "to :L" + (critter.getLevel() + 1);
                String levelUp = "otw to :L" + (critter.getLevel() + 1);
                x = handler.getGame().getWidth()-(levelUp.length() * 20)-5;
                FontGrabber.renderString(g, levelUp, x, yOffset, 20, 20);
                //quadrant3 AND quadrant4
                yOffset = yHalfScreen+5;
                xOffset = 5;
                g.drawRect(xOffset, yOffset, handler.getGame().getWidth()-10, yHalfScreen-10);
                yOffset += 5;
                xOffset += 20;
                x = handler.getGame().getWidth()-5-5-(8 * 20);

                MovesModule movesModule = critter.getMovesModule();
                String nameMove = "-dash";
                String ppInfoMove = "_underline_underline";
                int[] moves = movesModule.getMovesCurrent();
                int[] ppMoves = movesModule.getPpMovesCurrent();
                for (int i = 0; i < moves.length; i++) {
                    if (moves[i] != 0) {
                        nameMove = movesModule.lookUpMove(moves[i]).toString();
                        ppInfoMove = "pp " + ppMoves[i] + "/" +
                                movesModule.lookUpMove(moves[i]).getPpBase();

                        FontGrabber.renderString(g, nameMove, xOffset, yOffset, 20, 20);
                        yOffset += 20;
                        FontGrabber.renderString(g, ppInfoMove, x, yOffset, 20, 20);
                        yOffset += 20;
                    }
                }
                /*
                if (movesModule.getIdMove1() != 0) {
                    nameMove1 = movesModule.lookUpMove(movesModule.getIdMove1()).toString();
                    ppInfoMove1 = "pp " + movesModule.getPpMove1() + "/" +
                            movesModule.lookUpMove(movesModule.getIdMove1()).getPpBase();

                }
                FontGrabber.renderString(g, nameMove1, xOffset, yOffset, 20, 20);
                yOffset += 20;
                FontGrabber.renderString(g, ppInfoMove1, x, yOffset, 20, 20);
                yOffset += 20;

                String nameMove2 = "-";
                String ppInfoMove2 = "__";
                if (movesModule.getIdMove2() != 0) {
                    nameMove2 = movesModule.lookUpMove(movesModule.getIdMove2()).toString();
                    ppInfoMove2 = "pp " + movesModule.getPpMove2() + "/" +
                            movesModule.lookUpMove(movesModule.getIdMove2()).getPpBase();
                }
                FontGrabber.renderString(g, nameMove2, xOffset, yOffset, 20, 20);
                yOffset += 20;
                FontGrabber.renderString(g, ppInfoMove2, x, yOffset, 20, 20);
                yOffset += 20;

                String nameMove3 = "-";
                String ppInfoMove3 = "__";
                if (movesModule.getIdMove3() != 0) {
                    nameMove3 = movesModule.lookUpMove(movesModule.getIdMove3()).toString();
                    ppInfoMove3 = "pp " + movesModule.getPpMove3() + "/" +
                            movesModule.lookUpMove(movesModule.getIdMove3()).getPpBase();
                }
                FontGrabber.renderString(g, nameMove3, xOffset, yOffset, 20, 20);
                yOffset += 20;
                FontGrabber.renderString(g, ppInfoMove3, x, yOffset, 20, 20);
                yOffset += 20;

                String nameMove4 = "-";
                String ppInfoMove4 = "__";
                if (movesModule.getIdMove4() != 0) {
                    nameMove4 = movesModule.lookUpMove(movesModule.getIdMove4()).toString();
                    ppInfoMove4 = "pp " + movesModule.getPpMove4() + "/" +
                            movesModule.lookUpMove(movesModule.getIdMove4()).getPpBase();
                }
                FontGrabber.renderString(g, nameMove4, xOffset, yOffset, 20, 20);
                yOffset += 20;
                FontGrabber.renderString(g, ppInfoMove4, x, yOffset, 20, 20);
                */

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