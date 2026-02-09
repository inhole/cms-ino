package cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_LOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", length = 20)
    private String userId;

    @Column(name = "USER_NAME", length = 20)
    private String userName;

    @Column(name = "LOG_IP")
    private String logIp;

    @Column(name = "REG_DT", nullable = false)
    private java.time.LocalDateTime regDt;
}
