package view;

import utils.RoundedButton;
import javax.swing.*;
import java.awt.*;

public class BarangManagementView extends JPanel {
    public RoundedButton btnTambah;
    public JPanel panelDaftarBarang; 

    public BarangManagementView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header & Tombol Tambah
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel lblTitle = new JLabel("Manajemen Barang");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));

        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        btnTambah = new RoundedButton("Tambah Barang Baru", 15);
        btnTambah.setBackground(new Color(138, 43, 226)); // Ungu Sopia
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnTambah.setPreferredSize(new Dimension(180, 30));
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnTambah, BorderLayout.EAST);

        // Wadah Card Barang
        panelDaftarBarang = new JPanel();
        panelDaftarBarang.setLayout(new BoxLayout(panelDaftarBarang, BoxLayout.Y_AXIS));
        panelDaftarBarang.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelDaftarBarang);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(headerPanel, BorderLayout.NORTH);
        // Biar ada jarak dikit antara judul dan list
        add(scrollPane, BorderLayout.CENTER);
    }
}