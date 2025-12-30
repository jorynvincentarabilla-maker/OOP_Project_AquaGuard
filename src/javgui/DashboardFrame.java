// DashboardFrame.java
package javgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javmodels.User;

public class DashboardFrame extends JFrame {

    private final User currentUser;

    public DashboardFrame() {
        this(null);
    }

    public DashboardFrame(User user) {
        this.currentUser = user;
        setTitle("AquaGuard - Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        // Create top menu bar
        createMenuBar();

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setPreferredSize(new Dimension(900, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("🌊 AquaGuard Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JButton adminButton = new JButton(getUserBadgeText());
        adminButton.setFocusPainted(false);
        adminButton.setBackground(new Color(240, 248, 255));
        adminButton.setForeground(new Color(0, 102, 204));
        adminButton.addActionListener(e -> showUserInfo());
        headerPanel.add(adminButton, BorderLayout.EAST);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to Marine Management System");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        welcomeLabel.setForeground(new Color(200, 220, 255));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(welcomeLabel, BorderLayout.SOUTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        contentPanel.setBackground(new Color(240, 248, 255));

        // Quick Access Cards
        contentPanel.add(createCard("🐟 Marine Resources", "View and manage marine life", new Color(64, 164, 223), e -> openMarine()));
        contentPanel.add(createCard("🌿 Sustainability", "Monitor energy and waste", new Color(76, 175, 80), e -> openSustainability()));
        contentPanel.add(createCard("📊 Monitoring", "Sensor data and reports", new Color(255, 152, 0), e -> openMonitoring()));
        contentPanel.add(createCard("👥 User Management", "Manage system users", new Color(156, 39, 176), e -> openUsers()));

        // Stats Panel at Bottom
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        statsPanel.setBackground(new Color(240, 248, 255));

        

        // Sidebar Navigation
        JPanel sidebar = createSidebar();

        // Add all components
        add(headerPanel, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 102, 204));
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame();
                dispose();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(logoutItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // View Menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setForeground(Color.WHITE);
        viewMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem marineItem = new JMenuItem("Marine Resources");
        marineItem.addActionListener(e -> openMarine());

        JMenuItem sustainItem = new JMenuItem("Sustainability");
        sustainItem.addActionListener(e -> openSustainability());

        JMenuItem monitoringItem = new JMenuItem("Monitoring & Reports");
        monitoringItem.addActionListener(e -> openMonitoring());

        JMenuItem usersItem = new JMenuItem("User Management");
        usersItem.addActionListener(e -> openUsers());

        viewMenu.add(marineItem);
        viewMenu.add(sustainItem);
        viewMenu.add(monitoringItem);
        viewMenu.add(usersItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(Color.WHITE);
        helpMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem aboutItem = new JMenuItem("About AquaGuard");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "<html><div style='text-align: center;'>"
                            + "<h2>AquaGuard v1.0</h2>"
                            + "<p>Marine Resource Management System</p>"
                            + "<p> 2024 AquaGuard Team</p>"
                            + "</div></html>",
                    "About",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private JPanel createCard(String title, String description, Color color, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(color);

        JLabel descLabel = new JLabel("<html><div style='width:180px'>" + description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(Color.DARK_GRAY);

        JButton actionBtn = new JButton("Open →");
        actionBtn.setBackground(color);
        actionBtn.setForeground(Color.WHITE);
        actionBtn.setFocusPainted(false);
        actionBtn.setBorderPainted(false);
        actionBtn.addActionListener(action);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);
        card.add(actionBtn, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createStatBox(String title, String value, String icon) {
        JPanel statBox = new JPanel(new BorderLayout());
        statBox.setBackground(Color.WHITE);
        statBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 255), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(new Color(0, 102, 204));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statBox.add(valueLabel, BorderLayout.CENTER);
        statBox.add(titleLabel, BorderLayout.SOUTH);

        return statBox;
    }

    private void openMarine() {
        new MarineFrame(currentUser);
    }

    private void openSustainability() {
        new SustainabilityFrame();
    }

    private void openMonitoring() {
        new MonitoringReportsFrame();
    }

    private void openUsers() {
        new UserManagementFrame();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(6, 1, 0, 10));
        sidebar.setBackground(new Color(230, 242, 255));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JButton homeBtn = new JButton("Dashboard");
        JButton usersBtn = new JButton("User Management");
        JButton marineBtn = new JButton("Marine Resources");
        JButton monitoringBtn = new JButton("Monitoring & Reports");
        JButton sustainabilityBtn = new JButton("Sustainability");
        JButton logoutBtn = new JButton("Logout");

        styleNavButton(homeBtn);
        styleNavButton(usersBtn);
        styleNavButton(marineBtn);
        styleNavButton(monitoringBtn);
        styleNavButton(sustainabilityBtn);
        styleNavButton(logoutBtn);

        homeBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "You are already on the dashboard.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE));
        usersBtn.addActionListener(e -> openUsers());
        marineBtn.addActionListener(e -> openMarine());
        monitoringBtn.addActionListener(e -> openMonitoring());
        sustainabilityBtn.addActionListener(e -> openSustainability());
        logoutBtn.addActionListener(e -> performLogout());

        sidebar.add(homeBtn);
        sidebar.add(usersBtn);
        sidebar.add(marineBtn);
        sidebar.add(monitoringBtn);
        sidebar.add(sustainabilityBtn);
        sidebar.add(logoutBtn);
        return sidebar;
    }

    private void styleNavButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(0, 102, 204));
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 1));
    }

    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame();
            dispose();
        }
    }

    private String getUserBadgeText() {
        if (currentUser == null) {
            return "Admin";
        }

        String role = currentUser.getRole();
        if (role == null || role.trim().isEmpty()) {
            return currentUser.getName() != null ? currentUser.getName() : "Account";
        }
        return role.toUpperCase();
    }

    private void showUserInfo() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this,
                    "No user context available.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Name: " + currentUser.getName() + "\nEmail: " + currentUser.getEmail() + "\nRole: " + currentUser.getRole(),
                "Account",
                JOptionPane.INFORMATION_MESSAGE);
    }
}