package cms.admin.page.controller;

import cms.admin.page.service.PageService;
import cms.entity.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/pages")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "Page Management");
        model.addAttribute("pages", pageService.findAll());
        return "admin/page/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Create Page");
        model.addAttribute("page", new Page());
        model.addAttribute("templates", pageService.findTemplates());
        return "admin/page/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Page page, RedirectAttributes redirectAttributes) {
        try {
            pageService.save(page);
            redirectAttributes.addFlashAttribute("message", "Page has been created.");
            return "redirect:/admin/pages";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/pages/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Page");
        model.addAttribute("page", pageService.findById(id));
        model.addAttribute("templates", pageService.findTemplates());
        return "admin/page/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Page page, RedirectAttributes redirectAttributes) {
        page.setPageId(id);
        try {
            pageService.save(page);
            redirectAttributes.addFlashAttribute("message", "Page has been updated.");
            return "redirect:/admin/pages";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/pages/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pageService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Page has been deleted.");
        return "redirect:/admin/pages";
    }
}
