package controller;

import view.ManajerDashboardView;
import org.jfree.chart.*;
import java.awt.BorderLayout;
import java.awt.Color;
import model.ManajerDashboardModel;


public class ManajerDashboardController {
    private ManajerDashboardView view;

    public ManajerDashboardController(ManajerDashboardView view) {
        this.view = view;

        view.resetWarna();
        view.cardOmzet.setBackground(new Color(243, 232, 255)); 
        refreshDashboardOtomatis();
        tampilkanLaporanOmzet();

        view.cardOmzet.addActionListener(e -> {
            ubahWarnaKartu(view.cardOmzet);
            tampilkanLaporanOmzet();
        });

        view.cardTransaksi.addActionListener(e -> {
            ubahWarnaKartu(view.cardTransaksi);
            tampilkanLaporanTransaksi();
        });

        view.cardStok.addActionListener(e -> {
            ubahWarnaKartu(view.cardStok);
            tampilkanLaporanStok();
        });
    }

    public void refreshDashboardOtomatis() {
        int omzet = ManajerDashboardModel.getOmzetBulanIni();
        view.lblAngkaOmzet.setText("Rp " + String.format("%,d", omzet));
        
        view.lblAngkaTransaksi.setText(String.valueOf(ManajerDashboardModel.getTotalTransaksiHariIni()));
        view.lblAngkaStok.setText(String.valueOf(ManajerDashboardModel.getStokMenipis()));
    }

    private void tampilkanLaporanOmzet() {
        renderChart(ManajerDashboardModel.getGrafikOmzet());
    }

    private void tampilkanLaporanTransaksi() {
        renderChart(ManajerDashboardModel.getGrafikTransaksi());
    }

    private void tampilkanLaporanStok() {
        renderChart(ManajerDashboardModel.getGrafikStok());
    }

    private void renderChart(JFreeChart chart) {
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