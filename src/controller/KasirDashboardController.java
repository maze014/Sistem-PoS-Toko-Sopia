package controller;

import view.KasirDashboardView;
import model.KasirDashboardModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import java.awt.BorderLayout;

public class KasirDashboardController {
    
    public KasirDashboardController(KasirDashboardView view, int idKasirAktif) {
        // 1. Ambil Angka Ringkasan
        int omzet = KasirDashboardModel.getOmzetKasirHariIni(idKasirAktif);
        int totalTrx = KasirDashboardModel.getTotalTransaksiKasirHariIni(idKasirAktif);

        view.lblOmzet.setText("Rp " + String.format("%,d", omzet));
        view.lblTotalTrx.setText(totalTrx + " Transaksi");

        // 2. Ambil Grafik dan Tempel ke Layar
        JFreeChart chart = KasirDashboardModel.getGrafikPerformaKasir(idKasirAktif);
        
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}