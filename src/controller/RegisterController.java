package controller;

import view.RegisterView;
import model.User;
import config.DBConfig;
import config.HashUtil; // Pakai fungsi SHA-256 yang kita buat kemarin
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
    private RegisterView view;

    public RegisterController(RegisterView view) {
        this.view = view;
        
        // Pasang listener ke tombol simpan di View
        this.view.btnSimpan.addActionListener(e -> simpanUser());
        
        // Pasang listener tombol batal
        // this.view.btnBatal.addActionListener(e -> view.dispose());
    }

    private void simpanUser() {
        // 1. Ambil data dari View
        String namaD = view.txtNamaDepan.getText().trim();
        String namaB = view.txtNamaBelakang.getText().trim();
        String user = view.txtUsername.getText().trim();
        String pass = new String(view.txtPassword.getPassword());
        String role = view.cbRole.getSelectedItem().toString();

        // 2. Validasi: Jangan sampai ada yang kosong
        if (namaD.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua field wajib diisi, Cok!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Hash Password pakai SHA-256
        String passHashed = HashUtil.hashSHA256(pass);

        // 4. Bungkus ke Model User
        User userBaru = new User(namaD, namaB, user, passHashed, role);

        // 5. Eksekusi ke Database
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "INSERT INTO users (nama_depan, nama_belakang, username, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, userBaru.getNamaDepan());
            ps.setString(2, userBaru.getNamaBelakang());
            ps.setString(3, userBaru.getUsername());
            ps.setString(4, userBaru.getPassword());
            ps.setString(5, userBaru.getRole());

            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                JOptionPane.showMessageDialog(view, "User baru berhasil didaftarkan!");
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