package controller;

import view.LaporanTransaksiView;
import config.DBConfig;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;

public class LaporanTransaksiController {
    private LaporanTransaksiView view;

    public LaporanTransaksiController(LaporanTransaksiView view) {
        this.view = view;

        // Tampilkan grafik awal (7 Hari Terakhir)
        loadGrafikTransaksi(7);

        // Pasang pendengar di Dropdown Filter
        view.cbFilterWaktu.addActionListener(e -> {
            int index = view.cbFilterWaktu.getSelectedIndex();
            if (index == 0) loadGrafikTransaksi(7);       // 7 Hari
            else if (index == 1) loadGrafikTransaksi(30); // 30 Hari
            else if (index == 2) loadGrafikTransaksi(365);// 1 Tahun
        });
    }

    private void loadGrafikTransaksi(int limitHari) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double totalPendapatan = 0;

        // Query SQL dinamis berdasarkan jumlah hari
        String sql = "SELECT DATE(tanggal) as tgl, SUM(total_harga) as pendapatan " +
                     "FROM transaksi " +
                     "WHERE tanggal >= DATE_SUB(CURRENT_DATE, INTERVAL " + limitHari + " DAY) " +
                     "GROUP BY DATE(tanggal) ORDER BY tgl ASC";

        try (Connection conn = DBConfig.getConnection(); 
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                double pendapatanHarian = rs.getDouble("pendapatan");
                String tanggal = rs.getString("tgl");
                
                // Masukkan data ke grafik
                dataset.addValue(pendapatanHarian, "Pendapatan", tanggal);
                
                // Hitung total buat ditaruh di pojok kanan atas
                totalPendapatan += pendapatanHarian; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update teks Total Pendapatan
        view.lblTotalPendapatan.setText("Total: Rp " + String.format("%,.0f", totalPendapatan));

        // Buat Grafik Garis (Line Chart) biar kelihatan naik turunnya
        JFreeChart chart = ChartFactory.createLineChart(
            "Tren Pendapatan (" + limitHari + " Hari Terakhir)", 
            "Tanggal", 
            "Total Pendapatan (Rp)", 
            dataset, 
            PlotOrientation.VERTICAL, 
            false, true, false
        );

        // Tempel ke layar
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), java.awt.BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}