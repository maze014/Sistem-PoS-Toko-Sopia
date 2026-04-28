package controller;

import view.TransaksiView;
import model.TransaksiModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TransaksiController {
    private TransaksiView view;
    private int idKasirAktif;
    private int grandTotal = 0;

    public TransaksiController(TransaksiView view, int idKasirAktif) {
        this.view = view;
        this.idKasirAktif = idKasirAktif;

        // 1. Aksi Tambah Barang ke Keranjang
        view.btnTambah.addActionListener(e -> tambahKeKeranjang());

        // Bisa teken ENTER di kolom Qty buat langsung nambah
        view.txtJumlah.addActionListener(e -> tambahKeKeranjang());

        // 2. Sensor ngetik Uang Bayar (Buat ngitung kembalian otomatis)
        view.txtUangBayar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { hitungKembalian(); }
            public void removeUpdate(DocumentEvent e) { hitungKembalian(); }
            public void changedUpdate(DocumentEvent e) { hitungKembalian(); }
        });

        // 3. Aksi Tombol Proses Bayar
        view.btnBayar.addActionListener(e -> prosesPembayaran());
    }

    private void tambahKeKeranjang() {
        String keyword = view.txtPencarianBarang.getText();
        String qtyStr = view.txtJumlah.getText();

        if (keyword.isEmpty() || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Isi ID/Nama barang dan Qty!");
            return;
        }

        try {
            int qtyBelanja = Integer.parseInt(qtyStr);
            Object[] barang = TransaksiModel.cariBarangUntukKeranjang(keyword);

            if (barang == null) {
                JOptionPane.showMessageDialog(view, "Barang tidak ditemukan!");
                return;
            }

            int idBarang = (int) barang[0];
            String namaBarang = (String) barang[1];
            int hargaJual = (int) barang[2];
            int stokDb = (int) barang[3];

            // Cek apakah stok cukup
            if (stokDb < qtyBelanja) {
                JOptionPane.showMessageDialog(view, "Stok tidak cukup! Sisa stok: " + stokDb);
                return;
            }

            int subtotal = hargaJual * qtyBelanja;

            // Masukkan ke tabel keranjang
            view.modelKeranjang.addRow(new Object[]{idBarang, namaBarang, hargaJual, qtyBelanja, subtotal});
            
            // Hitung ulang Grand Total
            hitungGrandTotal();
            
            // Bersihkan inputan biar kasir bisa scan barang selanjutnya
            view.txtPencarianBarang.setText("");
            view.txtJumlah.setText("1");
            view.txtPencarianBarang.requestFocus(); // Balikin kursor ke kolom nama

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Qty harus angka bulat!");
        }
    }

    private void hitungGrandTotal() {
        grandTotal = 0;
        for (int i = 0; i < view.modelKeranjang.getRowCount(); i++) {
            grandTotal += (int) view.modelKeranjang.getValueAt(i, 4);
        }
        view.lblTotalHarga.setText("Rp " + String.format("%,d", grandTotal));
        hitungKembalian(); // Update kembalian juga kalau ada perubahan total
    }

    private void hitungKembalian() {
        try {
            String uangStr = view.txtUangBayar.getText().replace(".", "").replace(",", "");
            if (uangStr.isEmpty()) {
                view.lblKembalian.setText("Kembalian: Rp 0");
                return;
            }
            int uangBayar = Integer.parseInt(uangStr);
            int kembalian = uangBayar - grandTotal;
            view.lblKembalian.setText("Kembalian: Rp " + String.format("%,d", kembalian));
        } catch (NumberFormatException e) {
            view.lblKembalian.setText("Kembalian: Rp 0");
        }
    }

    private void prosesPembayaran() {
        if (view.modelKeranjang.getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Keranjang masih kosong!");
            return;
        }

        try {
            int uangBayar = Integer.parseInt(view.txtUangBayar.getText().replace(".", "").replace(",", ""));
            
            if (uangBayar < grandTotal) {
                JOptionPane.showMessageDialog(view, "Uang pelanggan kurang!");
                return;
            }

            // PANGGIL DATABASE TRANSACTION
            boolean sukses = TransaksiModel.prosesCheckout(idKasirAktif, view.modelKeranjang, grandTotal, uangBayar);

            if (sukses) {
                int kembalian = uangBayar - grandTotal;
                JOptionPane.showMessageDialog(view, "Transaksi Berhasil!\nKembalian: Rp " + String.format("%,d", kembalian));
                
                // RESET SEMUA SETELAH BERHASIL
                view.modelKeranjang.setRowCount(0);
                hitungGrandTotal();
                view.txtUangBayar.setText("");
            } else {
                JOptionPane.showMessageDialog(view, "Terjadi kesalahan saat menyimpan transaksi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Masukkan jumlah uang yang valid!");
        }
    }
}