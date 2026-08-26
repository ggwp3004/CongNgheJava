package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

public class AuthService {

    private final UserRepository userRepository = new UserRepository();

    /**
     * Đăng nhập: kiểm tra email/mật khẩu và tài khoản còn active hay không.
     * Lưu ý: mật khẩu trong bài lab lưu dạng chuỗi thường để phục vụ học tập,
     * hệ thống thật cần băm mật khẩu (BCrypt...) trước khi so sánh/lưu.
     */
    public User login(String email, String password) {
        if (email == null || password == null) return null;
        User user = userRepository.findByEmail(email.trim());
        if (user == null || !user.isActive()) return null;
        if (!user.getPassword().equals(password)) return null;
        return user;
    }

    public String changePassword(User currentUser, String oldPassword, String newPassword, String confirmPassword) {
        if (!currentUser.getPassword().equals(oldPassword)) {
            return "Mật khẩu cũ không đúng";
        }
        if (newPassword == null || newPassword.length() < 4) {
            return "Mật khẩu mới phải có ít nhất 4 ký tự";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "Xác nhận mật khẩu mới không khớp";
        }
        currentUser.setPassword(newPassword);
        userRepository.save(currentUser);
        return null; // null nghĩa là thành công
    }
}
