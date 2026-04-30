package model;

import config.DBConfig;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LaporanTransaksiModel {
    public static double getTotalPendapatan(int limitHari) {
        double total = 0;
        String sql = "SELECT SUM(total_pembayaran) FROM transaksi " +
                     "WHERE DATE(tanggal) >= CURDATE() - INTERVAL " + (limitHari - 1) + " DAY";

        try (Connection conn = DBConfig.getConnection(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static JFreeChart getGrafikTransaksi(int limitHari) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        String sql = "SELECT DATE(tanggal) as tgl, SUM(total_pembayaran) as pendapatan " +
                     "FROM transaksi " +
                     "WHERE DATE(tanggal) >= CURDATE() - INTERVAL " + (limitHari - 1) + " DAY " +
                     "GROUP BY tanggal ORDER BY tanggal ASC";

        try (Connection conn = DBConfig.getConnection(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                dataset.addValue(rs.getDouble("pendapatan"), "Pendapatan", rs.getString("tgl"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ChartFactory.createLineChart(
            "Tren Pendapatan (" + limitHari + " Hari Terakhir)", 
            "Tanggal", 
            "Total Pendapatan (Rp)", 
            dataset, 
            PlotOrientation.VERTICAL, 
            false, true, false
        );
    }
}