package com.spring.blog_management.controller;

import com.spring.blog_management.entity.Post;
import com.spring.blog_management.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        System.out.println(post);
        Post createdPost = postService.createPost(post);

        return ResponseEntity.created(URI.create("/api/posts/" + createdPost.getId())).body(createdPost);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
        post.setId(id);
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}

