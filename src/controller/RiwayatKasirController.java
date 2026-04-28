package controller;

import view.RiwayatKasirView;
import model.RiwayatKasirModel;
import javax.swing.*;
import java.util.List;
import java.util.Map;

public class RiwayatKasirController {
    private RiwayatKasirView view;
    private int idKasir;

    public RiwayatKasirController(RiwayatKasirView view, int idKasir) {
        this.view = view;
        this.idKasir = idKasir;

        // 1. Jalankan render pertama kali saat dibuka
        renderRiwayat();

        // 2. SENSOR OTOMATIS: Refresh tiap kali halaman Riwayat ini muncul di layar
        view.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                renderRiwayat();
            }
        });
    }

    // Fungsi ini sekarang jadi pusat refresh data
    public void renderRiwayat() {
        // Hapus elemen lama di wadah yang baru
        view.panelDaftarRiwayat.removeAll();
        
        List<Map<String, Object>> data = RiwayatKasirModel.getDataRiwayat(idKasir);

        for (Map<String, Object> trx : data) {
            int id = (int) trx.get("id");
            String tgl = (String) trx.get("tanggal");
            String total = "Rp " + String.format("%,d", (int) trx.get("total"));
            
            // PAKAI ROUNDED BUTTON! (Tingkat lengkungan sesuaikan, misal 15)
            utils.RoundedButton btnCetak = new utils.RoundedButton("Cetak Struk", 15);
            
            // Logika Tombol Cetak
            btnCetak.addActionListener(e -> {
                String detail = RiwayatKasirModel.getDetailStruk(id);
                String strukFinal = "=== SOPIA POS ===\n" +
                                   "ID: #" + id + "\n" +
                                   "Tgl: " + tgl + "\n" +
                                   "-----------------\n" +
                                   detail +
                                   "-----------------\n" +
                                   "Total: " + total + "\n" +
                                   "Terima Kasih!";
                
                JOptionPane.showMessageDialog(view, strukFinal, "Cetak Struk", JOptionPane.INFORMATION_MESSAGE);
            });

            // Tempel ke panelDaftarRiwayat
            JPanel card = view.buatCard(id, tgl, total, btnCetak);
            view.panelDaftarRiwayat.add(card);
        }

        // Gambar ulang UI-nya
        view.panelDaftarRiwayat.revalidate();
        view.panelDaftarRiwayat.repaint();
    }
}