package view;

import main.Handler;
import main.input.KeyManager;
import model.states.StateManager;

import javax.swing.*;
import java.awt.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class Displayer {

    private Handler handler;
    private KeyManager keyManager;
    private String title;
    private int width, height;

    private JFrame frame;
    private JPanel currentPanel;

    public Displayer(Handler handler, KeyManager keyManager, String title, int width, int height) {
        this.handler = handler;
        this.keyManager = keyManager;
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
        frame.addKeyListener(keyManager);
        /////////////////////////////////

        currentPanel = StateManager.getCurrentState().getPanel();
        frame.add(BorderLayout.CENTER, currentPanel);
        frame.setVisible(true);

        //frame.setContentPane(currentPanel);

        //frame.add(currentPanel);
        //frame.pack();
    }

    // GETTERS & SETTERS

    public JPanel getCurrentPanel() {
        return currentPanel;
    }
    public void setCurrentPanel(JPanel currentPanel) { this.currentPanel = currentPanel; }

} // **** end view.Displayer class ****