package cms.front.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userId;
    private String userPwd;
    private String userName;
    private String userEmail;
    private String phone;
    private Boolean useYn;
    private String userLevel;
    private LocalDateTime lastLoginDt;
    private String memo;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
