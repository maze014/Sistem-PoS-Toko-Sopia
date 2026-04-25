package controller;

import view.LaporanStokView;
import config.DBConfig;
import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;
import java.sql.*;
import java.awt.Color;
import java.awt.BorderLayout;

public class LaporanStokController {
    private LaporanStokView view;

    public LaporanStokController(LaporanStokView view) {
        this.view = view;
        loadSummary();
        tampilkanGrafikDistribusiKategori(); // Grafik default

        // Klik Total Barang -> Grafik Pie Distribusi Kategori
        view.cardTotalBarang.addActionListener(e -> {
            view.resetWarna();
            view.cardTotalBarang.setBackground(new Color(243, 232, 255));
            tampilkanGrafikDistribusiKategori();
        });

        // Klik Stok Tipis -> Grafik Batang Barang Paling Sikit
        view.cardStokTipis.addActionListener(e -> {
            view.resetWarna();
            view.cardStokTipis.setBackground(new Color(243, 232, 255));
            tampilkanGrafikStokKritis();
        });

        view.cardKategori.addActionListener(e -> {
            view.resetWarna();
            view.cardKategori.setBackground(new Color(243, 232, 255));
            tampilkanGrafikKategori();
        });
    }

    private void loadSummary() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement()) {
            // 1. Total Unit Barang
            ResultSet rsTotal = st.executeQuery("SELECT SUM(stok) FROM barang");
            if (rsTotal.next()) view.lblAngkaTotal.setText(String.valueOf(rsTotal.getInt(1)));

            // 2. Jumlah Barang Stok < 10
            ResultSet rsTipis = st.executeQuery("SELECT COUNT(*) FROM barang WHERE stok < 10");
            if (rsTipis.next()) view.lblAngkaTipis.setText(rsTipis.getString(1));

            // 3. Total Kategori
            ResultSet rsKat = st.executeQuery("SELECT COUNT(*) FROM kategori");
            if (rsKat.next()) view.lblAngkaKategori.setText(rsKat.getString(1));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void tampilkanGrafikDistribusiKategori() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        String sql = "SELECT k.nama_kategori, SUM(b.stok) as total_stok " +
                     "FROM kategori k JOIN barang b ON k.id_kategori = b.id_kategori " +
                     "GROUP BY k.nama_kategori";
        
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                dataset.setValue(rs.getString("nama_kategori"), rs.getInt("total_stok"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createPieChart("Distribusi Stok Per Kategori", dataset, true, true, false);
        render(chart);
    }

    private void tampilkanGrafikStokKritis() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT nama_barang, stok FROM barang WHERE stok < 15 ORDER BY stok ASC LIMIT 10";
        
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                dataset.addValue(rs.getInt("stok"), "Stok", rs.getString("nama_barang"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart("Barang dengan Stok Kritis", "Nama Barang", "Sisa Stok", dataset, PlotOrientation.VERTICAL, false, true, false);
        render(chart);
    }

     private void tampilkanGrafikKategori() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Menghitung jumlah barang per kategori pakai JOIN
        String sql = "SELECT k.nama_kategori, COUNT(b.id_barang) as jumlah " +
                     "FROM kategori k LEFT JOIN barang b ON k.id_kategori = b.id_kategori " +
                     "GROUP BY k.id_kategori";
                     
        try (Connection conn = DBConfig.getConnection(); 
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                dataset.setValue(rs.getInt("jumlah"), "Jumlah Barang", rs.getString("nama_kategori"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart("Jumlah Barang per Kategori", "Kategori", "Total Barang", dataset);
        render(chart);
    }

    private void render(JFreeChart chart) {
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}