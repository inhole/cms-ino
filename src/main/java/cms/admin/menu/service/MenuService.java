package cms.admin.menu.service;

import cms.admin.menu.repository.MenuRepository;
import cms.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    @Transactional
    public Menu save(Menu menu) {
        if (menu.getLangType() == null || menu.getLangType().isBlank()) {
            menu.setLangType("KR");
        }
        if (menu.getMenuOrd() == null) {
            menu.setMenuOrd(BigDecimal.ONE);
        }
        if (menu.getOpenYn() == null) {
            menu.setOpenYn(Boolean.TRUE);
        }
        if (menu.getUpperMenuId() == null || menu.getUpperMenuId().isBlank()) {
            menu.setUpperMenuId("ROOT");
        }
        return menuRepository.save(menu);
    }

    @Transactional
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}
