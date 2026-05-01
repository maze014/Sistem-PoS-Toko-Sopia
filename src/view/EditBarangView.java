package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.net.URL;

import utils.RoundedBorder;
import utils.RoundedButton;
import model.Kategori;

public class EditBarangView extends JDialog {
    public JTextField txtNamaBarang, txtHarga, txtStok, txtKadaluarsa;
    public JComboBox<Kategori> cbKategori;
    public RoundedButton btnSimpan;
    private Color warnaOrange = new Color(255, 128, 0);
    private Color warnaBackground = new Color(255, 255, 255);

    public EditBarangView(Frame parent) {
        super(parent, "Manajemen Barang - Sopia POS", true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        JPanel panelKiri = new JPanel(new GridBagLayout());
        panelKiri.setBackground(Color.WHITE);
        panelKiri.setBorder(new EmptyBorder(30, 60, 30, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 10, 0);

        gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Form Barang");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        panelKiri.add(lblTitle, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        panelKiri.add(createLabelForm("Nama Barang"), gbc);
        
        gbc.gridy = 2;
        txtNamaBarang = createTextField("Contoh: Keripik Singkong Pedas");
        panelKiri.add(txtNamaBarang, gbc);

        gbc.gridy = 3;
        JPanel panelLabelHS = new JPanel(new GridLayout(1, 2, 10, 0));
        panelLabelHS.setOpaque(false);
        panelLabelHS.add(createLabelForm("Harga (Rp)"));
        panelLabelHS.add(createLabelForm("Stok Awal"));
        panelKiri.add(panelLabelHS, gbc);

        gbc.gridy = 4;
        JPanel panelInputHS = new JPanel(new GridLayout(1, 2, 10, 0));
        panelInputHS.setOpaque(false);
        txtHarga = createTextField("Misal: 5000");
        txtStok = createTextField("Misal: 50");
        panelInputHS.add(txtHarga);
        panelInputHS.add(txtStok);
        panelKiri.add(panelInputHS, gbc);

        gbc.gridy = 5;
        panelKiri.add(createLabelForm("Kategori Barang"), gbc);
        
        gbc.gridy = 6;
        cbKategori = new JComboBox<>();
        cbKategori.setPreferredSize(new Dimension(0, 40)); 
        cbKategori.setBorder(new RoundedBorder(15, warnaOrange));
        cbKategori.setOpaque(false); 
        cbKategori.setBackground(new Color(0, 0, 0, 0));
        cbKategori.putClientProperty("JComponent.outline", null);
        cbKategori.putClientProperty("JComboBox.buttonBorder", null);
        cbKategori.setFocusable(false);
        panelKiri.add(cbKategori, gbc);

        gbc.gridy = 7;
        panelKiri.add(createLabelForm("Tanggal Kadaluarsa"), gbc);
        
        gbc.gridy = 8;
        txtKadaluarsa = createTextField("YYYY-MM-DD (Misal: 2026-12-31)");
        panelKiri.add(txtKadaluarsa, gbc);

        gbc.gridy = 9;
        gbc.insets = new Insets(20, 0, 10, 0);
        btnSimpan = new RoundedButton("Edit Barang", 30);
        btnSimpan.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setBackground(warnaOrange);
        
        btnSimpan.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSimpan.setBackground(warnaOrange.darker());
            }
            public void mouseExited(MouseEvent e) {
                btnSimpan.setBackground(warnaOrange);
            }
        });
        panelKiri.add(btnSimpan, gbc);

        JPanel panelKanan = new JPanel(new GridBagLayout());
        panelKanan.setBackground(warnaBackground);

        URL imgUrl = getClass().getResource("/img/iconBarang.gif"); 
        if (imgUrl != null) {
            ImageIcon iconLogo = new ImageIcon(imgUrl);
            JLabel lblLogoAnimasi = new JLabel(iconLogo);
            lblLogoAnimasi.setOpaque(false);
            lblLogoAnimasi.setBackground(new Color(0, 0, 0, 0));
            panelKanan.setOpaque(false);
            panelKanan.add(lblLogoAnimasi);
        } else {
            panelKanan.add(new JLabel("LOGO BARANG GIF"));
        }

        add(panelKiri);
        add(panelKanan);
    }

    private JLabel createLabelForm(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(new Color(55, 65, 81)); 
        return lbl;
    }

    private JTextField createTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setColumns(10); 
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf.setBorder(new RoundedBorder(15, new Color(51, 255, 255)));
        tf.setForeground(Color.GRAY);
        tf.setText(placeholder);
        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (tf.getText().equals(placeholder)) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (tf.getText().trim().isEmpty()) {
                    tf.setText(placeholder);
                    tf.setForeground(Color.GRAY);
                }
            }
        });
        return tf;
    }
}