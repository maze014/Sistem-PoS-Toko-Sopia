package main;

import view.PosView;
import controller.PosController;

public class Main {
    public static void main(String[] args) {
        PosView view = new PosView();
        new PosController(view);
        view.setVisible(true);
    }
}