package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import utils.RoundedButton;
import utils.RoundedBorder;

public class LoginView extends JFrame {
    private JPanel panelKiri, panelKanan;
    public JTextField txtUsername;
    public JPasswordField txtPassword;
    public JComboBox<String> cbRole;
    public JButton btnLogin;
    private JLabel lblLogoAnimasi;
    private Color warnaUngu = new Color(126, 34, 206);
    private Color warnaBackground = new Color(255, 255, 255); 

    public LoginView() {
        setTitle("Login WaifuChan - Sopia POS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridLayout(1, 2));

        this.addWindowStateListener(e -> {
            if ((e.getOldState() & Frame.MAXIMIZED_BOTH) != 0 &&
                    (e.getNewState() & Frame.MAXIMIZED_BOTH) == 0) {
                SwingUtilities.invokeLater(() -> setLocationRelativeTo(null));
            }
        });

        panelKiri = new JPanel();
        panelKiri.setBackground(Color.WHITE); 
        panelKiri.setLayout(new GridBagLayout()); 
        panelKiri.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(0, 0, 15, 0); 
        gbc.gridx = 0; 
        gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Toko Sopia");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTitle.setForeground(Color.BLACK);
        panelKiri.add(lblTitle, gbc);

        gbc.gridy = 1;
        JLabel lblSubtitle = new JLabel("Selamat datang kembali, Wak!");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitle.setForeground(Color.GRAY);
        panelKiri.add(lblSubtitle, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelKiri.add(new JLabel(""), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelKiri.add(createLabelForm("Username"), gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtUsername = createTextField("Masukkan username");
        panelKiri.add(txtUsername, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelKiri.add(createLabelForm("Password"), gbc);

        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 15, 0);
        txtPassword = createPasswordField("Masukkan password");
        panelKiri.add(txtPassword, gbc);

        gbc.gridy = 9;
        btnLogin = new RoundedButton("Log In", 30);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(warnaUngu);
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(warnaUngu.darker());
            }

            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(warnaUngu);
            }
        });
        panelKiri.add(btnLogin, gbc);
        panelKanan = new JPanel();
        panelKanan.setBackground(warnaBackground);
        panelKanan.setLayout(new GridBagLayout());

        URL imgUrl = getClass().getResource("/img/iconTokoSopia.gif");

        if (imgUrl != null) {
            ImageIcon iconLogo = new ImageIcon(imgUrl);
            lblLogoAnimasi = new JLabel(iconLogo);
        } else {
            // Placeholder kalau file gambar gak ketemu
            lblLogoAnimasi = new JLabel("LOGO .GIF DISINI");
            lblLogoAnimasi.setFont(new Font("SansSerif", Font.BOLD, 24));
            lblLogoAnimasi.setForeground(Color.LIGHT_GRAY);
        }

        panelKanan.add(lblLogoAnimasi);

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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}