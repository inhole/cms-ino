package cms.admin.menu.dto;

import cms.entity.Menu;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuTreeItemDto {

    private final Menu menu;
    private final List<MenuTreeItemDto> children = new ArrayList<>();

    public MenuTreeItemDto(Menu menu) {
        this.menu = menu;
    }

    public void addChild(MenuTreeItemDto child) {
        this.children.add(child);
    }
}
