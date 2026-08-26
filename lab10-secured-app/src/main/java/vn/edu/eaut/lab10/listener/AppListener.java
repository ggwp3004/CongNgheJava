package vn.edu.eaut.lab10.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab10.config.JPAUtil;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Ép EntityManagerFactory khởi tạo ngay khi ứng dụng start
        JPAUtil.getEntityManagerFactory();

        // Bài 6: sinh tài khoản mẫu ban đầu nếu CSDL chưa có tài khoản nào
        UserRepository userRepository = new UserRepository();
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin@eaut.edu.vn", "admin123", "Quản trị viên", Role.ADMIN));
            userRepository.save(new User("staff@eaut.edu.vn", "staff123", "Nhân viên nghiệp vụ", Role.STAFF));
            userRepository.save(new User("user@eaut.edu.vn", "user123", "Người dùng thường", Role.USER));
            sce.getServletContext().log("Đã tạo tài khoản mẫu: admin/staff/user (mật khẩu tương ứng ...123)");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.close();
    }
}
