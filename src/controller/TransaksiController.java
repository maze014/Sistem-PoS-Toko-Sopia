package controller;

import view.TransaksiView;
import model.TransaksiModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiController {
    private TransaksiView view;
    private int idKasirAktif;
    private int grandTotal = 0;
    private boolean sedangMilihDariDropdown = false; 
    private List<Object[]> listKeranjang = new ArrayList<>();

    public TransaksiController(TransaksiView view, int idKasirAktif) {
        this.view = view;
        this.idKasirAktif = idKasirAktif;

        view.txtPencarianBarang.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { if(!sedangMilihDariDropdown) updateLiveSearch(); }
            public void removeUpdate(DocumentEvent e) { if(!sedangMilihDariDropdown) updateLiveSearch(); }
            public void changedUpdate(DocumentEvent e) { if(!sedangMilihDariDropdown) updateLiveSearch(); }
        });

        view.btnTambah.addActionListener(e -> tambahKeKeranjang());
        view.txtJumlah.addActionListener(e -> tambahKeKeranjang());
        view.txtUangBayar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { hitungKembalian(); }
            public void removeUpdate(DocumentEvent e) { hitungKembalian(); }
            public void changedUpdate(DocumentEvent e) { hitungKembalian(); }
        });

        view.btnBayar.addActionListener(e -> prosesPembayaran());
    }

    private void updateLiveSearch() {
        String keyword = view.txtPencarianBarang.getText();
        view.popupPencarian.removeAll();

        if (keyword.isEmpty()) {
            view.popupPencarian.setVisible(false);
            return;
        }

        List<Object[]> hasil = TransaksiModel.cariBarangLive(keyword);
        for (Object[] b : hasil) {
            String teksMenu = b[1] + " (Sisa: " + b[3] + ") - Rp" + b[2];
            
            JLabel item = new JLabel(teksMenu);
            item.setOpaque(true);
            item.setBackground(Color.WHITE);
            item.setFont(new Font("SansSerif", Font.PLAIN, 14));
            item.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 10));
            item.setCursor(new Cursor(Cursor.HAND_CURSOR));
            item.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    item.setBackground(new Color(200, 230, 255)); 
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    item.setBackground(Color.WHITE);
                }
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    sedangMilihDariDropdown = true;

                    view.txtPencarianBarang.setText(b[1].toString()); 
                    view.popupPencarian.setVisible(false);
                    view.txtJumlah.requestFocus();
     
                    SwingUtilities.invokeLater(() -> sedangMilihDariDropdown = false);
                }
            });
            view.popupPencarian.add(item);
        }

        if (hasil.size() > 0) {
            view.popupPencarian.show(view.txtPencarianBarang, 0, view.txtPencarianBarang.getHeight());
            view.txtPencarianBarang.requestFocus();
        } else {
            view.popupPencarian.setVisible(false);
        }
    }

    private void tambahKeKeranjang() {
        String keyword = view.txtPencarianBarang.getText();
        String qtyStr = view.txtJumlah.getText();

        if (keyword.isEmpty() || qtyStr.isEmpty()) return;

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

            if (stokDb < qtyBelanja) {
                JOptionPane.showMessageDialog(view, "Stok tidak cukup! Sisa stok: " + stokDb);
                return;
            }

            int subtotal = hargaJual * qtyBelanja;
            listKeranjang.add(new Object[]{idBarang, namaBarang, hargaJual, qtyBelanja, subtotal});

            renderKeranjangUI();
            
            sedangMilihDariDropdown = true;
            view.txtPencarianBarang.setText("");
            view.txtJumlah.setText("1");
            SwingUtilities.invokeLater(() -> sedangMilihDariDropdown = false);
            
            view.txtPencarianBarang.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Qty harus angka!");
        }
    }

    private void renderKeranjangUI() {
        view.panelDaftarKeranjang.removeAll();
        grandTotal = 0;

        for (int i = 0; i < listKeranjang.size(); i++) {
            Object[] item = listKeranjang.get(i);
            String nama = (String) item[1];
            int harga = (int) item[2];
            int qty = (int) item[3];
            int sub = (int) item[4];

            final int index = i; 
            JPanel card = view.buatCardKeranjang(nama, qty, harga, sub, e -> {
                listKeranjang.remove(index); 
                renderKeranjangUI(); 
            });

            view.panelDaftarKeranjang.add(card);
            grandTotal += sub;
        }

        view.lblTotalHarga.setText("Rp " + String.format("%,d", grandTotal));
        hitungKembalian();

        view.panelDaftarKeranjang.revalidate();
        view.panelDaftarKeranjang.repaint();
    }

    private void hitungKembalian() {
        try {
            String uangStr = view.txtUangBayar.getText().replace(".", "").replace(",", "");
            if (uangStr.isEmpty()) { view.lblKembalian.setText("Kembalian: Rp 0"); return; }
            
            int uangBayar = Integer.parseInt(uangStr);
            int kembalian = uangBayar - grandTotal;
            view.lblKembalian.setText("Kembalian: Rp " + String.format("%,d", kembalian));
        } catch (NumberFormatException e) {
            view.lblKembalian.setText("Kembalian: Rp 0");
        }
    }

    private void prosesPembayaran() {
        if (listKeranjang.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keranjang kosong!");
            return;
        }
        try {
            int uangBayar = Integer.parseInt(view.txtUangBayar.getText().replace(".", "").replace(",", ""));
            if (uangBayar < grandTotal) {
                JOptionPane.showMessageDialog(view, "Uang kurang!");
                return;
            }

            boolean sukses = TransaksiModel.prosesCheckout(idKasirAktif, listKeranjang, grandTotal, uangBayar);

            if (sukses) {
                int kembalian = uangBayar - grandTotal;
                JOptionPane.showMessageDialog(view, "Berhasil!\nKembalian: Rp " + String.format("%,d", kembalian));
                
                listKeranjang.clear();
                renderKeranjangUI();
                view.txtUangBayar.setText("");
            } else {
                JOptionPane.showMessageDialog(view, "Gagal simpan transaksi!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Masukkan uang valid!");
        }
    }
}