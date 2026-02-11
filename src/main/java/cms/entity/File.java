package cms.entity;

import cms.common.converter.BooleanToYNConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_FILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATA_CODE", length = 45)
    private String dataCode;

    @Column(name = "FILE_CODE", length = 45)
    private String fileCode;

    @Column(name = "FILE_TYPE_ID")
    private Long fileTypeId;

    @Column(name = "FILE_EXTENSIONS", length = 10)
    private String fileExtensions;

    @Column(name = "FILE_ORIGINALNAME", columnDefinition = "LONGTEXT")
    private String fileOriginalname;

    @Column(name = "FILE_NAME", columnDefinition = "LONGTEXT")
    private String fileName;

    @Column(name = "FILE_UUID", length = 200)
    private String fileUuid;

    @Column(name = "FILE_SEQUENCE", length = 6)
    private String fileSequence;

    @Column(name = "FILE_SIZE")
    private Integer fileSize;

    @Column(name = "FILE_SIZE_STR", length = 45)
    private String fileSizeStr;

    @Lob
    @Column(name = "FILE_PATH", columnDefinition = "LONGTEXT")
    private String filePath;

    @Column(name = "FILE_ROOT_PATH")
    private String fileRootPath;

    @Lob
    @Column(name = "FILE_URL", columnDefinition = "LONGTEXT")
    private String fileUrl;

    @Builder.Default
    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "DEL_YN", length = 1)
    private Boolean delYn = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_TYPE_ID", insertable = false, updatable = false)
    private FileType fileType;
}
