package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.RoundedButton;

import java.awt.*;

public class KasirDashboardView extends JPanel {
    public JLabel lblOmzet, lblTotalTrx;
    public JPanel panelWadahGrafik; // Wadah baru untuk grafik
    private Color warnaHijau = new Color(13, 148, 136);

    public KasirDashboardView() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(20, 30, 30, 30)); // Padding sedikit disesuaikan

        // --- KARTU ATAS ---
        JPanel panelKartu = new JPanel(new GridLayout(1, 2, 30, 0));
        panelKartu.setOpaque(false);
        panelKartu.setPreferredSize(new Dimension(0, 150));

        RoundedButton cardOmzet = buatKartu("Omzetmu Hari Ini", lblOmzet = new JLabel("Rp 0"));
        RoundedButton cardTrx = buatKartu("Pelanggan Dilayani", lblTotalTrx = new JLabel("0 Orang"));

        panelKartu.add(cardOmzet);
        panelKartu.add(cardTrx);

        // --- WADAH GRAFIK TENGAH ---
        panelWadahGrafik = new JPanel(new BorderLayout());
        panelWadahGrafik.setBackground(Color.WHITE);
        panelWadahGrafik.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));

        // Tempel ke layar utama
        add(panelKartu, BorderLayout.NORTH);
        add(panelWadahGrafik, BorderLayout.CENTER);
    }

    private RoundedButton buatKartu(String judul, JLabel lblAngka) {
        RoundedButton card = new RoundedButton("", 25);
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblJudul = new JLabel(judul);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblJudul.setForeground(Color.GRAY);

        lblAngka.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblAngka.setForeground(warnaHijau);

        card.add(lblJudul, BorderLayout.NORTH);
        card.add(lblAngka, BorderLayout.CENTER);
        return card;
    }
}