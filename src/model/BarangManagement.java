package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import config.DBConfig;

public class BarangManagement {
    public int idBarang;

    public static List<Barang> getAllBarang() {
        List<Barang> listBarang = new ArrayList<>();
        String sql = "SELECT * FROM barang";

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
                listBarang.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Gagal tarik data: " + e.getMessage());
        }
        return listBarang;
    }

    public static boolean tambahBarang(int idKategori, String namaBarang, int harga, int stok, Date tanggalKadaluarsa) {
        String sql = "INSERT INTO barang (id_kategori, nama_barang, harga, stok, tanggal_kadaluarsa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idKategori);
            ps.setString(2, namaBarang);
            ps.setInt(3, harga);
            ps.setInt(4, stok);
            ps.setDate(5, new java.sql.Date(tanggalKadaluarsa.getTime()));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal tambah barang: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateBarang(int idKategori, String namaBarang, int harga, int stok, Date tanggalKadaluarsa,
            int idBarangLama) {
        String sql = "UPDATE barang SET id_kategori = ?, nama_barang = ?, harga = ?, stok = ?, tanggal_kadaluarsa = ? WHERE id_barang = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idKategori);
            ps.setString(2, namaBarang);
            ps.setInt(3, harga);
            ps.setInt(4, stok);
            ps.setDate(5, new java.sql.Date(tanggalKadaluarsa.getTime()));
            ps.setInt(6, idBarangLama);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal tambah barang: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateStok(int idBarang, int perubahanStok) {
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
        String sql = "DELETE FROM barang WHERE id_barang = ?";

        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBarang);
            int barisYangTerkeping = ps.executeUpdate();
            return barisYangTerkeping > 0;

        } catch (SQLException e) {
            System.out.println("Gagal hapus user Wak: " + e.getMessage());
            return false;
        }
    }
}
