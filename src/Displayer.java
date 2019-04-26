import javax.swing.*;
import java.awt.*;

public class Displayer {
    private JFrame frame;
    private JPanel panel;

    public Displayer(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        panel = new MyPanel();
        frame.setContentPane(panel);

        frame.setVisible(true);
    }
}

class MyPanel extends JPanel {

    public MyPanel() {

    } // **** end MyPanel() constructor ****

    @Override
    public void paintComponent(Graphics g) {
        g.drawString("Pocket Critters - Serial Critter Nabbing!!!", 100, 50);
    }

} // **** end MyPanel inner-class ****