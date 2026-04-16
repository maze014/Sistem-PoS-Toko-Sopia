package config;
import java.sql.*;

public class DBConfig {
    // Fungsi ini WAJIB mencetak koneksi BARU setiap kali dipanggil
    public static Connection getConnection() throws SQLException {
        // Sesuaikan nama database, user, dan password kamu
        String url = "jdbc:mysql://localhost:3307/db_toko_sopia"; 
        String user = "root";
        String pass = "";
        
        // DriverManager.getConnection otomatis membuat jalur baru ke database
        return DriverManager.getConnection(url, user, pass);
    }
}