package model;

public class User {
    private int id;
    private String namaDepan;
    private String namaBelakang;
    private String username;
    private String password;
    private String role;
    public User() {}

    public User(String namaDepan, String namaBelakang, String username, String password, String role) {
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaDepan() { return namaDepan; }
    public void setNamaDepan(String namaDepan) { this.namaDepan = namaDepan; }

    public String getNamaBelakang() { return namaBelakang; }
    public void setNamaBelakang(String namaBelakang) { this.namaBelakang = namaBelakang; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getNamaLengkap() {
        return namaDepan + " " + namaBelakang;
    }
}