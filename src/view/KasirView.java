package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.KasirController;
import controller.LoginController;
import java.awt.*;

public class KasirView extends JFrame {
    Color sidebarColor = new Color(13, 148, 136); 
    
    public JButton btnDashboard, btnTransaksi, btnRiwayat, btnStok, btnLogout;
    public JPanel content = new JPanel(new BorderLayout());
    public int idUserAktif = 1; 

    public KasirView(String namaUser, int idUser) {
        this.idUserAktif = idUser;
        
        setTitle("Sopia POS - Kasir " + namaUser);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));
        setResizable(true); 

        this.addWindowStateListener(e -> {
            if ((e.getOldState() & Frame.MAXIMIZED_BOTH) != 0 &&
                    (e.getNewState() & Frame.MAXIMIZED_BOTH) == 0) {
                SwingUtilities.invokeLater(() -> setLocationRelativeTo(null));
            }
        });

        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(260, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel lblLogo = new JLabel("SOPIA POS");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 22));
        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        btnDashboard = addMenu(sidebar, "Dashboard Kasir");
        btnTransaksi = addMenu(sidebar, "Transaksi Baru");
        btnRiwayat = addMenu(sidebar, "Riwayat Penjualan");
        btnStok = addMenu(sidebar, "Cek Stok Barang");

        sidebar.add(Box.createVerticalGlue());
        btnLogout = addMenu(sidebar, "Logout");
        
        btnLogout.addActionListener(e -> {
            this.dispose();
            LoginView loginBaru = new LoginView();
            new LoginController(loginBaru);
            loginBaru.setVisible(true);
        });

        new KasirController(this);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
    }

    public void tampilkanHalaman(JPanel panelBaru) {
        content.removeAll();
        content.setLayout(new BorderLayout());
        content.add(panelBaru, BorderLayout.CENTER);
        content.revalidate(); 
        content.repaint(); 
    }

    private JButton addMenu(JPanel panel, String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return btn;
    }
}