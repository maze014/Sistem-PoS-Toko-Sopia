package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DBConfig;

public class BarangManagement {
public int idBarang;

    public static List<Barang> getAllBarang() {
        List<Barang> listBarang = new ArrayList<>();
        String sql = "SELECT * FROM barang"; // Pastikan nama tabel benar

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Barang b = new Barang(
                        rs.getInt("id_barang"),
                        rs.getInt("id_kategori"),
                        rs.getString("nama_barang"),
                        rs.getInt("harga"),
                        rs.getInt("stok"),
                        rs.getDate("tanggal_kadaluarsa"));
                // Tambahkan setter ID kalau perlu untuk hapus/edit
                listBarang.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Gagal tarik data: " + e.getMessage());
        }
        return listBarang;
    }

    // Fungsi khusus buat nambah atau ngurangin stok pakai tombol + dan -
    public static boolean updateStok(int idBarang, int perubahanStok) {
        // Kueri ini bakal nambahin atau ngurangin stok lama dengan nilai perubahan
        // (Kalau -1 ya stoknya berkurang, kalau +1 nambah)
        String sql = "UPDATE barang SET stok = stok + ? WHERE id_barang = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, perubahanStok);
            ps.setInt(2, idBarang);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update stok tambah: " + e.getMessage());
            return false;
        }
    }

     public static boolean deleteBarang(int idBarang) {
        // Peringatan: Pastikan nama tabelnya 'users' atau 'user', sesuaikan dengan
        // databasemu!
        String sql = "DELETE FROM barang WHERE id_barang = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            // Ganti tanda tanya (?) dengan username yang mau dihapus
            ps.setInt(1, idBarang);

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
