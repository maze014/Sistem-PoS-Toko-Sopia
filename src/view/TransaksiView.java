package view;

import utils.RoundedButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransaksiView extends JPanel {
    public RoundedTextField txtPencarianBarang, txtJumlah, txtUangBayar;
    public RoundedButton btnTambah, btnBayar;
    public JTable tabelKeranjang;
    public DefaultTableModel modelKeranjang;
    public JLabel lblTotalHarga, lblKembalian;

    public TransaksiView() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // ==========================================
        // PANEL KIRI: Form Tambah & Keranjang
        // ==========================================
        JPanel panelKiri = new JPanel(new BorderLayout(10, 10));
        panelKiri.setOpaque(false);

        // Form Atas
        JPanel formBarang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formBarang.setOpaque(false);
        
        txtPencarianBarang = new RoundedTextField(15, 15);
        txtJumlah = new RoundedTextField(5, 15);
        txtJumlah.setText("1"); // Default beli 1
        
        btnTambah = new RoundedButton("Tambah (+)", 15);
        btnTambah.setBackground(new Color(138, 43, 226)); // Ungu
        btnTambah.setForeground(Color.WHITE);

        formBarang.add(new JLabel("ID/Nama Barang:"));
        formBarang.add(txtPencarianBarang);
        formBarang.add(new JLabel("Qty:"));
        formBarang.add(txtJumlah);
        formBarang.add(btnTambah);

        // Tabel Keranjang
        String[] kolom = {"ID", "Nama Barang", "Harga", "Qty", "Subtotal"};
        modelKeranjang = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Kunci edit manual
        };
        tabelKeranjang = new JTable(modelKeranjang);
        tabelKeranjang.setRowHeight(30);
        tabelKeranjang.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollKeranjang = new JScrollPane(tabelKeranjang);
        scrollKeranjang.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollKeranjang.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // Scroll ghaib

        panelKiri.add(formBarang, BorderLayout.NORTH);
        panelKiri.add(scrollKeranjang, BorderLayout.CENTER);

        // ==========================================
        // PANEL KANAN: Total & Pembayaran
        // ==========================================
        JPanel panelKanan = new JPanel(new BorderLayout(10, 10));
        panelKanan.setPreferredSize(new Dimension(350, 0));
        panelKanan.setBackground(Color.WHITE);
        panelKanan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Angka Raksasa
        lblTotalHarga = new JLabel("Rp 0");
        lblTotalHarga.setFont(new Font("SansSerif", Font.BOLD, 48));
        lblTotalHarga.setForeground(new Color(220, 38, 38)); // Merah totalan
        lblTotalHarga.setHorizontalAlignment(SwingConstants.RIGHT);

        // Form Pembayaran
        JPanel formBayar = new JPanel(new GridLayout(5, 1, 0, 10));
        formBayar.setOpaque(false);
        
        txtUangBayar = new RoundedTextField(10, 15);
        txtUangBayar.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        lblKembalian = new JLabel("Kembalian: Rp 0");
        lblKembalian.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblKembalian.setForeground(new Color(13, 148, 136));
        
        btnBayar = new RoundedButton("PROSES BAYAR", 15);
        btnBayar.setBackground(new Color(13, 148, 136));
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setFont(new Font("SansSerif", Font.BOLD, 20));

        formBayar.add(new JLabel("Uang Tunai Pelanggan:"));
        formBayar.add(txtUangBayar);
        formBayar.add(new JLabel("")); // Spacer
        formBayar.add(lblKembalian);
        formBayar.add(btnBayar);

        panelKanan.add(lblTotalHarga, BorderLayout.NORTH);
        panelKanan.add(formBayar, BorderLayout.SOUTH);

        // Gabungkan
        add(panelKiri, BorderLayout.CENTER);
        add(panelKanan, BorderLayout.EAST);
    }

    // Inner class untuk textfield melengkung
    public class RoundedTextField extends JTextField {
        private int radius;
        public RoundedTextField(int columns, int radius) {
            super(columns);
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }
}