package controller;

import model.BarangManagement;
import model.Kategori;
import view.EditBarangView;
import javax.swing.*;
import java.util.List;

public class EditBarangController {
    private EditBarangView view;
    private int idBarangLama;

    public EditBarangController(EditBarangView view, int idBarangLama) {
        this.view = view;
        this.idBarangLama = idBarangLama;
        loadKategori();

        this.view.btnSimpan.addActionListener(e -> simpanBarang());
    }

    private void loadKategori() {
        List<Kategori> list = Kategori.getAllKategori();
        for (Kategori k : list) {
            view.cbKategori.addItem(k);
        }
    }

    private void simpanBarang() {
        String nama = view.txtNamaBarang.getText();
        String hargaString = view.txtHarga.getText();
        String stokString = view.txtStok.getText();
        Kategori kat = (Kategori) view.cbKategori.getSelectedItem();
        String tgl = view.txtKadaluarsa.getText();

        if (nama.isEmpty() || nama.equals("Contoh: Keripik Singkong Pedas") || hargaString.isEmpty()
                || hargaString.equals("Misal: 5000")
                || stokString.isEmpty() || stokString.equals("Misal: 50") || tgl.isEmpty()
                || tgl.equals("YYYY-MM-DD (Misal: 2026-12-31)"))
        {
            JOptionPane.showMessageDialog(view, "Semua field wajib diisi, Cok!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int harga = Integer.parseInt(hargaString);
            int stok = Integer.parseInt(stokString);

            java.sql.Date tglKadaluarsa = java.sql.Date.valueOf(tgl);

            boolean sukses = BarangManagement.updateBarang(kat.getIdKategori(), nama, harga, stok, tglKadaluarsa, idBarangLama);

            if (sukses) {
                JOptionPane.showMessageDialog(view, "Barang Berhasil Diedit!");
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal mengedit barang. Cek koneksi atau data yang dimasukkan.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Inputan Salah Wak! Cek harganya atau stoknya.");
        }
    }
}