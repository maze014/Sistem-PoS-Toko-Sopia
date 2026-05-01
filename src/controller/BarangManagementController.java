package controller;

import model.BarangManagement;
import model.Barang;
import view.BarangManagementView;
import utils.RoundedBorder;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import view.FormBarangView;
import view.EditBarangView;

public class BarangManagementController {
    private BarangManagementView view;
    private String role;

    public BarangManagementController(BarangManagementView view, String roleUser) {
        this.view = view;
        this.role = roleUser;
        refreshData();

        view.btnTambah.addActionListener(e -> {
            FormBarangView form = new FormBarangView((JFrame) SwingUtilities.getWindowAncestor(view));
            new FormBarangController(form);
            form.setVisible(true);
            refreshData();
        });
    }

    public void refreshData() {
        view.panelDaftarBarang.removeAll();
        List<Barang> listBarang = BarangManagement.getAllBarang();

        for (Barang b : listBarang) {
            view.panelDaftarBarang.add(buatCardBarang(b));
            view.panelDaftarBarang.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        view.panelDaftarBarang.revalidate();
        view.panelDaftarBarang.repaint();
    }

    private JPanel buatCardBarang(Barang b) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(new RoundedBorder(15, new Color(200, 200, 200)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        infoPanel.setPreferredSize(new Dimension(250, 60));
        JLabel lblNama = new JLabel(b.getNamaBarang());
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 16));
        // Format harga ala kadarnya dulu, nanti bisa pakai util FormatRupiah
        JLabel lblHarga = new JLabel("Rp " + b.getHarga() + ",00");
        lblHarga.setForeground(Color.GRAY);
        infoPanel.add(lblNama);
        infoPanel.add(lblHarga);

        JPanel stokPanel = new JPanel(new GridBagLayout());
        stokPanel.setOpaque(false);
        JPanel wadahStok = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        wadahStok.setOpaque(false);

        JButton btnMinus = new JButton("-");
        JLabel lblStok = new JLabel("Stok: " + b.getStok());
        lblStok.setFont(new Font("SansSerif", Font.BOLD, 14));
        JButton btnPlus = new JButton("+");

        // tombol stok berkurang
        btnMinus.addActionListener(e -> {
            if (b.getStok() > 0) { // Biar stok gak minus
                boolean sukses = BarangManagement.updateStok(b.getIdBarang(), -1);
                if (sukses) {
                    b.setStok(b.getStok() - 1);
                    lblStok.setText("Stok: " + b.getStok()); 
                }
            } else {
                JOptionPane.showMessageDialog(view, "Stok udah abis Wak, gak bisa dikurangin lagi!");
            }
        });

        // ombol stok bertambah
        btnPlus.addActionListener(e -> {
            boolean sukses = BarangManagement.updateStok(b.getIdBarang(), 1);
            if (sukses) {
                b.setStok(b.getStok() + 1);
                lblStok.setText("Stok: " + b.getStok());
            }
        });

        wadahStok.add(btnMinus);
        wadahStok.add(lblStok);
        wadahStok.add(btnPlus);
        stokPanel.add(wadahStok);

        JPanel aksiPanel = new JPanel(new GridBagLayout());
        aksiPanel.setOpaque(false);
        JPanel wadahTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        wadahTombol.setOpaque(false);

        JButton btnEdit = new JButton("Edit");

        JButton btnDelete = new JButton("Hapus");
        btnDelete.setForeground(Color.RED);
        
        // tombol edit
        btnEdit.addActionListener(e -> {
            EditBarangView editPopUp = new EditBarangView((JFrame) SwingUtilities.getWindowAncestor(view));
            editPopUp.setTitle("Edit Barang: " + b.getNamaBarang());
            editPopUp.txtNamaBarang.setText(b.getNamaBarang());
            editPopUp.txtNamaBarang.setForeground(Color.BLACK);
            editPopUp.txtHarga.setText(String.valueOf(b.getHarga()));
            editPopUp.txtHarga.setForeground(Color.BLACK);
            editPopUp.txtStok.setText(String.valueOf(b.getStok()));
            editPopUp.txtStok.setForeground(Color.BLACK);
            editPopUp.cbKategori.setSelectedItem(b.getTanggalKadaluarsa());
            editPopUp.txtKadaluarsa.setText(String.valueOf(b.getTanggalKadaluarsa()));
            editPopUp.txtKadaluarsa.setForeground(Color.BLACK);

            new EditBarangController(editPopUp, b.getIdBarang()); 
            editPopUp.setVisible(true);
            
            refreshData();
        });
        
        // tombol delete
        btnDelete.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(view,
                "Yakin mau hapus " + b.getNamaBarang() + "?",
                    "Hapus Barang", JOptionPane.YES_NO_OPTION);
                    
                    if (konfirmasi == JOptionPane.YES_OPTION) {
                        boolean berhasil = BarangManagement.deleteBarang(b.getIdBarang());
                        
                if (berhasil) {
                    JOptionPane.showMessageDialog(view,
                        "Barang " + b.getNamaBarang() + " berhasil dimusnahkan!",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    
                        refreshData();
                    } else {
                        JOptionPane.showMessageDialog(view,
                            "Gagal menghapus barang! Cek koneksi database Wak.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        wadahTombol.add(btnEdit);
        wadahTombol.add(btnDelete);
        aksiPanel.add(wadahTombol);
        
        if (role.equalsIgnoreCase("Gudang")) {
                btnDelete.setVisible(false);
            }

        card.add(infoPanel, BorderLayout.WEST);
        card.add(stokPanel, BorderLayout.CENTER);
        card.add(aksiPanel, BorderLayout.EAST);
        
        return card;
    }
}