package cms.admin.menu.service;

import cms.admin.menu.dto.MenuReorderRequest;
import cms.admin.menu.dto.MenuTreeItemDto;
import cms.admin.menu.repository.MenuRepository;
import cms.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private static final String ROOT = "ROOT";
    private final MenuRepository menuRepository;

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public List<Menu> findByLangType(String langType) {
        return menuRepository.findByLangTypeOrderByMenuGrpAscMenuOrdAscMenuIdAsc(defaultLang(langType));
    }

    public List<MenuTreeItemDto> findTreeByLangType(String langType) {
        List<Menu> menus = findByLangType(langType);
        Map<Long, MenuTreeItemDto> nodeMap = new HashMap<>();
        List<MenuTreeItemDto> roots = new ArrayList<>();

        for (Menu menu : menus) {
            nodeMap.put(menu.getMenuId(), new MenuTreeItemDto(menu));
        }

        for (Menu menu : menus) {
            MenuTreeItemDto node = nodeMap.get(menu.getMenuId());
            Long parentId = parseParentId(menu.getUpperMenuId());
            if (parentId == null || !nodeMap.containsKey(parentId)) {
                roots.add(node);
                continue;
            }
            nodeMap.get(parentId).addChild(node);
        }

        Comparator<MenuTreeItemDto> comparator = Comparator
                .comparing((MenuTreeItemDto n) -> n.getMenu().getMenuOrd(), Comparator.nullsLast(BigDecimal::compareTo))
                .thenComparing(n -> n.getMenu().getMenuId());
        sortRecursive(roots, comparator);
        return roots;
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    @Transactional
    public Menu save(Menu menu) {
        menu.setLangType(defaultLang(menu.getLangType()));
        if (menu.getOpenYn() == null) {
            menu.setOpenYn(Boolean.TRUE);
        }
        if (menu.getUpperMenuId() == null || menu.getUpperMenuId().isBlank()) {
            menu.setUpperMenuId(ROOT);
        }
        if (menu.getMenuType() == null || menu.getMenuType().isBlank()) {
            menu.setMenuType("LINK");
        }
        if (menu.getMenuOrd() == null) {
            BigDecimal maxOrd = menuRepository.findMaxOrderByLangTypeAndUpperMenuId(menu.getLangType(), menu.getUpperMenuId());
            menu.setMenuOrd(maxOrd.add(BigDecimal.ONE));
        }

        Menu saved = menuRepository.save(menu);
        if (saved.getMenuGrp() == null) {
            saved.setMenuGrp(resolveMenuGroupByParent(saved.getUpperMenuId(), saved.getMenuId()));
            saved = menuRepository.save(saved);
        }
        return saved;
    }

    @Transactional
    public void delete(Long id) {
        if (menuRepository.existsByUpperMenuId(String.valueOf(id))) {
            throw new IllegalStateException("하위 메뉴가 있어 삭제할 수 없습니다.");
        }
        menuRepository.deleteById(id);
    }

    @Transactional
    public void reorder(String langType, List<MenuReorderRequest> orders) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        String currentLang = defaultLang(langType);

        for (MenuReorderRequest req : orders) {
            if (req.getMenuId() == null || req.getMenuOrd() == null) {
                continue;
            }
            Menu menu = findById(req.getMenuId());
            if (!Objects.equals(menu.getLangType(), currentLang)) {
                continue;
            }
            String upperMenuId = req.getParentId() == null ? ROOT : String.valueOf(req.getParentId());
            menu.setUpperMenuId(upperMenuId);
            menu.setMenuOrd(BigDecimal.valueOf(req.getMenuOrd()));
            menu.setMenuGrp(resolveMenuGroupByParent(upperMenuId, menu.getMenuId()));
            menuRepository.save(menu);
        }
    }

    private String defaultLang(String langType) {
        return (langType == null || langType.isBlank()) ? "KR" : langType;
    }

    private BigDecimal resolveMenuGroupByParent(String upperMenuId, Long selfId) {
        Long parentId = parseParentId(upperMenuId);
        if (parentId == null) {
            return BigDecimal.valueOf(selfId);
        }
        return menuRepository.findById(parentId)
                .map(parent -> parent.getMenuGrp() != null ? parent.getMenuGrp() : BigDecimal.valueOf(parentId))
                .orElse(BigDecimal.valueOf(selfId));
    }

    private Long parseParentId(String upperMenuId) {
        if (upperMenuId == null || upperMenuId.isBlank() || ROOT.equalsIgnoreCase(upperMenuId)) {
            return null;
        }
        try {
            return Long.parseLong(upperMenuId);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private void sortRecursive(List<MenuTreeItemDto> list, Comparator<MenuTreeItemDto> comparator) {
        list.sort(comparator);
        for (MenuTreeItemDto node : list) {
            sortRecursive(node.getChildren(), comparator);
        }
    }
}
