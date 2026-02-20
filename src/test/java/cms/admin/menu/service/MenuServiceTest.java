package cms.admin.menu.service;

import cms.admin.menu.repository.MenuRepository;
import cms.entity.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("메뉴 타입과 무관하게 상위 메뉴 지정 가능")
    void saveShouldSucceedWhenParentIsNotGroup() {
        Menu parent = Menu.builder()
                .menuId(10L)
                .menuType("LINK")
                .upperMenuId("ROOT")
                .menuGrp(BigDecimal.TEN)
                .build();
        given(menuRepository.findById(10L)).willReturn(Optional.of(parent));
        given(menuRepository.save(any(Menu.class))).willAnswer(invocation -> invocation.getArgument(0));

        Menu child = Menu.builder()
                .menuId(20L)
                .menuNm("하위")
                .langType("KR")
                .upperMenuId("10")
                .menuType("LINK")
                .menuGrp(BigDecimal.valueOf(20))
                .build();

        assertDoesNotThrow(() -> menuService.save(child));
    }

    @Test
    @DisplayName("메뉴 깊이 3단계 초과 시 저장 실패")
    void saveShouldFailWhenDepthOverMax() {
        Menu lv1 = Menu.builder().menuId(1L).menuType("GROUP").upperMenuId("ROOT").build();
        Menu lv2 = Menu.builder().menuId(2L).menuType("GROUP").upperMenuId("1").build();
        Menu lv3 = Menu.builder().menuId(3L).menuType("GROUP").upperMenuId("2").build();

        given(menuRepository.findById(3L)).willReturn(Optional.of(lv3));
        given(menuRepository.findById(2L)).willReturn(Optional.of(lv2));
        given(menuRepository.findById(1L)).willReturn(Optional.of(lv1));

        Menu child = Menu.builder()
                .menuId(100L)
                .menuNm("depth4")
                .langType("KR")
                .upperMenuId("3")
                .menuType("LINK")
                .menuGrp(BigDecimal.valueOf(100))
                .build();

        assertThrows(IllegalStateException.class, () -> menuService.save(child));
    }

    @Test
    @DisplayName("GROUP 하위, 허용 깊이에서는 저장 성공")
    void saveShouldSucceedWhenParentGroupAndDepthValid() {
        Menu lv1 = Menu.builder().menuId(1L).menuType("GROUP").upperMenuId("ROOT").menuGrp(BigDecimal.ONE).build();
        Menu lv2 = Menu.builder().menuId(2L).menuType("GROUP").upperMenuId("1").menuGrp(BigDecimal.ONE).build();
        given(menuRepository.findById(2L)).willReturn(Optional.of(lv2));
        given(menuRepository.findById(1L)).willReturn(Optional.of(lv1));
        given(menuRepository.save(any(Menu.class))).willAnswer(invocation -> invocation.getArgument(0));

        Menu child = Menu.builder()
                .menuId(200L)
                .menuNm("depth3")
                .langType("KR")
                .upperMenuId("2")
                .menuType("LINK")
                .menuGrp(BigDecimal.valueOf(200))
                .build();

        assertDoesNotThrow(() -> menuService.save(child));
    }
}
