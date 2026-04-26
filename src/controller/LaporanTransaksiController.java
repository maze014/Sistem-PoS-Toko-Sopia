package controller;

import view.LaporanTransaksiView;
import model.LaporanTransaksiModel; // Import Model baru
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import java.awt.BorderLayout;

public class LaporanTransaksiController {
    private LaporanTransaksiView view;

    public LaporanTransaksiController(LaporanTransaksiView view) {
        this.view = view;

        // Tampilkan awal
        loadDataKeLayar(7);

        // Filter Dropdown
        view.cbFilterWaktu.addActionListener(e -> {
            int index = view.cbFilterWaktu.getSelectedIndex();
            int hari = (index == 0) ? 7 : (index == 1) ? 30 : 365;
            loadDataKeLayar(hari);
        });
    }

    private void loadDataKeLayar(int limitHari) {
        // 1. Ambil Angka Total dari Model
        double total = LaporanTransaksiModel.getTotalPendapatan(limitHari);
        view.lblTotalPendapatan.setText("Total: Rp " + String.format("%,.0f", total));

        // 2. Ambil Grafik dari Model
        JFreeChart chart = LaporanTransaksiModel.getGrafikTransaksi(limitHari);
        
        // 3. Tempel ke View
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}