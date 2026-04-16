package controller;

import view.AdminDashboard;
import view.UserManagementView;

public class AdminController {
    private AdminDashboard adminView;

    public AdminController(AdminDashboard adminView) {
        this.adminView = adminView;

        // Pas tombol User Management diklik
        this.adminView.btnUserManage.addActionListener(e -> {
            // 1. Bikin View untuk User Management (Harus JPanel)
            UserManagementView userView = new UserManagementView();

            // 2. Pasang Controller-nya (Biar tombol di dalamnya hidup)
            new UserManagementController(userView);

            // 3. PANGGIL FUNGSI SAKTI TADI!
            this.adminView.tampilkanHalaman(userView);
        });
    }
}