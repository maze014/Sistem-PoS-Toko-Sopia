package model;

import config.DBConfig;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class TransaksiModel {

    // Cari barang spesifik pas kasir ngetik ID/Nama
    public static Object[] cariBarangUntukKeranjang(String keyword) {
        String sql = "SELECT id_barang, nama_barang, harga_jual, stok FROM barang " +
                     "WHERE id_barang = ? OR nama_barang = ?";
        try (Connection conn = DBConfig.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Object[]{
                    rs.getInt("id_barang"), 
                    rs.getString("nama_barang"), 
                    rs.getInt("harga_jual"), 
                    rs.getInt("stok")
                };
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null; // Kalau barang gak ketemu
    }

    // FUNGSI SAKTI CHECKOUT (Database Transaction)
    public static boolean prosesCheckout(int idKasir, DefaultTableModel keranjang, int totalBelanja, int uangBayar) {
        Connection conn = null;
        try {
            conn = DBConfig.getConnection();
            conn.setAutoCommit(false); // Kunci database! Kalau ada error, batalkan semua.

            // 1. Insert Transaksi (Asumsi id_pelanggan = 1 untuk pelanggan umum)
            String sqlTrx = "INSERT INTO transaksi (id_pelanggan, id_user, tanggal, total_pembayaran) VALUES (1, ?, NOW(), ?)";
            PreparedStatement psTrx = conn.prepareStatement(sqlTrx, Statement.RETURN_GENERATED_KEYS);
            psTrx.setInt(1, idKasir);
            psTrx.setInt(2, totalBelanja);
            psTrx.executeUpdate();

            // Ambil ID Transaksi yang baru aja dibikin MySQL
            ResultSet rsKeys = psTrx.getGeneratedKeys();
            int idTransaksiBaru = 0;
            if (rsKeys.next()) idTransaksiBaru = rsKeys.getInt(1);

            // 2. Loop Keranjang: Insert Detail Transaksi & Kurangi Stok Barang
            String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_barang, jumlah_barang) VALUES (?, ?, ?)";
            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
            
            String sqlStok = "UPDATE barang SET stok = stok - ? WHERE id_barang = ?";
            PreparedStatement psStok = conn.prepareStatement(sqlStok);

            for (int i = 0; i < keranjang.getRowCount(); i++) {
                int idBarang = (int) keranjang.getValueAt(i, 0);
                int qty = (int) keranjang.getValueAt(i, 3);

                // Suntik ke detail_transaksi
                psDetail.setInt(1, idTransaksiBaru);
                psDetail.setInt(2, idBarang);
                psDetail.setInt(3, qty);
                psDetail.addBatch();

                // Kurangi stok di tabel barang
                psStok.setInt(1, qty);
                psStok.setInt(2, idBarang);
                psStok.addBatch();
            }
            psDetail.executeBatch();
            psStok.executeBatch();

            // 3. Insert Pembayaran (Otomatis Tunai)
            String sqlBayar = "INSERT INTO pembayaran (id_transaksi, jumlah_bayar, metode_pembayaran) VALUES (?, ?, 'Tunai')";
            PreparedStatement psBayar = conn.prepareStatement(sqlBayar);
            psBayar.setInt(1, idTransaksiBaru);
            psBayar.setInt(2, uangBayar);
            psBayar.executeUpdate();

            // 4. Kalau semua sukses, COMMIT! Permanenkan data.
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