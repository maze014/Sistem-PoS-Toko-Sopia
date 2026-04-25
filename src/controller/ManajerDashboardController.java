package controller;

import view.ManajerDashboardView;
import config.DBConfig;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.sql.*;
import java.awt.BorderLayout;
import java.awt.Color;


public class ManajerDashboardController {
    private ManajerDashboardView view;

    public ManajerDashboardController(ManajerDashboardView view) {
        this.view = view;
        loadSummary();
        tampilkanLaporanOmzet(); // Default awal

        // Klik Omzet -> Grafik Garis (Tren Penjualan)
        view.cardOmzet.addActionListener(e -> {
            view.resetWarna();
            view.cardOmzet.setBackground(new Color(243, 232, 255));
            tampilkanLaporanOmzet();
        });

        // Klik Transaksi -> Grafik Batang (Perbandingan Harian)
        view.cardTransaksi.addActionListener(e -> {
            view.resetWarna();
            view.cardTransaksi.setBackground(new Color(243, 232, 255));
            tampilkanLaporanTransaksi();
        });

        // Klik Stok -> Grafik Pie (Stok Menipis)
        view.cardStok.addActionListener(e -> {
            view.resetWarna();
            view.cardStok.setBackground(new Color(243, 232, 255));
            tampilkanLaporanStok();
        });
    }

    private void loadSummary() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement()) {
            // 1. Hitung Omzet Bulan Ini
            ResultSet rsOmzet = st.executeQuery("SELECT SUM(total_harga) FROM transaksi WHERE MONTH(tanggal) = MONTH(CURRENT_DATE)");
            if (rsOmzet.next()) view.lblAngkaOmzet.setText("Rp " + String.format("%,d", rsOmzet.getInt(1)));

            // 2. Total Transaksi Hari Ini
            ResultSet rsTrans = st.executeQuery("SELECT COUNT(*) FROM transaksi WHERE DATE(tanggal) = CURRENT_DATE");
            if (rsTrans.next()) view.lblAngkaTransaksi.setText(rsTrans.getString(1));

            // 3. Stok Menipis (di bawah 10)
            ResultSet rsStok = st.executeQuery("SELECT COUNT(*) FROM barang WHERE stok < 10");
            if (rsStok.next()) view.lblAngkaStok.setText(rsStok.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void tampilkanLaporanOmzet() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT DATE(tanggal), SUM(total_harga) FROM transaksi GROUP BY DATE(tanggal) LIMIT 7";
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) dataset.addValue(rs.getDouble(2), "Pendapatan", rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createLineChart("Tren Pendapatan 7 Hari Terakhir", "Tanggal", "Rupiah", dataset);
        renderChart(chart);
    }

    private void tampilkanLaporanTransaksi() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT DATE(tanggal), COUNT(*) FROM transaksi GROUP BY DATE(tanggal) LIMIT 7";
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) dataset.addValue(rs.getInt(2), "Transaksi", rs.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart("Volume Transaksi Harian", "Tanggal", "Jumlah Transaksi", dataset, PlotOrientation.VERTICAL, true, true, false);
        renderChart(chart);
    }

    private void tampilkanLaporanStok() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        String sql = "SELECT nama_barang, stok FROM barang WHERE stok < 20 ORDER BY stok ASC LIMIT 5";
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) dataset.setValue(rs.getString(1) + " (" + rs.getInt(2) + ")", rs.getInt(2));
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createPieChart("5 Barang dengan Stok Terendah", dataset, true, true, false);
        renderChart(chart);
    }

    private void renderChart(JFreeChart chart) {
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}