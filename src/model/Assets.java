package model;

import utils.ImageLoader;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage world, player;

    public static void init() {
        world = ImageLoader.loadImage("/pokemon-gsc-kanto.png");
        player = ImageLoader.loadImage("/butters_profchaos.jpg");
    }
}
