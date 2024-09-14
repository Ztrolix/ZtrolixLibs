package com.ztrolix.zlibs.gui;

import javax.swing.*;
import java.awt.*;

public class PreLaunchWindow {
    private static final JDialog frame = new JDialog();
    private static boolean disposed = false;

    static {
        frame.setTitle("Loading Minecraft...");
        frame.setResizable(false);
        frame.setSize(400, 55);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setAlwaysOnTop(true);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setForeground(Color.getColor("16711935", Color.BLUE));
        progressBar.setStringPainted(true);
        progressBar.setString("Launching...");
        frame.add(progressBar, BorderLayout.CENTER);
    }

    public static void display() {
        if (disposed) throw new IllegalStateException("Pre-launch window has been disposed!");
        frame.setVisible(true);
    }

    public static void remove() {
        if (disposed) return;
        frame.setVisible(false);
        frame.dispose();
        disposed = true;
    }

    public static void main(String[] args) {
        display();
    }
}