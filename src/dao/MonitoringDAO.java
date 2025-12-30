package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnect;

public class MonitoringDAO {

    public String getMetric(String metricName) {
        String sql = "SELECT metric_value FROM monitoring_data WHERE metric_name = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return "-";
            }

            pstmt.setString(1, metricName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("metric_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-";
    }

    public int getReportCount(String reportType) {
        String sql = "SELECT COUNT(*) AS cnt FROM reports WHERE report_type = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn != null ? conn.prepareStatement(sql) : null) {

            if (pstmt == null) {
                return 0;
            }

            pstmt.setString(1, reportType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
