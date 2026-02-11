package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_BOARD_FIELD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BoardField.BoardFieldId.class)
public class BoardField {

    @Id
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Id
    @Column(name = "FIELD_CODE", length = 20)
    private String fieldCode;

    @Column(name = "FIELD_NM", length = 100)
    private String fieldNm;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "REQUIRED_YN", length = 1)
    private Boolean requiredYn = false;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn = false;

    @Column(name = "FIELD_ORD")
    private BigDecimal fieldOrd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", insertable = false, updatable = false)
    private Board board;

    // Composite Key
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class BoardFieldId implements Serializable {
        private Long boardId;
        private String fieldCode;
    }
}
