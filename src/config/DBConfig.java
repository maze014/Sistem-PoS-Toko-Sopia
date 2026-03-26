package config;
import java.sql.*;

public class DBConfig {
    private static Connection conn;
    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3307/db_toko_sopia"; // Sesuaikan port
                String user = "root";
                String pass = "";
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                System.out.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return conn;
    }
}