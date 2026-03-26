package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KasirDashboard extends JFrame {

    // Warna Tema Kasir (Biru Cerah & Putih agar mata gak cepat lelah)
    Color sidebarColor = new Color(76, 0, 153); // Blue Accent
    Color activeMenuColor = new Color(29, 78, 216);
    Color bgColor = new Color(248, 250, 252);

    public KasirDashboard(String namaKasir) {
        setTitle("Sopia POS - Kasir Station");
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
        // [ SIDEBAR KASIR ]
        // ==========================================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        // Logo
        JLabel lblLogo = new JLabel("SOPIA POS");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 50)));

        // Menu Khusus Kasir
        addMenuButton(sidebar, "Dashboard", true);
        addMenuButton(sidebar, "Transaksi Baru", false);
        addMenuButton(sidebar, "Riwayat Penjualan", false);
        addMenuButton(sidebar, "Stok Barang", false);

        sidebar.add(Box.createVerticalGlue()); // Dorong logout ke bawah

        addMenuButton(sidebar, "Logout", false);

        // ==========================================
        // [ CONTENT AREA ]
        // ==========================================
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Header Section
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        JLabel lblHalo = new JLabel("Semangat Kerja, " + namaKasir + "!");
        lblHalo.setFont(new Font("SansSerif", Font.BOLD, 28));
        JLabel lblSub = new JLabel("Siap melayani pelanggan dengan senyuman hari ini?");
        lblSub.setForeground(Color.GRAY);
        titlePanel.add(lblHalo);
        titlePanel.add(lblSub);

        header.add(titlePanel, BorderLayout.WEST);

        // Panel Card Statistik Kasir
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(30, 0, 10, 0));

        cardPanel.add(new KasirStatCard("Total Nota", "42 Transaksi", new Color(59, 130, 246)));
        cardPanel.add(new KasirStatCard("Item Terjual", "156 Produk", new Color(139, 92, 246)));
        cardPanel.add(new KasirStatCard("Pencapaian", "85%", new Color(249, 115, 22)));

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setOpaque(false);
        wrapper.add(cardPanel);

        mainContent.add(wrapper, BorderLayout.CENTER);

        // Preview Stok Menipis (Penting buat Kasir agar bisa lapor)
        String[] cols = { "Nama Barang", "Kategori", "Sisa Stok", "Status" };
        Object[][] data = {
                { "Kopi Susu Gula Aren", "Minuman", "5", "TIPIS" },
                { "Roti Bakar Coklat", "Makanan", "2", "TIPIS" },
                { "Tissue Pack", "Lainnya", "0", "HABIS" }
        };
        JTable table = new JTable(data, cols);
        table.setRowHeight(35);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder("Peringatan Stok (Hampir Habis)"));
        mainContent.add(sp, BorderLayout.SOUTH);

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

class KasirStatCard extends JPanel {
    public KasirStatCard(String title, String value, Color color) {
        setLayout(new GridLayout(2, 1, 0, 0));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(250, 80));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 5, 0, color), // Garis di bawah
                new EmptyBorder(10, 20, 10, 20)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblValue.setForeground(color); // Warna teks nilai ngikutin tema card

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