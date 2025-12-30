package javgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UserDAO;
import javmodels.User;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;

    public RegisterFrame() {
        setTitle("AquaGuard - Registration");
        setSize(450, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(0, 102, 204));
        header.setPreferredSize(new Dimension(450, 80));

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLabel.setForeground(new Color(0, 102, 204));
        gbc.gridy = 0;
        form.add(nameLabel, gbc);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(280, 38));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 255), 2),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        gbc.gridy = 1;
        form.add(nameField, gbc);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        emailLabel.setForeground(new Color(0, 102, 204));
        gbc.gridy = 2;
        form.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(280, 38));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 255), 2),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        gbc.gridy = 3;
        form.add(emailField, gbc);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(0, 102, 204));
        gbc.gridy = 4;
        form.add(passLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(280, 38));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 220, 255), 2),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        gbc.gridy = 5;
        form.add(passwordField, gbc);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        roleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridy = 6;
        form.add(roleLabel, gbc);

        roleCombo = new JComboBox<>(new String[]{"staff", "researcher"});
        roleCombo.setPreferredSize(new Dimension(280, 38));
        gbc.gridy = 7;
        form.add(roleCombo, gbc);

        JPanel actions = new JPanel();
        actions.setBackground(Color.WHITE);

        JButton createBtn = new JButton("Create Account");
        createBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createBtn.setBackground(new Color(0, 102, 204));
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusPainted(false);
        createBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton backBtn = new JButton("Back to Login");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        actions.add(createBtn);
        actions.add(backBtn);

        createBtn.addActionListener(e -> performRegistration());
        backBtn.addActionListener(e -> dispose());

        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void performRegistration() {
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String email = emailField.getText() != null ? emailField.getText().trim() : "";
        String password = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "AquaGuard", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        if (userDAO.emailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email already registered. Please use another email.", "AquaGuard", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User(0, email, password, name, role);
        boolean ok = userDAO.addUser(user);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.", "AquaGuard", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please check database connection.", "AquaGuard", JOptionPane.ERROR_MESSAGE);
        }
    }
}
