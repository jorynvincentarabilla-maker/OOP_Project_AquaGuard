package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnect;
import javmodels.MarineResource;

public class MarineResourceDAO {

    public List<MarineResource> getAllResources() {
        String sql = "SELECT id, name, type, quantity FROM marine_resources ORDER BY id ASC";

        List<MarineResource> resources = new ArrayList<>();
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return null;
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resources.add(new MarineResource(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("quantity")
                ));
            }
            return resources;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addResource(MarineResource resource) {
        String sql = "INSERT INTO marine_resources (name, type, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return false;
            }

            pstmt.setString(1, resource.getName());
            pstmt.setString(2, resource.getType());
            pstmt.setInt(3, resource.getQuantity());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateResource(MarineResource resource) {
        String sql = "UPDATE marine_resources SET name = ?, type = ?, quantity = ? WHERE id = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return false;
            }

            pstmt.setString(1, resource.getName());
            pstmt.setString(2, resource.getType());
            pstmt.setInt(3, resource.getQuantity());
            pstmt.setInt(4, resource.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteResource(int id) {
        String sql = "DELETE FROM marine_resources WHERE id = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return false;
            }

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public MarineResource getResourceById(int id) {
        String sql = "SELECT id, name, type, quantity FROM marine_resources WHERE id = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return null;
            }

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new MarineResource(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
