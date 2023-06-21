package com.spring.blog_management.controller;
import com.spring.blog_management.entity.Tag;
import com.spring.blog_management.service.TagService;
import com.spring.blog_management.controller.TagController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TagControllerTest {
    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTags() {
        List<Tag> tags = Arrays.asList(new Tag("Tag1"), new Tag("Tag2"));
        when(tagService.getAllTags()).thenReturn(tags);

        List<Tag> result = tagController.getAllTags();

        assertEquals(tags, result);
        verify(tagService, times(1)).getAllTags();
    }

    @Test
    void testGetTagById() {

        int tagId = 1;
        Tag tag = new Tag("Tag1");
        when(tagService.getTagById(tagId)).thenReturn(tag);

        Tag result = tagController.getTagById(tagId);

        assertEquals(tag, result);
        verify(tagService, times(1)).getTagById(tagId);
    }

    @Test
    void testCreateTag() {

        Tag tag = new Tag("Tag1");
        Tag createdTag = new Tag("Tag1");
        when(tagService.createTag(tag)).thenReturn(createdTag);

        Tag result = tagController.createTag(tag);

        assertEquals(createdTag, result);
        verify(tagService, times(1)).createTag(tag);
    }

    @Test
    void testUpdateTag() {

        int tagId = 1;
        Tag tag = new Tag("UpdatedTag");
        when(tagService.updateTag(any(Tag.class))).thenReturn(tag);

        Tag result = tagController.updateTag(tagId, tag);

        assertEquals(tag, result);
        verify(tagService, times(1)).updateTag(any(Tag.class));
    }

    @Test
    void testDeleteTag() {

        int tagId = 1;

        ResponseEntity<Void> response = tagController.deleteTag(tagId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tagService, times(1)).deleteTag(tagId);
    }
}
