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
        // Pas tombol User Management diklik
        this.adminView.btnUserManage.addActionListener(e -> {
            // 1. Bikin View untuk User Management (Harus JPanel)
            UserManagementView userView = new UserManagementView();

            // 2. Pasang Controller-nya (Biar tombol di dalamnya hidup)
            new UserManagementController(userView);

            // 3. PANGGIL FUNGSI SAKTI TADI!
            this.adminView.tampilkanHalaman(userView);
        });

        this.adminView.btnManageBarang.addActionListener(e -> {
            BarangManagementView barangView = new BarangManagementView();
            new BarangManagementController(barangView);
            this.adminView.tampilkanHalaman(barangView);
        });

        this.adminView.btnDashboard.addActionListener(e -> {
            new AdminDashboardController(dashboardView);
            this.adminView.tampilkanHalaman(dashboardView);
        });
    }
}