package model;

import config.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RiwayatKasirModel {
    public static List<Map<String, Object>> getDataRiwayat(int idKasir) {
        List<Map<String, Object>> listData = new ArrayList<>();
        String sql = "SELECT id_transaksi, tanggal, total_pembayaran FROM transaksi " +
                     "WHERE id_user = ? ORDER BY tanggal DESC LIMIT 20";

        try (Connection conn = DBConfig.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKasir);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt("id_transaksi"));
                map.put("tanggal", rs.getString("tanggal"));
                map.put("total", rs.getInt("total_pembayaran"));
                listData.add(map);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return listData;
    }

    public static String getDetailStruk(int idTrx) {
        StringBuilder struk = new StringBuilder();
        String sql = "SELECT b.nama_barang, dt.jumlah_barang, b.harga " +
                     "FROM detail_transaksi dt JOIN barang b ON dt.id_barang = b.id_barang " +
                     "WHERE dt.id_transaksi = ?";

        try (Connection conn = DBConfig.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTrx);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                struk.append(rs.getString("nama_barang")).append(" x")
                     .append(rs.getInt("jumlah_barang")).append("\n");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return struk.toString();
    }
}