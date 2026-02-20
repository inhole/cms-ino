package cms.admin.page.controller;

import cms.admin.page.service.PageService;
import cms.entity.Page;
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
class PageControllerTest {

    @Mock
    private PageService pageService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        PageController controller = new PageController(pageService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("페이지 목록 화면은 페이지 데이터를 모델에 담아 반환한다")
    void listHandler() throws Exception {
        Page item = Page.builder()
                .pageId(1L)
                .pageNm("about")
                .pageViewNm("회사소개")
                .build();
        given(pageService.findAll()).willReturn(List.of(item));

        mockMvc.perform(get("/admin/pages"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/page/list"))
                .andExpect(model().attributeExists("pages"));
    }
}
