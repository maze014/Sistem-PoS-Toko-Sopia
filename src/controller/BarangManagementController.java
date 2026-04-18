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

    public BarangManagementController(BarangManagementView view) {
        this.view = view;
        refreshData();

        view.btnTambah.addActionListener(e -> {
            // Nanti panggil Pop-up Form Barang di sini
            FormBarangView form = new FormBarangView((JFrame) SwingUtilities.getWindowAncestor(view));
            new FormBarangController(form);
            form.setVisible(true);
            refreshData();
        });
    }

    public void refreshData() {
        view.panelDaftarBarang.removeAll();
        // Asumsi kamu udah bikin fungsi getAllBarang() di Model
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

        // ==========================================
        // 1. KIRI: Info Barang (Nama & Harga)
        // ==========================================
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

        // ==========================================
        // 2. TENGAH: Kontrol Stok (+ dan -)
        // ==========================================
        JPanel stokPanel = new JPanel(new GridBagLayout()); // Pakai GridBag biar center vertikal
        stokPanel.setOpaque(false);
        JPanel wadahStok = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        wadahStok.setOpaque(false);

        JButton btnMinus = new JButton("-");
        JLabel lblStok = new JLabel("Stok: " + b.getStok());
        lblStok.setFont(new Font("SansSerif", Font.BOLD, 14));
        JButton btnPlus = new JButton("+");

        // Logika Tombol Minus
        btnMinus.addActionListener(e -> {
            if (b.getStok() > 0) { // Biar stok gak minus
                boolean sukses = BarangManagement.updateStok(b.getIdBarang(), -1);
                if (sukses) {
                    b.setStok(b.getStok() - 1); // Update objeknya
                    lblStok.setText("Stok: " + b.getStok()); // Langsung ubah teks di layar tanpa refresh total!
                }
            } else {
                JOptionPane.showMessageDialog(view, "Stok udah abis Wak, gak bisa dikurangin lagi!");
            }
        });

        // Logika Tombol Plus
        btnPlus.addActionListener(e -> {
            boolean sukses = BarangManagement.updateStok(b.getIdBarang(), 1);
            if (sukses) {
                b.setStok(b.getStok() + 1); // Update objeknya
                lblStok.setText("Stok: " + b.getStok()); // Mulus ganti angka di layar
            }
        });

        wadahStok.add(btnMinus);
        wadahStok.add(lblStok);
        wadahStok.add(btnPlus);
        stokPanel.add(wadahStok);

        // ==========================================
        // 3. KANAN: Tombol Aksi (Edit & Hapus)
        // ==========================================
        JPanel aksiPanel = new JPanel(new GridBagLayout()); // Pakai GridBag biar center vertikal
        aksiPanel.setOpaque(false);
        JPanel wadahTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        wadahTombol.setOpaque(false);

        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");
        btnDelete.setForeground(Color.RED);

         // --- Logika Tombol Edit (Khusus user ini) ---
        btnEdit.addActionListener(e -> {
            EditBarangView editPopUp = new EditBarangView((JFrame) SwingUtilities.getWindowAncestor(view));
            editPopUp.setTitle("Edit Barang: " + b.getNamaBarang());

            // ==========================================
            // 1. TEMBAKKAN DATA LAMA KE DALAM FORM
            // ==========================================
            editPopUp.txtNamaBarang.setText(b.getNamaBarang());
            editPopUp.txtNamaBarang.setForeground(Color.BLACK);
            editPopUp.txtHarga.setText(String.valueOf(b.getHarga()));
            editPopUp.txtHarga.setForeground(Color.BLACK);
            editPopUp.txtStok.setText(String.valueOf(b.getStok()));
            editPopUp.txtStok.setForeground(Color.BLACK);
            editPopUp.cbKategori.setSelectedItem(b.getTanggalKadaluarsa());
            editPopUp.txtKadaluarsa.setText(String.valueOf(b.getTanggalKadaluarsa()));
            editPopUp.txtKadaluarsa.setForeground(Color.BLACK);

            // 3. Pasang Otaknya
            new EditBarangController(editPopUp, b.getIdBarang()); // Kirim username lama ke controller
            editPopUp.setVisible(true);

            // 4. Refresh data setelah beres ngedit
            refreshData();
        });
        // --- Logika Tombol Delete (Khusus user ini) ---
        btnDelete.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(view,
                    "Yakin mau hapus " + b.getNamaBarang() + "?",
                    "Hapus Barang", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                boolean berhasil = BarangManagement.deleteBarang(b.getIdBarang());

                // 2. CEK HASILNYA
                if (berhasil) {
                    JOptionPane.showMessageDialog(view,
                            "Barang " + b.getNamaBarang() + " berhasil dimusnahkan!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);

                    // 3. REFRESH LAYAR (Biar card-nya langsung hilang)
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

        // Gabungkan semuanya ke Card
        card.add(infoPanel, BorderLayout.WEST);
        card.add(stokPanel, BorderLayout.CENTER);
        card.add(aksiPanel, BorderLayout.EAST);

        return card;
    }
}