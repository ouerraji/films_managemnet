package ma.sdglr.cinema.Controller;

import ma.sdglr.cinema.Model.Film;
import ma.sdglr.cinema.Model.Actor;
import ma.sdglr.cinema.Model.Category;
import ma.sdglr.cinema.Model.Language;
import ma.sdglr.cinema.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final ActorService actorService;
    private final LanguageService languageService;
    private final CategoryService categoryService;

    public FilmController(FilmService filmService,
                          ActorService actorService,
                          LanguageService languageService,
                          CategoryService categoryService) {
        this.filmService = filmService;
        this.actorService = actorService;
        this.languageService = languageService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin")
    public String managefilms(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        return "filmsmanage"; // Ensure this template exists and is configured correctly in your templates folder
    }
    @GetMapping
    public String listFilms(Model model) {
        model.addAttribute("films", filmService.getAllFilms());
        return "listfilms"; // Ensure this template exists and is configured correctly in your templates folder
    }

    @GetMapping("/add")
    public String showAddFilmForm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("allLanguages", languageService.getAllLanguages());
        model.addAttribute("allCategories", categoryService.getAllCategories());
        model.addAttribute("allActors", actorService.getAllActors());
        return "addfilm"; // Ensure this template exists and is configured correctly in your templates folder
    }

    @PostMapping("/add")
    public String addFilm(@ModelAttribute Film film,
                          @RequestParam(value = "actorIds", required = false) Set<Short> actorIds,
                          @RequestParam(value = "categoryIds", required = false) Set<Byte> categoryIds) {

        // Convert actor IDs to Actor objects
        Set<Actor> actors = new HashSet<>();
        if (actorIds != null) {
            for (Short actorId : actorIds) {
                Actor actor = actorService.getActorById(actorId);
                if (actor != null) {
                    actors.add(actor);
                }
            }
        }
        film.setActors(actors);

        // Convert category IDs to Category objects
        Set<Category> categories = new HashSet<>();
        if (categoryIds != null) {
            for (Byte categoryId : categoryIds) {
                Category category = categoryService.getCategoryById(categoryId);
                if (category != null) {
                    categories.add(category);
                }
            }
        }
        film.setFilmCategories(categories);

        // Save the new film
        filmService.addFilm(film);

        // Redirect to the films list after adding a film
        return "redirect:/films/admin";
    }

    @GetMapping("/details/{id}")
    public String filmDetails(@PathVariable("id") Short filmId, Model model) {
        Film film = filmService.getFilmById(filmId);
        if (film == null) {
            // Log the error and redirect to films list
            System.out.println("Film not found with ID: " + filmId);
            return "redirect:/films";
        }
        model.addAttribute("film", film);
        return "filmsdetails";
    }
    @GetMapping("/user/details/{id}")
    public String filmDetailsforuser(@PathVariable("id") Short filmId, Model model) {
        Film film = filmService.getFilmById(filmId);
        if (film == null) {
            // Log the error and redirect to films list
            System.out.println("Film not found with ID: " + filmId);
            return "redirect:/films";
        }
        model.addAttribute("film", film);
        return "filmsdetails2";
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Short id, Model model) {
        System.out.println("Fetching film with ID: " + id);  // Debug log
        Film film = filmService.getFilmById(id);
        if (film == null) {
            System.out.println("Film not found!");  // Debug log
            return "redirect:/films";
        }

        System.out.println("Film found: " + film.getTitle());  // Debug log
        model.addAttribute("film", film);
        model.addAttribute("allActors", actorService.getAllActors());
        model.addAttribute("allLanguages", languageService.getAllLanguages());
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "updatefilm";
    }

    @PostMapping("/update/{id}")
    public String updateFilm(@PathVariable Short id,
                             @ModelAttribute Film film,
                             @RequestParam(value = "actorIds", required = false) Set<Short> actorIds,
                             @RequestParam(value = "categoryIds", required = false) Set<Byte> categoryIds) {
        try {
            // Check if film exists
            Film existingFilm = filmService.getFilmById(id);
            if (existingFilm == null) {
                return "redirect:/films";
            }

            // Set the film ID
            film.setFilmId(id);

            // Validate and set the original language
            if (film.getOriginalLanguage() != null && film.getOriginalLanguage().getLanguageId() == null) {
                film.setOriginalLanguage(null); // or some default value if needed
            }

            // Handle actors
            Set<Actor> actors = new HashSet<>();
            if (actorIds != null) {
                for (Short actorId : actorIds) {
                    Actor actor = actorService.getActorById(actorId);
                    if (actor != null) {
                        actors.add(actor);
                    }
                }
            }
            film.setActors(actors);

            // Handle categories
            Set<Category> categories = new HashSet<>();
            if (categoryIds != null) {
                for (Byte categoryId : categoryIds) {
                    Category category = categoryService.getCategoryById(categoryId);
                    if (category != null) {
                        categories.add(category);
                    }
                }
            }
            film.setFilmCategories(categories);

            // Update the film
            filmService.updateFilm(film);

            return "redirect:/films/admin";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Short id) {
        // Check if the film exists before attempting to delete
        Film film = filmService.getFilmById(id);
        if (film != null) {
            filmService.deleteFilm(id);
        }
        // Redirect to the films list after deletion
        return "redirect:/films/admin";
    }
}
