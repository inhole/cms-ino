package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_BANNER_SUB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(BannerSub.BannerSubId.class)
public class BannerSub extends BaseEntity {

    @Id
    @Column(name = "BANNER_ID")
    private Long bannerId;

    @Id
    @Column(name = "SUB_NO", length = 20)
    private String subNo;

    @Column(name = "SUB_ID", length = 20)
    private String subId;

    @Column(name = "BANNER_TITLE")
    private String bannerTitle;

    @Column(name = "SET_TYPE", length = 20)
    private String setType;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "OPEN_YN", length = 1)
    private Boolean openYn;

    @Column(name = "BANNER_IMG_PC", length = 20)
    private String bannerImgPc;

    @Column(name = "BANNER_IMG_MO", length = 20)
    private String bannerImgMo;

    @Column(name = "SORT_ORD")
    private Integer sortOrd;

    @Column(name = "LINK_URL", length = 100)
    private String linkUrl;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "LINK_BLK_YN", length = 1)
    private Boolean linkBlkYn = false;

    @Column(name = "THUM_TITLE")
    private String thumTitle;

    @Column(name = "THUM_SUB_TITLE")
    private String thumSubTitle;

    @Builder.Default
    @Column(name = "TEXT_ALIGN", length = 1)
    private String textAlign = "L";

    @Builder.Default
    @Column(name = "MO_EFF", length = 6)
    private String moEff = "N";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BANNER_ID", insertable = false, updatable = false)
    private Banner banner;

    // Composite Key
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class BannerSubId implements Serializable {
        private Long bannerId;
        private String subNo;
    }
}
