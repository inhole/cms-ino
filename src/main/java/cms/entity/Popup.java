package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_POPUP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Popup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POP_ID")
    private Long popId;

    @Column(name = "DOMAIN_ID")
    private Long domainId;

    @Column(name = "POP_TITLE", length = 2000, nullable = false)
    private String popTitle;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "OPEN_YN", length = 1)
    private Boolean openYn = true;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "PERIOD_YN", length = 1)
    private Boolean periodYn = false;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "END_YN", length = 1)
    private Boolean endYn = true;

    @Column(name = "PERIOD_START_DATE")
    private java.time.LocalDateTime periodStartDate;

    @Column(name = "PERIOD_END_DATE")
    private java.time.LocalDateTime periodEndDate;

    @Column(name = "ATCH_FILE_ID", length = 20)
    private String atchFileId;

    @Column(name = "MOVE_URL", length = 2000)
    private String moveUrl;

    @Column(name = "SEQ_NUM")
    private Integer seqNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", insertable = false, updatable = false)
    private Domain domain;
}
