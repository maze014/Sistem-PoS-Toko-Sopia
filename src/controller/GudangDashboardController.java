package controller;

import view.GudangDashboardView;
import model.GudangDashboardModel;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

public class GudangDashboardController {
    private GudangDashboardView view;

    public GudangDashboardController(GudangDashboardView view) {
        this.view = view;
        refreshDashboard();
        view.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                refreshDashboard();
            }
        });
    }

    public void refreshDashboard() {
        int macamBarang = GudangDashboardModel.getTotalMacamBarang();
        int totalStok = GudangDashboardModel.getTotalStokFisik();
        
        view.lblMacamBarang.setText(String.format("%,d", macamBarang));
        view.lblTotalStok.setText(String.format("%,d", totalStok));

        view.panelDaftarTipis.removeAll();
        List<Object[]> barangKritis = GudangDashboardModel.getBarangStokTipis();
        
        view.lblTotalTipis.setText(String.valueOf(barangKritis.size()));

        if (barangKritis.isEmpty()) {
            JLabel lblAman = new JLabel("Mantap! Stok gudang aman terkendali. Tidak ada barang kritis.");
            lblAman.setFont(new Font("SansSerif", Font.ITALIC, 16));
            lblAman.setForeground(Color.GRAY);
            view.panelDaftarTipis.add(lblAman);
        } else {
            for (Object[] b : barangKritis) {
                int id = (int) b[0];
                String nama = (String) b[1];
                int stok = (int) b[2];
                view.panelDaftarTipis.add(view.buatCardStokTipis(id, nama, stok));
            }
        }

        view.panelDaftarTipis.revalidate();
        view.panelDaftarTipis.repaint();
    }
}