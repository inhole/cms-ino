package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_BOARD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long boardId;

    @Column(name = "DOMAIN_ID")
    private Long domainId;

    @Column(name = "BOARD_NM", nullable = false)
    private String boardNm;

    @Column(name = "BOARD_VIEW_NM")
    private String boardViewNm;

    @Column(name = "BOARD_DESC")
    private String boardDesc;

    @Column(name = "TOP_TEMPLATE_ID")
    private Long topTemplateId;

    @Column(name = "CONTENT_TEMPLATE_ID")
    private Long contentTemplateId;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn = true;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "TAP_MENU_YN", length = 1)
    private Boolean tapMenuYn;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "FILE_YN", length = 1)
    private Boolean fileYn;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "BLIND_YN", length = 1)
    private Boolean blindYn;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "CATEGORY_YN", length = 1)
    private Boolean categoryYn = true;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "PUSH_YN", length = 1)
    private Boolean pushYn;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "HEADER_YN", length = 1)
    private Boolean headerYn;

    @Lob
    @Column(name = "HEADER_CONTENTS", columnDefinition = "LONGTEXT")
    private String headerContents;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "POST_YN", length = 1)
    private Boolean postYn = true;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "REPLY_YN", length = 1)
    private Boolean replyYn;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "TOTAL_SEARCH_YN", length = 1)
    private Boolean totalSearchYn = false;

    @Column(name = "BOARD_AUTH_CONN", length = 20)
    private String boardAuthConn;

    @Column(name = "BOARD_AUTH_OPEN", length = 20)
    private String boardAuthOpen;

    @Column(name = "BOARD_AUTH_POST", length = 20)
    private String boardAuthPost;

    @Column(name = "BOARD_AUTH_REPLY", length = 20)
    private String boardAuthReply;

    @Column(name = "TEMPLATE_ID", length = 20)
    private String templateId;

    @Column(name = "RETURN_URL", length = 1000)
    private String returnUrl;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "BOARD_MORE_BTN_YN", length = 1)
    private Boolean boardMoreBtnYn = false;

    @Column(name = "BOARD_BTN_NM")
    private String boardBtnNm;

    @Column(name = "BOARD_BTN_URL")
    private String boardBtnUrl;

    @Column(name = "ADMIN_MAIL")
    private String adminMail;

    @Lob
    @Column(name = "BOTTOM_CONTENTS", columnDefinition = "LONGTEXT")
    private String bottomContents;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "BOTTOM_YN", length = 1)
    private Boolean bottomYn;

    @Column(name = "SEO_TITLE", length = 300)
    private String seoTitle;

    @Lob
    @Column(name = "SEO_DESCRIPTION", columnDefinition = "LONGTEXT")
    private String seoDescription;

    @Lob
    @Column(name = "SEO_KEYWORD", columnDefinition = "LONGTEXT")
    private String seoKeyword;

    @Lob
    @Column(name = "SEO_OG_DESCRIPTION", columnDefinition = "TEXT")
    private String seoOgDescription;

    @Lob
    @Column(name = "SEO_OG_SITE_NAME", columnDefinition = "TEXT")
    private String seoOgSiteName;

    @Lob
    @Column(name = "SEO_NAVER_VERIFY", columnDefinition = "TEXT")
    private String seoNaverVerify;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", insertable = false, updatable = false)
    private Domain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOP_TEMPLATE_ID", insertable = false, updatable = false)
    private Template topTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_TEMPLATE_ID", insertable = false, updatable = false)
    private Template contentTemplate;
}
