package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_MENU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private Long menuId;

    @Column(name = "DOMAIN_ID")
    private Long domainId;

    @Column(name = "MENU_NM", length = 2000, nullable = false)
    private String menuNm;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "OPEN_YN", length = 1)
    private Boolean openYn = false;

    @Column(name = "LANG_TYPE", length = 20, nullable = false)
    private String langType = "KR";

    @Column(name = "MENU_TYPE", length = 20)
    private String menuType;

    @Column(name = "UPPER_MENU_ID", length = 20)
    private String upperMenuId = "ROOT";

    @Column(name = "MENU_ORD", nullable = false)
    private BigDecimal menuOrd = BigDecimal.ONE;

    @Column(name = "MENU_DESC", length = 250)
    private String menuDesc;

    @Lob
    @Column(name = "MENU_CONTENTS", columnDefinition = "LONGTEXT")
    private String menuContents;

    @Column(name = "MENU_BBS_ID")
    private Long menuBoardId;

    @Column(name = "MENU_PAGE_ID")
    private Long menuPageId;

    @Column(name = "URL", length = 250)
    private String url;

    @Column(name = "INTERNAL_URL", length = 250)
    private String internalUrl;

    @Column(name = "MENU_MORE_URL")
    private String menuMoreUrl;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "GNB_MENU_YN", length = 1)
    private Boolean gnbMenuYn;

    @Column(name = "MENU_GRP")
    private BigDecimal menuGrp;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "BLANK_YN", length = 1)
    private Boolean blankYn;

    @Column(name = "MENU_TITLE", length = 200)
    private String menuTitle;

    @Lob
    @Column(name = "TITLE_DESC", columnDefinition = "TEXT")
    private String titleDesc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", insertable = false, updatable = false)
    private Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_BBS_ID", insertable = false, updatable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_PAGE_ID", insertable = false, updatable = false)
    private Page page;

}
