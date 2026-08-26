package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;
import vn.edu.eaut.lab10.service.AuthService;

import java.io.IOException;

@WebServlet("/user/profile")
public class ProfileController extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        String formType = request.getParameter("formType");

        if ("updateInfo".equals(formType)) {
            String fullName = request.getParameter("fullName");
            if (fullName == null || fullName.isBlank()) {
                request.setAttribute("error", "Họ tên không được để trống");
            } else {
                currentUser.setFullName(fullName);
                userRepository.save(currentUser);
                session.setAttribute("currentUser", currentUser);
                request.setAttribute("success", "Cập nhật hồ sơ thành công");
            }
        } else if ("changePassword".equals(formType)) {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            String error = authService.changePassword(currentUser, oldPassword, newPassword, confirmPassword);
            if (error != null) {
                request.setAttribute("error", error);
            } else {
                session.setAttribute("currentUser", currentUser);
                request.setAttribute("success", "Đổi mật khẩu thành công");
            }
        }

        request.getRequestDispatcher("/user/profile.jsp").forward(request, response);
    }
}
