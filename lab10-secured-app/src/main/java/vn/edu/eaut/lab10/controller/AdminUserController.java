package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Role;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.repository.UserRepository;

import java.io.IOException;

/**
 * CRUD tài khoản cho ADMIN: thêm, sửa họ tên/vai trò, khóa/mở tài khoản, tìm theo email.
 * Chỉ ADMIN mới vào được nhờ AuthenticationFilter + AuthorizationFilter chặn /admin/*.
 */
@WebServlet("/admin/users")
public class AdminUserController extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String keyword = request.getParameter("q");

        if ("toggle".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            User u = userRepository.findById(id);
            if (u != null) {
                u.setActive(!u.isActive());
                userRepository.save(u);
            }
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        if ("delete".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            userRepository.delete(id);
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        if ("edit".equals(action)) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            request.setAttribute("editUser", userRepository.findById(id));
        }

        var users = userRepository.findAll();
        if (keyword != null && !keyword.isBlank()) {
            users = users.stream()
                    .filter(u -> u.getEmail().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
        }

        request.setAttribute("users", users);
        request.setAttribute("roles", Role.values());
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String password = request.getParameter("password");
        Role role = Role.valueOf(request.getParameter("role"));

        User user;
        if (idParam == null || idParam.isBlank()) {
            if (userRepository.findByEmail(email) != null) {
                request.setAttribute("error", "Email đã tồn tại");
                request.setAttribute("users", userRepository.findAll());
                request.setAttribute("roles", Role.values());
                request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
                return;
            }
            user = new User(email, password, fullName, role);
        } else {
            user = userRepository.findById(Integer.valueOf(idParam));
            user.setFullName(fullName);
            user.setRole(role);
            if (password != null && !password.isBlank()) {
                user.setPassword(password);
            }
        }
        userRepository.save(user);
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
