package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_CODE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Code extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODE_ID")
    private Long codeId;

    @Column(name = "CODE_NM", length = 60)
    private String codeNm;

    @Column(name = "CODE_DESC", length = 200)
    private String codeDesc;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn = true;
}
