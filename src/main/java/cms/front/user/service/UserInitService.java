package cms.front.user.service;

import cms.entity.User;
import cms.front.user.repository.FrontUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class UserInitService implements CommandLineRunner {

    private final FrontUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default user if not exists
        if (!userRepository.existsByUserId("user")) {
            User user = User.builder()
                    .userId("user")
                    .userPwd(passwordEncoder.encode("user1234"))
                    .userName("일반 사용자")
                    .userEmail("user@example.com")
                    .phone("010-1234-5678")
                    .useYn(true)
                    .userLevel("USER")
                    .build();

            userRepository.save(user);
            log.info("Default user created - ID: user, Password: user1234");
        }
    }
}
