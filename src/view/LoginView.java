package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.border.AbstractBorder;
import java.awt.geom.RoundRectangle2D;

class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String label, int radius) {
        super(label);
        this.radius = radius;
        setContentAreaFilled(false); // Biar background bawaan Java gak muncul
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Warna saat ditekan vs warna biasa
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

        // Gambar background bulat
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();
    }
}

class RoundedBorder extends AbstractBorder {
    private int radius;
    private Color color;

    RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        // Menggambar garis tepi melengkung
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        // Kasih padding dalam biar teks gak nempel ke lengkungan (Top, Left, Bottom,
        // Right)
        return new Insets(10, 15, 10, 15);
    }
}

public class LoginView extends JFrame {

    // --- Komponen Form (Sisi Kiri) ---
    private JPanel panelKiri, panelKanan;
    public JTextField txtUsername;
    public JPasswordField txtPassword;
    String[] roles = { "Admin", "Kasir", "Manajer" };
    public JComboBox<String> cbRole;
    public JButton btnLogin;
    private JLabel lblLogoAnimasi;

    // Warna tema ala WaifuChan
    private Color warnaUngu = new Color(126, 34, 206); // #7e22ce
    private Color warnaBackground = new Color(249, 250, 251); // #f9fafb

    public LoginView() {
        // --- 1. Setting Frame Utama ---
        setTitle("Login WaifuChan - Sopia POS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        setResizable(true); // Biar layoutnya gak berantakan kalau di-resize
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Gunakan GridLayout(1, 2) untuk membagi frame jadi dua kolom (Kiri & Kanan)
        setLayout(new GridLayout(1, 2));

        this.addWindowStateListener(e -> {
            // Jika status berubah dari Full Screen ke Normal
            if ((e.getOldState() & Frame.MAXIMIZED_BOTH) != 0 &&
                    (e.getNewState() & Frame.MAXIMIZED_BOTH) == 0) {

                // Kasih delay dikit biar transisinya halus baru ke tengah
                SwingUtilities.invokeLater(() -> setLocationRelativeTo(null));
            }
        });

        // ==========================================
        // [ SISI KIRI: FORM LOGIN ]
        // ==========================================
        panelKiri = new JPanel();
        panelKiri.setBackground(Color.WHITE); // Form bersih di latar putih
        panelKiri.setLayout(new GridBagLayout()); // Pakai GBL buat posisi center-left
        panelKiri.setBorder(new EmptyBorder(50, 50, 50, 50)); // Margin dalam

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponen penuhi lebar
        gbc.insets = new Insets(0, 0, 15, 0); // Spasi antar komponen (bawah)
        gbc.gridx = 0; // Semua di kolom 0

        // 1. Logo/Nama Aplikasi di Atas Form
        gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Toko Sopia");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitle.setForeground(Color.BLACK);
        panelKiri.add(lblTitle, gbc);

        gbc.gridy = 1;
        JLabel lblSubtitle = new JLabel("Selamat datang kembali, Wak!");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitle.setForeground(Color.GRAY);
        panelKiri.add(lblSubtitle, gbc);

        // -- Spasi tambahan --
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelKiri.add(new JLabel(""), gbc);

        // 2. Input Username
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelKiri.add(createLabelForm("Username"), gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtUsername = createTextField("Masukkan username");
        panelKiri.add(txtUsername, gbc);

        // 3. Input Password
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelKiri.add(createLabelForm("Password"), gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtPassword = createPasswordField("Masukkan password");
        // Catatan: Untuk ikon mata (show password),
        // kamu perlu library tambahan atau trik JPanel khusus.
        // Ini versi standarnya dulu.
        panelKiri.add(txtPassword, gbc);

        // 5. Tombol Login
        gbc.gridy = 9;
        btnLogin = new RoundedButton("Log In", 30); // 30 adalah tingkat kebulatannya
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(warnaUngu);
        // Efek Hover Tombol
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(warnaUngu.darker());
            }

            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(warnaUngu);
            }
        });
        panelKiri.add(btnLogin, gbc);

        // ==========================================
        // [ SISI KANAN: LOGO & ANIMASI ]
        // ==========================================
        panelKanan = new JPanel();
        panelKanan.setBackground(warnaBackground);
        panelKanan.setLayout(new GridBagLayout()); // Center posisi logo

        URL imgUrl = getClass().getResource("/img/iconTokoSopia.gif");

        if (imgUrl != null) {
            ImageIcon iconLogo = new ImageIcon(imgUrl);
            lblLogoAnimasi = new JLabel(iconLogo);
        } else {
            // Placeholder kalau file gambar gak ketemu
            lblLogoAnimasi = new JLabel("LOGO .GIF DISINI");
            lblLogoAnimasi.setFont(new Font("SansSerif", Font.BOLD, 24));
            lblLogoAnimasi.setForeground(Color.LIGHT_GRAY);
        }

        panelKanan.add(lblLogoAnimasi);

        // --- Gabungkan Kedua Panel ke Frame Utama ---
        add(panelKiri);
        add(panelKanan);
    }

    // --- Helper Methods untuk Styling ---
    private JLabel createLabelForm(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(new Color(55, 65, 81)); // #374151
        return lbl;
    }

    private JTextField createTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setColumns(10); // Lebar minimum
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf.setBorder(new RoundedBorder(15, new Color(51, 255, 255)));
        tf.setForeground(Color.GRAY);
        tf.setText(placeholder);

        // Efek Placeholder sederhana
        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (tf.getText().equals(placeholder)) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (tf.getText().isEmpty()) {
                    tf.setText(placeholder);
                    tf.setForeground(Color.GRAY);
                }
            }
        });
        return tf;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField pf = new JPasswordField();
        pf.setColumns(10);
        pf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pf.setBorder(new RoundedBorder(15, new Color(51, 255, 255)));

        // --- LOGIKA PLACEHOLDER ---
        pf.setEchoChar((char) 0); // Matikan sensor (biar teks placeholder kelihatan)
        pf.setText(placeholder);
        pf.setForeground(Color.GRAY);

        pf.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pass = new String(pf.getPassword());
                if (pass.equals(placeholder)) {
                    pf.setText("");
                    pf.setEchoChar('•'); // Aktifkan sensor (titik-titik) pas ngetik
                    pf.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String pass = new String(pf.getPassword());
                if (pass.isEmpty()) {
                    pf.setEchoChar((char) 0); // Matikan sensor lagi
                    pf.setText(placeholder);
                    pf.setForeground(Color.GRAY);
                }
            }
        });
        return pf;
    }

    // Main method untuk ngetes view
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}