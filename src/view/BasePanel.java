package view;

import main.Handler;
import main.gfx.Assets;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel {

    private Handler handler;
    private int widthScreen, heightScreen;

    public BasePanel(Handler handler, int widthScreen, int heightScreen) {
        //@@@@@ SET DOUBLE-BUFFERED TO TRUE @@@@@
        super(true);
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        this.handler = handler;
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;

        setPreferredSize( new Dimension(widthScreen, heightScreen) );
        setMaximumSize( new Dimension(widthScreen, heightScreen) );
        setMinimumSize( new Dimension(widthScreen, heightScreen) );
        setFocusable(false);
    } // **** end BasePanel(Handler, int, int) constructor ****

    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);

        render(g);
    }
    /////////////////////////////////////////
    protected abstract void render(Graphics g);
    /////////////////////////////////////////

} // **** end BasePanel class ****