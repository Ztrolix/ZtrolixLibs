import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class ZtrolixLibsDownloader {

    // URLs corresponding to the dropdown options
    private static final String FABRIC_QUILT_URL = "https://example.com/fabric_quilt";
    private static final String SPIGOT_URL = "https://example.com/spigot";
    private static final String NEOFORGE_URL = "https://example.com/neoforge";
    private static final String FORGE_URL = "https://example.com/forge";

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("ZLibs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Create the dropdown
        String[] options = {"Fabric/Quilt", "Spigot", "NeoForge", "Forge"};
        JComboBox<String> dropdown = new JComboBox<>(options);

        // Create the download button
        JButton downloadButton = new JButton("Download & Install");

        // Add components to the frame
        frame.add(new JLabel("Ztrolix Libs", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(dropdown, BorderLayout.CENTER);
        frame.add(downloadButton, BorderLayout.SOUTH);

        // Action listener for the download button
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropdown.getSelectedItem();
                String downloadUrl = getDownloadUrl(selectedOption);

                // Open file chooser to select the download directory
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

        // Display the frame
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
                fileOutputStream.write(dataBuffer, bytesRead, bytesRead);
            }
        }
    }
}