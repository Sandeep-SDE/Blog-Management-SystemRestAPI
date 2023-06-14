package com.spring.blog_management.service;

import com.spring.blog_management.dao.CategoryRepository;
import com.spring.blog_management.entity.Category;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_ShouldReturnAllCategories() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1"));
        categories.add(new Category(2, "Category 2"));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(categories, result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_WithExistingId_ShouldReturnCategory() {

        int categoryId = 1;
        Category category = new Category(categoryId, "Category 1");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(categoryId);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void getCategoryById_WithNonExistingId_ShouldThrowNoSuchElementException() {

        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> categoryService.getCategoryById(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void createCategory_ShouldReturnCreatedCategory() {

        Category category = new Category(1, "Category 1");
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory() {

        Category category = new Category(1, "Category 1");
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.updateCategory(category);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void deleteCategory_ShouldInvokeDeleteById() {
        int categoryId = 1;

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}
