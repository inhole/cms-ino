package cms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_MEMBER_GRP_REL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(AdminGrpRel.AdminGrpRelId.class)
public class AdminGrpRel {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Id
    @Column(name = "GRP_ID")
    private Long grpId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRP_ID", insertable = false, updatable = false)
    private Group group;

    // Composite Key
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class AdminGrpRelId implements Serializable {
        private Long userId;
        private Long grpId;
    }
}
