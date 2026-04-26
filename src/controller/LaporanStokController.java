package controller;

import view.LaporanStokView;
import model.LaporanStokModel;

import org.jfree.chart.*;
import java.awt.Color;
import java.awt.BorderLayout;

public class LaporanStokController {
    private LaporanStokView view;

    public LaporanStokController(LaporanStokView view) {
        this.view = view;

        view.resetWarna();
        view.cardTotalBarang.setBackground(new Color(243, 232, 255)); 
        refreshDashboardOtomatis();
        tampilkanGrafikDistribusiKategori(); // Grafik default

        // Klik Total Barang -> Grafik Pie Distribusi Kategori
        view.cardTotalBarang.addActionListener(e -> {
            ubahWarnaKartu(view.cardTotalBarang);
            tampilkanGrafikDistribusiKategori();
        });

        // Klik Stok Tipis -> Grafik Batang Barang Paling Sikit
        view.cardStokTipis.addActionListener(e -> {
            ubahWarnaKartu(view.cardStokTipis);
            tampilkanGrafikStokKritis();
        });

        view.cardKategori.addActionListener(e -> {
            ubahWarnaKartu(view.cardKategori);
            tampilkanGrafikKategori();
        });
    }

    public void refreshDashboardOtomatis() {
        // Ambil angka dari Model
        view.lblAngkaTotal.setText(String.valueOf(LaporanStokModel.getTotalUnitBarang()));
        view.lblAngkaTipis.setText(String.valueOf(LaporanStokModel.getJumlahStokTipis()));
        view.lblAngkaKategori.setText(String.valueOf(LaporanStokModel.getTotalKategori()));
    }

    private void tampilkanGrafikDistribusiKategori() {
        render(LaporanStokModel.getGrafikDistribusiKategori());
    }

    private void tampilkanGrafikStokKritis() {
        render(LaporanStokModel.getGrafikStokKritis());
    }

    private void tampilkanGrafikKategori() {
        render(LaporanStokModel.getGrafikKategori());
    }

    // --- HELPER UNTUK UI ---
    private void render(JFreeChart chart) {
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }

    private void ubahWarnaKartu(javax.swing.JButton kartuAktif) {
        view.resetWarna();
        kartuAktif.setBackground(new Color(243, 232, 255));
    }
}