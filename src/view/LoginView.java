package view;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    public JTextField txtUsername = new JTextField(15);
    public JPasswordField txtPassword = new JPasswordField(15);
    
    // Ini dia pilihannya, Wak!
    String[] roles = {"admin", "manajer", "kasir"};
    public JComboBox<String> cbRole = new JComboBox<>(roles);
    
    public JButton btnLogin = new JButton("Login");

    public LoginView() {
        setTitle("Login POS Toko Sopia");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Layout rapi pakai Grid
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel(" Username:")); add(txtUsername);
        add(new JLabel(" Password:")); add(txtPassword);
        add(new JLabel(" Login Sebagai:")); add(cbRole);
        add(new JLabel("")); add(btnLogin);
    }
}