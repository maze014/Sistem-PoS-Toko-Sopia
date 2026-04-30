package model;

import config.DBConfig;
import java.sql.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ManajerDashboardModel {
    public static int getOmzetBulanIni() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT SUM(total_pembayaran) FROM transaksi WHERE MONTH(tanggal) = MONTH(CURRENT_DATE)")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getTotalTransaksiHariIni() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM transaksi WHERE MONTH(tanggal) = MONTH(CURRENT_DATE)")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getStokMenipis() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM barang WHERE stok < 10")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static JFreeChart getGrafikOmzet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT DATE(tanggal), SUM(total_pembayaran) FROM transaksi WHERE DATE(tanggal) >= CURDATE() - INTERVAL 6 DAY GROUP BY tanggal ORDER BY tanggal ASC";
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) dataset.addValue(rs.getDouble(2), "Pendapatan", rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }

        return ChartFactory.createLineChart("Tren Pendapatan 7 Hari Terakhir", "Tanggal", "Rupiah", dataset);
    }

    public static JFreeChart getGrafikTransaksi() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT DATE(tanggal), COUNT(*) FROM transaksi WHERE DATE(tanggal) >= CURDATE() - INTERVAL 6 DAY GROUP BY DATE(tanggal) ORDER BY DATE(tanggal) ASC";
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) dataset.addValue(rs.getInt(2), "Transaksi", rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart("Volume Transaksi Harian", "Tanggal", "Jumlah Transaksi", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        return chart;
    }

    public static JFreeChart getGrafikStok() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        String sql = "SELECT nama_barang, stok FROM barang WHERE stok < 10 ORDER BY stok ASC LIMIT 5";
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) dataset.setValue(rs.getString(1) + " (" + rs.getInt(2) + ")", rs.getInt(2));
        } catch (SQLException e) { e.printStackTrace(); }

        return ChartFactory.createPieChart("5 Barang dengan Stok Terendah", dataset, true, true, false);
    }
}