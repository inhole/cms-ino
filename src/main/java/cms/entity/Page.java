package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_PAGE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page extends BaseEntity {

    @Id
    @Column(name = "PAGE_ID", length = 45)
    private String pageId;

    @Column(name = "DOMAIN_ID", length = 20)
    private String domainId;

    @Column(name = "PAGE_NM")
    private String pageNm;

    @Column(name = "PAGE_VIEW_NM")
    private String pageViewNm;

    @Column(name = "PAGE_DESC")
    private String pageDesc;

    @Column(name = "ATCH_FILE_ID", length = 20)
    private String atchFileId;

    @Column(name = "KEYWORD")
    private String keyword;

    @Lob
    @Column(name = "PAGE_CONTENTS", columnDefinition = "LONGTEXT")
    private String pageContents;

    @Column(name = "PAGE_AUTH_CONN", length = 100)
    private String pageAuthConn;

    @Column(name = "TOP_TEMPLATE_ID", length = 20)
    private Long topTemplateId;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "TAP_MENU_YN", length = 1)
    private Boolean tapMenuYn;

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
    @JoinColumn(name = "TOP_TEMPLATE_ID", insertable = false, updatable = false)
    private Template topTemplate;
}
