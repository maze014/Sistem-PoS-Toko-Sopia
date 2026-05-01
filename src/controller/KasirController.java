package controller;

import view.KasirView;
import view.KasirDashboardView;
import view.RiwayatKasirView;
import view.StokBarangView;
import view.TransaksiView;

public class KasirController {
    private KasirView view;

    public KasirController(KasirView kasirView) {
        this.view = kasirView;
        KasirDashboardView dashView = new KasirDashboardView();
        new KasirDashboardController(dashView, view.idUserAktif);
        view.tampilkanHalaman(dashView);

        view.btnDashboard.addActionListener(e -> {
            new KasirDashboardController(dashView, view.idUserAktif);
            view.tampilkanHalaman(dashView);
        });

        view.btnRiwayat.addActionListener(e -> {
            RiwayatKasirView riwayatView = new RiwayatKasirView();
            new RiwayatKasirController(riwayatView, view.idUserAktif);
            view.tampilkanHalaman(riwayatView);
        });

        view.btnStok.addActionListener(e -> {
            StokBarangView stokView = new StokBarangView();
            new StokBarangController(stokView);
            view.tampilkanHalaman(stokView);
        });

        view.btnTransaksi.addActionListener(e -> {
            TransaksiView transaksiView = new TransaksiView();
            new TransaksiController(transaksiView, view.idUserAktif);
            view.tampilkanHalaman(transaksiView);
        });
    }

}
