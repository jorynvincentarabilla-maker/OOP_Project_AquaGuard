package javgui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.MonitoringDAO;

public class MonitoringReportsFrame extends JFrame {

    private JLabel activeSensorsValue;
    private JLabel dataReceivedValue;
    private JLabel systemStatusValue;

    private JLabel dailyReportsValue;
    private JLabel weeklyReportsValue;
    private JLabel monthlyReportsValue;

    private MonitoringDAO monitoringDAO;

    public MonitoringReportsFrame() {
        monitoringDAO = new MonitoringDAO();
        setTitle("Monitoring & Reports");
        setSize(700, 450);
        setLocationRelativeTo(null);

        JPanel container = new JPanel(new GridLayout(3, 1, 10, 10));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel monitoringPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        monitoringPanel.setBorder(BorderFactory.createTitledBorder("Monitoring Data"));

        monitoringPanel.add(new JLabel("Active Sensors:"));
        activeSensorsValue = new JLabel("-");
        monitoringPanel.add(activeSensorsValue);

        monitoringPanel.add(new JLabel("Data Received (per day):"));
        dataReceivedValue = new JLabel("-");
        monitoringPanel.add(dataReceivedValue);

        monitoringPanel.add(new JLabel("System Status:"));
        systemStatusValue = new JLabel("-");
        monitoringPanel.add(systemStatusValue);

        JPanel reportsPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        reportsPanel.setBorder(BorderFactory.createTitledBorder("Reports"));

        reportsPanel.add(new JLabel("Daily Reports Generated:"));
        dailyReportsValue = new JLabel("-");
        reportsPanel.add(dailyReportsValue);

        reportsPanel.add(new JLabel("Weekly Reports Generated:"));
        weeklyReportsValue = new JLabel("-");
        reportsPanel.add(weeklyReportsValue);

        reportsPanel.add(new JLabel("Monthly Reports Generated:"));
        monthlyReportsValue = new JLabel("-");
        reportsPanel.add(monthlyReportsValue);

        JPanel actions = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        JButton closeBtn = new JButton("Close");
        actions.add(refreshBtn);
        actions.add(closeBtn);

        refreshBtn.addActionListener(e -> refreshMetrics());
        closeBtn.addActionListener(e -> dispose());

        container.add(monitoringPanel);
        container.add(reportsPanel);
        container.add(actions);

        add(container);
        refreshMetrics();
        setVisible(true);
    }

    private void refreshMetrics() {
        activeSensorsValue.setText(monitoringDAO.getMetric("Active Sensors"));
        dataReceivedValue.setText(monitoringDAO.getMetric("Data Received (per day)"));
        systemStatusValue.setText(monitoringDAO.getMetric("System Status"));

        dailyReportsValue.setText(String.valueOf(monitoringDAO.getReportCount("daily")));
        weeklyReportsValue.setText(String.valueOf(monitoringDAO.getReportCount("weekly")));
        monthlyReportsValue.setText(String.valueOf(monitoringDAO.getReportCount("monthly")));
    }
}
