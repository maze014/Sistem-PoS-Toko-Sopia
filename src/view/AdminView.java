package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.AdminController;
import controller.LoginController;

import java.awt.*;

public class AdminView extends JFrame {
    Color sidebarColor = new Color(0, 204, 204); 
    Color bgColor = new Color(255, 255, 255);
    public JButton btnDashboard, btnUserManage, btnManageBarang, btnLogout;
    JPanel content = new JPanel(new BorderLayout());

    public AdminView(String namaUser, String role) {
        setTitle("Sopia POS - Dashboard " + role);
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

        btnDashboard = addMenu(sidebar, "Dashboard");
        btnUserManage = addMenu(sidebar, "User Management");
        btnManageBarang = addMenu(sidebar, "Management Barang");

        new AdminController(this);

        sidebar.add(Box.createVerticalGlue());
        btnLogout = addMenu(sidebar, "Logout");
        btnLogout.addActionListener(e -> {
            this.dispose();
            LoginView loginBaru = new LoginView();
            new LoginController(loginBaru);
            loginBaru.setVisible(true);
        });

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
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        return btn;
    }
}

class StatCard extends JPanel {
    public StatCard(String title, String value, Color color) {
        setLayout(new BorderLayout());
        setBackground(color);
        setPreferredSize(new Dimension(200, 120));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblVal = new JLabel(value);
        lblVal.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblVal.setForeground(Color.WHITE);

        add(lblTitle, BorderLayout.NORTH);
        add(lblVal, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
    }
}