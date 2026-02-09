package cms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_FILE_TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_TYPE_ID")
    private Long fileTypeId;

    @Column(name = "FILE_TYPE_NAME", length = 100)
    private String fileTypeName;

    @Column(name = "FILE_CATEGORY_TYPE", length = 45)
    private String fileCategoryType;

    @Column(name = "FILE_DEVICE_TYPE", length = 45)
    private String fileDeviceType;

    @Column(name = "FILE_LANG_TYPE", length = 45)
    private String fileLangType;
}
