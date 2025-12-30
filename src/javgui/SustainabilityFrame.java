// SustainabilityFrame.java
package javgui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SustainabilityFrame extends JFrame {
    public SustainabilityFrame() {
        setTitle("Sustainability");
        setSize(600, 400);
        
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Energy Panel
        JPanel energyPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        energyPanel.setBorder(BorderFactory.createTitledBorder("Energy Consumption"));
        energyPanel.add(new JLabel("Monthly Consumption:"));
        energyPanel.add(new JLabel("4500 kWh"));
        energyPanel.add(new JLabel("Daily Consumption:"));
        energyPanel.add(new JLabel("150 kWh"));
        energyPanel.add(new JLabel("Efficiency Rating:"));
        energyPanel.add(new JLabel("85%"));
        
        // Waste Panel
        JPanel wastePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        wastePanel.setBorder(BorderFactory.createTitledBorder("Waste Management"));
        wastePanel.add(new JLabel("Waste Generated:"));
        wastePanel.add(new JLabel("200 kg/month"));
        wastePanel.add(new JLabel("Waste Recycled:"));
        wastePanel.add(new JLabel("130 kg/month"));
        wastePanel.add(new JLabel("Recycling Rate:"));
        wastePanel.add(new JLabel("65%"));
        
        panel.add(energyPanel);
        panel.add(wastePanel);
        
        add(panel);
        setVisible(true);
    }
}