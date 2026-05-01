package controller;

import view.*;
import java.sql.*;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView view) {
        this.loginView = view;
        this.loginView.btnLogin.addActionListener(e -> {
            String user = loginView.txtUsername.getText();
            String pass = new String(loginView.txtPassword.getPassword());

            try {
                Connection conn = config.DBConfig.getConnection();

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(loginView, "Username dan Password tak boleh kosong!");
                    return;
                }

                String sql = "SELECT id_user, nama_depan, username, password, role FROM user WHERE username=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, user);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String idUserString = rs.getString("id_user");
                    String nama = rs.getString("nama_depan");
                    String role = rs.getString("role");
                    String hashDariDatabase = rs.getString("password");
                    boolean cocok = BCrypt.checkpw(pass, hashDariDatabase);
                    int idUser = Integer.parseInt(idUserString);

                    if (cocok) {
                        if (role.equalsIgnoreCase("Admin")) {
                            new AdminView(nama, role).setVisible(true);
                        } else if (role.equalsIgnoreCase("Manajer")) {
                            new ManajerView(nama).setVisible(true);
                        } else if (role.equalsIgnoreCase("Kasir")) {
                            new KasirView(nama, idUser).setVisible(true);
                        } else {
                            new GudangView(user).setVisible(true);
                        }
                        loginView.dispose();
                    } else {
                        JOptionPane.showMessageDialog(loginView, "Username atau Password Salah!");
                    }
                } else {
                    JOptionPane.showMessageDialog(loginView, "Username atau Password Salah!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}