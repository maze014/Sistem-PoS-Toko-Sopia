package view;

import javax.swing.*;
import utils.RoundedButton;
import java.awt.*;

public class UserManagementView extends JPanel {
    public JButton btnTambah;
    public JPanel panelDaftarUser;
    Color warnaUngu = new Color(126, 34, 206);

    public UserManagementView() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel lblTitle = new JLabel("Manajemen User");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));

        btnTambah = new RoundedButton("Tambah User Baru", 15);
        btnTambah.setBackground(warnaUngu);
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        btnTambah.setFont(new Font("SansSerif", Font.BOLD, 14));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnTambah, BorderLayout.EAST);

        panelDaftarUser = new JPanel();
        panelDaftarUser.setLayout(new BoxLayout(panelDaftarUser, BoxLayout.Y_AXIS));
        panelDaftarUser.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelDaftarUser);
        scrollPane.setBorder(null); 
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}