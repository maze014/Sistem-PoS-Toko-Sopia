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

public class LaporanStokModel {
    public static int getTotalUnitBarang() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT SUM(stok) FROM barang")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getJumlahStokTipis() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM barang WHERE stok < 10")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getTotalKategori() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM kategori")) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static JFreeChart getGrafikDistribusiKategori() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        String sql = "SELECT k.nama_kategori, SUM(b.stok) as total_stok " +
                     "FROM kategori k JOIN barang b ON k.id_kategori = b.id_kategori " +
                     "GROUP BY k.nama_kategori";
                     
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                dataset.setValue(rs.getString("nama_kategori"), rs.getInt("total_stok"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return ChartFactory.createPieChart("Distribusi Stok Per Kategori", dataset, true, true, false);
    }

    public static JFreeChart getGrafikStokKritis() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT nama_barang, stok FROM barang WHERE stok < 15 ORDER BY stok ASC LIMIT 10";
        
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                dataset.addValue(rs.getInt("stok"), "Stok", rs.getString("nama_barang"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart("Barang dengan Stok Kritis", "Nama Barang", "Sisa Stok", dataset, PlotOrientation.VERTICAL, false, true, false);
        
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        return chart;
    }

    public static JFreeChart getGrafikKategori() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT k.nama_kategori, COUNT(b.id_barang) as jumlah " +
                     "FROM kategori k LEFT JOIN barang b ON k.id_kategori = b.id_kategori " +
                     "GROUP BY k.id_kategori";
                     
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                dataset.setValue(rs.getInt("jumlah"), "Jumlah Barang", rs.getString("nama_kategori"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart("Jumlah Barang per Kategori", "Kategori", "Total Barang", dataset, PlotOrientation.VERTICAL, true, true, false);
        
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        return chart;
    }
}