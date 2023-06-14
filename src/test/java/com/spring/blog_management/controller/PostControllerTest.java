package com.spring.blog_management.controller;

import com.spring.blog_management.entity.Category;
import com.spring.blog_management.entity.Post;
import com.spring.blog_management.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPosts() {

        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Title 1", "Content 1", new HashSet<>(), new Category()));
        posts.add(new Post("Title 2", "Content 2", new HashSet<>(), new Category()));
        when(postService.getAllPosts()).thenReturn(posts);

        List<Post> result = postController.getAllPosts();

        assertEquals(posts, result);
        verify(postService, times(1)).getAllPosts();
    }

    @Test
    void testGetPostById() {

        int postId = 1;
        Post post = new Post("Title 1", "Content 1", new HashSet<>(), new Category());
        when(postService.getPostById(postId)).thenReturn(post);

        Post result = postController.getPostById(postId);

        assertEquals(post, result);
        verify(postService, times(1)).getPostById(postId);
    }

    @Test
    void testCreatePost() {

        Post post = new Post("Title 1", "Content 1", new HashSet<>(), new Category());
        Post createdPost = new Post("Title 1", "Content 1", new HashSet<>(), new Category());
        createdPost.setId(1);
        when(postService.createPost(post)).thenReturn(createdPost);
        ResponseEntity<Post> expectedResponse = ResponseEntity.created(URI.create("/api/posts/1")).body(createdPost);


        ResponseEntity<Post> result = postController.createPost(post);


        assertEquals(expectedResponse, result);
        verify(postService, times(1)).createPost(post);
    }

    @Test
    void testUpdatePost() {

        int postId = 1;
        Post post = new Post("Title 1", "Content 1", new HashSet<>(), new Category());
        Post updatedPost = new Post("Updated Title", "Updated Content", new HashSet<>(), new Category());
        updatedPost.setId(postId);
        when(postService.updatePost(post)).thenReturn(updatedPost);

        Post result = postController.updatePost(postId, post);

        assertEquals(updatedPost, result);
        verify(postService, times(1)).updatePost(post);
    }

    @Test
    void testDeletePost() {
        int postId = 1;

        ResponseEntity<Void> result = postController.deletePost(postId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(postService, times(1)).deletePost(postId);
    }
}
