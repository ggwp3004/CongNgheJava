package vn.edu.eaut.lab14.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Bai 10: Thay the user trong bo nho (InMemoryUserDetailsManager) bang user
 * duoc luu trong CSDL (o day dung H2 de khong can cai dat CSDL rieng).
 *
 * Mac dinh lab van chay voi user trong bo nho (xem SecurityConfig).
 * De chuyen sang dung bang nay, xem huong dan trong README.md muc "Bai 10".
 */
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // luu duoi dang da ma hoa BCrypt

    private String role; // ADMIN hoac USER (khong can tien to ROLE_)

    private boolean enabled = true;

    public AppUser() {
    }

    public AppUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
