package cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_GROUP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GRP_ID")
    private Long grpId;

    @Column(name = "GRP_NM", length = 100)
    private String grpNm;

    @Column(name = "GRP_DESC", length = 200)
    private String grpDesc;
}
