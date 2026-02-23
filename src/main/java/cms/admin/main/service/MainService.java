package cms.admin.main.service;

import cms.admin.board.repository.BoardRepository;
import cms.admin.main.repository.BannerRepository;
import cms.admin.main.repository.MainDomainRepository;
import cms.admin.main.repository.MainRepository;
import cms.entity.Banner;
import cms.entity.Board;
import cms.entity.Domain;
import cms.entity.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final MainRepository mainRepository;
    private final MainDomainRepository domainRepository;
    private final BannerRepository bannerRepository;
    private final BoardRepository boardRepository;

    public List<Main> findAll() {
        return mainRepository.findAll();
    }

    public Main findById(Long id) {
        return mainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Main not found"));
    }

    public List<Domain> findDomains() {
        return domainRepository.findAll();
    }

    public List<Banner> findBanners() {
        return bannerRepository.findAll();
    }

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    @Transactional
    public Main save(Main main) {
        if (main.getDomainId() == null) {
            throw new IllegalArgumentException("도메인은 필수입니다.");
        }
        return mainRepository.save(main);
    }

    @Transactional
    public void delete(Long id) {
        mainRepository.deleteById(id);
    }
}
