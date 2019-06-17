package model.states;

import main.Handler;
import main.gfx.Assets;
import model.entities.nabbers.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

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
        StateManager.add("BattleStateCritterBeltList", new BattleStateCritterBeltList(handler));
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

            //First monsterCapsuleIcon (index == 0).
            g.drawImage(Assets.monsterCapsuleIcon, 90*4, 76*4, 7*4, 7*4, null);
            //Second monsterCapsuleIcon (index == 1).
            g.drawImage(Assets.monsterCapsuleIcon, 98*4, 76*4, 7*4, 7*4, null);
            //Third monsterCapsuleIcon (index == 2).
            g.drawImage(Assets.monsterCapsuleIcon, 106*4, 76*4, 7*4, 7*4, null);
            //Fourth monsterCapsuleIcon (index == 3).
            //g.drawImage(Assets.monsterCapsuleIcon, 114*4, 76*4, 7*4, 7*4, null);
            //Fifth monsterCapsuleIcon (index == 4).
            //g.drawImage(Assets.monsterCapsuleIcon, 122*4, 76*4, 7*4, 7*4, null);
            //Sixth monsterCapsuleIcon (index == 5).
            //g.drawImage(Assets.monsterCapsuleIcon, 130*4, 76*4, 7*4, 7*4, null);
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
            menuMatrix[0][1] = "BattleStateCritterBeltList";
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

            if (menuMatrix[yIndex][xIndex].equals("BattleStateFight")) {
                g.drawImage(Assets.cursor, 291, 423, 7 * 4, 7 * 4, null);
            } else if (menuMatrix[yIndex][xIndex].equals("BattleStateCritterBeltList")) {
                g.drawImage(Assets.cursor, 484, 423, 7 * 4, 7 * 4, null);
            } else if (menuMatrix[yIndex][xIndex].equals("BattleStateItem")) {
                g.drawImage(Assets.cursor, 291, 484, 7 * 4, 7 * 4, null);
            } else if (menuMatrix[yIndex][xIndex].equals("BattleStateRun")) {
                g.drawImage(Assets.cursor, 484, 484, 7 * 4, 7 * 4, null);
            }
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

        //while-loop and track whose turn it is.
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
        private int index = 0;

        public BattleStateItem(Handler handler) {
            this.handler = handler;
        } // **** end BattleStateItem(Handler) constructor ****

        @Override
        public void tick() {
            System.out.println("BattleStateItem.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateItem.tick()... up");

                index--;

                if (index < 0) {
                    index = (player.getInventory().size()-1);
                }
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateItem.tick()... down");

                index++;

                if (index >= player.getInventory().size()) {
                    index = 0;
                }
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

                System.out.println( "Item selected for use: " + player.getInventory().get(index) );
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

    private class BattleStateCritterBeltList implements IState {

        private Handler handler;
        private int index;

        public BattleStateCritterBeltList(Handler handler) {
            this.handler = handler;
            index = 0;
        } // **** end BattleStateCritterBeltList(Handler) constructor ****

        @Override
        public void tick() {
            System.out.println("BattleStateCritterBeltList.tick()");

            //UP
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) {
                System.out.println("BattleStateCritterBeltList.tick()... up");

                index--;

                if (index < 0) {
                    index = (player.getCritterBeltList().length-1);
                }
            }
            //DOWN
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) {
                System.out.println("BattleStateCritterBeltList.tick()... down");

                index++;

                if (index >= player.getCritterBeltList().length) {
                    index = 0;
                }
            }
            //LEFT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) {
                System.out.println("BattleStateCritterBeltList.tick()... left");
            }
            //RIGHT
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) {
                System.out.println("BattleStateCritterBeltList.tick()... right");
            }
            //aButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_COMMA)) {
                System.out.println("BattleStateCritterBeltList.tick()... aButton");

                System.out.println( "Critter selected: " + player.getCritterBeltList()[index] );
            }
            //bButton
            else if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_PERIOD)) {
                System.out.println("BattleStateCritterBeltList.tick()... bButton");

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

    } // **** end BattleStateCritterBeltList inner-class ****

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

        int xCol = 0;
        int yRow = 0;
        Random rand = new Random();
        int counter = 0;
        int counterTarget = 60;
        @Override
        public void render(Graphics g) {
            //@@@@@ImageLoader.cropSpriteFromSpriteSheet(int, int, int, int, int, int, BufferedImage) tester@@@@@
            counter++;

            if (counter == counterTarget) {
                g.clearRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());

                for (int y = 0; y < 13; y++) {
                    for (int x = 0; x < 12; x++) {
                        g.drawImage(Assets.crittersBufferedImageNestedArray[y][x],
                                (x * 56) + (x * 1) + 1, (y * 56) + (y * 1) + 1, null);
                    }
                }

                xCol++;
                for (int y = 0; y < 13; y++) {
                    g.drawImage(Assets.crittersBufferedImageNestedArray[8][7],
                            (xCol * 56) + (xCol * 1) + 1, (y * 56) + (y * 1) + 1, null);
                }
                if (xCol >= 12) {
                    xCol = 0;
                }

                yRow++;
                for (int x = 0; x < 12; x++) {
                    g.drawImage(Assets.crittersBufferedImageNestedArray[8][7],
                            (x * 56) + (x * 1) + 1, (yRow * 56) + (yRow * 1) + 1, null);
                }
                if (yRow >= 13) {
                    yRow = 0;
                }

                counter = 0;
            }
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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

        int counter = 0;
        int counterTarget = 60;
        int colIndex = 0;
        int xRand = 0;
        int yRand = 0;
        Random rand = new Random();
        @Override
        public void render(Graphics g) {
            counter++;
            if (counter == counterTarget) {
                g.drawImage(Assets.battleStateSpriteSheet, 0, 0, handler.getGame().getWidth(),
                        handler.getGame().getHeight(), 320, 146, 320 + 159, 146 + 145, null);

                //@@@@@@@@@@@@@@TESTER for Assets.nabbersBufferedImageNestedArray@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                for (int y = 0; y < 6; y++) {
                    for (int x = 0; x < 8; x++) {
                        g.drawImage(Assets.nabbersBufferedImageNestedArray[y][x],
                                (x * 68) + (x * 1) + 1, (y * 64) + (y * 2) + 2, null);
                    }
                }

                for (int y = 0; y < 6; y++) {
                    g.drawImage(Assets.nabbersBufferedImageNestedArray[yRand][xRand],
                            (colIndex * 68) + (colIndex * 1) + (1), (y * 64) + (y * 2) + (2), null);
                }
                colIndex++;
                if (colIndex >= 8) {
                    colIndex = 0;
                    xRand = rand.nextInt(8);
                    yRand = rand.nextInt(6);
                }

                counter = 0;
            }


            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        }

        @Override
        public void enter(Object[] args) {

        }

        @Override
        public void exit() {

        }

    } // **** end BattleStateOutro inner-class ****

} // **** end BattleState class ****