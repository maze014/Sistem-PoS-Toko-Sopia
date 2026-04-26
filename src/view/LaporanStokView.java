package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import utils.RoundedButton;

public class LaporanStokView extends JPanel {
    public RoundedButton cardTotalBarang, cardStokTipis, cardKategori;
    public JLabel lblAngkaTotal, lblAngkaTipis, lblAngkaKategori;
    public JPanel panelWadahGrafik;
    private Color warnaUngu = new Color(126, 34, 206);

    public LaporanStokView() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- HEADER: 3 KAD RINGKASAN STOK ---
        JPanel panelKartu = new JPanel(new GridLayout(1, 3, 20, 0));
        panelKartu.setOpaque(false);
        panelKartu.setPreferredSize(new Dimension(0, 130));

        cardTotalBarang = buatKartu("Total Semua Stok Barang", lblAngkaTotal = new JLabel("0"));
        cardStokTipis = buatKartu("Stok Kritis (< 10)", lblAngkaTipis = new JLabel("0"));
        cardKategori = buatKartu("Jumlah Kategori", lblAngkaKategori = new JLabel("0"));

        panelKartu.add(cardTotalBarang);
        panelKartu.add(cardStokTipis);
        panelKartu.add(cardKategori);

        // --- TENGAH: WADAH GRAFIK ---
        panelWadahGrafik = new JPanel(new BorderLayout());
        panelWadahGrafik.setBackground(Color.WHITE);
        panelWadahGrafik.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 2));

        add(panelKartu, BorderLayout.NORTH);
        add(panelWadahGrafik, BorderLayout.CENTER);
    }

    private RoundedButton buatKartu(String judul, JLabel lblAngka) {
        RoundedButton card = new RoundedButton("", 25);
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblJudul = new JLabel(judul);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblJudul.setForeground(Color.GRAY);

        lblAngka.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblAngka.setForeground(warnaUngu);

        card.add(lblJudul, BorderLayout.NORTH);
        card.add(lblAngka, BorderLayout.CENTER);
        return card;
    }

    public void resetWarna() {
        cardTotalBarang.setBackground(Color.WHITE);
        cardStokTipis.setBackground(Color.WHITE);
        cardKategori.setBackground(Color.WHITE);
    }
}