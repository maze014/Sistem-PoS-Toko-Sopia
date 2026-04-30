package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.net.URL;
import utils.RoundedBorder;
import utils.RoundedButton;

public class EditView extends JDialog {
    public JTextField txtNamaDepan, txtNamaBelakang, txtUsername;
    public JPasswordField txtPassword;
    public JComboBox<String> cbRole;
    public JButton btnSimpan, btnBatal;
    String[] roles = { "Admin", "Kasir", "Manajer", "Petugas_Gudang" };

    private Color warnaOrange = new Color(255, 128, 0);
    private Color warnaBackground = new Color(255, 255, 255);

    public EditView(Frame parent) {
        super(parent, "Edit User - Sopia POS", true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        JPanel panelKiri = new JPanel(new GridBagLayout());
        panelKiri.setBackground(Color.WHITE);
        panelKiri.setBorder(new EmptyBorder(30, 60, 30, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 10, 0);

        gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Edit User");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        panelKiri.add(lblTitle, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        panelKiri.add(createLabelForm("Nama Lengkap"), gbc);
        gbc.gridy = 2;
        JPanel panelNama = new JPanel(new GridLayout(1, 2, 10, 0));
        panelNama.setOpaque(false);
        txtNamaDepan = createTextField("Nama Depan");
        txtNamaBelakang = createTextField("Nama Belakang");
        panelNama.add(txtNamaDepan);
        panelNama.add(txtNamaBelakang);
        gbc.gridy = 3;
        panelKiri.add(panelNama, gbc);
        gbc.gridy = 4;
        panelKiri.add(createLabelForm("Username"), gbc);
        gbc.gridy = 5;
        txtUsername = createTextField("Masukkan username unik");
        panelKiri.add(txtUsername, gbc);
        gbc.gridy = 6;
        panelKiri.add(createLabelForm("Password"), gbc);
        gbc.gridy = 7;
        txtPassword = createPasswordField("Masukkan password");
        panelKiri.add(txtPassword, gbc);
        gbc.gridy = 8;
        panelKiri.add(createLabelForm("Posisi"), gbc);
        gbc.gridy = 9;
        cbRole = new JComboBox<>(roles);
        cbRole.setPreferredSize(new Dimension(0, 40)); 
        cbRole.setBorder(new RoundedBorder(15, warnaOrange));
        cbRole.setOpaque(false);
        cbRole.setBackground(new Color(0, 0, 0, 0));
        cbRole.putClientProperty("JComponent.outline", null);
        cbRole.putClientProperty("JComboBox.buttonBorder", null);
        cbRole.setFocusable(false);
        panelKiri.add(cbRole, gbc);

        gbc.gridy = 10;
        btnSimpan = new RoundedButton("Edit User", 30);
        btnSimpan.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setBackground(warnaOrange);

        btnSimpan.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSimpan.setBackground(warnaOrange.darker());
            }

            public void mouseExited(MouseEvent e) {
                btnSimpan.setBackground(warnaOrange);
            }
        });
        panelKiri.add(btnSimpan, gbc);

        JPanel panelKanan = new JPanel(new GridBagLayout());
        panelKanan.setBackground(warnaBackground);

        URL imgUrl = getClass().getResource("/img/iconRegis.gif");
        if (imgUrl != null) {
            ImageIcon iconLogo = new ImageIcon(imgUrl);
            JLabel lblLogoAnimasi = new JLabel(iconLogo);

            lblLogoAnimasi.setOpaque(false);
            lblLogoAnimasi.setBackground(new Color(0, 0, 0, 0));
            panelKanan.setOpaque(false);
            panelKanan.add(lblLogoAnimasi);
        } else {
            panelKanan.add(new JLabel("LOGO GIF"));
        }

        add(panelKiri);
        add(panelKanan);
    }

    private JLabel createLabelForm(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(new Color(55, 65, 81));
        return lbl;
    }

    private JTextField createTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setColumns(10);
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf.setBorder(new RoundedBorder(15, new Color(51, 255, 255)));
        tf.setForeground(Color.GRAY);
        tf.setText(placeholder);
        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (tf.getText().equals(placeholder)) {
                    tf.setText("");
                    tf.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (tf.getText().isEmpty()) {
                    tf.setText(placeholder);
                    tf.setForeground(Color.GRAY);
                }
            }
        });
        return tf;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField pf = new JPasswordField();
        pf.setColumns(10);
        pf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        pf.setBorder(new RoundedBorder(15, new Color(51, 255, 255)));
        pf.setEchoChar((char) 0);
        pf.setText(placeholder);
        pf.setForeground(Color.GRAY);

        pf.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                String pass = new String(pf.getPassword());
                if (pass.equals(placeholder)) {
                    pf.setText("");
                    pf.setEchoChar('•');
                    pf.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String pass = new String(pf.getPassword());
                if (pass.isEmpty()) {
                    pf.setEchoChar((char) 0);
                    pf.setText(placeholder);
                    pf.setForeground(Color.GRAY);
                }
            }
        });
        return pf;
    }
}