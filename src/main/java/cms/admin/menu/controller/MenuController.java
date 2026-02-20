package cms.admin.menu.controller;

import cms.admin.menu.dto.MenuReorderRequest;
import cms.admin.menu.service.MenuService;
import cms.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "KR") String lang,
                       @RequestParam(required = false) Long editId,
                       @RequestParam(required = false, defaultValue = "create") String mode,
                       Model model) {
        List<Menu> menus = menuService.findByLangType(lang);
        Menu formMenu = new Menu();
        formMenu.setLangType(lang);
        formMenu.setUpperMenuId("ROOT");
        formMenu.setOpenYn(true);
        formMenu.setMenuType("LINK");

        boolean editMode = false;
        if (editId != null) {
            try {
                Menu selected = menuService.findById(editId);
                if (lang.equalsIgnoreCase(selected.getLangType())) {
                    formMenu = selected;
                    editMode = true;
                }
            } catch (RuntimeException ignored) {
            }
        } else if ("edit".equalsIgnoreCase(mode) && !menus.isEmpty()) {
            formMenu = menus.get(0);
            editMode = true;
        }

        model.addAttribute("lang", lang);
        model.addAttribute("formMenu", formMenu);
        model.addAttribute("editMode", editMode);
        model.addAttribute("parentMenus", menus);
        model.addAttribute("menus", menus);
        model.addAttribute("menuItems", menus.stream()
                .map(menu -> Map.of(
                        "menuId", menu.getMenuId(),
                        "menuNm", menu.getMenuNm() == null ? "" : menu.getMenuNm(),
                        "menuType", menu.getMenuType() == null ? "LINK" : menu.getMenuType(),
                        "menuTitle", menu.getMenuTitle() == null ? "" : menu.getMenuTitle(),
                        "menuDesc", menu.getMenuDesc() == null ? "" : menu.getMenuDesc(),
                        "url", menu.getUrl() == null ? "" : menu.getUrl(),
                        "upperMenuId", menu.getUpperMenuId() == null ? "ROOT" : menu.getUpperMenuId(),
                        "menuOrd", menu.getMenuOrd() == null ? 0 : menu.getMenuOrd(),
                        "openYn", Boolean.TRUE.equals(menu.getOpenYn())
                ))
                .collect(Collectors.toList()));
        model.addAttribute("menuTree", menuService.findTreeByLangType(lang));
        return "admin/menu/list";
    }

    @GetMapping("/create")
    public String createForm(@RequestParam(defaultValue = "KR") String lang, Model model) {
        return "redirect:/admin/menus?lang=" + lang + "&mode=create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Menu menu, RedirectAttributes redirectAttributes) {
        try {
            Menu saved = menuService.save(menu);
            redirectAttributes.addFlashAttribute("message", "메뉴가 등록되었습니다.");
            return "redirect:/admin/menus?lang=" + saved.getLangType() + "&editId=" + saved.getMenuId();
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/menus?lang=" + (menu.getLangType() == null ? "KR" : menu.getLangType()) + "&mode=create";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.findById(id);
        return "redirect:/admin/menus?lang=" + menu.getLangType() + "&editId=" + id;
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Menu menu, RedirectAttributes redirectAttributes) {
        menu.setMenuId(id);
        try {
            Menu saved = menuService.save(menu);
            redirectAttributes.addFlashAttribute("message", "메뉴가 수정되었습니다.");
            return "redirect:/admin/menus?lang=" + saved.getLangType() + "&editId=" + saved.getMenuId();
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/menus?lang=" + (menu.getLangType() == null ? "KR" : menu.getLangType()) + "&editId=" + id;
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam(defaultValue = "KR") String lang,
                         RedirectAttributes redirectAttributes) {
        try {
            menuService.delete(id);
            redirectAttributes.addFlashAttribute("message", "메뉴가 삭제되었습니다.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/menus?lang=" + lang;
    }

    @PostMapping("/reorder")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> reorder(@RequestParam(defaultValue = "KR") String lang,
                                                       @RequestBody List<MenuReorderRequest> orders) {
        try {
            menuService.reorder(lang, orders);
            return ResponseEntity.ok(Map.of("success", true, "message", "정렬이 저장되었습니다."));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
