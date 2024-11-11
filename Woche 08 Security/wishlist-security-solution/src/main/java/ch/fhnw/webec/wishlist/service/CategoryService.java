package ch.fhnw.webec.wishlist.service;

import ch.fhnw.webec.wishlist.db.CategoryRepository;
import ch.fhnw.webec.wishlist.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
