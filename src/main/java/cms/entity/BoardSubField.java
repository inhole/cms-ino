package cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_BOARD_SUB_FIELD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BoardSubField.BoardSubFieldId.class)
public class BoardSubField {

    @Id
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Id
    @Column(name = "NTT_NO")
    private BigDecimal nttNo;

    @Id
    @Column(name = "FIELD_CODE", length = 20)
    private String fieldCode;

    @Column(name = "FIELD_NM", length = 100)
    private String fieldNm;

    @Lob
    @Column(name = "FIELD_VALUE", columnDefinition = "LONGTEXT")
    private String fieldValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "BOARD_ID", insertable = false, updatable = false),
        @JoinColumn(name = "FIELD_CODE", insertable = false, updatable = false)
    })
    private BoardField boardField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "BOARD_ID", insertable = false, updatable = false),
        @JoinColumn(name = "NTT_NO", insertable = false, updatable = false)
    })
    private BoardSub boardSub;

    // Composite Key
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class BoardSubFieldId implements Serializable {
        private Long boardId;
        private BigDecimal nttNo;
        private String fieldCode;
    }
}
