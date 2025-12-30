// src/main/aquaguardapp.java
package logmain;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javgui.LoginFrame;

public class AquaGuardApp {
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show login window
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginWindow = new LoginFrame();
            loginWindow.setVisible(true);
        });
    }
}