package cms.admin.admin.dto;

import cms.common.dto.CommonDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminDto extends CommonDto {
    private Long id;
    private String userId;
    private String userPwd;
    private String userName;
    private String userEmail;
    private String phone;
    private Boolean useYn = true;
    private String userLevel;
    private LocalDateTime lastLoginDt;
    private String memo;
}
