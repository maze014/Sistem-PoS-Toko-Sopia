package controller;

import view.*;
import java.sql.*;
import javax.swing.JOptionPane;

import utils.HashUtil;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.loginView.btnLogin.addActionListener(e -> {
            String user = loginView.txtUsername.getText();
            String pass = new String(loginView.txtPassword.getPassword());
            String hashPass = HashUtil.hashSHA256(pass);

            try {
                Connection conn = config.DBConfig.getConnection();

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(loginView, "Woi, Username dan Password tak boleh kosong!");
                    return; // Berhenti di sini, jangan lanjut ke query
                }

                // Query dicek semua biar aman
                String sql = "SELECT id_user, nama_depan, username, password, role FROM user WHERE username=? AND password=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, user);
                ps.setString(2, hashPass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String idUserString = rs.getString("id_user");
                    String nama = rs.getString("nama_depan");
                    String role = rs.getString("role");
                    int idUser = Integer.parseInt(idUserString);

                    if (role.equalsIgnoreCase("Admin")) {
                        new AdminView(nama, role).setVisible(true);
                    } else if (role.equalsIgnoreCase("Manajer")) {
                        new ManajerView(nama).setVisible(true);
                    } else {
                        new KasirView(nama, idUser).setVisible(true);
                    }
                    loginView.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginView, "Username/Password/Role Salah!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}