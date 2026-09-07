package vn.edu.eaut.lab14.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.edu.eaut.lab14.model.AppUser;
import vn.edu.eaut.lab14.repository.AppUserRepository;

/**
 * Bai 10: UserDetailsService doc user tu CSDL (bang app_user) thay vi tu bo nho.
 *
 * Bean nay KHONG duoc kich hoat mac dinh. Mac dinh SecurityConfig dang dung
 * InMemoryUserDetailsManager (Bai 3) de dung dung voi code goi y trong de bai.
 *
 * De chuyen sang dung user trong CSDL (Bai 10):
 * 1. Trong SecurityConfig.java, xoa/comment bean userDetailsService() dang dung
 *    InMemoryUserDetailsManager.
 * 2. Bo @Service duoi day thanh active (no da san sang, chi can Spring thay the
 *    UserDetailsService duy nhat con lai trong context).
 * 3. Chay DataInitializer (da co san) de tao san 2 tai khoan admin/user trong H2.
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public JpaUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Khong tim thay user: " + username));

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .disabled(!appUser.isEnabled())
                .build();
    }
}
