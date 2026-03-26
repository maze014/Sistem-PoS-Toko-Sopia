package main;
import view.LoginView;
import controller.LoginController;

public class Main {
    public static void main(String[] args) {
        // Look and Feel Biar Keren
        try { javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); } 
        catch (Exception e) {}

        LoginView v = new LoginView();
        new LoginController(v);
        v.setVisible(true);
    }
}