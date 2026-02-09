package cms.common.service;

import cms.admin.admin.repository.UserRepository;
import cms.entity.Admin;
import cms.front.user.repository.FrontUserRepository;
import cms.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository adminRepository;
    private final FrontUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find admin first (TB_ADMIN)
        try {
            Admin admin = adminRepository.findByUserId(username)
                    .orElse(null);
            if (admin != null) {
                return buildAdminUserDetails(admin);
            }
        } catch (Exception e) {
            // Continue to check user
        }

        // Try to find regular user (TB_MEMBER)
        User user = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return buildUserDetails(user);
    }

    private UserDetails buildAdminUserDetails(Admin admin) {
        // Check if admin is enabled
        if (admin.getUseYn() == null || !admin.getUseYn()) {
            throw new UsernameNotFoundException("Admin is disabled: " + admin.getUserId());
        }

        // Build authorities based on admin level
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if ("ADMIN".equals(admin.getUserLevel())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("MANAGER".equals(admin.getUserLevel())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getUserId())
                .password(admin.getUserPwd())
                .authorities(authorities)
                .disabled(admin.getUseYn() == null || !admin.getUseYn())
                .build();
    }

    private UserDetails buildUserDetails(User user) {
        // Check if user is enabled
        if (user.getUseYn() == null || !user.getUseYn()) {
            throw new UsernameNotFoundException("User is disabled: " + user.getUserId());
        }

        // Regular users have ROLE_USER authority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getUserPwd())
                .authorities(authorities)
                .disabled(user.getUseYn() == null || !user.getUseYn())
                .build();
    }
}
