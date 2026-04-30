package model;

import config.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiModel {
    public static List<Object[]> cariBarangLive(String keyword) {
        List<Object[]> hasil = new ArrayList<>();
        String sql = "SELECT id_barang, nama_barang, harga, stok FROM barang " +
                     "WHERE nama_barang LIKE ? OR id_barang LIKE ? LIMIT 5";

        try (Connection conn = DBConfig.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ps.setString(2, search);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hasil.add(new Object[]{
                    rs.getInt("id_barang"), 
                    rs.getString("nama_barang"), 
                    rs.getInt("harga"), 
                    rs.getInt("stok")
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return hasil;
    }

    public static Object[] cariBarangUntukKeranjang(String keyword) {
        String sql = "SELECT id_barang, nama_barang, harga, stok FROM barang WHERE id_barang = ? OR nama_barang = ?";
        try (Connection conn = DBConfig.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, keyword); ps.setString(2, keyword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Object[]{ rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getInt("harga"), rs.getInt("stok") };
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static boolean prosesCheckout(int idKasir, List<Object[]> keranjang, int totalBelanja, int uangBayar) {
        Connection conn = null;
        try {
            conn = DBConfig.getConnection();
            conn.setAutoCommit(false); 

            String sqlTrx = "INSERT INTO transaksi (id_pelanggan, id_user, tanggal, total_pembayaran) VALUES (1, ?, NOW(), ?)";
            PreparedStatement psTrx = conn.prepareStatement(sqlTrx, Statement.RETURN_GENERATED_KEYS);
            psTrx.setInt(1, idKasir); psTrx.setInt(2, totalBelanja);
            psTrx.executeUpdate();

            ResultSet rsKeys = psTrx.getGeneratedKeys();
            int idTransaksiBaru = rsKeys.next() ? rsKeys.getInt(1) : 0;

            String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_barang, jumlah_barang) VALUES (?, ?, ?)";
            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
            String sqlStok = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";
            PreparedStatement psStok = conn.prepareStatement(sqlStok);

            for (Object[] item : keranjang) {
                int idBarang = (int) item[0];
                int qty = (int) item[3];

                psDetail.setInt(1, idTransaksiBaru); psDetail.setInt(2, idBarang); psDetail.setInt(3, qty);
                psDetail.addBatch();

                psStok.setInt(1, qty); psStok.setInt(2, idBarang);
                psStok.addBatch();
            }
            psDetail.executeBatch(); psStok.executeBatch();

            String sqlBayar = "INSERT INTO pembayaran (id_transaksi, jumlah_bayar, metode_pembayaran) VALUES (?, ?, 'Tunai')";
            PreparedStatement psBayar = conn.prepareStatement(sqlBayar);
            psBayar.setInt(1, idTransaksiBaru); psBayar.setInt(2, uangBayar);
            psBayar.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}