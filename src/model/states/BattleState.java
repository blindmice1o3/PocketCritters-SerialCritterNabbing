package model.states;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;

import java.awt.*;

public class BattleState implements IState {

    private enum Phase { INTRO, GAME_LOOP, OUTRO; }
    private Phase currentPhase;
    private Phase nextPhase;

    private Handler handler;

    private int xIndex, yIndex;

    private Player player;

    public BattleState(Handler handler) {
        this.handler = handler;

        currentPhase = Phase.INTRO;
        nextPhase = Phase.INTRO;

        xIndex = 0;
        yIndex = 0;
    } // **** end BattleState(Handler) constructor ****

    @Override
    public void updateInput() {
        //@@@@@INTRO@@@@@
        if (currentPhase == Phase.INTRO) {
            System.out.println("0BattleState.updateInput() with currentPhase == Phase.INTRO");
            if (handler.getKeyManager().aButton) {
                System.out.println("0BattleState.updateInput() with currentPhase == Phase.INTRO... aButton");
                ///////////////////////////////
                nextPhase = Phase.GAME_LOOP;
                ///////////////////////////////
            }
        }
        //@@@@@GAME_LOOP@@@@@
        else if (currentPhase == Phase.GAME_LOOP) {
            System.out.println("0BattleState.updateInput() with currentPhase == Phase.GAME_LOOP");
            //UP
            if (handler.getKeyManager().up) {
                System.out.println("7BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... up");
                if (yIndex > 0) {
                    yIndex--;
                }
            }
            //DOWN
            else if (handler.getKeyManager().down) {
                System.out.println("7BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... down");
                if (yIndex == 0) {
                    yIndex++;
                }
            }
            //LEFT
            else if (handler.getKeyManager().left) {
                System.out.println("7BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... left");
                if (xIndex > 0) {
                    xIndex--;
                }
            }
            //RIGHT
            else if (handler.getKeyManager().right) {
                System.out.println("7BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... right");
                if (xIndex == 0) {
                    xIndex++;
                }
            }
            //delete later
            else if (handler.getKeyManager().bButton) {
                System.out.println("0BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... bButton");
                ///////////////////////////////
                nextPhase = Phase.OUTRO;
                ///////////////////////////////
            }
        }
        //@@@@@OUTRO@@@@@
        else if (currentPhase == Phase.OUTRO) {
            System.out.println("0BattleState.updateInput() with currentPhase == Phase.OUTRO");
            if (handler.getKeyManager().startButton) {
                System.out.println("0BattleState.updateInput() with currentPhase == Phase.OUTRO... startButton");
                ///////////////////////////////
                nextPhase = Phase.INTRO;
                ///////////////////////////////
            }
        }
    }

    @Override
    public void tick() {
        //@@@@@INTRO@@@@@
        if (currentPhase == Phase.INTRO) {
            System.out.println("1BattleState.tick() with currentPhase == Phase.INTRO");

            if (nextPhase == Phase.GAME_LOOP) {
                ///////////////////////////////
                currentPhase = nextPhase;
                ///////////////////////////////
            }
        }
        //@@@@@GAME_LOOP@@@@@
        else if (currentPhase == Phase.GAME_LOOP) {
            System.out.println("1BattleState.tick() with currentPhase == Phase.GAME_LOOP");
            //DO STUFF... check player1's input, player1 do stuff, check player2's input, player2 do stuff... loop.

            //end of while loop... nextPhase = Phase.OUTRO;

            //After a winner is determined.
            if (nextPhase == Phase.OUTRO) {
                ///////////////////////////////
                currentPhase = nextPhase;
                ///////////////////////////////
            }
        }
        //@@@@@OUTRO@@@@@
        else if (currentPhase == Phase.OUTRO) {
            System.out.println("1BattleState.tick() with currentPhase == Phase.OUTRO");
            //DO STUFF...

            if (nextPhase == Phase.INTRO) {
                ///////////////////////////////
                currentPhase = nextPhase;
                ///////////////////////////////
                ///////////////////////////////
                StateManager.change("GameState", null);
                ///////////////////////////////
            }
        }
    }

    @Override
    public void render(Graphics g) {
        //@@@@@INTRO@@@@@
        if (currentPhase == Phase.INTRO) {
            //NEED TO FINE-TUNE COORDINATES!
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                    2, 2, 2+159, 2+145, null);
        }
        //@@@@@GAME_LOOP@@@@@
        else if (currentPhase == Phase.GAME_LOOP) {
            //NEED TO FINE-TUNE COORDINATES!
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                    161, 2, 161+159, 2+145, null);
        }
        //@@@@OUTRO@@@@@
        else if (currentPhase == Phase.OUTRO) {
            //NEED TO FINE-TUNE COORDINATES!
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(), handler.getGame().getHeight(),
                    320, 2, 320+159, 2+145, null);
        }
    }

    @Override
    public void enter(Object[] args) {
        //if first time entering BattleState... set the player reference variables.
        if (player == null) {
            if (args[0] instanceof Player) {
                this.player = (Player)args[0];
            }
        }
    }

    @Override
    public void exit() {

    }

} // **** end BattleState class ****