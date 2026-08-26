package vn.edu.eaut.lab10.service;

import vn.edu.eaut.lab10.model.Product;
import vn.edu.eaut.lab10.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProductService {

    private final ProductRepository productRepository = new ProductRepository();

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id);
    }

    /** Trả về thông báo lỗi, hoặc null nếu hợp lệ và lưu thành công */
    public String save(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            return "Tên sản phẩm không được để trống";
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return "Giá sản phẩm phải >= 0";
        }
        if (product.getQuantity() < 0) {
            return "Số lượng không được âm";
        }
        productRepository.save(product);
        return null;
    }

    public void delete(Integer id) {
        productRepository.delete(id);
    }
}
