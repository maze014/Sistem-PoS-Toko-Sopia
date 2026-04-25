package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import utils.RoundedButton;

public class ManajerDashboardView extends JPanel {
    public RoundedButton cardOmzet, cardTransaksi, cardStok;
    public JLabel lblAngkaOmzet, lblAngkaTransaksi, lblAngkaStok;
    public JPanel panelWadahGrafik;
    private Color warnaUngu = new Color(126, 34, 206);

    public ManajerDashboardView() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- ATAS: 3 KARTU MANAJER ---
        JPanel panelKartu = new JPanel(new GridLayout(1, 3, 20, 0));
        panelKartu.setOpaque(false);
        panelKartu.setPreferredSize(new Dimension(0, 130));

        cardOmzet = buatKartu("Omzet Bulan Ini", lblAngkaOmzet = new JLabel("Rp 0"));
        cardTransaksi = buatKartu("Total Transaksi", lblAngkaTransaksi = new JLabel("0"));
        cardStok = buatKartu("Stok Menipis", lblAngkaStok = new JLabel("0"));

        panelKartu.add(cardOmzet);
        panelKartu.add(cardTransaksi);
        panelKartu.add(cardStok);

        // --- TENGAH: WADAH GRAFIK LAPORAN ---
        panelWadahGrafik = new JPanel(new BorderLayout());
        panelWadahGrafik.setBackground(Color.WHITE);
        panelWadahGrafik.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

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
        cardOmzet.setBackground(Color.WHITE);
        cardTransaksi.setBackground(Color.WHITE);
        cardStok.setBackground(Color.WHITE);
    }
}