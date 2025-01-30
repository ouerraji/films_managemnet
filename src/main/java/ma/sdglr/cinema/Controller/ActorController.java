package ma.sdglr.cinema.Controller;

import ma.sdglr.cinema.Model.Actor;
import ma.sdglr.cinema.Service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    // Show all actors in the listactors.html
    @GetMapping
    public String getAllActors(Model model) {
        List<Actor> actors = actorService.getAllActors();
        model.addAttribute("actors", actors);
        return "actors/listactors";  // This should match the name of the Thymeleaf template
    }

    // Add a new actor (this will redirect to addactor.html for the form)
    @GetMapping("/add")
    public String showAddActorForm() {
        return "actors/addactor";  // This should match the name of the Thymeleaf template
    }

    // Add a new actor via POST
    @PostMapping
    public String addActor(Actor actor) {
        actorService.addActor(actor);
        return "redirect:/actors";  // After adding, redirect to the actors list
    }

    // Update an actor (add appropriate mapping for the form and functionality)
    @GetMapping("/update/{id}")
    public String showUpdateActorForm(@PathVariable Short id, Model model) {
        Actor actor = actorService.getActorById(id);
        model.addAttribute("actor", actor);
        return "actors/updateactor";  // This would be a new page you create for updating an actor
    }
    // Handle the POST request for updating an actor
    @PostMapping("/update/{id}")
    public String updateActor(@PathVariable Short id, Actor actor) {
        actor.setActorId(id);  // Ensure the actor's ID is set before updating
        actorService.updateActor(actor);
        return "redirect:/actors";  // After updating, redirect to the actors list
    }

    // Delete an actor
    @GetMapping("/delete/{id}")
    public String deleteActor(@PathVariable Short id, RedirectAttributes redirectAttributes) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            actorService.deleteActor(id);
            // Success: Actor deleted
            redirectAttributes.addFlashAttribute("message", "Actor successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (RuntimeException e) {
            // Log the exception message to verify if it's caught
            logger.error("Error deleting actor with ID: {}", id, e);

            // Error: Actor cannot be deleted due to foreign key constraint
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        // Redirect to the actors list page
        return "redirect:/actors";
    }

}
