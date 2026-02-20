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

@Controller
@RequestMapping("/admin/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "KR") String lang, Model model) {
        model.addAttribute("lang", lang);
        model.addAttribute("menus", menuService.findByLangType(lang));
        model.addAttribute("menuTree", menuService.findTreeByLangType(lang));
        return "admin/menu/list";
    }

    @GetMapping("/create")
    public String createForm(@RequestParam(defaultValue = "KR") String lang, Model model) {
        Menu menu = new Menu();
        menu.setLangType(lang);
        model.addAttribute("menu", menu);
        model.addAttribute("lang", lang);
        model.addAttribute("parentMenus", menuService.findByLangType(lang));
        return "admin/menu/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Menu menu, RedirectAttributes redirectAttributes) {
        Menu saved = menuService.save(menu);
        redirectAttributes.addFlashAttribute("message", "메뉴가 등록되었습니다.");
        return "redirect:/admin/menus?lang=" + saved.getLangType();
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Menu menu = menuService.findById(id);
        model.addAttribute("menu", menu);
        model.addAttribute("lang", menu.getLangType());
        model.addAttribute("parentMenus", menuService.findByLangType(menu.getLangType()));
        return "admin/menu/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Menu menu, RedirectAttributes redirectAttributes) {
        menu.setMenuId(id);
        Menu saved = menuService.save(menu);
        redirectAttributes.addFlashAttribute("message", "메뉴가 수정되었습니다.");
        return "redirect:/admin/menus?lang=" + saved.getLangType();
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
        menuService.reorder(lang, orders);
        return ResponseEntity.ok(Map.of("success", true, "message", "정렬이 저장되었습니다."));
    }
}
