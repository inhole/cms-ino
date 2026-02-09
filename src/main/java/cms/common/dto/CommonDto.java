package cms.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommonDto {
    private LocalDateTime regDt;
    private Long regUsrId;
    private LocalDateTime modDt;
    private Long modUsrId;
}
