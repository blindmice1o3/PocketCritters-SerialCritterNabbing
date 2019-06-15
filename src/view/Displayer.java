package view;

import main.Handler;
import main.gfx.Assets;

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

        panel = new MyPanel(width, height);
        panel.setPreferredSize( new Dimension(width, height) );
        panel.setMaximumSize( new Dimension(width, height) );
        panel.setMinimumSize( new Dimension(width, height) );
        panel.setFocusable(false);

        frame.setContentPane(panel);
        //frame.add(panel);
        //frame.pack();
        frame.setVisible(true);
    }

    // GETTERS & SETTERS

    public JFrame getFrame() { return frame; }
    public JPanel getPanel() {
        return panel;
    }

    class MyPanel extends JPanel {

        int widthScreen, heightScreen;

        public MyPanel(int widthScreen, int heightScreen) {
            super(true);

            this.widthScreen = widthScreen;
            this.heightScreen = heightScreen;
        } // **** end MyPanel(int, int) constructor ****

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(Assets.world, 0, 0, widthScreen, heightScreen,
                    (int)(handler.getGame().getGameCamera().getxOffset0()),
                    (int)(handler.getGame().getGameCamera().getyOffset0()),
                    (int)(handler.getGame().getGameCamera().getxOffset1()),
                    (int)(handler.getGame().getGameCamera().getyOffset1()),
                    null);
            //g.setColor(Color.YELLOW);
            //g.drawString("Pocket Critters - Serial Critter Nabbing!!!", 10, 10);

            handler.getGame().getPlayer().render(g);
            handler.getGame().getJames().render(g);
            handler.getGame().getJessie().render(g);
        }

        /*
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                handler.getGame().getGameCamera().setxOffset(
                        handler.getGame().getGameCamera().getxOffset()+32
                );
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                handler.getGame().getGameCamera().setxOffset(
                        handler.getGame().getGameCamera().getxOffset()-32
                );
            } else if (e.getKeyCode() == KeyEvent.VK_W) {
                handler.getGame().getGameCamera().setyOffset(
                        handler.getGame().getGameCamera().getyOffset()+32
                );
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                handler.getGame().getGameCamera().setyOffset(
                        handler.getGame().getGameCamera().getyOffset()-32
                );
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }
        */

    } // **** end MyPanel inner-class ****

} // **** end view.Displayer class ****