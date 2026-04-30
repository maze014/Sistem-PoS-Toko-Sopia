package view;

import utils.RoundedButton;
import javax.swing.*;
import java.awt.*;

public class RiwayatKasirView extends JPanel {
    public JPanel panelDaftarRiwayat;

    public RiwayatKasirView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel lblTitle = new JLabel("Riwayat Penjualan");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(13, 148, 136)); 

        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        headerPanel.add(lblTitle, BorderLayout.WEST);

        panelDaftarRiwayat = new JPanel();
        panelDaftarRiwayat.setLayout(new BoxLayout(panelDaftarRiwayat, BoxLayout.Y_AXIS));
        panelDaftarRiwayat.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelDaftarRiwayat);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel buatCard(int id, String tanggal, String total, RoundedButton btnCetak) {
        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); 
        cardWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));

        RoundedCardPanel card = new RoundedCardPanel(20);
        card.setLayout(new BorderLayout(15, 0));
        card.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        JLabel lblId = new JLabel("ID Transaksi: " + id + " | " + tanggal);
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblId.setForeground(Color.GRAY);

        JLabel lblTotal = new JLabel("Total: " + total);
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setForeground(new Color(13, 148, 136));

        info.add(lblId);
        info.add(lblTotal);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        panelTombol.setOpaque(false);

        btnCetak.setBackground(new Color(13, 148, 136));
        btnCetak.setForeground(Color.WHITE);
        btnCetak.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCetak.setPreferredSize(new Dimension(130, 32));

        panelTombol.add(btnCetak);

        card.add(info, BorderLayout.CENTER);
        card.add(panelTombol, BorderLayout.EAST);
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
            g2.setColor(new Color(220, 220, 220));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}