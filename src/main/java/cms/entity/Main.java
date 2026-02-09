package cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_MAIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Main extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAIN_ID")
    private Long mainId;

    @Column(name = "DOMAIN_ID", nullable = false)
    private Long domainId;

    @Column(name = "VISUAL_BANNER_ID")
    private Long visualBannerId;

    @Column(name = "BANNER_ID")
    private Long bannerId;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "CONNECT_BOARD_ID_01")
    private Long connectBoardId01;

    @Column(name = "CONNECT_BOARD_ID_02")
    private Long connectBoardId02;

    @Column(name = "CONNECT_BOARD_ID_03")
    private Long connectBoardId03;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", insertable = false, updatable = false)
    private Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VISUAL_BANNER_ID", insertable = false, updatable = false)
    private Banner visualBanner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANNER_ID", insertable = false, updatable = false)
    private Banner banner;
}
