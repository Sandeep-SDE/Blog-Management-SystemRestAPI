package com.spring.blog_management.controller;

import com.spring.blog_management.entity.Category;
import com.spring.blog_management.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
       // categoryController = new CategoryController(categoryService);
    }

    // here the test cases for the controller methods

    /**
     *      step 1: Create data for mocking
     *
     *      step2 : Mock the service method to return the category
     *
     *      step3 : Call the controller method
     *
     *      step4 : Verify the results by using asserts...
     */

    @Test
    public void testGetAllCategories(){
        List<Category> categories = Arrays.asList(
                new Category(1,"firs category"),
                new Category(2, "second category")
        );

        when(categoryService.getAllCategories()).thenReturn(categories);

        List<Category> returnedCategories = categoryController.getAllCategories();

        assertNotNull(returnedCategories);
        assertEquals(categories.size(), returnedCategories.size());
    }

    @Test
    public void testGetCategoryById(){

        Category category = new Category(1, "category1");

        when(categoryService.getCategoryById(1)).thenReturn(category);

        Category returnedCategory = categoryController.getCategoryById(1);

        assertNotNull(returnedCategory);
        assertEquals(category.getId(), returnedCategory.getId());
        assertEquals(category.getCategoryName(), returnedCategory.getCategoryName());
    }

    @Test
    public void testCreateCategory(){

        Category category = new Category(1, "category1");

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        ResponseEntity<Category> responseEntity = categoryController.createCategory(category);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(category.getId(), responseEntity.getBody().getId());
        assertEquals(category.getCategoryName(), responseEntity.getBody().getCategoryName());

    }

    @Test
    public void testUpdateCategory() {

        Category category = new Category(1, "Updated Category");

        when(categoryService.updateCategory(any(Category.class))).thenReturn(category);

        Category returnedCategory = categoryController.updateCategory(1, category);

        assertNotNull(returnedCategory);
        assertEquals(category.getId(), returnedCategory.getId());
        assertEquals(category.getCategoryName(), returnedCategory.getCategoryName());
    }

    @Test
    public void testDeleteCategory() {

        ResponseEntity<Void> responseEntity = categoryController.deleteCategory(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(categoryService, times(1)).deleteCategory(1);
    }

}