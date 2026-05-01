package model;

import config.DBConfig;
import java.sql.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.NumberAxis;

public class AdminDashboardModel {
    public static int getTotalPengguna() {
        try (Connection conn = DBConfig.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM user")) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTotalBarang() {
        try (Connection conn = DBConfig.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM barang")) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTotalKategori() {
        try (Connection conn = DBConfig.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM kategori")) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static JFreeChart getGrafikPengguna() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        try (Connection conn = DBConfig.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT role, COUNT(*) as jumlah FROM user GROUP BY role")) {
            while (rs.next()) {
                dataset.setValue(rs.getString("role"), rs.getInt("jumlah"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ChartFactory.createPieChart("Distribusi Role Pengguna", dataset, true, true, false);
    }

    public static JFreeChart getGrafikBarang() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT nama_barang, stok FROM barang ORDER BY stok DESC LIMIT 5";
        try (Connection conn = DBConfig.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                dataset.setValue(rs.getInt("stok"), "Stok", rs.getString("nama_barang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart("Top 5 Barang Stok Terbanyak", "Nama Barang", "Jumlah Stok", dataset);
    
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return chart;
    }

    public static JFreeChart getGrafikKategori() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT k.nama_kategori, COUNT(b.id_barang) as jumlah " +
                "FROM kategori k LEFT JOIN barang b ON k.id_kategori = b.id_kategori GROUP BY k.id_kategori";
        try (Connection conn = DBConfig.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                dataset.setValue(rs.getInt("jumlah"), "Jumlah Barang", rs.getString("nama_kategori"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart("Jumlah Barang per Kategori", "Kategori", "Total Barang",
                dataset);

        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 

        return chart;
    }
}