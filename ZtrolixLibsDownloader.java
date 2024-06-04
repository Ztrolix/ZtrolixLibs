import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class ZtrolixLibsDownloader {

    private static final String FABRIC_QUILT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/releases/latest/download/fabric.jar";
    private static final String SPIGOT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/releases/latest/download/spigot.jar";
    private static final String NEOFORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/releases/latest/download/neo.jar";
    private static final String FORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/releases/latest/download/forge.jar";

    public static void main(String[] args) {
        // Set the look and feel to Nimbus with flat colors
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.put("control", new Color(50, 50, 50));
            UIManager.put("info", new Color(50, 50, 50));
            UIManager.put("nimbusBase", new Color(35, 35, 35));
            UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", new Color(115, 164, 209));
            UIManager.put("nimbusGreen", new Color(176, 179, 50));
            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
            UIManager.put("nimbusLightBackground", new Color(50, 50, 50));
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(169, 46, 34));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
            UIManager.put("text", new Color(230, 230, 230));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Ztrolix Libs!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 30, 30));
        frame.setLocationRelativeTo(null); // Open in the center of the screen

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(50, 50, 50));

        JLabel titleLabel = new JLabel("Ztrolix Libs!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));

        String[] options = {"Fabric/Quilt", "Spigot", "NeoForge", "Forge"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setBackground(new Color(60, 60, 60));
        dropdown.setForeground(new Color(230, 230, 230));

        JButton downloadButton = new JButton("Download");
        downloadButton.setBackground(new Color(104, 93, 156));
        downloadButton.setForeground(new Color(255, 255, 255));

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdown.getSelectedItem();
                String downloadUrl = getDownloadUrl(selectedOption);

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    Path downloadDirectory = fileChooser.getSelectedFile().toPath();
                    try {
                        downloadFile(downloadUrl, downloadDirectory);
                        JOptionPane.showMessageDialog(frame, "Download completed!");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Download failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(dropdown, BorderLayout.CENTER);
        panel.add(downloadButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static String getDownloadUrl(String option) {
        switch (option) {
            case "Fabric/Quilt":
                return FABRIC_QUILT_URL;
            case "Spigot":
                return SPIGOT_URL;
            case "NeoForge":
                return NEOFORGE_URL;
            case "Forge":
                return FORGE_URL;
            default:
                throw new IllegalArgumentException("Unknown option: " + option);
        }
    }

    private static void downloadFile(String url, Path downloadDirectory) throws IOException {
        URL downloadUrl = new URL(url);
        try (BufferedInputStream in = new BufferedInputStream(downloadUrl.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(downloadDirectory.resolve("downloaded_file").toFile())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }
}