package controller;

import model.User;
import model.UserManagement;
import view.UserManagementView;
import view.RegisterView;
import view.EditView;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import utils.RoundedBorder;

public class UserManagementController {
    private UserManagementView view;

    public UserManagementController(UserManagementView view) {
        this.view = view;
        refreshData();

        this.view.btnTambah.addActionListener(e -> {
            RegisterView regView = new RegisterView((JFrame) SwingUtilities.getWindowAncestor(view));
            new RegisterController(regView);
            regView.setVisible(true);
            refreshData();
        });
    }

    public void refreshData() {
        view.panelDaftarUser.removeAll();
        List<User> users = UserManagement.getAllUsers();

        for (User u : users) {
            JPanel card = buatCardUser(u); 
            view.panelDaftarUser.add(card); 
            view.panelDaftarUser.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        view.panelDaftarUser.revalidate();
        view.panelDaftarUser.repaint();
    }

    private JPanel buatCardUser(User u) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(new RoundedBorder(15, new Color(200, 200, 200)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        card.setBorder(BorderFactory.createCompoundBorder(
                card.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel lblNama = new JLabel(u.getNamaDepan() + " " + u.getNamaBelakang());
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel lblRole = new JLabel(u.getRole() + " | " + u.getUsername());
        lblRole.setForeground(Color.GRAY);

        infoPanel.add(lblNama);
        infoPanel.add(lblRole);

        JPanel aksiPanel = new JPanel(new GridBagLayout());
        aksiPanel.setOpaque(false);

        JPanel wadahTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        wadahTombol.setOpaque(false);

        JButton btnEdit = new JButton("Edit");
        JButton btnDelete = new JButton("Hapus");
        btnDelete.setForeground(Color.RED);

        btnEdit.addActionListener(e -> {
            EditView editPopUp = new EditView((JFrame) SwingUtilities.getWindowAncestor(view));
            editPopUp.setTitle("Edit User: " + u.getUsername());
            editPopUp.txtNamaDepan.setText(u.getNamaDepan());
            editPopUp.txtNamaDepan.setForeground(Color.BLACK);
            editPopUp.txtNamaBelakang.setText(u.getNamaBelakang());
            editPopUp.txtNamaBelakang.setForeground(Color.BLACK);
            editPopUp.txtUsername.setText(u.getUsername());
            editPopUp.txtUsername.setForeground(Color.BLACK);
            editPopUp.cbRole.setSelectedItem(u.getRole());

            new EditController(editPopUp, u.getUsername());
            editPopUp.setVisible(true);
            refreshData();
        });
    
        btnDelete.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(view,
                    "Yakin mau hapus user " + u.getNamaDepan() + "?",
                    "Hapus User", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                boolean berhasil = UserManagement.deleteUser(u.getUsername());

                if (berhasil) {
                    JOptionPane.showMessageDialog(view,
                            "User " + u.getUsername() + " berhasil dimusnahkan!",
                            "Sukses", JOptionPane.INFORMATION_MESSAGE);

                    refreshData();
                } else {
                    JOptionPane.showMessageDialog(view,
                            "Gagal menghapus user! Cek koneksi database Wak.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        wadahTombol.add(btnEdit);
        wadahTombol.add(btnDelete);

        aksiPanel.add(wadahTombol);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(aksiPanel, BorderLayout.EAST);

        return card;
    }
}