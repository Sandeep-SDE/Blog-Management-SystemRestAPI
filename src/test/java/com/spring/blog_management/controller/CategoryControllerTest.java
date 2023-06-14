package com.spring.blog_management.controller;

import com.spring.blog_management.entity.Category;
import com.spring.blog_management.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    private CategoryController categoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryController = new CategoryController(categoryService);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = Arrays.asList(
                new Category("Category 1"),
                new Category("Category 2")
        );

        when(categoryService.getAllCategories()).thenReturn(categories);

        List<Category> result = categoryController.getAllCategories();

        assertEquals(categories.size(), result.size());
        assertEquals(categories.get(0).getCategoryName(), result.get(0).getCategoryName());
        assertEquals(categories.get(1).getCategoryName(), result.get(1).getCategoryName());

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCategoryById() {
        Category category = new Category("Category 1");

        when(categoryService.getCategoryById(1)).thenReturn(category);

        Category result = categoryController.getCategoryById(1);

        assertEquals(category.getCategoryName(), result.getCategoryName());

        verify(categoryService, times(1)).getCategoryById(1);
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category("Category 1");
        category.setId(1);
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryController.createCategory(category);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("/api/categories/1", responseEntity.getHeaders().getLocation().getPath());
        assertEquals(category.getCategoryName(), responseEntity.getBody().getCategoryName());

        verify(categoryService, times(1)).createCategory(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category("Category 1");

        when(categoryService.getCategoryById(anyInt())).thenReturn(category);
        when(categoryService.updateCategory(any(Category.class))).thenReturn(category);

        Category updatedCategory = new Category("Category 1");

        Category result = categoryController.updateCategory(1, updatedCategory);

        assertEquals(updatedCategory.getCategoryName(), result.getCategoryName());

        verify(categoryService, times(1)).updateCategory(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(1);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(categoryService, times(1)).deleteCategory(1);
    }
}
