package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.RoundedButton;

import java.awt.*;

public class AdminDashboardView extends JPanel {
    public RoundedButton cardPengguna, cardBarang, cardKategori;
    public JLabel lblAngkaPengguna, lblAngkaBarang, lblAngkaKategori;
    public JPanel panelWadahGrafik;
    private Color warnaUngu = new Color(126, 34, 206);

    public AdminDashboardView() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel panelKartu = new JPanel(new GridLayout(1, 3, 20, 0));
        panelKartu.setOpaque(false);
        panelKartu.setPreferredSize(new Dimension(0, 130));

        cardPengguna = buatKartu("Total Pengguna", lblAngkaPengguna = new JLabel("0"));
        cardBarang = buatKartu("Total Barang", lblAngkaBarang = new JLabel("0"));
        cardKategori = buatKartu("Total Kategori", lblAngkaKategori = new JLabel("0"));

        panelKartu.add(cardPengguna);
        panelKartu.add(cardBarang);
        panelKartu.add(cardKategori);

        panelWadahGrafik = new JPanel(new BorderLayout());
        panelWadahGrafik.setBackground(Color.WHITE);
        panelWadahGrafik.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));

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
    
    public void resetWarnaKartu() {
        cardPengguna.setBackground(Color.WHITE);
        cardBarang.setBackground(Color.WHITE);
        cardKategori.setBackground(Color.WHITE);
    }
}