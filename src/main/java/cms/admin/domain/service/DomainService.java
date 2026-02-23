package cms.admin.domain.service;

import cms.admin.domain.repository.DomainRepository;
import cms.entity.Domain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DomainService {

    private final DomainRepository domainRepository;

    public List<Domain> findAll() {
        return domainRepository.findAll();
    }

    public Domain findById(Long id) {
        return domainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Domain not found"));
    }

    @Transactional
    public Domain save(Domain domain) {
        if (domain.getDomainNm() == null || domain.getDomainNm().isBlank()) {
            throw new IllegalArgumentException("도메인명은 필수입니다.");
        }
        if (domain.getUseYn() == null) {
            domain.setUseYn(Boolean.TRUE);
        }
        return domainRepository.save(domain);
    }

    @Transactional
    public void delete(Long id) {
        domainRepository.deleteById(id);
    }
}
