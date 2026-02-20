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
    private static final int MAX_DEPTH = 3;
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

        validateParentRule(menu);
        validateDepthRule(menu.getMenuId(), menu.getUpperMenuId());

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
        List<Menu> langMenus = menuRepository.findByLangTypeOrderByMenuGrpAscMenuOrdAscMenuIdAsc(currentLang);
        Map<Long, Menu> menuMap = new HashMap<>();
        for (Menu m : langMenus) {
            menuMap.put(m.getMenuId(), m);
        }

        Map<Long, Long> nextParentMap = new HashMap<>();
        Map<Long, Integer> nextOrderMap = new HashMap<>();

        for (MenuReorderRequest req : orders) {
            if (req.getMenuId() == null || req.getMenuOrd() == null) {
                continue;
            }
            Menu menu = menuMap.get(req.getMenuId());
            if (menu == null) {
                throw new IllegalArgumentException("유효하지 않은 메뉴 ID가 포함되어 있습니다.");
            }
            Long parentId = req.getParentId();
            if (parentId != null) {
                Menu parent = menuMap.get(parentId);
                if (parent == null) {
                    throw new IllegalArgumentException("부모 메뉴가 존재하지 않습니다.");
                }
            }
            nextParentMap.put(req.getMenuId(), parentId);
            nextOrderMap.put(req.getMenuId(), req.getMenuOrd());
        }

        for (Long menuId : nextParentMap.keySet()) {
            validateNoCycle(menuId, nextParentMap);
            int depth = calculateDepth(menuId, nextParentMap);
            if (depth > MAX_DEPTH) {
                throw new IllegalStateException("메뉴 깊이는 최대 " + MAX_DEPTH + "단계까지 허용됩니다.");
            }
        }

        for (Map.Entry<Long, Long> e : nextParentMap.entrySet()) {
            Menu menu = menuMap.get(e.getKey());
            String upperMenuId = e.getValue() == null ? ROOT : String.valueOf(e.getValue());
            Integer menuOrd = nextOrderMap.get(e.getKey());
            menu.setUpperMenuId(upperMenuId);
            menu.setMenuOrd(BigDecimal.valueOf(menuOrd));
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

    private void validateParentRule(Menu menu) {
        String upperMenuId = menu.getUpperMenuId();
        if (upperMenuId == null || upperMenuId.isBlank() || ROOT.equalsIgnoreCase(upperMenuId)) {
            return;
        }
        Long parentId = parseParentId(upperMenuId);
        if (parentId == null) {
            throw new IllegalArgumentException("상위 메뉴 값이 올바르지 않습니다.");
        }
        if (menu.getMenuId() != null && Objects.equals(menu.getMenuId(), parentId)) {
            throw new IllegalArgumentException("자기 자신을 상위 메뉴로 지정할 수 없습니다.");
        }
        Menu parent = menuRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("상위 메뉴가 존재하지 않습니다."));
    }

    private void validateDepthRule(Long menuId, String upperMenuId) {
        int parentDepth = 0;
        String cursor = upperMenuId;
        int guard = 0;
        while (cursor != null && !cursor.isBlank() && !ROOT.equalsIgnoreCase(cursor)) {
            Long parentId = parseParentId(cursor);
            if (parentId == null) {
                throw new IllegalArgumentException("상위 메뉴 경로가 올바르지 않습니다.");
            }
            if (menuId != null && Objects.equals(menuId, parentId)) {
                throw new IllegalArgumentException("메뉴 계층이 순환될 수 없습니다.");
            }
            Menu parent = menuRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("상위 메뉴가 존재하지 않습니다."));
            parentDepth++;
            if (parentDepth + 1 > MAX_DEPTH) {
                throw new IllegalStateException("메뉴 깊이는 최대 " + MAX_DEPTH + "단계까지 허용됩니다.");
            }
            cursor = parent.getUpperMenuId();
            guard++;
            if (guard > 1000) {
                throw new IllegalStateException("메뉴 계층 검증 중 순환이 감지되었습니다.");
            }
        }
    }

    private void validateNoCycle(Long menuId, Map<Long, Long> parentMap) {
        Long current = menuId;
        int guard = 0;
        while (current != null) {
            Long parent = parentMap.get(current);
            if (parent == null) {
                return;
            }
            if (Objects.equals(parent, menuId)) {
                throw new IllegalStateException("메뉴 계층이 순환될 수 없습니다.");
            }
            current = parent;
            guard++;
            if (guard > 1000) {
                throw new IllegalStateException("메뉴 계층 검증 중 순환이 감지되었습니다.");
            }
        }
    }

    private int calculateDepth(Long menuId, Map<Long, Long> parentMap) {
        int depth = 1;
        Long current = parentMap.get(menuId);
        int guard = 0;
        while (current != null) {
            depth++;
            current = parentMap.get(current);
            guard++;
            if (guard > 1000) {
                throw new IllegalStateException("메뉴 깊이 계산 중 순환이 감지되었습니다.");
            }
        }
        return depth;
    }

}
