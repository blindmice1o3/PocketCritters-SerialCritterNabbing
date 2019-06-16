package model.states;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;

import java.awt.*;

public class BattleState implements IState {

    //private enum Phase { INTRO, GAME_LOOP, OUTRO; }
    //private Phase currentPhase;
    //private Phase nextPhase;

    private Handler handler;

    private Player player;

    public BattleState(Handler handler) {
        this.handler = handler;

        //currentPhase = Phase.INTRO;
        //nextPhase = Phase.INTRO;

        initStateManager();
    } // **** end BattleState(Handler) constructor ****

    private void initStateManager() {
        StateManager.add("BattleStateIntro", new BattleStateIntro(handler));
        StateManager.add("BattleStateMenu", new BattleStateMenu(handler));
        StateManager.add("BattleStateFight", new BattleStateFight(handler));
        StateManager.add("BattleStateItem", new BattleStateItem(handler));
        StateManager.add("BattleStateMonsterBeltList", new BattleStateMonsterBeltList(handler));
        StateManager.add("BattleStateRun", new BattleStateRun(handler));
        StateManager.add("BattleStateOutro", new BattleStateOutro(handler));
    }

    @Override
    public void updateInput() {

        /*
        //@@@@@INTRO@@@@@
        if (currentPhase == Phase.INTRO) {

        }
        //@@@@@GAME_LOOP@@@@@
        else if (currentPhase == Phase.GAME_LOOP) {

        }
        //@@@@@OUTRO@@@@@
        else if (currentPhase == Phase.OUTRO) {

        }
        */

    }

    @Override
    public void tick() {

        StateManager.change("BattleStateIntro", null);

        /*
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
        */

    }

    @Override
    public void render(Graphics g) {

        /*
        //@@@@@INTRO@@@@@
        if (currentPhase == Phase.INTRO) {
            //NEED TO FINE-TUNE COORDINATES!

        }
        //@@@@@GAME_LOOP@@@@@
        else if (currentPhase == Phase.GAME_LOOP) {
            //NEED TO FINE-TUNE COORDINATES!

        }
        //@@@@OUTRO@@@@@
        else if (currentPhase == Phase.OUTRO) {
            //NEED TO FINE-TUNE COORDINATES!

        }
        */

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

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateIntro implements IState {

        private Handler handler;

        public BattleStateIntro(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateIntro(Handler) constructor ****

        @Override
        public void updateInput() {

        }

        @Override
        public void tick() {
            System.out.println("BattleStateIntro.tick()");
            if (handler.getKeyManager().aButton) {
                System.out.println("BattleStateIntro.tick()... aButton");
                ///////////////////////////////
                StateManager.change("BattleStateMenu", null);
                ///////////////////////////////
            }
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                    handler.getGame().getHeight(), 2, 2, 2+159, 2+145, null);
        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateIntro inner-class ****

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateMenu implements IState {

        private Handler handler;
        private int xIndex, yIndex;

        public BattleStateMenu(Handler handler) {
            this.handler = handler;
            xIndex = 0;
            yIndex = 0;
        } // **** end BattleStateMenu(Handler) constructor ****

        @Override
        public void updateInput() {

        }

        @Override
        public void tick() {
            System.out.println("BattleStateMenu.tick()");
            //UP
            if (handler.getKeyManager().up) {
                System.out.println("BattleStateMenu.tick()... up");
                if (yIndex > 0) {
                    yIndex--;
                }
            }
            //DOWN
            else if (handler.getKeyManager().down) {
                System.out.println("BattleStateMenu.tick()... down");
                if (yIndex == 0) {
                    yIndex++;
                }
            }
            //LEFT
            else if (handler.getKeyManager().left) {
                System.out.println("BattleStateMenu.tick()... left");
                if (xIndex > 0) {
                    xIndex--;
                }
            }
            //RIGHT
            else if (handler.getKeyManager().right) {
                System.out.println("BattleStateMenu.tick()... right");
                if (xIndex == 0) {
                    xIndex++;
                }
            }
            //delete later
            else if (handler.getKeyManager().bButton) {
                System.out.println("BattleStateMenu.tick()... bButton");
                ///////////////////////////////
                StateManager.change("BattleStateOutro", null);
                ///////////////////////////////
            }
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                    handler.getGame().getHeight(), 161, 2, 161+159, 2+145, null);
        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateMenu inner-class ****

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateFight implements IState {

        private Handler handler;

        public BattleStateFight(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateFight(Handler) constructor ****

        @Override
        public void updateInput() {

        }

        @Override
        public void tick() {

        }

        @Override
        public void render(Graphics g) {

        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateFight inner-class ****

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateItem implements IState {

        private Handler handler;

        public BattleStateItem(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateItem(Handler) constructor ****

        @Override
        public void updateInput() {

        }

        @Override
        public void tick() {

        }

        @Override
        public void render(Graphics g) {

        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateItem inner-class ****

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateMonsterBeltList implements IState {

        private Handler handler;

        public BattleStateMonsterBeltList(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateMonsterBeltList(Handler) constructor ****

        @Override
        public void updateInput() {

        }

        @Override
        public void tick() {

        }

        @Override
        public void render(Graphics g) {

        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateMonsterBeltList inner-class ****

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateRun implements IState {

        private Handler handler;

        public BattleStateRun(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateRun(Handler) constructor ****

        @Override
        public void updateInput() {

        }

        @Override
        public void tick() {

        }

        @Override
        public void render(Graphics g) {

        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateRun inner-class ****

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateOutro implements IState {

        private Handler handler;

        public BattleStateOutro(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateOutro(Handler) constructor ****

        @Override
        public void updateInput() {

        }

        @Override
        public void tick() {
            System.out.println("BattleStateOutro.tick()");
            if (handler.getKeyManager().startButton) {
                System.out.println("BattleStateOutro.tick()... startButton");
                ///////////////////////////////
                StateManager.change("GameState", null);
                ///////////////////////////////
            }
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                    handler.getGame().getHeight(), 320, 2, 320+159, 2+145, null);
        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateOutro inner-class ****

} // **** end BattleState class ****