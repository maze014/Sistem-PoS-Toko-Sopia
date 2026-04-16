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
        String sql = "SELECT nama_depan, nama_belakang, username, role FROM user"; // Pastikan nama tabel benar

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
                // Tambahkan setter ID kalau perlu untuk hapus/edit
                listUser.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Gagal tarik data: " + e.getMessage());
        }
        return listUser;
    }

    // Fungsi buat hapus user, kembaliannya boolean (true kalau sukses, false kalau
    // gagal)
    public static boolean deleteUser(String username) {
        // Peringatan: Pastikan nama tabelnya 'users' atau 'user', sesuaikan dengan
        // databasemu!
        String sql = "DELETE FROM user WHERE username = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            // Ganti tanda tanya (?) dengan username yang mau dihapus
            ps.setString(1, username);

            // Eksekusi query-nya! (executeUpdate dipakai untuk Insert, Update, Delete)
            int barisYangTerkeping = ps.executeUpdate();

            // Kalau ada baris yang kehapus (lebih dari 0), berarti sukses!
            return barisYangTerkeping > 0;

        } catch (SQLException e) {
            System.out.println("Gagal hapus user Wak: " + e.getMessage());
            return false;
        }
    }
}
