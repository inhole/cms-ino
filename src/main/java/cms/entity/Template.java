package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_TEMPLATE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMPLATE_ID")
    private Long templateId;

    @Column(name = "TEMPLATE_NM", length = 100)
    private String templateNm;

    @Column(name = "TEMPLATE_VALUE", length = 100)
    private String templateValue;

    @Column(name = "TEMPLATE_TYPE", length = 20)
    private String templateType;

    @Builder.Default
    @Column(name = "CONTENTS_TYPE", length = 20)
    private String contentsType = "CBOARD001";

    @Builder.Default
    @Column(name = "ONE_PAGE_SIZE")
    private Integer onePageSize = 0;

    @Column(name = "BOARD_TYPE", length = 20)
    private String boardType;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn = true;
}
