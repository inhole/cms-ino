package cms.admin.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/admin/login")
    public String adminLogin(@RequestParam(required = false) String error,
                             @RequestParam(required = false) String logout,
                             @RequestParam(required = false) String roleError,
                             Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        if (roleError != null) {
            model.addAttribute("roleError", true);
        }
        return "admin/login";
    }
}
