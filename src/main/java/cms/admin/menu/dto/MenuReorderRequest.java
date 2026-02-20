package cms.admin.menu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuReorderRequest {
    private Long menuId;
    private Long parentId;
    private Integer menuOrd;
}
