package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.AuthService;

import java.io.IOException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = authService.login(email, password);
        if (user == null) {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng, hoặc tài khoản đã bị khóa");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("currentUser", user);
        response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if ("logout".equals(request.getParameter("action"))) {
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
