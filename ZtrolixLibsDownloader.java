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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ZtrolixLibsDownloader {
    private static final String FABRIC_API_BASE_URL = "https://cdn.modrinth.com/data/P7dR8mSH/versions/";
    private static final String QUILT_API_BASE_URL = "https://cdn.modrinth.com/data/qvIfYCYJ/versions/";

    private static final String FABRIC_QUILT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/fabric.jar";
    private static final String SPIGOT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/spigot.jar";
    private static final String NEOFORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/neo.jar";
    private static final String FORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/forge.jar";

    public static void main(String[] args) {
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

        JFrame frame = new JFrame("ZLibs Installer");
        frame.setSize(400, 250);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 20, 20));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCustomIcon(frame, "https://raw.githubusercontent.com/ZtrolixGit/ZtrolixLibs/main/icon.png");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(50, 50, 50));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Ztrolix Libs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(230, 230, 230));
        titleLabel.setPreferredSize(new Dimension(frame.getWidth(), 40));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);
        JButton minimizeButton = new JButton("_");
        minimizeButton.addActionListener(e -> frame.setState(Frame.ICONIFIED));
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> System.exit(0));
        controlPanel.add(minimizeButton);
        controlPanel.add(closeButton);
        topPanel.add(controlPanel, BorderLayout.EAST);
        closeButton.setBackground(new Color(169, 46, 34));
        closeButton.setForeground(new Color(255, 255, 255));
        minimizeButton.setBackground(new Color(191, 98, 4));
        minimizeButton.setForeground(new Color(255, 255, 255));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        String[] options = {"Select Loader", "Fabric", "Quilt", "Spigot", "NeoForge", "Forge"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setPreferredSize(new Dimension(370, 30));
        
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dropdownPanel.setOpaque(false);
        dropdownPanel.add(dropdown);
        centerPanel.add(dropdownPanel, BorderLayout.CENTER);

        JComboBox<String> versionDropdown = new JComboBox<>();
        versionDropdown.setPreferredSize(new Dimension(370, 30));

        JPanel versionDropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        versionDropdownPanel.setOpaque(false);
        versionDropdownPanel.add(versionDropdown);
        centerPanel.add(versionDropdownPanel, BorderLayout.SOUTH);

        dropdown.addActionListener(e -> updateVersionDropdown((String) dropdown.getSelectedItem(), versionDropdown));
        dropdown.addActionListener(e -> updateLoaderDropdown(dropdown));

        JButton downloadButton = new JButton("Download and Install");
        downloadButton.setBackground(new Color(104, 93, 156));
        downloadButton.setForeground(new Color(255, 255, 255));
        downloadButton.setPreferredSize(new Dimension(200, 30));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLoader = (String) dropdown.getSelectedItem();
                String selectedVersion = (String) versionDropdown.getSelectedItem();
                String downloadUrl = getDownloadUrl(selectedLoader, selectedVersion);
                String modUrl = getModUrl(selectedLoader, selectedVersion);
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    Path downloadDirectory = fileChooser.getSelectedFile().toPath();
                    String folderName = selectedLoader.equals("Spigot") ? "plugins" : "mods";
                    Path folderPath = downloadDirectory.resolve(folderName);

                    try {
                        Files.createDirectories(folderPath);
                        progressBar.setVisible(true);
                        new Thread(() -> {
                            try {
                                downloadFile(downloadUrl, folderPath, "ZtrolixLibs-" + selectedLoader + "-" + selectedVersion, progressBar);
                                if (modUrl != null) {
                                    downloadLibraryFile(modUrl, folderPath, "ZtrolixLibs-API-" + selectedLoader + "-" + selectedVersion, progressBar);
                                }
                                JOptionPane.showMessageDialog(frame, "Download completed!");
                                progressBar.setVisible(false);
                                progressBar.setValue(0);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(frame, "Download failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                progressBar.setVisible(false);
                                progressBar.setValue(0);
                            }
                        }).start();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Failed to create directory: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(centerPanel, BorderLayout.NORTH);
        bottomPanel.add(downloadButton, BorderLayout.CENTER);
        bottomPanel.add(progressBar, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String getDownloadUrl(String loader, String version) {
        switch (loader) {
            case "Fabric":
            case "Quilt":
                return FABRIC_QUILT_URL;
            case "Spigot":
                return SPIGOT_URL;
            case "NeoForge":
                return NEOFORGE_URL;
            case "Forge":
                return FORGE_URL;
            default:
                throw new IllegalArgumentException("Unknown loader: " + loader);
        }
    }

    private static String getModUrl(String loader, String version) {
        if (loader.equals("Quilt") || loader.equals("Fabric")) {
            return getModrinthUrl(loader, version);
        }
        return null;
    }

    private static String getModrinthUrl(String loader, String version) {
        switch (loader) {
            case "Fabric":
                switch (version) {
                    case "1.20":
                        return FABRIC_API_BASE_URL + "n2c5lxAo/fabric-api-0.83.0%2B1.20.jar";
                    case "1.20.1":
                        return FABRIC_API_BASE_URL + "P7uGFii0/fabric-api-0.92.2%2B1.20.1.jar";
                    case "1.20.2":
                        return FABRIC_API_BASE_URL + "8GVp7wDk/fabric-api-0.91.6%2B1.20.2.jar";
                    case "1.20.3":
                        return FABRIC_API_BASE_URL + "Yolngp3s/fabric-api-0.91.1%2B1.20.3.jar";
                    case "1.20.4":
                        return FABRIC_API_BASE_URL + "tAwdMmKY/fabric-api-0.97.1%2B1.20.4.jar";
                    case "1.20.5 EXPERIMENTAL":
                        return FABRIC_API_BASE_URL + "GCdY4I8I/fabric-api-0.97.8%2B1.20.5.jar";
                    case "1.20.6 EXPERIMENTAL":
                        return FABRIC_API_BASE_URL + "GT0R5Mz7/fabric-api-0.100.4%2B1.20.6.jar";
                    default:
                        throw new IllegalArgumentException("Unknown version: " + version);
                }
            case "Quilt":
                switch (version) {
                    case "1.20":
                        return QUILT_API_BASE_URL + "vTQynnGn/qfapi-7.2.2_qsl-6.1.2_fapi-0.88.1_mc-1.20.1.jar";
                    case "1.20.1":
                        return QUILT_API_BASE_URL + "Gydw2vxY/qfapi-7.5.0_qsl-6.1.2_fapi-0.92.2_mc-1.20.1.jar";
                    case "1.20.2":
                        return QUILT_API_BASE_URL + "zHVlrS0A/quilted-fabric-api-8.0.0-alpha.6%2B0.91.6-1.20.2.jar";
                    case "1.20.4":
                        return QUILT_API_BASE_URL + "AljqyvST/quilted-fabric-api-9.0.0-alpha.8%2B0.97.0-1.20.4.jar";
                    default:
                        throw new IllegalArgumentException("Unknown version: " + version);
                }
            default:
                throw new IllegalArgumentException("Unknown loader: " + loader);
        }
    }

    private static void downloadFile(String url, Path downloadDirectory, String fileName, JProgressBar progressBar) throws IOException {
        URL downloadUrl = new URL(url);
        try (BufferedInputStream in = new BufferedInputStream(downloadUrl.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(downloadDirectory.resolve(fileName + ".jar").toFile())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int totalBytesRead = 0;
            int fileSize = downloadUrl.openConnection().getContentLength();

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                int progress = (int) ((totalBytesRead / (double) fileSize) * 100);
                SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
            }
        }
    }

    private static void downloadLibraryFile(String url, Path downloadDirectory, String fileName, JProgressBar progressBar) throws IOException {
        URL downloadUrl = new URL(url);
        try (BufferedInputStream in = new BufferedInputStream(downloadUrl.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(downloadDirectory.resolve(fileName + ".jar").toFile())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int totalBytesRead = 0;
            int fileSize = downloadUrl.openConnection().getContentLength();

            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                int progress = (int) ((totalBytesRead / (double) fileSize) * 100);
                SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
            }
        }
    }

    private static void updateVersionDropdown(String loader, JComboBox<String> versionDropdown) {
        versionDropdown.removeAllItems();
        
        switch (loader) {
            case "Fabric":
                versionDropdown.addItem("1.20");
                versionDropdown.addItem("1.20.1");
                versionDropdown.addItem("1.20.2");
                versionDropdown.addItem("1.20.3");
                versionDropdown.addItem("1.20.4");
                versionDropdown.addItem("1.20.5 EXPERIMENTAL");
                versionDropdown.addItem("1.20.6 EXPERIMENTAL");
                break;
            case "Spigot":
                versionDropdown.addItem("1.20");
                versionDropdown.addItem("1.20.1");
                versionDropdown.addItem("1.20.2");
                versionDropdown.addItem("1.20.3");
                versionDropdown.addItem("1.20.4");
                break;
            case "Quilt":
                versionDropdown.addItem("1.20");
                versionDropdown.addItem("1.20.1");
                versionDropdown.addItem("1.20.2");
                versionDropdown.addItem("1.20.4");
                break;
            case "NeoForge":
                versionDropdown.addItem("1.20.4");
                break;
            case "Forge":
                versionDropdown.addItem("1.20.1");
                break;
        }
    }

    private static void updateLoaderDropdown(JComboBox<String> dropdown) {
        dropdown.removeItem("Select Loader");
    }

    private static void setCustomIcon(JFrame frame, String iconUrl) {
        try {
            // Download the icon image
            URL url = new URL(iconUrl);
            BufferedImage image = ImageIO.read(url);
            if (image != null) {
                // Set the application icon for the JFrame
                frame.setIconImage(image);
            } else {
                System.err.println("Failed to load icon image from URL: " + iconUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}