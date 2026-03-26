package view;
import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    public DashboardView(String namaUser, String role) {
        setTitle("Dashboard - POS Toko Sopia");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JLabel lblWelcome = new JLabel("Login sebagai: " + namaUser, SwingConstants.CENTER);
        JButton btnLogout = new JButton("Logout");

        JButton btnLaporan = new JButton("Laporan Keuangan");

        // Jika login sebagai kasir, matikan tombol laporan
        if (role.equals("kasir")) {
            btnLaporan.setEnabled(false); // Tombol jadi abu-abu/mati
            btnLaporan.setToolTipText("Hanya untuk Admin/Manajer");
        }

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginView().setVisible(true); // Balik ke login
        });

        setLayout(new BorderLayout());
        add(lblWelcome, BorderLayout.CENTER);
        add(btnLogout, BorderLayout.SOUTH);
    }
}