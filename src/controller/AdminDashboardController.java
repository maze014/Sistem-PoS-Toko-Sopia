package controller;

import view.AdminDashboardView;
import model.AdminDashboardModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class AdminDashboardController {
    private AdminDashboardView view;

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;

        view.resetWarnaKartu();
        view.cardPengguna.setBackground(new Color(243, 232, 255));
        
        refreshDashboardOtomatis();
        tampilkanGrafikPengguna();
        
        view.cardPengguna.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {  
                ubahWarnaKartu(view.cardPengguna);
                tampilkanGrafikPengguna();
            }
        });

        view.cardBarang.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ubahWarnaKartu(view.cardBarang);
                tampilkanGrafikBarang();
            }
        });

        view.cardKategori.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ubahWarnaKartu(view.cardKategori);
                tampilkanGrafikKategori();
            }
        });
    }

    public void refreshDashboardOtomatis() {
        view.lblAngkaPengguna.setText(String.valueOf(AdminDashboardModel.getTotalPengguna()));
        view.lblAngkaBarang.setText(String.valueOf(AdminDashboardModel.getTotalBarang()));
        view.lblAngkaKategori.setText(String.valueOf(AdminDashboardModel.getTotalKategori()));
    }

    private void tampilkanGrafikPengguna() {
        pasangGrafikKeLayar(AdminDashboardModel.getGrafikPengguna());
    }

    private void tampilkanGrafikBarang() {
        pasangGrafikKeLayar(AdminDashboardModel.getGrafikBarang());
    }

    private void tampilkanGrafikKategori() {
        pasangGrafikKeLayar(AdminDashboardModel.getGrafikKategori());
    }

    private void pasangGrafikKeLayar(JFreeChart chart) {
        view.panelWadahGrafik.removeAll(); 
        view.panelWadahGrafik.add(new ChartPanel(chart), java.awt.BorderLayout.CENTER); 
        view.panelWadahGrafik.revalidate();
        view.panelWadahGrafik.repaint();
    }

    private void ubahWarnaKartu(javax.swing.JButton kartuAktif) {
        view.resetWarnaKartu();
        kartuAktif.setBackground(new Color(243, 232, 255));
    }
}