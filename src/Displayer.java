import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Displayer {

    private Handler handler;

    private JFrame frame;
    private JPanel panel;

    public Displayer(Handler handler, String title) {
        this.handler = handler;

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 540);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new MyPanel();
        panel.setFocusable(true);
        frame.setContentPane(panel);

        frame.setVisible(true);
    }

    // GETTERS & SETTERS

    public JPanel getPanel() {
        return panel;
    }

    class MyPanel extends JPanel implements KeyListener {

        public MyPanel() {
            addKeyListener(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(Assets.world, 0, 0, 320*2, 270*2,
                    960, 3184, 1279, 3455, null);
            g.setColor(Color.YELLOW);
            g.drawString("Pocket Critters - Serial Critter Nabbing!!!", 10, 10);

            handler.getGame().getPlayer().render(g);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                handler.getGame().getPlayer().setXFuture(-32);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                handler.getGame().getPlayer().setXFuture(32);
            } else if (e.getKeyCode() == KeyEvent.VK_W) {
                handler.getGame().getPlayer().setYFuture(-32);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                handler.getGame().getPlayer().setYFuture(32);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

    } // **** end MyPanel inner-class ****

} // **** end Displayer class ****