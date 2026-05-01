package controller;

import view.AdminView;
import view.UserManagementView;
import view.BarangManagementView;
import view.AdminDashboardView;

public class AdminController {
    private AdminView adminView;

    public AdminController(AdminView adminView) {
        this.adminView = adminView;

        AdminDashboardView dashboardView = new AdminDashboardView();
        new AdminDashboardController(dashboardView);
        this.adminView.tampilkanHalaman(dashboardView);

        // tombol user management
        this.adminView.btnUserManage.addActionListener(e -> {
            UserManagementView userView = new UserManagementView();
            new UserManagementController(userView);
            this.adminView.tampilkanHalaman(userView);
        });

        // tombol barang management
        this.adminView.btnManageBarang.addActionListener(e -> {
            BarangManagementView barangView = new BarangManagementView();
            new BarangManagementController(barangView, "Admin");
            this.adminView.tampilkanHalaman(barangView);
        });

        // tombol dashboard admin
        this.adminView.btnDashboard.addActionListener(e -> {
            new AdminDashboardController(dashboardView);
            this.adminView.tampilkanHalaman(dashboardView);
        });
    }
}