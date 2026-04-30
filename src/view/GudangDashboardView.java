package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GudangDashboardView extends JPanel {
    public JLabel lblMacamBarang, lblTotalStok, lblTotalTipis;
    public JPanel panelDaftarTipis;

    public GudangDashboardView() {
        setLayout(new BorderLayout(25, 25));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(25, 30, 30, 30));

        JPanel panelAtas = new JPanel(new BorderLayout(0, 20));
        panelAtas.setOpaque(false);

        JLabel lblTitle = new JLabel("Dashboard Gudang");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setForeground(new Color(40, 40, 40));

        JPanel panelKartuStat = new JPanel(new GridLayout(1, 3, 20, 0));
        panelKartuStat.setOpaque(false);
        panelKartuStat.setPreferredSize(new Dimension(0, 140));

        JPanel cardMacam = buatKartuStat("Macam Barang", lblMacamBarang = new JLabel("0"), new Color(138, 43, 226)); 
        JPanel cardStok = buatKartuStat("Total Fisik Stok", lblTotalStok = new JLabel("0"), new Color(13, 148, 136)); 
        JPanel cardTipis = buatKartuStat("Stok Kritis (<10)", lblTotalTipis = new JLabel("0"), new Color(220, 38, 38)); 

        panelKartuStat.add(cardMacam);
        panelKartuStat.add(cardStok);
        panelKartuStat.add(cardTipis);

        panelAtas.add(lblTitle, BorderLayout.NORTH);
        panelAtas.add(panelKartuStat, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new BorderLayout(0, 10));
        panelBawah.setOpaque(false);

        JLabel lblSubTitle = new JLabel("⚠️ Perlu Restock Segera (Stok di bawah 10)");
        lblSubTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblSubTitle.setForeground(new Color(220, 38, 38));

        panelDaftarTipis = new JPanel();
        panelDaftarTipis.setLayout(new BoxLayout(panelDaftarTipis, BoxLayout.Y_AXIS));
        panelDaftarTipis.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(panelDaftarTipis);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(245, 245, 245));
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelBawah.add(lblSubTitle, BorderLayout.NORTH);
        panelBawah.add(scrollPane, BorderLayout.CENTER);

        add(panelAtas, BorderLayout.NORTH);
        add(panelBawah, BorderLayout.CENTER);
    }

    private JPanel buatKartuStat(String judul, JLabel lblAngka, Color warna) {
        RoundedCardPanel card = new RoundedCardPanel(20);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblJudul = new JLabel(judul);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblJudul.setForeground(Color.GRAY);

        lblAngka.setFont(new Font("SansSerif", Font.BOLD, 48));
        lblAngka.setForeground(warna);

        card.add(lblJudul, BorderLayout.NORTH);
        card.add(lblAngka, BorderLayout.CENTER);
        return card;
    }

    public JPanel buatCardStokTipis(int id, String nama, int stok) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        RoundedCardPanel card = new RoundedCardPanel(15);
        card.setLayout(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(15, 25, 15, 25));

        JPanel infoKiri = new JPanel(new GridLayout(2, 1));
        infoKiri.setOpaque(false);
        JLabel lblNama = new JLabel(nama);
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel lblId = new JLabel("ID Barang: #" + id);
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblId.setForeground(Color.GRAY);
        infoKiri.add(lblNama);
        infoKiri.add(lblId);

        JLabel lblStok = new JLabel(stok + " Pcs");
        lblStok.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblStok.setForeground(new Color(220, 38, 38));

        card.add(infoKiri, BorderLayout.CENTER);
        card.add(lblStok, BorderLayout.EAST);
        
        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    class RoundedCardPanel extends JPanel {
        private int radius;
        public RoundedCardPanel(int radius) { this.radius = radius; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
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