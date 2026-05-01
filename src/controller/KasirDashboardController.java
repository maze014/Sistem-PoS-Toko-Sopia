package controller;

import view.KasirDashboardView;
import model.KasirDashboardModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import java.awt.BorderLayout;

public class KasirDashboardController {
    
    public KasirDashboardController(KasirDashboardView view, int idKasirAktif) {
        int omzet = KasirDashboardModel.getOmzetKasirHariIni(idKasirAktif);
        int totalTrx = KasirDashboardModel.getTotalTransaksiKasirHariIni(idKasirAktif);

        view.lblOmzet.setText("Rp " + String.format("%,d", omzet));
        view.lblTotalTrx.setText(totalTrx + " Transaksi");

        JFreeChart chart = KasirDashboardModel.getGrafikPerformaKasir(idKasirAktif);
        
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}