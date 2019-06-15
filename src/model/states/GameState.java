package model.states;

import main.Handler;
import main.gfx.Assets;
import view.BasePanel;

import javax.swing.*;
import java.awt.*;

public class GameState implements IState {

    private Handler handler;
    private int widthScreen, heightScreen;

    private JPanel panel;

    public GameState(Handler handler, int widthScreen, int heightScreen) {
        this.handler = handler;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;

        panel = new GamePanel(handler, widthScreen, heightScreen);
    } // **** end GameState(Handler, int, int) constructor ****

    @Override
    public void tick() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void enter(Object[] args) {

    }

    @Override
    public void exit() {

    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    ////////////////////////////////////////////////////////////////////////////////
    private class GamePanel extends BasePanel {

        public GamePanel(Handler handler, int widthScreen, int heightScreen) {
            super(handler, widthScreen, heightScreen);
        } // **** end GamePanel(Handler, int, int) constructor ****

        @Override
        protected void render(Graphics g) {
            renderBackground(g);
            renderEntities(g);
        }
        private void renderBackground(Graphics g) {
            g.drawImage(Assets.world, 0, 0, widthScreen, heightScreen,
                    (int)(handler.getGame().getGameCamera().getxOffset0()),
                    (int)(handler.getGame().getGameCamera().getyOffset0()),
                    (int)(handler.getGame().getGameCamera().getxOffset1()),
                    (int)(handler.getGame().getGameCamera().getyOffset1()),
                    null);
            //g.setColor(Color.YELLOW);
            //g.drawString("Pocket Critters - Serial Critter Nabbing!!!", 10, 10);
        }

        private void renderEntities(Graphics g) {
            handler.getGame().getPlayer().render(g);
            handler.getGame().getJames().render(g);
            handler.getGame().getJessie().render(g);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////

} // **** end GameState class ****