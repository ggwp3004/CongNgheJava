package vn.edu.eaut.lab14.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import vn.edu.eaut.lab14.model.AppUser;
import vn.edu.eaut.lab14.repository.AppUserRepository;

/**
 * Bai 10: Tao san 2 tai khoan admin/user trong bang app_user (H2) khi ung dung
 * khoi dong, de sinh vien co the kiem thu JpaUserDetailsService.
 * (Khong anh huong gi neu van dang dung InMemoryUserDetailsManager mac dinh.)
 */
@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedUsers(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.findByUsername("admin").isEmpty()) {
                repository.save(new AppUser("admin", passwordEncoder.encode("123456"), "ADMIN"));
            }
            if (repository.findByUsername("user").isEmpty()) {
                repository.save(new AppUser("user", passwordEncoder.encode("123456"), "USER"));
            }
        };
    }
}
