package com.spring.blog_management.service;

import com.spring.blog_management.dao.TagRepository;
import com.spring.blog_management.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTags_ReturnsListOfTags() {

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Tag1"));
        tags.add(new Tag("Tag2"));
        when(tagRepository.findAll()).thenReturn(tags);

        List<Tag> result = tagService.getAllTags();

        assertEquals(2, result.size());
        assertEquals("Tag1", result.get(0).getTagName());
        assertEquals("Tag2", result.get(1).getTagName());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void getTagById_WithExistingId_ReturnsTag() {

        int id = 0;
        Tag tag = new Tag("Tag1");
        when(tagRepository.findById(id)).thenReturn(Optional.of(tag));

        Tag result = tagService.getTagById(id);

        assertEquals(id, result.getId());
        assertEquals("Tag1", result.getTagName());
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void getTagById_WithNonExistingId_ThrowsNoSuchElementException() {

        int id = 1;
        when(tagRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> tagService.getTagById(id));
        verify(tagRepository, times(1)).findById(id);
    }

    @Test
    void createTag_ReturnsCreatedTag() {

        Tag tagToCreate = new Tag("NewTag");
        Tag createdTag = new Tag("NewTag");
        when(tagRepository.save(tagToCreate)).thenReturn(createdTag);

        Tag result = tagService.createTag(tagToCreate);

        assertEquals(0, result.getId());
        assertEquals("NewTag", result.getTagName());
        verify(tagRepository, times(1)).save(tagToCreate);
    }

    @Test
    void updateTag_ReturnsUpdatedTag() {

        int id = 0;
        Tag tagToUpdate = new Tag("UpdatedTag");
        when(tagRepository.save(tagToUpdate)).thenReturn(tagToUpdate);

        Tag result = tagService.updateTag(tagToUpdate);

        assertEquals(id, result.getId());
        assertEquals("UpdatedTag", result.getTagName());
        verify(tagRepository, times(1)).save(tagToUpdate);
    }

    @Test
    void deleteTag_WithExistingId_DeletesTag() {

        int id = 0;

        tagService.deleteTag(id);

        verify(tagRepository, times(1)).deleteById(id);
    }
}
