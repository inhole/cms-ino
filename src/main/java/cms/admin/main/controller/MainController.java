package cms.admin.main.controller;

import cms.admin.main.service.MainService;
import cms.entity.Main;
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
@RequestMapping("/admin/mains")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "메인 관리");
        model.addAttribute("mains", mainService.findAll());
        return "admin/main/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "메인 등록");
        model.addAttribute("main", new Main());
        addFormOptions(model);
        return "admin/main/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Main main, RedirectAttributes redirectAttributes) {
        try {
            mainService.save(main);
            redirectAttributes.addFlashAttribute("message", "메인 구성이 등록되었습니다.");
            return "redirect:/admin/mains";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/mains/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "메인 수정");
        model.addAttribute("main", mainService.findById(id));
        addFormOptions(model);
        return "admin/main/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute Main main, RedirectAttributes redirectAttributes) {
        main.setMainId(id);
        try {
            mainService.save(main);
            redirectAttributes.addFlashAttribute("message", "메인 구성이 수정되었습니다.");
            return "redirect:/admin/mains";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/mains/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        mainService.delete(id);
        redirectAttributes.addFlashAttribute("message", "메인 구성이 삭제되었습니다.");
        return "redirect:/admin/mains";
    }

    private void addFormOptions(Model model) {
        model.addAttribute("domains", mainService.findDomains());
        model.addAttribute("banners", mainService.findBanners());
        model.addAttribute("boards", mainService.findBoards());
    }
}
