package main;

import utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Sandbox {

    public static BufferedImage renderedImage = ImageLoader.loadImage("/throwAwayImage.png");

    public static void main(String[] args) {
        JFrame frame = new JFrame("sandbox tester");
        MyPanel panel = new MyPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 480);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    static class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(renderedImage, 0, 0, null);
        }
    }

} // **** end Sandbox class ****