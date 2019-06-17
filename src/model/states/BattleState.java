package model.states;

import main.Handler;
import main.gfx.Assets;
import model.entities.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BattleState implements IState {

    private Handler handler;
    private Player player;

    public BattleState(Handler handler) {
        this.handler = handler;

        ////////////////////
        initStateManager();
        ////////////////////
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
    public void tick() {
        ///////////////////////////////
        StateManager.change("BattleStateIntro", null);
        ///////////////////////////////
    }

    @Override
    public void render(Graphics g) {

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
        public void tick() {
            System.out.println("BattleStateIntro.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateIntro.tick()... up");
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateIntro.tick()... down");
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateIntro.tick()... left");
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateIntro.tick()... right");
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateIntro.tick()... aButton");

                ///////////////////////////////
                StateManager.change("BattleStateMenu", null);
                ///////////////////////////////
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateIntro.tick()... bButton");
            }
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                    handler.getGame().getHeight(), 2, 2, 2+159, 2+145, null);

            g.drawImage(Assets.fontHashMap.get("C"), 200, 410, 10*4, 10*4, null);
            g.drawImage(Assets.fontHashMap.get("a"), 240, 420, 10*4, 10*4, null);
            g.drawImage(Assets.fontHashMap.get("n"), 280, 420, 10*4, 10*4, null);
            g.drawImage(Assets.fontHashMap.get("n"), 320, 420, 10*4, 10*4, null);
            g.drawImage(Assets.fontHashMap.get("a"), 360, 420, 10*4, 10*4, null);
            g.drawImage(Assets.fontHashMap.get("b"), 400, 420, 10*4, 10*4, null);
            g.drawImage(Assets.fontHashMap.get("i"), 440, 420, 10*4, 10*4, null);
            g.drawImage(Assets.fontHashMap.get("s"), 480, 420, 10*4, 10*4, null);

            g.drawImage(Assets.monsterCapsuleIcon, 130*4, 76*4, 7*4, 7*4, null);
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
        private String[][] menuMatrix;

        public BattleStateMenu(Handler handler) {
            this.handler = handler;
            xIndex = 0;
            yIndex = 0;
            menuMatrix = new String[2][2];
            menuMatrix[0][0] = "BattleStateFight";
            menuMatrix[0][1] = "BattleStateMonsterBeltList";
            menuMatrix[1][0] = "BattleStateItem";
            menuMatrix[1][1] = "BattleStateRun";
        } // **** end BattleStateMenu(Handler) constructor ****

        @Override
        public void tick() {
            System.out.println("BattleStateMenu.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateMenu.tick()... up");
                if (yIndex == 1) {
                    yIndex--;
                }
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateMenu.tick()... down");
                if (yIndex == 0) {
                    yIndex++;
                }
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateMenu.tick()... left");
                if (xIndex == 1) {
                    xIndex--;
                }
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateMenu.tick()... right");
                if (xIndex == 0) {
                    xIndex++;
                }
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateMenu.tick()... aButton");

                ////////////////////////////////////////////////////////////
                StateManager.change(menuMatrix[yIndex][xIndex], null);
                ////////////////////////////////////////////////////////////
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateMenu.tick()... bButton");
            }
        }

        public void resetIndexForBattleStateMenu() {
            xIndex = 0;
            yIndex = 0;
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
        public void tick() {
            System.out.println("BattleStateFight.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateFight.tick()... up");
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateFight.tick()... down");
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateFight.tick()... left");
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateFight.tick()... right");
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateFight.tick()... aButton");
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateFight.tick()... bButton");

                ///////////////////////////////
                StateManager.change("BattleStateMenu", null);
                ///////////////////////////////
            }
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                    handler.getGame().getHeight(), 161, 146, 161+159, 146+145, null);
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
        public void tick() {
            System.out.println("BattleStateItem.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateItem.tick()... up");
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateItem.tick()... down");
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateItem.tick()... left");
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateItem.tick()... right");
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateItem.tick()... aButton");
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateItem.tick()... bButton");

                ///////////////////////////////
                StateManager.change("BattleStateMenu", null);
                ///////////////////////////////
            }
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                    handler.getGame().getHeight(), 2, 146, 2+159, 146+145, null);
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
        public void tick() {
            System.out.println("BattleStateMonsterBeltList.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateMonsterBeltList.tick()... up");
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateMonsterBeltList.tick()... down");
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateMonsterBeltList.tick()... left");
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateMonsterBeltList.tick()... right");
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateMonsterBeltList.tick()... aButton");
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateMonsterBeltList.tick()... bButton");

                ///////////////////////////////
                StateManager.change("BattleStateMenu", null);
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

    } // **** end BattleStateMonsterBeltList inner-class ****

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private class BattleStateRun implements IState {

        private Handler handler;

        public BattleStateRun(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateRun(Handler) constructor ****

        @Override
        public void tick() {
            System.out.println("BattleStateRun.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateRun.tick()... up");
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateRun.tick()... down");
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateRun.tick()... left");
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateRun.tick()... right");
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateRun.tick()... aButton");

                ///////////////////////////////
                StateManager.change("BattleStateOutro", null);
                ///////////////////////////////
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateRun.tick()... bButton");

                ///////////////////////////////
                StateManager.change("BattleStateMenu", null);
                ///////////////////////////////
            }
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
        public void tick() {
            System.out.println("BattleStateOutro.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateOutro.tick()... up");
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateOutro.tick()... down");
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateOutro.tick()... left");
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateOutro.tick()... right");
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateOutro.tick()... aButton");

                ///////////////////////////////
                ((BattleStateMenu)StateManager.get("BattleStateMenu")).resetIndexForBattleStateMenu();
                StateManager.change("GameState", null);
                ///////////////////////////////
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateOutro.tick()... bButton");
            }
        }

        @Override
        public void render(Graphics g) {
            g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                    handler.getGame().getHeight(), 320, 146, 320+159, 146+145, null);
        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateOutro inner-class ****

} // **** end BattleState class ****