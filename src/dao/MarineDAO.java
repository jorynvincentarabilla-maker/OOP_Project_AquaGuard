// MarineDAO.java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnect;
import javmodels.MarineResource;

public class MarineDAO {
    
    public List<MarineResource> getAllResources() {
        List<MarineResource> resources = new ArrayList<>();
        String sql = "SELECT * FROM marine_resources";
        
        try (Connection conn = DBConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                resources.add(new MarineResource(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resources;
    }
    
    public boolean addResource(MarineResource resource) {
        String sql = "INSERT INTO marine_resources (name, type, quantity) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, resource.getName());
            pstmt.setString(2, resource.getType());
            pstmt.setInt(3, resource.getQuantity());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}