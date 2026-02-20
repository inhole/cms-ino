package cms.admin.menu.controller;

import cms.admin.menu.dto.MenuTreeItemDto;
import cms.admin.menu.service.MenuService;
import cms.entity.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class MenuControllerTest {

    @Mock
    private MenuService menuService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MenuController controller = new MenuController(menuService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("메뉴 목록 핸들러는 메뉴 데이터를 모델에 담아 list 뷰를 반환한다")
    void menuListHandler() throws Exception {
        Menu menu = Menu.builder()
                .menuId(1L)
                .menuNm("메인")
                .menuType("LINK")
                .langType("KR")
                .menuOrd(BigDecimal.ONE)
                .upperMenuId("ROOT")
                .openYn(true)
                .build();

        given(menuService.findByLangType(anyString())).willReturn(List.of(menu));
        given(menuService.findTreeByLangType(anyString())).willReturn(List.of(new MenuTreeItemDto(menu)));

        mockMvc.perform(get("/admin/menus").param("lang", "KR"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/menu/list"))
                .andExpect(model().attributeExists("lang"))
                .andExpect(model().attributeExists("menus"))
                .andExpect(model().attributeExists("menuTree"));
    }
}
