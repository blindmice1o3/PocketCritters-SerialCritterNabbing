package view;

import main.Handler;
import main.input.KeyManager;
import model.states.StateManager;

import javax.swing.*;
import java.awt.*;

public class Displayer {

    private Handler handler;
    private String title;
    private int width, height;

    private JFrame frame;
    private JPanel panel;

    public Displayer(Handler handler, String title, int width, int height) {
        this.handler = handler;
        this.title = title;
        this.width = width;
        this.height = height;

        initDisplayer();
    }

    private void initDisplayer() {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        /////////////////////////////////
        frame.addKeyListener( handler.getKeyManager() );
        /////////////////////////////////

        panel = new BasePanel();
        //frame.setContentPane(panel);
        frame.add(BorderLayout.CENTER, panel);
        frame.pack();
        frame.setVisible(true);
    }

    // GETTERS & SETTERS

    public JPanel getPanel() {
        return panel;
    }
    public void setPanel(JPanel panel) { this.panel = panel; }

    /////////////////////////////////////////////////////////////////////
    private class BasePanel extends JPanel {

        public BasePanel() {
            //@@@@@ SET DOUBLE-BUFFERED TO TRUE @@@@@
            super(true);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            setPreferredSize( new Dimension(width, height) );
            setMaximumSize( new Dimension(width, height) );
            setMinimumSize( new Dimension(width, height) );
            setFocusable(false);
        } // **** end BasePanel(Handler, int, int) constructor ****

        @Override
        public void paintComponent(Graphics g) {
            //super.paintComponent(g);

            handler.getStateManager().getCurrentState().render(g);
        }

    } // **** end BasePanel inner-class ****
    ////////////////////////////////////////////////////////////////////

} // **** end view.Displayer class ****