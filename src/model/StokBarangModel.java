package model;

import config.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StokBarangModel {
    public static List<Object[]> cariBarang(String keyword) {
        List<Object[]> dataBarang = new ArrayList<>();
        
        // Cari berdasarkan nama barang ATAU ID barang
        String sql = "SELECT id_barang, nama_barang, stok, harga FROM barang " +
                     "WHERE nama_barang LIKE ? ORDER BY nama_barang ASC LIMIT 100";
                     
        try (Connection conn = DBConfig.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String hargaRp = "Rp " + String.format("%,d", rs.getInt("harga"));
                dataBarang.add(new Object[]{
                    rs.getInt("id_barang"), 
                    rs.getString("nama_barang"), 
                    rs.getInt("stok"), 
                    hargaRp
                });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        
        return dataBarang;
    }
}