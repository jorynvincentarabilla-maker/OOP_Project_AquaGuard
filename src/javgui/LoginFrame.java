// LoginFrame.java
package javgui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UserDAO;
import javmodels.User;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox rememberCheck;
    private JButton togglePasswordBtn;
    private boolean passwordVisible = false;
    
    public LoginFrame() {
        setTitle("AquaGuard");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Create rounded panel for main content
        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header with gradient
        GradientHeader headerPanel = new GradientHeader();
        headerPanel.setPreferredSize(new Dimension(450, 80));
        
        JLabel titleLabel = new JLabel("🌊 AquaGuard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel);
        
        // Form panel with rounded corners
        RoundedPanel formPanel = new RoundedPanel(20);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(12, 12, 12, 12);
        
        // Email Section
        JLabel emailLabel = new JLabel("📧 Email");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);
        
        emailField = new RoundedTextField(15);
        emailField.setPreferredSize(new Dimension(250, 40));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setText("user@example.com");
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 255), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(emailField, gbc);
        
        // Password Section
        JLabel passLabel = new JLabel("🔒 Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(passLabel, gbc);
        
        // Create a panel to hold password field and toggle button
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setOpaque(false);
        
        passwordField = new RoundedPasswordField(15);
        passwordField.setPreferredSize(new Dimension(250, 40));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 255), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        
        // Toggle Password Button
        togglePasswordBtn = new JButton("👁");
        togglePasswordBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        togglePasswordBtn.setPreferredSize(new Dimension(40, 40));
        togglePasswordBtn.setMargin(new Insets(0, 0, 0, 0));
        togglePasswordBtn.setFocusPainted(false);
        togglePasswordBtn.setBackground(new Color(240, 248, 255));
        togglePasswordBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255), 2));
        togglePasswordBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePasswordBtn.setToolTipText("Show/Hide Password");
        togglePasswordBtn.addActionListener(e -> togglePasswordVisibility());
        
        // Add toggle button to the right of password field
        passwordPanel.add(togglePasswordBtn, BorderLayout.EAST);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(passwordPanel, gbc);
        
        // Remember Me Checkbox with custom style
        rememberCheck = new JCheckBox("Remember me") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        };
        rememberCheck.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rememberCheck.setBackground(Color.WHITE);
        rememberCheck.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(rememberCheck, gbc);
        
        // Login Button with gradient and rounded corners
        GradientButton loginButton = new GradientButton("Log in");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(280, 45));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> performLogin());
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Create account");
        registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setForeground(new Color(0, 102, 204));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> new RegisterFrame());
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(registerButton, gbc);
        
        // Water wave decoration at bottom
        JPanel wavePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw water waves
                g2.setColor(new Color(64, 164, 223, 100));
                g2.fillArc(50, -30, 100, 60, 0, 180);
                g2.fillArc(150, -30, 100, 60, 0, 180);
                g2.fillArc(250, -30, 100, 60, 0, 180);
                
                g2.setColor(new Color(32, 132, 191, 150));
                g2.fillArc(100, -20, 100, 60, 0, 180);
                g2.fillArc(200, -20, 100, 60, 0, 180);
                g2.fillArc(300, -20, 100, 60, 0, 180);
            }
        };
        wavePanel.setPreferredSize(new Dimension(450, 30));
        wavePanel.setBackground(new Color(240, 248, 255));
        
        // Assemble everything
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(wavePanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        
        if (passwordVisible) {
            // Show password
            passwordField.setEchoChar((char) 0); // Show plain text
            togglePasswordBtn.setText("🙈");
            togglePasswordBtn.setToolTipText("Hide Password");
            
            // Change button color to indicate active state
            togglePasswordBtn.setBackground(new Color(0, 102, 204));
            togglePasswordBtn.setForeground(Color.WHITE);
        } else {
            // Hide password
            passwordField.setEchoChar('•'); // Show password dots
            togglePasswordBtn.setText("👁");
            togglePasswordBtn.setToolTipText("Show Password");
            
            // Reset button color
            togglePasswordBtn.setBackground(new Color(240, 248, 255));
            togglePasswordBtn.setForeground(Color.BLACK);
        }
        
        // Refresh the field
        passwordField.repaint();
    }
    
    private void performLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.checkLogin(email, password);
        
        if (user != null) {
            // Success animation
            emailField.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
            passwordField.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
            
            JOptionPane.showMessageDialog(this, 
                "<html><div style='text-align: center;'>"
                + "<h3 style='color: #4CAF50;'>✅ Login Successful!</h3>"
                + "Welcome back, <b>" + user.getName() + "</b>!"
                + "</div></html>", 
                "AquaGuard", 
                JOptionPane.INFORMATION_MESSAGE);
            
            DashboardFrame dashboard = new DashboardFrame(user);
            dashboard.setVisible(true);
            this.dispose();
        } else {
            // Error animation
            emailField.setBorder(BorderFactory.createLineBorder(new Color(244, 67, 54), 2));
            passwordField.setBorder(BorderFactory.createLineBorder(new Color(244, 67, 54), 2));
            
            JOptionPane.showMessageDialog(this, 
                "<html><div style='text-align: center;'>"
                + "<h3 style='color: #F44336;'>❌ Login Failed!</h3>"
                + "Invalid email or password."
                + "</div></html>", 
                "AquaGuard", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Custom Rounded Panel
    class RoundedPanel extends JPanel {
        private int radius;
        
        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }
    
    // Custom Rounded Text Field
    class RoundedTextField extends JTextField {
        private int radius;
        
        public RoundedTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }
    
    // Custom Rounded Password Field
    class RoundedPasswordField extends JPasswordField {
        private int radius;
        
        public RoundedPasswordField(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }
    
    // Gradient Header Panel
    class GradientHeader extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Ocean blue gradient
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(0, 102, 204),  // Deep blue
                getWidth(), getHeight(), new Color(64, 164, 223)  // Light blue
            );
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
            
            // Water wave effect overlay
            g2.setColor(new Color(255, 255, 255, 30));
            for (int i = 0; i < 5; i++) {
                g2.fillArc(i * 80, getHeight() - 20, 160, 40, 0, 180);
            }
        }
    }
    
    // Gradient Button
    class GradientButton extends JButton {
        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Button gradient
            GradientPaint gradient;
            if (getModel().isPressed()) {
                gradient = new GradientPaint(
                    0, 0, new Color(0, 82, 164),  // Darker when pressed
                    0, getHeight(), new Color(44, 124, 183)
                );
            } else {
                gradient = new GradientPaint(
                    0, 0, new Color(0, 122, 204),  // Normal gradient
                    0, getHeight(), new Color(64, 184, 243)
                );
            }
            
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            
            // Button border
            g2.setColor(new Color(0, 82, 164, 150));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 25, 25);
            
            super.paintComponent(g);
        }
    }
}