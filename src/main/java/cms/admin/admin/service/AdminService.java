package cms.admin.admin.service;

import cms.admin.admin.dto.AdminDto;
import cms.admin.admin.repository.UserRepository;
import cms.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AdminDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AdminDto> findAdmins() {
        return userRepository.findByUserLevelOrderByIdDesc("ADMIN").stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AdminDto> findUsers() {
        return userRepository.findByUserLevelNotOrderByIdDesc("ADMIN").stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AdminDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void save(AdminDto dto) {
        validate(dto);
        Admin admin;
        if (dto.getId() != null) {
            // Update existing
            admin = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            updateEntity(admin, dto);
        } else {
            // Create new
            admin = new Admin();
            updateEntity(admin, dto);
            // Encode password for new users
            if (dto.getUserPwd() != null && !dto.getUserPwd().isEmpty()) {
                admin.setUserPwd(passwordEncoder.encode(dto.getUserPwd()));
            }
        }
        userRepository.save(admin);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void updateEntity(Admin admin, AdminDto dto) {
        admin.setUserId(dto.getUserId());
        admin.setUserName(dto.getUserName());
        admin.setUserEmail(dto.getUserEmail());
        admin.setPhone(dto.getPhone());
        admin.setUseYn(dto.getUseYn());
        admin.setUserLevel(dto.getUserLevel() == null ? null : dto.getUserLevel().toUpperCase(Locale.ROOT));
        admin.setMemo(dto.getMemo());

        // Only update password if provided
        if (dto.getUserPwd() != null && !dto.getUserPwd().isEmpty() && dto.getId() != null) {
            admin.setUserPwd(passwordEncoder.encode(dto.getUserPwd()));
        }
    }

    private void validate(AdminDto dto) {
        if (dto.getUserId() == null || dto.getUserId().isBlank()) {
            throw new IllegalArgumentException("User ID is required");
        }

        if (dto.getId() == null && (dto.getUserPwd() == null || dto.getUserPwd().isBlank())) {
            throw new IllegalArgumentException("Password is required for new users");
        }

        boolean duplicate = dto.getId() == null
                ? userRepository.existsByUserId(dto.getUserId())
                : userRepository.existsByUserIdAndIdNot(dto.getUserId(), dto.getId());

        if (duplicate) {
            throw new IllegalArgumentException("User ID already exists");
        }
    }

    private AdminDto convertToDto(Admin admin) {
        AdminDto dto = new AdminDto();
        dto.setId(admin.getId());
        dto.setUserId(admin.getUserId());
        dto.setUserName(admin.getUserName());
        dto.setUserEmail(admin.getUserEmail());
        dto.setPhone(admin.getPhone());
        dto.setUseYn(admin.getUseYn());
        dto.setUserLevel(admin.getUserLevel());
        dto.setLastLoginDt(admin.getLastLoginDt());
        dto.setMemo(admin.getMemo());
        dto.setRegDt(admin.getRegDt());
        dto.setModDt(admin.getModDt());
        return dto;
    }
}
