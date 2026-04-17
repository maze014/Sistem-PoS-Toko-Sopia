package model;

import java.sql.Date;

public class Barang {
    private int idBarang;
    private int idKategori;
    private String namaBarang;
    private int harga;
    private int stok; // Ini nanti isinya yang sudah di-hash
    private Date tanggalKadaluarsa;

    // Constructor Kosong (Penting buat framework atau manual set)
    public Barang() {}

    // Constructor Lengkap (Buat register/tambah user baru)
    public Barang(int idBarang, int idKategori, String namaBarang, int harga, int stok, Date tanggalKadaluarsa) {
        this.idBarang = idBarang;
        this.idKategori = idKategori;
        this.namaBarang = namaBarang;
        this.harga = harga;
        this.stok = stok;
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    // --- GETTER & SETTER (Wajib ada biar data bisa diambil/diubah) ---
    
    public int getIdBarang() { return idBarang; }
    public void setIdBarang(int idBarang) { this.idBarang = idBarang; }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public int getHarga() { return harga; }
    public void setHarga(int harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public Date getTanggalKadaluarsa() { return tanggalKadaluarsa; }
    public void setTanggalKadaluarsa(Date tanggalKadaluarsa) { this.tanggalKadaluarsa = tanggalKadaluarsa; }
}