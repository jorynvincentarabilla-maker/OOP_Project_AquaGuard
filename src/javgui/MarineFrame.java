// MarineFrame.java
package javgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.MarineResourceDAO;
import javmodels.MarineResource;
import javmodels.User;

public class MarineFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private MarineResourceDAO marineDAO;

    private final User currentUser;
    private final boolean isAdmin;

    public MarineFrame() {
        this(null);
    }

    public MarineFrame(User user) {
        this.currentUser = user;
        this.isAdmin = user != null && user.getRole() != null && user.getRole().equalsIgnoreCase("admin");

        setTitle("Marine Resources");
        setSize(750, 450);
        setLayout(new BorderLayout());

        marineDAO = new MarineResourceDAO();

        String[] columns = {"ID", "Name", "Type", "Quantity"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton refreshBtn = new JButton("Refresh");
        topBar.add(refreshBtn);

        if (isAdmin) {
            JButton addBtn = new JButton("Add");
            JButton editBtn = new JButton("Edit");
            JButton deleteBtn = new JButton("Delete");

            topBar.add(addBtn);
            topBar.add(editBtn);
            topBar.add(deleteBtn);

            addBtn.addActionListener(e -> addResource());
            editBtn.addActionListener(e -> editSelectedResource());
            deleteBtn.addActionListener(e -> deleteSelectedResource());
        } else {
            JLabel viewOnly = new JLabel("View only (admin required to modify)");
            topBar.add(viewOnly);
        }

        refreshBtn.addActionListener(e -> loadResources());

        add(topBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        loadResources();
        setVisible(true);
    }

    private void loadResources() {
        model.setRowCount(0);
        List<MarineResource> resources = marineDAO.getAllResources();
        if (resources != null) {
            for (MarineResource r : resources) {
                model.addRow(new Object[]{
                    r.getId(),
                    r.getName(),
                    r.getType(),
                    r.getQuantity()
                });
            }
        }
    }

    private void addResource() {
        if (!isAdmin) {
            JOptionPane.showMessageDialog(this, "Only admin can add marine resources.", "Permission denied",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField qtyField = new JTextField();

        nameField.setPreferredSize(new Dimension(220, 28));
        typeField.setPreferredSize(new Dimension(220, 28));
        qtyField.setPreferredSize(new Dimension(120, 28));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Marine Resource", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String type = typeField.getText() != null ? typeField.getText().trim() : "";
        String qtyStr = qtyField.getText() != null ? qtyField.getText().trim() : "";

        if (name.isEmpty() || type.isEmpty() || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok = marineDAO.addResource(new MarineResource(0, name, type, qty));
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Failed to add resource.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        loadResources();
    }

    private void editSelectedResource() {
        if (!isAdmin) {
            JOptionPane.showMessageDialog(this, "Only admin can edit marine resources.", "Permission denied",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a resource.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String existingName = String.valueOf(model.getValueAt(row, 1));
        String existingType = String.valueOf(model.getValueAt(row, 2));
        String existingQty = String.valueOf(model.getValueAt(row, 3));

        JTextField nameField = new JTextField(existingName);
        JTextField typeField = new JTextField(existingType);
        JTextField qtyField = new JTextField(existingQty);

        nameField.setPreferredSize(new Dimension(220, 28));
        typeField.setPreferredSize(new Dimension(220, 28));
        qtyField.setPreferredSize(new Dimension(120, 28));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Quantity:"));
        panel.add(qtyField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Marine Resource", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String type = typeField.getText() != null ? typeField.getText().trim() : "";
        String qtyStr = qtyField.getText() != null ? qtyField.getText().trim() : "";

        if (name.isEmpty() || type.isEmpty() || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok = marineDAO.updateResource(new MarineResource(id, name, type, qty));
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Failed to update resource.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        loadResources();
    }

    private void deleteSelectedResource() {
        if (!isAdmin) {
            JOptionPane.showMessageDialog(this, "Only admin can delete marine resources.", "Permission denied",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a resource.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected resource?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean ok = marineDAO.deleteResource(id);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Failed to delete resource.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        loadResources();
    }
}