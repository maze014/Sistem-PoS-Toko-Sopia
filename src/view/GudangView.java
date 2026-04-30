package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.GudangController;
import controller.LoginController;

import java.awt.*;

public class GudangView extends JFrame {
    Color sidebarColor = new Color(44, 62, 80); 
    Color bgColor = new Color(255, 255, 255); 
    public JButton btnDashboard, btnManageBarang, btnLogout;
    public JPanel content = new JPanel(new BorderLayout());

    public GudangView(String namaUser) {
        setTitle("Sopia POS - Petugas Gudang (" + namaUser + ")");
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

        JLabel lblLogo = new JLabel("GUDANG POS");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 22));
        sidebar.add(lblLogo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        btnDashboard = addMenu(sidebar, "Dashboard Gudang");
        btnManageBarang = addMenu(sidebar, "Manajemen Barang");

        new GudangController(this);

        sidebar.add(Box.createVerticalGlue());
        btnLogout = addMenu(sidebar, "Logout");
        btnLogout.addActionListener(e -> {
            this.dispose();
            LoginView loginBaru = new LoginView(); 
            new LoginController(loginBaru);
            loginBaru.setVisible(true);
        });

        new GudangController(this);

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
        btn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        return btn;
    }
}