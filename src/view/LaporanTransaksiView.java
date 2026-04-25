package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LaporanTransaksiView extends JPanel {

    public JComboBox<String> cbFilterWaktu;
    public JPanel panelWadahGrafik;
    public JLabel lblTotalPendapatan;

    public LaporanTransaksiView() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // ==========================================
        // HEADER: Judul & Filter
        // ==========================================
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);

        JLabel lblJudul = new JLabel("Laporan Transaksi & Pendapatan");
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 24));

        // Bagian Kanan Header (Filter & Total)
        JPanel panelKananHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelKananHeader.setOpaque(false);

        lblTotalPendapatan = new JLabel("Total: Rp 0");
        lblTotalPendapatan.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTotalPendapatan.setForeground(new Color(126, 34, 206)); // Warna Ungu

        String[] pilihanWaktu = {"7 Hari Terakhir", "30 Hari Terakhir", "Tahun Ini"};
        cbFilterWaktu = new JComboBox<>(pilihanWaktu);
        cbFilterWaktu.setFont(new Font("SansSerif", Font.PLAIN, 14));

        panelKananHeader.add(lblTotalPendapatan);
        panelKananHeader.add(new JLabel("Filter:"));
        panelKananHeader.add(cbFilterWaktu);

        panelHeader.add(lblJudul, BorderLayout.WEST);
        panelHeader.add(panelKananHeader, BorderLayout.EAST);

        // ==========================================
        // TENGAH: Wadah Grafik Raksasa
        // ==========================================
        panelWadahGrafik = new JPanel(new BorderLayout());
        panelWadahGrafik.setBackground(Color.WHITE);
        panelWadahGrafik.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));

        add(panelHeader, BorderLayout.NORTH);
        add(panelWadahGrafik, BorderLayout.CENTER);
    }
}