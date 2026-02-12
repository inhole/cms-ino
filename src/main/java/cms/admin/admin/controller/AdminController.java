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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String list() {
        return "redirect:/admin/users/admin";
    }

    @GetMapping("/admin")
    public String adminList(Model model) {
        model.addAttribute("pageTitle", "Admin User Management");
        model.addAttribute("users", adminService.findAdmins());
        model.addAttribute("userType", "admin");
        model.addAttribute("userTypeLabel", "Admin");
        model.addAttribute("listPath", "/admin/users/admin");
        return "admin/user/list";
    }

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("pageTitle", "User Management");
        model.addAttribute("users", adminService.findUsers());
        model.addAttribute("userType", "user");
        model.addAttribute("userTypeLabel", "User");
        model.addAttribute("listPath", "/admin/users/user");
        return "admin/user/list";
    }

    @GetMapping("/new")
    public String createForm(@RequestParam(defaultValue = "admin") String type, Model model) {
        String normalizedType = normalizeType(type);
        AdminDto user = new AdminDto();
        user.setUserLevel("admin".equals(normalizedType) ? "ADMIN" : "USER");

        model.addAttribute("pageTitle", "Create User");
        model.addAttribute("user", user);
        model.addAttribute("isNew", true);
        model.addAttribute("userType", normalizedType);
        model.addAttribute("listPath", toListPath(normalizedType));
        return "admin/user/form";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable Long id,
                           @RequestParam(defaultValue = "admin") String type,
                           Model model) {
        String normalizedType = normalizeType(type);

        model.addAttribute("pageTitle", "Edit User");
        model.addAttribute("user", adminService.findById(id));
        model.addAttribute("isNew", false);
        model.addAttribute("userType", normalizedType);
        model.addAttribute("listPath", toListPath(normalizedType));
        return "admin/user/form";
    }

    @PostMapping
    public String save(@ModelAttribute AdminDto dto,
                       @RequestParam(defaultValue = "admin") String userType,
                       RedirectAttributes redirectAttributes) {
        String normalizedType = normalizeType(userType);

        try {
            adminService.save(dto);
            redirectAttributes.addFlashAttribute("message", "User saved successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:" + toListPath(normalizedType);
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam(defaultValue = "admin") String userType,
                         RedirectAttributes redirectAttributes) {
        String normalizedType = normalizeType(userType);

        adminService.delete(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully.");
        return "redirect:" + toListPath(normalizedType);
    }

    private String normalizeType(String type) {
        return "user".equalsIgnoreCase(type) ? "user" : "admin";
    }

    private String toListPath(String type) {
        return "user".equals(type) ? "/admin/users/user" : "/admin/users/admin";
    }
}
