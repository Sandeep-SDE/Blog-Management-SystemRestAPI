package com.spring.blog_management.service;
import com.spring.blog_management.dao.PostRepository;
import com.spring.blog_management.entity.Category;
import com.spring.blog_management.entity.Post;
import com.spring.blog_management.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPosts_ReturnsListOfPosts() {

        List<Post> posts = new ArrayList<>();
        Category category = new Category( "Category1");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Tag1"));
        tags.add(new Tag("Tag2"));
        posts.add(new Post("Post1", "Content1", tags, category));
        posts.add(new Post("Post2", "Content2", tags, category));
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertEquals(2, result.size());
        assertEquals("Post1", result.get(0).getPostTitle());
        assertEquals("Post2", result.get(1).getPostTitle());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void getPostById_WithExistingId_ReturnsPost() {

        int id = 1;
        Category category = new Category("Category1");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Tag1"));
        tags.add(new Tag("Tag2"));
        Post post = new Post("Post1", "Content1", tags, category);
        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(id);

        assertEquals("Post1", result.getPostTitle());
        verify(postRepository, times(1)).findById(id);
    }

    @Test
    void getPostById_WithNonExistingId_ThrowsNoSuchElementException() {
        // Arrange
        int id = 1;
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> postService.getPostById(id));
        verify(postRepository, times(1)).findById(id);
    }

    @Test
    void createPost_ReturnsCreatedPost() {

        Category category = new Category("Category1");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Tag1"));
        tags.add(new Tag("Tag2"));
        Post postToCreate = new Post("NewPost", "NewContent", tags, category);
        when(postRepository.save(postToCreate)).thenReturn(postToCreate);

        Post result = postService.createPost(postToCreate);

        assertEquals("NewPost", result.getPostTitle());
    }
}
