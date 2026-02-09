package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_DOMAIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Domain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOMAIN_ID")
    private Long domainId;

    @Column(name = "DOMAIN_NM", nullable = false)
    private String domainNm;

    @Column(name = "DOMAIN_ADMIN_NM")
    private String domainAdminNm;

    @Column(name = "DOMAIN_URL", length = 100)
    private String domainUrl;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "USE_YN", length = 1)
    private Boolean useYn;

    @Column(name = "CMS_CONN", length = 1)
    private String cmsConn;

    @Column(name = "SEO_TITLE", length = 100)
    private String seoTitle;

    @Lob
    @Column(name = "SEO_DESCRIPTION", columnDefinition = "TEXT")
    private String seoDescription;

    @Lob
    @Column(name = "SEO_KEYWORD", columnDefinition = "TEXT")
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
}
