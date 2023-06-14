package com.spring.blog_management.service;

import com.spring.blog_management.dao.PostRepository;
import com.spring.blog_management.entity.Post;
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
    void getAllPosts_ShouldReturnAllPosts() {

        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "Post 1", "Content 1"));
        posts.add(new Post(2, "Post 2", "Content 2"));
        when(postRepository.findAll()).thenReturn(posts);

        List<Post> result = postService.getAllPosts();

        assertEquals(posts, result);
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void getPostById_WithExistingId_ShouldReturnPost() {

        int postId = 1;
        Post post = new Post(postId, "Post 1", "Content 1");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(postId);

        assertEquals(post, result);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostById_WithNonExistingId_ShouldThrowNoSuchElementException() {
        int postId = 1;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> postService.getPostById(postId));
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void createPost_ShouldReturnCreatedPost() {

        Post post = new Post(1, "Post 1", "Content 1");
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.createPost(post);

        assertEquals(post, result);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void updatePost_ShouldReturnUpdatedPost() {

        Post post = new Post(1, "Post 1", "Content 1");
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.updatePost(post);

        assertEquals(post, result);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void deletePost_ShouldInvokeDeleteById() {

        int postId = 1;

        postService.deletePost(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }
}
