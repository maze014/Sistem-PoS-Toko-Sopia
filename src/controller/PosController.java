package controller;

import view.PosView;
import model.Barang;

public class PosController {
    private PosView view;

    public PosController(PosView view) {
        this.view = view;
        
        // Logika saat tombol simpan diklik
        this.view.btnSimpan.addActionListener(e -> {
            String nama = view.txtNama.getText();
            int harga = Integer.parseInt(view.txtHarga.getText());
            
            Barang b = new Barang(nama, harga);
            
            // Output ke layar (Simulasi simpan database)
            view.displayArea.append("Tersimpan: " + b.getNama() + " - Rp" + b.getHarga() + "\n");
            
            // Debugging sederhana
            System.out.println("DEBUG: Data " + nama + " berhasil diproses.");
        });
    }
}