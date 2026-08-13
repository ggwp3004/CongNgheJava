package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.TaiKhoanDAL;
import vn.edu.eaut.lab5.model.TaiKhoan;
import vn.edu.eaut.lab5.util.PasswordUtil;

import java.sql.SQLException;

public class TaiKhoanBUS {

    private final TaiKhoanDAL taiKhoanDAL = new TaiKhoanDAL();

    /**
     * Kiem tra dang nhap.
     * @return TaiKhoan neu dang nhap thanh cong
     * @throws IllegalArgumentException neu sai tai khoan / mat khau
     * @throws SQLException neu loi ket noi CSDL
     */
    public TaiKhoan login(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui long nhap ten dang nhap");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Vui long nhap mat khau");
        }

        TaiKhoan tk = taiKhoanDAL.findByUsername(username.trim());
        if (tk == null || !PasswordUtil.matches(password, tk.getPassword())) {
            throw new IllegalArgumentException("Sai ten dang nhap hoac mat khau");
        }
        return tk;
    }
}
