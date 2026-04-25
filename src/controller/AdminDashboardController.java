package controller;

import view.AdminDashboardView;
import config.DBConfig;

// Import JFreeChart (Wajib ada Library-nya!)
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.awt.Color;

public class AdminDashboardController {
    private AdminDashboardView view;

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;
        
        // 1. Load angka di kartu saat pertama kali buka
        loadAngkaRingkasan();

        // 2. Tampilkan grafik pertama (Pengguna) sebagai default
        tampilkanGrafikPengguna();
        view.cardPengguna.setBackground(new Color(243, 232, 255)); // Kasih tanda kalau ini yang lagi aktif

        // ==========================================
        // 3. LOGIKA KLIK KARTU (TOMBOL FILTER)
        // ==========================================
        view.cardPengguna.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                view.resetWarnaKartu();
                view.cardPengguna.setBackground(new Color(243, 232, 255));
                tampilkanGrafikPengguna();
            }
        });

        view.cardBarang.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                view.resetWarnaKartu();
                view.cardBarang.setBackground(new Color(243, 232, 255));
                tampilkanGrafikBarang();
            }
        });

        view.cardKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                view.resetWarnaKartu();
                view.cardKategori.setBackground(new Color(243, 232, 255));
                tampilkanGrafikKategori();
            }
        });
    }

    // --- NGAMBIL ANGKA TOTAL BUAT DI KARTU ---
    private void loadAngkaRingkasan() {
        try (Connection conn = DBConfig.getConnection(); Statement st = conn.createStatement()) {
            ResultSet rsUser = st.executeQuery("SELECT COUNT(*) AS total FROM user");
            if (rsUser.next()) view.lblAngkaPengguna.setText(rsUser.getString("total"));

            ResultSet rsBarang = st.executeQuery("SELECT COUNT(*) AS total FROM barang");
            if (rsBarang.next()) view.lblAngkaBarang.setText(rsBarang.getString("total"));

            ResultSet rsKategori = st.executeQuery("SELECT COUNT(*) AS total FROM kategori");
            if (rsKategori.next()) view.lblAngkaKategori.setText(rsKategori.getString("total"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==========================================
    // FUNGSI BIKIN GRAFIK PENGGUNA (PIE CHART)
    // ==========================================
    private void tampilkanGrafikPengguna() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        
        try (Connection conn = DBConfig.getConnection(); 
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT role, COUNT(*) as jumlah FROM user GROUP BY role")) {
            
            while (rs.next()) {
                dataset.setValue(rs.getString("role"), rs.getInt("jumlah"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createPieChart("Distribusi Role Pengguna", dataset, true, true, false);
        pasangGrafikKeLayar(chart);
    }

    // ==========================================
    // FUNGSI BIKIN GRAFIK BARANG (BAR CHART)
    // ==========================================
    private void tampilkanGrafikBarang() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Menampilkan 5 barang dengan stok terbanyak
        String sql = "SELECT nama_barang, stok FROM barang ORDER BY stok DESC LIMIT 5";
        try (Connection conn = DBConfig.getConnection(); 
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                dataset.setValue(rs.getInt("stok"), "Stok", rs.getString("nama_barang"));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        JFreeChart chart = ChartFactory.createBarChart("Top 5 Barang Stok Terbanyak", "Nama Barang", "Jumlah Stok", dataset);
        pasangGrafikKeLayar(chart);
    }

    // ==========================================
    // FUNGSI BIKIN GRAFIK KATEGORI (BAR CHART)
    // ==========================================
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
        pasangGrafikKeLayar(chart);
    }

    // --- Helper buat Refresh Wadah Grafiknya ---
    private void pasangGrafikKeLayar(JFreeChart chart) {
        view.panelWadahGrafik.removeAll(); // Hapus grafik lama
        ChartPanel panelChart = new ChartPanel(chart); // Masukin grafik baru
        view.panelWadahGrafik.add(panelChart, java.awt.BorderLayout.CENTER); // Tempel ke wadah
        
        // Refresh layar biar up to date
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}