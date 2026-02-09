package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_BOARD_SUB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BoardSub.BoardSubId.class)
public class BoardSub extends BaseEntity {

    @Id
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Id
    @Column(name = "NTT_NO")
    private BigDecimal nttNo;

    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "NTT_UPPER_NO")
    private BigDecimal nttUpperNo;

    @Column(name = "LEVEL")
    private BigDecimal level;

    @Lob
    @Column(name = "NTT_ANSWER", columnDefinition = "LONGTEXT")
    private String nttAnswer;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "NTT_ANSWER_YN", length = 1)
    private Boolean nttAnswerYn = false;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "TOP_YN", length = 1)
    private Boolean topYn = false;

    @Column(name = "CNT")
    private BigDecimal cnt = BigDecimal.ZERO;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "DEL_YN", length = 1)
    private Boolean delYn = false;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "OPEN_YN", length = 1)
    private Boolean openYn = true;

    @Column(name = "SEQ_NUM")
    private Integer seqNum;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "TEMP_SAVE_YN", length = 1)
    private Boolean tempSaveYn = false;

    @Lob
    @Column(name = "MEMO", columnDefinition = "TEXT")
    private String memo;

    @Column(name = "INQ_STATUS_TYPE", length = 20)
    private String inqStatusType;

    @Column(name = "RECEIPT_ID", length = 20)
    private String receiptId;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "REPLY_YN", length = 1)
    private Boolean replyYn = true;

    @Column(name = "SEO_TITLE", length = 300)
    private String seoTitle;

    @Lob
    @Column(name = "SEO_DESCRIPTION", columnDefinition = "LONGTEXT")
    private String seoDescription;

    @Lob
    @Column(name = "SEO_KEYWORD", columnDefinition = "LONGTEXT")
    private String seoKeyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", insertable = false, updatable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
    private BoardCategory category;

    // Composite Key
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class BoardSubId implements Serializable {
        private Long boardId;
        private BigDecimal nttNo;
    }
}
