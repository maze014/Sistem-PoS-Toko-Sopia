package view;

import utils.RoundedButton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TransaksiView extends JPanel {
    public RoundedTextField txtPencarianBarang, txtJumlah, txtUangBayar;
    public RoundedButton btnTambah, btnBayar;
    public JPanel panelDaftarKeranjang;
    public JLabel lblTotalHarga, lblKembalian;
    public JPopupMenu popupPencarian;

    public TransaksiView() {
        setLayout(new BorderLayout(25, 20));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        RoundedCardPanel panelKiri = new RoundedCardPanel(20);
        panelKiri.setLayout(new BorderLayout(15, 15));
        panelKiri.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formBarang = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        formBarang.setOpaque(false);

        txtPencarianBarang = new RoundedTextField(15, 15);
        txtPencarianBarang.setPreferredSize(new Dimension(220, 35));

        popupPencarian = new JPopupMenu();

        txtJumlah = new RoundedTextField(4, 15);
        txtJumlah.setPreferredSize(new Dimension(60, 35));
        txtJumlah.setText("1");

        btnTambah = new RoundedButton("Tambah", 15);
        btnTambah.setBackground(new Color(138, 43, 226));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setPreferredSize(new Dimension(100, 35));

        formBarang.add(new JLabel("Barang:"));
        formBarang.add(txtPencarianBarang);
        formBarang.add(new JLabel("Qty:"));
        formBarang.add(txtJumlah);
        formBarang.add(btnTambah);

        panelDaftarKeranjang = new JPanel();
        panelDaftarKeranjang.setLayout(new BoxLayout(panelDaftarKeranjang, BoxLayout.Y_AXIS));
        panelDaftarKeranjang.setBackground(Color.WHITE);

        JScrollPane scrollKeranjang = new JScrollPane(panelDaftarKeranjang);
        scrollKeranjang.setBorder(null);
        scrollKeranjang.getViewport().setBackground(Color.WHITE);
        scrollKeranjang.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollKeranjang.getVerticalScrollBar().setUnitIncrement(16);

        panelKiri.add(formBarang, BorderLayout.NORTH);
        panelKiri.add(scrollKeranjang, BorderLayout.CENTER);

        RoundedCardPanel panelKanan = new RoundedCardPanel(20);
        panelKanan.setPreferredSize(new Dimension(380, 0));
        panelKanan.setLayout(new BorderLayout(10, 20));
        panelKanan.setBorder(new EmptyBorder(30, 25, 30, 25));

        JPanel panelTotalInfo = new JPanel(new GridLayout(2, 1));
        panelTotalInfo.setOpaque(false);
        JLabel lblLabelTotal = new JLabel("Total Belanja");
        lblLabelTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblLabelTotal.setForeground(Color.GRAY);

        lblTotalHarga = new JLabel("Rp 0");
        lblTotalHarga.setFont(new Font("SansSerif", Font.BOLD, 52));
        lblTotalHarga.setForeground(new Color(220, 38, 38));

        panelTotalInfo.add(lblLabelTotal);
        panelTotalInfo.add(lblTotalHarga);

        JPanel formBayar = new JPanel(new GridLayout(5, 1, 0, 12));
        formBayar.setOpaque(false);

        txtUangBayar = new RoundedTextField(10, 15);
        txtUangBayar.setFont(new Font("SansSerif", Font.BOLD, 24));
        txtUangBayar.setHorizontalAlignment(JTextField.RIGHT);

        lblKembalian = new JLabel("Kembalian: Rp 0");
        lblKembalian.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblKembalian.setForeground(new Color(13, 148, 136));

        btnBayar = new RoundedButton("PROSES BAYAR", 15);
        btnBayar.setBackground(new Color(13, 148, 136));
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setFont(new Font("SansSerif", Font.BOLD, 20));

        formBayar.add(new JLabel("Tunai Pelanggan (Rp):"));
        formBayar.add(txtUangBayar);
        formBayar.add(new JLabel(""));
        formBayar.add(lblKembalian);
        formBayar.add(btnBayar);

        panelKanan.add(panelTotalInfo, BorderLayout.NORTH);
        panelKanan.add(formBayar, BorderLayout.SOUTH);

        add(panelKiri, BorderLayout.CENTER);
        add(panelKanan, BorderLayout.EAST);
    }

    public JPanel buatCardKeranjang(String nama, int qty, int hargaSatuan, int subtotal, ActionListener aksiHapus) {
        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        cardWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        RoundedCardPanel card = new RoundedCardPanel(15);
        card.setLayout(new BorderLayout(10, 0));
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel infoKiri = new JPanel(new GridLayout(2, 1));
        infoKiri.setOpaque(false);
        JLabel lblNama = new JLabel(nama);
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 15));
        JLabel lblHarga = new JLabel("Rp " + String.format("%,d", hargaSatuan) + "  x" + qty);
        lblHarga.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblHarga.setForeground(Color.GRAY);
        infoKiri.add(lblNama);
        infoKiri.add(lblHarga);

        JPanel infoKanan = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        infoKanan.setOpaque(false);
        JLabel lblSub = new JLabel("Rp " + String.format("%,d", subtotal));
        lblSub.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblSub.setForeground(new Color(13, 148, 136));

        JButton btnHapus = new JButton("X");
        btnHapus.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnHapus.setForeground(Color.RED);
        btnHapus.setContentAreaFilled(false);
        btnHapus.setBorderPainted(false);
        btnHapus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHapus.addActionListener(aksiHapus);

        infoKanan.add(lblSub);
        infoKanan.add(btnHapus);

        card.add(infoKiri, BorderLayout.CENTER);
        card.add(infoKanan, BorderLayout.EAST);

        cardWrapper.add(card, BorderLayout.CENTER);
        return cardWrapper;
    }

    public class RoundedTextField extends JTextField {
        private int radius;

        public RoundedTextField(int columns, int radius) {
            super(columns);
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
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