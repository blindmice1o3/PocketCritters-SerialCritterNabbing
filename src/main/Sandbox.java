package main;

import main.gfx.Assets;
import main.utils.ImageLoader;
import main.utils.TileSpriteToRGBConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class Sandbox {
    // Team Rocket w Jessie and James.
    public static BufferedImage outputImage = ImageLoader.loadImage("/throwAwayImage.png");
    // Beginning of hard-coded RGB world map.
    public static RenderedImage overwriter = ImageLoader.loadImage("/world map (rgb).png");

    public static void main(String[] args) {
        //TileSpriteToRGBConverter tester = new TileSpriteToRGBConverter();
        //tester.testConsoleOutput( tester.translateTileSpriteToRGBImage() );

        int counter = 1;
        String formattedString = String.format("%05d", counter);
        System.out.println(formattedString);

        /*
        //Hopefully creating a NON-null File object that will be overwritten from Team Rocket to hard-coded RGB world map.
        File file = new File("/res/throwAwayImage.png");
        */

        /*
        try {
            file = File.createTempFile("outputter",".png");
            System.out.println("Creating a new File object probably successful.");
        } catch (IOException e) {
            System.out.println("Catch-clause of creating a File object.");
            e.printStackTrace();
        }
        */

        /*
        try {
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            if (ImageIO.write(overwriter, ".png", file)) {
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                System.out.println("Woohoo! ImageIO.write() probably successfully overwritten throwAwayImage.png.");
            } else {
                System.out.println("Awww, looks like ImageIO.write() did NOT work as intended.");
            }
        } catch (IOException e) {
            System.out.println("Inside the catch-clause while attempting ImageIO.write().");
            e.printStackTrace();
        }
        */


        /*
        JFrame frame = new JFrame("sandbox tester");
        BasePanel panel = new BasePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 480);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(panel);
        frame.setVisible(true);
        */
    }

    static class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(outputImage, 0, 0, null);
            //g.drawImage(overwriter, 0, 0, null);
        }
    }

} // **** end Sandbox class ****
















































// Revelation: my mom will be the number one woman in my life, sorry ladies you'll have to settle for number 2... *cross
// out ladies and replace with lady* *find a way to indicate a message: my momma taught me better than that or something
// about teaching the right priority*