package controller;

import view.*;
import java.sql.*;
import javax.swing.JOptionPane;
import config.HashUtil;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.loginView.btnLogin.addActionListener(e -> {
            String user = loginView.txtUsername.getText();
            String pass = new String(loginView.txtPassword.getPassword());
            String hashPass = HashUtil.hashSHA256(pass);
            String roleTerpilih = loginView.cbRole.getSelectedItem().toString(); // Ambil pilihan dropdown

            try {
                Connection conn = config.DBConfig.getConnection();

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(loginView, "Woi, Username dan Password tak boleh kosong!");
                    return; // Berhenti di sini, jangan lanjut ke query
                }

                // Query dicek semua biar aman
                String sql = "SELECT nama_depan, username, password, role FROM user WHERE username=? AND password=? AND role=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, user);
                ps.setString(2, hashPass);
                ps.setString(3, roleTerpilih);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String nama = rs.getString("nama_depan");

                    JOptionPane.showMessageDialog(loginView, "Login Berhasil sebagai " + roleTerpilih);
                    new DashboardView(rs.getString("nama_depan"), rs.getString("role")).setVisible(true);
                    loginView.dispose();
                    // Kirim nama dan role ke Dashboard agar menu bisa dibatasi
                    new DashboardView(nama, roleTerpilih).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(loginView, "Username/Password/Role Salah!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}