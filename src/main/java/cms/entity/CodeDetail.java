package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_CODE_DETAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(CodeDetail.CodeDetailId.class)
public class CodeDetail extends BaseEntity {

    @Id
    @Column(name = "CODE_ID")
    private Long codeId;

    @Id
    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "CODE_NM", length = 60)
    private String codeNm;

    @Column(name = "CODE_DESC", length = 200)
    private String codeDesc;

    @Column(name = "SORT_ORD")
    private BigDecimal sortOrd;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODE_ID", insertable = false, updatable = false)
    private Code codeEntity;

    // Composite Key
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class CodeDetailId implements Serializable {
        private Long codeId;
        private String code;
    }
}
