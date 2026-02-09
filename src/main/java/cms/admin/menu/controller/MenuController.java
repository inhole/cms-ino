package cms.admin.menu.controller;

import cms.admin.menu.service.MenuService;
import cms.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "Menu Management");
        model.addAttribute("menus", menuService.findAll());
        return "admin/menu/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Create Menu");
        model.addAttribute("menu", new Menu());
        return "admin/menu/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Menu menu) {
        menuService.save(menu);
        return "redirect:/admin/menus";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Menu");
        model.addAttribute("menu", menuService.findById(id));
        return "admin/menu/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Menu menu) {
        menu.setMenuId(id);
        menuService.save(menu);
        return "redirect:/admin/menus";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/admin/menus";
    }
}
