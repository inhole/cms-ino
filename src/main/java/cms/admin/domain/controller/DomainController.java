package cms.admin.domain.controller;

import cms.admin.domain.service.DomainService;
import cms.entity.Domain;
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
@RequestMapping("/admin/domains")
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "도메인 관리");
        model.addAttribute("domains", domainService.findAll());
        return "admin/domain/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "도메인 등록");
        model.addAttribute("domain", new Domain());
        return "admin/domain/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Domain domain, RedirectAttributes redirectAttributes) {
        try {
            domainService.save(domain);
            redirectAttributes.addFlashAttribute("message", "도메인이 등록되었습니다.");
            return "redirect:/admin/domains";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/domains/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "도메인 수정");
        model.addAttribute("domain", domainService.findById(id));
        return "admin/domain/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Domain domain, RedirectAttributes redirectAttributes) {
        domain.setDomainId(id);
        try {
            domainService.save(domain);
            redirectAttributes.addFlashAttribute("message", "도메인이 수정되었습니다.");
            return "redirect:/admin/domains";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/domains/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        domainService.delete(id);
        redirectAttributes.addFlashAttribute("message", "도메인이 삭제되었습니다.");
        return "redirect:/admin/domains";
    }
}
