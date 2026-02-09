package cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_GROUP_AUTH")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(GroupAuth.GroupAuthId.class)
public class GroupAuth extends BaseEntity {

    @Id
    @Column(name = "GRP_ID")
    private Long grpId;

    @Id
    @Column(name = "DOMAIN_ID")
    private Long domainId;

    @Column(name = "MENU", length = 20)
    private String menu;

    @Column(name = "MAIN", length = 20)
    private String main;

    @Column(name = "BOARD", length = 20)
    private String board;

    @Column(name = "PERSON", length = 20)
    private String person;

    @Column(name = "SITE", length = 20)
    private String site;

    @Column(name = "STAT", length = 20)
    private String stat;

    @Column(name = "GRP_DEPT", length = 20)
    private String grpDept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRP_ID", insertable = false, updatable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMAIN_ID", insertable = false, updatable = false)
    private Domain domain;

    // Composite Key
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class GroupAuthId implements Serializable {
        private Long grpId;
        private Long domainId;
    }
}
