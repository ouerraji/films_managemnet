package ma.sdglr.cinema.Controller;

import ma.sdglr.cinema.Model.Category;
import ma.sdglr.cinema.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // List Categories
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/listcategory";
    }

    // Add Category Form
    @GetMapping("/add")
    public String addCategoryForm() {
        return "categories/addcategory";
    }

    // Save New Category
    @PostMapping
    public String createCategory(Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }

    // Update Category Form
    @GetMapping("/update/{id}")
    public String updateCategoryForm(@PathVariable Byte id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categories/updatecategory";
    }

    // Save Updated Category
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Byte id, Category category) {
        category.setCategoryId(id); // Ensure the ID is set for the update
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }


    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Byte id, RedirectAttributes redirectAttributes) {


        try {
            categoryService.deleteCategoryById(id);
            // Success: Category deleted
            redirectAttributes.addFlashAttribute("message", "Category successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            // Log the exception to debug


            // Error: Category cannot be deleted due to foreign key constraint
            redirectAttributes.addFlashAttribute("message", "Cannot delete this category because it is associated with films.");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        // Redirect to the categories list page
        return "redirect:/categories";
    }




}
