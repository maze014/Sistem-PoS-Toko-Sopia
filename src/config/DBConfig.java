package config;
import java.sql.*;

public class DBConfig {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3307/db_toko_sopia"; 
        String user = "root";
        String pass = "";
        
        return DriverManager.getConnection(url, user, pass);
    }
}