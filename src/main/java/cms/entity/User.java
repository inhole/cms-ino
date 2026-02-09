package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_MEMBER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID", length = 100)
    private String userId;

    @Column(name = "USER_PWD", length = 100)
    private String userPwd;

    @Column(name = "USER_NAME", length = 100)
    private String userName;

    @Column(name = "USER_EMAIL", length = 100)
    private String userEmail;

    @Column(name = "PHONE", length = 30)
    private String phone;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn = true;

    @Column(name = "USER_LEVEL", length = 20)
    private String userLevel = "USER";

    @Column(name = "LAST_LOGIN_DT")
    private java.time.LocalDateTime lastLoginDt;

    @Lob
    @Column(name = "MEMO", columnDefinition = "TEXT")
    private String memo;
}
