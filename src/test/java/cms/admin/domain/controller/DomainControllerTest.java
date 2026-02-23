package cms.admin.domain.controller;

import cms.admin.domain.service.DomainService;
import cms.entity.Domain;
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
class DomainControllerTest {

    @Mock
    private DomainService domainService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        DomainController controller = new DomainController(domainService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("도메인 목록 화면은 도메인 데이터를 모델에 담아 반환한다")
    void listHandler() throws Exception {
        Domain item = Domain.builder()
                .domainId(1L)
                .domainNm("대표 도메인")
                .domainUrl("https://example.com")
                .build();
        given(domainService.findAll()).willReturn(List.of(item));

        mockMvc.perform(get("/admin/domains"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/domain/list"))
                .andExpect(model().attributeExists("domains"));
    }
}
