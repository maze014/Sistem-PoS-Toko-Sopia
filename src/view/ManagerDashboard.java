package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagerDashboard extends JFrame {

    // Warna Tema Manajer (Lebih elegan, misal Hijau Emerald atau Biru Gelap)
    Color sidebarColor = new Color(15, 23, 42); // Navy Dark
    Color activeMenuColor = new Color(30, 41, 59);
    Color bgColor = new Color(241, 245, 249);

    public ManagerDashboard(String namaManager) {
        setTitle("Sopia POS - Manager Panel");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(bgColor);
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

        // ==========================================
        //         [ SIDEBAR MANAJER ]
        // ==========================================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        // Logo Aplikasi
        JLabel lblLogo = new JLabel("SOPIA POS");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 50)));

        // Menu Khusus Manajer
        addMenuButton(sidebar, "Dashboard", true);
        addMenuButton(sidebar, "Laporan Transaksi", false);
        addMenuButton(sidebar, "Laporan Stok", false);
        
        sidebar.add(Box.createVerticalGlue()); // Dorong logout ke bawah
        
        addMenuButton(sidebar, "Logout", false);

        // ==========================================
        //         [ CONTENT AREA ]
        // ==========================================
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Header Section
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setOpaque(false);
        JLabel lblHalo = new JLabel("Halo, Manager " + namaManager);
        lblHalo.setFont(new Font("SansSerif", Font.BOLD, 28));
        JLabel lblSub = new JLabel("Berikut adalah ringkasan performa toko hari ini.");
        lblSub.setForeground(Color.GRAY);
        header.add(lblHalo);
        header.add(lblSub);
        mainContent.add(header, BorderLayout.NORTH);

        // Panel Card Laporan (Statistik)
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 25, 0));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(30, 0, 10, 0));

        // Card Warna-warni ala gambar yang kamu kirim
        cardPanel.add(new ReportCard("Total Omzet", "Rp 12.500.000", new Color(79, 70, 229))); // Indigo
        cardPanel.add(new ReportCard("Produk Terlaris", "Indomie Goreng", new Color(16, 185, 129))); // Emerald
        cardPanel.add(new ReportCard("Stok Menipis", "5 Item", new Color(239, 68, 68))); // Red

        mainContent.add(cardPanel, BorderLayout.CENTER);

        // Preview Tabel Laporan Singkat (Bottom)
        String[] cols = {"Tanggal", "No Nota", "Total", "Kasir"};
        Object[][] data = {
            {"2026-03-26", "TRX-001", "Rp 50.000", "Budi"},
            {"2026-03-26", "TRX-002", "Rp 120.000", "Ani"}
        };
        JTable table = new JTable(data, cols);
        table.setRowHeight(30);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Transaksi Terakhir"));
        mainContent.add(sp, BorderLayout.SOUTH);

        // Gabungkan
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }

    private void addMenuButton(JPanel container, String text, boolean isActive) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("SansSerif", isActive ? Font.BOLD : Font.PLAIN, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(isActive ? activeMenuColor : sidebarColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(isActive);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        container.add(btn);
        container.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}

// Class Card khusus untuk Laporan
class ReportCard extends JPanel {
    public ReportCard(String title, String value, Color color) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250, 150));
        
        // Garis warna di samping kiri card biar estetik
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 10, 0, 0, color),
            new EmptyBorder(20, 20, 5, 20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblValue.setForeground(Color.DARK_GRAY);

        add(lblTitle, BorderLayout.NORTH);
        add(lblValue, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.dispose();
    }
}
