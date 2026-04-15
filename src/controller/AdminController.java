package controller;

import view.AdminDashboard;
import view.RegisterView;

public class AdminController {
    private AdminDashboard adminView;
    public AdminController(AdminDashboard adminView) {
        this.adminView = adminView;

        // Tombol di DASHBOARD yang manggil FORM REGISTER
        this.adminView.btnRegisterBaru.addActionListener(e -> {
            System.out.println("Tombol diklik!");
            RegisterView regView = new RegisterView(adminView); // Buat View-nya
            new RegisterController(regView); // Kasih Otaknya
            regView.setVisible(true); // Munculin Form-nya
        });
    }
}