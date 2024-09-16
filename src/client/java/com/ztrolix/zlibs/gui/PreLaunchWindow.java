package com.ztrolix.zlibs.gui;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class PreLaunchWindow {
    private static final JDialog frame = new JDialog();
    private static boolean disposed = false;

    static {
        try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf.");
        }

        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(400, 45);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 20, 20));
        frame.setBackground(new Color(0, 0, 0, 0));

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(60, 63, 65));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setOpaque(false);

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(60, 63, 65));

        JLabel titleLabel = new JLabel("Loading Minecraft...");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        titleBar.add(titleLabel, BorderLayout.WEST);

        JButton closeButton = new JButton("-");
        closeButton.setBackground(new Color(60, 63, 65));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setOpaque(true);
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                disposed = true;
            }
        });
        titleBar.add(closeButton, BorderLayout.EAST);
        mainPanel.add(titleBar, BorderLayout.NORTH);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString("Launching...");
        progressBar.setBackground(new Color(60, 63, 65));
        progressBar.setForeground(new Color(3, 169, 244));
        mainPanel.add(progressBar, BorderLayout.CENTER);

        frame.add(mainPanel, BorderLayout.CENTER);
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