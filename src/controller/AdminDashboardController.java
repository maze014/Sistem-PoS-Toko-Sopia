package controller;

import view.AdminDashboardView;
import model.AdminDashboardModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class AdminDashboardController {
    private AdminDashboardView view;

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;

        view.resetWarnaKartu();
        view.cardPengguna.setBackground(new Color(243, 232, 255)); // Set default kartu Pengguna aktif
        
        // 1. Load angka di kartu saat pertama kali buka
        refreshDashboardOtomatis();

        // 2. Tampilkan grafik pertama (Pengguna) sebagai default
        tampilkanGrafikPengguna();
        
        // ==========================================
        // 3. LOGIKA KLIK KARTU (TOMBOL FILTER)
        // ==========================================
        view.cardPengguna.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {  
                ubahWarnaKartu(view.cardPengguna);
                tampilkanGrafikPengguna();
            }
        });

        view.cardBarang.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ubahWarnaKartu(view.cardBarang);
                tampilkanGrafikBarang();
            }
        });

        view.cardKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ubahWarnaKartu(view.cardKategori);
                tampilkanGrafikKategori();
            }
        });
    }
    // ==========================================
    // FUNGSI PENGHUBUNG MODEL & VIEW
    // ==========================================
    public void refreshDashboardOtomatis() {
        // Ambil Int dari model, set ke Label
        view.lblAngkaPengguna.setText(String.valueOf(AdminDashboardModel.getTotalPengguna()));
        view.lblAngkaBarang.setText(String.valueOf(AdminDashboardModel.getTotalBarang()));
        view.lblAngkaKategori.setText(String.valueOf(AdminDashboardModel.getTotalKategori()));
    }

    private void tampilkanGrafikPengguna() {
        // Minta grafik dari Model, lalu tempel ke layar
        pasangGrafikKeLayar(AdminDashboardModel.getGrafikPengguna());
    }

    private void tampilkanGrafikBarang() {
        pasangGrafikKeLayar(AdminDashboardModel.getGrafikBarang());
    }

    private void tampilkanGrafikKategori() {
        pasangGrafikKeLayar(AdminDashboardModel.getGrafikKategori());
    }

    // --- HELPER UNTUK UI ---
    private void pasangGrafikKeLayar(JFreeChart chart) {
        view.panelWadahGrafik.removeAll(); 
        view.panelWadahGrafik.add(new ChartPanel(chart), java.awt.BorderLayout.CENTER); 
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }

    private void ubahWarnaKartu(javax.swing.JButton kartuAktif) {
        view.resetWarnaKartu();
        kartuAktif.setBackground(new Color(243, 232, 255));
    }
}