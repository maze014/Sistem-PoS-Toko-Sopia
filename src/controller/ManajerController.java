package controller;

import view.ManajerView;
import view.ManajerDashboardView;
import view.LaporanTransaksiView;
import view.LaporanStokView;

public class ManajerController {
    private ManajerView manajerView;

    public ManajerController(ManajerView manajerView) {
        this.manajerView = manajerView;
        // 1. Tampilkan Dashboard pas pertama kali buka
        ManajerDashboardView dashboardView = new ManajerDashboardView();
        new ManajerDashboardController(dashboardView); 
        this.manajerView.tampilkanHalaman(dashboardView);

        this.manajerView.btnDashboard.addActionListener(e -> {
            new ManajerDashboardController(dashboardView);
            this.manajerView.tampilkanHalaman(dashboardView);
        });

        this.manajerView.btnLaporanTransaksi.addActionListener(e -> {
            LaporanTransaksiView laporanTransaksiView = new LaporanTransaksiView();
            new LaporanTransaksiController(laporanTransaksiView);
            this.manajerView.tampilkanHalaman(laporanTransaksiView);
        });

        this.manajerView.btnLaporanStok.addActionListener(e -> {
            LaporanStokView laporanStokView = new LaporanStokView();
            new LaporanStokController(laporanStokView);
            this.manajerView.tampilkanHalaman(laporanStokView);
        });
    }
}
