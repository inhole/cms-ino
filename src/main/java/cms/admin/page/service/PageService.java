package cms.admin.page.service;

import cms.admin.page.repository.PageRepository;
import cms.admin.page.repository.TemplateRepository;
import cms.entity.Page;
import cms.entity.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PageService {

    private final PageRepository pageRepository;
    private final TemplateRepository templateRepository;

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Page findById(Long id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Page not found"));
    }

    public List<Template> findTemplates() {
        return templateRepository.findAll();
    }

    @Transactional
    public Page save(Page page) {
        if (page.getPageNm() == null || page.getPageNm().isBlank()) {
            throw new IllegalArgumentException("페이지명은 필수입니다.");
        }
        if (page.getUseYn() == null) {
            page.setUseYn(Boolean.TRUE);
        }
        if (page.getTapMenuYn() == null) {
            page.setTapMenuYn(Boolean.FALSE);
        }
        return pageRepository.save(page);
    }

    @Transactional
    public void delete(Long id) {
        pageRepository.deleteById(id);
    }
}
