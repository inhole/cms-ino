package cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_BOARD_CATEGORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "BOARD_ID", nullable = false)
    private Long boardId;

    @Column(name = "BOARD_SN")
    private BigDecimal boardSn;

    @Column(name = "CATEGORY_NM")
    private String categoryNm;

    @Lob
    @Column(name = "EMAIL", columnDefinition = "LONGTEXT")
    private String email;

    @Column(name = "SORT_ORD", nullable = false)
    private BigDecimal sortOrd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID", insertable = false, updatable = false)
    private Board board;
}
