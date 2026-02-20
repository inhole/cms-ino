package cms.admin.main.controller;

import cms.admin.main.service.MainService;
import cms.entity.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    private MainService mainService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MainController controller = new MainController(mainService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("메인 목록 페이지는 메인 데이터를 모델에 담아 반환한다")
    void listHandler() throws Exception {
        Main item = Main.builder()
                .mainId(1L)
                .domainId(10L)
                .keyword("테스트")
                .build();
        given(mainService.findAll()).willReturn(List.of(item));

        mockMvc.perform(get("/admin/mains"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/main/list"))
                .andExpect(model().attributeExists("mains"));
    }
}
