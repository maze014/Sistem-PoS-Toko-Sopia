package view;

import javax.swing.*;
import java.awt.*;

public class StokBarangView extends JPanel {
    public JTextField txtCari;
    public JPanel panelDaftarStok;

    public StokBarangView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel lblTitle = new JLabel("Cek Stok & Harga");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(13, 148, 136));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);

        txtCari = new RoundedTextField(20, 15);
        txtCari.setPreferredSize(new Dimension(250, 35));
        txtCari.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JLabel lblCari = new JLabel("Cari Barang: ");
        lblCari.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        searchPanel.add(lblCari);
        searchPanel.add(txtCari);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        panelDaftarStok = new JPanel();
        panelDaftarStok.setLayout(new BoxLayout(panelDaftarStok, BoxLayout.Y_AXIS));
        panelDaftarStok.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelDaftarStok);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel buatCard(int id, String nama, int stok, String harga) {
        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        cardWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        RoundedCardPanel card = new RoundedCardPanel(20);
        card.setLayout(new BorderLayout(15, 0));
        card.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel infoBarang = new JPanel(new GridLayout(2, 1));
        infoBarang.setOpaque(false);
        JLabel lblNama = new JLabel(nama);
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        JLabel lblHarga = new JLabel("Harga: " + harga);
        lblHarga.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblHarga.setForeground(Color.GRAY);
        
        infoBarang.add(lblNama);
        infoBarang.add(lblHarga);

        JLabel lblStok = new JLabel("Sisa: " + stok);
        lblStok.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblStok.setForeground(stok < 10 ? Color.RED : new Color(13, 148, 136));
        lblStok.setHorizontalAlignment(SwingConstants.RIGHT);

        card.add(infoBarang, BorderLayout.CENTER);
        card.add(lblStok, BorderLayout.EAST);
        
        cardWrapper.add(card, BorderLayout.CENTER);
        return cardWrapper;
    }

    class RoundedCardPanel extends JPanel {
        private int radius;
        public RoundedCardPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.setColor(new Color(230, 230, 230));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class RoundedTextField extends JTextField {
        private int radius;
        public RoundedTextField(int columns, int radius) {
            super(columns);
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
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