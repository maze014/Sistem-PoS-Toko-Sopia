package model;

import config.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GudangDashboardModel {
    public static int getTotalMacamBarang() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(id_barang) FROM barang")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getTotalStokFisik() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT SUM(stok) FROM barang")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static List<Object[]> getBarangStokTipis() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT id_barang, nama_barang, stok FROM barang WHERE stok < 10 ORDER BY stok ASC";
        try (Connection conn = DBConfig.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{ rs.getInt("id_barang"), rs.getString("nama_barang"), rs.getInt("stok") });
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}