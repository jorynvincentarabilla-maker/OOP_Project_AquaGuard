package javgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.UserDAO;
import javmodels.User;

public class UserManagementFrame extends JFrame {

    private final UserDAO userDAO;
    private final DefaultTableModel model;
    private final JTable table;

    public UserManagementFrame() {
        this.userDAO = new UserDAO();

        setTitle("User Management");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[] { "ID", "Email", "Name", "Role" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(24);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton refreshBtn = new JButton("Refresh");
        JButton addBtn = new JButton("Add User");
        JButton editBtn = new JButton("Edit Selected");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton closeBtn = new JButton("Close");

        controls.add(refreshBtn);
        controls.add(addBtn);
        controls.add(editBtn);
        controls.add(deleteBtn);
        controls.add(closeBtn);

        add(controls, BorderLayout.NORTH);

        refreshBtn.addActionListener(e -> refreshUsers());
        addBtn.addActionListener(e -> addUser());
        editBtn.addActionListener(e -> editSelectedUser());
        deleteBtn.addActionListener(e -> deleteSelectedUser());
        closeBtn.addActionListener(e -> dispose());

        refreshUsers();
        setVisible(true);
    }

    private void refreshUsers() {
        model.setRowCount(0);

        List<User> users = userDAO.getAllUsers();
        if (users == null) {
            JOptionPane.showMessageDialog(this, "Failed to load users from database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (User u : users) {
            model.addRow(new Object[] { u.getId(), u.getEmail(), u.getName(), u.getRole() });
        }
    }

    private void addUser() {
        JTextField emailField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField passwordField = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[] { "staff", "researcher" });

        emailField.setPreferredSize(new Dimension(250, 28));
        nameField.setPreferredSize(new Dimension(250, 28));
        passwordField.setPreferredSize(new Dimension(250, 28));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String email = emailField.getText().trim();
        String name = nameField.getText().trim();
        String password = passwordField.getText();
        String role = (String) roleBox.getSelectedItem();

        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || role == null || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok = userDAO.addUser(new User(0, email, password, name, role));
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refreshUsers();
    }

    private void editSelectedUser() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        User existing = userDAO.getUserById(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Failed to load selected user from database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField emailField = new JTextField(existing.getEmail());
        JTextField nameField = new JTextField(existing.getName());
        JTextField passwordField = new JTextField();
        JComboBox<String> roleBox = new JComboBox<>(new String[] { "staff", "researcher" });
        roleBox.setSelectedItem(existing.getRole());

        emailField.setPreferredSize(new Dimension(250, 28));
        nameField.setPreferredSize(new Dimension(250, 28));
        passwordField.setPreferredSize(new Dimension(250, 28));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("New Password (leave blank to keep):"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit User", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String email = emailField.getText().trim();
        String name = nameField.getText().trim();
        String role = (String) roleBox.getSelectedItem();
        String password = passwordField.getText();

        if (email.isEmpty() || name.isEmpty() || role == null || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in required fields.", "Validation",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String finalPassword = password.isEmpty() ? existing.getPassword() : password;

        boolean ok = userDAO.updateUser(new User(id, email, finalPassword, name, role));
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Failed to update user.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refreshUsers();
    }

    private void deleteSelectedUser() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a user.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected user?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean ok = userDAO.deleteUser(id);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        refreshUsers();
    }
}
