package cms.admin.admin.controller;

import cms.admin.admin.dto.AdminDto;
import cms.admin.admin.service.AdminService;
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
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "User Management");
        model.addAttribute("users", adminService.findAll());
        return "admin/user/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Create User");
        model.addAttribute("user", new AdminDto());
        model.addAttribute("isNew", true);
        return "admin/user/form";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit User");
        model.addAttribute("user", adminService.findById(id));
        model.addAttribute("isNew", false);
        return "admin/user/form";
    }

    @PostMapping
    public String save(@ModelAttribute AdminDto dto, RedirectAttributes redirectAttributes) {
        try {
            adminService.save(dto);
            redirectAttributes.addFlashAttribute("message", "User saved successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        adminService.delete(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully.");
        return "redirect:/admin/users";
    }
}
