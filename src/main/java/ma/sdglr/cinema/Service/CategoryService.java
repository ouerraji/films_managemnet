package ma.sdglr.cinema.Service;

import ma.sdglr.cinema.Model.Category;
import ma.sdglr.cinema.Repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Byte id) {
        return categoryRepository.findById(id);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.update(category);
    }

    public void deleteCategoryById(Byte id) {
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Log and throw a specific exception if foreign key constraint is violated
            throw new RuntimeException("Category is associated with films, cannot delete.");
        }
    }
}
