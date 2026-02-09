package cms.admin.admin.service;

import cms.admin.admin.repository.UserRepository;
import cms.entity.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminInitService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default admin user if not exists
        if (!userRepository.existsByUserId("admin")) {
            Admin admin = Admin.builder()
                    .userId("admin")
                    .userPwd(passwordEncoder.encode("admin1234"))
                    .userName("시스템 관리자")
                    .userEmail("admin@example.com")
                    .phone("010-0000-0000")
                    .useYn(true)
                    .userLevel("ADMIN")
                    .build();

            userRepository.save(admin);
            log.info("Default admin user created - ID: admin, Password: admin1234");
        }
    }
}
