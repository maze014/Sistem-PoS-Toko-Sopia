package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.AdminController;
import controller.LoginController;

import java.awt.*;

public class AdminDashboard extends JFrame {
    // Warna tema sesuai gambar
    Color sidebarColor = new Color(0, 204, 204); // Biru
    Color bgColor = new Color(255, 255, 255); // Abu-abu muda
    public JButton btnDashboard, btnUserManage, btnManageBarang, btnLogout;
    JPanel content = new JPanel(new BorderLayout());
    
    public AdminDashboard(String namaUser, String role) {
        setTitle("Sopia POS - Dashboard " + role);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        setResizable(true); // Biar layoutnya gak berantakan kalau di-resize

        this.addWindowStateListener(e -> {
            // Jika status berubah dari Full Screen ke Normal
            if ((e.getOldState() & Frame.MAXIMIZED_BOTH) != 0 &&
                    (e.getNewState() & Frame.MAXIMIZED_BOTH) == 0) {

                // Kasih delay dikit biar transisinya halus baru ke tengah
                SwingUtilities.invokeLater(() -> setLocationRelativeTo(null));
            }
        });

        // --- SIDEBAR (Kiri) ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        JLabel lblLogo = new JLabel("SOPIA POS");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 22));
        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        btnDashboard = addMenu(sidebar, "Dashboard");
        btnUserManage = addMenu(sidebar, "User Management");
        btnManageBarang = addMenu(sidebar, "Management Barang");

        btnUserManage.addActionListener(e -> {
            new AdminController(this);
        });

        sidebar.add(Box.createVerticalGlue()); // Dorong logout ke bawah
        btnLogout = addMenu(sidebar, "Logout");
        btnLogout.addActionListener(e -> {
            this.dispose();
            LoginView loginBaru = new LoginView();
            new LoginController(loginBaru);
            loginBaru.setVisible(true);
        });

        // --- MAIN CONTENT (Kanan) ---
        content.setBackground(bgColor);
        content.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header (Welcome Message)
        JLabel lblWelcome = new JLabel("Welcome back, " + namaUser + " (" + role + ")");
        lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 24));
        content.add(lblWelcome, BorderLayout.NORTH);

        // Panel Card Statistik (Mirip gambar: Biru, Ungu, Kuning)
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        if (role.equalsIgnoreCase("Manajer")) {
            cardPanel.add(new StatCard("Penjualan Bulanan", "Rp 15.000.000", new Color(126, 34, 206))); // Ungu
            cardPanel.add(new StatCard("Stok Masuk", "200 Unit", new Color(24, 119, 242))); // Biru
            cardPanel.add(new StatCard("Total Transaksi", "45 Nota", new Color(245, 158, 11))); // Kuning
        } else {
            cardPanel.add(new StatCard("Stok Total", "1.200", new Color(24, 119, 242)));
            cardPanel.add(new StatCard("Laba Hari Ini", "Rp 500.000", new Color(126, 34, 206)));
            cardPanel.add(new StatCard("Pelanggan Baru", "12", new Color(245, 158, 11)));
        }

        content.add(cardPanel, BorderLayout.CENTER);

        // Tabel (Simulasi Standard Table Design di gambar)
        String[] columns = { "ID", "Keterangan", "Waktu", "Status" };
        Object[][] data = { { "001", "Penjualan Kopi", "10:00", "SUCCESS" },
                { "002", "Stok Masuk", "11:30", "PENDING" } };
        JTable table = new JTable(data, columns);
        content.add(new JScrollPane(table), BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
    }

    public void tampilkanHalaman(JPanel panelBaru) {
        // 1. Sapu bersih semua isi panel kanan yang lama
        content.removeAll();

        // 2. Pastikan layoutnya BorderLayout biar panel baru langsung full screen di
        // kanan
        content.setLayout(new BorderLayout());

        // 3. Masukkan panel yang baru ke tengah-tengah
        content.add(panelBaru, BorderLayout.CENTER);

        // 4. KUNCI UTAMA: Beri tahu Java buat nge-refresh layar!
        content.revalidate(); // Update susunan komponen
        content.repaint(); // Gambar ulang warnanya
    }

    private JButton addMenu(JPanel panel, String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        return btn;
    }
}

// Class khusus buat bikin Card Statistik yang melengkung
class StatCard extends JPanel {
    public StatCard(String title, String value, Color color) {
        setLayout(new BorderLayout());
        setBackground(color);
        setPreferredSize(new Dimension(200, 120));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblVal = new JLabel(value);
        lblVal.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblVal.setForeground(Color.WHITE);

        add(lblTitle, BorderLayout.NORTH);
        add(lblVal, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Pojok melengkung
        g2.dispose();
    }
}