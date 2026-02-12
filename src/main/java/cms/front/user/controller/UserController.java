package cms.front.user.controller;

import cms.front.user.dto.UserDto;
import cms.front.user.service.FrontUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final FrontUserService userService;

    @GetMapping("/login")
    public String userLogin(@RequestParam(required = false) String error,
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
        return "front/login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/user/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            UserDto user = userService.findByUserId(userDetails.getUsername());
            model.addAttribute("user", user);
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("pageTitle", "사용자 대시보드");
        }
        return "front/dashboard";
    }

    @GetMapping("/user/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            UserDto user = userService.findByUserId(userDetails.getUsername());
            model.addAttribute("user", user);
        }
        return "front/profile";
    }

    @PostMapping("/user/profile/update")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @ModelAttribute UserDto userDto) {
        UserDto currentUser = userService.findByUserId(userDetails.getUsername());
        userDto.setId(currentUser.getId());
        userDto.setUserId(currentUser.getUserId());
        userDto.setUserLevel("USER");
        userService.save(userDto);
        return "redirect:/front/profile?success";
    }
}
