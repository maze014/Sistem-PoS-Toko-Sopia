package controller;

import view.GudangView;
import view.GudangDashboardView;
import view.BarangManagementView;

public class GudangController {
    private GudangView viewUtama;

    public GudangController(GudangView viewUtama) {
        this.viewUtama = viewUtama;

        GudangDashboardView viewDashboard = new GudangDashboardView();
        new GudangDashboardController(viewDashboard);
        this.viewUtama.tampilkanHalaman(viewDashboard);

        this.viewUtama.btnDashboard.addActionListener(e -> {
            new GudangDashboardController(viewDashboard);
            this.viewUtama.tampilkanHalaman(viewDashboard);
        });

        this.viewUtama.btnManageBarang.addActionListener(e -> {
            BarangManagementView viewBarang = new BarangManagementView();
            new BarangManagementController(viewBarang, "Gudang");
            this.viewUtama.tampilkanHalaman(viewBarang);    
        });
    }
}