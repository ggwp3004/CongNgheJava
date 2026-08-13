package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class ThongKeDAL {

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) AS doanh_thu " +
                "FROM hoa_don WHERE ngay_lap BETWEEN ? AND ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(tuNgay));
            ps.setDate(2, Date.valueOf(denNgay));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getBigDecimal("doanh_thu");
            }
        }
        return BigDecimal.ZERO;
    }

    public String hoaDonCaoNhat() throws SQLException {
        String sql = "SELECT hd.ma_hd, hd.ngay_lap, kh.ten_kh, hd.tong_tien " +
                "FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh = kh.ma_kh " +
                "ORDER BY hd.tong_tien DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return String.format("Hoa don #%d - Ngay %s - KH: %s - Tong tien: %s VND",
                        rs.getInt("ma_hd"), rs.getDate("ngay_lap"),
                        rs.getString("ten_kh"), rs.getBigDecimal("tong_tien"));
            }
        }
        return "Chua co du lieu hoa don";
    }

    public String sanPhamBanChayNhat() throws SQLException {
        String sql = "SELECT sp.ma_sp, sp.ten_sp, SUM(ct.so_luong) AS tong_so_luong " +
                "FROM chi_tiet_hoa_don ct JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                "GROUP BY sp.ma_sp, sp.ten_sp ORDER BY tong_so_luong DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return String.format("%s (da ban %d san pham)",
                        rs.getString("ten_sp"), rs.getInt("tong_so_luong"));
            }
        }
        return "Chua co du lieu ban hang";
    }
}
