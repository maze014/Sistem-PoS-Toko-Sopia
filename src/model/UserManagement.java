package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConfig;

public class UserManagement {
    public static List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT nama_depan, nama_belakang, username, role FROM user";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User(
                        rs.getString("nama_depan"),
                        rs.getString("nama_belakang"),
                        rs.getString("username"),
                        null,
                        rs.getString("role"));
                listUser.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Gagal tarik data: " + e.getMessage());
        }
        return listUser;
    }

    public static boolean deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            int barisYangTerkeping = ps.executeUpdate();
            return barisYangTerkeping > 0;

        } catch (SQLException e) {
            System.out.println("Gagal hapus user Wak: " + e.getMessage());
            return false;
        }
    }
}
