package ma.sdglr.cinema.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @GetMapping("/logout")
    public String showLogoutPage() {
        return "redirect:/films";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if ("oussama".equals(username) && "oussama123".equals(password)) {
            return "redirect:/films/admin";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}