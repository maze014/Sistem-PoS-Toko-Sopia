package controller;

import view.LaporanTransaksiView;
import model.LaporanTransaksiModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import java.awt.BorderLayout;

public class LaporanTransaksiController {
    private LaporanTransaksiView view;

    public LaporanTransaksiController(LaporanTransaksiView view) {
        this.view = view;

        loadDataKeLayar(7);

        view.cbFilterWaktu.addActionListener(e -> {
            int index = view.cbFilterWaktu.getSelectedIndex();
            int hari = (index == 0) ? 7 : (index == 1) ? 30 : 365;
            loadDataKeLayar(hari);
        });
    }

    private void loadDataKeLayar(int limitHari) {
        double total = LaporanTransaksiModel.getTotalPendapatan(limitHari);
        view.lblTotalPendapatan.setText("Total: Rp " + String.format("%,.0f", total));

        JFreeChart chart = LaporanTransaksiModel.getGrafikTransaksi(limitHari);
        
        view.panelWadahGrafik.removeAll();
        view.panelWadahGrafik.add(new ChartPanel(chart), BorderLayout.CENTER);
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }
}