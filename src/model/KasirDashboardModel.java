package model;

import config.DBConfig;
import java.sql.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class KasirDashboardModel {

    public static int getOmzetKasirHariIni(int idKasir) {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT SUM(total_pembayaran) FROM transaksi WHERE DATE(tanggal) = CURDATE() AND id_user = " + idKasir)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getTotalTransaksiKasirHariIni(int idKasir) {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM transaksi WHERE DATE(tanggal) = CURDATE() AND id_user = " + idKasir)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // ==========================================
    // FUNGSI BARU: GRAFIK PERFORMA KASIR
    // ==========================================
    public static JFreeChart getGrafikPerformaKasir(int idKasir) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Tarik data 7 hari terakhir KHUSUS untuk kasir ini
        String sql = "SELECT DATE(`tanggal`) as tgl, SUM(total_pembayaran) as pendapatan " +
                     "FROM transaksi " +
                     "WHERE id_user = ? AND DATE(`tanggal`) >= CURDATE() - INTERVAL 6 DAY " +
                     "GROUP BY tgl ORDER BY tgl ASC";
                     
        try (Connection conn = DBConfig.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idKasir); // Kunci ID Kasir!
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                dataset.addValue(rs.getDouble("pendapatan"), "Penjualan Saya", rs.getString("tgl"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return ChartFactory.createLineChart(
            "Performa Penjualan Saya (7 Hari Terakhir)", 
            "Tanggal", 
            "Total Omzet (Rp)", 
            dataset, 
            PlotOrientation.VERTICAL, 
            false, true, false
        );
    }
}