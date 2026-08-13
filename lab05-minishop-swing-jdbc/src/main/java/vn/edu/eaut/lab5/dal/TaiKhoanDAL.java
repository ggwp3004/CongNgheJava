package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAL {

    public TaiKhoan findByUsername(String username) throws SQLException {
        String sql = "SELECT ma_tk, username, password, ho_ten, vai_tro FROM tai_khoan WHERE username = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public boolean updatePassword(int maTk, String newHashedPassword) throws SQLException {
        String sql = "UPDATE tai_khoan SET password = ? WHERE ma_tk = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHashedPassword);
            ps.setInt(2, maTk);
            return ps.executeUpdate() > 0;
        }
    }

    private TaiKhoan map(ResultSet rs) throws SQLException {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTk(rs.getInt("ma_tk"));
        tk.setUsername(rs.getString("username"));
        tk.setPassword(rs.getString("password"));
        tk.setHoTen(rs.getString("ho_ten"));
        tk.setVaiTro(rs.getString("vai_tro"));
        return tk;
    }
}
