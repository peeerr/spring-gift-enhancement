package gift.service;

import gift.domain.Category;
import gift.exception.CategoryNameNotDuplicateException;
import gift.exception.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import gift.dto.request.CategoryRequest;
import gift.dto.response.CategoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(Category::toDto)
                .toList();
    }

    public void addCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryNameNotDuplicateException();
        }

        Category category = new Category(request.getName());

        categoryRepository.save(category);
    }

    public void editCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new CategoryNameNotDuplicateException();
        }

        category.changeName(request.getName());
    }

    public void removeCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
