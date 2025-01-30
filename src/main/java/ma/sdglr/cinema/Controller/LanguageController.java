package ma.sdglr.cinema.Controller;

import ma.sdglr.cinema.Model.Language;
import ma.sdglr.cinema.Service.LanguageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public String listLanguages(Model model) {
        model.addAttribute("languages", languageService.getAllLanguages());
        return "languages/listlanguage";  // Refers to list.html in the templates folder
    }

    @GetMapping("/add")
    public String showAddLanguageForm(Model model) {
        model.addAttribute("language", new Language());
        return "languages/addlanguage";  // Refers to add.html in the templates folder
    }

    @PostMapping
    public String addLanguage(@ModelAttribute Language language) {
        languageService.addLanguage(language);
        return "redirect:/languages";
    }

    @GetMapping("/update/{id}")
    public String showUpdateLanguageForm(@PathVariable Byte id, Model model) {
        model.addAttribute("language", languageService.getLanguageById(Short.valueOf(id)));
        return "languages/updatelanguage";  // Refers to update.html in the templates folder
    }

    @PostMapping("/update/{id}")
    public String updateLanguage(@PathVariable Byte id, @ModelAttribute Language language) {
        language.setLanguageId(id);
        languageService.updateLanguage(language);
        return "redirect:/languages";
    }

    @GetMapping("/delete/{id}")
    public String deleteLanguage(@PathVariable Byte id, RedirectAttributes redirectAttributes) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            languageService.deleteLanguage(Short.valueOf(id));
            // Success: Language deleted
            redirectAttributes.addFlashAttribute("message", "Language successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (RuntimeException e) {
            // Log the exception message to verify if it's caught
            logger.error("Error deleting language with ID: {}", id, e);

            // Error: Language cannot be deleted due to foreign key constraint
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        // Redirect to the languages list page
        return "redirect:/languages";
    }

}
