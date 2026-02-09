package cms.front.user.service;

import cms.entity.User;
import cms.front.user.dto.UserDto;
import cms.front.user.repository.FrontUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FrontUserService {

    private final FrontUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserDto findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Transactional
    public void save(UserDto dto) {
        User user;
        if (dto.getId() != null) {
            // Update existing
            user = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            updateEntity(user, dto);
        } else {
            // Create new
            user = new User();
            user.setUserLevel("USER");
            updateEntity(user, dto);
            // Encode password for new users
            if (dto.getUserPwd() != null && !dto.getUserPwd().isEmpty()) {
                user.setUserPwd(passwordEncoder.encode(dto.getUserPwd()));
            }
        }
        userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void updateEntity(User user, UserDto dto) {
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUserName());
        user.setUserEmail(dto.getUserEmail());
        user.setPhone(dto.getPhone());
        user.setUseYn(dto.getUseYn());
        user.setMemo(dto.getMemo());

        // Only update password if provided
        if (dto.getUserPwd() != null && !dto.getUserPwd().isEmpty() && dto.getId() != null) {
            user.setUserPwd(passwordEncoder.encode(dto.getUserPwd()));
        }
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUserName());
        dto.setUserEmail(user.getUserEmail());
        dto.setPhone(user.getPhone());
        dto.setUseYn(user.getUseYn());
        dto.setUserLevel(user.getUserLevel());
        dto.setLastLoginDt(user.getLastLoginDt());
        dto.setMemo(user.getMemo());
        dto.setRegDt(user.getRegDt());
        dto.setModDt(user.getModDt());
        return dto;
    }
}
