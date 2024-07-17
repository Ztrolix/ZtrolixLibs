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

    private static final String FABRIC_QUILT_21_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/1.21/fabric.jar";
    private static final String FABRIC_QUILT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/1.20/fabric.jar";
    private static final String SPIGOT_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/1.20/spigot.jar";
    private static final String NEOFORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/1.20/neo.jar";
    private static final String FORGE_URL = "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/api/1.20/forge.jar";

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

        JFrame frame = new JFrame("Ztrolix Libs Downloader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        frame.setContentPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Ztrolix Libs Downloader");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JComboBox<String> loaderDropdown = new JComboBox<>(new String[]{"Fabric", "Quilt", "Spigot", "NeoForge", "Forge"});
        loaderDropdown.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(loaderDropdown);
        loaderDropdown.setPreferredSize(new Dimension(370, 30));

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JComboBox<String> versionDropdown = new JComboBox<>();
        versionDropdown.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(versionDropdown);
        versionDropdown.setPreferredSize(new Dimension(370, 30));

        JButton downloadButton = new JButton("Download");
        downloadButton.setFont(new Font("Arial", Font.BOLD, 18));
        downloadButton.setBackground(new Color(70, 70, 70));
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setFocusPainted(false);
        downloadButton.setBorder(new EmptyBorder(10, 25, 10, 25));
        panel.add(downloadButton);

        loaderDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                versionDropdown.removeAllItems();
                String loader = (String) loaderDropdown.getSelectedItem();
                switch (loader) {
                    case "Fabric":
                    case "Quilt":
                        versionDropdown.addItem("1.20");
                        versionDropdown.addItem("1.20.1");
                        versionDropdown.addItem("1.20.2");
                        versionDropdown.addItem("1.20.3");
                        versionDropdown.addItem("1.20.4");
                        versionDropdown.addItem("1.20.5 EXPERIMENTAL");
                        versionDropdown.addItem("1.20.6 EXPERIMENTAL");
                        versionDropdown.addItem("1.21 EXPERIMENTAL");
                        break;
                    case "Spigot":
                        versionDropdown.addItem("1.20");
                        versionDropdown.addItem("1.20.1");
                        versionDropdown.addItem("1.20.2");
                        versionDropdown.addItem("1.20.3");
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
        });

        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loader = (String) loaderDropdown.getSelectedItem();
                String version = (String) versionDropdown.getSelectedItem();
                String downloadUrl = null;

                switch (loader) {
                    case "Fabric":
                    case "Quilt":
                        if ("1.21 EXPERIMENTAL".equals(version)) {
                            downloadUrl = FABRIC_QUILT_21_URL;
                        } else {
                            downloadUrl = FABRIC_QUILT_URL;
                        }
                        break;
                    case "Spigot":
                        downloadUrl = SPIGOT_URL;
                        break;
                    case "NeoForge":
                        downloadUrl = NEOFORGE_URL;
                        break;
                    case "Forge":
                        downloadUrl = FORGE_URL;
                        break;
                }

                if (downloadUrl != null) {
                    try {
                        downloadFile(downloadUrl);
                        JOptionPane.showMessageDialog(frame, "Download completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Download failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        setCustomIcon(frame, "https://github.com/ZtrolixGit/ZtrolixLibs/raw/main/icon.png");

        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 50, 50));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void downloadFile(String urlString) throws IOException {
        URL url = new URL(urlString);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        Path path = Paths.get("libs/" + Paths.get(url.getPath()).getFileName().toString());
        Files.createDirectories(path.getParent());
        FileOutputStream fis = new FileOutputStream(path.toFile());
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        bis.close();
        fis.close();
    }

    private static void setCustomIcon(JFrame frame, String iconUrl) {
        try {
            URL url = new URL(iconUrl);
            BufferedImage image = ImageIO.read(url);
            if (image != null) {
                frame.setIconImage(image);
            } else {
                System.err.println("Failed to load icon image from URL: " + iconUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}