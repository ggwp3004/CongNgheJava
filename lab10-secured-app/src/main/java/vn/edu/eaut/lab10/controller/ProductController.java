package vn.edu.eaut.lab10.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab10.model.Product;
import vn.edu.eaut.lab10.service.ProductService;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * CRUD sản phẩm - module nghiệp vụ mẫu.
 * ADMIN và STAFF được vào /staff/* theo bảng quy tắc truy cập.
 */
@WebServlet("/staff/products")
public class ProductController extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            productService.delete(Integer.valueOf(request.getParameter("id")));
            response.sendRedirect(request.getContextPath() + "/staff/products");
            return;
        }

        if ("edit".equals(action)) {
            Product p = productService.findById(Integer.valueOf(request.getParameter("id")));
            request.setAttribute("product", p);
        }

        request.setAttribute("products", productService.findAll());
        request.getRequestDispatcher("/staff/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("id");

        Product product = new Product();
        if (idParam != null && !idParam.isBlank()) {
            product.setId(Integer.valueOf(idParam));
        }
        product.setName(request.getParameter("name"));
        product.setDescription(request.getParameter("description"));
        try {
            product.setPrice(new BigDecimal(request.getParameter("price")));
            product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Giá và số lượng phải là số hợp lệ");
            request.setAttribute("products", productService.findAll());
            request.getRequestDispatcher("/staff/products.jsp").forward(request, response);
            return;
        }

        String error = productService.save(product);
        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("products", productService.findAll());
            request.getRequestDispatcher("/staff/products.jsp").forward(request, response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/staff/products");
    }
}
