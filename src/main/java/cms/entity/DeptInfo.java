package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_DEPT_INFO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPT_CODE")
    private Long deptCode;

    @Column(name = "DEPT_NM", length = 4000)
    private String deptNm;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn;
}
