package controller;

import view.StokBarangView;
import model.StokBarangModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class StokBarangController {
    private StokBarangView view;

    public StokBarangController(StokBarangView view) {
        this.view = view;
        
        renderCards("");

        view.txtCari.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { renderCards(view.txtCari.getText()); }
            public void removeUpdate(DocumentEvent e) { renderCards(view.txtCari.getText()); }
            public void changedUpdate(DocumentEvent e) { renderCards(view.txtCari.getText()); }
        });
    }

    private void renderCards(String keyword) {
        view.panelDaftarStok.removeAll();
        
        List<Object[]> data = StokBarangModel.cariBarang(keyword);
        
        for (Object[] b : data) {
            int id = (int) b[0];
            String nama = (String) b[1];
            int stok = (int) b[2];
            String harga = (String) b[3];
            
            view.panelDaftarStok.add(view.buatCard(id, nama, stok, harga));
        }

        view.panelDaftarStok.revalidate();
        view.panelDaftarStok.repaint();
    }
}