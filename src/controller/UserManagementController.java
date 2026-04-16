package controller;

import model.User;
import model.UserManagement;
import view.UserManagementView;
import view.RegisterView;
import view.EditView;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import utils.RoundedBorder;

public class UserManagementController {
    private UserManagementView view;

    public UserManagementController(UserManagementView view) {
        this.view = view;
        refreshData(); // Langsung cetak card pas dibuka

        // // Tombol Tambah User Baru
        this.view.btnTambah.addActionListener(e -> {
            RegisterView regView = new RegisterView((JFrame) SwingUtilities.getWindowAncestor(view));
            new RegisterController(regView);
            regView.setVisible(true);
            // Refresh data setelah pop-up tambah user ditutup
            refreshData();
        });
    }

    // Fungsi buat narik data dan nyetak Card

    public void refreshData() {
        view.panelDaftarUser.removeAll(); // Sapu bersih wadahnya dulu
        List<User> users = UserManagement.getAllUsers();

        for (User u : users) {
            JPanel card = buatCardUser(u); // Bikin 1 card
            view.panelDaftarUser.add(card); // Masukin ke wadah
            view.panelDaftarUser.add(Box.createRigidArea(new Dimension(0, 10))); // Kasih jarak antar card
        }

        // Wajib panggil ini biar layar ter-update
        view.panelDaftarUser.revalidate();
        view.panelDaftarUser.repaint();
    }

    // ==========================================
    // PABRIK PEMBUAT CARD
    // ==========================================
    private JPanel buatCardUser(User u) {
        // 1. Setup Card Utama
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        // Pakai border ungu melengkung yang udah kamu bikin kemarin
        card.setBorder(new RoundedBorder(15, new Color(200, 200, 200)));
        // Biar ukuran tingginya fix, gak gepeng
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15) // Padding dalam card
        ));

        // 2. Info Kiri (Nama & Role)
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel lblNama = new JLabel(u.getNamaDepan() + " " + u.getNamaBelakang());
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel lblRole = new JLabel(u.getRole() + " | " + u.getUsername());
        lblRole.setForeground(Color.GRAY);

        infoPanel.add(lblNama);
        infoPanel.add(lblRole);

        // 3. Tombol Kanan (Edit & Delete)
        JPanel aksiPanel = new JPanel(new GridBagLayout());
        aksiPanel.setOpaque(false);

        JPanel wadahTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        wadahTombol.setOpaque(false);

        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");
        btnDelete.setForeground(Color.RED);

        // --- Logika Tombol Edit (Khusus user ini) ---
        btnEdit.addActionListener(e -> {
            EditView editPopUp = new EditView((JFrame) SwingUtilities.getWindowAncestor(view));
            editPopUp.setTitle("Edit User: " + u.getUsername());

            // ==========================================
            // 1. TEMBAKKAN DATA LAMA KE DALAM FORM
            // ==========================================
            editPopUp.txtNamaDepan.setText(u.getNamaDepan());
            editPopUp.txtNamaDepan.setForeground(Color.BLACK);
            editPopUp.txtNamaBelakang.setText(u.getNamaBelakang());
            editPopUp.txtNamaBelakang.setForeground(Color.BLACK);
            editPopUp.txtUsername.setText(u.getUsername());
            editPopUp.txtUsername.setForeground(Color.BLACK);
            editPopUp.cbRole.setSelectedItem(u.getRole());

            // 3. Pasang Otaknya
            new EditController(editPopUp, u.getUsername()); // Kirim username lama ke controller
            editPopUp.setVisible(true);

            // 4. Refresh data setelah beres ngedit
            refreshData();
        });
        // --- Logika Tombol Delete (Khusus user ini) ---
        btnDelete.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(view,
                    "Yakin mau hapus user " + u.getNamaDepan() + "?",
                    "Hapus User", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                boolean berhasil = UserManagement.deleteUser(u.getUsername());

                // 2. CEK HASILNYA
                if (berhasil) {
                    JOptionPane.showMessageDialog(view,
                            "User " + u.getUsername() + " berhasil dimusnahkan!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);

                    // 3. REFRESH LAYAR (Biar card-nya langsung hilang)
                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Gagal menghapus user! Cek koneksi database Wak.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        wadahTombol.add(btnEdit);
        wadahTombol.add(btnDelete);

        aksiPanel.add(wadahTombol);

        // 4. Gabungkan ke Card
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(aksiPanel, BorderLayout.EAST);

        return card;
    }
}