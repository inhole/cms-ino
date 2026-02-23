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
        model.addAttribute("pageTitle", "페이지 관리");
        model.addAttribute("pages", pageService.findAll());
        return "admin/page/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "페이지 등록");
        model.addAttribute("page", new Page());
        model.addAttribute("templates", pageService.findTemplates());
        return "admin/page/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Page page, RedirectAttributes redirectAttributes) {
        try {
            pageService.save(page);
            redirectAttributes.addFlashAttribute("message", "페이지가 등록되었습니다.");
            return "redirect:/admin/pages";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/pages/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "페이지 수정");
        model.addAttribute("page", pageService.findById(id));
        model.addAttribute("templates", pageService.findTemplates());
        return "admin/page/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Page page, RedirectAttributes redirectAttributes) {
        page.setPageId(id);
        try {
            pageService.save(page);
            redirectAttributes.addFlashAttribute("message", "페이지가 수정되었습니다.");
            return "redirect:/admin/pages";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/pages/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pageService.delete(id);
        redirectAttributes.addFlashAttribute("message", "페이지가 삭제되었습니다.");
        return "redirect:/admin/pages";
    }
}
