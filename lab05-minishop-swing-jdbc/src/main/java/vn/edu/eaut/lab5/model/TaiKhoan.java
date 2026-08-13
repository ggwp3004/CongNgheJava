package vn.edu.eaut.lab5.model;

public class TaiKhoan {
    private int maTk;
    private String username;
    private String password; // luu duoi dang hash SHA-256
    private String hoTen;
    private String vaiTro; // ADMIN | NHANVIEN

    public TaiKhoan() {}

    public TaiKhoan(int maTk, String username, String password, String hoTen, String vaiTro) {
        this.maTk = maTk;
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
    }

    public int getMaTk() { return maTk; }
    public void setMaTk(int maTk) { this.maTk = maTk; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(vaiTro);
    }

    @Override
    public String toString() {
        return hoTen + " (" + username + ")";
    }
}
