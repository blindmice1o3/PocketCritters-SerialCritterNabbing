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
        if (currentPhase == Phase.INTRO) {
            System.out.println("BattleState.updateInput() with currentPhase == Phase.INTRO");
            if (handler.getKeyManager().aButton) {
                System.out.println("BattleState.updateInput() with currentPhase == Phase.INTRO... aButton");
                ///////////////////////////////
                nextPhase = Phase.GAME_LOOP;
                ///////////////////////////////
            }
        } else if (currentPhase == Phase.GAME_LOOP) {
            System.out.println("BattleState.updateInput() with currentPhase == Phase.GAME_LOOP");
            //UP
            if (handler.getKeyManager().up) {
                System.out.println("BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... up");
                if (yIndex > 0) {
                    yIndex--;
                }
            }
            //DOWN
            else if (handler.getKeyManager().down) {
                System.out.println("BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... down");
                if (yIndex == 0) {
                    yIndex++;
                }
            }
            //LEFT
            else if (handler.getKeyManager().left) {
                System.out.println("BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... left");
                if (xIndex > 0) {
                    xIndex--;
                }
            }
            //RIGHT
            else if (handler.getKeyManager().right) {
                System.out.println("BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... right");
                if (xIndex == 0) {
                    xIndex++;
                }
            }
            //delete later
            else if (handler.getKeyManager().aButton) {
                System.out.println("BattleState.updateInput() with currentPhase == Phase.GAME_LOOP... aButton");
                ///////////////////////////////
                nextPhase = Phase.OUTRO;
                ///////////////////////////////
            }
        } else if (currentPhase == Phase.OUTRO) {
            System.out.println("BattleState.updateInput() with currentPhase == Phase.OUTRO");
            if (handler.getKeyManager().aButton) {
                System.out.println("BattleState.updateInput() with currentPhase == Phase.OUTRO... aButton");
                ///////////////////////////////
                nextPhase = Phase.INTRO;
                ///////////////////////////////
            }
        }
    }

    @Override
    public void tick() {
        if (currentPhase == Phase.INTRO) {
            System.out.println("BattleState.tick() with currentPhase == Phase.INTRO");
            ///////////////////////////////
            currentPhase = nextPhase;
            ///////////////////////////////
        } else if (currentPhase == Phase.GAME_LOOP) {
            System.out.println("BattleState.tick() with currentPhase == Phase.GAME_LOOP");
            //DO STUFF... check player1's input, player1 do stuff, check player2's input, player2 do stuff... loop.

            //end of while loop... nextPhase = Phase.OUTRO;

            //After a winner is determined.
            ///////////////////////////////
            currentPhase = nextPhase;
            ///////////////////////////////
        } else if (currentPhase == Phase.OUTRO) {
            System.out.println("BattleState.tick() with currentPhase == Phase.OUTRO");
            //DO STUFF...


            ///////////////////////////////
            StateManager.change("GameState", null);
            ///////////////////////////////
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.battleStateSpriteSheet, 0, 0, null);
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