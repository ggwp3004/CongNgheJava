package vn.edu.eaut.lab14.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Cau hinh Spring Security cho Lab 14.
 *
 * Bai 2: phan quyen URL cong khai / can dang nhap / chi ADMIN.
 * Bai 3: 2 tai khoan admin/user trong bo nho.
 * Bai 6: /courses/** chi ADMIN truy cap.
 * Bai 7: trang bao loi 403 tuy chinh khi khong co quyen (accessDeniedPage).
 * Bai 9: /students/delete/** chi ADMIN (thuc hien cung Bai 2).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        // URL cong khai, khong can dang nhap
                        .requestMatchers("/", "/about", "/css/**", "/js/**", "/webjars/**").permitAll()
                        .requestMatchers("/login", "/error/**").permitAll()
                        // Bai 10 (tuy chon): cho phep xem H2 console de kiem tra bang app_user
                        .requestMatchers("/h2-console/**").permitAll()

                        // Bai 6: /courses/** chi ADMIN duoc truy cap
                        .requestMatchers("/courses/**").hasRole("ADMIN")

                        // Bai 2 + Bai 9: them/xoa sinh vien chi ADMIN
                        .requestMatchers("/students/create", "/students/delete/**", "/students/edit/**")
                        .hasRole("ADMIN")

                        // Cac URL /students/** con lai: ADMIN va USER deu xem duoc
                        .requestMatchers("/students/**").hasAnyRole("ADMIN", "USER")

                        // Con lai deu phai dang nhap
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/students", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                // Bai 7: khi user khong co quyen (403) chuyen huong sang trang loi tuy chinh
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/403")
                )
                // Bai 10 (tuy chon): H2 console dung frame + form POST, can noi long CSRF/frameOptions
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    /**
     * Bai 3: cau hinh 2 tai khoan admin/user trong bo nho.
     *
     * Danh dau @Primary de day la UserDetailsService duoc Spring Security su
     * dung mac dinh. De chuyen sang Bai 10 (user luu trong CSDL), xoa bean nay
     * (hoac bo @Primary) - khi do JpaUserDetailsService se duoc su dung.
     */
    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("123456"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("123456"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
