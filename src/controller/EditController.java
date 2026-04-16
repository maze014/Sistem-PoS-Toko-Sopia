package controller;

import view.EditView;
import model.User;
import utils.HashUtil;
import config.DBConfig;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditController {
    private EditView view;
    private String usernameLama; // Simpan username lama untuk query UPDATE

    public EditController(EditView view, String usernameLama) {
        this.view = view;

        // Pasang listener ke tombol simpan di View
        this.view.btnSimpan.addActionListener(e -> simpanUser());
        this.usernameLama = usernameLama; // Simpan username lama yang dikirim dari UserManagementController
    }

    private void simpanUser() {
        // 1. Ambil data dari View
        String namaD = view.txtNamaDepan.getText().trim();
        String namaB = view.txtNamaBelakang.getText().trim();
        String user = view.txtUsername.getText().trim();
        String pass = new String(view.txtPassword.getPassword());
        String role = view.cbRole.getSelectedItem().toString();

        // 2. Validasi: Jangan sampai ada yang kosong
        if (namaD.isEmpty() || namaD.equals("Nama Depan") ||
                namaB.isEmpty() || namaB.equals("Nama Belakang") ||
                user.isEmpty() || user.equals("Masukkan username unik") ||
                pass.isEmpty() || pass.equals("Masukkan password")) {
            JOptionPane.showMessageDialog(view, "Semua field wajib diisi, Cok!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";

        if (!pass.matches(passwordRegex)) {
            JOptionPane.showMessageDialog(view,
                    "Password terlalu lemah, Wak!\n" +
                            "- Minimal 8 karakter\n" +
                            "- Wajib ada 1 Huruf Besar\n" +
                            "- Wajib ada 1 Angka\n" +
                            "- Wajib ada 1 Karakter Spesial (@,#,$,dll)",
                    "Validasi Gagal", JOptionPane.ERROR_MESSAGE);
            return; // Hentikan proses kalau password gagal validasi
        }

        // 3. Hash Password pakai SHA-256
        String passHashed = HashUtil.hashSHA256(pass);

        // 4. Bungkus ke Model User
        User userBaru = new User(namaD, namaB, user, passHashed, role);  

        // 5. Eksekusi ke Database
        try (Connection conn = DBConfig.getConnection()) {
            //System.out.println("Username Lama: " + usernameLama); // Debug: Pastikan username lama benar
            String sql = "UPDATE user SET nama_depan = ?, nama_belakang = ?, username = ?, password = ?, role = ? WHERE username= ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, userBaru.getNamaDepan());
            ps.setString(2, userBaru.getNamaBelakang());
            ps.setString(3, userBaru.getUsername());
            ps.setString(4, userBaru.getPassword());
            ps.setString(5, userBaru.getRole());
            ps.setString(6, usernameLama);

            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                JOptionPane.showMessageDialog(view, "User berhasil diedit!");
                view.dispose(); // Tutup form setelah sukses
            }
        } catch (SQLException e) {
            // Cek kalau username kembar (Duplicate Entry)
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(view, "Username sudah dipakai orang lain!");
            } else {
                JOptionPane.showMessageDialog(view, "Error Database: " + e.getMessage());
            }
        }
    }
}