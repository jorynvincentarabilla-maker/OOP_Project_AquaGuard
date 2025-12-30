// UserDAO.java
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnect;
import javmodels.User;

public class UserDAO {
    
    public User checkLogin(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return null;
            }
            
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (email, password, name, role) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return false;
            }
            
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getRole());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<User> getAllUsers() {
        String sql = "SELECT id, email, password, name, role FROM users ORDER BY id ASC";
 
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {
 
            if (pstmt == null) {
                return null;
            }
 
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role")
                ));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT id, email, password, name, role FROM users WHERE id = ?";
 
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {
 
            if (pstmt == null) {
                return null;
            }
 
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT id, email, password, name, role FROM users WHERE email = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return null;
            }

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean emailExists(String email) {
        return getUserByEmail(email) != null;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET email = ?, password = ?, name = ?, role = ? WHERE id = ?";
 
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {
 
            if (pstmt == null) {
                return false;
            }
 
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getRole());
            pstmt.setInt(5, user.getId());
 
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
 
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
}