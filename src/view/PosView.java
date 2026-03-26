package view;

import javax.swing.*;
import java.awt.*;

public class PosView extends JFrame {
    public JTextField txtNama = new JTextField(20);
    public JTextField txtHarga = new JTextField(20);
    public JButton btnSimpan = new JButton("Simpan Transaksi");
    public JTextArea displayArea = new JTextArea(10, 30);

    public PosView() {
        setTitle("Desktop POS Toko Sopia");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(new JLabel("Nama Barang:"));
        panel.add(txtNama);
        panel.add(new JLabel("Harga:"));
        panel.add(txtHarga);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(btnSimpan, BorderLayout.SOUTH);
    }
}