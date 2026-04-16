package view;

import javax.swing.*;
import utils.RoundedButton;
import java.awt.*;

public class UserManagementView extends JPanel {
    public JButton btnTambah;

    // Ini wadah utama buat nampung card-card user nanti
    public JPanel panelDaftarUser;
    Color warnaUngu = new Color(126, 34, 206);

    public UserManagementView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Header & Tombol Tambah (Di Atas)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel lblTitle = new JLabel("Manajemen User");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));

        btnTambah = new RoundedButton("Tambah User Baru", 15);
        btnTambah.setBackground(warnaUngu); // Warna Ungu
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        btnTambah.setFont(new Font("SansSerif", Font.BOLD, 14));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnTambah, BorderLayout.EAST);

        // 2. Wadah Card User (Di Tengah)
        // Pakai BoxLayout biar card-nya numpuk ke bawah dengan rapi
        panelDaftarUser = new JPanel();
        panelDaftarUser.setLayout(new BoxLayout(panelDaftarUser, BoxLayout.Y_AXIS));
        panelDaftarUser.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelDaftarUser);
        scrollPane.setBorder(null); // Biar gak ada garis jelek
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll mulus

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}